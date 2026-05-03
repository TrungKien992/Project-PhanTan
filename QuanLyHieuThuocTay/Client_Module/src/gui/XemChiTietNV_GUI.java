package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.time.format.DateTimeFormatter;
import entity.NhanVien;

public class XemChiTietNV_GUI extends JDialog {

    private JTextField txtMaNV_XCTNV;
    private JTextField txtTenNV_XCTNV;
    private JTextField txtNgaySinh_XCTNV;
    private JTextField txtSDT_XCTNV;
    private JTextField txtGioiTinh_XCTNV;
    private JTextField txtChucVu_XCTNV;
    private JTextField txtDiaChi_XCTNV;
    private JTextField txtTaiKhoan_XCTNV;
    private JLabel lblAnhNV_XCTNV;

    // === Màu & Font ===
    private static final Color COLOR_BACKGROUND_PRIMARY = new Color(240, 242, 245);
    private static final Color COLOR_CARD_BACKGROUND = Color.WHITE;
    private static final Color COLOR_PRIMARY_BLUE = new Color(0, 123, 255);
    private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
    private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
    private static final Color COLOR_BORDER_LIGHT = new Color(222, 226, 230);
    private static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_TEXT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_DETAIL_ITALIC = new Font("Segoe UI", Font.ITALIC, 15);

    public XemChiTietNV_GUI(JFrame parent, NhanVien nv) {
        super(parent, "Xem Chi Tiết Nhân Viên", true);
        initialize();
        hienThiThongTinNhanVien(nv);
        setLocationRelativeTo(parent);
    }

    private void initialize() {
        setBounds(100, 100, 930, 600);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BACKGROUND_PRIMARY);
        getContentPane().setLayout(null);

        // === Panel Ảnh ===
        JPanel panelAnhNV_XCTNV = new JPanel();
        panelAnhNV_XCTNV.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        panelAnhNV_XCTNV.setBackground(COLOR_CARD_BACKGROUND);
        panelAnhNV_XCTNV.setBounds(40, 40, 320, 480);
        getContentPane().add(panelAnhNV_XCTNV);
        panelAnhNV_XCTNV.setLayout(new BorderLayout(0, 0));

        lblAnhNV_XCTNV = new JLabel("Không tìm thấy ảnh", SwingConstants.CENTER);
        lblAnhNV_XCTNV.setFont(FONT_DETAIL_ITALIC);
        lblAnhNV_XCTNV.setForeground(COLOR_TEXT_MUTED);
        panelAnhNV_XCTNV.add(lblAnhNV_XCTNV, BorderLayout.CENTER);

        // === Panel Thông tin ===
        JPanel panelTTNV_XCTNV = new JPanel();
        panelTTNV_XCTNV.setBackground(COLOR_CARD_BACKGROUND);
        panelTTNV_XCTNV.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                "Thông tin nhân viên",
                TitledBorder.LEADING, TitledBorder.TOP,
                FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        panelTTNV_XCTNV.setBounds(380, 40, 510, 480);
        getContentPane().add(panelTTNV_XCTNV);
        panelTTNV_XCTNV.setLayout(null);

        int labelX = 20;
        int valueX = 160;
        int width = 320;
        int y = 30;
        int h = 30;
        int gap = 10;

        // Các nhãn và trường
        panelTTNV_XCTNV.add(createLabel("Mã nhân viên:", labelX, y));
        txtMaNV_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Tên nhân viên:", labelX, y));
        txtTenNV_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Ngày sinh:", labelX, y));
        txtNgaySinh_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Số điện thoại:", labelX, y));
        txtSDT_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Giới tính:", labelX, y));
        txtGioiTinh_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Chức vụ:", labelX, y));
        txtChucVu_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Địa chỉ:", labelX, y));
        txtDiaChi_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);

        y += h + gap;
        panelTTNV_XCTNV.add(createLabel("Tài khoản:", labelX, y));
        txtTaiKhoan_XCTNV = createTextField(valueX, y, width, h, panelTTNV_XCTNV);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL_BOLD);
        lbl.setForeground(COLOR_TEXT_DARK);
        lbl.setBounds(x, y, 131, 30);
        return lbl;
    }

    private JTextField createTextField(int x, int y, int w, int h, JPanel parent) {
        JTextField txt = new JTextField();
        txt.setFont(FONT_TEXT_FIELD);
        txt.setEditable(false);
        txt.setBorder(null);
        txt.setBackground(COLOR_CARD_BACKGROUND);
        txt.setBounds(x, y, w, h);
        parent.add(txt);
        return txt;
    }

    private void hienThiThongTinNhanVien(NhanVien nv) {
        if (nv == null) return;

        txtMaNV_XCTNV.setText(nv.getMaNV());
        txtTenNV_XCTNV.setText(nv.getTenNV());
        txtNgaySinh_XCTNV.setText(
                nv.getNgaySinh() != null ? nv.getNgaySinh().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
        );
        txtSDT_XCTNV.setText(nv.getSoDienThoai());
        txtGioiTinh_XCTNV.setText(nv.getGioiTinh());
        txtChucVu_XCTNV.setText(nv.getChucVu() != null ? nv.getChucVu().getTenChucVu() : "");
        txtDiaChi_XCTNV.setText(nv.getDiaChi());
        txtTaiKhoan_XCTNV.setText(nv.getTaiKhoan() != null ? nv.getTaiKhoan().getTenTK() : "");

        // === Load ảnh từ cột "ảnh nhân viên" ===
        lblAnhNV_XCTNV.setText("Không tìm thấy ảnh");
        lblAnhNV_XCTNV.setIcon(null);

     // === Load ảnh từ cột "ảnh nhân viên" ===
        if (nv.getAnh() != null && !nv.getAnh().trim().isEmpty()) {
            String duongDanAnh = nv.getAnh().trim();

            // Xử lý đường dẫn
            if (duongDanAnh.startsWith("/")) {
                duongDanAnh = duongDanAnh.substring(1);
            }
            if (!duongDanAnh.toLowerCase().startsWith("img") && !duongDanAnh.contains(":")) {
                duongDanAnh = "img/" + duongDanAnh;
            }

            File file = new File(duongDanAnh);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());

                // === Giải pháp quan trọng: chỉ scale khi JLabel đã có kích thước ===
                SwingUtilities.invokeLater(() -> {
                    int w = lblAnhNV_XCTNV.getWidth();
                    int h = lblAnhNV_XCTNV.getHeight();

                    if (w > 0 && h > 0) {
                        Image scaledImg = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                        lblAnhNV_XCTNV.setIcon(new ImageIcon(scaledImg));
                        lblAnhNV_XCTNV.setText(null);
                    } else {
                        lblAnhNV_XCTNV.setIcon(icon); // fallback: gán ảnh gốc nếu chưa có size
                        lblAnhNV_XCTNV.setText(null);
                    }
                });
            } else {
                lblAnhNV_XCTNV.setText("Không tìm thấy ảnh");
                lblAnhNV_XCTNV.setIcon(null);
            }
        } else {
            lblAnhNV_XCTNV.setText("Chưa có ảnh");
            lblAnhNV_XCTNV.setIcon(null);
        }

    }

}
