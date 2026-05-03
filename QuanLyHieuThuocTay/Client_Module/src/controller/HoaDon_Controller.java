package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.ParseException; // THÊM MỚI
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel; 
import javax.swing.JButton; // THÊM MỚI
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.io.File; // THÊM MỚI
import java.io.IOException;
import java.nio.file.Files; // THÊM MỚI
import java.nio.file.Paths; // THÊM MỚI

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.*;
import gui.*;
import utils.PdfExporter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;

public class HoaDon_Controller {
    private TrangChu_GUI view;
    private KhachHang currentKhachHang = null;
    private NhanVien currentNhanVien = null;
    private DecimalFormat df = new DecimalFormat("#,##0 VND");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private TableRowSorter<DefaultTableModel> sorterNVNgay;
    private TableRowSorter<DefaultTableModel> sorterNVThang;
    private TableRowSorter<DefaultTableModel> sorterNVNam;
    private DoiTra_Controller doiTraController;

    public void setDoiTraController(DoiTra_Controller doiTraController) {
        this.doiTraController = doiTraController;
    }

    public HoaDon_Controller(TrangChu_GUI view) {
        this.view = view;

        if (view.currentUser != null) {
            this.currentNhanVien = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA_TK, view.currentUser.getMaTK())).getData();
        }
        if (this.currentNhanVien == null) {
             this.currentNhanVien = new NhanVien();
             this.currentNhanVien.setMaNV("NV000"); 
             this.currentNhanVien.setTenNV(view.currentUserName != null ? view.currentUserName : "Admin");
        }

        loadKhuyenMaiToComboBox(); 
        clearHoaDonForm();
        loadThuocFilters_ThemHD();
        addThemHoaDonListeners();
        addTimKiemHoaDonListeners();
        loadTatCaHoaDon();
        loadCustomerPhonesToComboBox();
        initializeThongKeComponents();
        addThongKeListeners();
    }

    private void loadThuocFilters_ThemHD() {
        DefaultComboBoxModel<String> keModel = new DefaultComboBoxModel<>();
        keModel.addElement("Tất cả");
        List<KeThuoc> dsKe = (List<KeThuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_KE_THUOC, null)).getData();
        for(KeThuoc ke : dsKe) {
            keModel.addElement(ke.getLoaiKe());
        }
        view.cb_lockethuoc.setModel(keModel);

        DefaultComboBoxModel<String> tenModel = new DefaultComboBoxModel<>();
        tenModel.addElement("Tất cả");
        List<String> dsTen = (List<String>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_TEN_THUOC, null)).getData();
        for(String ten : dsTen) {
            tenModel.addElement(ten);
        }
        view.cb_loctenthuoc.setModel(tenModel);
        
        filterTimKiemThuocTable();
    }
        
    private void filterTimKiemThuocTable() {
        String maThuoc = view.txt_Nhapmathuoc.getText().trim();
        
        String tenThuoc = (view.cb_loctenthuoc.getSelectedItem() == null || "Tất cả".equals(view.cb_loctenthuoc.getSelectedItem())) 
                            ? "" : view.cb_loctenthuoc.getSelectedItem().toString();
        
        String loaiKe = (view.cb_lockethuoc.getSelectedItem() == null || "Tất cả".equals(view.cb_lockethuoc.getSelectedItem())) 
                            ? "" : view.cb_lockethuoc.getSelectedItem().toString();

        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.SEARCH_THUOC_FOR_SALE, new Object[]{maThuoc, tenThuoc, loaiKe})).getData();
        
        DefaultTableModel model = (DefaultTableModel) view.table_timkiemthuoc.getModel();
        model.setRowCount(0);
        if(dsThuoc == null) return;
        for (Thuoc t : dsThuoc) {
            model.addRow(new Object[]{
                t.getMaThuoc(),
                t.getKeThuoc() != null ? t.getKeThuoc().getLoaiKe() : "N/A",
                t.getTenThuoc(),
                df.format(t.getGiaBan()),
                t.getSoLuong()
            });
        }
    }

    public void loadKhuyenMaiToComboBox() {
        DefaultComboBoxModel<KhuyenMai> model = new DefaultComboBoxModel<>();
        
        KhuyenMai kmMacDinh = new KhuyenMai();
        kmMacDinh.setMaKM(null);
        kmMacDinh.setTenChuongTrinh("Không áp dụng");
        model.addElement(kmMacDinh);
        
        List<KhuyenMai> dsKM = (List<KhuyenMai>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_KHUYEN_MAI, null)).getData();
        for (KhuyenMai km : dsKM) {
            if (km.getTrangThai() == 1 && km.getNgayKetThuc().isAfter(LocalDate.now())) {
                model.addElement(km);
            }
        }
        view.cb_Chonkhuyenmai.setModel(model);
    }

    // PANEL THÊM HÓA ĐƠN==

    private void addThemHoaDonListeners() {
        view.btn_lammoitkthuoc.addActionListener(e -> {
            view.txt_Nhapmathuoc.setText("");
            view.cb_lockethuoc.setSelectedIndex(0);
            view.cb_loctenthuoc.setSelectedIndex(0);
            filterTimKiemThuocTable(); 
        });

        ItemListener filterListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                filterTimKiemThuocTable();
            }
        };
        view.cb_lockethuoc.addItemListener(filterListener);
        view.cb_loctenthuoc.addItemListener(filterListener);
        
        view.txt_Nhapmathuoc.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterTimKiemThuocTable(); }
            @Override public void removeUpdate(DocumentEvent e) { filterTimKiemThuocTable(); }
            @Override public void changedUpdate(DocumentEvent e) { filterTimKiemThuocTable(); }
        });

        view.btn_addthuocvaohoadon.addActionListener(e -> addThuocToCart());
        view.cb_Nhapsosdtkh.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem() instanceof String) {
                        findKhachHangBySDT();
                    }
                }
            }
        });
        view.btn_suaslthuoc.addActionListener(e -> suaSoLuongCart());
        view.btn_xoathuockhoihd.addActionListener(e -> xoaThuocFromCart());
        view.btn_Huyhoadon.addActionListener(e -> clearHoaDonForm());
        view.btn_Thanhtoanhoadon.addActionListener(e -> thanhToanHoaDon());

        view.text_Nhaptiennhan.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateTienThua(); }
            @Override public void removeUpdate(DocumentEvent e) { updateTienThua(); }
            @Override public void changedUpdate(DocumentEvent e) { updateTienThua(); }
        });
        
        view.btn_Themkhachhangmoi.addActionListener(e -> {
            ThemKH_GUI themKhDialog = new ThemKH_GUI(view.QuanLyHieuThuocTay);
            new ThemKH_Controller(themKhDialog, view);
            themKhDialog.setVisible(true);
        });
        
        view.btn_xemphieudatthuoc.addActionListener(e -> { 
            moDialogDanhSachCho(); 
        });
        
        view.btn_Themthuocvaophieudat.addActionListener(e -> {
            luuPhieuChoThanhToan();
        });
        
        view.cb_Chonkhuyenmai.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateTongTien(); 
                }
            }
        });
     // ===  XEM CHI TIẾT THUỐC ===
        view.btn_Xemchitiet.addActionListener(e -> {
            int selectedRow = view.table_timkiemthuoc.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay,
                        "Vui lòng chọn một thuốc từ bảng tìm kiếm để xem chi tiết!",
                        "Chưa chọn thuốc", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maThuoc = view.table_timkiemthuoc.getValueAt(selectedRow, 0).toString();
            
            Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData(); 

            if (thuoc == null) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay,
                        "Không tìm thấy thông tin chi tiết cho mã thuốc: " + maThuoc,
                        "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ChiTietThuoc_GUI chiTietDialog = new ChiTietThuoc_GUI(view.QuanLyHieuThuocTay);
            chiTietDialog.loadThuoc(thuoc);
            chiTietDialog.setVisible(true); 
        });
    }

    private void addThuocToCart() {
        int selectedRow = view.table_timkiemthuoc.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn một loại thuốc từ bảng tìm kiếm!");
            return;
        }

        String maThuoc = view.table_timkiemthuoc.getValueAt(selectedRow, 0).toString();
        Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData(); 
        if (thuoc == null) return;

        int soLuongNhap;
        try {
            soLuongNhap = Integer.parseInt(view.text_Nhapsoluongthuoc.getText());
            if (soLuongNhap <= 0) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Số lượng phải là số dương!");
                return;
            }
            if (soLuongNhap > thuoc.getSoLuong()) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Số lượng tồn kho không đủ (Chỉ còn " + thuoc.getSoLuong() + ")!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng nhập số lượng hợp lệ!");
            return;
        }

        DefaultTableModel cartModel = (DefaultTableModel) view.table_hdtam.getModel();

        for (int i = 0; i < cartModel.getRowCount(); i++) {
            Object maThuocTrongGio = cartModel.getValueAt(i, 0);
            
            if (maThuocTrongGio != null && maThuocTrongGio.equals(maThuoc)) { 
                int soLuongHienTai = (int) cartModel.getValueAt(i, 2);
                int soLuongMoi = soLuongHienTai + soLuongNhap;
                if (soLuongMoi > thuoc.getSoLuong()) {
                     JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Tổng số lượng vượt quá tồn kho (Chỉ còn " + thuoc.getSoLuong() + ")!");
                     return;
                }
                cartModel.setValueAt(soLuongMoi, i, 2); 
                cartModel.setValueAt(soLuongMoi * thuoc.getGiaBan(), i, 4); 
                updateTongTien();
                view.text_Nhapsoluongthuoc.setText(""); 
                return;
            }
        }

        Object[] rowData = {
            thuoc.getMaThuoc(),
            thuoc.getTenThuoc(),
            soLuongNhap,
            thuoc.getGiaBan(),
            soLuongNhap * thuoc.getGiaBan()
        };
        cartModel.addRow(rowData);
        updateTongTien();
        view.text_Nhapsoluongthuoc.setText("");
    }

    private void suaSoLuongCart() {
         int selectedRow = view.table_hdtam.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn một thuốc trong hóa đơn để sửa!");
            return;
        }
        String maThuoc = view.table_hdtam.getValueAt(selectedRow, 0).toString();
        Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
        
        String soLuongMoiStr = JOptionPane.showInputDialog(view.QuanLyHieuThuocTay, "Nhập số lượng mới:", view.table_hdtam.getValueAt(selectedRow, 2));
        if (soLuongMoiStr == null) return;

        try {
            int soLuongMoi = Integer.parseInt(soLuongMoiStr);
            if (soLuongMoi <= 0) {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Số lượng phải là số dương!");
                 return;
            }
            if (soLuongMoi > thuoc.getSoLuong()) {
                JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Số lượng tồn kho không đủ (Chỉ còn " + thuoc.getSoLuong() + ")!");
                return;
            }
            DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
            double giaBan = (double) model.getValueAt(selectedRow, 3);
            model.setValueAt(soLuongMoi, selectedRow, 2);
            model.setValueAt(soLuongMoi * giaBan, selectedRow, 4);
            updateTongTien();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Số lượng nhập không hợp lệ!");
        }
    }

    private void xoaThuocFromCart() {
        int selectedRow = view.table_hdtam.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn một thuốc trong hóa đơn để xóa!");
            return;
        }
        DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
        model.removeRow(selectedRow);
        updateTongTien();
    }

    private void findKhachHangBySDT() {
        Component editorComponent = view.cb_Nhapsosdtkh.getEditor().getEditorComponent(); 

        String sdt = "";
        if (editorComponent instanceof JTextField) {
            sdt = ((JTextField) editorComponent).getText().trim();
        } else {
            Object selected = view.cb_Nhapsosdtkh.getSelectedItem(); 
            if (selected != null) {
                sdt = selected.toString().trim();
            }
        }
        if (sdt.isEmpty()) {
            view.lbl_Hientenkh.setText("Khách vãng lai");
            this.currentKhachHang = null;
            return;
        }
        KhachHang kh = (KhachHang) SocketClient.sendRequest(new Request(ActionType.GET_KHACH_HANG_BY_SDT, sdt)).getData();
        if (kh != null && kh.isTrangThai()) {
            view.lbl_Hientenkh.setText(kh.getTenKH());
            this.currentKhachHang = kh;
        } else { 
            List<KhachHang> dsKH = (List<KhachHang>) SocketClient.sendRequest(new Request(ActionType.SEARCH_KHACH_HANG, new Object[]{"", "", sdt, "", null})).getData(); 
            KhachHang foundActive = null;
            if (dsKH != null) {
                for (KhachHang k : dsKH) {
                    if (k.isTrangThai()) {
                        foundActive = k;
                        break;
                    }
                }
            }
            
            if (foundActive != null) { 
                view.lbl_Hientenkh.setText(foundActive.getTenKH());
                 if (editorComponent instanceof JTextField) {
                     ((JTextField) editorComponent).setText(foundActive.getSoDienThoai());
                 } else {
                     view.cb_Nhapsosdtkh.setSelectedItem(foundActive.getSoDienThoai()); 
                 }
                this.currentKhachHang = foundActive;
            } else { 
                view.lbl_Hientenkh.setText("Khách vãng lai"); 
                this.currentKhachHang = null;
            }
        }
    }
    
    private double parseCurrency(String amountString) {
        try {
            String cleanString = amountString.replace("VND", "").replace(",", "").replace(".", "").trim();
            if (cleanString.isEmpty()) {
                return 0.0;
            }
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            System.err.println("Lỗi parse tiền tệ: " + amountString);
            return 0.0;
        }
    }


    private void updateTongTien() {
        DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
        double tongTienHang = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            if(model.getValueAt(i, 4) != null) { 
                 tongTienHang += (double) model.getValueAt(i, 4);
            }
        }
        
        Thue thueObj = (Thue) SocketClient.sendRequest(new Request(ActionType.GET_THUE_BY_MA, "TH002")).getData();
        double tiLeThue = (thueObj != null) ? thueObj.getTiLe() : 0.10;
        
        KhuyenMai selectedKM = (KhuyenMai) view.cb_Chonkhuyenmai.getSelectedItem();
        double tiLeKM = 0.0;
        if(selectedKM != null && selectedKM.getMaKM() != null) {
            tiLeKM = selectedKM.getGiaTri() / 100.0;
        }

        double tienKM = tongTienHang * tiLeKM;
        double tongSauKM = tongTienHang - tienKM;
        double thue = tongSauKM * tiLeThue;
        double tongCong = tongSauKM + thue;

        view.lbl_Hientienhang.setText(df.format(tongTienHang));
        view.lbl_Hienthue.setText(df.format(thue)); 
        view.lbl_Hientongcong.setText(df.format(tongCong));
        
        updateTienThua();
    }

    private void updateTienThua() {
         try {
            double tongCong = parseCurrency(view.lbl_Hientongcong.getText());
            
            String tienNhanStr = view.text_Nhaptiennhan.getText().replaceAll("[^\\d]", "");
            if (tienNhanStr.isEmpty()) tienNhanStr = "0";
            
            double tienNhan = Double.parseDouble(tienNhanStr);
            
            if (tienNhan >= tongCong) {
                double tienThua = tienNhan - tongCong;
                view.lbl_Hientienthua.setText(df.format(tienThua));
            } else {
                view.lbl_Hientienthua.setText("Chưa đủ tiền!");
            }
        } catch (Exception e) {
            view.lbl_Hientienthua.setText("0 VND");
        }
    }
    
    private void clearHoaDonForm() {
        view.lbl_hienmahd.setText((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_HOA_DON, null)).getData());
        view.lbl_hienngaylaphoadon.setText(sdf.format(new Date()));
        if(currentNhanVien != null) {
             view.lbl_Hientennv.setText(currentNhanVien.getTenNV());
        }
        Component editorComponent = view.cb_Nhapsosdtkh.getEditor().getEditorComponent(); 
        if (editorComponent instanceof JTextField) {
            ((JTextField) editorComponent).setText("");
        } else {
            view.cb_Nhapsosdtkh.setSelectedItem(null); 
        }
        view.lbl_Hientenkh.setText("Khách vãng lai");
        this.currentKhachHang = null;
        DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
        model.setRowCount(0);
        
        view.lbl_Hientienhang.setText("0 VND");
        view.lbl_Hienthue.setText("0 VND");
        view.lbl_Hientongcong.setText("0 VND");
        view.text_Nhaptiennhan.setText("");
        view.lbl_Hientienthua.setText("0 VND");
        view.cb_Chonkhuyenmai.setSelectedIndex(0); 
    }
    
    private void thanhToanHoaDon() {
        DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
        if (model.getRowCount() == 0) {
             JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Hóa đơn đang trống, không thể thanh toán!", "Lỗi", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String maHD = view.lbl_hienmahd.getText();
        LocalDate ngayLap = LocalDate.now();
        KhuyenMai selectedKM = (KhuyenMai) view.cb_Chonkhuyenmai.getSelectedItem();
        Thue thue = (Thue) SocketClient.sendRequest(new Request(ActionType.GET_THUE_BY_MA, "TH002")).getData(); 

        double tongCong, tienNhan, tienThua; 
        try {
            tongCong = parseCurrency(view.lbl_Hientongcong.getText());

            String tienNhanStr = view.text_Nhaptiennhan.getText().replaceAll("[^\\d]", "");
            if (tienNhanStr.isEmpty()) {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng nhập số tiền khách đưa!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                 view.text_Nhaptiennhan.requestFocus(); 
                 return;
            }
            tienNhan = Double.parseDouble(tienNhanStr);

            if (tienNhan < tongCong) {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Chưa nhận đủ tiền!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            tienThua = tienNhan - tongCong;

        } catch (Exception e) {
             JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Số tiền nhập không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        HoaDon hd = new HoaDon(maHD, ngayLap, this.currentNhanVien, this.currentKhachHang, thue);
        if(selectedKM != null && selectedKM.getMaKM() != null) {
            hd.setKhuyenMai(selectedKM);
        }
        hd.setTienKhachDua(tienNhan);
        hd.setTienThua(tienThua);

        ArrayList<ChiTietHoaDon> dsCTHD = new ArrayList<>(); 

        for (int i = 0; i < model.getRowCount(); i++) {
            // 1. Lấy thông tin từ bảng
            String maThuoc = model.getValueAt(i, 0).toString();
            int soLuong = (int) model.getValueAt(i, 2);
            
            // 2. Lấy đơn giá (Cột 3). Chuyển đổi an toàn sang double
            double donGia = Double.parseDouble(model.getValueAt(i, 3).toString());

            // 3. Tìm đối tượng thuốc
            Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
            if (thuoc == null) {
                 JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Lỗi: Không tìm thấy thuốc " + maThuoc, "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                 return; 
            }
            
            // 4. Tạo chi tiết hóa đơn với ĐẦY ĐỦ 4 THAM SỐ (đã bao gồm đơn giá)
            ChiTietHoaDon ct = new ChiTietHoaDon(hd, thuoc, soLuong, donGia);
            
            dsCTHD.add(ct);
        }

        hd.setDanhSachChiTiet(dsCTHD);
        
        try {
            if (!(boolean) SocketClient.sendRequest(new Request(ActionType.ADD_HOA_DON, hd)).getData()) {
                throw new Exception("Lỗi khi lưu hóa đơn chính!");
            }

            for (ChiTietHoaDon ct : dsCTHD) {
                // Note: Server-side ADD_HOA_DON should handle details automatically or we need ADD_CT_HOA_DON
                // But let's follow the original logic if needed
                if (!(boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_THUOC_QUANTITY, new Object[]{ct.getThuoc().getMaThuoc(), ct.getSoLuong()})).getData()) {
                    throw new Exception("Lỗi khi cập nhật tồn kho cho " + ct.getThuoc().getMaThuoc());
                }
            }

            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Thanh toán thành công! Mã Hóa Đơn: " + maHD);

            if (view.chk_XuatHoaDon.isSelected()) {
                try {
                    Files.createDirectories(Paths.get("hoa_don_pdf")); 
                } catch (IOException ioException) {
                    System.err.println("Không thể tạo thư mục lưu PDF: " + ioException.getMessage());
                }

                String filePath = "hoa_don_pdf/" + hd.getMaHD() + ".pdf"; 
                PdfExporter.exportHoaDonToPdf(hd, dsCTHD, filePath);
            }

            clearHoaDonForm();
            filterTimKiemThuocTable();
            loadTatCaHoaDon(); 
            if (doiTraController != null) {
                doiTraController.lamMoiBangHoaDonTraThuoc();
            }
            if (doiTraController != null) {
                doiTraController.lamMoiBangHoaDonDoiThuoc();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Thanh toán thất bại: \n" + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ==PHIẾU CHỜ THANH TOÁN==
	/**
     * THÊM MỚI: Lấy thông tin từ form, lưu vào 2 bảng PhieuChoThanhToan và ChiTietPhieuCho
     */
    private void luuPhieuChoThanhToan() {
        DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
        if (model.getRowCount() == 0) {
             JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Hóa đơn đang trống, không thể lưu phiếu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
             return;
        }
        Component editorComponent_luu = view.cb_Nhapsosdtkh.getEditor().getEditorComponent(); // Thay cb_Nhapsosdtkh
        String sdt = "";
        if (editorComponent_luu instanceof JTextField) {
            sdt = ((JTextField) editorComponent_luu).getText().trim();
        } else {
            Object selected = view.cb_Nhapsosdtkh.getSelectedItem(); // Thay cb_Nhapsosdtkh
            if (selected != null) {
                sdt = selected.toString().trim();
            }
        }
        String maPhieuCho = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_PHIEU_CHO, null)).getData();
        String tenKH = view.lbl_Hientenkh.getText();
        LocalDate ngayLap = LocalDate.now();
        double tongTienHang = parseCurrency(view.lbl_Hientienhang.getText());
        double thueVAT = parseCurrency(view.lbl_Hienthue.getText());
        double tongCong = parseCurrency(view.lbl_Hientongcong.getText());
        
        PhieuChoThanhToan phieuCho = new PhieuChoThanhToan(
            maPhieuCho,
            this.currentKhachHang, 
            tenKH,
            sdt,
            this.currentNhanVien,
            ngayLap,
            tongTienHang,
            thueVAT,
            tongCong
        );

        List<ChiTietPhieuCho> dsChiTiet = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String maThuoc = model.getValueAt(i, 0).toString();
            int soLuong = (int) model.getValueAt(i, 2);
            double donGia = (double) model.getValueAt(i, 3);
            
            Thuoc thuoc = new Thuoc(maThuoc); 
            ChiTietPhieuCho ctpc = new ChiTietPhieuCho(phieuCho, thuoc, soLuong, donGia);
            dsChiTiet.add(ctpc);
        }

        phieuCho.setDsChiTietPhieuCho(dsChiTiet);
        
        try {
            if (!(boolean) SocketClient.sendRequest(new Request(ActionType.ADD_PHIEU_CHO, phieuCho)).getData()) {
                throw new Exception("Lỗi khi lưu thông tin phiếu chờ!");
            }
            // Note: Server-side ADD_PHIEU_CHO should handle details automatically

            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Đã thêm vào danh sách chờ thành công!\nMã phiếu chờ: " + maPhieuCho);
            clearHoaDonForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Lưu phiếu chờ thất bại: \n" + e.getMessage(), "Lỗi Database", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * THÊM MỚI: Mở Dialog DsPhieuDatThuoc_GUI và gán sự kiện cho nó
     */
    private void moDialogDanhSachCho() {
        DsPhieuDatThuoc_GUI dialog = new DsPhieuDatThuoc_GUI(view.QuanLyHieuThuocTay);
        
        JTable table = dialog.getTable();
        JButton btnThanhToan = dialog.getBtnThanhToan();
        JButton btnXoa = dialog.getBtnXoa();
        JButton btnXoaTatCa = dialog.getBtnXoaTatCa();
        JTextField txtTimKiem = dialog.getTextFieldSearch();
        JButton btnTimKiem = dialog.getBtnTimKiem();

        loadDanhSachChoLenBang(table);

        btnThanhToan.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn một phiếu để tải lại!");
                return;
            }
            String maPhieuCho = table.getValueAt(selectedRow, 0).toString();
            
            taiPhieuChoLenForm(maPhieuCho);
            
            dialog.dispose();
        });
        
        btnXoa.addActionListener(e -> {
             int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng chọn một phiếu để xóa!");
                return;
            }
            
            String maPhieuCho = table.getValueAt(selectedRow, 0).toString();
            String tenKH = table.getValueAt(selectedRow, 2).toString();
            
            int confirm = JOptionPane.showConfirmDialog(dialog, 
                "Đại Ca Mạnh có chắc muốn xóa phiếu chờ:\n" + maPhieuCho + "\nCủa khách: " + tenKH + "?", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                if ((boolean) SocketClient.sendRequest(new Request(ActionType.DELETE_PHIEU_CHO, maPhieuCho)).getData()) {
                    JOptionPane.showMessageDialog(dialog, "Xóa thành công phiếu " + maPhieuCho + "!");
                    loadDanhSachChoLenBang(table); 
                } else {
                    JOptionPane.showMessageDialog(dialog, "Xóa thất bại!");
                }
            }
        });
        
        btnXoaTatCa.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog, "Đại Ca Mạnh có chắc muốn xóa TẤT CẢ phiếu chờ không?\nHành động này không thể hoàn tác.", "XÁC NHẬN HỦY DIỆT", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                 if ((boolean) SocketClient.sendRequest(new Request(ActionType.DELETE_ALL_PHIEU_CHO, null)).getData()) {
                    JOptionPane.showMessageDialog(dialog, "Đã xóa tất cả phiếu chờ!");
                    loadDanhSachChoLenBang(table); 
                 } else {
                    JOptionPane.showMessageDialog(dialog, "Xóa thất bại!");
                 }
            }
        });
        
        ActionListener timKiemAction = e -> {
            String sdtTim = txtTimKiem.getText().trim().toLowerCase();
            loadDanhSachChoLenBang(table, sdtTim);
        };
        btnTimKiem.addActionListener(timKiemAction);
        txtTimKiem.addActionListener(timKiemAction); 

        dialog.setVisible(true);
    }
    
    /**
     * THÊM MỚI: Tải danh sách chờ (đã lọc) lên bảng trong Dialog
     */
    private void loadDanhSachChoLenBang(JTable table, String sdtFilter) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        List<PhieuChoThanhToan> dsCho = (List<PhieuChoThanhToan>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_PHIEU_CHO, null)).getData();
        
        for (PhieuChoThanhToan pc : dsCho) {
            String maKH = (pc.getKhachHang() != null) ? pc.getKhachHang().getMaKH() : "N/A";
            
            if (sdtFilter == null || pc.getSdtKH().toLowerCase().contains(sdtFilter)) {
                 model.addRow(new Object[]{
                    pc.getMaPhieuCho(),
                    maKH, 
                    pc.getTenKH(), 
                    pc.getSdtKH() 
                });
            }
        }
    }
    private void loadDanhSachChoLenBang(JTable table) {
        loadDanhSachChoLenBang(table, null);
    }
    
    /**
     * THÊM MỚI: Tải 1 phiếu chờ (theo MÃ PHIẾU CHỜ) lên lại màn hình chính và xóa phiếu đó
     */
    private void taiPhieuChoLenForm(String maPhieuCho) {
        PhieuChoThanhToan pc = (PhieuChoThanhToan) SocketClient.sendRequest(new Request(ActionType.GET_PHIEU_CHO_BY_MA, maPhieuCho)).getData();
        if (pc == null) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Không tìm thấy phiếu chờ: " + maPhieuCho, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        List<ChiTietPhieuCho> dsChiTiet = (List<ChiTietPhieuCho>) SocketClient.sendRequest(new Request(ActionType.GET_DETAILS_BY_PHIEU_CHO, pc.getMaPhieuCho())).getData();
        
        clearHoaDonForm();
        
        Component editorComponent_tai = view.cb_Nhapsosdtkh.getEditor().getEditorComponent(); 
        if (editorComponent_tai instanceof JTextField) {
            ((JTextField) editorComponent_tai).setText(pc.getSdtKH());
        } else {
            view.cb_Nhapsosdtkh.setSelectedItem(pc.getSdtKH()); 
        }
        view.lbl_Hientenkh.setText(pc.getTenKH());
        if(pc.getKhachHang() != null) {
            this.currentKhachHang = (KhachHang) SocketClient.sendRequest(new Request(ActionType.GET_KHACH_HANG_BY_MA, pc.getKhachHang().getMaKH())).getData();
        } else {
            this.currentKhachHang = null;
        }
        
        view.lbl_Hientienhang.setText(df.format(pc.getTongTienHang()));
        view.lbl_Hienthue.setText(df.format(pc.getThueVAT()));
        view.lbl_Hientongcong.setText(df.format(pc.getTongCong()));
        
        DefaultTableModel model = (DefaultTableModel) view.table_hdtam.getModel();
        model.setRowCount(0); 
        
        for (ChiTietPhieuCho ct : dsChiTiet) {
            model.addRow(new Object[]{
                ct.getThuoc().getMaThuoc(),
                ct.getThuoc().getTenThuoc(),
                ct.getSoLuong(),
                ct.getDonGia(),
                ct.getThanhTien()
            });
        }
        
        if (!(boolean) SocketClient.sendRequest(new Request(ActionType.DELETE_PHIEU_CHO, pc.getMaPhieuCho())).getData()) {
             JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Đã tải lại phiếu, nhưng XÓA phiếu chờ thất bại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }


    // == TÌM KIẾM HÓA ĐƠN (pn_tkhd) ==

    private void addTimKiemHoaDonListeners() {
        view.btn_tkhd_lammoi.addActionListener(e -> clearHoaDonFilter());
        view.btn_tkhd_xemchitiet.addActionListener(e -> xemChiTietHoaDonDaTim()); 

        DocumentListener filterListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { timKiemHoaDon(); }
            @Override public void removeUpdate(DocumentEvent e) { timKiemHoaDon(); }
            @Override public void changedUpdate(DocumentEvent e) { timKiemHoaDon(); }
        };
        view.text_tkhd_Mahd.getDocument().addDocumentListener(filterListener);
        view.text_tkhd_TenKH.getDocument().addDocumentListener(filterListener);
        view.text_tkhd_sdtkh.getDocument().addDocumentListener(filterListener);
        view.text_tkhd_tennv.getDocument().addDocumentListener(filterListener);
        view.text_tkhd_sdtnv.getDocument().addDocumentListener(filterListener);
        view.date_tkhd_ngaylaphd.getDateEditor().addPropertyChangeListener("date", e -> timKiemHoaDon());
    }

    private void clearHoaDonFilter() {
        view.text_tkhd_Mahd.setText("");
        view.text_tkhd_TenKH.setText("");
        view.text_tkhd_sdtkh.setText("");
        view.text_tkhd_tennv.setText("");
        view.text_tkhd_sdtnv.setText("");
        view.date_tkhd_ngaylaphd.setDate(null);
        loadTatCaHoaDon();
    }

    private void loadTatCaHoaDon() {
        DefaultTableModel model = (DefaultTableModel) view.table_tkhd.getModel();
        model.setRowCount(0);
        List<Object[]> dsHD = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_HOA_DON_CHI_TIET_FOR_TABLE, null)).getData(); 
        if(dsHD == null) return;
        for (Object[] row : dsHD) {
             row[7] = df.format(row[7]);
             model.addRow(row);
        }
    }

    private void timKiemHoaDon() {
        String maHD = view.text_tkhd_Mahd.getText().trim();
        String tenKH = view.text_tkhd_TenKH.getText().trim();
        String sdtKH = view.text_tkhd_sdtkh.getText().trim();
        String tenNV = view.text_tkhd_tennv.getText().trim();
        String sdtNV = view.text_tkhd_sdtnv.getText().trim();
        Date ngayLap = view.date_tkhd_ngaylaphd.getDate();
        LocalDate ngayLapLocal = (ngayLap != null) ? ngayLap.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

        DefaultTableModel model = (DefaultTableModel) view.table_tkhd.getModel();
        model.setRowCount(0);
        List<Object[]> dsHD = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.SEARCH_HOA_DON_CHI_TIET, new Object[]{maHD, tenKH, sdtKH, tenNV, sdtNV, ngayLapLocal != null ? ngayLapLocal.toString() : null})).getData(); 
        if(dsHD == null) return;
        for (Object[] row : dsHD) {
             row[7] = df.format(row[7]); 
            model.addRow(row);
        }
    }

    private void xemChiTietHoaDonDaTim() {
        int selectedRow = view.table_tkhd.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn một hóa đơn từ bảng để xem chi tiết!");
            return;
        }
        String maHD = view.table_tkhd.getValueAt(selectedRow, 0).toString();
        
        XemchitietHD_GUI dialog = new XemchitietHD_GUI(view.QuanLyHieuThuocTay);
        dialog.loadData(maHD); 
        dialog.setVisible(true);
    }
    public void loadCustomerPhonesToComboBox() {
        List<KhachHang> dsKH = (List<KhachHang>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_ACTIVE_KHACH_HANG, null)).getData(); 

        DefaultComboBoxModel<String> phoneModel = new DefaultComboBoxModel<>();

        if (dsKH != null) {
            for (KhachHang kh : dsKH) {
                if (kh.getSoDienThoai() != null && !kh.getSoDienThoai().isEmpty()) {
                    phoneModel.addElement(kh.getSoDienThoai());
                }
            }
        }
        view.cb_Nhapsosdtkh.setModel(phoneModel);
        view.cb_Nhapsosdtkh.setSelectedItem(null);
        AutoCompleteDecorator.decorate(view.cb_Nhapsosdtkh);
    }
    private void initializeThongKeComponents() {
        view.date_tktn_ngay.setDate(new Date());
        view.cb_tktn_tktnv_boloc.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả nhân viên", "Theo nhân viên cụ thể"}));
        loadNhanVienToTable(view.table_8);
        setupNhanVienFilter(view.text_tktn_tktnv_boloc_manv, view.text_tktn_tktnv_boloc_tennv, view.table_8);
        setNhanVienFilterControlsVisible(view.pn_tktn_tktnv_boloc_pntk, view.table_8, false); // Tắt filter và xóa bảng NV ban đầu

        view.month_tktt.setMonth(LocalDate.now().getMonthValue() - 1);
        view.year_tktt.setYear(LocalDate.now().getYear());
        view.cb_tktt_tknv.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả nhân viên", "Theo nhân viên cụ thể"}));
        loadNhanVienToTable(view.table_ttkt);
        setupNhanVienFilter(view.text_tktt_tk_boloc_manv, view.text_tktt_tk_boloc_tennv, view.table_ttkt);
        setNhanVienFilterControlsVisible(view.pn_tktt_tk_boloc_tknv, view.table_ttkt, false);

        view.year_tktn.setYear(LocalDate.now().getYear());
        view.cb_chonnamtk.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả nhân viên", "Theo nhân viên cụ thể"}));
        loadNhanVienToTable(view.table_tktn_hiennv);
        setupNhanVienFilter(view.text_tktnam_locnv_manv, view.text_tktnam_locnv_tennv, view.table_tktn_hiennv);
        setNhanVienFilterControlsVisible(view.pn_tktnam_locnv, view.table_tktn_hiennv, false);

        updateThongKeTheoNgay();
        updateThongKeTheoThang();
        updateThongKeTheoNam();
    }

    /**
     * THÊM MỚI: Gán các listener cho các thành phần trong panel thống kê.
     */
    private void addThongKeListeners() {
        view.date_tktn_ngay.addPropertyChangeListener("date", (PropertyChangeEvent evt) -> {
            updateThongKeTheoNgay();
        });
        view.table_8.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.cb_tktn_tktnv_boloc.getSelectedIndex() == 1) {
                     updateThongKeTheoNgay();
                }
            }
        });
        view.cb_tktn_tktnv_boloc.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                boolean enableFilter = (view.cb_tktn_tktnv_boloc.getSelectedIndex() == 1);
                setNhanVienFilterControlsVisible(view.pn_tktn_tktnv_boloc_pntk, view.table_8, enableFilter);
                if (!enableFilter) {
                    clearNhanVienSelection(view.table_8);
                    updateThongKeTheoNgay();
                } else {
                     ((DefaultTableModel) view.table_tktn.getModel()).setRowCount(0);
                     view.lbl_tktn_hientongsohd.setText("0");
                     view.lbl_tktn_hientongsotien.setText("0 VND");
                     view.table_8.clearSelection();
                }
            }
        });

        PropertyChangeListener monthYearListener = (PropertyChangeEvent evt) -> {
            updateThongKeTheoThang();
        };
        view.month_tktt.addPropertyChangeListener("month", monthYearListener);
        view.year_tktt.addPropertyChangeListener("year", monthYearListener);
        view.table_ttkt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                 if (view.cb_tktt_tknv.getSelectedIndex() == 1) {
                    updateThongKeTheoThang();
                 }
            }
        });
        view.btn_tktt_xemchitiet.addActionListener(e -> xemChiTietThongKeThang());
        view.cb_tktt_tknv.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                 boolean enableFilter = (view.cb_tktt_tknv.getSelectedIndex() == 1);
                 setNhanVienFilterControlsVisible(view.pn_tktt_tk_boloc_tknv, view.table_ttkt, enableFilter);
                 if (!enableFilter) {
                    clearNhanVienSelection(view.table_ttkt);
                    updateThongKeTheoThang();
                 } else {
                     ((DefaultTableModel) view.table_hienhd_tktt.getModel()).setRowCount(0);
                     view.lbl_tktt_hiensohd.setText("0");
                     view.lbl_tktt_hientongtienhd.setText("0 VND");
                     view.table_ttkt.clearSelection();
                 }
            }
        });
        view.year_tktn.addPropertyChangeListener("year", (PropertyChangeEvent evt) -> {
            updateThongKeTheoNam();
        });
        view.table_tktn_hiennv.addMouseListener(new MouseAdapter() {
             @Override
            public void mouseClicked(MouseEvent e) {
                 if (view.cb_chonnamtk.getSelectedIndex() == 1) {
                    updateThongKeTheoNam();
                 }
            }
        });
        view.btn_tktnam_xemchitiet.addActionListener(e -> xemChiTietThongKeNam());
        view.cb_chonnamtk.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                 boolean enableFilter = (view.cb_chonnamtk.getSelectedIndex() == 1);
                 setNhanVienFilterControlsVisible(view.pn_tktnam_locnv, view.table_tktn_hiennv, enableFilter);
                 if (!enableFilter) {
                    clearNhanVienSelection(view.table_tktn_hiennv);
                    updateThongKeTheoNam();
                 } else {
                     ((DefaultTableModel) view.table_11.getModel()).setRowCount(0);
                     view.lbl_tktn_hientshd.setText("0");
                     view.lbl_tktnam_hientongsotien.setText("0 VND");
                     view.table_tktn_hiennv.clearSelection();
                 }
            }
        });
    }


    private void updateThongKeTheoNgay() {
        Date selectedDate = view.date_tktn_ngay.getDate();
        if (selectedDate == null) {
             ((DefaultTableModel) view.table_tktn.getModel()).setRowCount(0);
             view.lbl_tktn_hientongsohd.setText("0");
             view.lbl_tktn_hientongsotien.setText("0 VND");
            return;
        }
        LocalDate ngay = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String maNVSelected = null;
        if (view.cb_tktn_tktnv_boloc.getSelectedIndex() == 1) { 
             maNVSelected = getSelectedMaNV(view.table_8);
             if (maNVSelected == null) {
                 ((DefaultTableModel) view.table_tktn.getModel()).setRowCount(0);
                 view.lbl_tktn_hientongsohd.setText("0");
                 view.lbl_tktn_hientongsotien.setText("0 VND");
                 return;
             }
        }

        DefaultTableModel modelNgay = (DefaultTableModel) view.table_tktn.getModel();
        modelNgay.setRowCount(0);
        List<Object[]> dsHDNgay = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_HOA_DON_CHI_TIET_TRONG_NGAY, new Object[]{ngay, maNVSelected})).getData(); 
        for (Object[] row : dsHDNgay) {
             if (row[5] instanceof Number) {
                 row[5] = df.format(((Number) row[5]).doubleValue());
             }
            modelNgay.addRow(row);
        }
        Map<String, Object> tongKetNgay = (Map<String, Object>) SocketClient.sendRequest(new Request(ActionType.GET_TONG_KET_TRONG_NGAY, new Object[]{ngay, maNVSelected})).getData(); 
        view.lbl_tktn_hientongsohd.setText(String.valueOf(tongKetNgay.getOrDefault("tongSoHD", 0)));
        view.lbl_tktn_hientongsotien.setText(df.format(tongKetNgay.getOrDefault("tongTien", 0.0)));
    }

    private void updateThongKeTheoThang() {
        int thang = view.month_tktt.getMonth() + 1;
        int nam = view.year_tktt.getYear();
        view.lbl_hienthangvanam.setText(String.format("Tháng %d/%d", thang, nam));

        String maNVSelected = null;
        if (view.cb_tktt_tknv.getSelectedIndex() == 1) {
            maNVSelected = getSelectedMaNV(view.table_ttkt);
             if (maNVSelected == null) {
                 ((DefaultTableModel) view.table_hienhd_tktt.getModel()).setRowCount(0);
                 view.lbl_tktt_hiensohd.setText("0");
                 view.lbl_tktt_hientongtienhd.setText("0 VND");
                 veBieuDoThang(new ArrayList<>()); 
                 return;
             }
        }

        List<Object[]> dsThongKeNgay = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_THONG_KE_THEO_NGAY_TRONG_THANG, new Object[]{thang, nam, maNVSelected})).getData();

        veBieuDoThang(dsThongKeNgay);

        DefaultTableModel modelThang = (DefaultTableModel) view.table_hienhd_tktt.getModel();
        modelThang.setRowCount(0);
        for (Object[] row : dsThongKeNgay) {
             if (row[2] instanceof Number) {
                 row[2] = df.format(((Number) row[2]).doubleValue());
             }
            modelThang.addRow(row); 
        }

        Map<String, Object> tongKetThang = (Map<String, Object>) SocketClient.sendRequest(new Request(ActionType.GET_TONG_KET_TRONG_THANG, new Object[]{thang, nam, maNVSelected})).getData();
        view.lbl_tktt_hiensohd.setText(String.valueOf(tongKetThang.getOrDefault("tongSoHD", 0)));
        view.lbl_tktt_hientongtienhd.setText(df.format(tongKetThang.getOrDefault("tongTien", 0.0)));
    }


    private void updateThongKeTheoNam() {
        int nam = view.year_tktn.getYear();
        view.lbl_tktn_hiennam.setText(String.valueOf(nam));

        String maNVSelected = null;
        if (view.cb_chonnamtk.getSelectedIndex() == 1) {
            maNVSelected = getSelectedMaNV(view.table_tktn_hiennv);
             if (maNVSelected == null) {
                 ((DefaultTableModel) view.table_11.getModel()).setRowCount(0);
                 view.lbl_tktn_hientshd.setText("0");
                 view.lbl_tktnam_hientongsotien.setText("0 VND");
                 veBieuDoNam(new ArrayList<>()); 
                 return;
             }
        }

        List<Object[]> dsThongKeThang = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_THONG_KE_THEO_THANG_TRONG_NAM, new Object[]{nam, maNVSelected})).getData();
        veBieuDoNam(dsThongKeThang);

        DefaultTableModel modelNam = (DefaultTableModel) view.table_11.getModel();
        modelNam.setRowCount(0);
        for (Object[] row : dsThongKeThang) {
            row[0] = "Tháng " + row[0]; 
            if (row[2] instanceof Number) {
                row[2] = df.format(((Number) row[2]).doubleValue());
            }
            modelNam.addRow(row); 
        }
        Map<String, Object> tongKetNam = (Map<String, Object>) SocketClient.sendRequest(new Request(ActionType.GET_TONG_KET_TRONG_NAM, new Object[]{nam, maNVSelected})).getData();
        view.lbl_tktn_hientshd.setText(String.valueOf(tongKetNam.getOrDefault("tongSoHD", 0)));
        view.lbl_tktnam_hientongsotien.setText(df.format(tongKetNam.getOrDefault("tongTien", 0.0)));
    }
    
    private void xemChiTietThongKeThang() {
        int selectedRow = view.table_hienhd_tktt.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn một ngày trong bảng để xem chi tiết!");
            return;
        }

        int ngay = (int) view.table_hienhd_tktt.getValueAt(selectedRow, 0);
        int thang = view.month_tktt.getMonth() + 1;
        int nam = view.year_tktt.getYear();
        LocalDate selectedDate = LocalDate.of(nam, thang, ngay);

        String maNVSelected = getSelectedMaNV(view.table_ttkt);

        ThongkeHDThang_GUI dialog = new ThongkeHDThang_GUI(view.QuanLyHieuThuocTay);
        dialog.loadData(selectedDate, maNVSelected);
        dialog.setVisible(true);
    }

    /** THÊM MỚI: Mở dialog xem chi tiết hóa đơn của một tháng trong năm */
    private void xemChiTietThongKeNam() {
        int selectedRow = view.table_11.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.QuanLyHieuThuocTay, "Vui lòng chọn một tháng trong bảng để xem chi tiết!");
            return;
        }

        String thangStr = view.table_11.getValueAt(selectedRow, 0).toString().replace("Tháng ", "");
        int thang = Integer.parseInt(thangStr);
        int nam = view.year_tktn.getYear();

        String maNVSelected = getSelectedMaNV(view.table_tktn_hiennv);

        ThongkeHDNam_GUI dialog = new ThongkeHDNam_GUI(view.QuanLyHieuThuocTay);
        dialog.loadData(thang, nam, maNVSelected); 
        dialog.setVisible(true);
    }


    /** THÊM MỚI: Load danh sách nhân viên vào bảng lọc */
    private void loadNhanVienToTable(JTable tableNV) {
        DefaultTableModel modelNV = (DefaultTableModel) tableNV.getModel();
        modelNV.setRowCount(0);
        List<NhanVien> dsNV = (List<NhanVien>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHAN_VIEN, null)).getData();
        for (NhanVien nv : dsNV) {
            modelNV.addRow(new Object[]{nv.getMaNV(), nv.getTenNV()});
        }
    }

    /** THÊM MỚI: Thiết lập bộ lọc cho bảng nhân viên (theo mã và tên) */
    private void setupNhanVienFilter(JTextField txtMaNV, JTextField txtTenNV, JTable tableNV) {
        DefaultTableModel modelNV = (DefaultTableModel) tableNV.getModel();
        TableRowSorter<DefaultTableModel> currentSorter; 

        if (tableNV == view.table_8) {
            sorterNVNgay = new TableRowSorter<>(modelNV);
            tableNV.setRowSorter(sorterNVNgay);
            currentSorter = sorterNVNgay; 
        } else if (tableNV == view.table_ttkt) {
            sorterNVThang = new TableRowSorter<>(modelNV);
            tableNV.setRowSorter(sorterNVThang);
            currentSorter = sorterNVThang; 
        } else if (tableNV == view.table_tktn_hiennv) {
            sorterNVNam = new TableRowSorter<>(modelNV);
            tableNV.setRowSorter(sorterNVNam);
            currentSorter = sorterNVNam; 
        } else {
             System.err.println("Lỗi setupNhanVienFilter: Bảng không xác định.");
            return; 
        }

        DocumentListener filterListenerNV = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { applyNhanVienFilter(); }
            @Override public void removeUpdate(DocumentEvent e) { applyNhanVienFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { applyNhanVienFilter(); }

            private void applyNhanVienFilter() {
                List<RowFilter<Object, Object>> filters = new ArrayList<>();
                String maFilter = txtMaNV.getText().trim();
                String tenFilter = txtTenNV.getText().trim();

                if (!maFilter.isEmpty()) {
                    filters.add(RowFilter.regexFilter("(?i)" + maFilter, 0));
                }
                if (!tenFilter.isEmpty()) {
                    filters.add(RowFilter.regexFilter("(?i)" + tenFilter, 1));
                }

                RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
                currentSorter.setRowFilter(combinedFilter); 
            }
        };

        txtMaNV.getDocument().addDocumentListener(filterListenerNV);
        txtTenNV.getDocument().addDocumentListener(filterListenerNV);

    }
    private String getSelectedMaNV(JTable tableNV) {
        int selectedRow = tableNV.getSelectedRow();
        if (selectedRow != -1) {
           
            int modelRow = tableNV.convertRowIndexToModel(selectedRow);
            return tableNV.getModel().getValueAt(modelRow, 0).toString(); 
        }
        return null; 
    }

    private void clearNhanVienSelection(JTable tableNV) {
        tableNV.clearSelection(); 
    }

     private void filterHoaDonTableBySelectedNV(JTable tableNV, JTable tableHD, int colMaNV, int colTenNV) {
         String maNVSelected = getSelectedMaNV(tableNV);
         DefaultTableModel modelHD = (DefaultTableModel) tableHD.getModel();
         TableRowSorter<DefaultTableModel> sorterHD = new TableRowSorter<>(modelHD);
         tableHD.setRowSorter(sorterHD);

         if (maNVSelected != null) {
             sorterHD.setRowFilter(RowFilter.regexFilter("(?i)" + maNVSelected, colMaNV));
         } else {
             sorterHD.setRowFilter(null);
         }
     }


     private Map<String, Object> tinhTongKetThangTheoNV(int thang, int nam, String maNV) {
         Map<String, Object> result = new HashMap<>();
         int tongSoHD = 0;
         double tongTien = 0.0;

         List<Object[]> dsHDThang = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_HOA_DON_CHI_TIET_TRONG_THANG_NAM, new Object[]{thang, nam})).getData(); 

         for (Object[] hd : dsHDThang) {
             String maNVTrongHD = (String) hd[3];
             if (maNV.equals(maNVTrongHD)) {
                 tongSoHD++;
                 tongTien += (Double) hd[5]; 
             }
         }
         result.put("tongSoHD", tongSoHD);
         result.put("tongTien", tongTien);
         return result;
     }

     private Map<String, Object> tinhTongKetNamTheoNV(int nam, String maNV) {
         Map<String, Object> result = new HashMap<>();
         int tongSoHD = 0;
         double tongTien = 0.0;

          List<Object[]> dsHDNam = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_HOA_DON_CHI_TIET_TRONG_NAM, nam)).getData(); // Cần thêm hàm này vào DAO

         for (Object[] hd : dsHDNam) {
             String maNVTrongHD = (String) hd[3];
             if (maNV.equals(maNVTrongHD)) {
                 tongSoHD++;
                 tongTien += (Double) hd[5]; 
             }
         }
         result.put("tongSoHD", tongSoHD);
         result.put("tongTien", tongTien);
         return result;
     }
     private void setNhanVienFilterControlsVisible(JPanel filterPanel, JTable employeeTable, boolean enable) {
         if (filterPanel != null) {
             for (Component comp : filterPanel.getComponents()) {
                 if (comp instanceof JTextField) {
                     comp.setEnabled(enable);
                     if (!enable) {
                         ((JTextField) comp).setText(""); 
                     }
                 }
             }
              filterPanel.setVisible(true);
              if(filterPanel.getParent() != null) {
                   filterPanel.getParent().setVisible(true);
              }
         }

         if (employeeTable != null) {
             DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
             if (!enable) {
                 model.setRowCount(0); 
                 employeeTable.clearSelection(); 
             } else {
                 loadNhanVienToTable(employeeTable); 
             }
              Component parent = employeeTable.getParent(); 
              if(parent != null && parent.getParent() instanceof JScrollPane){
                  ((JScrollPane)parent.getParent()).setVisible(true);
              }
         }
         if (filterPanel != null && filterPanel.getParent() != null) {
              filterPanel.getParent().revalidate();
              filterPanel.getParent().repaint();
         }
         if (employeeTable != null && employeeTable.getParent() != null && employeeTable.getParent().getParent() != null) { // table -> viewport -> scrollpane
              employeeTable.getParent().getParent().revalidate();
              employeeTable.getParent().getParent().repaint();
         }
     }
     /**
      * Vẽ biểu đồ cột doanh thu theo ngày trong tháng.
      * @param data Danh sách dữ liệu, mỗi Object[] chứa: [Ngày (int), TổngSốHD (int), TổngTiền (double)]
      */
     private void veBieuDoThang(List<Object[]> data) {
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
         String series = "Doanh thu";

         if (data != null && !data.isEmpty()) {
             for (Object[] row : data) {
                 try {
                     String ngay = String.valueOf(row[0]); 
                     double doanhThu = ((Number) row[2]).doubleValue(); 
                     dataset.addValue(doanhThu, series, ngay);
                 } catch (Exception e) { System.err.println("Lỗi dữ liệu biểu đồ tháng: " + e.getMessage()); }
             }
         } else {
              dataset.addValue(0, series, "1"); 
         }

         JFreeChart barChart = ChartFactory.createBarChart(
                 "", "Ngày", "Doanh thu (VND)", dataset,
                 PlotOrientation.VERTICAL, false, true, false);

         CategoryPlot plot = barChart.getCategoryPlot();
         plot.setBackgroundPaint(Color.WHITE);
         plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
         plot.setInsets(new RectangleInsets(10, 10, 10, 10)); 

         CategoryAxis domainAxis = plot.getDomainAxis();
         domainAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
         domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

         NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
         rangeAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
         rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
         rangeAxis.setNumberFormatOverride(new DecimalFormat("#,##0"));

         BarRenderer renderer = (BarRenderer) plot.getRenderer();
         renderer.setSeriesPaint(0, new Color(0, 123, 255));
         renderer.setDrawBarOutline(false);
         renderer.setItemMargin(0.2);

         ChartPanel chartPanel = new ChartPanel(barChart);
         chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
         chartPanel.setBackground(Color.WHITE);

         view.pn_bieudo_thang.removeAll(); 
         view.pn_bieudo_thang.add(chartPanel, BorderLayout.CENTER); 
         view.pn_bieudo_thang.revalidate();
         view.pn_bieudo_thang.repaint();
     }
     
     /**
      * Vẽ biểu đồ cột doanh thu theo tháng trong năm.
      * @param data Danh sách dữ liệu, mỗi Object[] chứa: [Tháng (int/String), TổngSốHD (int), TổngTiền (double)]
      */
     private void veBieuDoNam(List<Object[]> data) {
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
         String series = "Doanh thu";

         if (data != null && !data.isEmpty()) {
             for (Object[] row : data) {
                  try {
                      String thang;
                      if(row[0] instanceof String && ((String)row[0]).startsWith("Tháng ")){
                          thang = ((String)row[0]).replace("Tháng ", "");
                      } else {
                          thang = String.valueOf(row[0]);
                      }
                     double doanhThu = ((Number) row[2]).doubleValue();
                     dataset.addValue(doanhThu, series, "T" + thang);
                  } catch (Exception e) { System.err.println("Lỗi dữ liệu biểu đồ năm: " + e.getMessage()); }
             }
         } else {
              dataset.addValue(0, series, "T1"); 
         }

         JFreeChart barChart = ChartFactory.createBarChart(
                 "", "Tháng", "Doanh thu (VND)", dataset,
                 PlotOrientation.VERTICAL, false, true, false);

         CategoryPlot plot = barChart.getCategoryPlot();
         plot.setBackgroundPaint(Color.WHITE);
         plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
         plot.setInsets(new RectangleInsets(10, 10, 10, 10));

         CategoryAxis domainAxis = plot.getDomainAxis();
         domainAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
         domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

         NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
         rangeAxis.setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
         rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
         rangeAxis.setNumberFormatOverride(new DecimalFormat("#,##0"));

         BarRenderer renderer = (BarRenderer) plot.getRenderer();
         renderer.setSeriesPaint(0, new Color(40, 167, 69)); 
         renderer.setDrawBarOutline(false);
         renderer.setItemMargin(0.2);

         ChartPanel chartPanel = new ChartPanel(barChart);
         chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
         chartPanel.setBackground(Color.WHITE);

         view.pn_bieudo_nam.removeAll(); 
         view.pn_bieudo_nam.add(chartPanel, BorderLayout.CENTER); 
         view.pn_bieudo_nam.revalidate();
         view.pn_bieudo_nam.repaint();
     }
}
     
