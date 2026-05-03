package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;

public class ThongkeHDNam_GUI extends JDialog {

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
    private static final Font FONT_BUTTON_STANDARD = new Font("Segoe UI", Font.BOLD, 14);
    // --- Kết thúc định nghĩa Style ---

    private JTable table;
    private JLabel lblTitle;
    private JLabel lblTongSoHDValue;
    private JLabel lblTongTienValue;
    private JButton btnXemChiTiet;
    private JFrame parentFrame;
    private int currentYear;
    private String currentMaNV;
    private final DecimalFormat df = new DecimalFormat("#,##0 VND");

    public ThongkeHDNam_GUI(JFrame parent) {
        super(parent, "Thống Kê Chi Tiết Tháng", true);
        this.parentFrame = parent;

        initialize();
        setLocationRelativeTo(parent);
    }

    private void initialize() {
        setSize(800, 725);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null); // <<< THAY ĐỔI 1: SỬ DỤNG ABSOLUTE LAYOUT
        getContentPane().setBackground(COLOR_BACKGROUND_PRIMARY);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Tiêu đề ---
        lblTitle = new JLabel("");
        lblTitle.setFont(FONT_TITLE_SECTION);
        lblTitle.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(10, 10, 764, 36); // <<< THAY ĐỔI 2: setBounds
        getContentPane().add(lblTitle);

        // --- Bảng Dữ Liệu ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane.setBounds(10, 56, 764, 540); // <<< THAY ĐỔI 3: setBounds
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
                "Ngày", "Tổng Số Hóa Đơn", "Tổng Tiền Các Hóa Đơn"
            }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollPane.setViewportView(table);

        // --- Panel Tổng Kết và Nút (ở dưới) ---
        JPanel panelSouth = new JPanel(null); // <<< THAY ĐỔI 4: setLayout(null)
        panelSouth.setOpaque(false);
        panelSouth.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDER_LIGHT),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelSouth.setBounds(10, 607, 764, 68); // <<< THAY ĐỔI 5: setBounds
        getContentPane().add(panelSouth);

        // -- Panel con cho tổng kết --
        JPanel panelSummary = new JPanel(null); // <<< THAY ĐỔI 6: setLayout(null)
        panelSummary.setOpaque(false);
        panelSummary.setBounds(0, 0, 580, 68); // <<< THAY ĐỔI 7: setBounds
        panelSouth.add(panelSummary);

        JLabel lblTongSoHD = new JLabel("Tổng Số Hóa Đơn:");
        lblTongSoHD.setFont(FONT_LABEL_BOLD);
        lblTongSoHD.setForeground(COLOR_TEXT_DARK);
        lblTongSoHD.setBounds(10, 11, 140, 32); // <<< THAY ĐỔI 8: setBounds
        panelSummary.add(lblTongSoHD);

        lblTongSoHDValue = new JLabel("0");
        lblTongSoHDValue.setFont(FONT_LABEL_BOLD);
        lblTongSoHDValue.setForeground(COLOR_PRIMARY_BLUE);
        lblTongSoHDValue.setBounds(160, 11, 50, 32); // <<< THAY ĐỔI 9: setBounds
        panelSummary.add(lblTongSoHDValue);

        JLabel lblTongTien = new JLabel("Tổng Tiền Các Hóa Đơn:");
        lblTongTien.setFont(FONT_LABEL_BOLD);
        lblTongTien.setForeground(COLOR_TEXT_DARK);
        lblTongTien.setBounds(220, 11, 180, 32); // <<< THAY ĐỔI 10: setBounds
        panelSummary.add(lblTongTien);

        lblTongTienValue = new JLabel("0 VND");
        lblTongTienValue.setFont(FONT_SUMMARY_TOTAL);
        lblTongTienValue.setForeground(COLOR_SUCCESS_GREEN);
        lblTongTienValue.setBounds(410, 11, 200, 32); // <<< THAY ĐỔI 11: setBounds
        panelSummary.add(lblTongTienValue);

        // -- Nút Xem Chi Tiết --
        btnXemChiTiet = new JButton("Xem Chi Tiết Ngày");
        btnXemChiTiet.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnXemChiTiet, COLOR_PRIMARY_BLUE);
        btnXemChiTiet.setBounds(584, 11, 170, 45); // <<< THAY ĐỔI 12: setBounds
        panelSouth.add(btnXemChiTiet);

        // --- Thêm ActionListener cho nút Xem Chi Tiết ---
        btnXemChiTiet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xemChiTietNgayTrongThang();
            }
        });
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

     /**
     * Hàm để style các nút bấm
     */
    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    /**
     * Hàm load dữ liệu thống kê theo ngày của một tháng (có thể lọc theo NV)
     * @param thang Tháng cần xem (1-12)
     * @param nam Năm cần xem
     * @param maNVSelected Mã nhân viên cần lọc (hoặc null/rỗng)
     */
    public void loadData(int thang, int nam, String maNVSelected) {
        this.currentYear = nam;
        this.currentMaNV = maNVSelected;

        lblTitle.setText("Thống Kê Chi Tiết Tháng " + thang + "/" + nam);

        List<Object[]> dsThongKeNgay = (List<Object[]>) SocketClient.sendRequest(new Request(ActionType.GET_THONG_KE_THEO_NGAY_TRONG_THANG, new Object[]{thang, nam, maNVSelected})).getData();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        int tongSoHDThang = 0;
        double tongTienThang = 0;

        for (Object[] row : dsThongKeNgay) {
            int soHDNgay = (int) row[1];
            double tongTienNgay = (double) row[2];
            row[2] = df.format(tongTienNgay);
            model.addRow(row);

            tongSoHDThang += soHDNgay;
            tongTienThang += tongTienNgay;
        }

        lblTongSoHDValue.setText(String.valueOf(tongSoHDThang));
        lblTongTienValue.setText(df.format(tongTienThang));
    }

    /**
     * Xử lý sự kiện khi nhấn nút "Xem Chi Tiết Ngày"
     */
    private void xemChiTietNgayTrongThang() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ngày trong bảng để xem chi tiết!");
            return;
        }

        int ngay = (int) table.getValueAt(selectedRow, 0);
        String title = lblTitle.getText();
        String[] parts = title.split(" ");
        String thangNamPart = parts[parts.length - 1];
        String[] thangNam = thangNamPart.split("/");
        int thang = Integer.parseInt(thangNam[0]);
        int nam = this.currentYear;

        LocalDate selectedDate = LocalDate.of(nam, thang, ngay);

        ThongkeHDThang_GUI dialogChiTietNgay = new ThongkeHDThang_GUI(this.parentFrame);
        dialogChiTietNgay.loadData(selectedDate, this.currentMaNV);
        dialogChiTietNgay.setVisible(true);
    }
}