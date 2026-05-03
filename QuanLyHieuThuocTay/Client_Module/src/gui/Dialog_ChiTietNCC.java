package gui; // Hoặc package của bạn

import entity.NhaCungCap;
import javax.swing.*;
import java.awt.*;

public class Dialog_ChiTietNCC extends JDialog {

    private static final Color COLOR_DATA_RED = new Color(220, 53, 69);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 15);
    private static final Font FONT_DATA = new Font("Segoe UI", Font.PLAIN, 15);
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    private static final Color COLOR_TITLE_BLUE = new Color(0, 123, 255);

   
    public Dialog_ChiTietNCC(JFrame parentFrame, NhaCungCap ncc) {
        
        super(parentFrame, "Chi Tiết Nhà Cung Cấp", true); 
        
        setSize(700, 550); 
        setLocationRelativeTo(parentFrame); 
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
        setLayout(null); 
        
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE); 

        // --- Tiêu đề ---
        JLabel lblTitle = new JLabel("CHI TIẾT NHÀ CUNG CẤP");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TITLE_BLUE);
        lblTitle.setBounds(0, 20, 700, 35);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitle);

       
        int startX_Label = 50;
        int startX_Data = 200;
        int startY = 80;
        int vGap = 40;
        int height = 25;
        int labelWidth = 140;
        int dataWidth = 400;

        // Mã NCC
        JLabel lblMaNCC = createLabel("Mã Nhà Cung Cấp:", startX_Label, startY, labelWidth, height);
        JLabel lblMaNCC_Data = createDataLabel(ncc.getMaNhaCungCap(), startX_Data, startY, dataWidth, height);
        contentPane.add(lblMaNCC);
        contentPane.add(lblMaNCC_Data);

        // Tên NCC
        JLabel lblTenNCC = createLabel("Tên Nhà Cung Cấp:", startX_Label, startY + vGap, labelWidth, height);
        JLabel lblTenNCC_Data = createDataLabel(ncc.getTenNhaCungCap(), startX_Data, startY + vGap, dataWidth, height);
        contentPane.add(lblTenNCC);
        contentPane.add(lblTenNCC_Data);

        // Số Điện Thoại
        JLabel lblSDT = createLabel("Số Điện Thoại:", startX_Label, startY + vGap * 2, labelWidth, height);
        JLabel lblSDT_Data = createDataLabel(ncc.getSoDienThoai(), startX_Data, startY + vGap * 2, dataWidth, height);
        contentPane.add(lblSDT);
        contentPane.add(lblSDT_Data);
        
        // Email
        JLabel lblEmail = createLabel("Email:", startX_Label, startY + vGap * 3, labelWidth, height);
        JLabel lblEmail_Data = createDataLabel(ncc.getEmail(), startX_Data, startY + vGap * 3, dataWidth, height);
        contentPane.add(lblEmail);
        contentPane.add(lblEmail_Data);

        // Địa Chỉ
        JLabel lblDiaChi = createLabel("Địa Chỉ:", startX_Label, startY + vGap * 4, labelWidth, height);
        JLabel lblDiaChi_Data = createDataLabel(ncc.getDiaChi(), startX_Data, startY + vGap * 4, dataWidth, height);
        contentPane.add(lblDiaChi);
        contentPane.add(lblDiaChi_Data);

        // Trạng Thái
        String trangThai = ncc.isTrangThai() ? "Đang hợp tác" : "Ngừng hợp tác";
        JLabel lblTrangThai = createLabel("Trạng Thái:", startX_Label, startY + vGap * 5, labelWidth, height);
        JLabel lblTrangThai_Data = createDataLabel(trangThai, startX_Data, startY + vGap * 5, dataWidth, height);
        contentPane.add(lblTrangThai);
        contentPane.add(lblTrangThai_Data);

        // Ghi Chú (Giống phần Thành Phần trong ảnh)
        JLabel lblGhiChu = createLabel("Ghi Chú:", startX_Label, startY + vGap * 6, labelWidth, height);
        contentPane.add(lblGhiChu);

        JTextArea txtGhiChu = new JTextArea();
        txtGhiChu.setText(ncc.getGhiChu());
        txtGhiChu.setFont(FONT_DATA);
        txtGhiChu.setEditable(false); // Không cho phép chỉnh sửa
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setBackground(new Color(245, 245, 245)); // Nền hơi xám
        
        // **QUAN TRỌNG: Bọc JTextArea trong JScrollPane**
        JScrollPane scrollGhiChu = new JScrollPane(txtGhiChu);
        scrollGhiChu.setBounds(startX_Data, startY + vGap * 6, dataWidth, 100);
        contentPane.add(scrollGhiChu);
    }

    
    private JLabel createLabel(String text, int x, int y, int w, int h) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setBounds(x, y, w, h);
        return lbl;
    }

 
    private JLabel createDataLabel(String text, int x, int y, int w, int h) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_DATA);
        lbl.setForeground(COLOR_DATA_RED); 
        lbl.setBounds(x, y, w, h);
        return lbl;
    }
}