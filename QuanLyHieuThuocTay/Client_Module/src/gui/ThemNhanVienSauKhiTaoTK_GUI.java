package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.ChucVu;
import entity.NhanVien;
import entity.TaiKhoan;

public class ThemNhanVienSauKhiTaoTK_GUI extends JDialog {

    // ========== HẰNG SỐ MÀU/FONT (Giữ nguyên style của Đại Ca) ==========
    private static final Color COLOR_BACKGROUND_PRIMARY = new Color(240, 242, 245);
    private static final Color COLOR_CARD_BACKGROUND = Color.WHITE;
    private static final Color COLOR_PRIMARY_BLUE = new Color(0, 123, 255);
    private static final Color COLOR_SUCCESS_GREEN = new Color(40, 167, 69);
    private static final Color COLOR_DANGER_RED = new Color(220, 53, 69);
    private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
    private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
    private static final Color COLOR_BORDER_LIGHT = new Color(222, 226, 230);
    private static final Font FONT_TITLE_SECTION = new Font("Segoe UI", Font.BOLD, 26);
    private static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_TEXT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_BUTTON_STANDARD = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONT_DETAIL_ITALIC = new Font("Segoe UI", Font.ITALIC, 15);
    // ==========================================================

    private JTextField txtMaNV_Dialog;
    private JTextField txtTenNV_Dialog;
    private JDateChooser dateNgaySinh_Dialog;
    private JComboBox<String> cboGioiTinh_Dialog;
    private JTextField txtSDT_Dialog;
    private JTextField txtTinh_Dialog;
    private JTextField txtHuyen_Dialog;
    private JLabel lblAnhNV_Dialog;
    
    private String duongDanAnh_Dialog = null;
    private TaiKhoan taiKhoanMoi; // Tài khoản này ĐÃ ĐƯỢC LƯU ở Controller
    private String maNVMoi;
        // Biến cờ để báo kết quả về Controller
    private boolean isSuccess = false;

    public ThemNhanVienSauKhiTaoTK_GUI(JFrame parent, TaiKhoan tkMoi, String maNV) {
        super(parent, "Thêm Thông Tin Nhân Viên Tương Ứng", true); // Modal = true
        this.taiKhoanMoi = tkMoi;
        this.maNVMoi = maNV;
        
        initialize(); // Gọi hàm khởi tạo giao diện
        setLocationRelativeTo(parent);
    }

    // Hàm getter để Controller kiểm tra kết quả
    public boolean isSuccess() {
        return isSuccess;
    }

    private void initialize() {
        setSize(900, 650);
        // Khi đóng form bằng nút X, mặc định isSuccess vẫn là false -> Controller sẽ xóa TK
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BACKGROUND_PRIMARY);
        getContentPane().setLayout(null);

        JLabel lblTitleDialog = new JLabel("THÊM THÔNG TIN NHÂN VIÊN");
        lblTitleDialog.setFont(FONT_TITLE_SECTION);
        lblTitleDialog.setForeground(COLOR_PRIMARY_BLUE);
        lblTitleDialog.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitleDialog.setBounds(0, 15, 884, 40);
        getContentPane().add(lblTitleDialog);

        // --- PANEL ẢNH ---
        JPanel pnlAnhDialog = new JPanel(new BorderLayout());
        pnlAnhDialog.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        pnlAnhDialog.setBackground(COLOR_BACKGROUND_PRIMARY);
        pnlAnhDialog.setBounds(30, 70, 250, 300);
        getContentPane().add(pnlAnhDialog);

        lblAnhNV_Dialog = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblAnhNV_Dialog.setFont(FONT_DETAIL_ITALIC);
        lblAnhNV_Dialog.setForeground(COLOR_TEXT_MUTED);
        pnlAnhDialog.add(lblAnhNV_Dialog, BorderLayout.CENTER);

        JButton btnChonAnhDialog = new JButton("Chọn Ảnh");
        styleButton(btnChonAnhDialog, COLOR_TEXT_MUTED);
        btnChonAnhDialog.setFont(FONT_BUTTON_STANDARD);
        btnChonAnhDialog.setBounds(80, 380, 150, 35);
        getContentPane().add(btnChonAnhDialog);

        btnChonAnhDialog.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                duongDanAnh_Dialog = file.getAbsolutePath();
                try {
                    ImageIcon icon = new ImageIcon(duongDanAnh_Dialog);
                    if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        Image img = icon.getImage().getScaledInstance(lblAnhNV_Dialog.getWidth(), lblAnhNV_Dialog.getHeight(), Image.SCALE_SMOOTH);
                        lblAnhNV_Dialog.setIcon(new ImageIcon(img));
                        lblAnhNV_Dialog.setText(null);
                    } else {
                        throw new Exception("File ảnh không hợp lệ");
                    }
                } catch(Exception ex) {
                    lblAnhNV_Dialog.setText("Lỗi ảnh");
                    lblAnhNV_Dialog.setIcon(null);
                    JOptionPane.showMessageDialog(this, "Không thể tải ảnh: " + ex.getMessage(), "Lỗi Ảnh", JOptionPane.ERROR_MESSAGE);
                    duongDanAnh_Dialog = null;
                }
            }
        });

        // --- PANEL THÔNG TIN ---
        JPanel pnlInfoDialog = new JPanel(null);
        pnlInfoDialog.setBackground(COLOR_CARD_BACKGROUND);
        pnlInfoDialog.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
             "Thông Tin Bắt Buộc", TitledBorder.LEADING, TitledBorder.TOP,
             FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlInfoDialog.setBounds(300, 70, 570, 480);
        getContentPane().add(pnlInfoDialog);

        int labelX_d = 20;
        int inputX_d = 160;
        int inputW_d = 380;
        int startY_d = 20;
        int height_d = 30;
        int vGap_d = 15;

        // Mã NV
        JLabel lblMaNVDialog = new JLabel("Mã Nhân Viên:");
        lblMaNVDialog.setFont(FONT_LABEL_BOLD);
        lblMaNVDialog.setBounds(labelX_d, startY_d, 130, height_d);
        pnlInfoDialog.add(lblMaNVDialog);
        txtMaNV_Dialog = new JTextField(maNVMoi);
        txtMaNV_Dialog.setFont(FONT_LABEL_BOLD);
        txtMaNV_Dialog.setEditable(false);
        txtMaNV_Dialog.setBackground(COLOR_CARD_BACKGROUND);
        txtMaNV_Dialog.setBorder(null);
        txtMaNV_Dialog.setForeground(COLOR_DANGER_RED);
        txtMaNV_Dialog.setBounds(inputX_d, startY_d, inputW_d, height_d);
        pnlInfoDialog.add(txtMaNV_Dialog);

        // Tài khoản (Read-only)
        JLabel lblTenTKDialog = new JLabel("Tài Khoản:");
        lblTenTKDialog.setFont(FONT_LABEL_BOLD);
        lblTenTKDialog.setBounds(labelX_d, startY_d + height_d + vGap_d, 130, height_d);
        pnlInfoDialog.add(lblTenTKDialog);
        JTextField txtTenTKDialog = new JTextField(taiKhoanMoi.getTenTK());
        txtTenTKDialog.setFont(FONT_TEXT_FIELD);
        txtTenTKDialog.setEditable(false);
        txtTenTKDialog.setBackground(new Color(230,230,230));
        txtTenTKDialog.setBounds(inputX_d, startY_d + height_d + vGap_d, inputW_d, height_d);
        pnlInfoDialog.add(txtTenTKDialog);

        // Chức vụ (Read-only - lấy từ quyền tài khoản)
        JLabel lblChucVuDialog = new JLabel("Chức Vụ:");
        lblChucVuDialog.setFont(FONT_LABEL_BOLD);
        lblChucVuDialog.setBounds(labelX_d, startY_d + 2*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblChucVuDialog);
        JTextField txtChucVuDialog = new JTextField(taiKhoanMoi.getQuyenHan() != null ? taiKhoanMoi.getQuyenHan() : "(Chưa cấp)");
        txtChucVuDialog.setFont(FONT_TEXT_FIELD);
        txtChucVuDialog.setEditable(false);
        txtChucVuDialog.setBackground(new Color(230,230,230));
        txtChucVuDialog.setBounds(inputX_d, startY_d + 2*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(txtChucVuDialog);

        // Tên NV
        JLabel lblTenNVDialog = new JLabel("Tên Nhân Viên (*):");
        lblTenNVDialog.setFont(FONT_LABEL_BOLD);
        lblTenNVDialog.setBounds(labelX_d, startY_d + 3*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblTenNVDialog);
        txtTenNV_Dialog = new JTextField();
        txtTenNV_Dialog.setFont(FONT_TEXT_FIELD);
        txtTenNV_Dialog.setBounds(inputX_d, startY_d + 3*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(txtTenNV_Dialog);

        // Ngày sinh
        JLabel lblNgaySinhDialog = new JLabel("Ngày Sinh (*):");
        lblNgaySinhDialog.setFont(FONT_LABEL_BOLD);
        lblNgaySinhDialog.setBounds(labelX_d, startY_d + 4*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblNgaySinhDialog);
        dateNgaySinh_Dialog = new JDateChooser();
        dateNgaySinh_Dialog.setFont(FONT_TEXT_FIELD);
        dateNgaySinh_Dialog.setDateFormatString("dd/MM/yyyy");
        dateNgaySinh_Dialog.setBounds(inputX_d, startY_d + 4*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(dateNgaySinh_Dialog);

        // Giới tính
        JLabel lblGioiTinhDialog = new JLabel("Giới Tính (*):");
        lblGioiTinhDialog.setFont(FONT_LABEL_BOLD);
        lblGioiTinhDialog.setBounds(labelX_d, startY_d + 5*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblGioiTinhDialog);
        cboGioiTinh_Dialog = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboGioiTinh_Dialog.setFont(FONT_TEXT_FIELD);
        cboGioiTinh_Dialog.setBounds(inputX_d, startY_d + 5*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(cboGioiTinh_Dialog);

        // SĐT
        JLabel lblSDTDialog = new JLabel("Số Điện Thoại (*):");
        lblSDTDialog.setFont(FONT_LABEL_BOLD);
        lblSDTDialog.setBounds(labelX_d, startY_d + 6*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblSDTDialog);
        txtSDT_Dialog = new JTextField();
        txtSDT_Dialog.setFont(FONT_TEXT_FIELD);
        txtSDT_Dialog.setBounds(inputX_d, startY_d + 6*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(txtSDT_Dialog);

        // Tỉnh
        JLabel lblTinhDialog = new JLabel("Tỉnh/TP (*):");
        lblTinhDialog.setFont(FONT_LABEL_BOLD);
        lblTinhDialog.setBounds(labelX_d, startY_d + 7*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblTinhDialog);
        txtTinh_Dialog = new JTextField();
        txtTinh_Dialog.setFont(FONT_TEXT_FIELD);
        txtTinh_Dialog.setBounds(inputX_d, startY_d + 7*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(txtTinh_Dialog);

        // Huyện
        JLabel lblHuyenDialog = new JLabel("Quận/Huyện (*):");
        lblHuyenDialog.setFont(FONT_LABEL_BOLD);
        lblHuyenDialog.setBounds(labelX_d, startY_d + 8*(height_d + vGap_d), 130, height_d);
        pnlInfoDialog.add(lblHuyenDialog);
        txtHuyen_Dialog = new JTextField();
        txtHuyen_Dialog.setFont(FONT_TEXT_FIELD);
        txtHuyen_Dialog.setBounds(inputX_d, startY_d + 8*(height_d + vGap_d), inputW_d, height_d);
        pnlInfoDialog.add(txtHuyen_Dialog);


        // --- NÚT LƯU ---
        JButton btnLuuNV = new JButton("Lưu Thông Tin Nhân Viên");
        styleButton(btnLuuNV, COLOR_SUCCESS_GREEN);
        btnLuuNV.setFont(FONT_BUTTON_STANDARD);
        btnLuuNV.setBounds(350, 560, 250, 40);
        getContentPane().add(btnLuuNV);

        btnLuuNV.addActionListener(e -> {
            // 1. Lấy và kiểm tra dữ liệu
            String tenNV = txtTenNV_Dialog.getText().trim();
            java.util.Date ngaySinh = dateNgaySinh_Dialog.getDate();
            String gioiTinh = cboGioiTinh_Dialog.getSelectedItem().toString();
            String sdt = txtSDT_Dialog.getText().trim();
            String tinh = txtTinh_Dialog.getText().trim();
            String huyen = txtHuyen_Dialog.getText().trim();
            String anh = duongDanAnh_Dialog;

            if (tenNV.isEmpty() || ngaySinh == null || sdt.isEmpty() || tinh.isEmpty() || huyen.isEmpty() || anh == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin bắt buộc (*) và chọn ảnh!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!sdt.matches("^0\\d{9}$")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Xử lý ngày sinh
            LocalDate ngaySinhLocal = ngaySinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // 2. Tìm ChucVu (Dựa trên quyền hạn của Tài Khoản)
            ChucVu chucVuNV = (ChucVu) SocketClient.sendRequest(new Request(ActionType.GET_CHUC_VU_BY_TEN, taiKhoanMoi.getQuyenHan())).getData();
            
            // Xử lý nếu chức vụ không tìm thấy hoặc tài khoản chưa cấp quyền
            if (chucVuNV == null) {
                 if (taiKhoanMoi.getQuyenHan() == null || taiKhoanMoi.getQuyenHan().isEmpty()) {
                     JOptionPane.showMessageDialog(this, "Tài khoản chưa được cấp quyền, không thể xác định chức vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                     return;
                 }
                 // Tùy chọn: Có thể tạo một chức vụ tạm hoặc báo lỗi. Ở đây báo lỗi để an toàn.
                 JOptionPane.showMessageDialog(this, "Không tìm thấy Chức vụ phù hợp ("+taiKhoanMoi.getQuyenHan()+")!", "Lỗi Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            // 3. Tạo đối tượng Nhân Viên
            NhanVien nvMoi = new NhanVien();
            nvMoi.setMaNV(maNVMoi);
            nvMoi.setTenNV(tenNV);
            nvMoi.setNgaySinh(ngaySinhLocal);
            nvMoi.setGioiTinh(gioiTinh);
            nvMoi.setSoDienThoai(sdt);
            nvMoi.setDiaChi(tinh + ", " + huyen);
            nvMoi.setAnh(anh);
            nvMoi.setTaiKhoan(this.taiKhoanMoi); // Gán tài khoản đã lưu
            nvMoi.setChucVu(chucVuNV);
            nvMoi.setTrangThai("Còn làm việc"); // Gán mặc định khi tạo mới

            // 4. Gọi DAO để thêm nhân viên
            boolean themNVThanhCong = (boolean) SocketClient.sendRequest(new Request(ActionType.ADD_NHAN_VIEN, nvMoi)).getData();

            if (themNVThanhCong) {
                isSuccess = true; // Đánh dấu thành công
                JOptionPane.showMessageDialog(this, "Thêm tài khoản và thông tin nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Đóng form
            } else {
                // Thất bại
                JOptionPane.showMessageDialog(this, "Thêm thông tin nhân viên thất bại! Vui lòng kiểm tra lại.", "Lỗi DB", JOptionPane.ERROR_MESSAGE);
                // isSuccess vẫn là false, người dùng có thể sửa và bấm Lưu lại
            }
        });
        
    } // Kết thúc initialize()

    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    }
}