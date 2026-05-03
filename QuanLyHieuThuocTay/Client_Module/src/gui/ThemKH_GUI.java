package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import controller.KhachHang_Controller; // Giữ lại import controller nếu cần
import controller.ThemKH_Controller;
import java.awt.*;
import java.awt.event.*;

public class ThemKH_GUI extends JDialog {

    // Khai báo public giữ nguyên
    public JTextField txtMaKH;
    public JTextField txtTenKH;
    public JTextField txtSDT;
    public JTextField txtDiaChi;
    public JButton btnLamMoi;
    public JButton btnThem;

    // ========== THÊM HẰNG SỐ MÀU/FONT VÀ HÀM TRỢ GIÚP ==========
    private static final Color COLOR_BACKGROUND_PRIMARY = new Color(240, 242, 245);
	private static final Color COLOR_CARD_BACKGROUND = Color.WHITE;
	private static final Color COLOR_PRIMARY_BLUE = new Color(0, 123, 255);
	private static final Color COLOR_SUCCESS_GREEN = new Color(40, 167, 69);
	private static final Color COLOR_DANGER_RED = new Color(220, 53, 69);
	private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
	private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
	private static final Color COLOR_BORDER_LIGHT = new Color(222, 226, 230);
	private static final Font FONT_TITLE_MAIN = new Font("Segoe UI", Font.BOLD, 38);
	private static final Font FONT_TITLE_SECTION = new Font("Segoe UI", Font.BOLD, 26);
	private static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 15);
	private static final Font FONT_TEXT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
	private static final Font FONT_BUTTON_STANDARD = new Font("Segoe UI", Font.BOLD, 14);
	private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 15);
	private static final Font FONT_TABLE_CELL = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font FONT_SUMMARY_TOTAL = new Font("Segoe UI", Font.BOLD, 20);
	private static final Font FONT_DETAIL_ITALIC = new Font("Segoe UI", Font.ITALIC, 15);

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15)); // Tăng padding
    }
    // ==========================================================

    public ThemKH_GUI(JFrame parent) {
        super(parent, "Thêm Khách Hàng", true);
        initialize();
        setLocationRelativeTo(parent);
    }

    private void initialize() {
        setBounds(100, 100, 930, 600);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BACKGROUND_PRIMARY); // Nền dialog
        getContentPane().setLayout(null); // Giữ null layout

        // --- Panel Tiêu đề ---
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 102, 102)); // Màu gốc
        panel.setBounds(0, 0, 914, 80); // Điều chỉnh height
        getContentPane().add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15)); // Căn giữa

        JLabel lblNewLabel = new JLabel("THÊM KHÁCH HÀNG MỚI"); // Text rõ hơn
        lblNewLabel.setFont(FONT_TITLE_SECTION); // Font
        lblNewLabel.setForeground(Color.WHITE); // Màu chữ
        panel.add(lblNewLabel);

        // --- Panel Nhập liệu ---
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(COLOR_CARD_BACKGROUND); // Nền trắng
        panel_1.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Khách Hàng", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        )); // Viền
        panel_1.setBounds(10, 90, 894, 460); // Điều chỉnh vị trí/kích thước
        getContentPane().add(panel_1);
        panel_1.setLayout(null);

        // Định nghĩa vị trí/kích thước
        int labelX_themkh = 50;
        int inputX_themkh = 220;
        int inputWidth_themkh = 620; // Width lớn hơn
        int startY_themkh = 50;
        int height_themkh = 33;
        int vGap_themkh = 40; // Khoảng cách lớn hơn

        JLabel lblMaKH = new JLabel("Mã Khách Hàng :");
        lblMaKH.setFont(FONT_LABEL_BOLD); // Font
        lblMaKH.setForeground(COLOR_TEXT_DARK); // Màu
        lblMaKH.setBounds(labelX_themkh, startY_themkh, 160, height_themkh); // Tăng width
        panel_1.add(lblMaKH);

        txtMaKH = new JTextField();
        txtMaKH.setFont(FONT_LABEL_BOLD); // Font đậm
        txtMaKH.setForeground(COLOR_DANGER_RED); // Màu đỏ
        txtMaKH.setEditable(false);
        txtMaKH.setBorder(null); // Bỏ viền
        txtMaKH.setBackground(COLOR_CARD_BACKGROUND); // Nền giống panel
        txtMaKH.setBounds(inputX_themkh, startY_themkh, inputWidth_themkh, height_themkh);
        panel_1.add(txtMaKH);

        JLabel lblTenKH = new JLabel("Tên Khách Hàng :");
        lblTenKH.setFont(FONT_LABEL_BOLD);
        lblTenKH.setForeground(COLOR_TEXT_DARK);
        lblTenKH.setBounds(labelX_themkh, startY_themkh + height_themkh + vGap_themkh, 160, height_themkh);
        panel_1.add(lblTenKH);

        txtTenKH = new JTextField();
        txtTenKH.setBounds(inputX_themkh, startY_themkh + height_themkh + vGap_themkh, inputWidth_themkh, height_themkh);
        txtTenKH.setFont(FONT_TEXT_FIELD); // Font
        panel_1.add(txtTenKH);

        JLabel lblSDT = new JLabel("Số Điện Thoại :");
        lblSDT.setFont(FONT_LABEL_BOLD);
        lblSDT.setForeground(COLOR_TEXT_DARK);
        lblSDT.setBounds(labelX_themkh, startY_themkh + 2 * (height_themkh + vGap_themkh), 160, height_themkh);
        panel_1.add(lblSDT);

        txtSDT = new JTextField();
        txtSDT.setBounds(inputX_themkh, startY_themkh + 2 * (height_themkh + vGap_themkh), inputWidth_themkh, height_themkh);
        txtSDT.setFont(FONT_TEXT_FIELD);
        panel_1.add(txtSDT);

        JLabel lblDiaChi = new JLabel("Địa Chỉ :");
        lblDiaChi.setFont(FONT_LABEL_BOLD);
        lblDiaChi.setForeground(COLOR_TEXT_DARK);
        lblDiaChi.setBounds(labelX_themkh, startY_themkh + 3 * (height_themkh + vGap_themkh), 160, height_themkh);
        panel_1.add(lblDiaChi);

        txtDiaChi = new JTextField();
        txtDiaChi.setBounds(inputX_themkh, startY_themkh + 3 * (height_themkh + vGap_themkh), inputWidth_themkh, height_themkh);
        txtDiaChi.setFont(FONT_TEXT_FIELD);
        panel_1.add(txtDiaChi);

        // --- Nút chức năng ---
        int buttonY = startY_themkh + 4 * (height_themkh + vGap_themkh) + 30; // Vị trí Y nút
        int buttonWidth = 174;
        int buttonHeight = 50; // Tăng chiều cao nút
        int buttonGap = 150; // Khoảng cách nút
        int totalButtonWidth = 2 * buttonWidth + buttonGap;
        int buttonStartX = (panel_1.getWidth() - totalButtonWidth) / 2; // Căn giữa 2 nút

        btnLamMoi = new JButton("Làm Mới");
        btnLamMoi.setFont(FONT_BUTTON_STANDARD); // Font
        styleButton(btnLamMoi, COLOR_TEXT_MUTED); // Style
        btnLamMoi.setBounds(buttonStartX, buttonY, buttonWidth, buttonHeight);
        panel_1.add(btnLamMoi);

        btnThem = new JButton("Thêm");
        btnThem.setFont(FONT_BUTTON_STANDARD); // Font
        styleButton(btnThem, COLOR_SUCCESS_GREEN); // Style
        btnThem.setBounds(buttonStartX + buttonWidth + buttonGap, buttonY, buttonWidth, buttonHeight);
        panel_1.add(btnThem);
    }
}