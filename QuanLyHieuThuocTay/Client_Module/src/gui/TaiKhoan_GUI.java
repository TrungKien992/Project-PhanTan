package gui;

import entity.TaiKhoan;
import javax.swing.*;
import java.awt.*;

public class TaiKhoan_GUI extends JDialog {
    private JTextField txtMaTK;
    private JTextField txtTenTK;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboQuyenHan;
    private JCheckBox chkTrangThai; // Thêm check trạng thái
    private JButton btnLuu;
    private JButton btnHuy;
    private boolean isConfirmed = false; // Để kiểm tra người dùng có bấm Lưu không
    private TaiKhoan taiKhoanResult = null;

    // Constructor
    public TaiKhoan_GUI(JFrame parent, String title, TaiKhoan tkEdit, String newMaTK) {
        super(parent, title, true); // Modal = true để chặn thao tác màn hình chính
        setLayout(new BorderLayout(10, 10));
        setSize(400, 350);
        setLocationRelativeTo(parent);

        // --- Panel Nội dung ---
        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 1. Mã TK (Read-only)
        gbc.gridx = 0; gbc.gridy = 0; pnlCenter.add(new JLabel("Mã TK:"), gbc);
        txtMaTK = new JTextField(15);
        txtMaTK.setEditable(false); // Không cho sửa mã
        gbc.gridx = 1; pnlCenter.add(txtMaTK, gbc);

        // 2. Tên TK
        gbc.gridx = 0; gbc.gridy = 1; pnlCenter.add(new JLabel("Tên đăng nhập:"), gbc);
        txtTenTK = new JTextField(15);
        gbc.gridx = 1; pnlCenter.add(txtTenTK, gbc);

        // 3. Mật khẩu
        gbc.gridx = 0; gbc.gridy = 2; pnlCenter.add(new JLabel("Mật khẩu:"), gbc);
        txtMatKhau = new JPasswordField(15);
        gbc.gridx = 1; pnlCenter.add(txtMatKhau, gbc);

        // 4. Quyền hạn
        gbc.gridx = 0; gbc.gridy = 3; pnlCenter.add(new JLabel("Quyền hạn:"), gbc);
        cboQuyenHan = new JComboBox<>(new String[]{"Quản lý", "Nhân viên bán hàng"});
        gbc.gridx = 1; pnlCenter.add(cboQuyenHan, gbc);

        // 5. Trạng thái (Chỉ hiện khi sửa, hoặc mặc định active khi thêm)
        gbc.gridx = 0; gbc.gridy = 4; pnlCenter.add(new JLabel("Trạng thái:"), gbc);
        chkTrangThai = new JCheckBox("Hoạt động");
        chkTrangThai.setSelected(true);
        gbc.gridx = 1; pnlCenter.add(chkTrangThai, gbc);

        add(pnlCenter, BorderLayout.CENTER);

        // --- Panel Nút bấm ---
        JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLuu = new JButton("Lưu");
        btnHuy = new JButton("Hủy");
        pnlSouth.add(btnLuu);
        pnlSouth.add(btnHuy);
        add(pnlSouth, BorderLayout.SOUTH);

        // --- Logic hiển thị dữ liệu ---
        if (tkEdit != null) {
            // Chế độ CẬP NHẬT
            txtMaTK.setText(tkEdit.getMaTK());
            txtTenTK.setText(tkEdit.getTenTK());
            txtMatKhau.setText(tkEdit.getMatKhau()); 
            cboQuyenHan.setSelectedItem(tkEdit.getQuyenHan());
            chkTrangThai.setSelected(tkEdit.isTrangThai());
            txtTenTK.setEditable(false); // Thường tên đăng nhập không cho sửa
        } else {
            // Chế độ THÊM MỚI
            txtMaTK.setText(newMaTK); // Mã tự động truyền vào
            txtMatKhau.setText("");
        }

        // --- Xử lý sự kiện ---
        btnLuu.addActionListener(e -> {
            if(validateInput()) {
                taiKhoanResult = new TaiKhoan(
                    txtMaTK.getText(),
                    txtTenTK.getText(),
                    new String(txtMatKhau.getPassword()),
                    cboQuyenHan.getSelectedItem().toString(),
                    chkTrangThai.isSelected()
                );
                isConfirmed = true;
                dispose();
            }
        });

        btnHuy.addActionListener(e -> dispose());
    }

    private boolean validateInput() {
        if (txtTenTK.getText().trim().isEmpty() || new String(txtMatKhau.getPassword()).trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tài khoản và mật khẩu!");
            return false;
        }
        return true;
    }

    public boolean isConfirmed() { return isConfirmed; }
    public TaiKhoan getTaiKhoan() { return taiKhoanResult; }
}