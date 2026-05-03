package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.KhuyenMai;
import gui.TrangChu_GUI;
import java.text.DecimalFormat; // Import
import java.text.SimpleDateFormat; // Import

public class KhuyenMai_Controller {

    private TrangChu_GUI view;
    private DecimalFormat df = new DecimalFormat("#,##0.##"); 

    public KhuyenMai_Controller(TrangChu_GUI view) {
        this.view = view;

        loadDataToTable(view.table_ThemKM);
        loadDataToTable(view.table_CapNhatKM);
        loadDataToTable(view.table_TimKiemKM);

        addThemKMListeners();
        
        addCapNhatKMListeners();
        
        addTimKiemKMListeners();
        clearThemKMForm();
    }

    private void loadDataToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<KhuyenMai> dsKM = (List<KhuyenMai>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_KHUYEN_MAI, null)).getData();
        for (KhuyenMai km : dsKM) {
            model.addRow(new Object[]{
                km.getMaKM(),
                km.getTenChuongTrinh(),
                km.getGiaTri(),
                km.getNgayBatDau(),
                km.getNgayKetThuc(),
                km.getSoLuongToiDa() == 0 ? "Không giới hạn" : km.getSoLuongToiDa(),
                km.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc"
            });
        }
    }
    private void updateKhuyenMaiComboBox() {
        if (view.cb_Chonkhuyenmai != null) {
            DefaultComboBoxModel<KhuyenMai> model = (DefaultComboBoxModel<KhuyenMai>) view.cb_Chonkhuyenmai.getModel();
            model.removeAllElements();
            
            KhuyenMai kmMacDinh = new KhuyenMai();
            kmMacDinh.setMaKM(null);
            kmMacDinh.setTenChuongTrinh("Không áp dụng");
            model.addElement(kmMacDinh);
            
            List<KhuyenMai> dsKM = (List<KhuyenMai>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_KHUYEN_MAI, null)).getData();
            for (KhuyenMai km : dsKM) {
                if (km.getTrangThai() == 1 && (km.getNgayKetThuc().isAfter(LocalDate.now()) || km.getNgayKetThuc().isEqual(LocalDate.now()))) {
                    model.addElement(km); 
                }
            }
        }
    }


    private void addThemKMListeners() {
        view.btnThem_ThemKM.addActionListener(e -> themKhuyenMai());
        view.btnLamMoi_ThemKM.addActionListener(e -> clearThemKMForm());
    }

    private void clearThemKMForm() {
    	view.txtMaKM_Them.setText((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_KHUYEN_MAI, null)).getData());
        view.txtTenKM_Them.setText("");
        view.txtGiaTri_Them.setText("");
        view.txtSoLuong_Them.setText("");
        view.dateNgayBD_Them.setDate(null);
        view.dateNgayKT_Them.setDate(null);
        view.cboTrangThai_Them.setSelectedIndex(0);
        view.txtMaKM_Them.requestFocus();
    }

    private void themKhuyenMai() {
        String maKM = view.txtMaKM_Them.getText().trim();
        String tenKM = view.txtTenKM_Them.getText().trim();
        if (maKM.isEmpty() || tenKM.isEmpty()) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Mã và Tên khuyến mãi không được rỗng!");
            return;
        }

        try {
            double giaTri = Double.parseDouble(view.txtGiaTri_Them.getText());
            int soLuong = 0;
            if (!view.txtSoLuong_Them.getText().trim().isEmpty()) {
                soLuong = Integer.parseInt(view.txtSoLuong_Them.getText());
            }
            
            Date ngayBDUtil = view.dateNgayBD_Them.getDate();
            Date ngayKTUtil = view.dateNgayKT_Them.getDate();
            if(ngayBDUtil == null || ngayKTUtil == null) {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Ngày bắt đầu và kết thúc không được rỗng!");
                 return;
            }
            LocalDate ngayBD = ngayBDUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngayKT = ngayKTUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if(ngayKT.isBefore(ngayBD)) {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Ngày kết thúc phải sau ngày bắt đầu!");
                 return;
            }
            
            int trangThai = view.cboTrangThai_Them.getSelectedItem().equals("Đang diễn ra") ? 1 : 0;
            
            KhuyenMai km = new KhuyenMai(maKM, tenKM, giaTri, ngayBD, ngayKT, soLuong, trangThai);

            if ((boolean) SocketClient.sendRequest(new Request(ActionType.ADD_KHUYEN_MAI, km)).getData()) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Thêm khuyến mãi thành công!");
                loadDataToTable(view.table_ThemKM);
                loadDataToTable(view.table_CapNhatKM);
                loadDataToTable(view.table_TimKiemKM);
                updateKhuyenMaiComboBox(); 
                clearThemKMForm();
            } else {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Thêm thất bại! (Trùng mã KM?)");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Giá trị và Số lượng phải là số!");
        }
    }
    
    private void addCapNhatKMListeners() {
        view.table_CapNhatKM.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.table_CapNhatKM.getSelectedRow();
                if(row != -1) {
                    view.txtMaKM_CapNhat.setText(view.table_CapNhatKM.getValueAt(row, 0).toString());
                    view.txtTenKM_CapNhat.setText(view.table_CapNhatKM.getValueAt(row, 1).toString());
                    view.txtGiaTri_CapNhat.setText(view.table_CapNhatKM.getValueAt(row, 2).toString());
                    
                    LocalDate ngayBD = (LocalDate) view.table_CapNhatKM.getValueAt(row, 3);
                    view.dateNgayBD_CapNhat.setDate(Date.from(ngayBD.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    
                    LocalDate ngayKT = (LocalDate) view.table_CapNhatKM.getValueAt(row, 4);
                    view.dateNgayKT_CapNhat.setDate(Date.from(ngayKT.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    
                    String soLuongStr = view.table_CapNhatKM.getValueAt(row, 5).toString();
                    view.txtSoLuong_CapNhat.setText(soLuongStr.equals("Không giới hạn") ? "" : soLuongStr);
                    
                    view.cboTrangThai_CapNhat.setSelectedItem(view.table_CapNhatKM.getValueAt(row, 6).toString());
                }
            }
        });

        view.btnCapNhat_CapNhatKM.addActionListener(e -> capNhatKhuyenMai());
        
        view.btnXoa_CapNhatKM.addActionListener(e -> xoaKhuyenMai());

        view.btnKhoiPhuc_CapNhatKM.addActionListener(e -> {
             int row = view.table_CapNhatKM.getSelectedRow();
             if(row != -1) {
                 view.table_CapNhatKM.getMouseListeners()[view.table_CapNhatKM.getMouseListeners().length - 1]
                     .mouseClicked(null); // Tái sử dụng logic click
             }
        });
    }

    private void capNhatKhuyenMai() {
        String maKM = view.txtMaKM_CapNhat.getText();
        if (maKM.isEmpty()) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn khuyến mãi từ bảng!");
            return;
        }
        String tenKM = view.txtTenKM_CapNhat.getText().trim();
        try {
            double giaTri = Double.parseDouble(view.txtGiaTri_CapNhat.getText());
            int soLuong = 0;
            if (!view.txtSoLuong_CapNhat.getText().trim().isEmpty()) {
                soLuong = Integer.parseInt(view.txtSoLuong_CapNhat.getText());
            }
            Date ngayBDUtil = view.dateNgayBD_CapNhat.getDate();
            Date ngayKTUtil = view.dateNgayKT_CapNhat.getDate();
            LocalDate ngayBD = ngayBDUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ngayKT = ngayKTUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int trangThai = view.cboTrangThai_CapNhat.getSelectedItem().equals("Đang diễn ra") ? 1 : 0;
            
            KhuyenMai km = new KhuyenMai(maKM, tenKM, giaTri, ngayBD, ngayKT, soLuong, trangThai);

            if ((boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_KHUYEN_MAI, km)).getData()) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Cập nhật thành công!");
                loadDataToTable(view.table_ThemKM);
                loadDataToTable(view.table_CapNhatKM);
                loadDataToTable(view.table_TimKiemKM);
                updateKhuyenMaiComboBox(); // (REQ 7)
            } else {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Cập nhật thất bại!");
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Lỗi dữ liệu: " + e.getMessage());
        }
    }

    private void xoaKhuyenMai() {
        String maKM = view.txtMaKM_CapNhat.getText();
        if (maKM.isEmpty()) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn khuyến mãi từ bảng để ngưng hoạt động!");
            return;
        }
        
        // Sửa câu hỏi xác nhận cho phù hợp hành động
        int confirm = JOptionPane.showConfirmDialog(view.QuanLyHieuThuocTay, 
                "Xác nhận ngưng hoạt động chương trình khuyến mãi " + maKM + "?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
                
        if (confirm == JOptionPane.YES_OPTION) {
            if ((boolean) SocketClient.sendRequest(new Request(ActionType.DELETE_KHUYEN_MAI, maKM)).getData()) {
                // Thông báo thành công
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Đã ngưng chương trình khuyến mãi thành công!");
                
                // Cập nhật lại các bảng dữ liệu
                loadDataToTable(view.table_ThemKM);
                loadDataToTable(view.table_CapNhatKM);
                loadDataToTable(view.table_TimKiemKM);
                updateKhuyenMaiComboBox(); 
                
                // Xóa trắng form sau khi xử lý (Optional)
                view.txtMaKM_CapNhat.setText("");
                view.txtTenKM_CapNhat.setText("");
                view.txtGiaTri_CapNhat.setText("");
                view.txtSoLuong_CapNhat.setText("");
                view.dateNgayBD_CapNhat.setDate(null);
                view.dateNgayKT_CapNhat.setDate(null);
                view.cboTrangThai_CapNhat.setSelectedIndex(0);
                
            } else {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Thao tác thất bại!");
            }
        }
    }
    
    // --- Logic Panel Tìm Kiếm KM (REQ 7) ---
    private void addTimKiemKMListeners() {
        // 1. Tạo 1 listener chung cho các ô text và date
        DocumentListener filterDocListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterKhuyenMai(); }
            @Override public void removeUpdate(DocumentEvent e) { filterKhuyenMai(); }
            @Override public void changedUpdate(DocumentEvent e) { filterKhuyenMai(); }
        };
        
        view.txtMaTenKM_TK.getDocument().addDocumentListener(filterDocListener);
        
        // Listener cho JDateChooser (cần PropertyChangeListener)
        PropertyChangeListener dateListener = pce -> filterKhuyenMai();
        view.dateNgayBD_TK.addPropertyChangeListener("date", dateListener);
        view.dateNgayKT_TK.addPropertyChangeListener("date", dateListener);
        
        // Listener cho ComboBox Trạng Thái
        view.cboTrangThai_TK.addActionListener(e -> filterKhuyenMai());
        
        // Nút Làm Mới
        view.btnLamMoi_TK.addActionListener(e -> {
            view.txtMaTenKM_TK.setText("");
            view.dateNgayBD_TK.setDate(null);
            view.dateNgayKT_TK.setDate(null);
            view.cboTrangThai_TK.setSelectedIndex(0); // Về "Tất cả"
            loadDataToTable(view.table_TimKiemKM); // Load lại toàn bộ
        });
        
     // 2. Lắng nghe ô tìm kiếm trong tab CẬP NHẬT
        view.txtMaTenKM_TK_CapNhat.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterCapNhatKMTable(); }
            @Override public void removeUpdate(DocumentEvent e) { filterCapNhatKMTable(); }
            @Override public void changedUpdate(DocumentEvent e) { filterCapNhatKMTable(); }
        });
        
        // 3. Nút Làm Mới TK (trong tab CẬP NHẬT)
        view.btnLamMoi_TK_CapNhatKM.addActionListener(e -> {
            view.txtMaTenKM_TK_CapNhat.setText("");
            loadDataToTable(view.table_CapNhatKM); // Tải lại toàn bộ bảng Cập Nhật
        });
    }
	// THÊM HÀM MỚI NÀY
    private void filterKhuyenMai() {
        try {
            String maTen = view.txtMaTenKM_TK.getText().trim();
            
            Date tuNgayUtil = view.dateNgayBD_TK.getDate();
            LocalDate tuNgay = (tuNgayUtil != null) ? tuNgayUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            
            Date denNgayUtil = view.dateNgayKT_TK.getDate();
            LocalDate denNgay = (denNgayUtil != null) ? denNgayUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

            int trangThai;
            String trangThaiStr = view.cboTrangThai_TK.getSelectedItem().toString();
            if (trangThaiStr.equals("Đang diễn ra")) {
                trangThai = 1;
            } else if (trangThaiStr.equals("Đã kết thúc")) {
                trangThai = 0;
            } else {
                trangThai = -1; // "Tất cả"
            }

            List<KhuyenMai> dsKM = (List<KhuyenMai>) SocketClient.sendRequest(new Request(ActionType.SEARCH_KHUYEN_MAI, new Object[]{maTen, tuNgay, denNgay, trangThai})).getData();
            
            DefaultTableModel model = (DefaultTableModel) view.table_TimKiemKM.getModel();
            model.setRowCount(0);
            for (KhuyenMai km : dsKM) {
                model.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenChuongTrinh(),
                    km.getGiaTri(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getSoLuongToiDa() == 0 ? "Không giới hạn" : km.getSoLuongToiDa(),
                    km.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc"
                });
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }
 // (Thêm vào KhuyenMai_Controller.java)

    // Hàm lọc cho bảng TÌM KIẾM
    private void filterTimKiemKMTable() {
        try {
            String maTen = view.txtMaTenKM_TK.getText().trim();
            
            Date tuNgayUtil = view.dateNgayBD_TK.getDate();
            LocalDate tuNgay = (tuNgayUtil != null) ? tuNgayUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
            
            Date denNgayUtil = view.dateNgayKT_TK.getDate();
            LocalDate denNgay = (denNgayUtil != null) ? denNgayUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

            int trangThai;
            String trangThaiStr = view.cboTrangThai_TK.getSelectedItem().toString();
            if (trangThaiStr.equals("Đang diễn ra")) {
                trangThai = 1;
            } else if (trangThaiStr.equals("Đã kết thúc")) {
                trangThai = 0;
            } else {
                trangThai = -1; // "Tất cả"
            }

            List<KhuyenMai> dsKM = (List<KhuyenMai>) SocketClient.sendRequest(new Request(ActionType.SEARCH_KHUYEN_MAI, new Object[]{maTen, tuNgay, denNgay, trangThai})).getData();
            
            DefaultTableModel model = (DefaultTableModel) view.table_TimKiemKM.getModel();
            model.setRowCount(0);
            for (KhuyenMai km : dsKM) {
                model.addRow(new Object[]{
                    km.getMaKM(),
                    km.getTenChuongTrinh(),
                    km.getGiaTri(),
                    km.getNgayBatDau(),
                    km.getNgayKetThuc(),
                    km.getSoLuongToiDa() == 0 ? "Không giới hạn" : km.getSoLuongToiDa(),
                    km.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc"
                });
            }
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        }
    }

    // Hàm lọc cho bảng CẬP NHẬT
    private void filterCapNhatKMTable() {
        String maTen = view.txtMaTenKM_TK_CapNhat.getText().trim();
        
        // Dùng hàm search (chỉ tìm theo mã/tên)
        List<KhuyenMai> dsKM = (List<KhuyenMai>) SocketClient.sendRequest(new Request(ActionType.SEARCH_KHUYEN_MAI, new Object[]{maTen, null, null, -1})).getData(); // -1 là "Tất cả"
        
        DefaultTableModel model = (DefaultTableModel) view.table_CapNhatKM.getModel();
        model.setRowCount(0);
        for (KhuyenMai km : dsKM) {
            model.addRow(new Object[]{
                km.getMaKM(),
                km.getTenChuongTrinh(),
                km.getGiaTri(),
                km.getNgayBatDau(),
                km.getNgayKetThuc(),
                km.getSoLuongToiDa() == 0 ? "Không giới hạn" : km.getSoLuongToiDa(),
                km.getTrangThai() == 1 ? "Đang diễn ra" : "Đã kết thúc"
            });
        }
    }
}
