package gui;

import javax.swing.*;
import javax.swing.SwingConstants;
import java.io.File;
import entity.Thuoc; 
import java.awt.*;
import java.text.DecimalFormat; 
import java.time.format.DateTimeFormatter;

public class ChiTietThuoc_GUI extends JDialog {

    // ========== THÊM HẰNG SỐ MÀU/FONT ==========
	private static final Color COLOR_BACKGROUND_PRIMARY = new Color(240, 242, 245);
	private static final Color COLOR_CARD_BACKGROUND = Color.WHITE;
	private static final Color COLOR_PRIMARY_BLUE = new Color(0, 123, 255);
	private static final Color COLOR_DANGER_RED = new Color(220, 53, 69);
	private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
	private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
	private static final Color COLOR_BORDER_LIGHT = new Color(222, 226, 230);
	private static final Font FONT_TITLE_SECTION = new Font("Segoe UI", Font.BOLD, 26);
	private static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 15);
	private static final Font FONT_VALUE_TEXT = new Font("Segoe UI", Font.ITALIC, 16); // Font cho giá trị
	private static final Font FONT_DETAIL_ITALIC = new Font("Segoe UI", Font.ITALIC, 15);
	// ==========================================================

    // các JLabel hiển thị thông tin thuốc (Giữ nguyên)
    public JLabel lblMaThuoc, lblTenThuoc, lblSoLuong, lblGiaNhap, lblGiaBan,
            lblDonViTinh, lblNhaCungCap, lblHanSuDung, lblTenKeThuoc;
    public JTextArea txtAreaThanhPhan; // Đổi thành JTextArea
    public JLabel lblAnh;

    public ChiTietThuoc_GUI(JFrame parent) {
        super(parent, "Chi Tiết Thuốc", true);
        initialize();
        setLocationRelativeTo(parent);
    }

    private void initialize() {
        setBounds(100, 100, 930, 600); // Giữ kích thước
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BACKGROUND_PRIMARY); // Nền dialog
        getContentPane().setLayout(null); // Giữ null layout

        // panel header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(COLOR_PRIMARY_BLUE); // Nền xanh
        panelHeader.setBounds(0, 0, 914, 70); // Điều chỉnh height
        getContentPane().add(panelHeader);
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15)); // Căn giữa

        JLabel lblTitle = new JLabel("Chi Tiết Thuốc");
        lblTitle.setFont(FONT_TITLE_SECTION); // Font
        lblTitle.setForeground(Color.WHITE); // Chữ trắng
        panelHeader.add(lblTitle);

        // panel nội dung
        JPanel panelContent = new JPanel();
        panelContent.setBackground(COLOR_CARD_BACKGROUND); // Nền trắng
        panelContent.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); // Viền
        panelContent.setBounds(10, 80, 894, 470); // Điều chỉnh
        getContentPane().add(panelContent);
        panelContent.setLayout(null);

        // panel chứa ảnh thuốc
        JPanel panelAnh = new JPanel();
        panelAnh.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); // Viền nhạt
        panelAnh.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền xám
        panelAnh.setBounds(35, 35, 330, 300);
        panelContent.add(panelAnh);
        panelAnh.setLayout(new BorderLayout(0, 0)); // Dùng BorderLayout

        lblAnh = new JLabel("Không có ảnh");
        lblAnh.setHorizontalAlignment(SwingConstants.CENTER);
        lblAnh.setFont(FONT_DETAIL_ITALIC); // Font italic
        lblAnh.setForeground(COLOR_TEXT_MUTED); // Màu nhạt
        panelAnh.add(lblAnh, BorderLayout.CENTER);

        // nhãn thông tin thuốc
        // (Sửa lại hàm createLabel để chỉ tạo 1 label title)
        createTitleLabel(panelContent, "Mã Thuốc :", 400, 35); // Vị trí X cột 1
        createTitleLabel(panelContent, "Tên Thuốc :", 400, 70);
        createTitleLabel(panelContent, "Số Lượng :", 400, 105);
        createTitleLabel(panelContent, "Giá Nhập :", 400, 140);
        createTitleLabel(panelContent, "Giá Bán :", 400, 175);
        createTitleLabel(panelContent, "Đơn Vị Tính :", 400, 210);
        createTitleLabel(panelContent, "Nhà Cung Cấp :", 400, 245);
        createTitleLabel(panelContent, "Hạn Sử Dụng :", 400, 280);
        createTitleLabel(panelContent, "Tên Kệ Thuốc :", 400, 315);
        createTitleLabel(panelContent, "Thành Phần :", 35, 350);

        // các JLabel giá trị
        lblMaThuoc = createValueLabel(panelContent, 550, 35); // Vị trí X cột 2
        lblTenThuoc = createValueLabel(panelContent, 550, 70);
        lblSoLuong = createValueLabel(panelContent, 550, 105);
        lblGiaNhap = createValueLabel(panelContent, 550, 140);
        lblGiaBan = createValueLabel(panelContent, 550, 175);
        lblDonViTinh = createValueLabel(panelContent, 550, 210);
        lblNhaCungCap = createValueLabel(panelContent, 550, 245);
        lblHanSuDung = createValueLabel(panelContent, 550, 280);
        lblTenKeThuoc = createValueLabel(panelContent, 550, 315);
        
        // Dùng JScrollPane cho Thành phần
        JScrollPane scrollThanhPhan = new JScrollPane();
        scrollThanhPhan.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollThanhPhan.setBounds(35, 380, 825, 75); // Vị trí
        panelContent.add(scrollThanhPhan);

        txtAreaThanhPhan = new JTextArea("...");
        txtAreaThanhPhan.setFont(FONT_VALUE_TEXT); // Font
        txtAreaThanhPhan.setForeground(COLOR_TEXT_DARK); // Màu chữ
        txtAreaThanhPhan.setEditable(false); // Không cho sửa
        txtAreaThanhPhan.setLineWrap(true);
        txtAreaThanhPhan.setWrapStyleWord(true);
        txtAreaThanhPhan.setBackground(COLOR_CARD_BACKGROUND); // Nền trắng
        txtAreaThanhPhan.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding
        scrollThanhPhan.setViewportView(txtAreaThanhPhan);
    }

    // Hàm helper tạo Label Tiêu đề
    private void createTitleLabel(JPanel parent, String text, int x, int y) {
        JLabel lblTitle = new JLabel(text);
        lblTitle.setFont(FONT_LABEL_BOLD); // Font đậm
        lblTitle.setForeground(COLOR_TEXT_DARK); // Màu chữ
        lblTitle.setBounds(x, y, 150, 24); // Tăng width
        parent.add(lblTitle);
    }

    // Hàm helper tạo Label Giá trị
    private JLabel createValueLabel(JPanel parent, int x, int y) {
        JLabel lblValue = new JLabel("...");
        lblValue.setFont(FONT_VALUE_TEXT); // Font giá trị
        lblValue.setForeground(COLOR_DANGER_RED); // Màu đỏ
        lblValue.setBounds(x, y, 320, 24); // Tăng width
        parent.add(lblValue);
        return lblValue;
    }

    // phương thức load dữ liệu từ entity Thuoc
    public void loadThuoc(Thuoc t) {
        if (t == null) {
            JOptionPane.showMessageDialog(this, "Không có thông tin thuốc để hiển thị.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Định dạng số tiền và ngày tháng
        DecimalFormat df = new DecimalFormat("#,##0 VND");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lblMaThuoc.setText(t.getMaThuoc());
        lblTenThuoc.setText(t.getTenThuoc());
        lblSoLuong.setText(String.valueOf(t.getSoLuong()));
        lblGiaNhap.setText(df.format(t.getGiaNhap())); // Format tiền
        lblGiaBan.setText(df.format(t.getGiaBan())); // Format tiền
        lblDonViTinh.setText(t.getDonViTinh());
        
        if(t.getNhaCungCap() != null)
        	lblNhaCungCap.setText(t.getNhaCungCap().getTenNhaCungCap());
        else
        	lblNhaCungCap.setText("N/A");

        if(t.getHanSuDung() != null)
        	lblHanSuDung.setText(t.getHanSuDung().format(dtf)); // Format ngày
        else
        	lblHanSuDung.setText("N/A");
        
        if(t.getKeThuoc() != null)
        	lblTenKeThuoc.setText(t.getKeThuoc().getLoaiKe());
        else
        	lblTenKeThuoc.setText("N/A");
        
        txtAreaThanhPhan.setText(t.getThanhPhan()); // Dùng JTextArea

        // hiển thị ảnh thực tế nếu có
        String anhPath = t.getAnh();
        if (anhPath != null && !anhPath.isEmpty()) {
            File file = new File(anhPath);
            if (file.exists() && !file.isDirectory()) {
                ImageIcon icon = new ImageIcon(anhPath);
                Image img = icon.getImage().getScaledInstance(330, 300, Image.SCALE_SMOOTH);
                lblAnh.setIcon(new ImageIcon(img));
                lblAnh.setText(null);
            } else {
                lblAnh.setIcon(null);
                lblAnh.setText("Ảnh không tồn tại");
                System.err.println("Không tìm thấy file ảnh: " + anhPath); // Debug
            }
        } else {
            lblAnh.setIcon(null);
            lblAnh.setText("Không có ảnh");
        }
    }
}