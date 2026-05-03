package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent; // Import mới
import java.awt.event.ActionListener; // Import mới
import java.io.FileOutputStream; // Import mới
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser; // Import mới
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Import mới
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel; // Import mới

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import org.apache.poi.ss.usermodel.Cell; // Import mới
import org.apache.poi.ss.usermodel.Row; // Import mới
import org.apache.poi.ss.usermodel.Sheet; // Import mới
import org.apache.poi.ss.usermodel.Workbook; // Import mới
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Import mới
import javax.swing.JButton;

public class ThongkeHDThang_GUI extends JDialog {

    // --- Định nghĩa Style giống TrangChu_GUI ---
    private static final Color COLOR_BACKGROUND_PRIMARY = new Color(240, 242, 245);
    private static final Color COLOR_CARD_BACKGROUND = Color.WHITE;
    private static final Color COLOR_PRIMARY_BLUE = new Color(0, 123, 255);
    private static final Color COLOR_SUCCESS_GREEN = new Color(40, 167, 69);
    private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
    private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
    private static final Color COLOR_BORDER_LIGHT = new Color(222, 226, 230);

    private static final Font FONT_TITLE_SECTION = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_TABLE_CELL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_SUMMARY_TOTAL = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FONT_BUTTON_STANDARD = new Font("Segoe UI", Font.BOLD, 14); // Font nút

    private JTable table;
    private JLabel lblTitle;
    private JLabel lblTongSoHDValue;
    private JLabel lblTongTienValue;
    private JButton btnNewButton;
    private JFrame parentFrame;
    private final DecimalFormat df = new DecimalFormat("#,##0 VND");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ThongkeHDThang_GUI(JFrame parent) {
        super(parent, "Thống Kê Chi Tiết", true);
        this.parentFrame = parent;

        initialize();
        setLocationRelativeTo(parent);
    }

    private void initialize() {
        setSize(1280, 710);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BACKGROUND_PRIMARY);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().setLayout(null); // <<< THAY ĐỔI 1: SỬ DỤNG ABSOLUTE LAYOUT

        // --- Tiêu đề ---
        lblTitle = new JLabel("Bảng Thống Kê Hóa Đơn Ngày ...");
        lblTitle.setFont(FONT_TITLE_SECTION);
        lblTitle.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(10, 10, 1244, 36); // <<< THAY ĐỔI 2: setBounds
        getContentPane().add(lblTitle);

        // --- Bảng Dữ Liệu ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane.setBounds(10, 56, 1244, 540); // <<< THAY ĐỔI 3: setBounds
        getContentPane().add(scrollPane);

        table = new JTable() {
            @Override
            public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã Hóa Đơn", "SĐT Khách Hàng", "Tên Khách Hàng", "Mã Nhân Viên", "Tên Nhân Viên", "Tổng Tiền"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollPane.setViewportView(table);

        // --- Panel Tổng Kết (ở dưới) ---
        JPanel panelSummary = new JPanel();
        panelSummary.setBackground(COLOR_CARD_BACKGROUND);
        panelSummary.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER_LIGHT),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelSummary.setBounds(10, 607, 1244, 54); // <<< THAY ĐỔI 4: setBounds
        getContentPane().add(panelSummary);
        panelSummary.setLayout(null); // <<< THAY ĐỔI 5: setLayout(null) cho panel con

        JLabel lblTongSoHD = new JLabel("Tổng Số Hóa Đơn:");
        lblTongSoHD.setFont(FONT_LABEL_BOLD);
        lblTongSoHD.setForeground(COLOR_TEXT_DARK);
        lblTongSoHD.setBounds(10, 11, 140, 32); // <<< THAY ĐỔI 6: setBounds
        panelSummary.add(lblTongSoHD);

        lblTongSoHDValue = new JLabel("0");
        lblTongSoHDValue.setFont(FONT_LABEL_BOLD);
        lblTongSoHDValue.setForeground(COLOR_PRIMARY_BLUE);
        lblTongSoHDValue.setBounds(160, 11, 50, 32); // <<< THAY ĐỔI 7: setBounds
        panelSummary.add(lblTongSoHDValue);

        JLabel lblTongTien = new JLabel("Tổng Tiền Các Hóa Đơn:");
        lblTongTien.setFont(FONT_LABEL_BOLD);
        lblTongTien.setForeground(COLOR_TEXT_DARK);
        lblTongTien.setBounds(220, 11, 180, 32); // <<< THAY ĐỔI 8: setBounds
        panelSummary.add(lblTongTien);

        lblTongTienValue = new JLabel("0 VND");
        lblTongTienValue.setFont(FONT_SUMMARY_TOTAL);
        lblTongTienValue.setForeground(COLOR_SUCCESS_GREEN);
        lblTongTienValue.setBounds(410, 11, 300, 32); // <<< THAY ĐỔI 9: setBounds
        panelSummary.add(lblTongTienValue);

        btnNewButton = new JButton("Xuất File");
        btnNewButton.setFont(FONT_BUTTON_STANDARD); // <<< THAY ĐỔI 10: Style nút
        styleButton(btnNewButton, COLOR_SUCCESS_GREEN); // <<< THAY ĐỔI 11: Style nút
        btnNewButton.setBounds(1108, 11, 126, 33); // <<< THAY ĐỔI 12: setBounds (giữ nguyên)
        panelSummary.add(btnNewButton);
        
        // ===== BẮT ĐẦU CODE THÊM MỚI (Mục 2) =====
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(parentFrame, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Lấy tên file từ tiêu đề
                String tenFile = lblTitle.getText().replace("Chi Tiết Hóa Đơn Ngày ", "ChiTietHD_Ngay_").replace("/", "-");
                exportTableToExcel(table, tenFile);
            }
        });
        // ===== KẾT THÚC CODE THÊM MỚI (Mục 2) =====
    }

    /**
     * Hàm này áp dụng style CHUNG cho một JTable đã được tạo
     */
    private void applyCommonTableStyling(JTable table) {
        table.setFont(FONT_TABLE_CELL);
        table.setRowHeight(30);
        table.setGridColor(COLOR_BORDER_LIGHT);
        table.setShowGrid(true);
        table.setSelectionBackground(COLOR_PRIMARY_BLUE);
        table.setSelectionForeground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HEADER);
        header.setBackground(new Color(230, 235, 240));
        header.setForeground(COLOR_TEXT_DARK);
        header.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
    }
    
    // ===== BẮT ĐẦU CODE THÊM MỚI (Mục 2) =====
    /**
     * Hàm để style các nút bấm
     */
    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Padding
    }
    
    /**
     * Hàm hỗ trợ xuất dữ liệu từ JTable ra file Excel (dùng Apache POI)
     * Lưu tự động vào thư mục 'xuat_excel' trong project.
     * @param tableToExport Bảng JTable chứa dữ liệu cần xuất
     * @param defaultFileName Tên file gợi ý (không bao gồm .xlsx) - SẼ BỊ GHI ĐÈ BỞI FORMAT MỚI
     */
    private void exportTableToExcel(JTable tableToExport, String defaultFileName) {
        // --- BẮT ĐẦU THAY ĐỔI ---
        // 1. Lấy ngày từ tiêu đề của dialog để đặt tên file
        String title = lblTitle.getText(); // Ví dụ: "Thống Kê Hóa Đơn Ngày 28/10/2025"
        String ngayXuatStr = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Mặc định ngày hiện tại
        try {
            // Cố gắng trích xuất ngày từ tiêu đề
            String datePart = title.substring(title.lastIndexOf(" ") + 1); // Lấy phần "dd/MM/yyyy"
            LocalDate date = LocalDate.parse(datePart, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            ngayXuatStr = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Format lại thành dd-MM-yyyy
        } catch (Exception ex) {
            System.err.println("Không thể trích xuất ngày từ tiêu đề, sử dụng ngày hiện tại.");
        }
        String finalFileName = "Thong Ke Hoa Don Ngay " + ngayXuatStr + ".xlsx";


        // 2. Tạo đường dẫn thư mục 'xuat_excel' trong thư mục gốc của project
        String projectDir = System.getProperty("user.dir");
        String exportDirPath = projectDir + java.io.File.separator + "xuat_excel";
        java.io.File exportDir = new java.io.File(exportDirPath);

        // 3. Tạo thư mục nếu chưa tồn tại
        if (!exportDir.exists()) {
            boolean created = exportDir.mkdirs();
            if (!created) {
                JOptionPane.showMessageDialog(this.parentFrame, // Dùng frame cha đã lưu
                        "Không thể tạo thư mục lưu file tại:\n" + exportDirPath,
                        "Lỗi Tạo Thư Mục", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 4. Tạo đường dẫn file đầy đủ
        String filePath = exportDirPath + java.io.File.separator + finalFileName;
        // --- KẾT THÚC THAY ĐỔI ---

        // 5. Bắt đầu quá trình tạo file Excel (Giữ nguyên try-catch)
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("ChiTietNgay_" + ngayXuatStr); // Tên sheet
            TableModel model = tableToExport.getModel();

            // 6. Tạo hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            for (int j = 0; j < model.getColumnCount(); j++) {
                Cell cell = headerRow.createCell(j);
                cell.setCellValue(model.getColumnName(j));
            }

            // 7. Ghi dữ liệu (Giữ nguyên logic xử lý kiểu dữ liệu và tiền tệ)
            for (int i = 0; i < model.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    Cell cell = dataRow.createCell(j);

                    if (j == 5 && value instanceof String) { // Cột 5 "Tổng Tiền"
                        try {
                            Number num = df.parse((String) value);
                            cell.setCellValue(num.doubleValue());
                        } catch (Exception pe) {
                            cell.setCellValue((String) value);
                        }
                    } else if (value instanceof String) {
                        cell.setCellValue((String) value);
                    } else if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Double) {
                        cell.setCellValue((Double) value);
                    } else if (value instanceof Boolean) {
                        cell.setCellValue((Boolean) value);
                    } else if (value != null) {
                        cell.setCellValue(value.toString());
                    } else {
                        cell.setCellValue("");
                    }
                }
            }

            // 8. Tự động điều chỉnh độ rộng cột
            for(int j = 0; j < model.getColumnCount(); j++) {
                sheet.autoSizeColumn(j);
            }

            // 9. Lưu file (Đường dẫn đã xác định)
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(this.parentFrame, // Dùng frame cha
                    "Xuất file Excel thành công!\nĐã lưu tại thư mục 'xuat_excel' trong project.", // Thông báo mới
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.parentFrame, // Dùng frame cha
                    "Có lỗi xảy ra khi xuất file Excel:\n" + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        // Bỏ hoàn toàn phần else
    }
    public void loadData(LocalDate selectedDate, String maNVSelected) {
        if (selectedDate == null) return;

        // Cập nhật tiêu đề
        lblTitle.setText("Chi Tiết Hóa Đơn Ngày " + selectedDate.format(dateFormatter));

        // Lấy dữ liệu từ DAO
        List<Object[]> dsHD = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_HOA_DON_CHI_TIET_TRONG_NGAY, new Object[]{selectedDate, maNVSelected})).getData();

        // Hiển thị lên bảng
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        double tongTienNgay = 0;

        for (Object[] row : dsHD) {
            double tongTienHD = (double) row[5]; // Lấy giá trị gốc (double)
            // Format tiền cho cột tổng tiền TRƯỚC KHI thêm vào model
            Object[] displayRow = new Object[]{
                row[0], // MaHD
                row[1], // SDT KH
                row[2], // Ten KH
                row[3], // Ma NV
                row[4], // Ten NV
                df.format(tongTienHD) // Format tiền thành String
            };
            model.addRow(displayRow); // Thêm dòng đã format vào bảng
            tongTienNgay += tongTienHD; // Cộng dồn giá trị gốc (double)
        }

        // Cập nhật label tổng kết
        lblTongSoHDValue.setText(String.valueOf(dsHD.size()));
        lblTongTienValue.setText(df.format(tongTienNgay)); // Format tổng tiền cuối cùng
    }
}