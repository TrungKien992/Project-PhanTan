package utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import entity.ChiTietHoaDon;
import entity.HoaDon;

import javax.swing.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Tiện ích xuất hóa đơn ra file PDF sử dụng thư viện OpenPDF.
 */
public class PdfExporter {

    public static void exportHoaDonToPdf(HoaDon hd, List<ChiTietHoaDon> dsCTHD, String filePath) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Cài đặt font tiếng Việt (Sử dụng Times New Roman có sẵn trong project)
            BaseFont bf;
            try {
                bf = BaseFont.createFont("fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (Exception e) {
                // Fallback nếu không tìm thấy file font
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            }
            
            Font fontTitle = new Font(bf, 18, Font.BOLD);
            Font fontBold = new Font(bf, 12, Font.BOLD);
            Font fontNormal = new Font(bf, 12, Font.NORMAL);
            Font fontItalic = new Font(bf, 11, Font.ITALIC);
            DecimalFormat df = new DecimalFormat("#,##0 VND");

            // --- TIÊU ĐỀ ---
            Paragraph title = new Paragraph("HIỆU THUỐC TÂY KHÁNH HƯNG", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            Paragraph address = new Paragraph("123 Đường ABC, Quận XYZ, TP.HCM", fontItalic);
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);
            
            document.add(new Paragraph(" ", fontNormal));
            Paragraph header = new Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" ", fontNormal));

            // --- THÔNG TIN CHUNG ---
            document.add(new Paragraph("Mã hóa đơn: " + hd.getMaHD(), fontNormal));
            document.add(new Paragraph("Ngày lập: " + hd.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            document.add(new Paragraph("Nhân viên: " + (hd.getNhanVien() != null ? hd.getNhanVien().getTenNV() : "N/A"), fontNormal));
            document.add(new Paragraph("Khách hàng: " + (hd.getKhachHang() != null ? hd.getKhachHang().getTenKH() : "Khách vãng lai"), fontNormal));
            document.add(new Paragraph(" ", fontNormal));

            // --- BẢNG CHI TIẾT ---
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 5, 2, 3, 3}); // Tỉ lệ độ rộng các cột

            String[] headers = {"STT", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(5);
                cell.setBackgroundColor(new java.awt.Color(230, 235, 245));
                table.addCell(cell);
            }

            int stt = 1;
            double tongTienHang = 0;
            for (ChiTietHoaDon ct : dsCTHD) {
                table.addCell(createCell(String.valueOf(stt++), fontNormal, Element.ALIGN_CENTER));
                table.addCell(createCell(ct.getThuoc().getTenThuoc(), fontNormal, Element.ALIGN_LEFT));
                table.addCell(createCell(String.valueOf(ct.getSoLuong()), fontNormal, Element.ALIGN_CENTER));
                table.addCell(createCell(df.format(ct.getThuoc().getGiaBan()), fontNormal, Element.ALIGN_RIGHT));
                
                double thanhTien = ct.tinhThanhTien();
                table.addCell(createCell(df.format(thanhTien), fontNormal, Element.ALIGN_RIGHT));
                tongTienHang += thanhTien;
            }
            document.add(table);
            document.add(new Paragraph(" ", fontNormal));

            // --- TỔNG KẾT ---
            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(40);
            summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // Thuế
            double thue = 0;
            if (hd.getThue() != null) {
                thue = tongTienHang * hd.getThue().getTiLe();
                summaryTable.addCell(new Phrase("Thuế (VAT " + (int)(hd.getThue().getTiLe() * 100) + "%):", fontNormal));
                PdfPCell cellThue = new PdfPCell(new Phrase(df.format(thue), fontNormal));
                cellThue.setBorder(Rectangle.NO_BORDER);
                cellThue.setHorizontalAlignment(Element.ALIGN_RIGHT);
                summaryTable.addCell(cellThue);
            }
            
            // Khuyến mãi
            double khuyenMaiGiam = 0;
            if (hd.getKhuyenMai() != null) {
                khuyenMaiGiam = tongTienHang * (hd.getKhuyenMai().getGiaTri() / 100);
                summaryTable.addCell(new Phrase("Khuyến mãi:", fontNormal));
                PdfPCell cellKM = new PdfPCell(new Phrase("-" + df.format(khuyenMaiGiam), fontNormal));
                cellKM.setBorder(Rectangle.NO_BORDER);
                cellKM.setHorizontalAlignment(Element.ALIGN_RIGHT);
                summaryTable.addCell(cellKM);
            }

            // Tổng cộng
            double tongCong = (tongTienHang - khuyenMaiGiam) + thue;
            summaryTable.addCell(new Phrase("TỔNG CỘNG:", fontBold));
            PdfPCell cellTotal = new PdfPCell(new Phrase(df.format(tongCong), fontBold));
            cellTotal.setBorder(Rectangle.NO_BORDER);
            cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            summaryTable.addCell(cellTotal);

            document.add(summaryTable);
            
            document.add(new Paragraph(" ", fontNormal));
            Paragraph footer = new Paragraph("Cảm ơn Quý khách. Hẹn gặp lại!", fontItalic);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            JOptionPane.showMessageDialog(null, "Xuất hóa đơn PDF thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất PDF: " + e.getMessage(), "Lỗi Hệ Thống", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static PdfPCell createCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        return cell;
    }
}
