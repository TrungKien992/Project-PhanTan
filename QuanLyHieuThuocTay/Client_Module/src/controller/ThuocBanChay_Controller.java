package controller;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.Thuoc;

public class ThuocBanChay_Controller {

    public ThuocBanChay_Controller() {
    }

    public List<Object[]> getDanhSachThuocBanChay() {
        // ... (Giữ nguyên code lấy danh sách cũ của đại ca ở đây) ...
        List<Object[]> danhSachHienThi = new ArrayList<>();
        Map<String, Integer> thongKeSoLuong = (Map<String, Integer>) SocketClient.sendRequest(new Request(ActionType.GET_THONG_KE_THUOC_BAN_CHAY, null)).getData();

        for (Map.Entry<String, Integer> entry : thongKeSoLuong.entrySet()) {
            String maThuoc = entry.getKey();
            int soLuongBan = entry.getValue();
            Thuoc thuoc = (Thuoc) SocketClient.sendRequest(new Request(ActionType.GET_THUOC_BY_MA, maThuoc)).getData();

            if (thuoc != null) {
                // Thứ tự mảng Object[]: 
                // [0]Mã, [1]Tên, [2]SL Bán, [3]Giá, [4]ĐVT, [5]NCC, [6]HSD, [7]Kệ
                Object[] rowData = {
                    maThuoc,
                    thuoc.getTenThuoc(),
                    soLuongBan, 
                    thuoc.getGiaBan(),
                    thuoc.getDonViTinh(),
                    (thuoc.getNhaCungCap() != null) ? thuoc.getNhaCungCap().getTenNhaCungCap() : "N/A",
                    thuoc.getHanSuDung(), 
                    (thuoc.getKeThuoc() != null) ? thuoc.getKeThuoc().getLoaiKe() : "N/A"
                };
                danhSachHienThi.add(rowData);
            }
        }
        return danhSachHienThi;
    }

    // --- HÀM MỚI: VIẾT Y HỆT PHONG CÁCH FILE MẪU ---
    public void exportToExcel(List<Object[]> ds, String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Thống kê thuốc bán chạy");

            // Tạo tiêu đề cột
            String[] headers = {
                "Mã Thuốc", "Tên Thuốc", "Số Lượng Bán", "Giá Bán", 
                "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Vị Trí Kệ"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                
                // Style cho đẹp tí (tùy chọn, giống file mẫu thì để mặc định cũng được)
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Ghi dữ liệu
            int rowNum = 1;
            for (Object[] rowData : ds) {
                Row row = sheet.createRow(rowNum++);

                // Ép kiểu dữ liệu từ Object[] ra để ghi vào Excel
                row.createCell(0).setCellValue(rowData[0] != null ? rowData[0].toString() : ""); // Mã
                row.createCell(1).setCellValue(rowData[1] != null ? rowData[1].toString() : ""); // Tên
                
                // Số lượng bán (Integer)
                if (rowData[2] instanceof Integer) {
                    row.createCell(2).setCellValue((Integer) rowData[2]);
                } else {
                    row.createCell(2).setCellValue(0);
                }

                // Giá bán (Double)
                if (rowData[3] instanceof Double) {
                    row.createCell(3).setCellValue((Double) rowData[3]);
                } else {
                    row.createCell(3).setCellValue(0.0);
                }

                row.createCell(4).setCellValue(rowData[4] != null ? rowData[4].toString() : ""); // ĐVT
                row.createCell(5).setCellValue(rowData[5] != null ? rowData[5].toString() : ""); // NCC
                row.createCell(6).setCellValue(rowData[6] != null ? rowData[6].toString() : ""); // HSD
                row.createCell(7).setCellValue(rowData[7] != null ? rowData[7].toString() : ""); // Kệ
            }

            // Auto resize cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Xuất file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                workbook.write(out);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất file Excel: " + e.getMessage());
        }
    }
}