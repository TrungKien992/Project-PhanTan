package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;

import entity.NhanVien;
import entity.TaiKhoan;

public class Dangnhap_GUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;

    public static TaiKhoan taiKhoanLogin = null;
    public static String tenNhanVienLogin = null;

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dangnhap_GUI frame = new Dangnhap_GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Dangnhap_GUI() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon_tieude.png"));

        setTitle("Đăng Nhập Hệ Thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 995, 920);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setBounds(543, 310, 404, 32);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 123, 255));
        contentPane.add(lblTitle);

        JPanel panelInput = new JPanel();
        panelInput.setBackground(new Color(244, 244, 244));
        panelInput.setBounds(532, 372, 404, 75);
        contentPane.add(panelInput);
        panelInput.setLayout(null);

        JLabel lblTenDangNhap = new JLabel("Tên đăng nhập:");
        lblTenDangNhap.setBounds(5, 8, 106, 22);
        lblTenDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panelInput.add(lblTenDangNhap);

        txtTenDangNhap = new JTextField();
        txtTenDangNhap.setBounds(116, 5, 283, 28);
        txtTenDangNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panelInput.add(txtTenDangNhap);
        txtTenDangNhap.setColumns(10);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setBounds(43, 46, 68, 22);
        lblMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panelInput.add(lblMatKhau);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(116, 43, 283, 28);
        txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panelInput.add(txtMatKhau);

        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(new Color(244, 244, 244));
        panelButtons.setBounds(675, 458, 188, 51);
        contentPane.add(panelButtons);
        panelButtons.setLayout(null);
        
        JButton btnDangNhap = new JButton("Đăng Nhập");
        btnDangNhap.setBounds(33, 5, 127, 42);
        panelButtons.add(btnDangNhap);
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDangNhap.setBackground(new Color(40, 167, 69));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tenTK = txtTenDangNhap.getText().trim();
                String matKhau = new String(txtMatKhau.getPassword());

                if (tenTK.isEmpty() || matKhau.isEmpty()) {
                    JOptionPane.showMessageDialog(Dangnhap_GUI.this, "Vui lòng nhập tên đăng nhập và mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Response res = SocketClient.sendRequest(new Request(ActionType.LOGIN, new String[]{tenTK, matKhau}));
                TaiKhoan tk = (TaiKhoan) res.getData();

                if (tk == null) {
                    JOptionPane.showMessageDialog(Dangnhap_GUI.this, "Tên đăng nhập hoặc mật khẩu không đúng!", "Đăng nhập thất bại", JOptionPane.ERROR_MESSAGE);
                } else if (tk.getQuyenHan() == null || tk.getQuyenHan().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(Dangnhap_GUI.this, "Tài khoản của bạn chưa được cấp quyền truy cập. Vui lòng liên hệ quản trị viên!", "Chưa được cấp quyền", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(Dangnhap_GUI.this, "Đăng nhập thành công! Quyền: " + tk.getQuyenHan(), "Thành công", JOptionPane.INFORMATION_MESSAGE);

                    taiKhoanLogin = tk;
                    NhanVien nv = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA_TK, tk.getMaTK())).getData();
                    if (nv != null) {
                        tenNhanVienLogin = nv.getTenNV();
                    } else {
                        tenNhanVienLogin = tk.getTenTK();
                    }

                    EventQueue.invokeLater(() -> {
                         try {
                            TrangChu_GUI trangChu = new TrangChu_GUI();
                            trangChu.QuanLyHieuThuocTay.setVisible(true);
                            dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
        });
        
        txtMatKhau.addActionListener(e -> btnDangNhap.doClick());
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("img/img_dangnhap.png"));
        lblNewLabel.setBounds(0, 0, 979, 881);
        contentPane.add(lblNewLabel);
        txtTenDangNhap.addActionListener(e -> txtMatKhau.requestFocus()); // Chuyển focus khi Enter ở tên DN
    }
}