package controller;

import gui.DialogChiTietPhieuDatHangNCC;
import gui.TrangChu_GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.*;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhieuDatHang_Controller {

    private TrangChu_GUI trangChuGUI;
    private DecimalFormat currencyFormatter = new DecimalFormat("###,##0 VNĐ");

    private List<ChiTietPhieuDatHang> chiTietTamThoi = new ArrayList<>();
    private PhieuDatHang phieuHienTai = null;
    private NhanVien nhanVienHienTai = null;

    public PhieuDatHang_Controller(TrangChu_GUI trangChuGUI) {
        this.trangChuGUI = trangChuGUI;

        this.nhanVienHienTai = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA, "NV001")).getData();
        if (this.nhanVienHienTai == null) {
             System.err.println("Lỗi: Không tìm thấy nhân viên mặc định NV001!");
             this.nhanVienHienTai = new NhanVien(); 
             this.nhanVienHienTai.setMaNV("UNKNOWN");
        }


        registerPhieuDatHangEvents();
        loadDataToPhieuDatHangTable();
        loadNccToFilterComboBox();
    }

    private void registerPhieuDatHangEvents() {
        trangChuGUI.btnTaoMoiPhieu.addActionListener(e -> moDialogTaoMoi());
        trangChuGUI.btnXemSuaPhieu.addActionListener(e -> moDialogXemSua());
        trangChuGUI.btnHuyPhieu.addActionListener(e -> huyPhieuDatHang());
        trangChuGUI.btnLamMoiBoLoc_PDH.addActionListener(e -> lamMoiBoLocTimKiem());

        KeyAdapter filterKeyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) { filterPhieuDatHangTable(); }
        };
        trangChuGUI.txtMaPhieu_TKNCC.addKeyListener(filterKeyListener);
        trangChuGUI.txtTongTien_TKNCC.addKeyListener(filterKeyListener);

        ItemListener filterItemListener = e -> { if (e.getStateChange() == ItemEvent.SELECTED) filterPhieuDatHangTable(); };
        trangChuGUI.cboNCC_TKNCC.addItemListener(filterItemListener);
        trangChuGUI.cboTrangThai_TKNCC_PDH.addItemListener(filterItemListener);
        trangChuGUI.cboLoaiTongTien_TKNCC.addItemListener(filterItemListener);

        PropertyChangeListener dateChangeListener = evt -> { if ("date".equals(evt.getPropertyName())) filterPhieuDatHangTable(); };
        trangChuGUI.dateChooserTuNgay_TKNCC.addPropertyChangeListener(dateChangeListener);
        trangChuGUI.dateChooserDenNgay_TKNCC.addPropertyChangeListener(dateChangeListener);
    }


    private void moDialogTaoMoi() {
        DialogChiTietPhieuDatHangNCC dialog = new DialogChiTietPhieuDatHangNCC(trangChuGUI);

        chiTietTamThoi.clear();
        phieuHienTai = new PhieuDatHang();
        
        phieuHienTai.setMaPhieu((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_PHIEU_DAT_HANG, null)).getData());
        
        phieuHienTai.setNgayDat(LocalDate.now());
        phieuHienTai.setTrangThai("Đã đặt hàng");
        phieuHienTai.setNhanVien(nhanVienHienTai);

        loadDuLieuChoDialogMoi(dialog);
        registerDialogEvents(dialog);
        dialog.setVisible(true);
    }

    private void moDialogXemSua() {
        int selectedRow = trangChuGUI.table_DSPDDH.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn phiếu để xem/sửa.", "Chưa chọn phiếu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maPhieu = trangChuGUI.table_DSPDDH.getValueAt(selectedRow, 0).toString();

        phieuHienTai = (PhieuDatHang) SocketClient.sendRequest(new Request(ActionType.GET_PHIEU_DAT_HANG_BY_MA, maPhieu)).getData();
        
        if (phieuHienTai != null) {

            chiTietTamThoi = (List<ChiTietPhieuDatHang>) SocketClient.sendRequest(new Request(ActionType.GET_DETAILS_BY_PHIEU_DAT_HANG, maPhieu)).getData();
        } else {
             JOptionPane.showMessageDialog(trangChuGUI, "Không tìm thấy dữ liệu cho mã phiếu: " + maPhieu, "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        DialogChiTietPhieuDatHangNCC dialog = new DialogChiTietPhieuDatHangNCC(trangChuGUI);
        loadDuLieuChoDialogSua(dialog);
        registerDialogEvents(dialog);
        
        if (phieuHienTai.getTrangThai().equalsIgnoreCase("Đã hủy")) {
            dialog.btnLuu.setEnabled(false);
            dialog.btnThemThuocVaoBang.setEnabled(false);
            dialog.btnXoaThuocKhoiBang.setEnabled(false);
            dialog.setTitle("Chi tiết Phiếu Đặt Hàng (Đã hủy - Chỉ xem)");
        }
        
        dialog.setVisible(true);
    }

    // === HÀM LOAD DỮ LIỆU CHO DIALOG ===
    private void loadDuLieuChoDialogMoi(DialogChiTietPhieuDatHangNCC dialog) {
        dialog.txtMaPhieu.setText(phieuHienTai.getMaPhieu());
        dialog.dateChooserNgayDat.setDate(java.sql.Date.valueOf(phieuHienTai.getNgayDat()));
        loadNhaCungCapComboBoxChoDialog(dialog);
        loadThuocComboBoxChoDialog(dialog);
        ((DefaultTableModel) dialog.tableChiTietDatHang.getModel()).setRowCount(0);
        dialog.txtTongTien.setText("0 VNĐ");
        dialog.txtGhiChu.setText("");
        if (dialog.cboNhaCungCap.getItemCount() > 0) {
             dialog.cboNhaCungCap.setSelectedIndex(0);
        }
    }

    private void loadDuLieuChoDialogSua(DialogChiTietPhieuDatHangNCC dialog) {
        loadNhaCungCapComboBoxChoDialog(dialog);
        loadThuocComboBoxChoDialog(dialog);

        dialog.txtMaPhieu.setText(phieuHienTai.getMaPhieu());
        if (phieuHienTai.getNhaCungCap() != null) {
            dialog.cboNhaCungCap.setSelectedItem(phieuHienTai.getNhaCungCap().getTenNhaCungCap());
        } else {
             dialog.cboNhaCungCap.setSelectedIndex(-1);
        }
        if (phieuHienTai.getNgayDat() != null) {
            dialog.dateChooserNgayDat.setDate(java.sql.Date.valueOf(phieuHienTai.getNgayDat()));
        } else {
            dialog.dateChooserNgayDat.setDate(null);
        }
        dialog.txtTongTien.setText(currencyFormatter.format(phieuHienTai.getTongTien()));
        dialog.txtGhiChu.setText(phieuHienTai.getGhiChu());

        capNhatBangChiTietDialog(dialog);
    }

    private void loadNhaCungCapComboBoxChoDialog(DialogChiTietPhieuDatHangNCC dialog) {
         try {
             List<NhaCungCap> dsNCC = (List<NhaCungCap>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHA_CUNG_CAP, null)).getData(); 
             dialog.cboNhaCungCap.removeAllItems();
             for (NhaCungCap ncc : dsNCC) {
                 dialog.cboNhaCungCap.addItem(ncc.getTenNhaCungCap());
             }
         } catch (Exception e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(dialog, "Lỗi tải danh sách Nhà Cung Cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
         }
    }

    private void loadThuocComboBoxChoDialog(DialogChiTietPhieuDatHangNCC dialog) {
         try {
             List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_THUOC, null)).getData(); 
             dialog.cboChonThuoc.removeAllItems();
             for (Thuoc t : dsThuoc) {
                 dialog.cboChonThuoc.addItem(t.getTenThuoc() + " (" + t.getMaThuoc() + ")");
             }
             // Sau khi load xong, set giá trị null để sự kiện không kích hoạt nhầm lúc đầu
             dialog.cboChonThuoc.setSelectedIndex(-1);
         } catch (Exception e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(dialog, "Lỗi tải danh sách Thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
         }
    }

    // === ĐĂNG KÝ SỰ KIỆN CHO CÁC NÚT BÊN TRONG DIALOG ===
    private void registerDialogEvents(DialogChiTietPhieuDatHangNCC dialog) {
        dialog.btnThemThuocVaoBang.addActionListener(e -> themThuocVaoBangTam(dialog));
        dialog.btnXoaThuocKhoiBang.addActionListener(e -> xoaThuocKhoiBangTam(dialog));
        dialog.btnLuu.addActionListener(e -> luuPhieuDatHang(dialog));
        dialog.btnHuy.addActionListener(e -> dialog.dispose());
        
        // --- THÊM MỚI: Sự kiện khi chọn thuốc trong ComboBox ---
        dialog.cboChonThuoc.addActionListener(e -> capNhatGiaNhapKhiChonThuoc(dialog));
    }
    
    // --- THÊM MỚI: Hàm xử lý lấy giá nhập ---
    private void capNhatGiaNhapKhiChonThuoc(DialogChiTietPhieuDatHangNCC dialog) {
        if (dialog.cboChonThuoc.getSelectedIndex() == -1 || dialog.cboChonThuoc.getSelectedItem() == null) {
            return;
        }
        try {
            String selectedThuocStr = dialog.cboChonThuoc.getSelectedItem().toString();
            // Lấy mã thuốc trong ngoặc (...)
            if (selectedThuocStr.contains("(") && selectedThuocStr.endsWith(")")) {
                String maThuoc = selectedThuocStr.substring(selectedThuocStr.lastIndexOf("(") + 1, selectedThuocStr.lastIndexOf(")"));
                
                Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
                if (thuoc != null) {
                    double giaNhap = thuoc.getGiaNhap();
                    // Format giá nhập để hiển thị lên TextField (không kèm chữ VNĐ để dễ parse lại khi thêm)
                    DecimalFormat dfInput = new DecimalFormat("###,###");
                    dialog.txtDonGiaThuoc.setText(dfInput.format(giaNhap));
                    dialog.txtSoLuongThuoc.requestFocus(); // Focus vào ô số lượng cho tiện
                }
            }
        } catch (Exception ex) {
            System.err.println("Lỗi tự động cập nhật giá: " + ex.getMessage());
        }
    }

    private void themThuocVaoBangTam(DialogChiTietPhieuDatHangNCC dialog) {
        try {
            if (dialog.cboChonThuoc.getSelectedIndex() == -1) {
                 JOptionPane.showMessageDialog(dialog, "Vui lòng chọn thuốc.", "Chưa chọn thuốc", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            String selectedThuocStr = dialog.cboChonThuoc.getSelectedItem().toString();
            int soLuong;
            double donGia;

             try {
                 soLuong = Integer.parseInt(dialog.txtSoLuongThuoc.getText().trim());
                 if (soLuong <= 0) throw new NumberFormatException();
             } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(dialog, "Số lượng phải là số nguyên dương!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                 dialog.txtSoLuongThuoc.requestFocus();
                 return;
             }

            try {
                 donGia = Double.parseDouble(dialog.txtDonGiaThuoc.getText().trim().replace(",", ""));
                 if (donGia < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(dialog, "Đơn giá phải là số không âm!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                 dialog.txtDonGiaThuoc.requestFocus();
                 return;
            }

            String maThuoc = selectedThuocStr.substring(selectedThuocStr.lastIndexOf("(") + 1, selectedThuocStr.lastIndexOf(")"));
            Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData(); 

            if (thuoc == null) {
                 JOptionPane.showMessageDialog(dialog, "Không tìm thấy thông tin thuốc với mã: " + maThuoc, "Lỗi", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            boolean updated = false;
            for (ChiTietPhieuDatHang ct : chiTietTamThoi) {
                 if (ct.getThuoc().getMaThuoc().equals(maThuoc)) {
                     ct.setSoLuong(ct.getSoLuong() + soLuong);
                     ct.setDonGia(donGia); // Cập nhật đơn giá mới
                     updated = true;
                     break;
                 }
            }
            if (!updated) {
                 ChiTietPhieuDatHang chiTietMoi = new ChiTietPhieuDatHang(phieuHienTai, thuoc, soLuong, donGia);
                 chiTietTamThoi.add(chiTietMoi);
            }

            capNhatBangChiTietDialog(dialog);
            dialog.txtSoLuongThuoc.setText("");
            dialog.txtDonGiaThuoc.setText("");
            // dialog.cboChonThuoc.setSelectedIndex(-1); // Có thể bỏ comment dòng này nếu muốn reset combobox sau khi thêm

        } catch (Exception ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(dialog, "Lỗi khi thêm thuốc: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaThuocKhoiBangTam(DialogChiTietPhieuDatHangNCC dialog) {
        int selectedRow = dialog.tableChiTietDatHang.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(dialog, "Vui lòng chọn thuốc cần xóa khỏi phiếu.", "Chưa chọn thuốc", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maThuocCanXoa = dialog.tableChiTietDatHang.getValueAt(selectedRow, 0).toString();
        chiTietTamThoi.removeIf(ct -> ct.getThuoc().getMaThuoc().equals(maThuocCanXoa));
        capNhatBangChiTietDialog(dialog);
    }

    private void capNhatBangChiTietDialog(DialogChiTietPhieuDatHangNCC dialog) {
         DefaultTableModel model = (DefaultTableModel) dialog.tableChiTietDatHang.getModel();
         model.setRowCount(0);
         double tongTienMoi = 0;
         for (ChiTietPhieuDatHang ct : chiTietTamThoi) {
             if(ct.getThuoc() != null) {
                 model.addRow(new Object[]{
                     ct.getThuoc().getMaThuoc(),
                     ct.getThuoc().getTenThuoc(),
                     ct.getSoLuong(),
                     currencyFormatter.format(ct.getDonGia()),
                     currencyFormatter.format(ct.getThanhTien())
                 });
                 tongTienMoi += ct.getThanhTien();
             }
         }
         dialog.txtTongTien.setText(currencyFormatter.format(tongTienMoi));
    }

    private double tinhTongTienTamThoi() {
        double tong = 0;
        for (ChiTietPhieuDatHang ct : chiTietTamThoi) {
            tong += ct.getThanhTien();
        }
        return tong;
    }

    private void luuPhieuDatHang(DialogChiTietPhieuDatHangNCC dialog) {
        try {
              if (dialog.cboNhaCungCap.getSelectedIndex() == -1) {
                  JOptionPane.showMessageDialog(dialog, "Vui lòng chọn nhà cung cấp!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                  return;
              }
             String tenNccChon = dialog.cboNhaCungCap.getSelectedItem().toString();
             NhaCungCap nccChon = (NhaCungCap) SocketClient.sendRequest(new Request(ActionType.GET_NHA_CUNG_CAP_BY_TEN, tenNccChon)).getData();
             if (nccChon == null) {
                  JOptionPane.showMessageDialog(dialog, "Nhà cung cấp không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                  return;
             }
             Date ngayDatUtil = dialog.dateChooserNgayDat.getDate();
             if (ngayDatUtil == null) {
                  JOptionPane.showMessageDialog(dialog, "Vui lòng chọn ngày đặt!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                  return;
             }

             phieuHienTai.setNhaCungCap(nccChon);
             phieuHienTai.setNgayDat(ngayDatUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
             phieuHienTai.setGhiChu(dialog.txtGhiChu.getText().trim());

             if (chiTietTamThoi.isEmpty()) {
                  JOptionPane.showMessageDialog(dialog, "Phiếu đặt hàng phải có ít nhất một chi tiết thuốc!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                  return;
             }
             phieuHienTai.setChiTietList(new ArrayList<>(chiTietTamThoi));

             phieuHienTai.setTongTien(tinhTongTienTamThoi());

             boolean success = false;

             boolean isUpdating = SocketClient.sendRequest(new Request(ActionType.GET_PHIEU_DAT_HANG_BY_MA, phieuHienTai.getMaPhieu())).getData() != null;

             if (isUpdating) {
                 success = (boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_PHIEU_DAT_HANG, phieuHienTai)).getData(); 
             } else {
                 success = (boolean) SocketClient.sendRequest(new Request(ActionType.ADD_PHIEU_DAT_HANG, phieuHienTai)).getData();
             }

             if (success) {
                 JOptionPane.showMessageDialog(dialog, (isUpdating ? "Cập nhật" : "Lưu") + " phiếu đặt hàng thành công!");
                 dialog.dispose();
                 loadDataToPhieuDatHangTable(); // Tải lại bảng chính
             } else {
                 JOptionPane.showMessageDialog(dialog, (isUpdating ? "Cập nhật" : "Lưu") + " phiếu đặt hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
             }

        } catch (Exception ex) {
             ex.printStackTrace();
             JOptionPane.showMessageDialog(dialog, "Lỗi khi lưu phiếu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // === CÁC HÀM KHÁC ===
    private void huyPhieuDatHang() {
        int selectedRow = trangChuGUI.table_DSPDDH.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn phiếu cần hủy.", "Chưa chọn phiếu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String maPhieu = trangChuGUI.table_DSPDDH.getValueAt(selectedRow, 0).toString();
        String tenNCC = trangChuGUI.table_DSPDDH.getValueAt(selectedRow, 2).toString();
        String trangThai = trangChuGUI.table_DSPDDH.getValueAt(selectedRow, 5).toString();

        if (trangThai.equalsIgnoreCase("Đã hủy")) {
            JOptionPane.showMessageDialog(trangChuGUI, "Phiếu này đã bị hủy trước đó.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(trangChuGUI,
            "Bạn có chắc muốn hủy phiếu '" + maPhieu + "' của nhà cung cấp '" + tenNCC + "' không?",
            "Xác nhận hủy phiếu",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
             boolean result = (boolean) SocketClient.sendRequest(new Request(ActionType.CANCEL_PHIEU_DAT_HANG, maPhieu)).getData();
             if (result) {
                 JOptionPane.showMessageDialog(trangChuGUI, "Hủy phiếu '" + maPhieu + "' thành công.");
                 loadDataToPhieuDatHangTable();
             } else {
                 JOptionPane.showMessageDialog(trangChuGUI, "Hủy phiếu thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             }
        }
    }

    private void lamMoiBoLocTimKiem() {
        trangChuGUI.txtMaPhieu_TKNCC.setText("");
        trangChuGUI.cboNCC_TKNCC.setSelectedIndex(0);
        trangChuGUI.cboTrangThai_TKNCC_PDH.setSelectedIndex(0);
        trangChuGUI.dateChooserTuNgay_TKNCC.setDate(null);
        trangChuGUI.dateChooserDenNgay_TKNCC.setDate(null);
        trangChuGUI.cboLoaiTongTien_TKNCC.setSelectedIndex(0);
        trangChuGUI.txtTongTien_TKNCC.setText("");
        loadDataToPhieuDatHangTable();
    }
    
    private void loadDataToPhieuDatHangTable() {
        try { 
            DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_DSPDDH.getModel();
            model.setRowCount(0);
            
            List<PhieuDatHang> dsPhieu = (List<PhieuDatHang>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_PHIEU_DAT_HANG, null)).getData(); 

            for (PhieuDatHang pdh : dsPhieu) {
                model.addRow(new Object[]{
                    pdh.getMaPhieu(),
                    (pdh.getNhaCungCap() != null) ? pdh.getNhaCungCap().getMaNhaCungCap() : "",
                    (pdh.getNhaCungCap() != null) ? pdh.getNhaCungCap().getTenNhaCungCap() : "",
                    (pdh.getNgayDat() != null) ? pdh.getNgayDat().toString() : "", 
                    currencyFormatter.format(pdh.getTongTien()),
                    pdh.getTrangThai()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(trangChuGUI, 
                "Lỗi nghiêm trọng khi tải dữ liệu Phiếu Đặt Hàng từ CSDL.\nChi tiết: " + e.getMessage(), 
                "Lỗi CSDL", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterPhieuDatHangTable() {
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_DSPDDH.getModel();
        model.setRowCount(0);
        
        List<PhieuDatHang> dsPhieu = (List<PhieuDatHang>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_PHIEU_DAT_HANG, null)).getData();

        String maPhieuFilter = trangChuGUI.txtMaPhieu_TKNCC.getText().trim().toLowerCase();
        String tenNccFilter = trangChuGUI.cboNCC_TKNCC.getSelectedItem().toString();
        String trangThaiFilter = trangChuGUI.cboTrangThai_TKNCC_PDH.getSelectedItem().toString();
        Date tuNgayUtil = trangChuGUI.dateChooserTuNgay_TKNCC.getDate();
        Date denNgayUtil = trangChuGUI.dateChooserDenNgay_TKNCC.getDate();
        String loaiTongTienFilter = trangChuGUI.cboLoaiTongTien_TKNCC.getSelectedItem().toString();
        String tongTienStr = trangChuGUI.txtTongTien_TKNCC.getText().trim();

        LocalDate tuNgay = (tuNgayUtil != null) ? tuNgayUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        LocalDate denNgay = (denNgayUtil != null) ? denNgayUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        Double tongTienFilter = null;
        try {
            if (!tongTienStr.isEmpty()) {
                tongTienFilter = Double.parseDouble(tongTienStr.replace(",", "").replace(" VNĐ", "")); 
            }
        } catch (NumberFormatException e) { }

        for (PhieuDatHang pdh : dsPhieu) {
            boolean match = true;

            if (match && !maPhieuFilter.isEmpty() && (pdh.getMaPhieu() == null || !pdh.getMaPhieu().toLowerCase().contains(maPhieuFilter))) {
                match = false;
            }

            if (match && !tenNccFilter.equals("Tất cả") && (pdh.getNhaCungCap() == null || !pdh.getNhaCungCap().getTenNhaCungCap().equals(tenNccFilter))) {
                match = false;
            }
            if (match && !trangThaiFilter.equals("Tất cả") && (pdh.getTrangThai() == null || !pdh.getTrangThai().equals(trangThaiFilter))) {
                match = false;
            }
            if (match && tuNgay != null && (pdh.getNgayDat() == null || pdh.getNgayDat().isBefore(tuNgay))) {
                match = false;
            }
             if (match && denNgay != null && (pdh.getNgayDat() == null || pdh.getNgayDat().isAfter(denNgay))) {
                match = false;
            }
            if (match && tongTienFilter != null) {
                 if (loaiTongTienFilter.equals("Bằng") && pdh.getTongTien() != tongTienFilter) match = false;
                 else if (loaiTongTienFilter.equals("Lớn hơn") && pdh.getTongTien() <= tongTienFilter) match = false;
                 else if (loaiTongTienFilter.equals("Nhỏ hơn") && pdh.getTongTien() >= tongTienFilter) match = false;
            }

            if (match) {
                 model.addRow(new Object[]{
                     pdh.getMaPhieu(),
                     (pdh.getNhaCungCap() != null) ? pdh.getNhaCungCap().getMaNhaCungCap() : "",
                     (pdh.getNhaCungCap() != null) ? pdh.getNhaCungCap().getTenNhaCungCap() : "",
                     (pdh.getNgayDat() != null) ? pdh.getNgayDat().toString() : "",
                     currencyFormatter.format(pdh.getTongTien()),
                     pdh.getTrangThai()
                 });
            }
        }
    }

    private void loadNccToFilterComboBox() {
        try {
             List<NhaCungCap> dsNCC = (List<NhaCungCap>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHA_CUNG_CAP, null)).getData();
             trangChuGUI.cboNCC_TKNCC.removeAllItems();
             trangChuGUI.cboNCC_TKNCC.addItem("Tất cả");
             for(NhaCungCap ncc : dsNCC) {
                 trangChuGUI.cboNCC_TKNCC.addItem(ncc.getTenNhaCungCap());
             }
        } catch (Exception e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(trangChuGUI, "Lỗi khi tải danh sách nhà cung cấp.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}