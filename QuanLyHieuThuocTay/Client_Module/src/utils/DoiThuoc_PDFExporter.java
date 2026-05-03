package utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import entity.CTPhieuDoiTra;
import entity.PhieuDoiTra;

import javax.swing.*;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DoiThuoc_PDFExporter {

    public static void exportPhieuDoiThuocToPdf(PhieuDoiTra pdt, List<CTPhieuDoiTra> dsCT, String filePath) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            BaseFont bf;
            try {
                bf = BaseFont.createFont("fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (Exception e) {
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            }
            
            Font fontTitle = new Font(bf, 18, Font.BOLD);
            Font fontBold = new Font(bf, 12, Font.BOLD);
            Font fontNormal = new Font(bf, 12, Font.NORMAL);
            Font fontItalic = new Font(bf, 11, Font.ITALIC);
            DecimalFormat df = new DecimalFormat("#,##0 VND");

            Paragraph title = new Paragraph("HIỆU THUỐC TÂY KHÁNH HƯNG", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            Paragraph address = new Paragraph("123 Đường ABC, Quận XYZ, TP.HCM", fontItalic);
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);
            
            document.add(new Paragraph(" ", fontNormal));
            Paragraph header = new Paragraph("PHIẾU ĐỔI THUỐC", fontTitle);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(new Paragraph(" ", fontNormal));

            document.add(new Paragraph("Mã phiếu: " + pdt.getMaPDT(), fontNormal));
            document.add(new Paragraph("Ngày lập: " + pdt.getNgayDoiTra().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), fontNormal));
            document.add(new Paragraph("Nhân viên: " + (pdt.getNhanVien() != null ? pdt.getNhanVien().getTenNV() : "N/A"), fontNormal));
            document.add(new Paragraph("Lý do: " + pdt.getLyDo(), fontNormal));
            document.add(new Paragraph("Từ hóa đơn: " + (pdt.getHoaDon() != null ? pdt.getHoaDon().getMaHD() : "N/A"), fontNormal));
            document.add(new Paragraph(" ", fontNormal));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 5, 2, 3, 3});

            String[] headers = {"STT", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(new java.awt.Color(230, 235, 245));
                table.addCell(cell);
            }

            int stt = 1;
            for (CTPhieuDoiTra ct : dsCT) {
                table.addCell(createCell(String.valueOf(stt++), fontNormal, Element.ALIGN_CENTER));
                table.addCell(createCell(ct.getThuoc().getTenThuoc(), fontNormal, Element.ALIGN_LEFT));
                table.addCell(createCell(String.valueOf(ct.getSoLuong()), fontNormal, Element.ALIGN_CENTER));
                table.addCell(createCell(df.format(ct.getThuoc().getGiaBan()), fontNormal, Element.ALIGN_RIGHT));
                table.addCell(createCell(df.format(ct.getSoLuong() * ct.getThuoc().getGiaBan() * 1.1), fontNormal, Element.ALIGN_RIGHT));
            }
            document.add(table);
            document.add(new Paragraph(" ", fontNormal));

            Paragraph note = new Paragraph("Lưu ý: Đổi thuốc không hoàn tiền chênh lệch.", fontItalic);
            document.add(note);

            document.close();
            JOptionPane.showMessageDialog(null, "Xuất phiếu đổi thuốc PDF thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất PDF: " + e.getMessage());
        }
    }

    private static PdfPCell createCell(String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        return cell;
    }
}
