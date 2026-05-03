package controller;
// Force Eclipse refresh

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.KeThuoc;
import entity.NhaCungCap;
import entity.Thuoc;
import gui.ChiTietThuoc_GUI;
import gui.TrangChu_GUI;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Thuoc_Controller {
    private TrangChu_GUI trangChuGUI;
    private File selectedFile = null;
    private String filePath = null;
    private DefaultTableModel modelTtf; 

    public Thuoc_Controller(TrangChu_GUI trangChuGUI) {
        this.trangChuGUI = trangChuGUI;


        // Khởi tạo model cho bảng tạm file và đảm bảo rỗng
        modelTtf = (DefaultTableModel) trangChuGUI.table_ttf.getModel();
        modelTtf.setRowCount(0); 

        // Cập nhật các hàm hiển thị để chỉ hiển thị thuốc active
        hienThiThuoc(); 
        hienThiThuocLenCapNhat();
        hienThiThuocLenThemThuoc();
        hienThiThuocLenThemThuocFile(); // Vẫn là bảng tạm, không lấy từ DAO

        loadComboboxData();


        ItemListener filterListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                hienThiThuocTheoLoc();
            }
        };
        trangChuGUI.cb_tkt_kethuoc.addItemListener(filterListener);
        trangChuGUI.cb_tkt_tenthuoc.addItemListener(filterListener);
        trangChuGUI.cb_tkt_ncc.addItemListener(filterListener);

        // nút xem chi tiết thuốc
        trangChuGUI.btn_tkt_xemchitiet.addActionListener(e -> {
            int selectedRow = trangChuGUI.table_tkt.getSelectedRow();
            if (selectedRow >= 0) {
                String maThuoc = trangChuGUI.table_tkt.getValueAt(selectedRow, 0).toString();
                Thuoc t = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
                if (t != null) {
                    ChiTietThuoc_GUI chitiet = new ChiTietThuoc_GUI((JFrame) trangChuGUI);
                    chitiet.loadThuoc(t);
                    chitiet.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn thuốc để xem chi tiết");
            }
        });

        trangChuGUI.btn_tkt_Lammoi.addActionListener(e -> {
            trangChuGUI.cb_tkt_kethuoc.setSelectedIndex(0);
            trangChuGUI.cb_tkt_tenthuoc.setSelectedIndex(0);
            trangChuGUI.cb_tkt_ncc.setSelectedIndex(0);
            hienThiThuoc();
        });
        
        trangChuGUI.btn_ttLammoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                trangChuGUI.text_tttt.setText("");
                trangChuGUI.text_ttsl.setText("");
                trangChuGUI.text_ttgn.setText("");
                trangChuGUI.text_ttgb.setText("");
                trangChuGUI.textArea_tttp.setText("");

                trangChuGUI.cb_ttdvt.setSelectedIndex(0);
                trangChuGUI.cb_ttncc.setSelectedIndex(0);
                trangChuGUI.cb_tttkt.setSelectedIndex(0);

                trangChuGUI.date_tthsd.setDate(null);

                trangChuGUI.lb_Chuaanh_tt.setIcon(null);
                trangChuGUI.lb_Chuaanh_tt.setText("Chưa có ảnh");

                trangChuGUI.table_themthuoc.clearSelection();
                
                // Khôi phục mã thuốc mới nhất
                trangChuGUI.text_ttmt.setText((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_THUOC, null)).getData()); 
            }
        });


        trangChuGUI.btn_cnt_tk_lammoi.addActionListener(e -> {
            trangChuGUI.text_cnt_tkmt.setText("");
            trangChuGUI.text_cnt_tktt.setText("");
            trangChuGUI.cb_cnt_tktkt.setSelectedIndex(0);
            hienThiThuocLenCapNhat();
        });

        ItemListener filterCntListener = e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                filterThuocCapNhat();
            }
        };
        trangChuGUI.cb_cnt_tktkt.addItemListener(filterCntListener);

        trangChuGUI.table_Capnhatthuoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = trangChuGUI.table_Capnhatthuoc.getSelectedRow();
                if (selectedRow >= 0) {
                    String maThuoc = trangChuGUI.table_Capnhatthuoc.getValueAt(selectedRow, 0).toString();
                    // Lấy thuốc từ DAO để đảm bảo lấy đủ thông tin (bao gồm cả trangThai)
                    Thuoc t = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData(); 
                    if (t != null) {
                        trangChuGUI.text_cntmt.setText(t.getMaThuoc());
                        trangChuGUI.text_cnttt.setText(t.getTenThuoc());
                        trangChuGUI.text_cntsl.setText(String.valueOf(t.getSoLuong()));
                        trangChuGUI.text_cntgn.setText(String.valueOf(t.getGiaNhap()));
                        trangChuGUI.text_cntgb.setText(String.valueOf(t.getGiaBan()));
                        trangChuGUI.cb_cntdvt.setSelectedItem(t.getDonViTinh());
                        trangChuGUI.cb_cntncc.setSelectedItem(t.getNhaCungCap().getTenNhaCungCap());
                        
                        if (t.getHanSuDung() != null) {
                            java.util.Date date = java.sql.Date.valueOf(t.getHanSuDung());
                            trangChuGUI.date_cnthsd.setDate(date);
                        } else {
                            trangChuGUI.date_cnthsd.setDate(null);
                        }
                        trangChuGUI.cb_cnt_tkt.setSelectedItem(t.getKeThuoc().getLoaiKe());
                        trangChuGUI.textArea_cnttp.setText(t.getThanhPhan());
                        
                        // Nếu có JComboBox trạng thái, sẽ set ở đây:
                        // trangChuGUI.cb_cnt_trangthai.setSelectedItem(t.getTrangThai());

                        if (t.getAnh() != null && !t.getAnh().isEmpty()) {
                            ImageIcon icon = new ImageIcon(t.getAnh());
                            Image img = icon.getImage().getScaledInstance(
                                    trangChuGUI.lb_Chuaanh.getWidth(),
                                    trangChuGUI.lb_Chuaanh.getHeight(),
                                    Image.SCALE_SMOOTH);
                            trangChuGUI.lb_Chuaanh.setIcon(new ImageIcon(img));
                            trangChuGUI.lb_Chuaanh.setText("");
                        } else {
                            trangChuGUI.lb_Chuaanh.setIcon(null);
                            trangChuGUI.lb_Chuaanh.setText("Chưa có ảnh");
                        }
                    }
                }
            }
        });
        
        trangChuGUI.btn_cntKhoiphuc.addActionListener(e -> {
            String maThuoc = trangChuGUI.text_cntmt.getText().trim();

            if (maThuoc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập hoặc chọn mã thuốc cần khôi phục!");
                return;
            }

            Thuoc thuocGoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();

            if (thuocGoc != null) {
                trangChuGUI.text_cntmt.setText(thuocGoc.getMaThuoc());
                trangChuGUI.text_cnttt.setText(thuocGoc.getTenThuoc());
                trangChuGUI.text_cntsl.setText(String.valueOf(thuocGoc.getSoLuong()));
                trangChuGUI.text_cntgn.setText(String.valueOf(thuocGoc.getGiaNhap()));
                trangChuGUI.text_cntgb.setText(String.valueOf(thuocGoc.getGiaBan()));

                if (thuocGoc.getHanSuDung() != null) {
                    java.util.Date date = java.sql.Date.valueOf(thuocGoc.getHanSuDung());
                    trangChuGUI.date_cnthsd.setDate(date);
                } else {
                    trangChuGUI.date_cnthsd.setDate(null);
                }

                trangChuGUI.textArea_cnttp.setText(thuocGoc.getThanhPhan());

                trangChuGUI.cb_cntdvt.setSelectedItem(thuocGoc.getDonViTinh());
                trangChuGUI.cb_cntncc.setSelectedItem(thuocGoc.getNhaCungCap().getTenNhaCungCap());
                trangChuGUI.cb_cnt_tkt.setSelectedItem(thuocGoc.getKeThuoc().getLoaiKe());
                
                // Khôi phục trạng thái (nếu có)
                // trangChuGUI.cb_cnt_trangthai.setSelectedItem(thuocGoc.getTrangThai()); 

                // hiển thị ảnh
                if (thuocGoc.getAnh() != null && !thuocGoc.getAnh().isEmpty()) {
                    ImageIcon icon = new ImageIcon(thuocGoc.getAnh());
                    Image img = icon.getImage().getScaledInstance(trangChuGUI.lb_Chuaanh.getWidth(),
                            trangChuGUI.lb_Chuaanh.getHeight(),
                            Image.SCALE_SMOOTH);
                    trangChuGUI.lb_Chuaanh.setIcon(new ImageIcon(img));
                    trangChuGUI.lb_Chuaanh.setText("");
                } else {
                    trangChuGUI.lb_Chuaanh.setIcon(null);
                    trangChuGUI.lb_Chuaanh.setText("Chưa có ảnh");
                }

                
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy thuốc có mã " + maThuoc + " trong cơ sở dữ liệu!");
            }
        });


        // === START: CODE SỬA LỖI BẢNG TẠM ===
        trangChuGUI.btn_ttf_chonfile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn file Excel");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "File Excel (*.xls, *.xlsx)", "xls", "xlsx"));
            
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();

                int tongSoThuoc = 0;
                try (FileInputStream fis = new FileInputStream(selectedFile)) {
                    Workbook workbook;
                    if (filePath.endsWith(".xlsx")) {
                        workbook = new XSSFWorkbook(fis);
                    } else {
                        workbook = new HSSFWorkbook(fis);
                    }

                    Sheet sheet = workbook.getSheetAt(0);
                    
                    // Lấy mã thuốc khởi tạo từ DB và chuẩn bị cho việc sinh mã liên tục
                    String lastMa = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_THUOC, null)).getData();
                    int lastNumber = 0;
                    if (lastMa != null && lastMa.startsWith("T")) {
                         // Lấy số từ mã (ví dụ: T01 -> 1)
                         // VÍ DỤ: Nếu MAX(MaThuoc) là T57, lastMa là T58 -> lastNumber = 58
                         lastNumber = Integer.parseInt(lastMa.substring(1));
                    }
                    
                    modelTtf.setRowCount(0); 
                    
                    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) continue; 

                        String tenThuoc = getCellValue(row.getCell(0));
                        if (!tenThuoc.trim().isEmpty()) {
                            tongSoThuoc++;
                            
                            // === SỬA: SINH MÃ THUỐC TẠM THỜI VÀ HIỂN THỊ ===
                            String prefix = "T";
                            // SỬ DỤNG lastNumber VÀ SAU ĐÓ TĂNG LÊN
                            String maThuocTam = String.format("%s%02d", prefix, lastNumber);
                            lastNumber++; // Tăng cho lần tiếp theo (59)
                            
                            // === ÁNH XẠ DỮ LIỆU ĐÚNG VÀO CỘT JTABLE THEO THỨ TỰ GỐC ===
                            modelTtf.addRow(new Object[]{
                                    maThuocTam,                              // 0: Mã Thuốc (ĐÃ SINH)
                                    tenThuoc,                                // 1: Tên Thuốc (Cell 0)
                                    getCellValue(row.getCell(1)),            // 2: Số Lượng (Cell 1)
                                    getCellValue(row.getCell(2)),            // 3: Giá Nhập (Cell 2)
                                    getCellValue(row.getCell(3)),            // 4: Giá Bán (Cell 3)
                                    getCellValue(row.getCell(6)),            // 5: Đơn Vị Tính (Cell 6)
                                    getCellValue(row.getCell(9)),            // 6: Nhà Cung Cấp (Cell 9)
                                    getCellValue(row.getCell(4)),            // 7: Hạn Sử Dụng (Cell 4)
                                    getCellValue(row.getCell(8)),            // 8: Tên Kệ Thuốc (Cell 8)
                                    getCellValue(row.getCell(5)),            // 9: Thành Phần (Cell 5)
                                    getCellValue(row.getCell(7))             // 10: Ảnh (Cell 7)
                            });
                        }
                    }
                    workbook.close();

                    trangChuGUI.lbl_ttfile_hienthitongsothuoc.setText(""+tongSoThuoc);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel!");
                }
            }
        });
        trangChuGUI.btn_ttf_lammoi.addActionListener(e -> {
            trangChuGUI.lbl_ttfile_hienthitongsothuoc.setText("0");
            selectedFile = null;
            filePath = null;
            modelTtf.setRowCount(0); // Xóa bảng tạm
            JOptionPane.showMessageDialog(null, "Đã làm mới thông tin file. Bạn có thể chọn file mới để thêm thuốc.");
        });
        
        trangChuGUI.btn_cnt_ChonAnh.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh đại diện thuốc");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Hình ảnh (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(
                        trangChuGUI.lb_Chuaanh.getWidth(),
                        trangChuGUI.lb_Chuaanh.getHeight(),
                        Image.SCALE_SMOOTH);
                trangChuGUI.lb_Chuaanh.setIcon(new ImageIcon(img));
                trangChuGUI.lb_Chuaanh.setText("");

                trangChuGUI.lb_Chuaanh.setToolTipText(path);
            }
        });
        
     // nút chọn ảnh thêm thuốc
        trangChuGUI.btn_ChonAnh_tt.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh đại diện thuốc");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Hình ảnh (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(
                        trangChuGUI.lb_Chuaanh_tt.getWidth(),
                        trangChuGUI.lb_Chuaanh_tt.getHeight(),
                        Image.SCALE_SMOOTH);
                trangChuGUI.lb_Chuaanh_tt.setIcon(new ImageIcon(img));
                trangChuGUI.lb_Chuaanh_tt.setText("");

                // lưu đường dẫn ảnh vào label để lấy lại khi cập nhật
                trangChuGUI.lb_Chuaanh_tt.setToolTipText(path);
            }
        });

        
        trangChuGUI.btn_cntCapnhat.addActionListener(e -> {
            try {
                // ... (Logic Cập nhật giữ nguyên, đã được sửa lỗi trạng thái ở các bước trước) ...
                String maThuoc = trangChuGUI.text_cntmt.getText().trim();
                String tenThuoc = trangChuGUI.text_cnttt.getText().trim();
                int soLuong = Integer.parseInt(trangChuGUI.text_cntsl.getText().trim());
                double giaNhap = Double.parseDouble(trangChuGUI.text_cntgn.getText().trim());
                double giaBan = Double.parseDouble(trangChuGUI.text_cntgb.getText().trim());
                String donViTinh = trangChuGUI.cb_cntdvt.getSelectedItem().toString();

                java.util.Date hsdDate = trangChuGUI.date_cnthsd.getDate();
                LocalDate hanSuDung = null;
                if (hsdDate != null) {
                    hanSuDung = new java.sql.Date(hsdDate.getTime()).toLocalDate();
                } else {
                    JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn hạn sử dụng!");
                    return;
                }

                String thanhPhan = trangChuGUI.textArea_cnttp.getText().trim();
                String loaiKe = trangChuGUI.cb_cnt_tkt.getSelectedItem().toString();
                String tenNCC = trangChuGUI.cb_cntncc.getSelectedItem().toString();
                
                Thuoc thuocGoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
                String trangThai = (thuocGoc != null) ? thuocGoc.getTrangThai() : "Đang kinh doanh"; 

                String maKe = null;
                List<Thuoc> dsAll = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_THUOC, null)).getData();
                for (KeThuoc k : dsAll.stream().map(Thuoc::getKeThuoc).distinct().toList()) {
                    if (k.getLoaiKe().equalsIgnoreCase(loaiKe)) {
                        maKe = k.getMaKe();
                        break;
                    }
                }

                if (maKe == null) {
                    JOptionPane.showMessageDialog(trangChuGUI,
                            "Không tìm thấy mã kệ tương ứng với loại kệ: " + loaiKe);
                    return;
                }

                NhaCungCap nccObj = (NhaCungCap) SocketClient.sendRequest(new Request(ActionType.GET_NHA_CUNG_CAP_BY_TEN, tenNCC)).getData();
                String maNCC = nccObj != null ? nccObj.getMaNhaCungCap() : null;
                if (maNCC == null || maNCC.isEmpty()) {
                    JOptionPane.showMessageDialog(trangChuGUI,
                            "Không tìm thấy mã nhà cung cấp cho: " + tenNCC);
                    return;
                }

                Thuoc t = new Thuoc();
                t.setMaThuoc(maThuoc);
                t.setTenThuoc(tenThuoc);
                t.setSoLuong(soLuong);
                t.setGiaNhap(giaNhap);
                t.setGiaBan(giaBan);
                t.setDonViTinh(donViTinh);
                t.setHanSuDung(hanSuDung);
                t.setThanhPhan(thanhPhan);
                t.setTrangThai(trangThai); 

                KeThuoc ke = new KeThuoc();
                ke.setMaKe(maKe);
                t.setKeThuoc(ke);

                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNhaCungCap(maNCC);
                t.setNhaCungCap(ncc);

                if (trangChuGUI.lb_Chuaanh.getToolTipText() != null) {
                    t.setAnh(trangChuGUI.lb_Chuaanh.getToolTipText());
                }

                boolean updated = (boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_THUOC, t)).getData();
                if (updated) {
                    JOptionPane.showMessageDialog(trangChuGUI, "Cập nhật thuốc thành công!");
                    hienThiThuoc();
                    hienThiThuocLenCapNhat();
                    hienThiThuocLenThemThuoc();
                } else {
                    JOptionPane.showMessageDialog(trangChuGUI, "Cập nhật thất bại!");
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(trangChuGUI, "Nhập sai kiểu dữ liệu số!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(trangChuGUI, "Lỗi: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        trangChuGUI.text_cnt_tkmt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemThuoc_TK();
            }
        });

        trangChuGUI.text_cnt_tktt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timKiemThuoc_TK();
            }
        });

        trangChuGUI.cb_cnt_tktkt.addActionListener(e -> timKiemThuoc_TK());
        
        trangChuGUI.btn_ttThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // ... (Logic Thêm thủ công giữ nguyên) ...
                    String maThuoc = trangChuGUI.text_ttmt.getText().trim();
                    String tenThuoc = trangChuGUI.text_tttt.getText().trim();
                    String donViTinh = trangChuGUI.cb_ttdvt.getSelectedItem().toString();
                    String tenNCC = trangChuGUI.cb_ttncc.getSelectedItem().toString();
                    String loaiKe = trangChuGUI.cb_tttkt.getSelectedItem().toString();
                    String thanhPhan = trangChuGUI.textArea_tttp.getText().trim();
                    String anh = (trangChuGUI.lb_Chuaanh_tt.getIcon() != null)
                            ? trangChuGUI.lb_Chuaanh_tt.getToolTipText() 
                            : null;


                    if (tenThuoc.isEmpty() || trangChuGUI.text_ttsl.getText().isEmpty() ||
                        trangChuGUI.text_ttgn.getText().isEmpty() || trangChuGUI.text_ttgb.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin thuốc!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int soLuong = Integer.parseInt(trangChuGUI.text_ttsl.getText().trim());
                    double giaNhap = Double.parseDouble(trangChuGUI.text_ttgn.getText().trim());
                    double giaBan = Double.parseDouble(trangChuGUI.text_ttgb.getText().trim());

                    java.util.Date date = trangChuGUI.date_tthsd.getDate();
                    if (date == null) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn hạn sử dụng!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    java.sql.Date hanSuDung = new java.sql.Date(date.getTime());

                    NhaCungCap nccRes = (NhaCungCap) SocketClient.sendRequest(new Request(ActionType.GET_NHA_CUNG_CAP_BY_TEN, tenNCC)).getData();
                    String maNCC = nccRes != null ? nccRes.getMaNhaCungCap() : null;
                    KeThuoc keRes = (KeThuoc) SocketClient.sendRequest(new Request(ActionType.GET_KE_THUOC_BY_TEN, loaiKe)).getData();
                    String maKe = keRes != null ? keRes.getMaKe() : null;

                    if (maNCC == null || maKe == null) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy mã NCC hoặc mã Kệ tương ứng!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    String trangThai = "Đang kinh doanh"; 

                    Thuoc t = new Thuoc(
                        maThuoc, tenThuoc, giaNhap, giaBan, soLuong, hanSuDung.toLocalDate(),
                        thanhPhan, donViTinh, anh, 
                        new KeThuoc(maKe, loaiKe), 
                        new NhaCungCap(maNCC, tenNCC), 
                        trangThai 
                    );

                    boolean result = (boolean) SocketClient.sendRequest(new Request(ActionType.ADD_THUOC, t)).getData();
                    if (result) {
                        JOptionPane.showMessageDialog(null, "Thêm thuốc thành công!");
                        
                        hienThiThuoc();
                        hienThiThuocLenCapNhat();
                        hienThiThuocLenThemThuoc();
                        
                        trangChuGUI.text_ttmt.setText((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_THUOC, null)).getData());

                        // Làm mới form
                        trangChuGUI.text_tttt.setText("");
                        trangChuGUI.text_ttsl.setText("");
                        trangChuGUI.text_ttgn.setText("");
                        trangChuGUI.text_ttgb.setText("");
                        trangChuGUI.textArea_tttp.setText("");
                        trangChuGUI.cb_ttdvt.setSelectedIndex(0);
                        trangChuGUI.cb_ttncc.setSelectedIndex(0);
                        trangChuGUI.cb_tttkt.setSelectedIndex(0);
                        trangChuGUI.date_tthsd.setDate(null);
                        trangChuGUI.lb_Chuaanh_tt.setIcon(null);
                        trangChuGUI.lb_Chuaanh_tt.setText("Chưa có ảnh");
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm thuốc thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng, giá nhập và giá bán phải là số hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm thuốc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        trangChuGUI.btn_ttf_them.addActionListener(e -> {
            if (selectedFile == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn file Excel trước khi thêm!");
                return;
            }

            if (modelTtf.getRowCount() == 0) {
                 JOptionPane.showMessageDialog(null, "Không có dữ liệu trong bảng tạm để thêm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                 return;
            }

         // TÁCH HÀM XỬ LÝ LƯU DB ĐỂ CÓ THỂ ĐỌC LẠI DỮ LIỆU ẢNH (KHÔNG CÓ TRONG MODEL)
            try {
                int soThuocThem = luuThuocTuBangTam(selectedFile, filePath, modelTtf);

                // === CẬP NHẬT UI VÀ XÓA BẢNG TẠM SAU KHI LƯU DB ===
                hienThiThuoc();
                hienThiThuocLenCapNhat();
                hienThiThuocLenThemThuoc();
                
                modelTtf.setRowCount(0); // **XÓA SẠCH DỮ LIỆU TỪ BẢNG TẠM**
                loadComboboxData();
                
                // BỔ SUNG: CẬP NHẬT MÃ THUỐC MỚI NHẤT CHO FORM THÊM THỦ CÔNG
                trangChuGUI.text_ttmt.setText((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_THUOC, null)).getData()); 

                JOptionPane.showMessageDialog(null, "Đã thêm " + soThuocThem + " thuốc mới từ bảng tạm!");
                trangChuGUI.lbl_ttfile_hienthitongsothuoc.setText(String.valueOf(0)); 

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi định dạng số/ngày! Kiểm tra cột Số lượng, Giá nhập, Giá bán, hoặc Hạn sử dụng.", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm thuốc: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        trangChuGUI.btn_CNT_Xoa.addActionListener(e -> {
            String maThuoc = trangChuGUI.text_cntmt.getText().trim();

            if (maThuoc.isEmpty()) {
                JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn thuốc cần xóa (ngừng kinh doanh)!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(trangChuGUI, 
                    "Bạn có chắc chắn muốn chuyển thuốc " + maThuoc + " sang trạng thái 'Ngừng kinh doanh' không?\n"
                    + "Thuốc sẽ không còn hiển thị ở các tab Quản lý và Bán hàng.", 
                    "Xác nhận ngừng kinh doanh", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Hằng số được định nghĩa trong thuoc_DAO.java: TRANG_THAI_NGUNG_KINH_DOANH = "Ngừng kinh doanh"
                // Do không có quyền truy cập trực tiếp, ta dùng String literal
                String TRANG_THAI_NGUNG_KINH_DOANH = "Ngừng kinh doanh"; 
                
                boolean updated = (boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_TRANG_THAI_THUOC, new Object[]{maThuoc, TRANG_THAI_NGUNG_KINH_DOANH})).getData();

                if (updated) {
                    JOptionPane.showMessageDialog(trangChuGUI, "Đã chuyển thuốc " + maThuoc + " sang trạng thái 'Ngừng kinh doanh' thành công!");
                    
                    // 1. Cập nhật lại các bảng hiển thị (chỉ hiển thị thuốc đang kinh doanh)
                    hienThiThuoc();
                    hienThiThuocLenCapNhat();
                    hienThiThuocLenThemThuoc();
                    
                    // 2. Làm mới form Cập nhật/Xóa
                    trangChuGUI.text_cntmt.setText("");
                    trangChuGUI.text_cnttt.setText("");
                    trangChuGUI.text_cntsl.setText("");
                    trangChuGUI.text_cntgn.setText("");
                    trangChuGUI.text_cntgb.setText("");
                    trangChuGUI.textArea_cnttp.setText("");
                    trangChuGUI.date_cnthsd.setDate(null);
                    trangChuGUI.lb_Chuaanh.setIcon(null);
                    trangChuGUI.lb_Chuaanh.setText("Chưa có ảnh");
                    
                } else {
                    JOptionPane.showMessageDialog(trangChuGUI, "Chuyển trạng thái thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }
    
    // ... (Các hàm helper và hiển thị khác giữ nguyên) ...
    
    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }


    /**
     * SỬA: Dùng getAllActiveThuoc() để chỉ hiển thị thuốc Đang kinh doanh
     */
    private void hienThiThuoc() {
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_tkt.getModel();
        model.setRowCount(0);
        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_ACTIVE_THUOC, null)).getData(); // CHỈ ACTIVE
        for (Thuoc t : dsThuoc) {
            model.addRow(new Object[]{
                    t.getMaThuoc(), t.getTenThuoc(), t.getSoLuong(), t.getGiaNhap(), t.getGiaBan(),
                    t.getDonViTinh(), t.getNhaCungCap().getTenNhaCungCap(),
                    t.getHanSuDung(), t.getKeThuoc().getLoaiKe(), t.getAnh()
            });
        }
    }

    /**
     * SỬA: Dùng getAllActiveThuoc() cho lọc mặc định
     */
    private void hienThiThuocTheoLoc() {
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_tkt.getModel();
        model.setRowCount(0);

        // Dùng danh sách Active cho lọc (vì bảng này chỉ hiển thị active)
        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_ACTIVE_THUOC, null)).getData(); 

        Object keObj = trangChuGUI.cb_tkt_kethuoc.getSelectedItem();
        Object tenObj = trangChuGUI.cb_tkt_tenthuoc.getSelectedItem();
        Object nccObj = trangChuGUI.cb_tkt_ncc.getSelectedItem();

        if (keObj == null || tenObj == null || nccObj == null) {
            return;
        }

        String keChon = keObj.toString();
        String tenChon = tenObj.toString();
        String nccChon = nccObj.toString();

        for (Thuoc t : dsThuoc) {
            boolean matchKe = keChon.equals("Tất cả") || t.getKeThuoc().getLoaiKe().equals(keChon);
            boolean matchTen = tenChon.equals("Tất cả") || t.getTenThuoc().equals(tenChon);
            boolean matchNCC = nccChon.equals("Tất cả") || t.getNhaCungCap().getTenNhaCungCap().equals(nccChon);

            if (matchKe && matchTen && matchNCC) {
                model.addRow(new Object[]{
                        t.getMaThuoc(), t.getTenThuoc(), t.getSoLuong(), t.getGiaNhap(),
                        t.getGiaBan(), t.getDonViTinh(), t.getNhaCungCap().getTenNhaCungCap(),
                        t.getHanSuDung(), t.getKeThuoc().getLoaiKe(), t.getAnh()
                });
            }
        }
    }

    /**
     * SỬA: Dùng getAllActiveThuoc() cho bảng cập nhật
     */
    private void hienThiThuocLenCapNhat() {
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_Capnhatthuoc.getModel();
        model.setRowCount(0);
        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_ACTIVE_THUOC, null)).getData(); // CHỈ ACTIVE
        for (Thuoc t : dsThuoc) {
            model.addRow(new Object[]{
                    t.getMaThuoc(), t.getTenThuoc(), t.getSoLuong(), t.getGiaNhap(), t.getGiaBan(),
                    t.getDonViTinh(), t.getNhaCungCap().getTenNhaCungCap(),
                    t.getHanSuDung(), t.getKeThuoc().getLoaiKe(), t.getThanhPhan(), t.getAnh()
            });
        }
    }
    
    /**
     * SỬA: Dùng getAllActiveThuoc() cho bảng thêm thuốc
     */
    private void hienThiThuocLenThemThuoc() {
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_themthuoc.getModel();
        model.setRowCount(0);
        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_ACTIVE_THUOC, null)).getData(); // CHỈ ACTIVE
        for (Thuoc t : dsThuoc) {
            model.addRow(new Object[]{
                    t.getMaThuoc(), t.getTenThuoc(), t.getSoLuong(), t.getGiaNhap(), t.getGiaBan(),
                    t.getDonViTinh(), t.getNhaCungCap().getTenNhaCungCap(),
                    t.getHanSuDung(), t.getKeThuoc().getLoaiKe(), t.getThanhPhan(), t.getAnh()
            });
        }
    }
    
    /**
     * GIỮ NGUYÊN: Bảng này chỉ là bảng tạm (table_ttf)
     */
    private void hienThiThuocLenThemThuocFile() {
        // Hàm này không cần lấy dữ liệu từ DAO, giữ nguyên mục đích ban đầu
    }


    /**
     * SỬA: Dùng searchThuocForQuanLy đã được lọc trạng thái
     */
    private void timKiemThuoc_TK() {
        String maTK = trangChuGUI.text_cnt_tkmt.getText().trim();
        String tenTK = trangChuGUI.text_cnt_tktt.getText().trim();
        Object selectedItem = trangChuGUI.cb_cnt_tktkt.getSelectedItem();
        String tenKe = selectedItem != null ? selectedItem.toString() : "";
        filterTable(trangChuGUI.table_Capnhatthuoc, maTK, tenTK, tenKe);
    }

    /**
     * SỬA: Dùng searchThuocForQuanLy đã được lọc trạng thái
     */
    private void filterTable(JTable table, String maTK, String tenTK, String keTK) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        // Dùng hàm search đã được lọc trạng thái
        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.SEARCH_THUOC, new Object[]{maTK, tenTK, keTK})).getData(); 

        for (Thuoc t : dsThuoc) {
            model.addRow(new Object[]{
                    t.getMaThuoc(), t.getTenThuoc(), t.getSoLuong(), t.getGiaNhap(), t.getGiaBan(),
                    t.getDonViTinh(), t.getNhaCungCap().getTenNhaCungCap(),
                    t.getHanSuDung(), t.getKeThuoc().getLoaiKe(), t.getThanhPhan(), t.getAnh()
            });
        }
    }


    private void filterThuocCapNhat() {
        String maTK = trangChuGUI.text_cnt_tkmt.getText().trim();
        String tenTK = trangChuGUI.text_cnt_tktt.getText().trim();
        String keTK = trangChuGUI.cb_cnt_tktkt.getSelectedItem().toString().trim();
        filterTable(trangChuGUI.table_Capnhatthuoc, maTK, tenTK, keTK);
    }

    private void loadComboboxData() {
        trangChuGUI.cb_tkt_kethuoc.removeAllItems();
        trangChuGUI.cb_tkt_tenthuoc.removeAllItems();
        trangChuGUI.cb_tkt_ncc.removeAllItems();
        trangChuGUI.cb_cnt_tkt.removeAllItems();
        trangChuGUI.cb_cntncc.removeAllItems();
        trangChuGUI.cb_cntdvt.removeAllItems();
        trangChuGUI.cb_cnt_tktkt.removeAllItems();
        trangChuGUI.cb_ttdvt.removeAllItems();
        trangChuGUI.cb_ttncc.removeAllItems();
        trangChuGUI.cb_tttkt.removeAllItems();

        trangChuGUI.cb_tkt_kethuoc.addItem("Tất cả");
        trangChuGUI.cb_tkt_tenthuoc.addItem("Tất cả");
        trangChuGUI.cb_tkt_ncc.addItem("Tất cả");
        trangChuGUI.cb_cnt_tkt.addItem("Tất cả");
        trangChuGUI.cb_cnt_tktkt.addItem("Tất cả");

        // Lấy tất cả thuốc để load đầy đủ các loại kệ, tên, NCC vào combobox
        List<Thuoc> dsThuoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_THUOC, null)).getData();
        for (Thuoc t : dsThuoc) {
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_tkt_kethuoc.getModel())
                    .getIndexOf(t.getKeThuoc().getLoaiKe()) == -1)
                trangChuGUI.cb_tkt_kethuoc.addItem(t.getKeThuoc().getLoaiKe());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_tkt_tenthuoc.getModel())
                    .getIndexOf(t.getTenThuoc()) == -1)
                trangChuGUI.cb_tkt_tenthuoc.addItem(t.getTenThuoc());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_tkt_ncc.getModel())
                    .getIndexOf(t.getNhaCungCap().getTenNhaCungCap()) == -1)
                trangChuGUI.cb_tkt_ncc.addItem(t.getNhaCungCap().getTenNhaCungCap());

            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_cnt_tkt.getModel())
                    .getIndexOf(t.getKeThuoc().getLoaiKe()) == -1)
                trangChuGUI.cb_cnt_tkt.addItem(t.getKeThuoc().getLoaiKe());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_cntncc.getModel())
                    .getIndexOf(t.getNhaCungCap().getTenNhaCungCap()) == -1)
                trangChuGUI.cb_cntncc.addItem(t.getNhaCungCap().getTenNhaCungCap());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_cntdvt.getModel())
                    .getIndexOf(t.getDonViTinh()) == -1)
                trangChuGUI.cb_cntdvt.addItem(t.getDonViTinh());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_cnt_tktkt.getModel())
                    .getIndexOf(t.getKeThuoc().getLoaiKe()) == -1)
                trangChuGUI.cb_cnt_tktkt.addItem(t.getKeThuoc().getLoaiKe());
            
            
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_tttkt.getModel())
                    .getIndexOf(t.getKeThuoc().getLoaiKe()) == -1)
                trangChuGUI.cb_tttkt.addItem(t.getKeThuoc().getLoaiKe());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_ttncc.getModel())
                    .getIndexOf(t.getNhaCungCap().getTenNhaCungCap()) == -1)
                trangChuGUI.cb_ttncc.addItem(t.getNhaCungCap().getTenNhaCungCap());
            if (((DefaultComboBoxModel<String>) trangChuGUI.cb_ttdvt.getModel())
                    .getIndexOf(t.getDonViTinh()) == -1)
                trangChuGUI.cb_ttdvt.addItem(t.getDonViTinh());
            
        }
    }
 // --- THÊM HÀM MỚI ĐỂ XỬ LÝ LOGIC LƯU DB VÀ ĐỌC FILE LẠI LẤY ẢNH ---
    private int luuThuocTuBangTam(File selectedFile, String filePath, DefaultTableModel modelTtf) throws Exception {
        int soThuocThem = 0;
        // Refactored to use network calls
        
        // === BỔ SUNG: KHỞI TẠO BỘ ĐẾM MÃ THUỐC TRƯỚC VÒNG LẶP ===
        String lastMa = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_THUOC, null)).getData();
        int currentNumber = 0;
        if (lastMa != null && lastMa.startsWith("T")) {
             currentNumber = Integer.parseInt(lastMa.substring(1)); // Bắt đầu từ mã tiếp theo (VD: 58)
        }

        try (FileInputStream fis = new FileInputStream(selectedFile)) {
            Workbook workbook;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else {
                workbook = new HSSFWorkbook(fis);
            }
            Sheet sheet = workbook.getSheetAt(0);

         // Dùng modelTtf để lấy dữ liệu hiển thị (10 cột)
            for (int i = 0; i < modelTtf.getRowCount(); i++) {
                
                // Lấy dữ liệu từ bảng tạm theo chỉ mục hiển thị (0-9)
                String tenThuoc = modelTtf.getValueAt(i, 1).toString();
                String soluongStr = modelTtf.getValueAt(i, 2).toString();
                String giaNhapStr = modelTtf.getValueAt(i, 3).toString();
                String giaBanStr = modelTtf.getValueAt(i, 4).toString();
                
                String donViTinh = modelTtf.getValueAt(i, 5).toString(); 
                String tenNCC = modelTtf.getValueAt(i, 6).toString();    
                String hanSDStr = modelTtf.getValueAt(i, 7).toString();  
                String tenKeThuoc = modelTtf.getValueAt(i, 8).toString();
                String thanhPhan = modelTtf.getValueAt(i, 9).toString();
                
                // Lấy đường dẫn Ảnh từ file Excel gốc (dòng i+1 trong file)
                Row rowExcel = sheet.getRow(i + 1); 
                String anh = (rowExcel != null) ? getCellValue(rowExcel.getCell(7)) : ""; // Cell H (Index 7)

                if (tenThuoc.isEmpty()) continue; 

                // --- Chuyển đổi kiểu dữ liệu ---
                int soLuong = Integer.parseInt(soluongStr);
                double giaNhap = Double.parseDouble(giaNhapStr);
                double giaBan = Double.parseDouble(giaBanStr);
                LocalDate hanSD = LocalDate.parse(hanSDStr); 

                // --- Logic nghiệp vụ (Tìm/Thêm NCC, Kệ) ---
                NhaCungCap ncc = (NhaCungCap) SocketClient.sendRequest(new Request(ActionType.GET_NHA_CUNG_CAP_BY_TEN, tenNCC)).getData();
                if (ncc == null) { /* ... (Thêm NCC) ... */ }
                KeThuoc ke = (KeThuoc) SocketClient.sendRequest(new Request(ActionType.GET_KE_THUOC_BY_TEN, tenKeThuoc)).getData();
                if (ke == null) { 
                     // Nếu kệ không tồn tại, ném ngoại lệ để dừng
                     throw new IllegalArgumentException("Kệ thuốc '" + tenKeThuoc + "' không tồn tại.");
                }
                
                // --- SINH MÃ THUỐC ---
                String maThuoc = String.format("T%02d", currentNumber);
                currentNumber++;

                Thuoc t = new Thuoc(
                    maThuoc, tenThuoc, giaNhap, giaBan, soLuong, hanSD,
                    thanhPhan, donViTinh, anh, ke, ncc, "Đang kinh doanh"
                );

                boolean ok = (boolean) SocketClient.sendRequest(new Request(ActionType.ADD_THUOC, t)).getData();
                if (ok) soThuocThem++;
            }
            workbook.close();
        }

        return soThuocThem;
    }
}