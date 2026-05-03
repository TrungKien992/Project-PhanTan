package controller;
// Force Eclipse refresh

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.KhachHang;
import gui.TrangChu_GUI;


public class KhachHang_Controller implements ActionListener {

    private TrangChu_GUI trangChuGUI;

    // biến tạm để lưu thông tin gốc của khách hàng đang chọn
    private KhachHang khachHangGoc = null;

    public KhachHang_Controller(TrangChu_GUI trangChuGUI) {
        this.trangChuGUI = trangChuGUI;

        // hiển thị danh sách khách hàng khi khởi tạo controller
        hienThiDanhSachKhachHang();

        // gán sự kiện cho các nút
        trangChuGUI.btnLammoi_CNKH.addActionListener(this);
        trangChuGUI.btn_kh_Lammoi.addActionListener(this);
        trangChuGUI.btn_cnkh_CapNhat.addActionListener(this);
        trangChuGUI.btn_cnkh_Khoiphuc.addActionListener(this); 
        trangChuGUI.btn_CNKH_Xoa.addActionListener(this); // GẮN SỰ KIỆN CHO NÚT XÓA

        
        // gán sự kiện click cho bảng
        trangChuGUI.table_CapNhatKH.addMouseListener(new MouseAdapter() {
        	
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiThongTinLenTextField();
            } 
        });
        
        
        // sự kiện tìm kiếm theo mã hoặc tên khách hàng (Tab Cập nhật)
        trangChuGUI.txtMKH_TK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemKhachHang(); // Đã sửa đổi để chỉ tìm KH Active
            }
        });

        trangChuGUI.txtTenKH_TK.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemKhachHang(); // Đã sửa đổi để chỉ tìm KH Active
            }
        });

        // sự kiện tìm kiếm (Tab Tìm kiếm)
        trangChuGUI.txt_kh_MaKH.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyReleased(KeyEvent e) {
                timKiemKhachHang_TK(); // Đã sửa đổi để chỉ tìm KH Active
            }
		});
        
        trangChuGUI.txt_kh_TenKH.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyReleased(KeyEvent e) {
        		timKiemKhachHang_TK(); // Đã sửa đổi để chỉ tìm KH Active
            }
		});
        
        trangChuGUI.txt_kh_SDT.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyReleased(KeyEvent e) {
        		timKiemKhachHang_TK(); // Đã sửa đổi để chỉ tìm KH Active
            }
		});
        
        trangChuGUI.txt_kh_dc.addKeyListener(new KeyAdapter() {
        	@Override
            public void keyReleased(KeyEvent e) {
        		timKiemKhachHang_TK(); // Đã sửa đổi để chỉ tìm KH Active
            }
		});
    }


    /**
     * hiển thị danh sách khách hàng lên JTable
     * CHỈ HIỂN THỊ KHÁCH HÀNG CÓ TRẠNG THÁI TRUE (ACTIVE)
     */
    public void hienThiDanhSachKhachHang() {
        // LẤY DANH SÁCH KHÁCH HÀNG ĐANG ACTIVE (trangThai = 1)
        List<KhachHang> dsKH = (List<KhachHang>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_KHACH_HANG, null)).getData(); 
        DefaultTableModel modelCN = (DefaultTableModel) trangChuGUI.table_CapNhatKH.getModel();
        DefaultTableModel modelTK = (DefaultTableModel) trangChuGUI.table_tkkh.getModel();

        modelCN.setRowCount(0);
        modelTK.setRowCount(0);
        for (KhachHang kh : dsKH) {
            
            Object[] row = { kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getSoDienThoai() };
            modelCN.addRow(row);
            modelTK.addRow(row);
        }
    }

    /**
     * hiển thị thông tin khách hàng lên textfield khi click vào bảng
     */
    private void hienThiThongTinLenTextField() {
        int row = trangChuGUI.table_CapNhatKH.getSelectedRow();
        if (row >= 0) {
            String maKH = trangChuGUI.table_CapNhatKH.getValueAt(row, 0).toString();
            String tenKH = trangChuGUI.table_CapNhatKH.getValueAt(row, 1).toString();
       
            String diaChi = trangChuGUI.table_CapNhatKH.getValueAt(row, 2).toString(); 
            String sdt = trangChuGUI.table_CapNhatKH.getValueAt(row, 3).toString();   

            trangChuGUI.txt_cnkh_MaKh.setText(maKH);
            trangChuGUI.txt_cnkh_tenkh.setText(tenKH);
            trangChuGUI.txt_cnkh_dc.setText(diaChi); 
            trangChuGUI.txt_cnkh_SDt.setText(sdt);   

            // Lấy KhachHang đầy đủ từ DAO để lấy đúng trạng thái (mặc định là true nếu chưa cập nhật)
            khachHangGoc = (KhachHang) SocketClient.sendRequest(new Request(ActionType.GET_KHACH_HANG_BY_MA, maKH)).getData(); 
        }
    }

    /**
     * làm mới form (xóa nội dung textfield)
     */
    private void lamMoiForm() {
    	// xóa nội dung tìm kiếm
        trangChuGUI.txtMKH_TK.setText("");
        trangChuGUI.txtTenKH_TK.setText("");
        // Xóa nội dung nhập/cập nhật
        trangChuGUI.txt_cnkh_MaKh.setText("");
        trangChuGUI.txt_cnkh_tenkh.setText("");
        trangChuGUI.txt_cnkh_dc.setText("");
        trangChuGUI.txt_cnkh_SDt.setText("");
        
        hienThiDanhSachKhachHang();
    }
    
    private void lammoitimkiemkhachhang() {
    	// xóa nội dung tìm kiếm
        trangChuGUI.txt_kh_MaKH.setText("");
        trangChuGUI.txt_kh_TenKH.setText("");
        trangChuGUI.txt_kh_SDT.setText("");
        trangChuGUI.txt_kh_dc.setText("");
        hienThiDanhSachKhachHang();
    }

    /**
     * khôi phục thông tin gốc của khách hàng đang chọn
     */
    private void khoiPhucThongTin() {
        if (khachHangGoc == null) {
            JOptionPane.showMessageDialog(null, "Không có thông tin để khôi phục! Vui lòng chọn khách hàng.");
            return;
        }

        trangChuGUI.txt_cnkh_MaKh.setText(khachHangGoc.getMaKH());
        trangChuGUI.txt_cnkh_tenkh.setText(khachHangGoc.getTenKH());
        trangChuGUI.txt_cnkh_dc.setText(khachHangGoc.getDiaChi());
        trangChuGUI.txt_cnkh_SDt.setText(khachHangGoc.getSoDienThoai());
    }

    /**
     * kiểm tra hợp lệ dữ liệu khách hàng (Giữ nguyên)
     */
    private boolean validData(String tenKH, String sdt, String diaChi) {
        // ... (Giữ nguyên các validation) ...
        if (tenKH.isEmpty() || sdt.isEmpty() || diaChi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng!");
            return false;
        }

        String regexTen = "^[A-ZÀ-ỴĐ][a-zà-ỹđ]+( [A-ZÀ-ỴĐ][a-zà-ỹđ]+)*$";
        if (!tenKH.matches(regexTen)) {
            JOptionPane.showMessageDialog(null,
                "Họ tên không hợp lệ!\n- Viết hoa chữ cái đầu mỗi từ\n- Không chứa số hoặc ký tự đặc biệt.");
            return false;
        }

        if (!sdt.matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải gồm đúng 10 chữ số!");
            return false;
        }

        String regexDiaChi = "^[a-zA-Z0-9À-Ỵà-ỹĐđ\\s,\\.]+$";
        if (!diaChi.matches(regexDiaChi)) {
            JOptionPane.showMessageDialog(null, "Địa chỉ không được chứa ký tự đặc biệt!");
            return false;
        }

        return true;
    }
    
    /**
     * Tìm kiếm khách hàng theo mã hoặc tên (Tab Cập nhật)
     * SỬ DỤNG HÀM searchKhachHang CỦA DAO ĐỂ CHỈ LẤY KH ACTIVE
     */
    private void timKiemKhachHang() {
        String maTK = trangChuGUI.txtMKH_TK.getText().trim();
        String tenTK = trangChuGUI.txtTenKH_TK.getText().trim();
        
        // Sử dụng hàm search đã được lọc trangThai=1 trong DAO
        List<KhachHang> dsKH = (List<KhachHang>) SocketClient.sendRequest(new Request(ActionType.SEARCH_KHACH_HANG, new Object[]{maTK, tenTK, "", ""})).getData(); 
        
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_CapNhatKH.getModel();
        model.setRowCount(0);

        for (KhachHang kh : dsKH) {
            Object[] row = { kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getSoDienThoai() };
            model.addRow(row);
        }

        if (maTK.isEmpty() && tenTK.isEmpty()) {
            hienThiDanhSachKhachHang();
        }
    }
    
    /**
     * Tìm kiếm khách hàng (Tab Tìm kiếm)
     * SỬ DỤNG HÀM searchKhachHang CỦA DAO ĐỂ CHỈ LẤY KH ACTIVE
     */
    private void timKiemKhachHang_TK() {
        String maTK = trangChuGUI.txt_kh_MaKH.getText().trim();
        String tenTK = trangChuGUI.txt_kh_TenKH.getText().trim();
        String sdtTK = trangChuGUI.txt_kh_SDT.getText().trim();
        String dcTK = trangChuGUI.txt_kh_dc.getText().trim();
        
        // Sử dụng hàm search đã được lọc trangThai=1 trong DAO
        List<KhachHang> dsKH = (List<KhachHang>) SocketClient.sendRequest(new Request(ActionType.SEARCH_KHACH_HANG, new Object[]{maTK, tenTK, sdtTK, dcTK})).getData(); 
        
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_tkkh.getModel();
        model.setRowCount(0);

        for (KhachHang kh : dsKH) {
            Object[] row = { kh.getMaKH(), kh.getTenKH(), kh.getDiaChi(), kh.getSoDienThoai() };
            model.addRow(row);
        }

        if (maTK.isEmpty() && tenTK.isEmpty() && sdtTK.isEmpty() && dcTK.isEmpty()) {
            hienThiDanhSachKhachHang();
        }
    }



    /**
     * cập nhật thông tin khách hàng (Cần đảm bảo truyền trangThai)
     */
    private void capNhatKhachHang() {
        String maKH = trangChuGUI.txt_cnkh_MaKh.getText().trim();
        String tenKH = trangChuGUI.txt_cnkh_tenkh.getText().trim();
        String diaChi = trangChuGUI.txt_cnkh_dc.getText().trim();
        String sdt = trangChuGUI.txt_cnkh_SDt.getText().trim();


        if (!validData(tenKH, sdt, diaChi)) {
            return;
        }

        int row = trangChuGUI.table_CapNhatKH.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần cập nhật trong bảng!");
            return;
        }

        // Lấy thông tin gốc từ bảng (Nếu cần so sánh thì phải lấy từ bảng)
        String oldTen = trangChuGUI.table_CapNhatKH.getValueAt(row, 1).toString();
        String oldDiaChi = trangChuGUI.table_CapNhatKH.getValueAt(row, 2).toString();
        String oldSDT = trangChuGUI.table_CapNhatKH.getValueAt(row, 3).toString();


        if (tenKH.equals(oldTen) && sdt.equals(oldSDT) && diaChi.equals(oldDiaChi)) {
            JOptionPane.showMessageDialog(null, "Không có thay đổi nào để cập nhật!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn cập nhật thông tin khách hàng này không?",
                "Xác nhận cập nhật", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Lấy trạng thái hiện tại từ khachHangGoc (đã được gán khi click chuột)
            boolean currentTrangThai = true; 
            if (khachHangGoc != null && khachHangGoc.getMaKH().equals(maKH)) {
                 currentTrangThai = khachHangGoc.isTrangThai();
            }
            
            // SỬ DỤNG CONSTRUCTOR ĐẦY ĐỦ THAM SỐ (ma, ten, sdt, diaChi, trangThai)
            KhachHang kh = new KhachHang(maKH, tenKH, sdt, diaChi, currentTrangThai); 
            boolean result = (boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_KHACH_HANG, kh)).getData();

            if (result) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                hienThiDanhSachKhachHang();
                lamMoiForm();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại! Vui lòng thử lại.");
            }
        }
    }
    
    /**
     * Xóa khách hàng (Set trangThai = 0)
     */
    private void xoaKhachHang() {
        int row = trangChuGUI.table_CapNhatKH.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần xóa trong bảng!");
            return;
        }

        String maKH = trangChuGUI.txt_cnkh_MaKh.getText().trim();

        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn XÓA (Ngừng giao dịch) khách hàng có mã " + maKH + " này không?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = (boolean) SocketClient.sendRequest(new Request(ActionType.DELETE_KHACH_HANG, maKH)).getData(); // Gọi hàm delete mới

            if (result) {
                JOptionPane.showMessageDialog(null, "Xóa thành công!");
                hienThiDanhSachKhachHang(); // Cập nhật lại bảng (Khách hàng vừa xóa sẽ biến mất)
                lamMoiForm(); // Xóa nội dung trên form
            } else {
                JOptionPane.showMessageDialog(null, "Xóa thất bại! Vui lòng thử lại.");
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(trangChuGUI.btnLammoi_CNKH)) {
                lamMoiForm();
        } else if (o.equals(trangChuGUI.btn_cnkh_CapNhat)) {
                capNhatKhachHang();
        } else if (o.equals(trangChuGUI.btn_cnkh_Khoiphuc)) {
                khoiPhucThongTin();
        } else if (o.equals(trangChuGUI.btn_CNKH_Xoa)) { // XỬ LÝ SỰ KIỆN NÚT XÓA
        	    xoaKhachHang();
        } else if(o.equals(trangChuGUI.btn_kh_Lammoi)) {
        	lammoitimkiemkhachhang();
        }

    }

}