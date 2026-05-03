package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.NhaCungCap; // Cần import Entity này
import entity.Thuoc;
import gui.ChiTietThuoc_GUI; // Cần import GUI Chi Tiết
import gui.TrangChu_GUI;

public class ThuocSapHetHang_Controller implements ActionListener {

    private TrangChu_GUI trangChuGUI;
    private List<Thuoc> dsThuocGoc;

    public ThuocSapHetHang_Controller(TrangChu_GUI trangChuGUI) {
        this.trangChuGUI = trangChuGUI;
        this.dsThuocGoc = new ArrayList<>(); 

        taiLaiDuLieuGocVaHienThi();

        trangChuGUI.btn_tshhan_xuatfile.addActionListener(this);
        trangChuGUI.btn_tshhan_loc.addActionListener(this);
        trangChuGUI.btn_tshhan_lammoi.addActionListener(this);
 
        trangChuGUI.item_tshhan_xemchitiet.addActionListener(this);
        trangChuGUI.item_tshhan_xemncc.addActionListener(this);

        trangChuGUI.text_tshhan_timkiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                
                locVaHienThiLenBang();
            }
        });
    }

   
    private void taiLaiDuLieuGocVaHienThi() {
     
        this.dsThuocGoc = (List<Thuoc>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_THUOC, null)).getData();
       
        locVaHienThiLenBang();
    }
    
    
    private void locVaHienThiLenBang() {
        DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_tshhan.getModel();
        model.setRowCount(0); 

        int nguongTon = (Integer) trangChuGUI.spinner_tshhan_nguong.getValue();
        String tuKhoa = trangChuGUI.text_tshhan_timkiem.getText().trim().toLowerCase();

        for (Thuoc t : dsThuocGoc) { 
 
            boolean matchNguong = t.getSoLuong() <= nguongTon;

            boolean matchTimKiem = tuKhoa.isEmpty() || 
                                   t.getMaThuoc().toLowerCase().contains(tuKhoa) ||
                                   t.getTenThuoc().toLowerCase().contains(tuKhoa);

            if (matchNguong && matchTimKiem) {
                
                String tenNCC = (t.getNhaCungCap() != null) ? t.getNhaCungCap().getTenNhaCungCap() : "N/A";
                String tenKe = (t.getKeThuoc() != null) ? t.getKeThuoc().getLoaiKe() : "N/A";

                model.addRow(new Object[] {
                    t.getMaThuoc(),
                    t.getTenThuoc(),
                    t.getSoLuong(),
                    t.getDonViTinh(),
                    tenNCC,
                    tenKe
                });
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o.equals(trangChuGUI.btn_tshhan_xuatfile)) {
            xuLyXuatFile();
        } 
        else if (o.equals(trangChuGUI.btn_tshhan_loc)) {
       
            locVaHienThiLenBang();
        } 
        else if (o.equals(trangChuGUI.btn_tshhan_lammoi)) {
            xuLyLamMoi();
        }
        else if (o.equals(trangChuGUI.item_tshhan_xemchitiet)) {
            xuLyXemChiTiet();
        }
        else if (o.equals(trangChuGUI.item_tshhan_xemncc)) {
            xuLyXemNCC();
        }
    }

    private void xuLyLamMoi() {
        
        trangChuGUI.text_tshhan_timkiem.setText("");
        trangChuGUI.spinner_tshhan_nguong.setValue(20); 
        
        taiLaiDuLieuGocVaHienThi();
    }

    
    private void xuLyXemChiTiet() {
        int viewRow = trangChuGUI.table_tshhan.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn một thuốc để xem chi tiết.");
            return;
        }
        
        int modelRow = trangChuGUI.table_tshhan.convertRowIndexToModel(viewRow);
        String maThuoc = (String) trangChuGUI.table_tshhan.getModel().getValueAt(modelRow, 0);

        Thuoc t = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
        if (t != null) {
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(trangChuGUI);
            ChiTietThuoc_GUI chitiet = new ChiTietThuoc_GUI(parentFrame);
            chitiet.loadThuoc(t);
            chitiet.setVisible(true);
        }
    }
    
    private void xuLyXemNCC() {
        int viewRow = trangChuGUI.table_tshhan.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(trangChuGUI, "Vui lòng chọn một thuốc để xem nhà cung cấp.");
            return;
        }

        int modelRow = trangChuGUI.table_tshhan.convertRowIndexToModel(viewRow);
        String maThuoc = (String) trangChuGUI.table_tshhan.getModel().getValueAt(modelRow, 0);

        Thuoc t = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();
        if (t == null || t.getNhaCungCap() == null) {
            JOptionPane.showMessageDialog(trangChuGUI, "Không tìm thấy thông tin nhà cung cấp cho thuốc này.");
            return;
        }

        String maNCC = t.getNhaCungCap().getMaNhaCungCap();
        NhaCungCap ncc = (NhaCungCap) SocketClient.sendRequest(new Request(ActionType.GET_NHA_CUNG_CAP_BY_MA, maNCC)).getData();

        if (ncc == null) {
            JOptionPane.showMessageDialog(trangChuGUI, "Không tìm thấy thông tin chi tiết cho NCC có mã: " + maNCC);
            return;
        }

        String message = String.format(
            "<html><b>THÔNG TIN NHÀ CUNG CẤP</b><br>" +
            "<hr>" +
            "<b>Tên:</b> %s<br>" +
            "<b>SĐT:</b> %s<br>" +
            "<b>Email:</b> %s<br>" +
            "<b>Địa chỉ:</b> %s</html>",
            ncc.getTenNhaCungCap(),
            ncc.getSoDienThoai().isEmpty() ? "Chưa cập nhật" : ncc.getSoDienThoai(),
            ncc.getEmail().isEmpty() ? "Chưa cập nhật" : ncc.getEmail(),
            ncc.getDiaChi().isEmpty() ? "Chưa cập nhật" : ncc.getDiaChi()
        );
        
        JOptionPane.showMessageDialog(trangChuGUI, message, "Thông tin Nhà Cung Cấp", JOptionPane.INFORMATION_MESSAGE);
    }

    private void xuLyXuatFile() {
        if (trangChuGUI.table_tshhan.getRowCount() == 0) {
            JOptionPane.showMessageDialog(trangChuGUI, "Không có dữ liệu để xuất file.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new File("DanhSachThuocSapHetHang.xlsx")); 
        fileChooser.setFileFilter(new FileNameExtensionFilter("File Excel (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(trangChuGUI);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                fileToSave = new File(filePath + ".xlsx");
            }

            xuatFileExcel(fileToSave);
        }
    }

    private void xuatFileExcel(File file) {
        try (Workbook workbook = new XSSFWorkbook(); 
             FileOutputStream fos = new FileOutputStream(file)) {

            Sheet sheet = workbook.createSheet("ThuocSapHetHang");
            DefaultTableModel model = (DefaultTableModel) trangChuGUI.table_tshhan.getModel();

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }

            for (int i = 0; i < model.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1); 
                for (int j = 0; j < model.getColumnCount(); j++) {
                 
                    Object value = model.getValueAt(i, j); 
                    Cell cell = dataRow.createCell(j);

                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

          
            for (int i = 0; i < model.getColumnCount(); i++) {
                sheet.autoSizeColumn(i);
            }

            
            workbook.write(fos);
            
            JOptionPane.showMessageDialog(trangChuGUI, "Xuất file thành công!\nĐã lưu tại: " + file.getAbsolutePath(), "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(trangChuGUI, "Có lỗi xảy ra khi xuất file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
