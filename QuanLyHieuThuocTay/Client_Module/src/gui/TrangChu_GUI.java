package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import java.beans.Beans;

import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;

import entity.*;
import controller.*;

public class TrangChu_GUI extends JFrame {

	public JButton btn_CNKH_Xoa;
	public JButton btn_CNT_Xoa;

    public JFrame QuanLyHieuThuocTay;
    public JPanel maincontent;
    public JTextField text_Nhapmathuoc;
    public JTable table_timkiemthuoc;
    public JTable table_hdtam;
    public JTextField text_Nhaptiennhan;
    public JTextField text_Nhapsoluongthuoc;
    public JTable table_tkt;
    public JTextField txt_kh_MaKH;
    public JTextField txt_kh_SDT;
    public JTextField txt_kh_TenKH;
    public JTable table_tkkh;
    public JTextField txt_cnkh_MaKh;
    public JTextField txt_cnkh_SDt;
    public JTextField txt_cnkh_tenkh;
    public JTextField txtMKH_TK;
    public JTextField txtTenKH_TK;
    public JTable table_CapNhatKH;
    public JTextField text_cntmt;
    public JTextField text_cnttt;
    public JTextField text_cntsl;
    public JTextField text_cntgn;
    public JTextField text_cntgb;
    public JTextField text_cnthsd;
    public JTextField text_cnt_tkmt;
    public JTextField text_cnt_tktt;
    public JTable table_Capnhatthuoc;
    public JTextField text_ttmt;
    public JTextField text_tttt;
    public JTextField text_ttsl;
    public JTextField text_ttgn;
    public JTextField text_ttgb;
    public JTable table_themthuoc;
    public JTable table_ttf;
    public JTable table_tshh;
    public JTable table_tshhan;
    public JTable table_5;
    public JTextField text_tkhd_TenKH;
    public JTextField text_tkhd_sdtkh;
    public JTextField text_tkhd_sdtnv;
    public JTextField text_tkhd_tennv;
    public JDateChooser date_tkhd_ngaylaphd;
    public JTable table_tkhd;
    public JDateChooser date_tktn_ngay;
    public JTable table_tktn;
    public JTextField text_tktn_tktnv_boloc_manv;
    public JTextField text_tktn_tktnv_boloc_tennv;
    public JTable table_8;
    public JTable table_hienhd_tktt;
    public JTextField text_tktt_tk_boloc_manv;
    public JTextField text_tktt_tk_boloc_tennv;
    public JTextField text_tktnam_locnv_manv;
    public JTextField text_tktnam_locnv_tennv;
    public JTable table_tktn_hiennv;
    public JTable table_11;
    public JTable table_ttkt;
    public JTextField txtMaNV_TNV;
    public JTextField txtTenNV_TNV;
    public JTextField txtSDT_TNV;
    public JTable table_TNV;
    public List<NhanVien> tempListNV = new ArrayList<>();
    public JTextField txt_TenNV_TKNV;
    public JTextField txtSDT_TKNV;
    public JTable table_TKNV;
    public JTextField txtTinh_TNV;
    public JTextField txtHuyen_TNV;
    public JTextField txtTinh_TKNV;
    public JTextField txtHuyen_TKNV;
    public JTextField txtMaNV_CNNV;
    public JTextField txtTenNV_CNNV;
    public JTextField txtSDT_CNNV;
    public JTextField txtTinh_CNNV;
    public JTextField txtHuyen_CNNV;
    public JTextField txtTenNV_TK_CNNV;
    public JTextField text_tkhd_Mahd;
    public JTable table_CNNV;
    public JTextField txt_kh_dc;
    public JTextField txt_cnkh_dc;
    public JTable table_ThemNCC;
    public JTextField txtMaNCC_CNNCC;
    public JTextField txtTenNCC_CNNCC;
    public JTextField txtSDT_CNNCC;
    public JTextField txtEmail_CNNCC;
    public String duongDanAnh_TNV = null;
    public String duongDanAnh_CNNV= null;
    public JTextField txtMaNCC_TNCC;
    public JTextField txtTenNCC_TNCC;
    public JTextField txtEmail_TNCC;
    public JTextField txtSDT_TNCC;
    public JTable table_CNNCC;
    public JTextField txtMaNCC_TKNCC;
    public JTextField txtTenNCC_TKNCC;
    public JTextField txtEmail_TKNCC;
    public JTextField txtSDT_TKNCC;
    public JTable table_TKNCC;
	public JButton btnLammoi_CNKH;
	public JButton btn_kh_Lammoi;
	public JButton btn_cnkh_CapNhat;
	public JButton btn_cntCapnhat;
	public JButton btn_cnt_ChonAnh;
	public JButton btn_cnkh_Khoiphuc;
	public JYearChooser year_tktt;
	public JMonthChooser month_tktt;
	public JYearChooser year_tktn;
	public JMenuItem currentSelectedItem = null;
	public JPanel currentVisibleSubmenu = null;
	public JButton selectedMainMenuButton = null;
	public CardLayout cl;
	public JComboBox<String> cb_tkt_kethuoc;
	public JComboBox<String> cb_tkt_tenthuoc;
	public JComboBox<String> cb_tkt_ncc;
	public JButton btn_tkt_xemchitiet;
	public JButton btn_tkt_Lammoi;
	public JButton btn_cnt_tk_lammoi;
	public JComboBox<String> cb_cnt_tktkt;
	public JComboBox<String> cb_cntdvt;
	public JComboBox<String> cb_cntncc;
	public JComboBox<String> cb_cnt_tkt;
	public JTextArea textArea_cnttp;
	public JButton btn_cntKhoiphuc;
	public JPanel pn_chuaanh;
	public JLabel lb_Chuaanh;
	public JTextField txtTimKiem;
	public JTable table;
// nhanVien_DAO nvDAO removed
	public JDateChooser date_cnthsd;
	public TaiKhoan currentUser;
	public String currentUserName;
    public JButton btnTaikhoan;
	public JDateChooser loadDataQLTK;
	public JTable tableQLTK;
	public JComboBox<String> cb_ttdvt;
	public JComboBox<String> cb_ttncc;
	public JComboBox<String> cb_tttkt;
	public JButton btn_ChonAnh_tt;
	public JLabel lb_Chuaanh_tt;
	public JButton btn_ttThem;
	public JButton btn_ttLammoi;
	public JTextArea textArea_tttp;
	public JDateChooser date_tthsd;
// thuoc_DAO daothuoc removed
	public JButton btn_ttf_chonfile;
	public JLabel lbl_ttf_tongsothuoc;
	public JLabel lbl_ttfile_hienthitongsothuoc;
	public JButton btn_ttf_lammoi;
	public JButton btn_ttf_them;
	public JCheckBox chk_XuatHoaDon;
	public JPanel pn_bieudo_thang; 
	public JPanel pn_bieudo_nam;  
	public HoaDon_Controller hoaDonController;
	public DoiTra_Controller doiTra_Controller;
    public ThuocBanChay_Controller thuocBanChayController;
	
    

	// ========== BẢNG MÀU VÀ FONT CHỮ HIỆN ĐẠI ==========
	private static final Color COLOR_BACKGROUND_PRIMARY = new Color(240, 242, 245);
	private static final Color COLOR_CARD_BACKGROUND = Color.WHITE;
	private static final Color COLOR_PRIMARY_BLUE = new Color(0, 123, 255);
	private static final Color COLOR_SUCCESS_GREEN = new Color(40, 167, 69);
	private static final Color COLOR_DANGER_RED = new Color(220, 53, 69);
	private static final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
	private static final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
	private static final Color COLOR_BORDER_LIGHT = new Color(222, 226, 230);

	// Fonts
	private static final Font FONT_TITLE_MAIN = new Font("Segoe UI", Font.BOLD, 38);
	private static final Font FONT_TITLE_SECTION = new Font("Segoe UI", Font.BOLD, 26);
	private static final Font FONT_LABEL_BOLD = new Font("Segoe UI", Font.BOLD, 15);
	private static final Font FONT_TEXT_FIELD = new Font("Segoe UI", Font.PLAIN, 15);
	private static final Font FONT_BUTTON_STANDARD = new Font("Segoe UI", Font.BOLD, 14);
	private static final Font FONT_TABLE_HEADER = new Font("Segoe UI", Font.BOLD, 15);
	private static final Font FONT_TABLE_CELL = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font FONT_SUMMARY_TOTAL = new Font("Segoe UI", Font.BOLD, 20);
	private static final Font FONT_DETAIL_ITALIC = new Font("Segoe UI", Font.ITALIC, 15);
    
	// ========== MÀU CHO SIDEBAR ==========
    private static final Color COLOR_SIDEBAR_BG_START = new Color(0, 80, 80); 
    private static final Color COLOR_SIDEBAR_BG_END = new Color(0, 120, 120); 
    private static final Color COLOR_SIDEBAR_BUTTON_HOVER = new Color(0, 153, 153); 
    private static final Color COLOR_SIDEBAR_BUTTON_SELECTED = new Color(0, 170, 170);
    private static final Color COLOR_SIDEBAR_TEXT = Color.WHITE;
	private static final Color COLOR_ACCENT_GREEN = null;
	private static final Font FONT_INPUT = null;
	private static final Font FONT_PLACEHOLDER = null;
    public JTextField txtDC_TNCC;
    public JTextField txtDiaChi_CNNCC;
    public JTextArea txtGhiChu_CNNCC;
    public JTextField txtDiaChi_TKNCC;
	public Object table_TimKiemNCC;
	public JButton btnThem_TNCC;
	public JButton btnLamMoi_TNCC;
	public JTextArea txtGhiChu_TNCC;
	public JComboBox<String> cboTrangThai_TNCC;
	public JComboBox<String> cboTrangThai_CNNCC;
	public JButton btnCapNhat_CNNCC;
	public JButton btnKhoiPhuc_CNNCC;
	public JButton btnLamMoi_CNNCC;
	public JButton btnXoa_CNNCC;
	public JButton btnXemChiTiet_TKNCC;
	public JTextArea txtGhiChu_TKNCC;
	public JComboBox<String> cboTrangThai_TKNCC;
	public JButton btnLamMoi_TKNCC;
	public JTextField txtTongTien_TKNCC;
	public JComboBox<String> cboLoaiTongTien_TKNCC;
	public JDateChooser dateChooserDenNgay_TKNCC;
	public JDateChooser dateChooserTuNgay_TKNCC;
	public JComboBox<String> cboTrangThai_TKNCC_PDH;
	public JComboBox<String> cboNCC_TKNCC;
	public JTextField txtMaPhieu_TKNCC;
	public JTable table_DSPDDH;
	public JButton btnTaoMoiPhieu;
	public JButton btnXemSuaPhieu;
	public JButton btnHuyPhieu;
	public JButton btnLamMoiBoLoc_PDH;
	public JButton btn_tkhd_lammoi;
	public JButton btn_tkhd_xemchitiet;
	public JLabel lbl_Hientongcong;
	public JLabel lbl_hienmahd;
	public JLabel lbl_Hientienthua;
	public JLabel lbl_Hienthue;
	public JLabel lbl_Hientienhang;
	public JLabel lbl_Hientenkh;
	public JLabel lbl_Hientennv;
	public JLabel lbl_hienngaylaphoadon;
	public JButton btn_lammoitkthuoc;
	public JButton btn_addthuocvaohoadon;
	public JButton btn_suaslthuoc;
	public JButton btn_xoathuockhoihd;
	public JButton btn_Huyhoadon;
	public JButton btn_Thanhtoanhoadon;
	public JTable table_ThemKM;
	public JTable table_CapNhatKM;
	public JTable table_TimKiemKM;
	public JTextField txtMaKM_CapNhat;
	public JComboBox<String> cboTrangThai_CapNhat;
	public JDateChooser dateNgayKT_CapNhat;
	public JDateChooser dateNgayBD_CapNhat;
	public JTextField txtSoLuong_CapNhat;
	public JTextField txtGiaTri_CapNhat;
	public JTextField txtTenKM_CapNhat;
	public JButton btnKhoiPhuc_CapNhatKM;
	public JButton btnXoa_CapNhatKM;
	public JButton btnCapNhat_CapNhatKM;
	public JComboBox<String> cboTrangThai_Them;
	public JDateChooser dateNgayKT_Them;
	public JDateChooser dateNgayBD_Them;
	public JTextField txtSoLuong_Them;
	public JTextField txtGiaTri_Them;
	public JTextField txtTenKM_Them;
	public JTextField txtMaKM_Them;
	public JButton btnThem_ThemKM;
	public JButton btnLamMoi_ThemKM;
	public JComboBox<KhuyenMai> cb_Chonkhuyenmai;
	public JButton btn_Themkhachhangmoi;
	public JButton btn_Themthuocvaophieudat;
	public JTextField txtMaTenKM_TK;
	public JDateChooser dateNgayBD_TK;
	public JDateChooser dateNgayKT_TK;
	public JComboBox<String> cboTrangThai_TK;
	public JButton btnLamMoi_TK;
	public JTextField txtMaTenKM_TK_CapNhat;
	public JButton btnLamMoi_TK_CapNhatKM;
	public JComboBox<String> cb_lockethuoc;
	public JTextField txt_Nhapmathuoc;
	public JComboBox<String> cb_loctenthuoc;
	public JButton btn_xemphieudatthuoc;
	public JButton btn_Xemchitiet;
	public JComboBox<String> cb_Nhapsosdtkh;
	public JLabel lbl_Ngayreal;
	public JLabel lbl_Gioreal;
	public JLabel lbl_tktn_hientshd;
	public JLabel lbl_tktnam_hientongsotien;
	public JLabel lbl_tktn_hiennam;
	public JLabel lbl_tktt_hiensohd;
	public JLabel lbl_tktt_hientongtienhd;
	public JLabel lbl_hienthangvanam;
	public JLabel lbl_tktn_hientongsotien;
	public JLabel lbl_tktn_hientongsohd;
	public JButton btn_tktt_xemchitiet;
	public JButton btn_tktnam_xemchitiet;
	public JComboBox<String> cb_tktn_tktnv_boloc;
	public JScrollPane scrollPane_5;
	public JPanel pn_tktn_tktnv_boloc_pntk;
	public JComboBox<String> cb_tktt_tknv;
	public JComboBox<String> cb_chonnamtk;
	public JPanel pn_tktnam_locnv;
	public JScrollPane scPtknv_hientablenv;
	public JPanel pn_tktt_tk_boloc_tknv;
	public JScrollPane scP_tktt;
	public JTextField txt_trathuoc_lydo;
	public JTextField txt_trathuoc_tkhd_maHD;
	public JTextField txt_trathuoc_tkhd_tenKH;
	public JTable table_trathuoc_tkhd;
	public JTable table_trathuoc_thuoc;
	public JButton btn_trathuoc_lammoi;
	public JDateChooser dateNgayLapHD_TKHD;
	public JLabel lbl_trathuoc_hienngaylaphoadon;
	public JLabel lbl_trathuoc_hienmahd;
	public JLabel lbl_trathuoc_Hientennv;
	public JLabel lbl_trathuoc_Hientenkh;
	public JLabel lbl_trathuoc_Hientienthuoc;
	public JLabel lbl_trathuoc_Hienthue;
	public JLabel lbl_trathuoc_Hientongcong;
	public JLabel lbl_trathuoc_hiensdtkh;
	public JLabel lbl_trathuoc_HienKmapdung;
	public JButton btn_trathuoc_LM;
	public JButton btn_trathuoc_luu;
	public JButton btn_trathuoc_XN;
	public JButton btn_trathuoc_xuathd;
	public JLabel lbl_trathuoc_tiendchoan;
	public JTextField txt_doithuoc_tkhd_mahd;
	public JTextField txt_doithuoc_tkhd_tenKh;
	public JTextField txt_doithuoc_lydo;
	public DoiTra_Controller doiTraController;
	public JTable table_doithuoc_hd;
	public JTable table_doithuoc_thuoc;
	public JDateChooser dateNgayLapHD_TKHD_doithuoc;
	public JButton btn_doithuoc_lammoi;
	public JLabel lbl_doithuoc_hienngaylaphoadon;
	public JLabel lbl_doithuoc_hienmahd;
	public JLabel lbl_doithuoc_Hientennv;
	public JLabel lbl_doithuoc_Hientenkh;
	public JLabel lbl_doithuoc_Hientienthuoc;
	public JLabel lbl_doithuoc_Hienthue;
	public JLabel lbl_doithuoc_Hientongcong;
	public JLabel lbl_doithuoc_tiendchoan;
	public JLabel lbl_doithuoc_hiensdtkh;
	public JLabel lbl_doithuoc_HienKmapdung;
	public JButton btn_doithuoc_LM;
	public JButton btn_doithuoc_xuathd;
	public JButton btn_doithuoc_XN;
	public JButton btn_doithuoc_luu;
	public JTextField txt_trathuoc_sldoi;
	public JTextField txt_doithuoc_sldoi;
	public Thuoc_Controller thuoc_controller;
	public JButton btn_tshhan_lammoi;
	public JButton btn_tshhan_loc;
	public JSpinner spinner_tshhan_nguong;
	public JLabel lbl_tshhan_nguong;
	public JTextField text_tshhan_timkiem;
	public JLabel lbl_tshhan_timkiem;
	public JButton btn_tshhan_xuatfile;
	public ThuocSapHetHang_Controller thuocSapHetHang_controller;
	public JPopupMenu popup_tshhan_table;
	public JMenuItem item_tshhan_xemchitiet;
	public JMenuItem item_tshhan_xemncc;
    
	public static void main(String[] args) {
		Locale.setDefault(Locale.forLanguageTag("vi-VN"));
	    try {
	        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                new TrangChu_GUI();    
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

    public TrangChu_GUI() {
         this.currentUser = Dangnhap_GUI.taiKhoanLogin;
         this.currentUserName = Dangnhap_GUI.tenNhanVienLogin;
         if (this.currentUser == null) {
             System.err.println("Chưa đăng nhập!");
              if (QuanLyHieuThuocTay != null) { 
                  QuanLyHieuThuocTay.dispose();
              }
              Dangnhap_GUI login = new Dangnhap_GUI();
              login.setVisible(true);
              return; 
         }
        initialize();
    }

    private void initialize() {
        QuanLyHieuThuocTay = new JFrame();
        QuanLyHieuThuocTay.setFont(new Font("Times New Roman", Font.PLAIN, 5));
        QuanLyHieuThuocTay.setTitle("Hệ Thống Quản Lý Hiệu Thuốc Tây");
        
        ImageIcon icon = new ImageIcon("image/z7068801445103_7be0ebb233e8a4eceb10c3aceb500455.jpg");
        QuanLyHieuThuocTay.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon_tieude.png"));
        QuanLyHieuThuocTay.setExtendedState(JFrame.MAXIMIZED_BOTH);
        QuanLyHieuThuocTay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        QuanLyHieuThuocTay.setLocationRelativeTo(null);

        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(
                    0, 0, COLOR_SIDEBAR_BG_START, 
                    0, height, COLOR_SIDEBAR_BG_END); 
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS)); 
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5)); 

        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setOpaque(false); 
        JLabel lblUserName = new JLabel(currentUserName != null ? currentUserName : "N/A", SwingConstants.CENTER); 
        lblUserName.setForeground(Color.WHITE);
        lblUserName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logoPanel.add(lblUserName, BorderLayout.CENTER);

        JLabel lblUserRole = new JLabel(currentUser.getQuyenHan(), SwingConstants.CENTER); 
        lblUserRole.setForeground(new Color(200, 220, 255)); 
        lblUserRole.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        logoPanel.add(lblUserRole, BorderLayout.SOUTH);
        logoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); 
        logoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(logoPanel); 

        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- Menu Hệ Thống ---
        JButton btnMenu_HT = createSidebarButton("Hệ thống", "/icons/system_16.png");
        JPanel systemSubmenuPanel = createSystemSubmenuPanel(); 
        addAccordionListener(btnMenu_HT, systemSubmenuPanel, sidebar); 
        sidebar.add(btnMenu_HT);
        sidebar.add(systemSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        // --- Menu Nhân Viên ---
        JButton btnMenu_NV = createSidebarButton("Nhân viên", "/icons/employee_16.png");
        JPanel employeeSubmenuPanel = createEmployeeSubmenuPanel(); 
        addAccordionListener(btnMenu_NV, employeeSubmenuPanel, sidebar); 
        sidebar.add(btnMenu_NV);
        sidebar.add(employeeSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        // --- Menu Khách Hàng ---
        JButton btnMenu_KH = createSidebarButton("Khách hàng", "/icons/customer_16.png");
        JPanel customerSubmenuPanel = createCustomerSubmenuPanel();
        addAccordionListener(btnMenu_KH, customerSubmenuPanel, sidebar); 
        sidebar.add(btnMenu_KH);
        sidebar.add(customerSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        // --- Menu Thuốc ---
        JButton btnMenu_Thuoc = createSidebarButton("Thuốc", "/icons/pill_16.png");
        JPanel medicineSubmenuPanel = createMedicineSubmenuPanel(); 
        addAccordionListener(btnMenu_Thuoc, medicineSubmenuPanel, sidebar); 
        sidebar.add(btnMenu_Thuoc);
        sidebar.add(medicineSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        // --- Menu Hóa Đơn ---
        JButton btnMenu_HD = createSidebarButton("Hoá đơn", "/icons/invoice_16.png");
        JPanel invoiceSubmenuPanel = createInvoiceSubmenuPanel(); 
        addAccordionListener(btnMenu_HD, invoiceSubmenuPanel, sidebar);
        sidebar.add(btnMenu_HD);
        sidebar.add(invoiceSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

        // --- Menu Nhà Cung Cấp ---
        JButton btnMenu_NCC = createSidebarButton("Nhà cung cấp", "/icons/supplier_16.png");
        JPanel supplierSubmenuPanel = createSupplierSubmenuPanel(); 
        addAccordionListener(btnMenu_NCC, supplierSubmenuPanel, sidebar); 
        sidebar.add(btnMenu_NCC);
        sidebar.add(supplierSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));

     // --- Menu Khuyến mãi ---
        JButton btnMenu_KM = createSidebarButton("Khuyến mãi", "/icons/discount_16.png");
        JPanel khuyenMaiSubmenuPanel = createKhuyenMaiSubmenuPanel(); 
        addAccordionListener(btnMenu_KM, khuyenMaiSubmenuPanel, sidebar); 
        sidebar.add(btnMenu_KM); 
        sidebar.add(khuyenMaiSubmenuPanel); 
        sidebar.add(Box.createRigidArea(new Dimension(0, 5)));


        sidebar.add(Box.createVerticalGlue()); 

        // --- Nút Đăng xuất ---
        JButton btnMenu_DX = createSidebarButton("Đăng xuất", "/icons/logout_16.png");
        addHoverEffect(btnMenu_DX);
        btnMenu_DX.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(QuanLyHieuThuocTay,
             "Bạn có chắc muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (QuanLyHieuThuocTay != null) {
                     QuanLyHieuThuocTay.dispose();
                }
                Dangnhap_GUI.taiKhoanLogin = null;
                Dangnhap_GUI.tenNhanVienLogin = null;

                EventQueue.invokeLater(() -> {
                    Dangnhap_GUI login = new Dangnhap_GUI();
                    login.setVisible(true);
                });
            }
        });
        sidebar.add(btnMenu_DX);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        QuanLyHieuThuocTay.getContentPane().add(sidebar, BorderLayout.WEST);
        
        maincontent = new JPanel();
        QuanLyHieuThuocTay.getContentPane().add(maincontent, BorderLayout.CENTER);
        maincontent.setLayout(new CardLayout(0, 0));
        
     // ===TRANG CHỦ===
        JPanel pn_Trangchu = new JPanel();
        maincontent.add(pn_Trangchu, "trangChu"); 
        pn_Trangchu.setLayout(null);
        
        JPanel pn_NgayGio = new JPanel();
        pn_NgayGio.setBounds(1000, 426, 575, 214); 
        pn_NgayGio.setOpaque(false); 
        pn_NgayGio.setLayout(null);
        pn_Trangchu.add(pn_NgayGio); 
        
        lbl_Ngayreal = new JLabel("dd/MM/yyyy"); 
        lbl_Ngayreal.setFont(new Font("Tahoma", Font.BOLD, 50));
        lbl_Ngayreal.setForeground(new Color(77, 135, 133));
        lbl_Ngayreal.setBounds(210, 11, 355, 73); 
        pn_NgayGio.add(lbl_Ngayreal);
        
        lbl_Gioreal = new JLabel("HH:mm:ss"); 
        lbl_Gioreal.setFont(new Font("Tahoma", Font.BOLD, 50));
        lbl_Gioreal.setForeground(new Color(77, 135, 133)); 
        lbl_Gioreal.setBounds(210, 108, 355, 73);
        pn_NgayGio.add(lbl_Gioreal);
        
        JLabel lbl_Ngay = new JLabel("Ngày :");
        lbl_Ngay.setForeground(new Color(77, 135, 133));
        lbl_Ngay.setFont(new Font("Tahoma", Font.BOLD, 50));
        lbl_Ngay.setBounds(10, 11, 190, 73);
        pn_NgayGio.add(lbl_Ngay);
        
        JLabel lbl_Gio = new JLabel("Giờ :");
        lbl_Gio.setForeground(new Color(77, 135, 133));
        lbl_Gio.setFont(new Font("Tahoma", Font.BOLD, 50));
        lbl_Gio.setBounds(10, 108, 190, 73);
        pn_NgayGio.add(lbl_Gio);
        
        JLabel lblNewLabel = new JLabel(""); 
        // Load ảnh nền Trang chủ
        ImageIcon iconTrangChu = new ImageIcon("img/Trangchu.jpg");
        if (iconTrangChu.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image img = iconTrangChu.getImage().getScaledInstance(1660, 1000, Image.SCALE_SMOOTH);
            lblNewLabel.setIcon(new ImageIcon(img));
        } else {
            System.err.println("❌ Không tìm thấy ảnh: img/Trangchu.jpg");
        }
        lblNewLabel.setBounds(0, 0, 1699, 1001);
        pn_Trangchu.add(lblNewLabel);
        pn_Trangchu.setComponentZOrder(lblNewLabel, pn_Trangchu.getComponentCount() - 1);
        
        javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                if (lbl_Ngayreal != null) {
                    lbl_Ngayreal.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                }
                if (lbl_Gioreal != null) {
                    lbl_Gioreal.setText(now.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                }
            }
        });
        timer.start(); 
        
        // 6. HIỂN THỊ CARD
        CardLayout cl = (CardLayout) maincontent.getLayout();
        cl.show(maincontent, "trangChu");
        
        // ==Thêm Hóa Đơn ==
        JPanel pn_Themhoadon = new JPanel();
        maincontent.add(pn_Themhoadon, "themHoaDon");
        pn_Themhoadon.setLayout(null);
        pn_Themhoadon.setBackground(COLOR_BACKGROUND_PRIMARY);

        JPanel pn_Themhoadon_west = new JPanel();
        pn_Themhoadon_west.setBounds(0, 0, 600, 968);
        pn_Themhoadon_west.setBackground(COLOR_BACKGROUND_PRIMARY); 
        pn_Themhoadon.add(pn_Themhoadon_west);
        pn_Themhoadon_west.setLayout(null);

        JPanel pn_timkiemthuoc = new JPanel();
        pn_timkiemthuoc.setBounds(10, 11, 579, 195);
        pn_timkiemthuoc.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Tìm Kiếm",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            FONT_LABEL_BOLD,
            COLOR_PRIMARY_BLUE
        ));
        pn_timkiemthuoc.setBackground(COLOR_CARD_BACKGROUND); 
        pn_Themhoadon_west.add(pn_timkiemthuoc);
        pn_timkiemthuoc.setLayout(null);

        JLabel lbl_Mathuoc = new JLabel("Mã Thuốc :");
        lbl_Mathuoc.setBounds(30, 29, 115, 28);
        lbl_Mathuoc.setFont(FONT_LABEL_BOLD); 
        lbl_Mathuoc.setForeground(COLOR_TEXT_DARK); 
        pn_timkiemthuoc.add(lbl_Mathuoc);

        JLabel lbl_Kethuoc = new JLabel("Kệ Thuốc :");
        lbl_Kethuoc.setBounds(30, 68, 115, 28);
        lbl_Kethuoc.setFont(FONT_LABEL_BOLD); 
        lbl_Kethuoc.setForeground(COLOR_TEXT_DARK); 
        pn_timkiemthuoc.add(lbl_Kethuoc);

        JLabel lbl_Tenthuoc = new JLabel("Tên Thuốc :");
        lbl_Tenthuoc.setBounds(30, 107, 121, 28);
        lbl_Tenthuoc.setFont(FONT_LABEL_BOLD); 
        lbl_Tenthuoc.setForeground(COLOR_TEXT_DARK); 
        pn_timkiemthuoc.add(lbl_Tenthuoc);

        txt_Nhapmathuoc = new JTextField();
        txt_Nhapmathuoc.setBounds(154, 29, 415, 28);
        txt_Nhapmathuoc.setFont(FONT_TEXT_FIELD);
        pn_timkiemthuoc.add(txt_Nhapmathuoc);
        txt_Nhapmathuoc.setColumns(10);

        cb_loctenthuoc = new JComboBox<String>();
        cb_loctenthuoc.setBounds(154, 109, 415, 28);
        cb_loctenthuoc.setFont(FONT_TEXT_FIELD); 
        pn_timkiemthuoc.add(cb_loctenthuoc);

        btn_lammoitkthuoc = new JButton("Làm Mới");
        btn_lammoitkthuoc.setBounds(217, 153, 136, 31);
        btn_lammoitkthuoc.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_lammoitkthuoc, COLOR_TEXT_MUTED); 
        pn_timkiemthuoc.add(btn_lammoitkthuoc);

        cb_lockethuoc = new JComboBox<String>();
        cb_lockethuoc.setModel(new DefaultComboBoxModel(new String[] {}));
        cb_lockethuoc.setBounds(153, 68, 415, 28);
        cb_lockethuoc.setFont(FONT_TEXT_FIELD); 
        pn_timkiemthuoc.add(cb_lockethuoc);

        JScrollPane scP_timkiemthuoc = new JScrollPane();
        scP_timkiemthuoc.setBounds(10, 217, 579, 682);
        scP_timkiemthuoc.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); 
        pn_Themhoadon_west.add(scP_timkiemthuoc);

        table_timkiemthuoc = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        applyCommonTableStyling(table_timkiemthuoc);
        table_timkiemthuoc.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null, null, null},
            },
            new String[] {
                "Mã Thuốc", "Kệ Thuốc", "Tên Thuốc", "Đơn Giá", "Số Lượng"
            }
        ));
        table_timkiemthuoc.getColumnModel().getColumn(0).setPreferredWidth(90);
        table_timkiemthuoc.getColumnModel().getColumn(1).setPreferredWidth(150);
        table_timkiemthuoc.getColumnModel().getColumn(2).setPreferredWidth(200);
        scP_timkiemthuoc.setViewportView(table_timkiemthuoc);

        btn_Xemchitiet = new JButton("Xem Chi Tiết");
        btn_Xemchitiet.setBounds(195, 910, 180, 47);
        btn_Xemchitiet.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_Xemchitiet, COLOR_PRIMARY_BLUE); 
        pn_Themhoadon_west.add(btn_Xemchitiet);

        JPanel pn_Themhoadon_east = new JPanel();
        pn_Themhoadon_east.setBounds(701, 0, 1018, 968);
        pn_Themhoadon_east.setBackground(COLOR_BACKGROUND_PRIMARY); 
        pn_Themhoadon.add(pn_Themhoadon_east);
        pn_Themhoadon_east.setLayout(null);

        JLabel lbl_Laphoadon = new JLabel("LẬP HÓA ĐƠN");
        lbl_Laphoadon.setFont(FONT_TITLE_MAIN); 
        lbl_Laphoadon.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_Laphoadon.setBounds(10, 11, 296, 59);
        pn_Themhoadon_east.add(lbl_Laphoadon);

        btn_xemphieudatthuoc = new JButton("Phiếu Đặt Thuốc");
        btn_xemphieudatthuoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DsPhieuDatThuoc_GUI DanhsachphieudatthuocDialog =new DsPhieuDatThuoc_GUI(QuanLyHieuThuocTay);
            }
        });
        btn_xemphieudatthuoc.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_xemphieudatthuoc, COLOR_TEXT_MUTED); 
        btn_xemphieudatthuoc.setBounds(590, 64, 186, 43);
        pn_Themhoadon_east.add(btn_xemphieudatthuoc);

        btn_Themkhachhangmoi = new JButton("Thêm Khách Hàng");
        btn_Themkhachhangmoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ThemKH_GUI themKhDialog = new ThemKH_GUI(QuanLyHieuThuocTay);
            }
        });
        btn_Themkhachhangmoi.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_Themkhachhangmoi, COLOR_TEXT_MUTED); 
        btn_Themkhachhangmoi.setBounds(786, 64, 198, 43);
        pn_Themhoadon_east.add(btn_Themkhachhangmoi);

        JPanel pn_Hoadonbanle = new JPanel();
        pn_Hoadonbanle.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Hóa Đơn",
            TitledBorder.LEADING,
            TitledBorder.TOP,
            FONT_LABEL_BOLD,
            COLOR_PRIMARY_BLUE
        ));
        pn_Hoadonbanle.setBackground(COLOR_CARD_BACKGROUND); 
        pn_Hoadonbanle.setBounds(10, 118, 986, 785);
        pn_Themhoadon_east.add(pn_Hoadonbanle);
        pn_Hoadonbanle.setLayout(null);

        JLabel lbl_Hoadonbanle = new JLabel("Hóa Đơn Bán Lẻ");
        lbl_Hoadonbanle.setFont(FONT_TITLE_SECTION); 
        lbl_Hoadonbanle.setForeground(COLOR_TEXT_DARK); 
        lbl_Hoadonbanle.setBounds(270, 11, 291, 47);
        pn_Hoadonbanle.add(lbl_Hoadonbanle);

        JLabel lbl_Ngaylaphoadon = new JLabel("Ngày Lập : ");
        lbl_Ngaylaphoadon.setForeground(COLOR_TEXT_MUTED); 
        lbl_Ngaylaphoadon.setFont(FONT_DETAIL_ITALIC); 
        lbl_Ngaylaphoadon.setBounds(708, 55, 100, 25);
        pn_Hoadonbanle.add(lbl_Ngaylaphoadon);

        lbl_hienngaylaphoadon = new JLabel("09/09/2025");
        lbl_hienngaylaphoadon.setForeground(COLOR_DANGER_RED); 
        lbl_hienngaylaphoadon.setFont(FONT_DETAIL_ITALIC); 
        lbl_hienngaylaphoadon.setBounds(818, 55, 100, 25);
        pn_Hoadonbanle.add(lbl_hienngaylaphoadon);

        JLabel lbl_Mahd = new JLabel("Mã Hóa Đơn :");
        lbl_Mahd.setFont(FONT_LABEL_BOLD); 
        lbl_Mahd.setBounds(43, 91, 114, 25);
        pn_Hoadonbanle.add(lbl_Mahd);

        JLabel lbl_Nhanvien = new JLabel("Nhân Viên :");
        lbl_Nhanvien.setFont(FONT_LABEL_BOLD); 
        lbl_Nhanvien.setBounds(43, 140, 114, 25);
        pn_Hoadonbanle.add(lbl_Nhanvien);

        JLabel lbl_Sodienthoai = new JLabel("SĐT Khách Hàng :");
        lbl_Sodienthoai.setFont(FONT_LABEL_BOLD); 
        lbl_Sodienthoai.setBounds(527, 91, 160, 25);
        pn_Hoadonbanle.add(lbl_Sodienthoai);

        JLabel lbl_Khachhang = new JLabel("Khách Hàng :");
        lbl_Khachhang.setFont(FONT_LABEL_BOLD); 
        lbl_Khachhang.setBounds(579, 140, 114, 25);
        pn_Hoadonbanle.add(lbl_Khachhang);

        btn_suaslthuoc = new JButton("Sửa Số Lượng");
        btn_suaslthuoc.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_suaslthuoc, COLOR_TEXT_MUTED); 
        btn_suaslthuoc.setBounds(711, 176, 160, 43);
        pn_Hoadonbanle.add(btn_suaslthuoc);

        btn_xoathuockhoihd = new JButton("Xóa");
        btn_xoathuockhoihd.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_xoathuockhoihd, COLOR_DANGER_RED); 
        btn_xoathuockhoihd.setBounds(881, 176, 89, 43);
        pn_Hoadonbanle.add(btn_xoathuockhoihd);

        lbl_hienmahd = new JLabel("HD00000001");
        lbl_hienmahd.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_hienmahd.setFont(FONT_LABEL_BOLD); 
        lbl_hienmahd.setBounds(167, 91, 209, 25);
        pn_Hoadonbanle.add(lbl_hienmahd);

        lbl_Hientennv = new JLabel("Nguyễn Trung Kiên");
        lbl_Hientennv.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_Hientennv.setFont(FONT_LABEL_BOLD); 
        lbl_Hientennv.setBounds(167, 142, 209, 25);
        pn_Hoadonbanle.add(lbl_Hientennv);

        lbl_Hientenkh = new JLabel("Nguyễn Ngô Đức Mạnh");
        lbl_Hientenkh.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_Hientenkh.setFont(FONT_LABEL_BOLD); 
        lbl_Hientenkh.setBounds(708, 140, 268, 25);
        pn_Hoadonbanle.add(lbl_Hientenkh);

        JScrollPane scP_Hdtam = new JScrollPane();
        scP_Hdtam.setBounds(10, 230, 966, 376);
        scP_Hdtam.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); 
        pn_Hoadonbanle.add(scP_Hdtam);

     // TẠO BẢNG 2 VỚI RENDERER RIÊNG
        table_hdtam = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        // GỌI HÀM STYLE CHUNG
        applyCommonTableStyling(table_hdtam);
        table_hdtam.setModel(new DefaultTableModel(
        	    new Object[][] {},
        	    new String[] {
        	        "Mã Thuốc", "Tên Thuốc", "Số Lượng", "Giá Tiền", "Thành Tiền"
        	    }
        	));
        scP_Hdtam.setViewportView(table_hdtam);

        JLabel lbl_tongtienhang = new JLabel("Tổng Tiền Hàng :");
        lbl_tongtienhang.setFont(FONT_LABEL_BOLD); 
        lbl_tongtienhang.setBounds(10, 617, 123, 25);
        pn_Hoadonbanle.add(lbl_tongtienhang);

        JLabel lbl_Tiennhan = new JLabel("Tiền Nhận :");
        lbl_Tiennhan.setFont(FONT_LABEL_BOLD); 
        lbl_Tiennhan.setBounds(10, 730, 100, 25);
        pn_Hoadonbanle.add(lbl_Tiennhan);

        JLabel lbl_Thue = new JLabel("Thuế (10% VAT) :");
        lbl_Thue.setFont(FONT_LABEL_BOLD); 
        lbl_Thue.setBounds(527, 617, 129, 25);
        pn_Hoadonbanle.add(lbl_Thue);

        JLabel lbl_Tongcong = new JLabel("Tổng Cộng :");
        lbl_Tongcong.setFont(FONT_SUMMARY_TOTAL); 
        lbl_Tongcong.setBounds(527, 671, 123, 25);
        pn_Hoadonbanle.add(lbl_Tongcong);

        JLabel lbl_Tienthua = new JLabel("Tiền Thừa :");
        lbl_Tienthua.setFont(FONT_LABEL_BOLD); 
        lbl_Tienthua.setBounds(527, 730, 95, 25);
        pn_Hoadonbanle.add(lbl_Tienthua);

        text_Nhaptiennhan = new JTextField();
        text_Nhaptiennhan.setBounds(120, 726, 397, 34);
        text_Nhaptiennhan.setFont(FONT_TEXT_FIELD); 
        pn_Hoadonbanle.add(text_Nhaptiennhan);
        text_Nhaptiennhan.setColumns(10);

        lbl_Hientienhang  = new JLabel("10000000");
        lbl_Hientienhang.setForeground(COLOR_TEXT_DARK); 
        lbl_Hientienhang.setFont(FONT_LABEL_BOLD); 
        lbl_Hientienhang.setBounds(143, 617, 374, 25);
        pn_Hoadonbanle.add(lbl_Hientienhang);

        lbl_Hienthue = new JLabel("10000000");
        lbl_Hienthue.setForeground(COLOR_TEXT_DARK); 
        lbl_Hienthue.setFont(FONT_LABEL_BOLD); 
        lbl_Hienthue.setBounds(666, 617, 304, 25);
        pn_Hoadonbanle.add(lbl_Hienthue);

        lbl_Hientongcong = new JLabel("10000000");
        lbl_Hientongcong.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_Hientongcong.setFont(FONT_SUMMARY_TOTAL); 
        lbl_Hientongcong.setBounds(660, 671, 310, 25);
        pn_Hoadonbanle.add(lbl_Hientongcong);

        lbl_Hientienthua = new JLabel("10000000");
        lbl_Hientienthua.setForeground(COLOR_SUCCESS_GREEN); 
        lbl_Hientienthua.setFont(FONT_LABEL_BOLD); 
        lbl_Hientienthua.setBounds(633, 730, 337, 25);
        pn_Hoadonbanle.add(lbl_Hientienthua);
        
        JLabel lbl_Chonkhuyenmai = new JLabel("Chọn Khuyến Mãi :");
        lbl_Chonkhuyenmai.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl_Chonkhuyenmai.setBounds(10, 671, 138, 25);
        pn_Hoadonbanle.add(lbl_Chonkhuyenmai);
        
        cb_Chonkhuyenmai = new JComboBox<KhuyenMai>(); 
        cb_Chonkhuyenmai.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cb_Chonkhuyenmai.setBounds(158, 669, 359, 28);
        pn_Hoadonbanle.add(cb_Chonkhuyenmai);
        
        cb_Nhapsosdtkh = new JComboBox<String>();
        cb_Nhapsosdtkh.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cb_Nhapsosdtkh.setEditable(true);
        cb_Nhapsosdtkh.setBounds(666, 91, 304, 28);
        pn_Hoadonbanle.add(cb_Nhapsosdtkh);

        btn_Huyhoadon = new JButton("Hủy Hóa Đơn");
        btn_Huyhoadon.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_Huyhoadon, COLOR_DANGER_RED); 
        btn_Huyhoadon.setBounds(10, 914, 180, 43);
        pn_Themhoadon_east.add(btn_Huyhoadon);

        btn_Themthuocvaophieudat = new JButton("Thêm Vào Phiếu Đặt Thuốc");
        btn_Themthuocvaophieudat.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_Themthuocvaophieudat, COLOR_TEXT_MUTED); 
        btn_Themthuocvaophieudat.setBounds(247, 914, 290, 43);
        pn_Themhoadon_east.add(btn_Themthuocvaophieudat);

        chk_XuatHoaDon = new JCheckBox("Xuất Hóa Đơn");
        chk_XuatHoaDon.setFont(FONT_LABEL_BOLD); 
        chk_XuatHoaDon.setForeground(COLOR_PRIMARY_BLUE); 
        chk_XuatHoaDon.setBackground(COLOR_BACKGROUND_PRIMARY); 
        chk_XuatHoaDon.setFocusPainted(false);
        chk_XuatHoaDon.setBounds(590, 914, 177, 43); 
        pn_Themhoadon_east.add(chk_XuatHoaDon);

        btn_Thanhtoanhoadon = new JButton("Thanh Toán");
        btn_Thanhtoanhoadon.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_Thanhtoanhoadon, COLOR_SUCCESS_GREEN); 
        btn_Thanhtoanhoadon.setBounds(797, 914, 186, 43);
        pn_Themhoadon_east.add(btn_Thanhtoanhoadon);

        text_Nhapsoluongthuoc = new JTextField();
        text_Nhapsoluongthuoc.setBounds(610, 567, 81, 37);
        text_Nhapsoluongthuoc.setFont(FONT_TEXT_FIELD); 
        text_Nhapsoluongthuoc.setHorizontalAlignment(SwingConstants.CENTER); 
        pn_Themhoadon.add(text_Nhapsoluongthuoc);
        text_Nhapsoluongthuoc.setColumns(10);

        btn_addthuocvaohoadon = new JButton(">>");
        btn_addthuocvaohoadon.setFont(new Font("Segoe UI", Font.BOLD, 30)); 
        styleButton(btn_addthuocvaohoadon, COLOR_SUCCESS_GREEN); 
        btn_addthuocvaohoadon.setBounds(610, 506, 81, 50);
        pn_Themhoadon.add(btn_addthuocvaohoadon);

        JLabel lbl_Nhapsoluongthuoc = new JLabel("<html><center>Nhập<br>Số<br>Lượng</center></html>");
        lbl_Nhapsoluongthuoc.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Nhapsoluongthuoc.setFont(FONT_LABEL_BOLD); 
        lbl_Nhapsoluongthuoc.setForeground(COLOR_TEXT_DARK); 
        lbl_Nhapsoluongthuoc.setBounds(610, 615, 81, 84);
        pn_Themhoadon.add(lbl_Nhapsoluongthuoc);
        
     // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM HÓA ĐƠN ĐÃ SỬA =====
        JPanel pn_tkhd = new JPanel();
        maincontent.add(pn_tkhd, "timKiemHoaDon");
        pn_tkhd.setLayout(null);
        pn_tkhd.setBackground(COLOR_BACKGROUND_PRIMARY); 

        JPanel pn_Nhapthongtintk = new JPanel();
        pn_Nhapthongtintk.setBackground(COLOR_CARD_BACKGROUND);
        pn_Nhapthongtintk.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Bộ Lọc Tìm Kiếm",
            TitledBorder.LEADING,
            TitledBorder.TOP,
            FONT_LABEL_BOLD, 
            COLOR_PRIMARY_BLUE 
        ));
        pn_Nhapthongtintk.setBounds(10, 11, 1699, 161);
        pn_tkhd.add(pn_Nhapthongtintk);
        pn_Nhapthongtintk.setLayout(null);
        
        JLabel lbl_tkhd = new JLabel("TÌM KIẾM HÓA ĐƠN");
        lbl_tkhd.setFont(FONT_TITLE_MAIN); 
        lbl_tkhd.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_tkhd.setHorizontalAlignment(SwingConstants.CENTER); 
        lbl_tkhd.setBounds(596, 11, 544, 41); 
        pn_Nhapthongtintk.add(lbl_tkhd);
        
        JLabel lbl_tkhd_Tenkh = new JLabel("Tên Khách Hàng :");
        lbl_tkhd_Tenkh.setFont(FONT_LABEL_BOLD); 
        lbl_tkhd_Tenkh.setForeground(COLOR_TEXT_DARK); 
        lbl_tkhd_Tenkh.setBounds(43, 63, 148, 30);
        pn_Nhapthongtintk.add(lbl_tkhd_Tenkh);
        
        JLabel lbl_tkhd_Mahd = new JLabel("Mã Hóa Đơn :");
        lbl_tkhd_Mahd.setFont(FONT_LABEL_BOLD); 
        lbl_tkhd_Mahd.setForeground(COLOR_TEXT_DARK); 
        lbl_tkhd_Mahd.setBounds(43, 104, 148, 30);
        pn_Nhapthongtintk.add(lbl_tkhd_Mahd);
        
        JLabel lbl_tkhd_SDTKH = new JLabel("SĐT Khách Hàng :");
        lbl_tkhd_SDTKH.setFont(FONT_LABEL_BOLD); 
        lbl_tkhd_SDTKH.setForeground(COLOR_TEXT_DARK); 
        lbl_tkhd_SDTKH.setBounds(538, 63, 154, 30);
        pn_Nhapthongtintk.add(lbl_tkhd_SDTKH);
        
        JLabel lbl_tkhd_tenNV = new JLabel("Tên Nhân Viên :");
        lbl_tkhd_tenNV.setFont(FONT_LABEL_BOLD); 
        lbl_tkhd_tenNV.setForeground(COLOR_TEXT_DARK); 
        lbl_tkhd_tenNV.setBounds(1046, 63, 136, 30);
        pn_Nhapthongtintk.add(lbl_tkhd_tenNV);
        
        JLabel lbl_tkhd_sdtnv = new JLabel("SĐT Nhân Viên :");
        lbl_tkhd_sdtnv.setFont(FONT_LABEL_BOLD); 
        lbl_tkhd_sdtnv.setForeground(COLOR_TEXT_DARK); 
        lbl_tkhd_sdtnv.setBounds(538, 104, 154, 30);
        pn_Nhapthongtintk.add(lbl_tkhd_sdtnv);
        
        JLabel lbl_tkhd_Ngaylaphd = new JLabel("Ngày Lập HĐ :");
        lbl_tkhd_Ngaylaphd.setFont(FONT_LABEL_BOLD); 
        lbl_tkhd_Ngaylaphd.setForeground(COLOR_TEXT_DARK); 
        lbl_tkhd_Ngaylaphd.setBounds(1046, 104, 136, 30);
        pn_Nhapthongtintk.add(lbl_tkhd_Ngaylaphd);
        
        text_tkhd_TenKH = new JTextField();
        text_tkhd_TenKH.setFont(FONT_TEXT_FIELD); 
        text_tkhd_TenKH.setBounds(194, 63, 268, 30);
        pn_Nhapthongtintk.add(text_tkhd_TenKH);
        text_tkhd_TenKH.setColumns(10);
        
        text_tkhd_Mahd = new JTextField();
        text_tkhd_Mahd.setFont(FONT_TEXT_FIELD); 
        text_tkhd_Mahd.setColumns(10);
        text_tkhd_Mahd.setBounds(194, 104, 268, 30);
        pn_Nhapthongtintk.add(text_tkhd_Mahd);
        
        text_tkhd_sdtkh = new JTextField();
        text_tkhd_sdtkh.setFont(FONT_TEXT_FIELD); 
        text_tkhd_sdtkh.setColumns(10);
        text_tkhd_sdtkh.setBounds(702, 63, 268, 30);
        pn_Nhapthongtintk.add(text_tkhd_sdtkh);
        
        text_tkhd_sdtnv = new JTextField();
        text_tkhd_sdtnv.setFont(FONT_TEXT_FIELD); 
        text_tkhd_sdtnv.setColumns(10);
        text_tkhd_sdtnv.setBounds(702, 104, 268, 30);
        pn_Nhapthongtintk.add(text_tkhd_sdtnv);
        
        text_tkhd_tennv = new JTextField();
        text_tkhd_tennv.setFont(FONT_TEXT_FIELD); 
        text_tkhd_tennv.setColumns(10);
        text_tkhd_tennv.setBounds(1192, 63, 268, 30);
        pn_Nhapthongtintk.add(text_tkhd_tennv);
        
        date_tkhd_ngaylaphd = new JDateChooser();
        date_tkhd_ngaylaphd.setFont(FONT_TEXT_FIELD);
        date_tkhd_ngaylaphd.setDateFormatString("dd/MM/yyyy");
        date_tkhd_ngaylaphd.setBounds(1192, 104, 268, 30);
        pn_Nhapthongtintk.add(date_tkhd_ngaylaphd);
        
        btn_tkhd_lammoi = new JButton("Làm Mới");
        btn_tkhd_lammoi.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_tkhd_lammoi, COLOR_TEXT_MUTED); 
        btn_tkhd_lammoi.setBounds(1535, 63, 141, 30); 
        pn_Nhapthongtintk.add(btn_tkhd_lammoi);
        
        btn_tkhd_xemchitiet = new JButton("Xem Chi Tiết");
        btn_tkhd_xemchitiet.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		XemchitietHD_GUI XemchitietHD =new XemchitietHD_GUI(QuanLyHieuThuocTay); 
        	}
        });
        btn_tkhd_xemchitiet.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_tkhd_xemchitiet, COLOR_PRIMARY_BLUE); 
        btn_tkhd_xemchitiet.setBounds(1535, 104, 141, 30); 
        pn_Nhapthongtintk.add(btn_tkhd_xemchitiet);
        
        JScrollPane scP_tabletkhd = new JScrollPane();
        scP_tabletkhd.setBounds(10, 183, 1679, 796); 
        scP_tabletkhd.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); 
        pn_tkhd.add(scP_tabletkhd);
        
        table_tkhd = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE); 
                    
                }
                return c;
            }
        };
        
        // ----- TÙY CHỈNH CHUNG CHO BẢNG -----
        table_tkhd.setFont(FONT_TABLE_CELL);
        table_tkhd.setRowHeight(30);
        table_tkhd.setGridColor(COLOR_BORDER_LIGHT);
        table_tkhd.setShowGrid(true);
        table_tkhd.setSelectionBackground(COLOR_PRIMARY_BLUE);
        table_tkhd.setSelectionForeground(Color.WHITE);
        
        // ----- TÙY CHỈNH HEADER CỦA BẢNG -----
        JTableHeader header_tkhd = table_tkhd.getTableHeader();
        if (header_tkhd != null) {
            header_tkhd.setFont(FONT_TABLE_HEADER);
            header_tkhd.setBackground(new Color(230, 235, 240));
            header_tkhd.setForeground(COLOR_TEXT_DARK);
            header_tkhd.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
            header_tkhd.setReorderingAllowed(false);
            header_tkhd.setResizingAllowed(true);
        }

        table_tkhd.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null, null, null},
        	},
        	new String[] {
        		"Mã Hóa Đơn", "Mã Khách Hàng", "Ngày Lập HD", "Tên Khách Hàng", "SĐT KH", "Tên Nhân Viên", "SĐT NV", "Tổng Tiền"
        	}
        ));
        scP_tabletkhd.setViewportView(table_tkhd);
       
        // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM THUỐC ĐÃ SỬA =====
        JPanel pn_Timkiemthuoc = new JPanel();
        maincontent.add(pn_Timkiemthuoc, "timkiemSP");
        pn_Timkiemthuoc.setLayout(null);
        pn_Timkiemthuoc.setBackground(COLOR_BACKGROUND_PRIMARY); 

        JPanel pn_tkt_filters = new JPanel();
        pn_tkt_filters.setBackground(COLOR_CARD_BACKGROUND);
        pn_tkt_filters.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Bộ Lọc Tìm Kiếm", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_tkt_filters.setBounds(10, 68, 1679, 110); 
        pn_Timkiemthuoc.add(pn_tkt_filters);
        pn_tkt_filters.setLayout(null);


        JLabel lbl_tkthuoc = new JLabel("TÌM KIẾM THUỐC");
        lbl_tkthuoc.setFont(FONT_TITLE_MAIN); 
        lbl_tkthuoc.setForeground(COLOR_PRIMARY_BLUE); 
        lbl_tkthuoc.setHorizontalAlignment(SwingConstants.CENTER); 
        lbl_tkthuoc.setBounds(0, 11, 1689, 46); 
        pn_Timkiemthuoc.add(lbl_tkthuoc); 

        JLabel lbl_tkt_kethuoc = new JLabel("Kệ Thuốc :");
        lbl_tkt_kethuoc.setFont(FONT_LABEL_BOLD); 
        lbl_tkt_kethuoc.setForeground(COLOR_TEXT_DARK); 
        lbl_tkt_kethuoc.setBounds(119, 28, 100, 30);
        pn_tkt_filters.add(lbl_tkt_kethuoc); 

        JLabel lbl_tkt_Tenthuoc = new JLabel("Tên Thuốc :");
        lbl_tkt_Tenthuoc.setFont(FONT_LABEL_BOLD); 
        lbl_tkt_Tenthuoc.setForeground(COLOR_TEXT_DARK); 
        lbl_tkt_Tenthuoc.setBounds(119, 69, 100, 30); 
        pn_tkt_filters.add(lbl_tkt_Tenthuoc); 

        JLabel lbl_tkt_ncc = new JLabel("Nhà Cung Cấp :");
        lbl_tkt_ncc.setFont(FONT_LABEL_BOLD); 
        lbl_tkt_ncc.setForeground(COLOR_TEXT_DARK); 
        lbl_tkt_ncc.setBounds(871, 28, 135, 30); 
        pn_tkt_filters.add(lbl_tkt_ncc); 

        cb_tkt_kethuoc = new JComboBox<String>();
        cb_tkt_kethuoc.setFont(FONT_TEXT_FIELD); 
        cb_tkt_kethuoc.setBounds(229, 28, 529, 30); 
        pn_tkt_filters.add(cb_tkt_kethuoc); 

        cb_tkt_tenthuoc = new JComboBox<String>();
        cb_tkt_tenthuoc.setFont(FONT_TEXT_FIELD); 
        cb_tkt_tenthuoc.setBounds(229, 69, 529, 30); 
        pn_tkt_filters.add(cb_tkt_tenthuoc); 

        cb_tkt_ncc = new JComboBox<String>();
        cb_tkt_ncc.setFont(FONT_TEXT_FIELD); 
        cb_tkt_ncc.setBounds(1016, 28, 540, 30); 
        pn_tkt_filters.add(cb_tkt_ncc); 

        btn_tkt_xemchitiet = new JButton("Xem Chi Tiết");
        btn_tkt_xemchitiet.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ChiTietThuoc_GUI Chitietphieudatthuoc = new ChiTietThuoc_GUI(QuanLyHieuThuocTay);
        	}
        });
        btn_tkt_xemchitiet.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_tkt_xemchitiet, COLOR_PRIMARY_BLUE); 
        btn_tkt_xemchitiet.setBounds(1209, 69, 179, 36); 
        pn_tkt_filters.add(btn_tkt_xemchitiet); 

        btn_tkt_Lammoi = new JButton("Làm Mới");
        btn_tkt_Lammoi.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_tkt_Lammoi, COLOR_TEXT_MUTED); 
        btn_tkt_Lammoi.setBounds(1421, 69, 135, 36); 
        pn_tkt_filters.add(btn_tkt_Lammoi); 

        JScrollPane scP_tkt_table = new JScrollPane();
        scP_tkt_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); 
        scP_tkt_table.setBounds(10, 189, 1679, 739); 
        pn_Timkiemthuoc.add(scP_tkt_table); 

        table_tkt = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        applyCommonTableStyling(table_tkt); 
        table_tkt.setModel(new DefaultTableModel(
        	new Object[][] {}, 
        	new String[] {
        		"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Giá Nhập", "Giá Bán", "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Tên Kệ Thuốc"
        	}
        ));
        table_tkt.getColumnModel().getColumn(2).setPreferredWidth(65);
        scP_tkt_table.setViewportView(table_tkt);
        
     // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM KHÁCH HÀNG ĐÃ SỬA (GIỮ NGUYÊN TÊN BIẾN) =====
        JPanel pn_TKKhachHang = new JPanel();
        maincontent.add(pn_TKKhachHang, "timkiemkh");
        pn_TKKhachHang.setLayout(null);
        pn_TKKhachHang.setBackground(COLOR_BACKGROUND_PRIMARY); 

        JLabel lblHeader_TKKH = new JLabel("TÌM KIẾM KHÁCH HÀNG");
        lblHeader_TKKH.setFont(FONT_TITLE_MAIN); 
        lblHeader_TKKH.setForeground(COLOR_PRIMARY_BLUE); 
        lblHeader_TKKH.setHorizontalAlignment(SwingConstants.CENTER); 
        lblHeader_TKKH.setBounds(0, 11, 1689, 46);
        pn_TKKhachHang.add(lblHeader_TKKH);

        JPanel pnl_KH_north = new JPanel();
        pnl_KH_north.setBackground(COLOR_CARD_BACKGROUND);
        pnl_KH_north.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Bộ Lọc Tìm Kiếm", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        )); 
        pnl_KH_north.setBounds(10, 68, 1679, 177); 
        pn_TKKhachHang.add(pnl_KH_north);
        pnl_KH_north.setLayout(null);

        JLabel lbl_kh_MaKH = new JLabel("Mã khách hàng :");
        lbl_kh_MaKH.setFont(FONT_LABEL_BOLD); 
        lbl_kh_MaKH.setForeground(COLOR_TEXT_DARK); 
        lbl_kh_MaKH.setBounds(167, 38, 148, 30);
        pnl_KH_north.add(lbl_kh_MaKH);

        txt_kh_MaKH = new JTextField();
        txt_kh_MaKH.setFont(FONT_TEXT_FIELD); 
        txt_kh_MaKH.setBounds(325, 38, 471, 32);
        pnl_KH_north.add(txt_kh_MaKH);
        txt_kh_MaKH.setColumns(10);

        JLabel lbl_kh_SDT = new JLabel("Số điện thoại :");
        lbl_kh_SDT.setFont(FONT_LABEL_BOLD); 
        lbl_kh_SDT.setForeground(COLOR_TEXT_DARK);
        lbl_kh_SDT.setBounds(167, 86, 148, 30); 
        pnl_KH_north.add(lbl_kh_SDT);

        txt_kh_SDT = new JTextField();
        txt_kh_SDT.setFont(FONT_TEXT_FIELD); 
        txt_kh_SDT.setColumns(10);
        txt_kh_SDT.setBounds(325, 86, 471, 32); 
        pnl_KH_north.add(txt_kh_SDT);

        JLabel lbl_kh_TenKH = new JLabel("Tên khách hàng :");
        lbl_kh_TenKH.setFont(FONT_LABEL_BOLD); 
        lbl_kh_TenKH.setForeground(COLOR_TEXT_DARK); 
        lbl_kh_TenKH.setBounds(884, 38, 156, 30);
        pnl_KH_north.add(lbl_kh_TenKH);

        txt_kh_TenKH = new JTextField();
        txt_kh_TenKH.setFont(FONT_TEXT_FIELD); 
        txt_kh_TenKH.setColumns(10);
        txt_kh_TenKH.setBounds(1048, 38, 471, 32); 
        pnl_KH_north.add(txt_kh_TenKH);

        JLabel lbl_kh_Diachi = new JLabel("Địa chỉ :");
        lbl_kh_Diachi.setFont(FONT_LABEL_BOLD); 
        lbl_kh_Diachi.setForeground(COLOR_TEXT_DARK);
        lbl_kh_Diachi.setBounds(888, 86, 152, 30);
        pnl_KH_north.add(lbl_kh_Diachi);

        txt_kh_dc = new JTextField();
        txt_kh_dc.setFont(FONT_TEXT_FIELD); 
        txt_kh_dc.setColumns(10);
        txt_kh_dc.setBounds(1048, 86, 471, 32); 
        pnl_KH_north.add(txt_kh_dc);

        btn_kh_Lammoi = new JButton("Làm mới");
        btn_kh_Lammoi.setFont(FONT_BUTTON_STANDARD); 
        styleButton(btn_kh_Lammoi, COLOR_TEXT_MUTED); 
        btn_kh_Lammoi.setBounds(1401, 134, 118, 32); 
        pnl_KH_north.add(btn_kh_Lammoi);

        JScrollPane scrollPane_tkkh = new JScrollPane();
        scrollPane_tkkh.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); 
        scrollPane_tkkh.setBounds(10, 256, 1679, 734); 
        pn_TKKhachHang.add(scrollPane_tkkh); 

        table_tkkh = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        applyCommonTableStyling(table_tkkh); 
        table_tkkh.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"M\u00E3 kh\u00E1ch h\u00E0ng", "T\u00EAn kh\u00E1ch h\u00E0ng", "\u0110\u1ECBa ch\u1EC9", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i"
        	}
        ));
        table_tkkh.getColumnModel().getColumn(0).setPreferredWidth(99);
        table_tkkh.getColumnModel().getColumn(1).setPreferredWidth(92);

        scrollPane_tkkh.setViewportView(table_tkkh); 
        
        // ===== BẮT ĐẦU KHỐI CODE CẬP NHẬT KHÁCH HÀNG ĐÃ SỬA =====
        JPanel pn_CapnhatKh = new JPanel();
        maincontent.add(pn_CapnhatKh, "capNhatKH");
        pn_CapnhatKh.setLayout(null);
        pn_CapnhatKh.setBackground(COLOR_BACKGROUND_PRIMARY);

        JLabel lblHeader_CNKH = new JLabel("CẬP NHẬT THÔNG TIN KHÁCH HÀNG");
        lblHeader_CNKH.setFont(FONT_TITLE_MAIN);
        lblHeader_CNKH.setForeground(COLOR_PRIMARY_BLUE);
        lblHeader_CNKH.setHorizontalAlignment(SwingConstants.CENTER);
        lblHeader_CNKH.setBounds(0, 11, 1689, 46);
        pn_CapnhatKh.add(lblHeader_CNKH);

        JPanel pnlNorth = new JPanel();
        pnlNorth.setBackground(COLOR_CARD_BACKGROUND);
        pnlNorth.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlNorth.setBounds(10, 68, 1679, 241);
        pn_CapnhatKh.add(pnlNorth);
        pnlNorth.setLayout(null);

        JLabel lblMaKh = new JLabel("Mã khách hàng :");
        lblMaKh.setFont(FONT_LABEL_BOLD);
        lblMaKh.setForeground(COLOR_TEXT_DARK);
        lblMaKh.setBounds(192, 40, 141, 30);
        pnlNorth.add(lblMaKh);

        txt_cnkh_MaKh = new JTextField();
        txt_cnkh_MaKh.setFont(FONT_TEXT_FIELD);
        txt_cnkh_MaKh.setBounds(354, 40, 301, 33);
        txt_cnkh_MaKh.setEditable(false); // Giữ nguyên trạng thái không sửa được
        txt_cnkh_MaKh.setBackground(new Color(230, 230, 230)); // Giữ màu nền xám
        pnlNorth.add(txt_cnkh_MaKh);
        txt_cnkh_MaKh.setColumns(10);

        JLabel lblSdt = new JLabel("Số điện thoại :");
        lblSdt.setFont(FONT_LABEL_BOLD);
        lblSdt.setForeground(COLOR_TEXT_DARK);
        lblSdt.setBounds(192, 90, 141, 30);
        pnlNorth.add(lblSdt);

        txt_cnkh_SDt = new JTextField();
        txt_cnkh_SDt.setFont(FONT_TEXT_FIELD);
        txt_cnkh_SDt.setColumns(10);
        txt_cnkh_SDt.setBounds(354, 90, 301, 33);
        pnlNorth.add(txt_cnkh_SDt);

        JLabel lblTenKh = new JLabel("Tên khách hàng :");
        lblTenKh.setFont(FONT_LABEL_BOLD);
        lblTenKh.setForeground(COLOR_TEXT_DARK);
        lblTenKh.setBounds(785, 37, 163, 30);
        pnlNorth.add(lblTenKh);

        txt_cnkh_tenkh = new JTextField();
        txt_cnkh_tenkh.setFont(FONT_TEXT_FIELD);
        txt_cnkh_tenkh.setColumns(10);
        txt_cnkh_tenkh.setBounds(955, 37, 550, 33);
        pnlNorth.add(txt_cnkh_tenkh);

        JLabel lblDC = new JLabel("Địa chỉ  :");
        lblDC.setFont(FONT_LABEL_BOLD);
        lblDC.setForeground(COLOR_TEXT_DARK);
        lblDC.setBounds(785, 87, 163, 30);
        pnlNorth.add(lblDC);

        txt_cnkh_dc = new JTextField();
        txt_cnkh_dc.setFont(FONT_TEXT_FIELD);
        txt_cnkh_dc.setColumns(10);
        txt_cnkh_dc.setBounds(955, 87, 550, 33);
        pnlNorth.add(txt_cnkh_dc);

        btn_cnkh_Khoiphuc = new JButton("Khôi phục form");
        btn_cnkh_Khoiphuc.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_cnkh_Khoiphuc, COLOR_TEXT_MUTED);
        btn_cnkh_Khoiphuc.setBounds(955, 179, 155, 40);
        pnlNorth.add(btn_cnkh_Khoiphuc);

        btn_cnkh_CapNhat = new JButton("Cập nhật");
        btn_cnkh_CapNhat.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_cnkh_CapNhat, COLOR_SUCCESS_GREEN);
        btn_cnkh_CapNhat.setBounds(1150, 179, 130, 40);
        pnlNorth.add(btn_cnkh_CapNhat);
        
        btn_CNKH_Xoa = new JButton("Xoá");
        btn_CNKH_Xoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn_CNKH_Xoa.setBounds(1318, 179, 130, 40);
        pnlNorth.add(btn_CNKH_Xoa);

        JPanel pnlTimkiem = new JPanel();
        pnlTimkiem.setBackground(COLOR_CARD_BACKGROUND);
        Font titleFont = FONT_LABEL_BOLD; // Dùng font chuẩn
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
             "Tìm Kiếm Khách Hàng Cần Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
             titleFont, COLOR_PRIMARY_BLUE
        );
        pnlTimkiem.setBorder(border);
        pnlTimkiem.setBounds(10, 320, 1679, 84);
        pn_CapnhatKh.add(pnlTimkiem);
        pnlTimkiem.setLayout(null);

        JLabel lblMaKh_TK = new JLabel("Mã khách hàng :");
        lblMaKh_TK.setBounds(76, 27, 140, 30);
        lblMaKh_TK.setFont(FONT_LABEL_BOLD);
        lblMaKh_TK.setForeground(COLOR_TEXT_DARK);
        pnlTimkiem.add(lblMaKh_TK);

        txtMKH_TK = new JTextField();
        txtMKH_TK.setFont(FONT_TEXT_FIELD);
        txtMKH_TK.setColumns(10);
        txtMKH_TK.setBounds(226, 27, 422, 33);
        pnlTimkiem.add(txtMKH_TK);

        JLabel lblTenKh_TK = new JLabel("Tên khách hàng :");
        lblTenKh_TK.setFont(FONT_LABEL_BOLD);
        lblTenKh_TK.setForeground(COLOR_TEXT_DARK);
        lblTenKh_TK.setBounds(717, 27, 163, 30);
        pnlTimkiem.add(lblTenKh_TK);

        txtTenKH_TK = new JTextField();
        txtTenKH_TK.setFont(FONT_TEXT_FIELD);
        txtTenKH_TK.setColumns(10);
        txtTenKH_TK.setBounds(887, 27, 567, 33);
        pnlTimkiem.add(txtTenKH_TK);

        btnLammoi_CNKH = new JButton("Làm mới TK");
        btnLammoi_CNKH.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLammoi_CNKH, COLOR_TEXT_MUTED);
        btnLammoi_CNKH.setBounds(1519, 26, 130, 35);
        pnlTimkiem.add(btnLammoi_CNKH);

        JScrollPane scrollPane_CapNhatKH = new JScrollPane();
        scrollPane_CapNhatKH.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_CapNhatKH.setBounds(10, 415, 1679, 513);
        pn_CapnhatKh.add(scrollPane_CapNhatKH);

        table_CapNhatKH = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                // JTable table = (JTable) c.getParent(); // Bỏ dòng này
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground()); // Dùng this
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    // Không set foreground trắng nữa
                }
                return c;
            }
        };
        applyCommonTableStyling(table_CapNhatKH);
        table_CapNhatKH.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"M\u00E3 kh\u00E1ch h\u00E0ng", "T\u00EAn Kh\u00E1ch h\u00E0ng", "\u0110\u1ECBa ch\u1EC9", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i"
        	}
        ));
        scrollPane_CapNhatKH.setViewportView(table_CapNhatKH);

        // ===== KẾT THÚC KHỐI CODE CẬP NHẬT KHÁCH HÀNG ĐÃ SỬA =====
        
        // ===== BẮT ĐẦU KHỐI CODE CẬP NHẬT THUỐC ĐÃ SỬA =====
        JPanel pn_Capnhatthuoc = new JPanel();
        maincontent.add(pn_Capnhatthuoc, "Capnhatthuoc");
        pn_Capnhatthuoc.setLayout(null);
        pn_Capnhatthuoc.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lbl_Capnhatthuoc = new JLabel("CẬP NHẬT THÔNG TIN THUỐC"); // Tiêu đề
        lbl_Capnhatthuoc.setFont(FONT_TITLE_MAIN);
        lbl_Capnhatthuoc.setForeground(COLOR_PRIMARY_BLUE);
        lbl_Capnhatthuoc.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Capnhatthuoc.setBounds(0, 11, 1689, 59);
        pn_Capnhatthuoc.add(lbl_Capnhatthuoc); // Add vào panel chính

        // Panel chứa form nhập thông tin cập nhật thuốc
        JPanel pn_Capnhatthuoc_nhaptt = new JPanel();
        pn_Capnhatthuoc_nhaptt.setBackground(COLOR_CARD_BACKGROUND);
        pn_Capnhatthuoc_nhaptt.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Thuốc", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        )); // Viền và tiêu đề
        pn_Capnhatthuoc_nhaptt.setBounds(10, 93, 1679, 410); // Điều chỉnh vị trí, kích thước
        pn_Capnhatthuoc.add(pn_Capnhatthuoc_nhaptt);
        pn_Capnhatthuoc_nhaptt.setLayout(null);

        // Panel chứa ảnh
        JPanel pn_chuaanh = new JPanel();
        pn_chuaanh.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); // Viền nhạt
        pn_chuaanh.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền xám nhạt
        pn_chuaanh.setBounds(53, 40, 330, 300); // Điều chỉnh vị trí
        pn_Capnhatthuoc_nhaptt.add(pn_chuaanh);
        pn_chuaanh.setLayout(new BorderLayout(0, 0)); // Dùng BorderLayout

        lb_Chuaanh = new JLabel("Chưa có ảnh", SwingConstants.CENTER); // Text mặc định
        lb_Chuaanh.setFont(FONT_DETAIL_ITALIC); // Font italic
        lb_Chuaanh.setForeground(COLOR_TEXT_MUTED); // Màu nhạt
        pn_chuaanh.add(lb_Chuaanh, BorderLayout.CENTER);

        btn_cnt_ChonAnh = new JButton("Chọn Ảnh");
        btn_cnt_ChonAnh.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_cnt_ChonAnh, COLOR_TEXT_MUTED);
        btn_cnt_ChonAnh.setBounds(153, 351, 132, 40); // Điều chỉnh vị trí
        pn_Capnhatthuoc_nhaptt.add(btn_cnt_ChonAnh);

        // Các label và input fields
        int labelX = 400; // Vị trí X cột 1
        int inputX = 530; // Vị trí X cột 1
        int labelX2 = 1000; // Vị trí X cột 2
        int inputX2 = 1140; // Vị trí X cột 2
        int startY = 40; // Vị trí Y dòng đầu
        int height = 33; // Chiều cao component
        int vGap = 12; // Khoảng cách dọc

        JLabel lbl_cntmt = new JLabel("Mã Thuốc :");
        lbl_cntmt.setFont(FONT_LABEL_BOLD);
        lbl_cntmt.setForeground(COLOR_TEXT_DARK);
        lbl_cntmt.setBounds(488, 40, 120, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cntmt);

        text_cntmt = new JTextField();
        text_cntmt.setFont(FONT_TEXT_FIELD);
        text_cntmt.setBounds(618, 40, 400, height);
        text_cntmt.setEditable(false); // Mã thường không sửa
        text_cntmt.setBackground(new Color(230, 230, 230)); // Nền xám
        pn_Capnhatthuoc_nhaptt.add(text_cntmt);
        text_cntmt.setColumns(10);

        JLabel lbl_cnttt = new JLabel("Tên Thuốc :");
        lbl_cnttt.setFont(FONT_LABEL_BOLD);
        lbl_cnttt.setForeground(COLOR_TEXT_DARK);
        lbl_cnttt.setBounds(488, 85, 120, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cnttt);

        text_cnttt = new JTextField();
        text_cnttt.setFont(FONT_TEXT_FIELD);
        text_cnttt.setColumns(10);
        text_cnttt.setBounds(618, 85, 400, height);
        pn_Capnhatthuoc_nhaptt.add(text_cnttt);

        JLabel lbl_cntsl = new JLabel("Số Lượng :");
        lbl_cntsl.setFont(FONT_LABEL_BOLD);
        lbl_cntsl.setForeground(COLOR_TEXT_DARK);
        lbl_cntsl.setBounds(488, 130, 120, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cntsl);

        text_cntsl = new JTextField();
        text_cntsl.setFont(FONT_TEXT_FIELD);
        text_cntsl.setColumns(10);
        text_cntsl.setBounds(618, 130, 400, height);
        pn_Capnhatthuoc_nhaptt.add(text_cntsl);

        JLabel lbl_cntgn = new JLabel("Giá Nhập :");
        lbl_cntgn.setFont(FONT_LABEL_BOLD);
        lbl_cntgn.setForeground(COLOR_TEXT_DARK);
        lbl_cntgn.setBounds(488, 175, 120, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cntgn);

        text_cntgn = new JTextField();
        text_cntgn.setFont(FONT_TEXT_FIELD);
        text_cntgn.setColumns(10);
        text_cntgn.setBounds(618, 175, 400, height);
        pn_Capnhatthuoc_nhaptt.add(text_cntgn);

        JLabel lbl_cntgb = new JLabel("Giá Bán :");
        lbl_cntgb.setFont(FONT_LABEL_BOLD);
        lbl_cntgb.setForeground(COLOR_TEXT_DARK);
        lbl_cntgb.setBounds(488, 220, 120, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cntgb);

        text_cntgb = new JTextField();
        text_cntgb.setFont(FONT_TEXT_FIELD);
        text_cntgb.setColumns(10);
        text_cntgb.setBounds(618, 220, 400, height);
        pn_Capnhatthuoc_nhaptt.add(text_cntgb);

        JLabel lbl_cntdvt = new JLabel("Đơn Vị Tính :");
        lbl_cntdvt.setFont(FONT_LABEL_BOLD);
        lbl_cntdvt.setForeground(COLOR_TEXT_DARK);
        lbl_cntdvt.setBounds(488, 265, 120, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cntdvt);

        cb_cntdvt = new JComboBox<String>();
        cb_cntdvt.setFont(FONT_TEXT_FIELD);
        cb_cntdvt.setBounds(618, 265, 400, height);
        pn_Capnhatthuoc_nhaptt.add(cb_cntdvt);

        JLabel lbl_cntncc = new JLabel("Nhà Cung Cấp :");
        lbl_cntncc.setFont(FONT_LABEL_BOLD);
        lbl_cntncc.setForeground(COLOR_TEXT_DARK);
        lbl_cntncc.setBounds(1089, 40, 130, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cntncc);

        cb_cntncc = new JComboBox<String>();
        cb_cntncc.setFont(FONT_TEXT_FIELD);
        cb_cntncc.setBounds(1229, 40, 400, height);
        pn_Capnhatthuoc_nhaptt.add(cb_cntncc);

        JLabel lbl_cnthsd = new JLabel("Hạn Sử Dụng :");
        lbl_cnthsd.setFont(FONT_LABEL_BOLD);
        lbl_cnthsd.setForeground(COLOR_TEXT_DARK);
        lbl_cnthsd.setBounds(1089, 85, 130, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cnthsd);

        // Dùng JDateChooser cho HSD
        date_cnthsd = new JDateChooser();
        date_cnthsd.setFont(FONT_TEXT_FIELD);
        date_cnthsd.setDateFormatString("dd/MM/yyyy");
        date_cnthsd.setBounds(1229, 85, 400, height);
        pn_Capnhatthuoc_nhaptt.add(date_cnthsd);

        JLabel lbl_cnttkt = new JLabel("Tên Kệ Thuốc :");
        lbl_cnttkt.setFont(FONT_LABEL_BOLD);
        lbl_cnttkt.setForeground(COLOR_TEXT_DARK);
        lbl_cnttkt.setBounds(1089, 130, 130, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cnttkt);

        cb_cnt_tkt = new JComboBox<String>();
        cb_cnt_tkt.setFont(FONT_TEXT_FIELD);
        cb_cnt_tkt.setBounds(1229, 130, 400, height);
        pn_Capnhatthuoc_nhaptt.add(cb_cnt_tkt);

        JLabel lbl_cnttp = new JLabel("Thành Phần :");
        lbl_cnttp.setFont(FONT_LABEL_BOLD);
        lbl_cnttp.setForeground(COLOR_TEXT_DARK);
        lbl_cnttp.setBounds(1089, 175, 130, height);
        pn_Capnhatthuoc_nhaptt.add(lbl_cnttp);

        // Dùng JScrollPane cho JTextArea
        JScrollPane scrollPane_cnttp = new JScrollPane();
        scrollPane_cnttp.setBounds(1229, 188, 400, 110);
        pn_Capnhatthuoc_nhaptt.add(scrollPane_cnttp);
        
                textArea_cnttp = new JTextArea();
                scrollPane_cnttp.setViewportView(textArea_cnttp);
                textArea_cnttp.setWrapStyleWord(true);
                textArea_cnttp.setLineWrap(true);
                textArea_cnttp.setFont(FONT_TEXT_FIELD);

        btn_cntKhoiphuc = new JButton("Khôi Phục form");
        btn_cntKhoiphuc.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_cntKhoiphuc, COLOR_TEXT_MUTED);
        btn_cntKhoiphuc.setBounds(1069, 351, 150, 40); // Điều chỉnh vị trí
        pn_Capnhatthuoc_nhaptt.add(btn_cntKhoiphuc);

        btn_cntCapnhat  = new JButton("Cập Nhật");
        btn_cntCapnhat.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_cntCapnhat, COLOR_SUCCESS_GREEN);
        btn_cntCapnhat.setBounds(1248, 351, 132, 40); // Điều chỉnh vị trí
        pn_Capnhatthuoc_nhaptt.add(btn_cntCapnhat);
        
         btn_CNT_Xoa = new JButton("Xoá");
        btn_CNT_Xoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn_CNT_Xoa.setBounds(1411, 351, 132, 40);
        pn_Capnhatthuoc_nhaptt.add(btn_CNT_Xoa);

        // Panel chứa bộ lọc tìm kiếm thuốc cần cập nhật
        JPanel pn_Capnhatthuoc_tk = new JPanel();
        pn_Capnhatthuoc_tk.setBackground(COLOR_CARD_BACKGROUND);
        pn_Capnhatthuoc_tk.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Tìm Kiếm Thuốc Cần Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_Capnhatthuoc_tk.setBounds(10, 500, 1679, 80); // Điều chỉnh
        pn_Capnhatthuoc.add(pn_Capnhatthuoc_tk);
        pn_Capnhatthuoc_tk.setLayout(null);

        JLabel lbl_cnt_tkmt = new JLabel("Mã Thuốc :");
        lbl_cnt_tkmt.setBounds(80, 34, 91, 30); // Điều chỉnh
        lbl_cnt_tkmt.setFont(FONT_LABEL_BOLD);
        lbl_cnt_tkmt.setForeground(COLOR_TEXT_DARK);
        pn_Capnhatthuoc_tk.add(lbl_cnt_tkmt);

        text_cnt_tkmt = new JTextField();
        text_cnt_tkmt.setFont(FONT_TEXT_FIELD);
        text_cnt_tkmt.setColumns(10);
        text_cnt_tkmt.setBounds(181, 34, 198, 30); // Điều chỉnh
        pn_Capnhatthuoc_tk.add(text_cnt_tkmt);

        JLabel lbl_cnt_tktt = new JLabel("Tên Thuốc :");
        lbl_cnt_tktt.setFont(FONT_LABEL_BOLD);
        lbl_cnt_tktt.setForeground(COLOR_TEXT_DARK);
        lbl_cnt_tktt.setBounds(410, 34, 108, 30); // Điều chỉnh
        pn_Capnhatthuoc_tk.add(lbl_cnt_tktt);

        text_cnt_tktt = new JTextField();
        text_cnt_tktt.setFont(FONT_TEXT_FIELD);
        text_cnt_tktt.setColumns(10);
        text_cnt_tktt.setBounds(520, 34, 302, 30); // Điều chỉnh
        pn_Capnhatthuoc_tk.add(text_cnt_tktt);

        JLabel lbl_cnt_tktkt = new JLabel("Tên Kệ Thuốc :");
        lbl_cnt_tktkt.setFont(FONT_LABEL_BOLD);
        lbl_cnt_tktkt.setForeground(COLOR_TEXT_DARK);
        lbl_cnt_tktkt.setBounds(860, 34, 125, 30); // Điều chỉnh
        pn_Capnhatthuoc_tk.add(lbl_cnt_tktkt);

        cb_cnt_tktkt = new JComboBox<String>();
        cb_cnt_tktkt.setFont(FONT_TEXT_FIELD);
        cb_cnt_tktkt.setBounds(990, 34, 394, 30); // Điều chỉnh
        pn_Capnhatthuoc_tk.add(cb_cnt_tktkt);

        btn_cnt_tk_lammoi = new JButton("Làm Mới");
        btn_cnt_tk_lammoi.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_cnt_tk_lammoi, COLOR_TEXT_MUTED);
        btn_cnt_tk_lammoi.setBounds(1446, 29, 138, 40); // Điều chỉnh
        pn_Capnhatthuoc_tk.add(btn_cnt_tk_lammoi);

        // ScrollPane chứa bảng danh sách thuốc
        JScrollPane scP_cnt_table = new JScrollPane();
        scP_cnt_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_cnt_table.setBounds(10, 590, 1679, 338); // Điều chỉnh vị trí, kích thước
        pn_Capnhatthuoc.add(scP_cnt_table);

        // Tạo và style bảng table_Capnhatthuoc
        table_Capnhatthuoc = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_Capnhatthuoc);
        table_Capnhatthuoc.setModel(new DefaultTableModel(
        	new Object[][] {},
        	new String[] {
        		"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Giá Nhập", "Giá Bán", "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Tên Kệ Thuốc", "Thành Phần"
        	}
        ));
        // Giữ lại các setPreferredWidth
        table_Capnhatthuoc.getColumnModel().getColumn(0).setPreferredWidth(30);
        table_Capnhatthuoc.getColumnModel().getColumn(1).setPreferredWidth(130);
        table_Capnhatthuoc.getColumnModel().getColumn(2).setPreferredWidth(15);
        table_Capnhatthuoc.getColumnModel().getColumn(3).setPreferredWidth(15);
        table_Capnhatthuoc.getColumnModel().getColumn(4).setPreferredWidth(15);
        table_Capnhatthuoc.getColumnModel().getColumn(5).setPreferredWidth(15);
        table_Capnhatthuoc.getColumnModel().getColumn(6).setPreferredWidth(100);
        table_Capnhatthuoc.getColumnModel().getColumn(7).setPreferredWidth(25);
        table_Capnhatthuoc.getColumnModel().getColumn(8).setPreferredWidth(100);
        table_Capnhatthuoc.getColumnModel().getColumn(9).setPreferredWidth(300);
        scP_cnt_table.setViewportView(table_Capnhatthuoc);

        // ===== KẾT THÚC KHỐI CODE CẬP NHẬT THUỐC =====


        // ===== BẮT ĐẦU KHỐI CODE THÊM THUỐC (TỪNG LOẠI) ĐÃ SỬA =====
        JPanel pn_themthuoc1 = new JPanel(); // Panel chính cho tab này
        maincontent.add(pn_themthuoc1, "Themthuocthuong");
        pn_themthuoc1.setLayout(null);
        pn_themthuoc1.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lbl_Themthuoc = new JLabel("THÊM THUỐC MỚI"); // Tiêu đề
        lbl_Themthuoc.setFont(FONT_TITLE_MAIN);
        lbl_Themthuoc.setForeground(COLOR_PRIMARY_BLUE);
        lbl_Themthuoc.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_Themthuoc.setBounds(0, 11, 1689, 47);
        pn_themthuoc1.add(lbl_Themthuoc); // Add vào panel chính

        // Panel chứa form nhập thông tin thêm thuốc
        JPanel pn_Themthuoc = new JPanel(); // Giữ nguyên tên biến
        pn_Themthuoc.setBackground(COLOR_CARD_BACKGROUND);
        pn_Themthuoc.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Thuốc Mới", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_Themthuoc.setLayout(null);
        pn_Themthuoc.setBounds(10, 68, 1679, 410); // Điều chỉnh
        pn_themthuoc1.add(pn_Themthuoc); // Add vào panel chính

        // Panel chứa ảnh (tương tự panel Cập Nhật)
        JPanel pn_chuaanh_1 = new JPanel();
        pn_chuaanh_1.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        pn_chuaanh_1.setBackground(COLOR_BACKGROUND_PRIMARY);
        pn_chuaanh_1.setBounds(77, 40, 330, 300);
        pn_Themthuoc.add(pn_chuaanh_1);
        pn_chuaanh_1.setLayout(new BorderLayout(0, 0));

        lb_Chuaanh_tt = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lb_Chuaanh_tt.setFont(FONT_DETAIL_ITALIC);
        lb_Chuaanh_tt.setForeground(COLOR_TEXT_MUTED);
        pn_chuaanh_1.add(lb_Chuaanh_tt, BorderLayout.CENTER);

        btn_ChonAnh_tt = new JButton("Chọn Ảnh");
        btn_ChonAnh_tt.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_ChonAnh_tt, COLOR_TEXT_MUTED);
        btn_ChonAnh_tt.setBounds(168, 350, 132, 40);
        pn_Themthuoc.add(btn_ChonAnh_tt);

        // Các label và input fields (tương tự Cập Nhật, chỉ đổi tên biến)
        JLabel lbl_ttmt = new JLabel("Mã Thuốc :");
        lbl_ttmt.setFont(FONT_LABEL_BOLD);
        lbl_ttmt.setForeground(COLOR_TEXT_DARK);
        lbl_ttmt.setBounds(467, 40, 120, height);
        
        pn_Themthuoc.add(lbl_ttmt);

        text_ttmt = new JTextField();
        text_ttmt.setFont(FONT_TEXT_FIELD);
        text_ttmt.setColumns(10);
        text_ttmt.setBounds(597, 40, 400, height);
        text_ttmt.setEditable(false); // Mã thường tự sinh hoặc không cho nhập
        text_ttmt.setBackground(new Color(230, 230, 230));
        // daothuoc = new thuoc_DAO();
        String maThuocMoi = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_THUOC, null)).getData();
        text_ttmt.setText(maThuocMoi);
        text_ttmt.setEditable(false);
        text_ttmt.setFocusable(false);
        pn_Themthuoc.add(text_ttmt);

        JLabel lbl_tttt = new JLabel("Tên Thuốc :");
        lbl_tttt.setFont(FONT_LABEL_BOLD);
        lbl_tttt.setForeground(COLOR_TEXT_DARK);
        lbl_tttt.setBounds(467, 85, 120, height);
        pn_Themthuoc.add(lbl_tttt);

        text_tttt = new JTextField();
        text_tttt.setFont(FONT_TEXT_FIELD);
        text_tttt.setColumns(10);
        text_tttt.setBounds(597, 85, 400, height);
        pn_Themthuoc.add(text_tttt);

        JLabel lbl_ttsl = new JLabel("Số Lượng :");
        lbl_ttsl.setFont(FONT_LABEL_BOLD);
        lbl_ttsl.setForeground(COLOR_TEXT_DARK);
        lbl_ttsl.setBounds(467, 130, 120, height);
        pn_Themthuoc.add(lbl_ttsl);

        text_ttsl = new JTextField();
        text_ttsl.setFont(FONT_TEXT_FIELD);
        text_ttsl.setColumns(10);
        text_ttsl.setBounds(597, 130, 400, height);
        pn_Themthuoc.add(text_ttsl);

        JLabel lbl_ttgn = new JLabel("Giá Nhập :");
        lbl_ttgn.setFont(FONT_LABEL_BOLD);
        lbl_ttgn.setForeground(COLOR_TEXT_DARK);
        lbl_ttgn.setBounds(467, 175, 120, height);
        pn_Themthuoc.add(lbl_ttgn);

        text_ttgn = new JTextField();
        text_ttgn.setFont(FONT_TEXT_FIELD);
        text_ttgn.setColumns(10);
        text_ttgn.setBounds(597, 175, 400, height);
        pn_Themthuoc.add(text_ttgn);

        JLabel lbl_ttgb = new JLabel("Giá Bán :");
        lbl_ttgb.setFont(FONT_LABEL_BOLD);
        lbl_ttgb.setForeground(COLOR_TEXT_DARK);
        lbl_ttgb.setBounds(467, 220, 120, height);
        pn_Themthuoc.add(lbl_ttgb);

        text_ttgb = new JTextField();
        text_ttgb.setFont(FONT_TEXT_FIELD);
        text_ttgb.setColumns(10);
        text_ttgb.setBounds(597, 220, 400, height);
        pn_Themthuoc.add(text_ttgb);

        JLabel lbl_ttdvt = new JLabel("Đơn Vị Tính :");
        lbl_ttdvt.setFont(FONT_LABEL_BOLD);
        lbl_ttdvt.setForeground(COLOR_TEXT_DARK);
        lbl_ttdvt.setBounds(467, 265, 120, height);
        pn_Themthuoc.add(lbl_ttdvt);

        cb_ttdvt = new JComboBox<String>();
        cb_ttdvt.setFont(FONT_TEXT_FIELD);
        cb_ttdvt.setBounds(597, 265, 400, height);
        pn_Themthuoc.add(cb_ttdvt);

        JLabel lbl_ttncc = new JLabel("Nhà Cung Cấp :");
        lbl_ttncc.setFont(FONT_LABEL_BOLD);
        lbl_ttncc.setForeground(COLOR_TEXT_DARK);
        lbl_ttncc.setBounds(1058, 40, 130, height);
        pn_Themthuoc.add(lbl_ttncc);

        cb_ttncc = new JComboBox<String>();
        cb_ttncc.setFont(FONT_TEXT_FIELD);
        cb_ttncc.setBounds(1198, 40, 400, height);
        pn_Themthuoc.add(cb_ttncc);

        JLabel lbl_tthsd = new JLabel("Hạn Sử Dụng :");
        lbl_tthsd.setFont(FONT_LABEL_BOLD);
        lbl_tthsd.setForeground(COLOR_TEXT_DARK);
        lbl_tthsd.setBounds(1058, 85, 130, height);
        pn_Themthuoc.add(lbl_tthsd);

        // Dùng JDateChooser cho HSD
        date_tthsd = new JDateChooser(); // Tạo biến mới
        date_tthsd.setFont(FONT_TEXT_FIELD);
        date_tthsd.setDateFormatString("dd/MM/yyyy");
        date_tthsd.setBounds(1198, 85, 400, height);
        pn_Themthuoc.add(date_tthsd);
        // Đại Ca cần thay biến text_tthsd thành date_tthsd trong code logic

        JLabel lbl_tttkt = new JLabel("Tên Kệ Thuốc :");
        lbl_tttkt.setFont(FONT_LABEL_BOLD);
        lbl_tttkt.setForeground(COLOR_TEXT_DARK);
        lbl_tttkt.setBounds(1058, 130, 130, height);
        pn_Themthuoc.add(lbl_tttkt);

        cb_tttkt = new JComboBox<String>();
        cb_tttkt.setFont(FONT_TEXT_FIELD);
        cb_tttkt.setBounds(1198, 130, 400, height);
        pn_Themthuoc.add(cb_tttkt);

        JLabel lbl_tttp = new JLabel("Thành Phần :");
        lbl_tttp.setFont(FONT_LABEL_BOLD);
        lbl_tttp.setForeground(COLOR_TEXT_DARK);
        lbl_tttp.setBounds(1058, 175, 130, height);
        pn_Themthuoc.add(lbl_tttp);

        JScrollPane scrollPane_tttp = new JScrollPane(); // ScrollPane cho thành phần
        scrollPane_tttp.setBounds(1198, 178, 400, 110);
        pn_Themthuoc.add(scrollPane_tttp);

        textArea_tttp = new JTextArea();
        textArea_tttp.setWrapStyleWord(true);
        textArea_tttp.setLineWrap(true);
        textArea_tttp.setFont(FONT_TEXT_FIELD);
        scrollPane_tttp.setViewportView(textArea_tttp); // Add vào ScrollPane

        btn_ttLammoi = new JButton("Làm Mới");
        btn_ttLammoi.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_ttLammoi, COLOR_TEXT_MUTED);
        btn_ttLammoi.setBounds(1198, 332, 132, 40);
        pn_Themthuoc.add(btn_ttLammoi);

        btn_ttThem = new JButton("Thêm");
        btn_ttThem.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_ttThem, COLOR_PRIMARY_BLUE);
        btn_ttThem.setBounds(1367, 332, 138, 40); 
        pn_Themthuoc.add(btn_ttThem);

        // Bảng hiển thị danh sách thuốc vừa thêm (tạm thời)
        JScrollPane scP_cnt_table_1 = new JScrollPane();
        scP_cnt_table_1.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_cnt_table_1.setBounds(10, 489, 1679, 439); // Điều chỉnh
        pn_themthuoc1.add(scP_cnt_table_1); // Add vào panel chính

        // Style bảng table_1
        table_themthuoc = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_themthuoc);
        table_themthuoc.setModel(new DefaultTableModel(
        	new Object[][] {},
        	new String[] {
        		"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Giá Nhập", "Giá Bán", "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Tên Kệ Thuốc", "Thành Phần"
        	}
        ));
        // Giữ lại setPreferredWidth
        table_themthuoc.getColumnModel().getColumn(0).setPreferredWidth(30);
        table_themthuoc.getColumnModel().getColumn(1).setPreferredWidth(130);
        table_themthuoc.getColumnModel().getColumn(2).setPreferredWidth(15);
        table_themthuoc.getColumnModel().getColumn(3).setPreferredWidth(15);
        table_themthuoc.getColumnModel().getColumn(4).setPreferredWidth(15);
        table_themthuoc.getColumnModel().getColumn(5).setPreferredWidth(15);
        table_themthuoc.getColumnModel().getColumn(6).setPreferredWidth(100);
        table_themthuoc.getColumnModel().getColumn(7).setPreferredWidth(25);
        table_themthuoc.getColumnModel().getColumn(8).setPreferredWidth(100);
        table_themthuoc.getColumnModel().getColumn(9).setPreferredWidth(300);
        scP_cnt_table_1.setViewportView(table_themthuoc);

        // ===== KẾT THÚC KHỐI CODE THÊM THUỐC (TỪNG LOẠI) =====


        // ===== BẮT ĐẦU KHỐI CODE THÊM THUỐC (TỪ FILE) ĐÃ SỬA =====
        JPanel pn_themthuocfile = new JPanel();
        maincontent.add(pn_themthuocfile, "Themthuocfile");
        pn_themthuocfile.setLayout(null);
        pn_themthuocfile.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        // Panel chứa tiêu đề và các nút chức năng
        JPanel pn_ttf = new JPanel();
        pn_ttf.setBackground(COLOR_CARD_BACKGROUND);
        pn_ttf.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); // Viền
        pn_ttf.setBounds(10, 11, 1679, 123); // Vị trí
        pn_themthuocfile.add(pn_ttf);
        pn_ttf.setLayout(null);

        JLabel lbl_ttf_tieude = new JLabel("THÊM DANH SÁCH THUỐC TỪ FILE EXCEL"); // Tiêu đề
        lbl_ttf_tieude.setFont(FONT_TITLE_MAIN);
        lbl_ttf_tieude.setForeground(COLOR_PRIMARY_BLUE);
        lbl_ttf_tieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_ttf_tieude.setBounds(0, 11, 1564, 47); // Căn giữa
        pn_ttf.add(lbl_ttf_tieude);

        btn_ttf_chonfile = new JButton("Chọn File");
        btn_ttf_chonfile.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_ttf_chonfile, COLOR_PRIMARY_BLUE); // Màu xanh
        btn_ttf_chonfile.setBounds(40, 74, 139, 38); // Vị trí
        pn_ttf.add(btn_ttf_chonfile);

        lbl_ttf_tongsothuoc = new JLabel("Tổng số thuốc :");
        lbl_ttf_tongsothuoc.setFont(FONT_LABEL_BOLD);
        lbl_ttf_tongsothuoc.setForeground(COLOR_TEXT_DARK);
        lbl_ttf_tongsothuoc.setBounds(200, 74, 131, 38); // Điều chỉnh
        pn_ttf.add(lbl_ttf_tongsothuoc);

        lbl_ttfile_hienthitongsothuoc = new JLabel("0");
        lbl_ttfile_hienthitongsothuoc.setFont(FONT_LABEL_BOLD);
        lbl_ttfile_hienthitongsothuoc.setForeground(COLOR_DANGER_RED); // Màu đỏ cho số lượng
        lbl_ttfile_hienthitongsothuoc.setBounds(340, 74, 100, 38); // Điều chỉnh
        pn_ttf.add(lbl_ttfile_hienthitongsothuoc);

        btn_ttf_lammoi = new JButton("Làm Mới");
        btn_ttf_lammoi.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_ttf_lammoi, COLOR_TEXT_MUTED);
        btn_ttf_lammoi.setBounds(1094, 74, 139, 38);
        pn_ttf.add(btn_ttf_lammoi);

        btn_ttf_them = new JButton("Thêm");
        btn_ttf_them.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_ttf_them, COLOR_PRIMARY_BLUE);
        btn_ttf_them.setBounds(1254, 74, 139, 38);
        pn_ttf.add(btn_ttf_them);

        // ScrollPane chứa bảng hiển thị thuốc từ file
        JScrollPane scP_ttf_table = new JScrollPane();
        scP_ttf_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_ttf_table.setBounds(10, 145, 1679, 783); // Điều chỉnh
        pn_themthuocfile.add(scP_ttf_table);

        // Style bảng table_2
        table_ttf = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_ttf);
        table_ttf.setModel(new DefaultTableModel(
            	new Object[][] {},
            	new String[] {
            		"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Giá Nhập", "Giá Bán", "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Tên Kệ Thuốc", "Thành Phần"
            	}
            ));
        // Giữ lại setPreferredWidth
        table_ttf.getColumnModel().getColumn(0).setPreferredWidth(30);
        table_ttf.getColumnModel().getColumn(1).setPreferredWidth(130);
        table_ttf.getColumnModel().getColumn(2).setPreferredWidth(15);
        table_ttf.getColumnModel().getColumn(3).setPreferredWidth(15);
        table_ttf.getColumnModel().getColumn(4).setPreferredWidth(15);
        table_ttf.getColumnModel().getColumn(5).setPreferredWidth(15);
        table_ttf.getColumnModel().getColumn(6).setPreferredWidth(100);
        table_ttf.getColumnModel().getColumn(7).setPreferredWidth(25);
        table_ttf.getColumnModel().getColumn(8).setPreferredWidth(100);
        table_ttf.getColumnModel().getColumn(9).setPreferredWidth(300);
        scP_ttf_table.setViewportView(table_ttf);

        // ===== KẾT THÚC KHỐI CODE THÊM THUỐC (TỪ FILE) =====


        // ===== BẮT ĐẦU KHỐI CODE THUỐC SẮP HẾT HẠN ĐÃ SỬA =====
        JPanel pn_thuocsaphethan = new JPanel();
        maincontent.add(pn_thuocsaphethan, "Thuocsaphethan");
        pn_thuocsaphethan.setLayout(null);
        pn_thuocsaphethan.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lbl_tshh_tieude = new JLabel("DANH SÁCH THUỐC HẾT HẠN / SẮP HẾT HẠN");
        lbl_tshh_tieude.setFont(FONT_TITLE_MAIN);
        lbl_tshh_tieude.setForeground(COLOR_DANGER_RED); // Màu đỏ cảnh báo
        lbl_tshh_tieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_tshh_tieude.setBounds(0, 11, 1584, 40); // Căn giữa
        pn_thuocsaphethan.add(lbl_tshh_tieude);

        JScrollPane scP_tshh_table = new JScrollPane();
        scP_tshh_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_tshh_table.setBounds(10, 66, 1679, 873); // Điều chỉnh
        pn_thuocsaphethan.add(scP_tshh_table);

        // Style bảng table_tshh
        table_tshh = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                DefaultTableModel model = (DefaultTableModel) getModel();
                Object hanSuDungObj = model.getValueAt(row, 7); // cột Hạn Sử Dụng

                if (!isRowSelected(row)) {
                    LocalDate today = LocalDate.now();
                    if (hanSuDungObj != null && !hanSuDungObj.toString().isEmpty()) {
                        LocalDate han = LocalDate.parse(hanSuDungObj.toString());
                        if (han.isBefore(today)) {
                            // Thuốc đã hết hạn → nền đỏ nhạt, chữ trắng
                            c.setBackground(new Color(255, 102, 102));
                        } else {
                            // Xen kẽ màu nền bình thường
                            c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                            c.setForeground(getForeground());
                        }
                    } else {
                        c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                        c.setForeground(getForeground());
                    }
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };

        applyCommonTableStyling(table_tshh);
        table_tshh.setModel(new DefaultTableModel(
            	new Object[][] {},
            	new String[] {
            		"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Giá Nhập", "Giá Bán", "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Tên Kệ Thuốc", "Thành Phần"
            	}
            ));
        // Giữ lại setPreferredWidth
        table_tshh.getColumnModel().getColumn(0).setPreferredWidth(30);
        table_tshh.getColumnModel().getColumn(1).setPreferredWidth(130);
        table_tshh.getColumnModel().getColumn(2).setPreferredWidth(15);
        table_tshh.getColumnModel().getColumn(3).setPreferredWidth(15);
        table_tshh.getColumnModel().getColumn(4).setPreferredWidth(15);
        table_tshh.getColumnModel().getColumn(5).setPreferredWidth(15);
        table_tshh.getColumnModel().getColumn(6).setPreferredWidth(100);
        table_tshh.getColumnModel().getColumn(7).setPreferredWidth(25);
        table_tshh.getColumnModel().getColumn(8).setPreferredWidth(100);
        table_tshh.getColumnModel().getColumn(9).setPreferredWidth(300);
        scP_tshh_table.setViewportView(table_tshh);

        JButton btnBieuDo_tshhan = new JButton("Biểu đồ");
        btnBieuDo_tshhan.setFont(FONT_BUTTON_STANDARD);
        btnBieuDo_tshhan.setBounds(1355, 950, 152, 40); // đặt bên trái nút Xuất File
        btnBieuDo_tshhan.addActionListener(e -> {
            ThuocSapHetHan_Controller controller = new ThuocSapHetHan_Controller();
            var dataset = controller.taoDatasetBieuDo();

            if (dataset.getColumnCount() == 0) {
                JOptionPane.showMessageDialog(null, "Không có thuốc sắp hết hạn để vẽ biểu đồ!");
                return;
            }

            // Tạo biểu đồ
            JFreeChart chart = ChartFactory.createBarChart(
                    "Biểu đồ số ngày còn lại đến khi hết hạn",
                    "Tên thuốc", "Số ngày còn lại",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            // Tùy chỉnh màu sắc cho đẹp
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setRangeGridlinePaint(Color.GRAY);

            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 500));

            // Hiển thị trong dialog riêng
            JDialog dialog = new JDialog();
            dialog.setTitle("Biểu đồ thuốc sắp hết hạn");
            dialog.setModal(true);
            dialog.getContentPane().add(chartPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
        pn_thuocsaphethan.add(btnBieuDo_tshhan);

        
        JButton btn_tshh_xuatfile = new JButton("Xuất File");
        btn_tshh_xuatfile.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tshh_xuatfile, COLOR_SUCCESS_GREEN);
        btn_tshh_xuatfile.setBounds(1537, 950, 152, 40); // Điều chỉnh
        btn_tshh_xuatfile.addActionListener(e -> {
            ThuocSapHetHan_Controller controller = new ThuocSapHetHan_Controller();
            List<Thuoc> dsSapHetHan = controller.getDanhSachThuocHetHanVaSapHetHan();

            if (dsSapHetHan.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Không có thuốc sắp hết hạn để xuất!");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Chọn nơi lưu file Excel");
            chooser.setSelectedFile(new java.io.File("DanhSachThuocSapHetHan.xlsx"));
            int option = chooser.showSaveDialog(null);

            if (option == JFileChooser.APPROVE_OPTION) {
                String filePath = chooser.getSelectedFile().getAbsolutePath();
                controller.exportToExcel(dsSapHetHan, filePath);
                JOptionPane.showMessageDialog(null, "Xuất file thành công!");
            }
        });

        pn_thuocsaphethan.add(btn_tshh_xuatfile);
        
        JLabel lbl_Thuocdahethan = new JLabel("ĐỎ = THUỐC ĐÃ HẾT HẠN");
        lbl_Thuocdahethan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl_Thuocdahethan.setForeground(new Color(255, 0, 0));
        lbl_Thuocdahethan.setBounds(50, 950, 183, 29);
        pn_thuocsaphethan.add(lbl_Thuocdahethan);
        
        
        
        pn_thuocsaphethan.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                DefaultTableModel model = (DefaultTableModel) table_tshh.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ

                ThuocSapHetHan_Controller controller = new ThuocSapHetHan_Controller();
                List<Thuoc> ds = controller.getDanhSachThuocHetHanVaSapHetHan(); // <-- dùng hàm mới

                for (Thuoc t : ds) {
                    model.addRow(new Object[]{
                        t.getMaThuoc(),
                        t.getTenThuoc(),
                        t.getSoLuong(),
                        t.getGiaNhap(),
                        t.getGiaBan(),
                        t.getDonViTinh(),
                        (t.getNhaCungCap() != null ? t.getNhaCungCap().getMaNhaCungCap() : ""),
                        t.getHanSuDung(),
                        (t.getKeThuoc() != null ? t.getKeThuoc().getLoaiKe() : ""),
                        t.getThanhPhan()
                    });
                }
            }
        });


        // ===== KẾT THÚC KHỐI CODE THUỐC SẮP HẾT HẠN =====


        // ===== BẮT ĐẦU KHỐI CODE THUỐC BÁN CHẠY ĐÃ SỬA =====
        JPanel pn_thuocbanchay = new JPanel();
        maincontent.add(pn_thuocbanchay, "Thuocbanchay");
        pn_thuocbanchay.setLayout(null);
        pn_thuocbanchay.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lbl_tbc_tieude = new JLabel("DANH SÁCH THUỐC BÁN CHẠY"); // Tiêu đề
        lbl_tbc_tieude.setFont(FONT_TITLE_MAIN);
        lbl_tbc_tieude.setForeground(COLOR_SUCCESS_GREEN); // Màu xanh lá
        lbl_tbc_tieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_tbc_tieude.setBounds(0, 11, 1584, 57); // Căn giữa
        pn_thuocbanchay.add(lbl_tbc_tieude);

        JScrollPane scP_tbc_table = new JScrollPane();
        scP_tbc_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_tbc_table.setBounds(10, 75, 1679, 864); // Điều chỉnh
        pn_thuocbanchay.add(scP_tbc_table);

        // Style bảng table_5 (nên đổi tên biến này cho rõ nghĩa, vd: table_thuocBanChay)
        table_5 = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_5);
        table_5.setModel(new DefaultTableModel(
            	new Object[][] {}, // Bỏ dữ liệu mẫu null
            	new String[] { // Sửa lại tên cột và thêm cột "Số Lượng Bán"
            		"Mã Thuốc", "Tên Thuốc", "Số Lượng Bán", "Giá Bán", "Đơn Vị Tính", "Nhà Cung Cấp", "Hạn Sử Dụng", "Tên Kệ Thuốc"
            	}
            ));
        scP_tbc_table.setViewportView(table_5);

        JButton btn_tbc_xuatfile = new JButton("Xuất File");
        btn_tbc_xuatfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 1. Gọi Controller
                ThuocBanChay_Controller controller = new ThuocBanChay_Controller();
                
                // 2. Lấy danh sách (Lưu ý: List<Object[]>)
                List<Object[]> dsBanChay = controller.getDanhSachThuocBanChay();

                // 3. Kiểm tra rỗng (Giống file mẫu)
                if (dsBanChay.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Không có dữ liệu thuốc bán chạy để xuất!");
                    return;
                }

                // 4. Mở hộp thoại chọn file (Giống file mẫu)
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Chọn nơi lưu file Excel");
                chooser.setSelectedFile(new java.io.File("DanhSachThuocBanChay.xlsx")); // Đặt tên mặc định

                int option = chooser.showSaveDialog(null);

                if (option == JFileChooser.APPROVE_OPTION) {
                    String filePath = chooser.getSelectedFile().getAbsolutePath();
                    
                    // Fix lỗi quên đuôi file .xlsx nếu người dùng không gõ
                    if (!filePath.toLowerCase().endsWith(".xlsx")) {
                        filePath += ".xlsx";
                    }

                    // 5. Xuất file
                    controller.exportToExcel(dsBanChay, filePath);
                    JOptionPane.showMessageDialog(null, "Xuất file thành công!");
                    
                     // Mở file lên luôn cho tiện (Optional)
                    try {
                        java.awt.Desktop.getDesktop().open(new java.io.File(filePath));
                    } catch (Exception ex) {
                        // Kệ nó nếu không mở được
                    }
                }
            }
        });
        btn_tbc_xuatfile.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tbc_xuatfile, COLOR_SUCCESS_GREEN);
        btn_tbc_xuatfile.setBounds(1537, 950, 152, 40); // Điều chỉnh
        pn_thuocbanchay.add(btn_tbc_xuatfile);

     // ===== THÊM CODE TỪ ĐÂY =====
        // Khởi tạo Controller
        thuocBanChayController = new ThuocBanChay_Controller();

        // Thêm Listener để load dữ liệu khi panel "Thuốc Bán Chạy" được hiển thị
        pn_thuocbanchay.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                DefaultTableModel model = (DefaultTableModel) table_5.getModel();
                model.setRowCount(0); // Xóa dữ liệu cũ

                try {
                    List<Object[]> data = thuocBanChayController.getDanhSachThuocBanChay();
                    if (data.isEmpty()) {
                        System.out.println("Không có dữ liệu thuốc bán chạy."); // Hoặc hiện thông báo
                    }
                    for (Object[] rowData : data) {
                        model.addRow(rowData);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(QuanLyHieuThuocTay, // Hoặc this
                        "Lỗi khi tải dữ liệu thuốc bán chạy:\n" + ex.getMessage(),
                        "Lỗi Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // ===== KẾT THÚC CODE THÊM =====
        
        // ===== KẾT THÚC KHỐI CODE THUỐC BÁN CHẠY =====


//        // ===== BẮT ĐẦU KHỐI CODE THUỐC SẮP HẾT HÀNG ĐÃ SỬA =====
//        JPanel pn_thuocsaphethang = new JPanel();
//        maincontent.add(pn_thuocsaphethang, "Thuocsaphethang");
//        pn_thuocsaphethang.setLayout(null);
//        pn_thuocsaphethang.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
//
//        JLabel lbl_tshhan_tieude = new JLabel("DANH SÁCH THUỐC SẮP HẾT HÀNG"); // Tiêu đề
//        lbl_tshhan_tieude.setFont(FONT_TITLE_MAIN);
//        lbl_tshhan_tieude.setForeground(COLOR_DANGER_RED); // Màu đỏ cảnh báo
//        lbl_tshhan_tieude.setHorizontalAlignment(SwingConstants.CENTER);
//        lbl_tshhan_tieude.setBounds(0, 11, 1584, 57); // Căn giữa
//        pn_thuocsaphethang.add(lbl_tshhan_tieude);
//
//        JScrollPane scP_tshhan_table = new JScrollPane();
//        scP_tshhan_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
//        scP_tshhan_table.setBounds(10, 75, 1679, 864); // Điều chỉnh
//        pn_thuocsaphethang.add(scP_tshhan_table);
//
//        // Style bảng table_tshhan
//        table_tshhan = new JTable() {
//             @Override
//            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//                Component c = super.prepareRenderer(renderer, row, column);
//                if (!isRowSelected(row)) {
//                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
//                    c.setForeground(this.getForeground());
//                } else {
//                    c.setBackground(COLOR_PRIMARY_BLUE);
//                }
//                return c;
//            }
//        };
//        applyCommonTableStyling(table_tshhan);
//        table_tshhan.setModel(new DefaultTableModel(
//            	new Object[][] {},
//            	new String[] { // Chỉ cần hiện các cột cần thiết
//            		"Mã Thuốc", "Tên Thuốc", "Số Lượng Tồn", "Đơn Vị Tính", "Nhà Cung Cấp", "Tên Kệ Thuốc"
//            	}
//            ));
//        // Điều chỉnh setPreferredWidth nếu cần
//        // table_tshhan.getColumnModel().getColumn(0).setPreferredWidth(30); ...
//        scP_tshhan_table.setViewportView(table_tshhan);
//
//        JButton btn_tshhan_xuatfile = new JButton("Xuất File");
//        btn_tshhan_xuatfile.setFont(FONT_BUTTON_STANDARD);
//        styleButton(btn_tshhan_xuatfile, COLOR_SUCCESS_GREEN);
//        btn_tshhan_xuatfile.setBounds(1537, 950, 152, 40); // Điều chỉnh
//        pn_thuocsaphethang.add(btn_tshhan_xuatfile);
//
//        // ===== KẾT THÚC KHỐI CODE THUỐC SẮP HẾT HÀNG =====
        
        JPanel pn_thuocsaphethang = new JPanel();
        maincontent.add(pn_thuocsaphethang, "Thuocsaphethang");
        pn_thuocsaphethang.setLayout(null);
        pn_thuocsaphethang.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lbl_tshhan_tieude = new JLabel("DANH SÁCH THUỐC SẮP HẾT HÀNG"); // Tiêu đề
        lbl_tshhan_tieude.setFont(FONT_TITLE_MAIN);
        lbl_tshhan_tieude.setForeground(COLOR_DANGER_RED); // Màu đỏ cảnh báo
        lbl_tshhan_tieude.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_tshhan_tieude.setBounds(0, 11, 1584, 57); // Căn giữa
        pn_thuocsaphethang.add(lbl_tshhan_tieude);

        // --- PANEL ĐIỀU KHIỂN MỚI ---
        JPanel pn_tshhan_controls = new JPanel();
        pn_tshhan_controls.setBackground(COLOR_BACKGROUND_PRIMARY);
        pn_tshhan_controls.setLayout(null);
        pn_tshhan_controls.setBounds(10, 75, 1564, 50); // Panel nằm giữa tiêu đề và bảng
        pn_thuocsaphethang.add(pn_tshhan_controls);

        // Component 1: Tìm kiếm
        lbl_tshhan_timkiem = new JLabel("Tìm kiếm:");
        lbl_tshhan_timkiem.setFont(FONT_PLACEHOLDER);
        lbl_tshhan_timkiem.setBounds(10, 15, 70, 25);
        pn_tshhan_controls.add(lbl_tshhan_timkiem);

        text_tshhan_timkiem = new JTextField();
        text_tshhan_timkiem.setBounds(80, 10, 250, 30);
        pn_tshhan_controls.add(text_tshhan_timkiem);

        // Component 2: Đặt ngưỡng
        lbl_tshhan_nguong = new JLabel("Hiển thị thuốc có số lượng tồn <=");
        lbl_tshhan_nguong.setFont(FONT_PLACEHOLDER);
        lbl_tshhan_nguong.setBounds(360, 15, 200, 25);
        pn_tshhan_controls.add(lbl_tshhan_nguong);

        // Spinner cho phép chọn từ 1 đến 1000, mặc định là 20
        spinner_tshhan_nguong = new JSpinner();
        spinner_tshhan_nguong.setModel(new SpinnerNumberModel(20, 1, 1000, 1));
        spinner_tshhan_nguong.setFont(FONT_INPUT);
        spinner_tshhan_nguong.setBounds(570, 10, 70, 30);
        pn_tshhan_controls.add(spinner_tshhan_nguong);

        // Component 3: Nút Lọc
        btn_tshhan_loc = new JButton("Lọc");
        btn_tshhan_loc.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tshhan_loc, COLOR_PRIMARY_BLUE);
        btn_tshhan_loc.setBounds(650, 10, 100, 30);
        pn_tshhan_controls.add(btn_tshhan_loc);

        // Component 4: Nút Làm Mới
        btn_tshhan_lammoi = new JButton("Làm Mới");
        btn_tshhan_lammoi.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tshhan_lammoi, COLOR_DANGER_RED); // Màu vàng cho làm mới
        btn_tshhan_lammoi.setBounds(760, 10, 100, 30);
        pn_tshhan_controls.add(btn_tshhan_lammoi);
        // --- KẾT THÚC PANEL ĐIỀU KHIỂN ---


        JScrollPane scP_tshhan_table = new JScrollPane();
        scP_tshhan_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        // Dịch bảng xuống dưới panel điều khiển và giảm chiều cao
        scP_tshhan_table.setBounds(10, 130, 1679, 809); 
        pn_thuocsaphethang.add(scP_tshhan_table);

        // Style bảng table_tshhan (Giữ nguyên code của bạn)
        table_tshhan = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_tshhan);
        table_tshhan.setModel(new DefaultTableModel(
            	new Object[][] {},
            	new String[] { // Chỉ cần hiện các cột cần thiết
            		"Mã Thuốc", "Tên Thuốc", "Số Lượng Tồn", "Đơn Vị Tính", "Nhà Cung Cấp", "Tên Kệ Thuốc"
            	}
            ));
        // Cho phép sắp xếp khi nhấn vào tiêu đề cột (Quan trọng)
        table_tshhan.setAutoCreateRowSorter(true); 

        scP_tshhan_table.setViewportView(table_tshhan);

        // --- THÊM MENU CHUỘT PHẢI ---
        popup_tshhan_table = new JPopupMenu();
        item_tshhan_xemchitiet = new JMenuItem("Xem chi tiết thuốc");
        item_tshhan_xemncc = new JMenuItem("Xem thông tin nhà cung cấp");

        popup_tshhan_table.add(item_tshhan_xemchitiet);
        popup_tshhan_table.add(item_tshhan_xemncc);

        // Gắn JPopupMenu vào JTable
        table_tshhan.setComponentPopupMenu(popup_tshhan_table);
        // --- KẾT THÚC MENU CHUỘT PHẢI ---


        btn_tshhan_xuatfile = new JButton("Xuất File");
        btn_tshhan_xuatfile.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tshhan_xuatfile, COLOR_SUCCESS_GREEN);
        // Vị trí y của nút Xuất File vẫn như cũ (nằm dưới bảng)
        btn_tshhan_xuatfile.setBounds(1537, 950, 152, 40); 
        pn_thuocsaphethang.add(btn_tshhan_xuatfile);

        // Khởi tạo controller (Giữ nguyên 2 dòng của bạn)
        thuoc_controller = new Thuoc_Controller(this);
        thuocSapHetHang_controller = new ThuocSapHetHang_Controller(this); 
        // ===== KẾT THÚC KHỐI CODE THUỐC SẮP HẾT HÀNG ===== 
        
     // ===== BẮT ĐẦU KHỐI CODE THỐNG KÊ HÓA ĐƠN ĐÃ SỬA =====
        JPanel pn_ThongkeHD = new JPanel();
        maincontent.add(pn_ThongkeHD, "ThongkeHD");
        pn_ThongkeHD.setLayout(null);
        pn_ThongkeHD.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JPanel pn_tkhd_tieude = new JPanel();
        pn_tkhd_tieude.setOpaque(false); // Trong suốt để lấy nền cha
        pn_tkhd_tieude.setBounds(0, 0, 1689, 81);
        pn_ThongkeHD.add(pn_tkhd_tieude);
        pn_tkhd_tieude.setLayout(null);
        
        JLabel lbl_tkhd_tieude = new JLabel("THỐNG KÊ DOANH THU HÓA ĐƠN"); // Chữ rõ hơn
        lbl_tkhd_tieude.setFont(FONT_TITLE_MAIN); // Font tiêu đề
        lbl_tkhd_tieude.setForeground(COLOR_PRIMARY_BLUE); // Màu tiêu đề
        lbl_tkhd_tieude.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa
        lbl_tkhd_tieude.setBounds(0, 11, 1584, 59); // Căn giữa
        pn_tkhd_tieude.add(lbl_tkhd_tieude);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(FONT_LABEL_BOLD); // Font cho tab
        tabbedPane.setBounds(10, 80, 1679, 921); // Căn chỉnh
        pn_ThongkeHD.add(tabbedPane);
        
        // ========== TAB THỐNG KÊ THEO NGÀY ==========
        JPanel pn_tketheongay = new JPanel();
        pn_tketheongay.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền
        tabbedPane.addTab("Theo Ngày", null, pn_tketheongay, null);
        pn_tketheongay.setLayout(null);
        
        JLabel lbl_Ngaythongke = new JLabel("Chọn Ngày TK :");
        lbl_Ngaythongke.setBounds(30, 11, 143, 30);
        lbl_Ngaythongke.setFont(FONT_LABEL_BOLD);
        lbl_Ngaythongke.setForeground(COLOR_TEXT_DARK);
        pn_tketheongay.add(lbl_Ngaythongke);
        
        // Dùng JDateChooser
        date_tktn_ngay = new JDateChooser();
        date_tktn_ngay.setBounds(183, 11, 223, 30);
        date_tktn_ngay.setFont(FONT_TEXT_FIELD);
        date_tktn_ngay.setDateFormatString("dd/MM/yyyy");
        pn_tketheongay.add(date_tktn_ngay);
        
        JLabel lbl_tktn_tongsohd = new JLabel("Tổng Số Hóa Đơn :");
        lbl_tktn_tongsohd.setBounds(572, 11, 165, 30);
        lbl_tktn_tongsohd.setFont(FONT_LABEL_BOLD);
        lbl_tktn_tongsohd.setForeground(COLOR_TEXT_DARK);
        pn_tketheongay.add(lbl_tktn_tongsohd);
        
        lbl_tktn_hientongsohd = new JLabel("0"); // Giá trị mặc định
        lbl_tktn_hientongsohd.setBounds(747, 11, 143, 30);
        lbl_tktn_hientongsohd.setFont(FONT_LABEL_BOLD);
        lbl_tktn_hientongsohd.setForeground(COLOR_PRIMARY_BLUE);
        pn_tketheongay.add(lbl_tktn_hientongsohd);
        
                JLabel lbl_tktn_tongtiencachoadon = new JLabel("Tổng Doanh Thu :");
                lbl_tktn_tongtiencachoadon.setBounds(962, 11, 160, 30);
                lbl_tktn_tongtiencachoadon.setFont(FONT_LABEL_BOLD);
                lbl_tktn_tongtiencachoadon.setForeground(COLOR_TEXT_DARK);
                pn_tketheongay.add(lbl_tktn_tongtiencachoadon);
                
                lbl_tktn_hientongsotien = new JLabel("0 VND"); // Giá trị mặc định
                lbl_tktn_hientongsotien.setBounds(1130, 11, 250, 30);
                lbl_tktn_hientongsotien.setFont(FONT_SUMMARY_TOTAL);
                lbl_tktn_hientongsotien.setForeground(COLOR_SUCCESS_GREEN);
                pn_tketheongay.add(lbl_tktn_hientongsotien);
                
                // -- Panel bên trái (Lọc nhân viên) --
                JPanel pn_tktn_tktnv = new JPanel();
                pn_tktn_tktnv.setBounds(10, 52, 480, 834);
                pn_tktn_tktnv.setOpaque(false);
                pn_tketheongay.add(pn_tktn_tktnv);
                pn_tktn_tktnv.setLayout(null);
                
                JPanel pn_tktn_tktnv_boloc = new JPanel();
                pn_tktn_tktnv_boloc.setBackground(COLOR_CARD_BACKGROUND);
                pn_tktn_tktnv_boloc.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                    "Lọc Theo Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP,
                    FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
                ));
                pn_tktn_tktnv_boloc.setBounds(10, 11, 459, 230);
                pn_tktn_tktnv.add(pn_tktn_tktnv_boloc);
                pn_tktn_tktnv_boloc.setLayout(null);
                
                JLabel lbl_tktn_tktnv_boloc = new JLabel("Thống Kê Theo :");
                lbl_tktn_tktnv_boloc.setBounds(21, 25, 142, 30);
                lbl_tktn_tktnv_boloc.setFont(FONT_LABEL_BOLD);
                lbl_tktn_tktnv_boloc.setForeground(COLOR_TEXT_DARK);
                pn_tktn_tktnv_boloc.add(lbl_tktn_tktnv_boloc);
                
                cb_tktn_tktnv_boloc = new JComboBox<String>();
                cb_tktn_tktnv_boloc.setFont(FONT_TEXT_FIELD);
                cb_tktn_tktnv_boloc.setBounds(173, 25, 258, 30);
                pn_tktn_tktnv_boloc.add(cb_tktn_tktnv_boloc);
                
                pn_tktn_tktnv_boloc_pntk = new JPanel();
                pn_tktn_tktnv_boloc_pntk.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                    "Tìm Kiếm Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP,
                    FONT_LABEL_BOLD, COLOR_TEXT_MUTED // Màu nhạt hơn
                ));
                pn_tktn_tktnv_boloc_pntk.setBackground(COLOR_CARD_BACKGROUND);
                pn_tktn_tktnv_boloc_pntk.setBounds(10, 66, 437, 153);
                pn_tktn_tktnv_boloc.add(pn_tktn_tktnv_boloc_pntk);
                pn_tktn_tktnv_boloc_pntk.setLayout(null);
                
                JLabel lbl_tktn_tktnv_boloc_manv = new JLabel("Mã Nhân Viên :");
                lbl_tktn_tktnv_boloc_manv.setFont(FONT_LABEL_BOLD);
                lbl_tktn_tktnv_boloc_manv.setForeground(COLOR_TEXT_DARK);
                lbl_tktn_tktnv_boloc_manv.setBounds(10, 33, 142, 30);
                pn_tktn_tktnv_boloc_pntk.add(lbl_tktn_tktnv_boloc_manv);
                
                JLabel lbl_tktn_tktnv_boloc_tennv = new JLabel("Tên Nhân Viên :");
                lbl_tktn_tktnv_boloc_tennv.setFont(FONT_LABEL_BOLD);
                lbl_tktn_tktnv_boloc_tennv.setForeground(COLOR_TEXT_DARK);
                lbl_tktn_tktnv_boloc_tennv.setBounds(10, 85, 142, 30);
                pn_tktn_tktnv_boloc_pntk.add(lbl_tktn_tktnv_boloc_tennv);
                
                text_tktn_tktnv_boloc_manv = new JTextField();
                text_tktn_tktnv_boloc_manv.setFont(FONT_TEXT_FIELD);
                text_tktn_tktnv_boloc_manv.setColumns(10);
                text_tktn_tktnv_boloc_manv.setBounds(162, 33, 261, 30);
                pn_tktn_tktnv_boloc_pntk.add(text_tktn_tktnv_boloc_manv);
                
                text_tktn_tktnv_boloc_tennv = new JTextField();
                text_tktn_tktnv_boloc_tennv.setFont(FONT_TEXT_FIELD);
                text_tktn_tktnv_boloc_tennv.setColumns(10);
                text_tktn_tktnv_boloc_tennv.setBounds(162, 85, 261, 30);
                pn_tktn_tktnv_boloc_pntk.add(text_tktn_tktnv_boloc_tennv);
                
                scrollPane_5 = new JScrollPane();
                scrollPane_5.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
                scrollPane_5.setBounds(10, 252, 459, 571);
                pn_tktn_tktnv.add(scrollPane_5);
                
                // Áp dụng đúng cách tạo bảng table_8
                table_8 = new JTable() {
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (!isRowSelected(row)) {
                            c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                        } else {
                            c.setBackground(COLOR_PRIMARY_BLUE);
                            
                        }
                        return c;
                    }
                };
                applyCommonTableStyling(table_8); // Áp dụng style chung
                table_8.setModel(new DefaultTableModel(
                    new Object[][] {},
                    new String[] {"Mã Nhân Viên", "Tên Nhân Viên"}
                ));
                scrollPane_5.setViewportView(table_8);
                
                // -- Bảng bên phải (Danh sách hóa đơn) --
                JScrollPane scP_tktn_tktnv_table = new JScrollPane();
                scP_tktn_tktnv_table.setBounds(500, 52, 1175, 770);
                scP_tktn_tktnv_table.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
                pn_tketheongay.add(scP_tktn_tktnv_table);
                
                // Áp dụng đúng cách tạo bảng table_tktn
                table_tktn = new JTable() {
                     @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (!isRowSelected(row)) {
                            c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                        } else {
                            c.setBackground(COLOR_PRIMARY_BLUE);
                            
                        }
                        return c;
                    }
                };
                applyCommonTableStyling(table_tktn); // Áp dụng style chung
                table_tktn.setModel(new DefaultTableModel(
                    new Object[][] {},
                    new String[] {"Mã Hóa Đơn", "SĐT Khách Hàng", "Tên Khách Hàng", "Mã Nhân Viên", "Tên Nhân Viên", "Tổng Tiền"}
                ));
                scP_tktn_tktnv_table.setViewportView(table_tktn);
                
                JButton btn_tktn_Xuatfile = new JButton("Xuất File");
                btn_tktn_Xuatfile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (table_tktn.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(QuanLyHieuThuocTay, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        // Lấy ngày tháng từ JDateChooser để đặt tên file
                        String tenFile = "ThongKeTheoNgay_TongHop";
                        if (date_tktn_ngay.getDate() != null) {
                            tenFile = "ThongKeNgay_" + new SimpleDateFormat("dd-MM-yyyy").format(date_tktn_ngay.getDate());
                        }
                        
                        exportTableToExcel(table_tktn, tenFile);
                    }
                });
                btn_tktn_Xuatfile.setBounds(1521, 833, 143, 42);
                btn_tktn_Xuatfile.setFont(FONT_BUTTON_STANDARD);
                styleButton(btn_tktn_Xuatfile, COLOR_SUCCESS_GREEN);
                pn_tketheongay.add(btn_tktn_Xuatfile);
        
        // ========== TAB THỐNG KÊ THEO THÁNG ==========
        JPanel pn_tketheothang = new JPanel();
        pn_tketheothang.setBackground(COLOR_BACKGROUND_PRIMARY);
        tabbedPane.addTab("Theo Tháng", null, pn_tketheothang, null);
        pn_tketheothang.setLayout(null);
        
        JLabel lbl_thang = new JLabel("Tháng :");
        lbl_thang.setFont(FONT_LABEL_BOLD);
        lbl_thang.setForeground(COLOR_TEXT_DARK);
        lbl_thang.setBounds(20, 11, 69, 30);
        pn_tketheothang.add(lbl_thang);

        // Dùng JMonthChooser
        month_tktt = new JMonthChooser();
        month_tktt.setFont(FONT_TEXT_FIELD);
        month_tktt.setBounds(99, 11, 135, 30);
        pn_tketheothang.add(month_tktt);

        JLabel lbl_nam = new JLabel("Năm :");
        lbl_nam.setFont(FONT_LABEL_BOLD);
        lbl_nam.setForeground(COLOR_TEXT_DARK);
        lbl_nam.setBounds(244, 11, 55, 30);
        pn_tketheothang.add(lbl_nam);

        // Dùng JYearChooser
        year_tktt = new JYearChooser();
        year_tktt.setFont(FONT_TEXT_FIELD);
        year_tktt.setBounds(309, 11, 100, 30); // Giảm chiều rộng
        pn_tketheothang.add(year_tktt);
        
        JLabel lbl_tktt_tongsohd = new JLabel("Tổng Số Hóa Đơn :");
        lbl_tktt_tongsohd.setFont(FONT_LABEL_BOLD);
        lbl_tktt_tongsohd.setForeground(COLOR_TEXT_DARK);
        lbl_tktt_tongsohd.setBounds(504, 11, 165, 30);
        pn_tketheothang.add(lbl_tktt_tongsohd);
        
        lbl_tktt_hiensohd  = new JLabel("0");
        lbl_tktt_hiensohd.setFont(FONT_LABEL_BOLD);
        lbl_tktt_hiensohd.setForeground(COLOR_PRIMARY_BLUE);
        lbl_tktt_hiensohd.setBounds(679, 11, 143, 30);
        pn_tketheothang.add(lbl_tktt_hiensohd);

        JLabel lbl_tktt_Tongtiencachd = new JLabel("Tổng Doanh Thu :");
        lbl_tktt_Tongtiencachd.setFont(FONT_LABEL_BOLD);
        lbl_tktt_Tongtiencachd.setForeground(COLOR_TEXT_DARK);
        lbl_tktt_Tongtiencachd.setBounds(900, 11, 160, 30);
        pn_tketheothang.add(lbl_tktt_Tongtiencachd);
        
        lbl_tktt_hientongtienhd = new JLabel("0 VND");
        lbl_tktt_hientongtienhd.setFont(FONT_SUMMARY_TOTAL);
        lbl_tktt_hientongtienhd.setForeground(COLOR_SUCCESS_GREEN);
        lbl_tktt_hientongtienhd.setBounds(1070, 11, 250, 30);
        pn_tketheothang.add(lbl_tktt_hientongtienhd);
        
        // -- Panel bên trái (Lọc nhân viên) --
        JPanel pn_tktt_tk = new JPanel(); // Giữ nguyên tên biến
        pn_tktt_tk.setOpaque(false);
        pn_tktt_tk.setLayout(null);
        pn_tktt_tk.setBounds(10, 52, 480, 834);
        pn_tketheothang.add(pn_tktt_tk);
        
        // (Code bên trong pn_tktt_tk giống hệt pn_tktn_tktnv, chỉ thay tên biến)
        JPanel pn_tktt_tk_boloc = new JPanel();
        pn_tktt_tk_boloc.setBackground(COLOR_CARD_BACKGROUND);
        pn_tktt_tk_boloc.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true), "Lọc Theo Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE));
        pn_tktt_tk_boloc.setBounds(10, 11, 459, 230);
        pn_tktt_tk.add(pn_tktt_tk_boloc);
        pn_tktt_tk_boloc.setLayout(null);
        
        JLabel lbl_tktt_tk_boloc_thongktheonv = new JLabel("Thống Kê Theo :");
        lbl_tktt_tk_boloc_thongktheonv.setFont(FONT_LABEL_BOLD);
        lbl_tktt_tk_boloc_thongktheonv.setForeground(COLOR_TEXT_DARK);
        lbl_tktt_tk_boloc_thongktheonv.setBounds(21, 25, 142, 30);
        pn_tktt_tk_boloc.add(lbl_tktt_tk_boloc_thongktheonv);
        
        cb_tktt_tknv = new JComboBox<String>();
        cb_tktt_tknv.setFont(FONT_TEXT_FIELD);
        cb_tktt_tknv.setBounds(173, 25, 258, 30);
        pn_tktt_tk_boloc.add(cb_tktt_tknv);
        
        pn_tktt_tk_boloc_tknv = new JPanel();
        pn_tktt_tk_boloc_tknv.setLayout(null);
        pn_tktt_tk_boloc_tknv.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true), "Tìm Kiếm Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL_BOLD, COLOR_TEXT_MUTED));
        pn_tktt_tk_boloc_tknv.setBackground(Color.WHITE);
        pn_tktt_tk_boloc_tknv.setBounds(10, 66, 437, 153);
        pn_tktt_tk_boloc.add(pn_tktt_tk_boloc_tknv);
        
        JLabel lbl_tktt_tk_boloc_manv = new JLabel("Mã Nhân Viên :");
        lbl_tktt_tk_boloc_manv.setFont(FONT_LABEL_BOLD);
        lbl_tktt_tk_boloc_manv.setForeground(COLOR_TEXT_DARK);
        lbl_tktt_tk_boloc_manv.setBounds(10, 33, 142, 30);
        pn_tktt_tk_boloc_tknv.add(lbl_tktt_tk_boloc_manv);
        
        JLabel lbl__tktt_tk_boloc_tennv = new JLabel("Tên Nhân Viên :");
        lbl__tktt_tk_boloc_tennv.setFont(FONT_LABEL_BOLD);
        lbl__tktt_tk_boloc_tennv.setForeground(COLOR_TEXT_DARK);
        lbl__tktt_tk_boloc_tennv.setBounds(10, 85, 142, 30);
        pn_tktt_tk_boloc_tknv.add(lbl__tktt_tk_boloc_tennv);
        
        text_tktt_tk_boloc_manv = new JTextField();
        text_tktt_tk_boloc_manv.setFont(FONT_TEXT_FIELD);
        text_tktt_tk_boloc_manv.setColumns(10);
        text_tktt_tk_boloc_manv.setBounds(162, 33, 261, 30);
        pn_tktt_tk_boloc_tknv.add(text_tktt_tk_boloc_manv);
        
        text_tktt_tk_boloc_tennv = new JTextField();
        text_tktt_tk_boloc_tennv.setFont(FONT_TEXT_FIELD);
        text_tktt_tk_boloc_tennv.setColumns(10);
        text_tktt_tk_boloc_tennv.setBounds(162, 85, 261, 30);
        pn_tktt_tk_boloc_tknv.add(text_tktt_tk_boloc_tennv);
        
        scP_tktt = new JScrollPane();
        scP_tktt.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_tktt.setBounds(10, 252, 459, 571);
        pn_tktt_tk.add(scP_tktt);
        
        // Áp dụng đúng cách tạo bảng table_ttkt
        table_ttkt = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_ttkt); // Áp dụng style chung
        table_ttkt.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã Nhân Viên", "Tên Nhân Viên"}
        ));
        scP_tktt.setViewportView(table_ttkt);

        // -- Bảng giữa (Thống kê theo ngày trong tháng) --
        JScrollPane scP_tablehienhd = new JScrollPane();
        scP_tablehienhd.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_tablehienhd.setBounds(500, 52, 436, 823);
        pn_tketheothang.add(scP_tablehienhd);
        
        // Áp dụng đúng cách tạo bảng table_hienhd_tktt
        table_hienhd_tktt = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        applyCommonTableStyling(table_hienhd_tktt); // Áp dụng style chung
        table_hienhd_tktt.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Ngày", "Tổng Số Hóa Đơn", "Tổng Tiền Các Hóa Đơn"}      
        ));
        scP_tablehienhd.setViewportView(table_hienhd_tktt);
        
        btn_tktt_xemchitiet = new JButton("Xem Chi tiết");
        btn_tktt_xemchitiet.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ThongkeHDThang_GUI ThongkeHDThang =new ThongkeHDThang_GUI(QuanLyHieuThuocTay);
            }
        });
        btn_tktt_xemchitiet.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tktt_xemchitiet, COLOR_PRIMARY_BLUE);
        btn_tktt_xemchitiet.setBounds(1521, 833, 143, 42);
        pn_tketheothang.add(btn_tktt_xemchitiet);
        
        // -- Khu vực biểu đồ (Đại Ca sẽ tự thêm component biểu đồ vào đây) --
        pn_bieudo_thang = new JPanel();
        pn_bieudo_thang.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        pn_bieudo_thang.setBackground(COLOR_CARD_BACKGROUND);
        pn_bieudo_thang.setBounds(946, 96, 718, 726);
        pn_tketheothang.add(pn_bieudo_thang);
        pn_bieudo_thang.setLayout(new BorderLayout(0, 0)); // Để chứa biểu đồ

        JLabel lbl_tieudebd_tktt = new JLabel("Biểu Đồ Doanh Thu Tháng:");
        lbl_tieudebd_tktt.setFont(FONT_TITLE_SECTION);
        lbl_tieudebd_tktt.setForeground(COLOR_TEXT_DARK);
        lbl_tieudebd_tktt.setBounds(946, 52, 350, 35);
        pn_tketheothang.add(lbl_tieudebd_tktt);
        
        lbl_hienthangvanam = new JLabel("..."); // Sẽ cập nhật sau
        lbl_hienthangvanam.setFont(FONT_TITLE_SECTION);
        lbl_hienthangvanam.setForeground(COLOR_PRIMARY_BLUE);
        lbl_hienthangvanam.setBounds(1300, 52, 254, 35);
        pn_tketheothang.add(lbl_hienthangvanam);
        
        // ========== TAB THỐNG KÊ THEO NĂM ==========
        JPanel pn_tketheonam = new JPanel();
        pn_tketheonam.setBackground(COLOR_BACKGROUND_PRIMARY);
        tabbedPane.addTab("Theo Năm", null, pn_tketheonam, null);
        pn_tketheonam.setLayout(null);
        
        JLabel lbl_namthongke = new JLabel("Chọn Năm TK :");
        lbl_namthongke.setFont(FONT_LABEL_BOLD);
        lbl_namthongke.setForeground(COLOR_TEXT_DARK);
        lbl_namthongke.setBounds(26, 11, 143, 30);
        pn_tketheonam.add(lbl_namthongke);

        // Dùng JYearChooser
        year_tktn = new JYearChooser();
        year_tktn.setFont(FONT_TEXT_FIELD);
        year_tktn.setBounds(179, 11, 100, 30); // Giảm chiều rộng
        pn_tketheonam.add(year_tktn);
        
        JLabel lbl_tktnam_tongsohd = new JLabel("Tổng Số Hóa Đơn :");
        lbl_tktnam_tongsohd.setFont(FONT_LABEL_BOLD);
        lbl_tktnam_tongsohd.setForeground(COLOR_TEXT_DARK);
        lbl_tktnam_tongsohd.setBounds(500, 11, 165, 30);
        pn_tketheonam.add(lbl_tktnam_tongsohd);
        
        lbl_tktn_hientshd = new JLabel("0");
        lbl_tktn_hientshd.setFont(FONT_LABEL_BOLD);
        lbl_tktn_hientshd.setForeground(COLOR_PRIMARY_BLUE);
        lbl_tktn_hientshd.setBounds(675, 11, 143, 30);
        pn_tketheonam.add(lbl_tktn_hientshd);
        
        JLabel lbl_tktnam_tongtiencachd = new JLabel("Tổng Doanh Thu :");
        lbl_tktnam_tongtiencachd.setFont(FONT_LABEL_BOLD);
        lbl_tktnam_tongtiencachd.setForeground(COLOR_TEXT_DARK);
        lbl_tktnam_tongtiencachd.setBounds(900, 11, 160, 30);
        pn_tketheonam.add(lbl_tktnam_tongtiencachd);
        
        lbl_tktnam_hientongsotien = new JLabel("0 VND");
        lbl_tktnam_hientongsotien.setFont(FONT_SUMMARY_TOTAL);
        lbl_tktnam_hientongsotien.setForeground(COLOR_SUCCESS_GREEN);
        lbl_tktnam_hientongsotien.setBounds(1070, 11, 250, 30);
        pn_tketheonam.add(lbl_tktnam_hientongsotien);

        // -- Panel bên trái (Lọc nhân viên) --
        JPanel pn_tktnam_tk = new JPanel(); // Giữ tên biến
        pn_tktnam_tk.setOpaque(false);
        pn_tktnam_tk.setLayout(null);
        pn_tktnam_tk.setBounds(10, 52, 480, 834);
        pn_tketheonam.add(pn_tktnam_tk);
        
        // (Code bên trong pn_tktnam_tk giống hệt pn_tktn_tktnv)
        JPanel pn_tktnam_tk_boloc = new JPanel();
        pn_tktnam_tk_boloc.setBackground(COLOR_CARD_BACKGROUND);
        pn_tktnam_tk_boloc.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true), "Lọc Theo Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE));
        pn_tktnam_tk_boloc.setBounds(10, 11, 459, 230);
        pn_tktnam_tk.add(pn_tktnam_tk_boloc);
        pn_tktnam_tk_boloc.setLayout(null);

        JLabel lbl_tktn_chonnamtk = new JLabel("Thống Kê Theo :");
        lbl_tktn_chonnamtk.setFont(FONT_LABEL_BOLD);
        lbl_tktn_chonnamtk.setForeground(COLOR_TEXT_DARK);
        lbl_tktn_chonnamtk.setBounds(21, 25, 142, 30);
        pn_tktnam_tk_boloc.add(lbl_tktn_chonnamtk);

        cb_chonnamtk = new JComboBox<String>();
        cb_chonnamtk.setFont(FONT_TEXT_FIELD);
        cb_chonnamtk.setBounds(173, 25, 258, 30);
        pn_tktnam_tk_boloc.add(cb_chonnamtk);

        pn_tktnam_locnv = new JPanel();
        pn_tktnam_locnv.setLayout(null);
        pn_tktnam_locnv.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true), "Tìm Kiếm Nhân Viên", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL_BOLD, COLOR_TEXT_MUTED));
        pn_tktnam_locnv.setBackground(Color.WHITE);
        pn_tktnam_locnv.setBounds(10, 66, 437, 153);
        pn_tktnam_tk_boloc.add(pn_tktnam_locnv);

        JLabel lbl_tktn_locnv_manv = new JLabel("Mã Nhân Viên :");
        lbl_tktn_locnv_manv.setFont(FONT_LABEL_BOLD);
        lbl_tktn_locnv_manv.setForeground(COLOR_TEXT_DARK);
        lbl_tktn_locnv_manv.setBounds(10, 33, 142, 30);
        pn_tktnam_locnv.add(lbl_tktn_locnv_manv);

        JLabel lbl_tktnam_locnv_tennv = new JLabel("Tên Nhân Viên :");
        lbl_tktnam_locnv_tennv.setFont(FONT_LABEL_BOLD);
        lbl_tktnam_locnv_tennv.setForeground(COLOR_TEXT_DARK);
        lbl_tktnam_locnv_tennv.setBounds(10, 85, 142, 30);
        pn_tktnam_locnv.add(lbl_tktnam_locnv_tennv);

        text_tktnam_locnv_manv = new JTextField();
        text_tktnam_locnv_manv.setFont(FONT_TEXT_FIELD);
        text_tktnam_locnv_manv.setColumns(10);
        text_tktnam_locnv_manv.setBounds(162, 33, 261, 30);
        pn_tktnam_locnv.add(text_tktnam_locnv_manv);

        text_tktnam_locnv_tennv = new JTextField();
        text_tktnam_locnv_tennv.setFont(FONT_TEXT_FIELD);
        text_tktnam_locnv_tennv.setColumns(10);
        text_tktnam_locnv_tennv.setBounds(162, 85, 261, 30);
        pn_tktnam_locnv.add(text_tktnam_locnv_tennv);

        scPtknv_hientablenv = new JScrollPane();
        scPtknv_hientablenv.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scPtknv_hientablenv.setBounds(10, 252, 459, 571);
        pn_tktnam_tk.add(scPtknv_hientablenv);

        // Áp dụng đúng cách tạo bảng table_tktn_hiennv
        table_tktn_hiennv = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        applyCommonTableStyling(table_tktn_hiennv); // Áp dụng style chung
        table_tktn_hiennv.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã Nhân Viên", "Tên Nhân Viên"}
        ));
        scPtknv_hientablenv.setViewportView(table_tktn_hiennv);

        // -- Bảng giữa (Thống kê theo tháng trong năm) --
        JScrollPane scP_table_hienhdtheothang = new JScrollPane();
        scP_table_hienhdtheothang.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_table_hienhdtheothang.setBounds(500, 52, 436, 823);
        pn_tketheonam.add(scP_table_hienhdtheothang);
        
        // Áp dụng đúng cách tạo bảng table_11
        table_11 = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                    
                }
                return c;
            }
        };
        applyCommonTableStyling(table_11); // Áp dụng style chung
        table_11.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Tháng", "Tổng Số Hóa Đơn", "Tổng Tiền Các Hóa Đơn"}
        ));
        scP_table_hienhdtheothang.setViewportView(table_11);
        
        btn_tktnam_xemchitiet = new JButton("Xem Chi tiết");
        btn_tktnam_xemchitiet.setFont(FONT_BUTTON_STANDARD);
        styleButton(btn_tktnam_xemchitiet, COLOR_PRIMARY_BLUE);
        btn_tktnam_xemchitiet.setBounds(1521, 833, 143, 42);
        pn_tketheonam.add(btn_tktnam_xemchitiet);
        
        // -- Khu vực biểu đồ --
        pn_bieudo_nam = new JPanel();
        pn_bieudo_nam.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        pn_bieudo_nam.setBackground(COLOR_CARD_BACKGROUND);
        pn_bieudo_nam.setBounds(946, 96, 728, 726);
        pn_tketheonam.add(pn_bieudo_nam);
        pn_bieudo_nam.setLayout(new BorderLayout(0, 0)); // Để chứa biểu đồ

        JLabel lbl_tktnam_tieude = new JLabel("Biểu Đồ Doanh Thu Năm:");
        lbl_tktnam_tieude.setFont(FONT_TITLE_SECTION);
        lbl_tktnam_tieude.setForeground(COLOR_TEXT_DARK);
        lbl_tktnam_tieude.setBounds(946, 52, 323, 35);
        pn_tketheonam.add(lbl_tktnam_tieude);
        
        lbl_tktn_hiennam = new JLabel("..."); // Sẽ cập nhật sau
        lbl_tktn_hiennam.setFont(FONT_TITLE_SECTION);
        lbl_tktn_hiennam.setForeground(COLOR_PRIMARY_BLUE);
        lbl_tktn_hiennam.setBounds(1280, 52, 121, 35);
        pn_tketheonam.add(lbl_tktn_hiennam);
        
        // ===== KẾT THÚC KHỐI CODE THỐNG KÊ HÓA ĐƠN ĐÃ SỬA =====

     // ===== BẮT ĐẦU KHỐI CODE THÊM NHÂN VIÊN ĐÃ SỬA (BỎ MÀU MENU BAR) =====
                JPanel panel_ThemNV = new JPanel();
                maincontent.add(panel_ThemNV, "themNV");
                panel_ThemNV.setLayout(null);
                panel_ThemNV.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
                

                // Panel tiêu đề (Dùng màu gốc của Đại Ca)
                JPanel panel_title_ThemNV = new JPanel();
                panel_title_ThemNV.setBounds(0, 0, 1699, 67); // Điều chỉnh width cho vừa màn hình
                panel_title_ThemNV.setBackground(new Color(0, 102, 102)); // <<< DÙNG MÀU GỐC
                panel_ThemNV.add(panel_title_ThemNV);
                panel_title_ThemNV.setLayout(null);

                JLabel lblTitle_TNV = new JLabel("THÊM NHÂN VIÊN MỚI");
                lblTitle_TNV.setBounds(0, 11, 1689, 45); // Căn giữa
                panel_title_ThemNV.add(lblTitle_TNV);
                lblTitle_TNV.setFont(FONT_TITLE_MAIN); // Font tiêu đề
                lblTitle_TNV.setForeground(Color.WHITE); // Chữ trắng
                lblTitle_TNV.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa text


                // Panel ảnh
                JPanel panelAnhNV_TNV = new JPanel();
                panelAnhNV_TNV.setLayout(new BorderLayout(0, 0));
                panelAnhNV_TNV.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
                panelAnhNV_TNV.setBackground(COLOR_BACKGROUND_PRIMARY);
                panelAnhNV_TNV.setBounds(40, 80, 383, 500);
                panel_ThemNV.add(panelAnhNV_TNV);

                JLabel lblAnhNV_TNV = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
                lblAnhNV_TNV.setFont(FONT_DETAIL_ITALIC);
                lblAnhNV_TNV.setForeground(COLOR_TEXT_MUTED);
                panelAnhNV_TNV.add(lblAnhNV_TNV, BorderLayout.CENTER);

                // Nút chọn ảnh (Giữ nguyên code icon)
                JButton btnChonAnh_TNV = new JButton("Chọn ảnh");
                btnChonAnh_TNV.setFont(FONT_BUTTON_STANDARD);
                styleButton(btnChonAnh_TNV, COLOR_TEXT_MUTED);
                btnChonAnh_TNV.setBounds(160, 590, 143, 40);
                // --- Code ActionListener và Icon của Đại Ca ---
                btnChonAnh_TNV.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Chọn ảnh nhân viên");
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        duongDanAnh_TNV = file.getAbsolutePath();
                        lblAnhNV_TNV.setIcon(new ImageIcon(new ImageIcon(duongDanAnh_TNV).getImage()
                            .getScaledInstance(lblAnhNV_TNV.getWidth(), lblAnhNV_TNV.getHeight(), Image.SCALE_SMOOTH)));
                        lblAnhNV_TNV.setText(null);
                    }
                });
                // --- Hết code ActionListener và Icon ---
                panel_ThemNV.add(btnChonAnh_TNV);


                // Panel thông tin nhân viên
                JPanel panelThongTinNV_TNV = new JPanel();
                panelThongTinNV_TNV.setLayout(null);
                panelThongTinNV_TNV.setBackground(COLOR_CARD_BACKGROUND);
                panelThongTinNV_TNV.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                    "Thông tin nhân viên", TitledBorder.LEADING, TitledBorder.TOP,
                    FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
                ));
                panelThongTinNV_TNV.setBounds(450, 80, 1239, 550);
                panel_ThemNV.add(panelThongTinNV_TNV);

                // Định nghĩa vị trí và khoảng cách
                int labelX_nv = 40;
                int inputX_nv = 190;
                int labelX2_nv = 550;
                int inputX2_nv = 680;
                int startY_nv = 40;
                int height_nv = 33;
                int vGap_nv = 15;

                JLabel lblMaNV_TNV = new JLabel("Mã nhân viên:");
                lblMaNV_TNV.setFont(FONT_LABEL_BOLD);
                lblMaNV_TNV.setForeground(COLOR_TEXT_DARK);
                lblMaNV_TNV.setBounds(labelX_nv, startY_nv, 140, height_nv);
                panelThongTinNV_TNV.add(lblMaNV_TNV);

                txtMaNV_TNV = new JTextField();
                txtMaNV_TNV.setFont(FONT_LABEL_BOLD);
                txtMaNV_TNV.setForeground(COLOR_DANGER_RED);
                txtMaNV_TNV.setEditable(false);
                txtMaNV_TNV.setColumns(10);
                txtMaNV_TNV.setBounds(inputX_nv, startY_nv, 300, height_nv);
                txtMaNV_TNV.setBorder(null);
                txtMaNV_TNV.setBackground(COLOR_CARD_BACKGROUND);
                // nhanVien_DAO nv_dao = new nhanVien_DAO();
                String maMoi = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_NHAN_VIEN, null)).getData();
                txtMaNV_TNV.setText(maMoi);
                panelThongTinNV_TNV.add(txtMaNV_TNV);

                JLabel lblGioiTinh_TNV = new JLabel("Giới Tính:");
                lblGioiTinh_TNV.setFont(FONT_LABEL_BOLD);
                lblGioiTinh_TNV.setForeground(COLOR_TEXT_DARK);
                lblGioiTinh_TNV.setBounds(603, 40, 120, height_nv);
                panelThongTinNV_TNV.add(lblGioiTinh_TNV);

                JComboBox<String> cboGIoiTinh_TNV = new JComboBox<String>();
                cboGIoiTinh_TNV.setModel(new DefaultComboBoxModel<String>(new String[] {"Nam", "Nữ"}));
                cboGIoiTinh_TNV.setFont(FONT_TEXT_FIELD);
                cboGIoiTinh_TNV.setBounds(733, 40, 439, 33);
                panelThongTinNV_TNV.add(cboGIoiTinh_TNV);

                JLabel lblTenNV_TNV = new JLabel("Tên nhân viên:");
                lblTenNV_TNV.setFont(FONT_LABEL_BOLD);
                lblTenNV_TNV.setForeground(COLOR_TEXT_DARK);
                lblTenNV_TNV.setBounds(40, 133, 1132, 33);
                panelThongTinNV_TNV.add(lblTenNV_TNV);

                txtTenNV_TNV = new JTextField();
                txtTenNV_TNV.setFont(FONT_TEXT_FIELD);
                txtTenNV_TNV.setColumns(10);
                txtTenNV_TNV.setBounds(190, 133, 982, 33);
                panelThongTinNV_TNV.add(txtTenNV_TNV);

                JLabel lblSDT_TNV = new JLabel("Số điện thoại:");
                lblSDT_TNV.setFont(FONT_LABEL_BOLD);
                lblSDT_TNV.setForeground(COLOR_TEXT_DARK);
                lblSDT_TNV.setBounds(40, 228, 540, 33);
                panelThongTinNV_TNV.add(lblSDT_TNV);

                txtSDT_TNV = new JTextField();
                txtSDT_TNV.setFont(FONT_TEXT_FIELD);
                txtSDT_TNV.setColumns(10);
                txtSDT_TNV.setBounds(190, 228, 390, 33);
                panelThongTinNV_TNV.add(txtSDT_TNV);

                JLabel lblNgaySinh_TNV = new JLabel("Ngày sinh:");
                lblNgaySinh_TNV.setFont(FONT_LABEL_BOLD);
                lblNgaySinh_TNV.setForeground(COLOR_TEXT_DARK);
                lblNgaySinh_TNV.setBounds(603, 228, 120, height_nv);
                panelThongTinNV_TNV.add(lblNgaySinh_TNV);

                JDateChooser dateNgaySinh_TNV = new JDateChooser();
                dateNgaySinh_TNV.setFont(FONT_TEXT_FIELD);
                dateNgaySinh_TNV.setDateFormatString("dd/MM/yyyy");
                dateNgaySinh_TNV.setBounds(733, 228, 439, 33);
                panelThongTinNV_TNV.add(dateNgaySinh_TNV);

                JLabel lblChucVu_TNV = new JLabel("Chức vụ:");
                lblChucVu_TNV.setFont(FONT_LABEL_BOLD);
                lblChucVu_TNV.setForeground(COLOR_TEXT_DARK);
                lblChucVu_TNV.setBounds(40, 324, 140, height_nv);
                panelThongTinNV_TNV.add(lblChucVu_TNV);

                JComboBox<ChucVu> cboChucVu_TNV = new JComboBox<ChucVu>();
                cboChucVu_TNV.setFont(FONT_TEXT_FIELD);
                cboChucVu_TNV.setBounds(190, 324, 390, 33);
                panelThongTinNV_TNV.add(cboChucVu_TNV);
                // chucVu_DAO cv_dao_TNV = new chucVu_DAO();
                List<ChucVu> dsChucVu = (List<ChucVu>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_CHUC_VU, null)).getData();
                for (ChucVu cv : dsChucVu) {
                	cboChucVu_TNV.addItem(cv);
                }
               
               
                JPanel panelDiaChi_TNV = new JPanel();
                panelDiaChi_TNV.setBackground(COLOR_CARD_BACKGROUND);
                panelDiaChi_TNV.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                     "Địa chỉ", TitledBorder.LEADING, TitledBorder.TOP,
                     FONT_LABEL_BOLD, COLOR_TEXT_MUTED
                ));


                panelDiaChi_TNV.setBounds(40, 408, 1189, 100);
                panelThongTinNV_TNV.add(panelDiaChi_TNV);
                panelDiaChi_TNV.setLayout(null);

                JLabel lblTinh_TNV = new JLabel("Tỉnh/TP:");
                lblTinh_TNV.setBounds(20, 35, 86, height_nv);
                lblTinh_TNV.setFont(FONT_LABEL_BOLD);
                lblTinh_TNV.setForeground(COLOR_TEXT_DARK);
                panelDiaChi_TNV.add(lblTinh_TNV);

                txtTinh_TNV = new JTextField();
                txtTinh_TNV.setFont(FONT_TEXT_FIELD);
                txtTinh_TNV.setBounds(110, 35, 430, 33);
                panelDiaChi_TNV.add(txtTinh_TNV);
                txtTinh_TNV.setColumns(10);

                JLabel lblHuyen_TNV = new JLabel("Quận/Huyện:");
                lblHuyen_TNV.setFont(FONT_LABEL_BOLD);
                lblHuyen_TNV.setForeground(COLOR_TEXT_DARK);
                lblHuyen_TNV.setBounds(571, 35, 120, height_nv);
                panelDiaChi_TNV.add(lblHuyen_TNV);

                txtHuyen_TNV = new JTextField();
                txtHuyen_TNV.setFont(FONT_TEXT_FIELD);
                txtHuyen_TNV.setColumns(10);
                txtHuyen_TNV.setBounds(701, 35, 430, 33);
                panelDiaChi_TNV.add(txtHuyen_TNV);

                // Nút Làm mới (Giữ nguyên code icon)
                JButton btnLamMoi_TNV = new JButton("Làm mới");
                btnLamMoi_TNV.setFont(FONT_BUTTON_STANDARD);
                styleButton(btnLamMoi_TNV, COLOR_TEXT_MUTED);
                btnLamMoi_TNV.setBounds(1386, 650, 143, 40);
                // --- Code ActionListener và Icon của Đại Ca ---
                btnLamMoi_TNV.addActionListener(e -> {
                    txtTenNV_TNV.setText("");
                    txtSDT_TNV.setText("");
                    txtTinh_TNV.setText("");
                    txtHuyen_TNV.setText("");
                    cboGIoiTinh_TNV.setSelectedIndex(0);
                    cboChucVu_TNV.setSelectedIndex(0);
                    dateNgaySinh_TNV.setDate(null);
                    lblAnhNV_TNV.setIcon(null);
                    lblAnhNV_TNV.setText("Chưa có ảnh");
                    duongDanAnh_TNV = null;

                    // nhanVien_DAO dao = new nhanVien_DAO();
                    txtMaNV_TNV.setText((String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_NHAN_VIEN, null)).getData());
                });
                ImageIcon iconLamMoi_TNV = loadIcon("icon-refresh.png");
                if (iconLamMoi_TNV != null) {
                    Image img = iconLamMoi_TNV.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    btnLamMoi_TNV.setIcon(new ImageIcon(img));
                    btnLamMoi_TNV.setHorizontalAlignment(SwingConstants.LEFT);
                    btnLamMoi_TNV.setIconTextGap(10);
                } else {
                    System.err.println("Lỗi: Không tìm thấy ảnh tại img/icon-refresh.png");
                }
                // --- Hết code ActionListener và Icon ---
                panel_ThemNV.add(btnLamMoi_TNV);

                // Nút Thêm (Giữ nguyên code icon)
                JButton btnThem_TNV = new JButton("Thêm");
                btnThem_TNV.setFont(FONT_BUTTON_STANDARD);
                styleButton(btnThem_TNV, COLOR_SUCCESS_GREEN);
                btnThem_TNV.setBounds(1556, 650, 133, 40);
        		// --- Code ActionListener và Icon của Đại Ca ---
             // --- Code ActionListener nút THÊM ---
                btnThem_TNV.addActionListener(e -> {
                    try {
                        // 1. Lấy thông tin cơ bản
                        String ten = txtTenNV_TNV.getText().trim();
                        String sdt = txtSDT_TNV.getText().trim();
                        String gioiTinh = cboGIoiTinh_TNV.getSelectedItem().toString();
                        java.util.Date ngaySinh = dateNgaySinh_TNV.getDate();

                        // 2. Lấy chức vụ
                        ChucVu selectedCV = (ChucVu) cboChucVu_TNV.getSelectedItem();
                        String maChucVu = selectedCV != null ? selectedCV.getMaChucVu() : "";
                        String tenChucVu = selectedCV != null ? selectedCV.getTenChucVu() : "";

                        // 3. Thông tin địa chỉ và ảnh
                        String tinh = txtTinh_TNV.getText().trim();
                        String huyen = txtHuyen_TNV.getText().trim();
                        String anh = duongDanAnh_TNV;

                        // 4. KIỂM TRA VALIDATION
                        if (ten.isEmpty() || sdt.isEmpty() || ngaySinh == null ||
                            maChucVu.isEmpty() || anh == null) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin và chọn ảnh!");
                            return;
                        }
                        
                        // nhanVien_DAO checkDao = new nhanVien_DAO();
                        if ((boolean) SocketClient.sendRequest(new Request(ActionType.CHECK_SDT_TON_TAI_NV, sdt)).getData()) {
                            JOptionPane.showMessageDialog(null, "Lỗi: Số điện thoại " + sdt + " đã tồn tại trong hệ thống!", "Trùng dữ liệu", JOptionPane.ERROR_MESSAGE);
                            txtSDT_TNV.requestFocus(); // Focus lại ô SĐT để sửa
                            return; // Dừng lại, không thêm nữa
                        }

                        if (tempListNV != null) {
                            for (NhanVien nvCho : tempListNV) {
                                if (nvCho.getSoDienThoai().equals(sdt)) {
                                    JOptionPane.showMessageDialog(null, "Lỗi: Số điện thoại này vừa được thêm vào danh sách chờ lưu!", "Trùng dữ liệu", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                        }

                        DefaultTableModel modelCheck = (DefaultTableModel) table_TNV.getModel();
                        for (int i = 0; i < modelCheck.getRowCount(); i++) {
                            String sdtTrenBang = modelCheck.getValueAt(i, 5).toString(); // Cột 5 là SĐT
                            if (sdtTrenBang.equals(sdt)) {
                                 JOptionPane.showMessageDialog(null, "Lỗi: Số điện thoại này đã có trên bảng hiển thị!", "Trùng dữ liệu", JOptionPane.ERROR_MESSAGE);
                                 return;
                            }
                        }

                        // 5. TẠO MÃ NHÂN VIÊN MỚI
                        // nhanVien_DAO nvDAO = new nhanVien_DAO();
                        String maNhanVienMoi = (String) SocketClient.sendRequest(new Request(ActionType.GENERATE_NEW_MA_NV_FROM_TABLE, null)).getData();

                        // 6. QUY ĐỊNH TÊN TÀI KHOẢN TỰ ĐỘNG
                        String tenTaiKhoanTuDong = maNhanVienMoi;
                        
                        // 7. CHUẨN BỊ DỮ LIỆU HIỂN THỊ
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String ngaySinhStr = sdf.format(ngaySinh);

                        if (table_TNV == null) {
                            JOptionPane.showMessageDialog(null, "Bảng nhân viên chưa được khởi tạo!");
                            return;
                        }

                        // 8. TẠO ĐỐI TƯỢNG NHÂN VIÊN
                        java.time.LocalDate ngaySinhLocal = ngaySinh.toInstant()
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate();
                        
                        // --- SỬA LỖI TẠI ĐÂY: Thêm tham số "Đang làm việc" vào Constructor ---
                        NhanVien nv = new NhanVien(
                            maNhanVienMoi,
                            ten,
                            ngaySinhLocal,
                            gioiTinh,
                            selectedCV,
                            sdt,
                            tinh + ", " + huyen,
                            anh,
                            "Đang làm việc", // <--- Đã thêm trạng thái
                            null // Tài khoản tạm thời null
                        );

                        if (tempListNV == null) {
                            tempListNV = new ArrayList<>();
                        }
                        tempListNV.add(nv);

                        // 9. CẬP NHẬT LÊN JTABLE
                        DefaultTableModel model = (DefaultTableModel) table_TNV.getModel();
                        model.addRow(new Object[]{
                            maNhanVienMoi, 
                            ten, 
                            ngaySinhStr, 
                            gioiTinh, 
                            tenChucVu,
                            sdt, 
                            tinh + ", " + huyen, 
                            anh, 
                            tenTaiKhoanTuDong,
                            "Đang làm việc" // Thêm hiển thị trạng thái lên bảng
                        });

                        // 10. RESET FORM
                        btnLamMoi_TNV.doClick();
                        String nextMa = (String) SocketClient.sendRequest(new Request(ActionType.GENERATE_NEW_MA_NV_FROM_TABLE, null)).getData();
                        txtMaNV_TNV.setText(nextMa);
                        
                        JOptionPane.showMessageDialog(null, "Đã thêm nhân viên " + ten + " vào danh sách chờ.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                    }
                });


                ImageIcon iconThem = loadIcon("icon-add.png");
                if (iconThem != null) {
                    Image img = iconThem.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    btnThem_TNV.setIcon(new ImageIcon(img));
                    btnThem_TNV.setHorizontalAlignment(SwingConstants.LEFT);
                    btnThem_TNV.setIconTextGap(10);
                } else {
                    System.err.println("Lỗi: Không tìm thấy ảnh tại img/icon-add.png");
                }
                // --- Hết code ActionListener và Icon ---
                panel_ThemNV.add(btnThem_TNV);
                
                JScrollPane scrollPane_TNV = new JScrollPane();
                scrollPane_TNV.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); // Viền
                scrollPane_TNV.setBounds(10, 701, 1679, 289); // Vị trí bảng
                panel_ThemNV.add(scrollPane_TNV);

                // Style bảng table_TNV
               table_TNV = new JTable() {
                     @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (!isRowSelected(row)) {
                            c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                            c.setForeground(this.getForeground()); // Dùng this
                        } else {
                            c.setBackground(COLOR_PRIMARY_BLUE);
                            // Không set foreground trắng
                        }
                        return c;
                    }
                };
                applyCommonTableStyling(table_TNV); // Áp dụng style chung
                table_TNV.setModel(new DefaultTableModel(
                	new Object[][] {
                		{null, null, null, null, null, null, null, null, null, null},
                	},
                	new String[] {
                		"M\u00E3 nh\u00E2n vi\u00EAn", "T\u00EAn nh\u00E2n vi\u00EAn", "Ng\u00E0y sinh", "Gi\u1EDBi t\u00EDnh", "Ch\u1EE9c v\u1EE5", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "\u0110\u1ECBa ch\u1EC9", "\u1EA2nh nh\u00E2n vi\u00EAn", "T\u00E0i kho\u1EA3n", "Tr\u1EA1ng Th\u00E1i"
                	}
                ));
                scrollPane_TNV.setViewportView(table_TNV);
                loadDataToTableNV(table_TNV);

                
                JButton btnLuu_TNV = new JButton("Lưu");
                btnLuu_TNV.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btnLuu_TNV.setBounds(1191, 650, 143, 40);
                ImageIcon iconLuu = loadIcon("save-icon.png");
                if (iconLuu != null) {
                    Image img = iconLuu.getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH);
                    btnLuu_TNV.setIcon(new ImageIcon(img));
                    btnLuu_TNV.setHorizontalAlignment(SwingConstants.LEFT);
                    btnLuu_TNV.setIconTextGap(10);
                } else {
                    System.err.println("Lỗi: Không tìm thấy ảnh tại img/save-icon.png");
                }
             // --- Code ActionListener nút LƯU ---
                btnLuu_TNV.addActionListener(e -> {
                    try {
                        DefaultTableModel model = (DefaultTableModel) table_TNV.getModel();
                        // DAOs removed

                        int rowCount = model.getRowCount();
                        if (rowCount == 0) {
                            JOptionPane.showMessageDialog(null, "Không có nhân viên nào để lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        int soLuongLuuThanhCong = 0;

                        for (int i = 0; i < rowCount; i++) {
                            Object maNVObj = model.getValueAt(i, 0);
                            if (maNVObj == null) continue;

                            String maNV = maNVObj.toString().trim();
                            NhanVien nvTonTai = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA, maNV)).getData();
                            if (nvTonTai != null) continue;

                            try {
                                String tenNV = model.getValueAt(i, 1).toString().trim();
                                String ngaySinhStr = model.getValueAt(i, 2).toString().trim();
                                String gioiTinh = model.getValueAt(i, 3).toString().trim();
                                String tenChucVu = model.getValueAt(i, 4).toString().trim();
                                String sdt = model.getValueAt(i, 5).toString().trim();
                                String diaChi = model.getValueAt(i, 6).toString().trim();
                                String anh = model.getValueAt(i, 7).toString().trim();
                                String tenTKHienThi = model.getValueAt(i, 8).toString().trim();

                                // Validation sơ bộ
                                if (tenNV.isEmpty() || ngaySinhStr.isEmpty() || gioiTinh.isEmpty() ||
                                    tenChucVu.isEmpty() || sdt.isEmpty() || diaChi.isEmpty() || anh.isEmpty()) {
                                    continue;
                                }

                                // 1. Xử lý ngày sinh
                                LocalDate ngaySinh;
                                if (ngaySinhStr.contains("/")) {
                                    ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                } else {
                                    ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                }

                                // 2. Lấy chức vụ từ DB
                                ChucVu cv = (ChucVu) SocketClient.sendRequest(new Request(ActionType.GET_CHUC_VU_BY_TEN, tenChucVu)).getData();
                                if (cv == null) continue;

                                // 3. TỰ ĐỘNG TẠO TÀI KHOẢN
                                String maTKMoi = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_TAI_KHOAN, null)).getData();
                                TaiKhoan tkMoi = new TaiKhoan(maTKMoi, tenTKHienThi, "123", "Nhân viên Bán Hàng", true);

                                // 4. LƯU TÀI KHOẢN -> LƯU NHÂN VIÊN
                                if ((boolean) SocketClient.sendRequest(new Request(ActionType.ADD_TAI_KHOAN, tkMoi)).getData()) {
                                    // --- SỬA LỖI TẠI ĐÂY: Thêm "Đang làm việc" vào Constructor ---
                                    NhanVien nv = new NhanVien(
                                        maNV, tenNV, ngaySinh, gioiTinh, cv, sdt, diaChi, anh, 
                                        "Đang làm việc", // Trạng thái
                                        tkMoi           // Tài khoản
                                    );
                                    
                                    if ((boolean) SocketClient.sendRequest(new Request(ActionType.ADD_NHAN_VIEN, nv)).getData()) {
                                        soLuongLuuThanhCong++;
                                    } else {
                                        SocketClient.sendRequest(new Request(ActionType.DELETE_TAI_KHOAN, maTKMoi)); // Rollback nếu lỗi
                                        System.err.println("Lỗi lưu NV: " + maNV);
                                    }
                                }
                            } catch (Exception ex1) {
                                ex1.printStackTrace();
                            }
                        }

                        if (tempListNV != null) tempListNV.clear();
                        loadDataToTableNV(table_TNV);

                        JOptionPane.showMessageDialog(null,
                            "Đã lưu thành công " + soLuongLuuThanhCong + " nhân viên mới.\nMật khẩu mặc định: 123",
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                            
                        txtMaNV_TNV.setText((String) SocketClient.sendRequest(new Request(ActionType.GENERATE_NEW_MA_NV_FROM_TABLE, null)).getData());

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                });

                panel_ThemNV.add(btnLuu_TNV);
                panel_ThemNV.addComponentListener(new java.awt.event.ComponentAdapter() {
                    @Override
                    public void componentShown(java.awt.event.ComponentEvent e) {
                        loadDataToTableNV(table_TNV);
                        try {
                            // nhanVien_DAO nvDAO = new nhanVien_DAO();
                            if (tempListNV == null) { 
                                tempListNV = new ArrayList<>();
                            }
                            String nextMa = (String) SocketClient.sendRequest(new Request(ActionType.GENERATE_NEW_MA_NV_FROM_TABLE, null)).getData();
                            txtMaNV_TNV.setText(nextMa);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            txtMaNV_TNV.setText("Lỗi!");
                        }
                    }
                });


        // ===== KẾT THÚC KHỐI CODE THÊM NHÂN VIÊN =====

     // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM NHÂN VIÊN ĐÃ SỬA =====
                JPanel panel_TimKiemNV = new JPanel();
                maincontent.add(panel_TimKiemNV, "timkiemnv");
                panel_TimKiemNV.setLayout(null);
                panel_TimKiemNV.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
    	        
                // Panel tiêu đề
                JPanel panel_title_TKNV = new JPanel();
                panel_title_TKNV.setLayout(null);
                panel_title_TKNV.setBackground(new Color(0, 102, 102)); // Màu gốc
                panel_title_TKNV.setBounds(0, 0, 1699, 67); // Điều chỉnh width nếu cần
                panel_TimKiemNV.add(panel_title_TKNV);

                JLabel lblTitle_TKNV = new JLabel("TÌM KIẾM NHÂN VIÊN");
                lblTitle_TKNV.setForeground(Color.WHITE);
                lblTitle_TKNV.setFont(FONT_TITLE_MAIN); // Font tiêu đề
                lblTitle_TKNV.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa
                lblTitle_TKNV.setBounds(0, 11, 1584, 45); // Căn giữa
                panel_title_TKNV.add(lblTitle_TKNV);

                // Panel bộ lọc
                JPanel pnlFilters_TKNV = new JPanel(); // Đặt tên mới cho dễ hiểu
                pnlFilters_TKNV.setBackground(COLOR_CARD_BACKGROUND);
                pnlFilters_TKNV.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                    "Bộ Lọc Tìm Kiếm", TitledBorder.LEADING, TitledBorder.TOP,
                    FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
                )); // Viền và tiêu đề
                pnlFilters_TKNV.setBounds(10, 79, 1679, 280); // Vị trí bộ lọc
                panel_TimKiemNV.add(pnlFilters_TKNV);
                pnlFilters_TKNV.setLayout(null);

                JLabel lblTenNV_TKNV = new JLabel("Tên nhân viên:");
                lblTenNV_TKNV.setFont(FONT_LABEL_BOLD); // Font label
                lblTenNV_TKNV.setForeground(COLOR_TEXT_DARK); // Màu chữ
                lblTenNV_TKNV.setBounds(40, 40, 163, 30); // Vị trí
                pnlFilters_TKNV.add(lblTenNV_TKNV);

                txt_TenNV_TKNV = new JTextField();
                txt_TenNV_TKNV.setFont(FONT_TEXT_FIELD); // Font text field
                txt_TenNV_TKNV.setBounds(210, 40, 682, 33); // Vị trí
                txt_TenNV_TKNV.setColumns(10);
                
                pnlFilters_TKNV.add(txt_TenNV_TKNV);

                JLabel lblSDT_TKNV = new JLabel("Số điện thoại:");
                lblSDT_TKNV.setFont(FONT_LABEL_BOLD); // Font label
                lblSDT_TKNV.setForeground(COLOR_TEXT_DARK); // Màu chữ
                lblSDT_TKNV.setBounds(925, 40, 139, 30); // Vị trí
                pnlFilters_TKNV.add(lblSDT_TKNV);

                txtSDT_TKNV = new JTextField();
                txtSDT_TKNV.setFont(FONT_TEXT_FIELD); // Font text field
                txtSDT_TKNV.setBounds(1075, 40, 582, 33); // Vị trí
                txtSDT_TKNV.setColumns(10);
                pnlFilters_TKNV.add(txtSDT_TKNV);

                JLabel lblGioiTinh_TK = new JLabel("Giới tính:");
                lblGioiTinh_TK.setFont(FONT_LABEL_BOLD); // Font label
                lblGioiTinh_TK.setForeground(COLOR_TEXT_DARK); // Màu chữ
                lblGioiTinh_TK.setBounds(40, 90, 163, 30); // Vị trí
                pnlFilters_TKNV.add(lblGioiTinh_TK);

                JComboBox<String> cboGioiTinh_TKNV = new JComboBox<String>();
                cboGioiTinh_TKNV.setModel(new DefaultComboBoxModel<String>(new String[] {"Tất cả", "Nam", "Nữ"})); // Thêm "Tất cả"
                cboGioiTinh_TKNV.setFont(FONT_TEXT_FIELD); // Font text field
                cboGioiTinh_TKNV.setBounds(210, 90, 163, 33); // Vị trí
                pnlFilters_TKNV.add(cboGioiTinh_TKNV);

                JLabel lblVaiTro_TKNV = new JLabel("Chức vụ:"); // Đổi thành Chức vụ
                lblVaiTro_TKNV.setFont(FONT_LABEL_BOLD); // Font label
                lblVaiTro_TKNV.setForeground(COLOR_TEXT_DARK); // Màu chữ
                lblVaiTro_TKNV.setBounds(925, 90, 139, 30); // Vị trí
                pnlFilters_TKNV.add(lblVaiTro_TKNV);

                JComboBox<ChucVu> cboVaiTro_TKNV = new JComboBox<ChucVu>(); // Dùng ChucVu
                cboVaiTro_TKNV.setFont(FONT_TEXT_FIELD); // Font text field
                cboVaiTro_TKNV.setBounds(1075, 90, 582, 33); // Vị trí
                // Load chức vụ vào combobox (Thêm lựa chọn null cho "Tất cả")
                cboVaiTro_TKNV.addItem(null);
                // chucVu_DAO cv_dao_TKNV = new chucVu_DAO();
                List<ChucVu> dsChucVu_TKNV = (List<ChucVu>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_CHUC_VU, null)).getData();
                for (ChucVu cv : dsChucVu_TKNV) {
                    cboVaiTro_TKNV.addItem(cv);
                }
                pnlFilters_TKNV.add(cboVaiTro_TKNV);


                // Panel địa chỉ trong bộ lọc
                JPanel panelDiaChi_TKNV = new JPanel();
                panelDiaChi_TKNV.setBackground(COLOR_CARD_BACKGROUND);
                panelDiaChi_TKNV.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
                     "Địa chỉ", TitledBorder.LEADING, TitledBorder.TOP,
                     FONT_LABEL_BOLD, COLOR_TEXT_MUTED // Màu nhạt hơn
                ));
                panelDiaChi_TKNV.setBounds(20, 140, 1649, 80); // Vị trí
                pnlFilters_TKNV.add(panelDiaChi_TKNV);
                panelDiaChi_TKNV.setLayout(null);

                JLabel lblTinh_TKNV = new JLabel("Tỉnh/TP:");
                lblTinh_TKNV.setBounds(20, 30, 86, 30);
                lblTinh_TKNV.setFont(FONT_LABEL_BOLD); // Font label
                lblTinh_TKNV.setForeground(COLOR_TEXT_DARK); // Màu chữ
                panelDiaChi_TKNV.add(lblTinh_TKNV);

                txtTinh_TKNV = new JTextField();
                txtTinh_TKNV.setBounds(110, 30, 639, 33);
                txtTinh_TKNV.setFont(FONT_TEXT_FIELD); // Font text field
                txtTinh_TKNV.setColumns(10);
                panelDiaChi_TKNV.add(txtTinh_TKNV);

                JLabel lblHuyen_TKNV = new JLabel("Quận/Huyện:");
                lblHuyen_TKNV.setBounds(780, 30, 120, 30);
                lblHuyen_TKNV.setFont(FONT_LABEL_BOLD); // Font label
                lblHuyen_TKNV.setForeground(COLOR_TEXT_DARK); // Màu chữ
                panelDiaChi_TKNV.add(lblHuyen_TKNV);

                txtHuyen_TKNV = new JTextField();
                txtHuyen_TKNV.setBounds(932, 30, 707, 33);
                txtHuyen_TKNV.setFont(FONT_TEXT_FIELD); // Font text field
                txtHuyen_TKNV.setColumns(10);
                panelDiaChi_TKNV.add(txtHuyen_TKNV);

                // Nút trong bộ lọc
                JButton btnLamMoi_TKNV = new JButton("Làm mới");
                btnLamMoi_TKNV.setFont(FONT_BUTTON_STANDARD); // Font nút
                styleButton(btnLamMoi_TKNV, COLOR_TEXT_MUTED); // Style nút phụ
                btnLamMoi_TKNV.setBounds(1529, 230, 140, 40); // Vị trí nút
                btnLamMoi_TKNV.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Reset toàn bộ text field
                        txt_TenNV_TKNV.setText("");
                        txtSDT_TKNV.setText("");
                        txtTinh_TKNV.setText("");
                        txtHuyen_TKNV.setText("");

                        // Reset combobox
                        cboGioiTinh_TKNV.setSelectedIndex(0); // Tất cả
                        cboVaiTro_TKNV.setSelectedIndex(0);   // Null hoặc “Tất cả”

                        // Load lại toàn bộ dữ liệu bảng
                        loadDataToTableNV(table_TKNV);
                    }
                });
                       ImageIcon iconLamMoi_TKNV = loadIcon("icon-refresh.png");
                if (iconLamMoi_TKNV != null) {
                    Image img = iconLamMoi_TKNV.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                    btnLamMoi_TKNV.setIcon(new ImageIcon(img));
                }
                pnlFilters_TKNV.add(btnLamMoi_TKNV);

                JButton btnXemCT_TKNV = new JButton("Xem chi tiết");
                btnXemCT_TKNV.addActionListener(e -> {
                    int row = table_TKNV.getSelectedRow();
                    if (row == -1) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xem chi tiết!");
                        return;
                    }

                    // Lấy mã nhân viên từ bảng (cột đầu tiên)
                    String maNV = table_TKNV.getValueAt(row, 0).toString();

                    // --- Lấy thông tin nhân viên đầy đủ từ DAO ---
                    // nhanVien_DAO nvDAO = new nhanVien_DAO();
                    NhanVien nv = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA, maNV)).getData();

                    if (nv == null) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy thông tin nhân viên!");
                        return;
                    }

                    // --- Mở dialog xem chi tiết ---
                    new XemChiTietNV_GUI((JFrame) SwingUtilities.getWindowAncestor(panel_TimKiemNV), nv).setVisible(true);
                });


                btnXemCT_TKNV.setFont(FONT_BUTTON_STANDARD);
                styleButton(btnXemCT_TKNV, COLOR_PRIMARY_BLUE);
                btnXemCT_TKNV.setBounds(1360, 230, 279, 40);
    	            ImageIcon iconXemCT = loadIcon("search-icon.png");
    	            if (iconXemCT != null) {
    	                Image img = iconXemCT.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    	                btnXemCT_TKNV.setIcon(new ImageIcon(img));
    	            }
                pnlFilters_TKNV.add(btnXemCT_TKNV);


                btnXemCT_TKNV.setFont(FONT_BUTTON_STANDARD); // Font nút
                styleButton(btnXemCT_TKNV, COLOR_PRIMARY_BLUE); // Style nút chính
                btnXemCT_TKNV.setBounds(1240, 230, 140, 40); // Vị trí nút
                pnlFilters_TKNV.add(btnXemCT_TKNV);

                // ScrollPane và Bảng kết quả
                JScrollPane scrollPane_TKNV = new JScrollPane();
                scrollPane_TKNV.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); // Viền
                scrollPane_TKNV.setBounds(10, 370, 1679, 620); // Vị trí bảng
                panel_TimKiemNV.add(scrollPane_TKNV);

                // Style bảng table_TKNV
                table_TKNV = new JTable() {
                     @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (!isRowSelected(row)) {
                            c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                            c.setForeground(this.getForeground()); // Dùng this
                        } else {
                            c.setBackground(COLOR_PRIMARY_BLUE);
                            // Không set foreground trắng
                        }
                        return c;
                    }
                };
                table_TKNV.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                applyCommonTableStyling(table_TKNV); // Áp dụng style chung
                table_TKNV.setModel(new DefaultTableModel(
                	new Object[][] {
                		{null, null, null, null, null, null, null, null, null, null},
                	},
                	new String[] {
                		"M\u00E3 nh\u00E2n vi\u00EAn", "T\u00EAn nh\u00E2n vi\u00EAn", "Ng\u00E0y sinh", "Gi\u1EDBi t\u00EDnh", "Ch\u1EE9c v\u1EE5", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "\u0110\u1ECBa ch\u1EC9", "\u1EA2nh nh\u00E2n vi\u00EAn", "T\u00E0i kho\u1EA3n", "Tr\u1EA1ng Th\u00E1i"
                	}
                ));
                scrollPane_TKNV.setViewportView(table_TKNV);
                loadDataToTableNV(table_TKNV); // load toàn bộ ban đầu

                JLabel lblTrangthai_TK = new JLabel("Trạng Thái :");
                lblTrangthai_TK.setForeground(new Color(33, 37, 41));
                lblTrangthai_TK.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblTrangthai_TK.setBounds(559, 90, 107, 30);
                pnlFilters_TKNV.add(lblTrangthai_TK);
                
                JComboBox<String> cboTrangthai_TKNV = new JComboBox<String>();
                cboTrangthai_TKNV.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                cboTrangthai_TKNV.setBounds(676, 90, 216, 33);
                cboTrangthai_TKNV.addItem("Tất cả");
                cboTrangthai_TKNV.addItem("Đang làm việc");
                cboTrangthai_TKNV.addItem("Nghỉ việc");
                pnlFilters_TKNV.add(cboTrangthai_TKNV);
                panel_TimKiemNV.addComponentListener(new ComponentAdapter() {
    	            @Override
    	            public void componentShown(ComponentEvent e) {
    	                loadDataToTableNV(table_TKNV);

    	            }
    	        });
                
             // --- SỬA DÒNG NÀY --- 
                // Thêm tham số cboTrangthai_TKNV vào vị trí gần cuối
                addLocSuKien(txt_TenNV_TKNV, txtSDT_TKNV, txtTinh_TKNV, txtHuyen_TKNV, 
                             cboGioiTinh_TKNV, cboVaiTro_TKNV, cboTrangthai_TKNV, table_TKNV);

        // ===== KẾT THÚC KHỐI CODE TÌM KIẾM NHÂN VIÊN =====
        
     // ===== BẮT ĐẦU KHỐI CODE CẬP NHẬT NHÂN VIÊN ĐÃ SỬA =====
	        JPanel panel_CapNhatNV = new JPanel();
	        maincontent.add(panel_CapNhatNV, "capnhatnv");
	        panel_CapNhatNV.setLayout(null);
	        panel_CapNhatNV.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
	       
	
	        // Panel tiêu đề
	        JPanel panel_title_TKNV_1 = new JPanel(); // Giữ nguyên tên biến
	        panel_title_TKNV_1.setLayout(null);
	        panel_title_TKNV_1.setBackground(new Color(0, 102, 102)); // Màu gốc
	        panel_title_TKNV_1.setBounds(0, 0, 1699, 67); // Điều chỉnh width nếu cần
	        panel_CapNhatNV.add(panel_title_TKNV_1);
	
	        JLabel lblTitle_TKNV_1 = new JLabel("CẬP NHẬT THÔNG TIN NHÂN VIÊN"); // Đổi text tiêu đề
	        lblTitle_TKNV_1.setForeground(Color.WHITE);
	        lblTitle_TKNV_1.setFont(FONT_TITLE_MAIN); // Font tiêu đề
	        lblTitle_TKNV_1.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa
	        lblTitle_TKNV_1.setBounds(0, 11, 1584, 45); // Căn giữa
	        panel_title_TKNV_1.add(lblTitle_TKNV_1);
	
	
	        // Panel ảnh
	        JPanel panelAnh_CNNV = new JPanel();
	        panelAnh_CNNV.setLayout(new BorderLayout(0, 0));
	        panelAnh_CNNV.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
	        panelAnh_CNNV.setBackground(COLOR_BACKGROUND_PRIMARY);
	        panelAnh_CNNV.setBounds(40, 80, 339, 328); // Vị trí
	        panel_CapNhatNV.add(panelAnh_CNNV);
	
	        JLabel lblAnhNV_CNNV = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
	        lblAnhNV_CNNV.setFont(FONT_DETAIL_ITALIC);
	        lblAnhNV_CNNV.setForeground(COLOR_TEXT_MUTED);
	        panelAnh_CNNV.add(lblAnhNV_CNNV, BorderLayout.CENTER);
	
	     // Nút chọn ảnh (Giữ nguyên code icon)
	        JButton btnChonAnh_CNNV = new JButton("Chọn ảnh");
	        btnChonAnh_CNNV.setFont(FONT_BUTTON_STANDARD); // Chỉ set font
	        styleButton(btnChonAnh_CNNV, COLOR_TEXT_MUTED); // Chỉ set màu
	        btnChonAnh_CNNV.setBounds(140, 420, 152, 37); // Vị trí
	        // --- Code ActionListener GỐC của Đại Ca (KHÔNG try-catch) ---
	        btnChonAnh_CNNV.addActionListener(e -> {
	            JFileChooser fileChooser = new JFileChooser();
	            fileChooser.setDialogTitle("Chọn ảnh nhân viên");
	            fileChooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "png", "jpeg"));
	            int result = fileChooser.showOpenDialog(null);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
	                duongDanAnh_CNNV = file.getAbsolutePath();
	                // Code gốc load ảnh
	                lblAnhNV_CNNV.setIcon(new ImageIcon(new ImageIcon(duongDanAnh_CNNV).getImage()
	                    .getScaledInstance(lblAnhNV_CNNV.getWidth(), lblAnhNV_CNNV.getHeight(), Image.SCALE_SMOOTH)));
	                lblAnhNV_CNNV.setText(null); // Giữ lại dòng này để xóa chữ
	            }
	        });
	        // --- Code Icon GỐC của Đại Ca ---
	        ImageIcon iconChonAnh = loadIcon("file-icon.png");
	        if (iconChonAnh != null) {
	            Image img = iconChonAnh.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	            btnChonAnh_CNNV.setIcon(new ImageIcon(img));
	            btnChonAnh_CNNV.setHorizontalAlignment(SwingConstants.LEFT);
	            btnChonAnh_CNNV.setIconTextGap(10);
	        } else {
	            System.err.println("Lỗi: Không tìm thấy ảnh");
	        }
	        // --- Hết code Icon ---
	        panel_CapNhatNV.add(btnChonAnh_CNNV); // Add nút vào panel
	        // --- Hết code ActionListener và Icon ---
	        panel_CapNhatNV.add(btnChonAnh_CNNV);
	
	        // Panel thông tin cập nhật
	        JPanel pnlInfo_CNNV = new JPanel();
	        pnlInfo_CNNV.setBackground(COLOR_CARD_BACKGROUND);
	        pnlInfo_CNNV.setBorder(BorderFactory.createTitledBorder(
	            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
	            "Thông Tin Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
	            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
	        ));
	        pnlInfo_CNNV.setBounds(400, 80, 1289, 320);
	        panel_CapNhatNV.add(pnlInfo_CNNV);
	        pnlInfo_CNNV.setLayout(null);
	
	        // Định nghĩa vị trí và khoảng cách
	        int labelX_cnnv = 30;
	        int inputX_cnnv = 170;
	        int labelX2_cnnv = 550;
	        int inputX2_cnnv = 680;
	        int startY_cnnv = 30;
	        int height_cnnv = 33;
	        int vGap_cnnv = 15;
	
	        JLabel lblMaNV_CNNV = new JLabel("Mã nhân viên:");
	        lblMaNV_CNNV.setFont(FONT_LABEL_BOLD);
	        lblMaNV_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblMaNV_CNNV.setBounds(labelX_cnnv, startY_cnnv, 130, height_cnnv);
	        pnlInfo_CNNV.add(lblMaNV_CNNV);
	
	        txtMaNV_CNNV = new JTextField();
	        txtMaNV_CNNV.setFont(FONT_LABEL_BOLD);
	        txtMaNV_CNNV.setForeground(COLOR_DANGER_RED);
	        txtMaNV_CNNV.setBounds(170, 30, 504, 33);
	        txtMaNV_CNNV.setEditable(false);
	        txtMaNV_CNNV.setBackground(COLOR_CARD_BACKGROUND); // Nền giống panel
	        txtMaNV_CNNV.setBorder(null); // Bỏ viền
	        pnlInfo_CNNV.add(txtMaNV_CNNV);
	        txtMaNV_CNNV.setColumns(10);
	
	        JLabel lblGioiTinh_CNNV = new JLabel("Giới tính:");
	        lblGioiTinh_CNNV.setFont(FONT_LABEL_BOLD);
	        lblGioiTinh_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblGioiTinh_CNNV.setBounds(684, 30, 120, height_cnnv);
	        pnlInfo_CNNV.add(lblGioiTinh_CNNV);
	
	        JComboBox<String> cboGIoiTinh_CNNV = new JComboBox<String>();
	        cboGIoiTinh_CNNV.setModel(new DefaultComboBoxModel<String>(new String[] {"Nam", "Nữ"}));
	        cboGIoiTinh_CNNV.setFont(FONT_TEXT_FIELD);
	        cboGIoiTinh_CNNV.setBounds(814, 30, 453, 33);
	        pnlInfo_CNNV.add(cboGIoiTinh_CNNV);
	
	
	        JLabel lblTenNV_CNNV = new JLabel("Tên nhân viên:");
	        lblTenNV_CNNV.setFont(FONT_LABEL_BOLD);
	        lblTenNV_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblTenNV_CNNV.setBounds(labelX_cnnv, startY_cnnv + height_cnnv + vGap_cnnv, 130, height_cnnv);
	        pnlInfo_CNNV.add(lblTenNV_CNNV);
	
	        txtTenNV_CNNV = new JTextField();
	        txtTenNV_CNNV.setFont(FONT_TEXT_FIELD);
	        txtTenNV_CNNV.setColumns(10);
	        txtTenNV_CNNV.setBounds(170, 78, 1097, 33);
	        pnlInfo_CNNV.add(txtTenNV_CNNV);
	
	        JLabel lblSDT_CNNV = new JLabel("Số điện thoại:");
	        lblSDT_CNNV.setFont(FONT_LABEL_BOLD);
	        lblSDT_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblSDT_CNNV.setBounds(labelX_cnnv, startY_cnnv + 2*(height_cnnv + vGap_cnnv), 130, height_cnnv);
	        pnlInfo_CNNV.add(lblSDT_CNNV);
	
	        txtSDT_CNNV = new JTextField();
	        txtSDT_CNNV.setFont(FONT_TEXT_FIELD);
	        txtSDT_CNNV.setColumns(10);
	        txtSDT_CNNV.setBounds(170, 126, 504, 33);
	        pnlInfo_CNNV.add(txtSDT_CNNV);
	
	        JLabel lblNgaySinh_CNNV = new JLabel("Ngày sinh:");
	        lblNgaySinh_CNNV.setFont(FONT_LABEL_BOLD);
	        lblNgaySinh_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblNgaySinh_CNNV.setBounds(684, 126, 120, height_cnnv);
	        pnlInfo_CNNV.add(lblNgaySinh_CNNV);
	
	        JDateChooser dateNgaySinh_CNNV = new JDateChooser();
	        dateNgaySinh_CNNV.setFont(FONT_TEXT_FIELD);
	        dateNgaySinh_CNNV.setDateFormatString("dd/MM/yyyy");
	        dateNgaySinh_CNNV.setBounds(814, 126, 453, 33);
	        pnlInfo_CNNV.add(dateNgaySinh_CNNV);
	
	
	        JLabel lblChucVu_CNNV = new JLabel("Chức vụ:");
	        lblChucVu_CNNV.setFont(FONT_LABEL_BOLD);
	        lblChucVu_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblChucVu_CNNV.setBounds(labelX_cnnv, startY_cnnv + 3*(height_cnnv + vGap_cnnv), 130, height_cnnv);
	        pnlInfo_CNNV.add(lblChucVu_CNNV);
	
	        JComboBox<ChucVu> cboChucVu_CNNV = new JComboBox<ChucVu>();
	        cboChucVu_CNNV.setFont(FONT_TEXT_FIELD);
	        cboChucVu_CNNV.setBounds(170, 174, 504, 33);
	        pnlInfo_CNNV.add(cboChucVu_CNNV);
	
	        JLabel lblTaiKhoan_CNNV = new JLabel("Tài khoản:");
	        lblTaiKhoan_CNNV.setFont(FONT_LABEL_BOLD);
	        lblTaiKhoan_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblTaiKhoan_CNNV.setBounds(684, 174, 120, height_cnnv);
	        pnlInfo_CNNV.add(lblTaiKhoan_CNNV);
	
	        JComboBox<TaiKhoan> cboTaiKhoan_CNNV = new JComboBox<TaiKhoan>();
	        cboTaiKhoan_CNNV.setFont(FONT_TEXT_FIELD);
	        cboTaiKhoan_CNNV.setBounds(814, 174, 453, 33);
	        pnlInfo_CNNV.add(cboTaiKhoan_CNNV);
	
	        List<ChucVu> dsChucVu_CNNV = (List<ChucVu>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_CHUC_VU, null)).getData();
	        for (ChucVu cv : dsChucVu_CNNV) {
	        	cboChucVu_CNNV.addItem(cv);
	        }
	        List<TaiKhoan> dsTaiKhoan_CNNV = (List<TaiKhoan>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_TAI_KHOAN, null)).getData();
	        for (TaiKhoan tk : dsTaiKhoan_CNNV) {
	            cboTaiKhoan_CNNV.addItem(tk);
	         }
	
	        // Panel địa chỉ
	        JPanel panelDiaChi_CNNV = new JPanel();
	        panelDiaChi_CNNV.setBackground(COLOR_CARD_BACKGROUND);
	        panelDiaChi_CNNV.setLayout(null);
	        panelDiaChi_CNNV.setBorder(BorderFactory.createTitledBorder(
	            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
	             "Địa chỉ", TitledBorder.LEADING, TitledBorder.TOP,
	             FONT_LABEL_BOLD, COLOR_TEXT_MUTED
	        ));
	        panelDiaChi_CNNV.setBounds(30, 232, 1249, 85);
	        pnlInfo_CNNV.add(panelDiaChi_CNNV);
	
	
	        JLabel lblTinh_CNNV = new JLabel("Tỉnh/TP:");
	        lblTinh_CNNV.setFont(FONT_LABEL_BOLD);
	        lblTinh_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblTinh_CNNV.setBounds(20, 30, 80, height_cnnv);
	        panelDiaChi_CNNV.add(lblTinh_CNNV);
	
	        txtTinh_CNNV = new JTextField();
	        txtTinh_CNNV.setFont(FONT_TEXT_FIELD);
	        txtTinh_CNNV.setColumns(10);
	        txtTinh_CNNV.setBounds(110, 30, 536, 33);
	        panelDiaChi_CNNV.add(txtTinh_CNNV);
	
	
	        JLabel lblHuyen_CNNV = new JLabel("Quận/Huyện:");
	        lblHuyen_CNNV.setFont(FONT_LABEL_BOLD);
	        lblHuyen_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblHuyen_CNNV.setBounds(656, 30, 120, height_cnnv);
	        panelDiaChi_CNNV.add(lblHuyen_CNNV);
	
	        txtHuyen_CNNV = new JTextField();
	        txtHuyen_CNNV.setFont(FONT_TEXT_FIELD);
	        txtHuyen_CNNV.setColumns(10);
	        txtHuyen_CNNV.setBounds(786, 30, 453, 33);
	        panelDiaChi_CNNV.add(txtHuyen_CNNV);
	
	
	        // Panel tìm kiếm
	        JPanel panelTK_CNNV = new JPanel();
	        panelTK_CNNV.setBackground(COLOR_CARD_BACKGROUND);
	        panelTK_CNNV.setLayout(null);
	        panelTK_CNNV.setBorder(BorderFactory.createTitledBorder(
	            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
	             "Tìm Kiếm Nhân Viên Cần Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
	             FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
	        ));
	        panelTK_CNNV.setBounds(400, 410, 1289, 80);
	        panel_CapNhatNV.add(panelTK_CNNV);
	
	
	     // Label và text field tìm kiếm
	        JLabel lblTenNV_TK_CNNV = new JLabel("Tên nhân viên:");
	        lblTenNV_TK_CNNV.setFont(FONT_LABEL_BOLD);
	        lblTenNV_TK_CNNV.setForeground(COLOR_TEXT_DARK);
	        lblTenNV_TK_CNNV.setBounds(30, 30, 132, 30);
	        panelTK_CNNV.add(lblTenNV_TK_CNNV);

	        txtTenNV_TK_CNNV = new JTextField();
	        txtTenNV_TK_CNNV.setFont(FONT_TEXT_FIELD);
	        txtTenNV_TK_CNNV.setColumns(10);
	        txtTenNV_TK_CNNV.setBounds(169, 29, 483, 33);
	        panelTK_CNNV.add(txtTenNV_TK_CNNV);

	        // Combobox chức vụ (phải tạo trước khi dùng trong listener)
	        JComboBox<ChucVu> cboChucVu_TK_CNNV = new JComboBox<ChucVu>();
	        cboChucVu_TK_CNNV.setFont(FONT_TEXT_FIELD);
	        cboChucVu_TK_CNNV.setBounds(825, 29, 441, 33);
 	        // Thêm item "Tất cả" (null) để bỏ lọc theo chức vụ khi cần
	        cboChucVu_TK_CNNV.addItem(null);
	        List<ChucVu> dsChucVu_tk_CNNV = (List<ChucVu>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_CHUC_VU, null)).getData();
	        for (ChucVu cv : dsChucVu_tk_CNNV) {
	            cboChucVu_TK_CNNV.addItem(cv);
	        }
	        panelTK_CNNV.add(cboChucVu_TK_CNNV);

	     // --- SỬA LẠI ĐOẠN DOCUMENT LISTENER & ACTION LISTENER ---

	     // 1. Sự kiện khi gõ phím vào ô Tên (lọc realtime)
	     txtTenNV_TK_CNNV.getDocument().addDocumentListener(new DocumentListener() {
	         @Override public void insertUpdate(DocumentEvent e) { 
	             locNhanVienCapNhat(txtTenNV_TK_CNNV, cboChucVu_TK_CNNV, table_CNNV); 
	         }
	         @Override public void removeUpdate(DocumentEvent e) { 
	             locNhanVienCapNhat(txtTenNV_TK_CNNV, cboChucVu_TK_CNNV, table_CNNV); 
	         }
	         @Override public void changedUpdate(DocumentEvent e) { 
	             locNhanVienCapNhat(txtTenNV_TK_CNNV, cboChucVu_TK_CNNV, table_CNNV); 
	         }
	     });

	     // 2. Sự kiện khi chọn Chức vụ
	     cboChucVu_TK_CNNV.addActionListener(e -> {
	         locNhanVienCapNhat(txtTenNV_TK_CNNV, cboChucVu_TK_CNNV, table_CNNV);
	     });

	        // ActionListener cho combobox chức vụ (khi chọn sẽ lọc)
	        cboChucVu_TK_CNNV.addActionListener(e -> {
	            // Reuse same logic as above (để tránh trùng code bạn có thể tách thành private method nếu các biến là field)
	            String keyword = txtTenNV_TK_CNNV.getText().trim().toLowerCase();
	            ChucVu selectedCV = (ChucVu) cboChucVu_TK_CNNV.getSelectedItem();
	            String tenChucVu = (selectedCV != null) ? selectedCV.getTenChucVu().toLowerCase() : "";

	            List<NhanVien> dsNV = (List<NhanVien>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHAN_VIEN, null)).getData();

	            DefaultTableModel model = (DefaultTableModel) table_CNNV.getModel();
	            model.setRowCount(0);

	            for (NhanVien nv : dsNV) {
	                boolean matchTen = keyword.isEmpty() || (nv.getTenNV() != null && nv.getTenNV().toLowerCase().contains(keyword));
	                boolean matchCV = tenChucVu.isEmpty() || (nv.getChucVu() != null && nv.getChucVu().getTenChucVu() != null
	                        && nv.getChucVu().getTenChucVu().toLowerCase().equalsIgnoreCase(tenChucVu));

	                if (matchTen && matchCV) {
	                    model.addRow(new Object[]{
	                        nv.getMaNV(),
	                        nv.getTenNV(),
	                        nv.getNgaySinh(),
	                        nv.getGioiTinh(),
	                        nv.getChucVu() != null ? nv.getChucVu().getTenChucVu() : "",
	                        nv.getSoDienThoai(),
	                        nv.getDiaChi(),
	                        nv.getAnh(),
	                        nv.getTaiKhoan() != null ? nv.getTaiKhoan().getTenTK() : ""
	                    });
	                }
	            }
	        });

	        panelTK_CNNV.add(cboChucVu_TK_CNNV);
	        
	        JLabel lbChucvu_TK_CNNV = new JLabel("Chức vụ :");
	        lbChucvu_TK_CNNV.setForeground(new Color(33, 37, 41));
	        lbChucvu_TK_CNNV.setFont(new Font("Segoe UI", Font.BOLD, 15));
	        lbChucvu_TK_CNNV.setBounds(683, 30, 132, 30);
	        panelTK_CNNV.add(lbChucvu_TK_CNNV);
	
	
	        // Các nút chức năng dưới form (Giữ nguyên code icon)
	        JButton btnLamMoi_CNNV = new JButton("Làm mới Form");
	        btnLamMoi_CNNV.setFont(FONT_BUTTON_STANDARD);
	        styleButton(btnLamMoi_CNNV, COLOR_TEXT_MUTED);
	        btnLamMoi_CNNV.setBounds(1009, 499, 152, 40);
	        btnLamMoi_CNNV.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Bỏ chọn dòng trong bảng
	                table_CNNV.clearSelection();
	
	                // Xóa toàn bộ dữ liệu trong form nhập
	                txtMaNV_CNNV.setText("");
	                txtTenNV_CNNV.setText("");
	                txtSDT_CNNV.setText("");
	                txtTinh_CNNV.setText("");
	                txtHuyen_CNNV.setText("");
	                cboGIoiTinh_CNNV.setSelectedIndex(0); // Mặc định chọn "Nam"
	                cboChucVu_CNNV.setSelectedIndex(0);
	                cboTaiKhoan_CNNV.setSelectedIndex(0);
	                dateNgaySinh_CNNV.setDate(null);
	
	                // Reset ảnh nhân viên
	                duongDanAnh_CNNV = null;
	                lblAnhNV_CNNV.setText("Chưa có ảnh");
	                lblAnhNV_CNNV.setIcon(null);
	            }
	        });
	
	        // --- Code ActionListener và Icon của Đại Ca ---
	        ImageIcon iconLamMoi_CNNV = loadIcon("icon-refresh.png");
	        if (iconLamMoi_CNNV != null) {
	            Image img = iconLamMoi_CNNV.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	            btnLamMoi_CNNV.setIcon(new ImageIcon(img));
	            btnLamMoi_CNNV.setHorizontalAlignment(SwingConstants.LEFT);
	            btnLamMoi_CNNV.setIconTextGap(10);
	        }
	        // --- Hết code ActionListener và Icon ---
	        panel_CapNhatNV.add(btnLamMoi_CNNV);
	
	        JButton btnXoa_CNNV = new JButton("Xóa");
	        btnXoa_CNNV.setFont(FONT_BUTTON_STANDARD);
	        styleButton(btnXoa_CNNV, COLOR_DANGER_RED); // Màu đỏ
	        btnXoa_CNNV.setBounds(1179, 499, 152, 40);
	        btnXoa_CNNV.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int selectedRow = table_CNNV.getSelectedRow();
	                if (selectedRow == -1) {
	                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                    return;
	                }

	                String maNV = table_CNNV.getValueAt(selectedRow, 0).toString();
	                int confirm = JOptionPane.showConfirmDialog(null,
	                        "Bạn có chắc muốn xóa nhân viên có mã: " + maNV + " không?",
	                        "Xác nhận xóa",
	                        JOptionPane.YES_NO_OPTION);

	                if (confirm == JOptionPane.YES_OPTION) {
	                    boolean result = (boolean) SocketClient.sendRequest(new Request(ActionType.DELETE_NHAN_VIEN, maNV)).getData();
	                    if (result) {
	                        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");

	                        // ======= LÀM MỚI BẢNG =======
	                        loadDataToTableNV(table_CNNV);

	                        // ======= RESET FORM =======
	                        txtMaNV_CNNV.setText("");
	                        txtTenNV_CNNV.setText("");
	                        txtSDT_CNNV.setText("");
	                        dateNgaySinh_CNNV.setDate(null);
	                        cboGIoiTinh_CNNV.setSelectedIndex(0);
	                        cboChucVu_CNNV.setSelectedIndex(0);
	                        cboTaiKhoan_CNNV.setSelectedIndex(0);
	                        txtTinh_CNNV.setText("");
	                        txtHuyen_CNNV.setText("");

	                        // ======= XÓA HÌNH ẢNH =======
	                        lblAnhNV_CNNV.setIcon(null);
	                        lblAnhNV_CNNV.setText("Không có hình ảnh");

	                    } else {
	                        JOptionPane.showMessageDialog(null, 
	                            "Không thể xóa nhân viên! Kiểm tra lại dữ liệu hoặc ràng buộc khóa ngoại.", 
	                            "Lỗi", 
	                            JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            }
	        });
	        ImageIcon iconXoa = loadIcon("delete-icon.png");
	        if (iconXoa != null) {
	            Image img = iconXoa.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	            btnXoa_CNNV.setIcon(new ImageIcon(img));
	            btnXoa_CNNV.setHorizontalAlignment(SwingConstants.LEFT);
	            btnXoa_CNNV.setIconTextGap(10);
	        }
	        panel_CapNhatNV.add(btnXoa_CNNV);
	
	        JButton btnKhoiPhuc_CNNV = new JButton("Khôi phục Form");
	        btnKhoiPhuc_CNNV.setFont(FONT_BUTTON_STANDARD);
	        styleButton(btnKhoiPhuc_CNNV, COLOR_TEXT_MUTED);
	        btnKhoiPhuc_CNNV.setBounds(1349, 499, 171, 40);
	        btnKhoiPhuc_CNNV.addActionListener(e -> {
	            int selectedRow = table_CNNV.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên trong bảng để khôi phục dữ liệu!");
	                return;
	            }

	            try {
	                String maNV = table_CNNV.getValueAt(selectedRow, 0).toString(); // cột 0 là mã NV
	                NhanVien nv = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA, maNV)).getData();

	                if (nv == null) {
	                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên trong cơ sở dữ liệu!");
	                    return;
	                }

	                // --- Gán dữ liệu lên form ---
	                txtMaNV_CNNV.setText(nv.getMaNV() != null ? nv.getMaNV() : "");
	                txtTenNV_CNNV.setText(nv.getTenNV() != null ? nv.getTenNV() : "");
	                txtSDT_CNNV.setText(nv.getSoDienThoai() != null ? nv.getSoDienThoai() : "");

	                // Giới tính (ComboBox<String>)
	                if (nv.getGioiTinh() != null) {
	                    cboGIoiTinh_CNNV.setSelectedItem(nv.getGioiTinh());
	                } else {
	                    cboGIoiTinh_CNNV.setSelectedIndex(0); // mặc định "Nam"
	                }

	                // Ngày sinh: NhanVien.getNgaySinh() trong DAO của bạn trả LocalDate
	                if (nv.getNgaySinh() != null) {
	                    java.time.LocalDate ld = nv.getNgaySinh();
	                    java.util.Date utilDate = java.util.Date.from(ld.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
	                    dateNgaySinh_CNNV.setDate(utilDate);
	                } else {
	                    dateNgaySinh_CNNV.setDate(null);
	                }

	                // Chức vụ: cboChucVu_CNNV chứa các object ChucVu -> chọn theo ma hoặc tên
	                for (int i = 0; i < cboChucVu_CNNV.getItemCount(); i++) {
	                    ChucVu item = cboChucVu_CNNV.getItemAt(i);
	                    if (item != null && nv.getChucVu() != null) {
	                        // so sánh theo mã chức vụ (an toàn hơn)
	                        if (item.getMaChucVu().equals(nv.getChucVu().getMaChucVu())) {
	                            cboChucVu_CNNV.setSelectedIndex(i);
	                            break;
	                        }
	                    }
	                }

	                // Tài khoản: cboTaiKhoan_CNNV chứa TaiKhoan objects -> chọn theo maTK hoặc tenTK
	                for (int i = 0; i < cboTaiKhoan_CNNV.getItemCount(); i++) {
	                    TaiKhoan item = cboTaiKhoan_CNNV.getItemAt(i);
	                    if (item != null && nv.getTaiKhoan() != null) {
	                        if (item.getMaTK().equals(nv.getTaiKhoan().getMaTK())) {
	                            cboTaiKhoan_CNNV.setSelectedIndex(i);
	                            break;
	                        }
	                    }
	                }

	                // Địa chỉ: tách tỉnh, huyện như bạn làm ở mouseClicked
	                String diaChi = nv.getDiaChi();
	                if (diaChi != null && diaChi.contains(",")) {
	                    String[] parts = diaChi.split(",", 2);
	                    txtTinh_CNNV.setText(parts[0].trim());
	                    txtHuyen_CNNV.setText(parts[1].trim());
	                } else if (diaChi != null) {
	                    txtTinh_CNNV.setText(diaChi.trim());
	                    txtHuyen_CNNV.setText("");
	                } else {
	                    txtTinh_CNNV.setText("");
	                    txtHuyen_CNNV.setText("");
	                }

	                // Ảnh: dùng trường anh trong entity (nv.getAnh())
	                duongDanAnh_CNNV = nv.getAnh(); // cập nhật biến lưu đường dẫn ảnh
	                if (duongDanAnh_CNNV != null && !duongDanAnh_CNNV.isEmpty()) {
	                    try {
	                        ImageIcon icon_kp = new ImageIcon(duongDanAnh_CNNV);
	                        if (icon_kp.getImageLoadStatus() == MediaTracker.COMPLETE) {
	                            Image img = icon_kp.getImage().getScaledInstance(
	                                lblAnhNV_CNNV.getWidth(), lblAnhNV_CNNV.getHeight(), Image.SCALE_SMOOTH
	                            );
	                            lblAnhNV_CNNV.setText(null);
	                            lblAnhNV_CNNV.setIcon(new ImageIcon(img));
	                        } else {
	                            throw new Exception("Ảnh không hợp lệ");
	                        }
	                    } catch (Exception ex) {
	                        lblAnhNV_CNNV.setText("Lỗi ảnh");
	                        lblAnhNV_CNNV.setIcon(null);
	                    }
	                } else {
	                    lblAnhNV_CNNV.setText("Chưa có ảnh");
	                    lblAnhNV_CNNV.setIcon(null);
	                }

	                JOptionPane.showMessageDialog(null, "Đã khôi phục dữ liệu gốc cho nhân viên " + nv.getTenNV());
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Lỗi khi khôi phục: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
	            }
	        });


	        panel_CapNhatNV.add(btnKhoiPhuc_CNNV);
	
	        JButton btnCapNhat_CNNV = new JButton("Cập nhật");
	        btnCapNhat_CNNV.setFont(FONT_BUTTON_STANDARD);
	        styleButton(btnCapNhat_CNNV, COLOR_SUCCESS_GREEN);
	        btnCapNhat_CNNV.setBounds(1539, 499, 150, 40);
	        ImageIcon iconCapNhat = loadIcon("update-icon.png");
	        if (iconCapNhat != null) {
	            Image img = iconCapNhat.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
	            btnCapNhat_CNNV.setIcon(new ImageIcon(img));
	            btnCapNhat_CNNV.setHorizontalAlignment(SwingConstants.LEFT);
	            btnCapNhat_CNNV.setIconTextGap(10);
	        } else {
	            System.err.println("Lỗi: Không tìm thấy ảnh");
	        }
	
	        panel_CapNhatNV.add(btnCapNhat_CNNV);
	        btnCapNhat_CNNV.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    int selectedRow = table_CNNV.getSelectedRow();
	                    if (selectedRow == -1) {
	                        JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                        return;
	                    }

	                    // ===== LẤY MÃ NHÂN VIÊN TỪ BẢNG =====
	                    String maNV = table_CNNV.getValueAt(selectedRow, 0).toString();

	                    // ===== LẤY DỮ LIỆU TỪ FORM =====
	                    String tenNV = txtTenNV_CNNV.getText().trim();
	                    String sdt = txtSDT_CNNV.getText().trim();
	                    String gioiTinh = cboGIoiTinh_CNNV.getSelectedItem().toString();
	                    java.util.Date ngaySinh = dateNgaySinh_CNNV.getDate();

	                    ChucVu selectedCV = (ChucVu) cboChucVu_CNNV.getSelectedItem();
	                    String maChucVu = selectedCV != null ? selectedCV.getMaChucVu() : "";

	                    TaiKhoan selectedTK = (TaiKhoan) cboTaiKhoan_CNNV.getSelectedItem();
	                    String maTK = selectedTK != null ? selectedTK.getMaTK() : "";

	                    String tinh = txtTinh_CNNV.getText().trim();
	                    String huyen = txtHuyen_CNNV.getText().trim();
	                    String anh = duongDanAnh_CNNV;

	                    // 1. Kiểm tra rỗng
	                    if (tenNV.isEmpty() || sdt.isEmpty() || ngaySinh == null || maChucVu.isEmpty() || maTK.isEmpty()) {
	                        JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin!");
	                        return;
	                    }

	                    // 2. KHỞI TẠO DAO (Đưa lên đây để dùng kiểm tra)
	                    // SocketClient used below

	                    // 3. === CHÈN ĐOẠN KIỂM TRA TRÙNG SĐT TẠI ĐÂY ===
	                    // Lấy thông tin cũ của nhân viên đang sửa
	                    NhanVien nvCu = (NhanVien) SocketClient.sendRequest(new Request(ActionType.GET_NHAN_VIEN_BY_MA, maNV)).getData();

	                    // Logic: Nếu SĐT mới khác SĐT cũ (có thay đổi) VÀ SĐT mới đã tồn tại trong DB -> Báo lỗi
	                    if (nvCu != null && !nvCu.getSoDienThoai().equals(sdt)) {
	                         if ((boolean) SocketClient.sendRequest(new Request(ActionType.CHECK_SDT_TON_TAI, sdt)).getData()) {
	                              JOptionPane.showMessageDialog(null, "Lỗi: Số điện thoại " + sdt + " đã thuộc về nhân viên khác!", "Trùng dữ liệu", JOptionPane.ERROR_MESSAGE);
	                              txtSDT_CNNV.requestFocus();
	                              return; // Dừng lại ngay
	                         }
	                    }
	                    // ===================================================

	                    // ===== CHUYỂN NGÀY =====
	                    LocalDate ngaySinhLocal = ngaySinh.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	                    // ===== TẠO ĐỐI TƯỢNG NHÂN VIÊN CẬP NHẬT =====
	                    NhanVien nv = new NhanVien();
	                    nv.setMaNV(maNV);
	                    nv.setTenNV(tenNV);
	                    nv.setSoDienThoai(sdt);
	                    nv.setGioiTinh(gioiTinh);
	                    nv.setNgaySinh(ngaySinhLocal);

	                    ChucVu cv = new ChucVu();
	                    cv.setMaChucVu(maChucVu);
	                    nv.setChucVu(cv);

	                    TaiKhoan tk = new TaiKhoan();
	                    tk.setMaTK(maTK);
	                    nv.setTaiKhoan(tk);

	                    nv.setDiaChi(tinh + ", " + huyen);
	                    nv.setAnh(anh);

	                    // ===== CẬP NHẬT DATABASE =====
	                    // nvDAO đã khởi tạo ở trên rồi, dùng luôn
	                    boolean result = (boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_NHAN_VIEN, nv)).getData();

	                    if (result) {
	                        JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công!");
	                        loadDataToTableNV(table_CNNV); // refresh lại bảng
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Không thể cập nhật nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                    }

	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + ex.getMessage());
	                }
	            }
	        });

	
	
	        // ScrollPane và Bảng
	        JScrollPane scrollPane_CNNV = new JScrollPane();
	        scrollPane_CNNV.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
	        scrollPane_CNNV.setBounds(10, 550, 1679, 440); // Điều chỉnh
	        panel_CapNhatNV.add(scrollPane_CNNV);
	
	        // Style bảng table_CNNV
	        table_CNNV = new JTable() {
	             @Override
	            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	                Component c = super.prepareRenderer(renderer, row, column);
	                if (!isRowSelected(row)) {
	                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
	                    c.setForeground(this.getForeground()); // Dùng this
	                } else {
	                    c.setBackground(COLOR_PRIMARY_BLUE);
	                    // Không set foreground trắng
	                }
	                return c;
	            }
	        };
	        applyCommonTableStyling(table_CNNV);
	        table_CNNV.setModel(new DefaultTableModel(
	        	new Object[][] {
	        	},
	        	new String[] {
	        		"M\u00E3 NV", "T\u00EAn NV", "Ng\u00E0y sinh", "Gi\u1EDBi t\u00EDnh", "Ch\u1EE9c v\u1EE5", "S\u0110T", "\u0110\u1ECBa ch\u1EC9", "\u1EA2nh", "T\u00E0i kho\u1EA3n", "Tr\u1EA1ng Th\u00E1i"
	        	}
	        ));
	        // Code MouseListener giữ nguyên
	         table_CNNV.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                 int selectedRow = table_CNNV.getSelectedRow();
	                if (selectedRow != -1) {
	                     String maNV = table_CNNV.getValueAt(selectedRow, 0).toString();
	                    String tenNV = table_CNNV.getValueAt(selectedRow, 1).toString();
	                    Object ngaySinhObj = table_CNNV.getValueAt(selectedRow, 2);
	                    String gioiTinh = table_CNNV.getValueAt(selectedRow, 3).toString();
	                    String chucVuStr = table_CNNV.getValueAt(selectedRow, 4).toString();
	                    String sdt = table_CNNV.getValueAt(selectedRow, 5).toString();
	                    String diaChi = table_CNNV.getValueAt(selectedRow, 6) != null ? table_CNNV.getValueAt(selectedRow, 6).toString() : "";
	                    String duongDanAnh = table_CNNV.getValueAt(selectedRow, 7) != null ? table_CNNV.getValueAt(selectedRow, 7).toString() : null;
	                    String taiKhoanStr = table_CNNV.getValueAt(selectedRow, 8).toString();
	
	                    txtMaNV_CNNV.setText(maNV);
	                    txtTenNV_CNNV.setText(tenNV);
	                    txtSDT_CNNV.setText(sdt);
	                    cboGIoiTinh_CNNV.setSelectedItem(gioiTinh);
	
	                    if (ngaySinhObj != null) {
	                         try {
	                            if (ngaySinhObj instanceof String) {
	                                 java.util.Date ngaySinh = new java.text.SimpleDateFormat("dd/MM/yyyy").parse((String)ngaySinhObj);
	                                dateNgaySinh_CNNV.setDate(ngaySinh);
	                            } else if (ngaySinhObj instanceof LocalDate) {
	                                java.util.Date ngaySinh = java.util.Date.from(((LocalDate)ngaySinhObj).atStartOfDay(ZoneId.systemDefault()).toInstant());
	                                dateNgaySinh_CNNV.setDate(ngaySinh);
	                            } else if (ngaySinhObj instanceof java.sql.Date) {
	                                dateNgaySinh_CNNV.setDate((java.sql.Date)ngaySinhObj);
	                            } else if (ngaySinhObj instanceof java.util.Date) {
	                                dateNgaySinh_CNNV.setDate((java.util.Date)ngaySinhObj);
	                            } else { dateNgaySinh_CNNV.setDate(null); }
	                        } catch (Exception ex) {
	                            dateNgaySinh_CNNV.setDate(null);
	                        }
	                    } else { dateNgaySinh_CNNV.setDate(null); }
	
	                    for (int i = 0; i < cboChucVu_CNNV.getItemCount(); i++) {
	                         ChucVu cvItem = cboChucVu_CNNV.getItemAt(i);
	                        if (cvItem != null && cvItem.getTenChucVu().equals(chucVuStr)) {
	                            cboChucVu_CNNV.setSelectedIndex(i);
	                            break;
	                        }
	                    }
	
	                    for (int i = 0; i < cboTaiKhoan_CNNV.getItemCount(); i++) {
	                        TaiKhoan tkItem = cboTaiKhoan_CNNV.getItemAt(i);
	                        if (tkItem != null && tkItem.getTenTK().equals(taiKhoanStr)) {
	                            cboTaiKhoan_CNNV.setSelectedIndex(i);
	                            break;
	                        }
	                    }
	
                    if (diaChi != null && diaChi.contains(",")) {
                        String[] parts = diaChi.split(",", 2);
                        txtTinh_CNNV.setText(parts[0].trim());
                        txtHuyen_CNNV.setText(parts[1].trim());
                    } else if (diaChi != null) {
                        txtTinh_CNNV.setText(diaChi.trim());
                        txtHuyen_CNNV.setText("");
                    } else {
                        txtTinh_CNNV.setText("");
                         txtHuyen_CNNV.setText("");
                    }

                    duongDanAnh_CNNV = duongDanAnh;

                    if (duongDanAnh != null && !duongDanAnh.isEmpty()) {
                        try {
                            File imageFile;

                            // ⚙️ Nếu đường dẫn bắt đầu bằng "/" (như /male-1.jpg) thì load từ thư mục gốc "img/"
                            if (duongDanAnh.startsWith("/")) {
                                String fileName = duongDanAnh.substring(1); // bỏ dấu '/'
                                imageFile = new File("img/" + fileName);
                            }
                            // ⚙️ Nếu có dấu ":" thì là đường dẫn tuyệt đối
                            else if (duongDanAnh.contains(":")) {
                                imageFile = new File(duongDanAnh);
                            }
                            // ⚙️ Nếu đã chứa "img/" hoặc "img\" thì không nối thêm nữa
                            else if (duongDanAnh.replace("\\", "/").startsWith("img/")) {
                                imageFile = new File(duongDanAnh);
                            }
                            // ⚙️ Còn lại thì load từ thư mục img/ theo tên file
                            else {
                                imageFile = new File("img/" + duongDanAnh);
                            }

                            if (imageFile.exists()) {
                                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                                Image img = icon.getImage().getScaledInstance(
                                        lblAnhNV_CNNV.getWidth(),
                                        lblAnhNV_CNNV.getHeight(),
                                        Image.SCALE_SMOOTH
                                );
                                lblAnhNV_CNNV.setText(null);
                                lblAnhNV_CNNV.setIcon(new ImageIcon(img));
                            } else {
                                System.out.println("❌ Không tìm thấy ảnh: " + imageFile.getAbsolutePath());
                                lblAnhNV_CNNV.setText("Không tìm thấy ảnh");
                                lblAnhNV_CNNV.setIcon(null);
                            }
                        } catch (Exception ex) {
                            lblAnhNV_CNNV.setText("Lỗi ảnh");
                            lblAnhNV_CNNV.setIcon(null);
                            ex.printStackTrace();
                        }
                    } else {
                        lblAnhNV_CNNV.setText("Chưa có ảnh");
                        lblAnhNV_CNNV.setIcon(null);
                    }


                }
            }
        });
        scrollPane_CNNV.setViewportView(table_CNNV);
        panel_CapNhatNV.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadDataToTableNV(table_CNNV);

   
            }
        });

        // ===== KẾT THÚC KHỐI CODE CẬP NHẬT NHÂN VIÊN =====
        
        // ===== BẮT ĐẦU KHỐI CODE THÊM NHÀ CUNG CẤP ĐÃ SỬA =====
        JPanel panel_themNCC = new JPanel();
        maincontent.add(panel_themNCC, "themNCC");
        panel_themNCC.setLayout(null);
        panel_themNCC.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lblTitleThemNCC = new JLabel("THÊM NHÀ CUNG CẤP MỚI"); // Tiêu đề
        lblTitleThemNCC.setFont(FONT_TITLE_MAIN);
        lblTitleThemNCC.setForeground(COLOR_PRIMARY_BLUE);
        lblTitleThemNCC.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitleThemNCC.setBounds(0, 11, 1584, 45); // Vị trí tiêu đề
        panel_themNCC.add(lblTitleThemNCC);
        // Đại Ca có thể thêm ActionListener cho nút này sau

        // Panel thông tin NCC
        JPanel panel_ThongTinNCC = new JPanel();
        panel_ThongTinNCC.setBackground(COLOR_CARD_BACKGROUND);
        panel_ThongTinNCC.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông tin nhà cung cấp", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        )); // Viền và tiêu đề
        panel_ThongTinNCC.setBounds(40, 80, 1649, 380); // Điều chỉnh vị trí/kích thước
        panel_themNCC.add(panel_ThongTinNCC);
        panel_ThongTinNCC.setLayout(null);

        // Định nghĩa lại vị trí và khoảng cách
        int labelX_tncc = 40;
        int inputX_tncc = 210;
        int labelX2_tncc = 580;
        int inputX2_tncc = 710;
        int startY_tncc = 40;
        int height_tncc = 33;
        int vGap_tncc = 15;

        JLabel lblMaNCC_TNCC = new JLabel("Mã nhà cung cấp:");
        lblMaNCC_TNCC.setFont(FONT_LABEL_BOLD); // Font label
        lblMaNCC_TNCC.setForeground(COLOR_TEXT_DARK); // Màu chữ
        lblMaNCC_TNCC.setBounds(labelX_tncc, startY_tncc, 160, height_tncc);
        panel_ThongTinNCC.add(lblMaNCC_TNCC);

        txtMaNCC_TNCC = new JTextField();
        txtMaNCC_TNCC.setFont(FONT_LABEL_BOLD); // Font đậm
        txtMaNCC_TNCC.setForeground(COLOR_DANGER_RED); // Màu đỏ
        txtMaNCC_TNCC.setEditable(false); // Mã thường tự sinh
        txtMaNCC_TNCC.setColumns(10);
        txtMaNCC_TNCC.setBounds(210, 40, 703, 33); // Điều chỉnh width
        txtMaNCC_TNCC.setBorder(null); // Bỏ viền
        txtMaNCC_TNCC.setBackground(COLOR_CARD_BACKGROUND); // Nền giống panel
        // Cần code để tạo mã NCC mới ở đây
        // txtMaNCC_TNCC.setText(maMoiNCC);
        panel_ThongTinNCC.add(txtMaNCC_TNCC);

        JLabel lblTenNCC_TNCC = new JLabel("Tên nhà cung cấp:");
        lblTenNCC_TNCC.setFont(FONT_LABEL_BOLD);
        lblTenNCC_TNCC.setForeground(COLOR_TEXT_DARK);
        lblTenNCC_TNCC.setBounds(labelX_tncc, startY_tncc + height_tncc + vGap_tncc, 179, height_tncc);
        panel_ThongTinNCC.add(lblTenNCC_TNCC);

        txtTenNCC_TNCC = new JTextField();
        txtTenNCC_TNCC.setFont(FONT_TEXT_FIELD);
        txtTenNCC_TNCC.setColumns(10);
        txtTenNCC_TNCC.setBounds(210, 88, 1402, 33); // Kéo dài
        panel_ThongTinNCC.add(txtTenNCC_TNCC);

        JLabel lblSDTNCC_TNCC = new JLabel("Số điện thoại:");
        lblSDTNCC_TNCC.setFont(FONT_LABEL_BOLD);
        lblSDTNCC_TNCC.setForeground(COLOR_TEXT_DARK);
        lblSDTNCC_TNCC.setBounds(labelX_tncc, startY_tncc + 2*(height_tncc + vGap_tncc), 160, height_tncc);
        panel_ThongTinNCC.add(lblSDTNCC_TNCC);

        txtSDT_TNCC = new JTextField();
        txtSDT_TNCC.setFont(FONT_TEXT_FIELD);
        txtSDT_TNCC.setColumns(10);
        txtSDT_TNCC.setBounds(210, 136, 703, 33);
        panel_ThongTinNCC.add(txtSDT_TNCC);

        JLabel lblEmail_TNCC = new JLabel("Email:");
        lblEmail_TNCC.setFont(FONT_LABEL_BOLD);
        lblEmail_TNCC.setForeground(COLOR_TEXT_DARK);
        lblEmail_TNCC.setBounds(923, 136, 120, height_tncc); 
        panel_ThongTinNCC.add(lblEmail_TNCC);

        txtEmail_TNCC = new JTextField();
        txtEmail_TNCC.setFont(FONT_TEXT_FIELD);
        txtEmail_TNCC.setColumns(10);
        txtEmail_TNCC.setBounds(1053, 136, 559, 33); 
        panel_ThongTinNCC.add(txtEmail_TNCC);


        JLabel lblTrangThai_TNCC = new JLabel("Trạng thái:");
        lblTrangThai_TNCC.setFont(FONT_LABEL_BOLD);
        lblTrangThai_TNCC.setForeground(COLOR_TEXT_DARK);
        lblTrangThai_TNCC.setBounds(923, 40, 120, 33); 
        panel_ThongTinNCC.add(lblTrangThai_TNCC);

        cboTrangThai_TNCC = new JComboBox<String>();
        cboTrangThai_TNCC.setFont(FONT_TEXT_FIELD);
        cboTrangThai_TNCC.setModel(new DefaultComboBoxModel<>(new String[] {"Đang hợp tác", "Ngừng hợp tác"})); 
        cboTrangThai_TNCC.setBounds(1053, 40, 559, 33); 
        panel_ThongTinNCC.add(cboTrangThai_TNCC);


        JLabel lblGhiChu_TNCC = new JLabel("Ghi chú:");
        lblGhiChu_TNCC.setFont(FONT_LABEL_BOLD);
        lblGhiChu_TNCC.setForeground(COLOR_TEXT_DARK);
        lblGhiChu_TNCC.setBounds(40, 245, 160, height_tncc);
        panel_ThongTinNCC.add(lblGhiChu_TNCC);

        JScrollPane scrollPaneGhiChu = new JScrollPane();
        scrollPaneGhiChu.setBounds(210, 250, 1402, 60); 
        panel_ThongTinNCC.add(scrollPaneGhiChu);

        txtGhiChu_TNCC = new JTextArea();
        txtGhiChu_TNCC.setFont(FONT_TEXT_FIELD);
        txtGhiChu_TNCC.setLineWrap(true); 
        txtGhiChu_TNCC.setWrapStyleWord(true);

        scrollPaneGhiChu.setViewportView(txtGhiChu_TNCC);
                txtGhiChu_TNCC.setFont(FONT_TEXT_FIELD);
                txtGhiChu_TNCC.setLineWrap(true); 
                txtGhiChu_TNCC.setWrapStyleWord(true);
                
                JLabel lblDC_TNCC = new JLabel("Địa chỉ:");
                lblDC_TNCC.setForeground(new Color(33, 37, 41));
                lblDC_TNCC.setFont(new Font("Segoe UI", Font.BOLD, 15));
                lblDC_TNCC.setBounds(40, 190, 132, 33);
                panel_ThongTinNCC.add(lblDC_TNCC);
                
                txtDC_TNCC = new JTextField();
                txtDC_TNCC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                txtDC_TNCC.setColumns(10);
                txtDC_TNCC.setBounds(210, 190, 1402, 33);
                panel_ThongTinNCC.add(txtDC_TNCC);

        btnLamMoi_TNCC = new JButton("Làm mới"); 
        btnLamMoi_TNCC.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoi_TNCC, COLOR_TEXT_MUTED);
        btnLamMoi_TNCC.setBounds(1320, 489, 140, 40);
        java.net.URL imgLamMoiTNCC = getClass().getResource("/icon-refresh.png");
        if (imgLamMoiTNCC != null) {
            ImageIcon originalIcon = new ImageIcon(imgLamMoiTNCC);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnLamMoi_TNCC.setIcon(scaledIcon);
            btnLamMoi_TNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnLamMoi_TKNCC.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        panel_themNCC.add(btnLamMoi_TNCC);

        btnThem_TNCC = new JButton("Thêm NCC"); 
        btnThem_TNCC.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnThem_TNCC.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnThem_TNCC, COLOR_SUCCESS_GREEN);
        btnThem_TNCC.setBounds(1549, 489, 140, 40); // Điều chỉnh
        java.net.URL imgThem_TNCC = getClass().getResource("/icon-add.png");
        if (imgThem_TNCC != null) {
            ImageIcon originalIcon = new ImageIcon(imgThem_TNCC);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnThem_TNCC.setIcon(scaledIcon);
            btnThem_TNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnThem_TNCC.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        panel_themNCC.add(btnThem_TNCC);


        // ScrollPane và Bảng hiển thị NCC đã thêm
        JScrollPane scrollPane_ThemNCC = new JScrollPane();
        scrollPane_ThemNCC.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_ThemNCC.setBounds(40, 540, 1649, 450); // Điều chỉnh vị trí/kích thước
        panel_themNCC.add(scrollPane_ThemNCC);

        // Style bảng table_ThemNCC
        table_ThemNCC = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_ThemNCC);
        table_ThemNCC.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null, null},
        	},
        	new String[] {
        		"M\u00E3 NCC", "T\u00EAn NCC", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Email", "\u0110\u1ECBa ch\u1EC9", "Tr\u1EA1ng th\u00E1i", "Ghi ch\u00FA"
        	}
        ));
        scrollPane_ThemNCC.setViewportView(table_ThemNCC);

        // ===== KẾT THÚC KHỐI CODE THÊM NHÀ CUNG CẤP =====
        
     // ===== BẮT ĐẦU KHỐI CODE CẬP NHẬT NHÀ CUNG CẤP ĐÃ SỬA =====
        JPanel panel_capNhatNCC = new JPanel();
        maincontent.add(panel_capNhatNCC, "capNhatNCC");
        panel_capNhatNCC.setLayout(null);
        panel_capNhatNCC.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lblTitle_CN_NCC = new JLabel("CẬP NHẬT THÔNG TIN NHÀ CUNG CẤP"); // Tiêu đề
        lblTitle_CN_NCC.setFont(FONT_TITLE_MAIN);
        lblTitle_CN_NCC.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle_CN_NCC.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle_CN_NCC.setBounds(0, 11, 1584, 52); // Vị trí tiêu đề
        panel_capNhatNCC.add(lblTitle_CN_NCC);

        // Panel thông tin cập nhật
        JPanel pnlInfo_CNNCC = new JPanel(); // Đặt tên mới
        pnlInfo_CNNCC.setBackground(COLOR_CARD_BACKGROUND);
        pnlInfo_CNNCC.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlInfo_CNNCC.setBounds(40, 80, 1649, 377); // Vị trí
        panel_capNhatNCC.add(pnlInfo_CNNCC);
        pnlInfo_CNNCC.setLayout(null);

        // Định nghĩa vị trí và khoảng cách (tương tự panel Thêm NCC)
        int labelX_cncc = 40;
        int inputX_cncc = 210;
        int labelX2_cncc = 580;
        int inputX2_cncc = 710;
        int startY_cncc = 30;
        int height_cncc = 33;
        int vGap_cncc = 15;

        JLabel lblMaNCC_CNNCC = new JLabel("Mã nhà cung cấp:");
        lblMaNCC_CNNCC.setFont(FONT_LABEL_BOLD);
        lblMaNCC_CNNCC.setForeground(COLOR_TEXT_DARK);
        lblMaNCC_CNNCC.setBounds(labelX_cncc, startY_cncc, 160, height_cncc);
        pnlInfo_CNNCC.add(lblMaNCC_CNNCC);

        txtMaNCC_CNNCC = new JTextField();
        txtMaNCC_CNNCC.setFont(FONT_LABEL_BOLD);
        txtMaNCC_CNNCC.setForeground(COLOR_DANGER_RED);
        txtMaNCC_CNNCC.setEditable(false);
        txtMaNCC_CNNCC.setColumns(10);
        txtMaNCC_CNNCC.setBounds(210, 30, 731, 33);
        txtMaNCC_CNNCC.setBorder(null);
        txtMaNCC_CNNCC.setBackground(COLOR_CARD_BACKGROUND);
        pnlInfo_CNNCC.add(txtMaNCC_CNNCC);

        JLabel lblTenNCC_CNNCC = new JLabel("Tên nhà cung cấp:");
        lblTenNCC_CNNCC.setFont(FONT_LABEL_BOLD);
        lblTenNCC_CNNCC.setForeground(COLOR_TEXT_DARK);
        lblTenNCC_CNNCC.setBounds(labelX_cncc, startY_cncc + height_cncc + vGap_cncc, 160, height_cncc);
        pnlInfo_CNNCC.add(lblTenNCC_CNNCC);

        txtTenNCC_CNNCC = new JTextField();
        txtTenNCC_CNNCC.setFont(FONT_TEXT_FIELD);
        txtTenNCC_CNNCC.setColumns(10);
        txtTenNCC_CNNCC.setBounds(210, 78, 1396, 33);
        pnlInfo_CNNCC.add(txtTenNCC_CNNCC);

        JLabel lblSDT_CNNCC = new JLabel("Số điện thoại:");
        lblSDT_CNNCC.setFont(FONT_LABEL_BOLD);
        lblSDT_CNNCC.setForeground(COLOR_TEXT_DARK);
        lblSDT_CNNCC.setBounds(labelX_cncc, startY_cncc + 2*(height_cncc + vGap_cncc), 160, height_cncc);
        pnlInfo_CNNCC.add(lblSDT_CNNCC);

        txtSDT_CNNCC = new JTextField();
        txtSDT_CNNCC.setFont(FONT_TEXT_FIELD);
        txtSDT_CNNCC.setColumns(10);
        txtSDT_CNNCC.setBounds(210, 126, 731, 33);
        pnlInfo_CNNCC.add(txtSDT_CNNCC);

        JLabel lblEmail_CNNCC = new JLabel("Email:");
        lblEmail_CNNCC.setFont(FONT_LABEL_BOLD);
        lblEmail_CNNCC.setForeground(COLOR_TEXT_DARK);
        lblEmail_CNNCC.setBounds(951, 126, 117, 33);
        pnlInfo_CNNCC.add(lblEmail_CNNCC);

        txtEmail_CNNCC = new JTextField(); 
        txtEmail_CNNCC.setFont(FONT_TEXT_FIELD);
        txtEmail_CNNCC.setColumns(10);
        txtEmail_CNNCC.setBounds(1078, 126, 528, 33);
        pnlInfo_CNNCC.add(txtEmail_CNNCC);

        JLabel lblTrangThai_CNNCC = new JLabel("Trạng thái:");
        lblTrangThai_CNNCC.setFont(FONT_LABEL_BOLD);
        lblTrangThai_CNNCC.setForeground(COLOR_TEXT_DARK);
        lblTrangThai_CNNCC.setBounds(951, 30, 117, 33);
        pnlInfo_CNNCC.add(lblTrangThai_CNNCC);

        cboTrangThai_CNNCC = new JComboBox<String>(); // Dùng ComboBox
        cboTrangThai_CNNCC.setFont(FONT_TEXT_FIELD);
        cboTrangThai_CNNCC.setModel(new DefaultComboBoxModel<>(new String[] {"Đang hợp tác", "Ngừng hợp tác"}));
        cboTrangThai_CNNCC.setBounds(1078, 30, 528, 33);
        pnlInfo_CNNCC.add(cboTrangThai_CNNCC);
        
        JLabel lblDC_CNNCC = new JLabel("Địa chỉ:");
        lblDC_CNNCC.setForeground(new Color(33, 37, 41));
        lblDC_CNNCC.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblDC_CNNCC.setBounds(40, 170, 132, 33);
        pnlInfo_CNNCC.add(lblDC_CNNCC);
        
        txtDiaChi_CNNCC = new JTextField();
        txtDiaChi_CNNCC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDiaChi_CNNCC.setColumns(10);
        txtDiaChi_CNNCC.setBounds(210, 170, 1396, 33);
        pnlInfo_CNNCC.add(txtDiaChi_CNNCC);
        
        JLabel lblGhiChu_CNNCC = new JLabel("Ghi chú:");
        lblGhiChu_CNNCC.setForeground(new Color(33, 37, 41));
        lblGhiChu_CNNCC.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblGhiChu_CNNCC.setBounds(40, 225, 160, 33);
        pnlInfo_CNNCC.add(lblGhiChu_CNNCC);
        
        JScrollPane scrollPaneGhiChuCNNCC = new JScrollPane();
        scrollPaneGhiChuCNNCC.setBounds(210, 230, 1396, 136);
        pnlInfo_CNNCC.add(scrollPaneGhiChuCNNCC);
        txtGhiChu_CNNCC = new JTextArea(); 
        txtGhiChu_CNNCC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtGhiChu_CNNCC.setWrapStyleWord(true); 
        txtGhiChu_CNNCC.setLineWrap(true);  
        scrollPaneGhiChuCNNCC.setViewportView(txtGhiChu_CNNCC);

        // Các nút chức năng
        btnLamMoi_CNNCC = new JButton("Làm mới");
        btnLamMoi_CNNCC.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoi_CNNCC, COLOR_TEXT_MUTED);
        btnLamMoi_CNNCC.setBounds(968, 469, 150, 40); // Điều chỉnh
        ImageIcon iconLamMoiCNNCC = loadIcon("icon-refresh.png");
        if (iconLamMoiCNNCC != null) {
            Image img = iconLamMoiCNNCC.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnLamMoi_CNNCC.setIcon(new ImageIcon(img));
            btnLamMoi_CNNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnLamMoi_CNNCC.setIconTextGap(10);
        }
        panel_capNhatNCC.add(btnLamMoi_CNNCC);

        btnKhoiPhuc_CNNCC = new JButton("Khôi phục");
        btnKhoiPhuc_CNNCC.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnKhoiPhuc_CNNCC, COLOR_TEXT_MUTED);
        btnKhoiPhuc_CNNCC.setBounds(1138, 469, 150, 40); // Điều chỉnh
        ImageIcon iconBack = loadIcon("back-icon.png");
        if (iconBack != null) {
            Image img = iconBack.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnKhoiPhuc_CNNCC.setIcon(new ImageIcon(img));
            btnKhoiPhuc_CNNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnKhoiPhuc_CNNCC.setIconTextGap(10);
        }
        panel_capNhatNCC.add(btnKhoiPhuc_CNNCC);

        btnCapNhat_CNNCC = new JButton("Cập nhật");
        btnCapNhat_CNNCC.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnCapNhat_CNNCC, COLOR_SUCCESS_GREEN);
        btnCapNhat_CNNCC.setBounds(1308, 469, 150, 40); // Điều chỉnh
        java.net.URL imgCapNhat_CNNCC = getClass().getResource("/update-icon.png");
        if (imgCapNhat_CNNCC != null) {
            ImageIcon originalIcon = new ImageIcon(imgCapNhat_CNNCC);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnCapNhat_CNNCC.setIcon(scaledIcon);
            btnCapNhat_CNNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnCapNhat_CNNCC.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        panel_capNhatNCC.add(btnCapNhat_CNNCC);


        // ScrollPane và Bảng
        JScrollPane scrollPane_CNNCC = new JScrollPane();
        scrollPane_CNNCC.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_CNNCC.setBounds(40, 520, 1649, 470); // Điều chỉnh
        panel_capNhatNCC.add(scrollPane_CNNCC);

        // Style bảng table_CNNCC
        table_CNNCC = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_CNNCC);
        table_CNNCC.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null, null},
        	},
        	new String[] {
        		"M\u00E3 NCC", "T\u00EAn NCC", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Email", "\u0110\u1ECBa ch\u1EC9", "Tr\u1EA1ng th\u00E1i", "Ghi ch\u00FA"
        	}
        ));
        scrollPane_CNNCC.setViewportView(table_CNNCC);
        
        btnXoa_CNNCC = new JButton("Xóa");
        btnXoa_CNNCC.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXoa_CNNCC.setBounds(1482, 469, 150, 40);
        ImageIcon iconXoaNCC = loadIcon("delete-icon.png");
        if (iconXoaNCC != null) {
            Image img = iconXoaNCC.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            btnXoa_CNNCC.setIcon(new ImageIcon(img));
            btnXoa_CNNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnXoa_CNNCC.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        panel_capNhatNCC.add(btnXoa_CNNCC);

        // ===== KẾT THÚC KHỐI CODE CẬP NHẬT NHÀ CUNG CẤP =====
        
     // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM NHÀ CUNG CẤP ĐÃ SỬA =====
        JPanel panel_TimKiemNCC = new JPanel();
        maincontent.add(panel_TimKiemNCC, "timKiemNCC");
        panel_TimKiemNCC.setLayout(null);
        panel_TimKiemNCC.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        JLabel lblTitle_TKNCC = new JLabel("TÌM KIẾM NHÀ CUNG CẤP"); // Tiêu đề
        lblTitle_TKNCC.setFont(FONT_TITLE_MAIN);
        lblTitle_TKNCC.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle_TKNCC.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle_TKNCC.setBounds(0, 11, 1584, 43); // Vị trí
        panel_TimKiemNCC.add(lblTitle_TKNCC);

        // Panel bộ lọc thông tin
        JPanel panel_ThongTinTKNCC = new JPanel();
        panel_ThongTinTKNCC.setLayout(null);
        panel_ThongTinTKNCC.setBackground(COLOR_CARD_BACKGROUND);
        panel_ThongTinTKNCC.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Bộ Lọc Tìm Kiếm", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        )); // Viền và tiêu đề
        panel_ThongTinTKNCC.setBounds(10, 68, 1679, 291); // Vị trí
        panel_TimKiemNCC.add(panel_ThongTinTKNCC);

        // Định nghĩa vị trí và khoảng cách (tương tự Thêm NCC)
        int labelX_tkncc = 40;
        int inputX_tkncc = 210;
        int labelX2_tkncc = 580;
        int inputX2_tkncc = 710;
        int startY_tkncc = 40;
        int height_tkncc = 33;
        int vGap_tkncc = 15;

        JLabel lblMaNCC_TKNCC = new JLabel("Mã nhà cung cấp:");
        lblMaNCC_TKNCC.setFont(FONT_LABEL_BOLD);
        lblMaNCC_TKNCC.setForeground(COLOR_TEXT_DARK);
        lblMaNCC_TKNCC.setBounds(labelX_tkncc, startY_tkncc, 160, height_tkncc);
        panel_ThongTinTKNCC.add(lblMaNCC_TKNCC);

        txtMaNCC_TKNCC = new JTextField();
        txtMaNCC_TKNCC.setFont(FONT_TEXT_FIELD);
        txtMaNCC_TKNCC.setColumns(10);
        txtMaNCC_TKNCC.setBounds(210, 40, 304, 33);
        panel_ThongTinTKNCC.add(txtMaNCC_TKNCC);

        JLabel lblTenNCC_TKNCC = new JLabel("Tên nhà cung cấp:");
        lblTenNCC_TKNCC.setFont(FONT_LABEL_BOLD);
        lblTenNCC_TKNCC.setForeground(COLOR_TEXT_DARK);
        lblTenNCC_TKNCC.setBounds(571, 40, 179, height_tkncc);
        panel_ThongTinTKNCC.add(lblTenNCC_TKNCC);

        txtTenNCC_TKNCC = new JTextField();
        txtTenNCC_TKNCC.setFont(FONT_TEXT_FIELD);
        txtTenNCC_TKNCC.setColumns(10);
        txtTenNCC_TKNCC.setBounds(741, 40, 300, 33);
        panel_ThongTinTKNCC.add(txtTenNCC_TKNCC);

        JLabel lblSDTNCC_TKNCC = new JLabel("Số điện thoại:");
        lblSDTNCC_TKNCC.setFont(FONT_LABEL_BOLD);
        lblSDTNCC_TKNCC.setForeground(COLOR_TEXT_DARK);
        lblSDTNCC_TKNCC.setBounds(40, 84, 160, height_tkncc);
        panel_ThongTinTKNCC.add(lblSDTNCC_TKNCC);

        txtSDT_TKNCC = new JTextField();
        txtSDT_TKNCC.setFont(FONT_TEXT_FIELD);
        txtSDT_TKNCC.setColumns(10);
        txtSDT_TKNCC.setBounds(210, 84, 304, 33);
        panel_ThongTinTKNCC.add(txtSDT_TKNCC);

        JLabel lblEmail_TKNCC = new JLabel("Email:");
        lblEmail_TKNCC.setFont(FONT_LABEL_BOLD);
        lblEmail_TKNCC.setForeground(COLOR_TEXT_DARK);
        lblEmail_TKNCC.setBounds(571, 84, 120, height_tkncc);
        panel_ThongTinTKNCC.add(lblEmail_TKNCC);

        txtEmail_TKNCC = new JTextField();
        txtEmail_TKNCC.setFont(FONT_TEXT_FIELD);
        txtEmail_TKNCC.setColumns(10);
        txtEmail_TKNCC.setBounds(741, 84, 300, height_tkncc);
        panel_ThongTinTKNCC.add(txtEmail_TKNCC);

        JLabel lblTrangThai_TKNCC = new JLabel("Trạng thái:");
        lblTrangThai_TKNCC.setFont(FONT_LABEL_BOLD);
        lblTrangThai_TKNCC.setForeground(COLOR_TEXT_DARK);
        lblTrangThai_TKNCC.setBounds(1106, 40, 120, height_tkncc);
        panel_ThongTinTKNCC.add(lblTrangThai_TKNCC);

        cboTrangThai_TKNCC = new JComboBox<String>(); // Dùng ComboBox
        cboTrangThai_TKNCC.setFont(FONT_TEXT_FIELD);
        cboTrangThai_TKNCC.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Đang hợp tác", "Ngừng hợp tác"})); // Thêm "Tất cả"
        cboTrangThai_TKNCC.setBounds(1236, 40, 396, 33);
        panel_ThongTinTKNCC.add(cboTrangThai_TKNCC);

        // Nút Làm mới cho bộ lọc
        btnLamMoi_TKNCC = new JButton("Làm mới bộ lọc"); // Tạo nút mới
        btnLamMoi_TKNCC.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoi_TKNCC, COLOR_TEXT_MUTED);
        btnLamMoi_TKNCC.setBounds(1472, 237, 160, 40); // Điều chỉnh vị trí ngoài panel lọc
        java.net.URL imgLamMoi_TKNCC = getClass().getResource("/icon-refresh.png");
        if (imgLamMoi_TKNCC != null) {
            ImageIcon originalIcon = new ImageIcon(imgLamMoi_TKNCC);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnLamMoi_TKNCC.setIcon(scaledIcon);
            btnLamMoi_TKNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnLamMoi_TKNCC.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        panel_ThongTinTKNCC.add(btnLamMoi_TKNCC);
        
        JLabel lblDC_DCNCC = new JLabel("Địa chỉ:");
        lblDC_DCNCC.setForeground(new Color(33, 37, 41));
        lblDC_DCNCC.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblDC_DCNCC.setBounds(1106, 84, 132, 33);
        panel_ThongTinTKNCC.add(lblDC_DCNCC);
        
        txtDiaChi_TKNCC = new JTextField();
        txtDiaChi_TKNCC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDiaChi_TKNCC.setColumns(10);
        txtDiaChi_TKNCC.setBounds(1236, 84, 396, 33);
        panel_ThongTinTKNCC.add(txtDiaChi_TKNCC);
        
        JLabel lblGhiChu_TKNCC = new JLabel("Ghi chú:");
        lblGhiChu_TKNCC.setForeground(new Color(33, 37, 41));
        lblGhiChu_TKNCC.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblGhiChu_TKNCC.setBounds(40, 136, 160, 33);
        panel_ThongTinTKNCC.add(lblGhiChu_TKNCC);
        
        txtGhiChu_TKNCC = new JTextArea();
        txtGhiChu_TKNCC.setWrapStyleWord(true);
        txtGhiChu_TKNCC.setLineWrap(true);
        txtGhiChu_TKNCC.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtGhiChu_TKNCC.setBounds(199, 128, 1433, 98);
        panel_ThongTinTKNCC.add(txtGhiChu_TKNCC);
        
        btnXemChiTiet_TKNCC = new JButton("Xem chi tiết");
        btnXemChiTiet_TKNCC.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnXemChiTiet_TKNCC.setBounds(1287, 237, 160, 40);
        java.net.URL imgXem_TKNCC = getClass().getResource("/search-icon.png");
        if (imgXem_TKNCC != null) {
            ImageIcon originalIcon = new ImageIcon(imgXem_TKNCC);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnXemChiTiet_TKNCC.setIcon(scaledIcon);
            btnXemChiTiet_TKNCC.setHorizontalAlignment(SwingConstants.LEFT);
            btnXemChiTiet_TKNCC.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        panel_ThongTinTKNCC.add(btnXemChiTiet_TKNCC);


        // ScrollPane và Bảng kết quả tìm kiếm
        JScrollPane scrollPane_TKNCC = new JScrollPane();
        scrollPane_TKNCC.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_TKNCC.setBounds(10, 370, 1679, 620); // Điều chỉnh vị trí/kích thước
        panel_TimKiemNCC.add(scrollPane_TKNCC);

        // Style bảng table_TKNCC
        table_TKNCC = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_TKNCC);
        table_TKNCC.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null, null, null, null, null},
        	},
        	new String[] {
        		"M\u00E3 NCC", "T\u00EAn NCC", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Email", "\u0110\u1ECBa ch\u1EC9", "Tr\u1EA1ng th\u00E1i", "Ghi ch\u00FA"
        	}
        ));
        scrollPane_TKNCC.setViewportView(table_TKNCC);

        // ===== KẾT THÚC KHỐI CODE TÌM KIẾM NHÀ CUNG CẤP =====
        
//        JPanel panel_QLPhieuDatHangNCC = new JPanel();
//        maincontent.add(panel_QLPhieuDatHangNCC, "quanLyPhieuThuNCC");
//        panel_QLPhieuDatHangNCC.setLayout(new BorderLayout(0, 0));
//        
//        JPanel panel_TKPhieuNhapHang = new JPanel();
//        panel_QLPhieuDatHangNCC.add(panel_TKPhieuNhapHang, BorderLayout.NORTH);
//        
//        JLabel lblTimKiemNCC = new JLabel("Tìm kiếm:");
//        lblTimKiemNCC.setFont(new Font("Times New Roman", Font.BOLD, 20));
//        panel_TKPhieuNhapHang.add(lblTimKiemNCC);
//        
//        txtTimKiem = new JTextField();
//        txtTimKiem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//        panel_TKPhieuNhapHang.add(txtTimKiem);
//        txtTimKiem.setColumns(10);
//        
//        JButton btnTim = new JButton("Tìm");
//        btnTim.setFont(new Font("Times New Roman", Font.BOLD, 20));
//        panel_TKPhieuNhapHang.add(btnTim);
//        
//        JScrollPane scrollPane_PhieuNhapHangNCC = new JScrollPane();
//        panel_QLPhieuDatHangNCC.add(scrollPane_PhieuNhapHangNCC, BorderLayout.CENTER);
//        
//        table = new JTable();
//        table.setModel(new DefaultTableModel(
//        	new Object[][] {
//        		{null, null, null, null, null, null},
//        	},
//        	new String[] {
//        		"M\u00E3 phi\u1EBFu ", "M\u00E3 nh\u00E0 cung c\u1EA5p", "T\u00EAn nh\u00E0 cung c\u1EA5p", "Ng\u00E0y \u0111\u1EB7t", "T\u1ED5ng ti\u1EC1n ", "Tr\u1EA1ng th\u00E1i"
//        	}
//        ));
//        scrollPane_PhieuNhapHangNCC.setViewportView(table);
//        
//        JPanel panel_btnPhieuNhapHangNCC = new JPanel();
//        panel_QLPhieuDatHangNCC.add(panel_btnPhieuNhapHangNCC, BorderLayout.SOUTH);
//        
//        JButton btnTaoMoiPhieu = new JButton("Tạo mới");
//        btnTaoMoiPhieu.setFont(new Font("Times New Roman", Font.BOLD, 20));
//        panel_btnPhieuNhapHangNCC.add(btnTaoMoiPhieu);
//        
//        JButton btnXemSuaPhieu = new JButton("Xem/Sửa");
//        btnXemSuaPhieu.setFont(new Font("Times New Roman", Font.BOLD, 20));
//        panel_btnPhieuNhapHangNCC.add(btnXemSuaPhieu);
//        
//        JButton btnXoaPhieu = new JButton("Xóa");
//        btnXoaPhieu.setFont(new Font("Times New Roman", Font.BOLD, 20));
//        panel_btnPhieuNhapHangNCC.add(btnXoaPhieu);
        
     /// ===== BẮT ĐẦU KHỐI CODE panel_QLPhieuDatHangNCC (Code Sửa Lỗi) =====
        JPanel panel_QLPhieuDatHangNCC = new JPanel();
        maincontent.add(panel_QLPhieuDatHangNCC, "quanLyPhieuDatHangNCC"); 
        panel_QLPhieuDatHangNCC.setLayout(new BorderLayout(0, 0));
        panel_QLPhieuDatHangNCC.setBackground(COLOR_BACKGROUND_PRIMARY); 

        JLabel lblTitle_QLPDH = new JLabel("QUẢN LÝ PHIẾU ĐẶT HÀNG NHÀ CUNG CẤP");
        lblTitle_QLPDH.setFont(FONT_TITLE_MAIN);
        lblTitle_QLPDH.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle_QLPDH.setHorizontalAlignment(SwingConstants.CENTER);
        panel_QLPhieuDatHangNCC.add(lblTitle_QLPDH, BorderLayout.NORTH); 

        // --- PANEL BỘ LỌC TÌM KIẾM ---
        JPanel panel_BoLocPhieuDatHang = new JPanel();
        panel_BoLocPhieuDatHang.setBackground(COLOR_CARD_BACKGROUND);
        panel_BoLocPhieuDatHang.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Bộ Lọc Tìm Kiếm Phiếu Đặt Hàng", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));

        // Bọc panel bộ lọc trong 1 panel khác để tạo khoảng cách
        JPanel northPanelWrapper = new JPanel();
        northPanelWrapper.setLayout(new BoxLayout(northPanelWrapper, BoxLayout.Y_AXIS));
        northPanelWrapper.setBackground(COLOR_BACKGROUND_PRIMARY);
        northPanelWrapper.add(Box.createVerticalStrut(10)); 
        northPanelWrapper.add(panel_BoLocPhieuDatHang);
        northPanelWrapper.add(Box.createVerticalStrut(10)); 
        panel_QLPhieuDatHangNCC.add(northPanelWrapper, BorderLayout.NORTH);

        // Sử dụng GridBagLayout
        GridBagLayout gbl_panel_BoLocPhieuDatHang = new GridBagLayout();
        gbl_panel_BoLocPhieuDatHang.columnWidths = new int[]{0, 0, 0, 0, 0};
        gbl_panel_BoLocPhieuDatHang.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel_BoLocPhieuDatHang.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_BoLocPhieuDatHang.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel_BoLocPhieuDatHang.setLayout(gbl_panel_BoLocPhieuDatHang);


        int yPos = 0; // Vị trí hàng hiện tại

        // Hàng 1: Mã phiếu
        JLabel lblMaPhieu = new JLabel("Mã phiếu:");
        lblMaPhieu.setFont(FONT_LABEL_BOLD); lblMaPhieu.setForeground(COLOR_TEXT_DARK);
        GridBagConstraints gbc_lblMaPhieu = new GridBagConstraints(); // Object riêng
        gbc_lblMaPhieu.insets = new Insets(8, 10, 8, 10);
        gbc_lblMaPhieu.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblMaPhieu.anchor = GridBagConstraints.EAST;
        gbc_lblMaPhieu.gridx = 0;
        gbc_lblMaPhieu.gridy = yPos;
        panel_BoLocPhieuDatHang.add(lblMaPhieu, gbc_lblMaPhieu);

        txtMaPhieu_TKNCC = new JTextField();
        txtMaPhieu_TKNCC.setFont(FONT_TEXT_FIELD);
        GridBagConstraints gbc_txtMaPhieu_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_txtMaPhieu_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_txtMaPhieu_TKNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtMaPhieu_TKNCC.gridx = 1;
        gbc_txtMaPhieu_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(txtMaPhieu_TKNCC, gbc_txtMaPhieu_TKNCC);

        // Hàng 1: Nhà cung cấp
        JLabel lblNhaCungCap = new JLabel("Nhà cung cấp:");
        lblNhaCungCap.setFont(FONT_LABEL_BOLD); lblNhaCungCap.setForeground(COLOR_TEXT_DARK);
        GridBagConstraints gbc_lblNhaCungCap = new GridBagConstraints(); // Object riêng
        gbc_lblNhaCungCap.anchor = GridBagConstraints.EAST;
        gbc_lblNhaCungCap.insets = new Insets(8, 10, 8, 10);
        gbc_lblNhaCungCap.gridx = 2;
        gbc_lblNhaCungCap.gridy = yPos;
        panel_BoLocPhieuDatHang.add(lblNhaCungCap, gbc_lblNhaCungCap);

        cboNCC_TKNCC = new JComboBox<String>();
        cboNCC_TKNCC.setFont(FONT_TEXT_FIELD);
        cboNCC_TKNCC.addItem("Tất cả"); 
        GridBagConstraints gbc_cboNCC_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_cboNCC_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_cboNCC_TKNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboNCC_TKNCC.gridx = 3;
        gbc_cboNCC_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(cboNCC_TKNCC, gbc_cboNCC_TKNCC);

        yPos++; // Sang hàng 2

        // Hàng 2: Trạng thái
        JLabel lblTrangThaiPDH = new JLabel("Trạng thái:");
        lblTrangThaiPDH.setFont(FONT_LABEL_BOLD); lblTrangThaiPDH.setForeground(COLOR_TEXT_DARK);
        GridBagConstraints gbc_lblTrangThaiPDH = new GridBagConstraints(); // Object riêng
        gbc_lblTrangThaiPDH.anchor = GridBagConstraints.EAST;
        gbc_lblTrangThaiPDH.insets = new Insets(8, 10, 8, 10);
        gbc_lblTrangThaiPDH.gridx = 0;
        gbc_lblTrangThaiPDH.gridy = yPos;
        panel_BoLocPhieuDatHang.add(lblTrangThaiPDH, gbc_lblTrangThaiPDH);

        cboTrangThai_TKNCC_PDH = new JComboBox<String>();
        cboTrangThai_TKNCC_PDH.setFont(FONT_TEXT_FIELD);
        cboTrangThai_TKNCC_PDH.setModel(new DefaultComboBoxModel<>(new String[] {"Tất cả", "Đã đặt hàng", "Đã nhập kho", "Đã hủy"}));
        GridBagConstraints gbc_cboTrangThai_TKNCC_PDH = new GridBagConstraints(); // Object riêng
        gbc_cboTrangThai_TKNCC_PDH.insets = new Insets(8, 10, 8, 10);
        gbc_cboTrangThai_TKNCC_PDH.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboTrangThai_TKNCC_PDH.gridx = 1;
        gbc_cboTrangThai_TKNCC_PDH.gridy = yPos;
        panel_BoLocPhieuDatHang.add(cboTrangThai_TKNCC_PDH, gbc_cboTrangThai_TKNCC_PDH);

        // Hàng 2: Từ ngày
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setFont(FONT_LABEL_BOLD); lblTuNgay.setForeground(COLOR_TEXT_DARK);
        GridBagConstraints gbc_lblTuNgay = new GridBagConstraints(); // Object riêng
        gbc_lblTuNgay.anchor = GridBagConstraints.EAST;
        gbc_lblTuNgay.insets = new Insets(8, 10, 8, 10);
        gbc_lblTuNgay.gridx = 2;
        gbc_lblTuNgay.gridy = yPos;
        panel_BoLocPhieuDatHang.add(lblTuNgay, gbc_lblTuNgay);

        dateChooserTuNgay_TKNCC = new JDateChooser();
        dateChooserTuNgay_TKNCC.setFont(FONT_TEXT_FIELD);
        dateChooserTuNgay_TKNCC.setDateFormatString("dd/MM/yyyy");
        GridBagConstraints gbc_dateChooserTuNgay_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_dateChooserTuNgay_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_dateChooserTuNgay_TKNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateChooserTuNgay_TKNCC.gridx = 3;
        gbc_dateChooserTuNgay_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(dateChooserTuNgay_TKNCC, gbc_dateChooserTuNgay_TKNCC);

        yPos++; // Sang hàng 3

        // Hàng 3: Đến ngày
        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setFont(FONT_LABEL_BOLD); lblDenNgay.setForeground(COLOR_TEXT_DARK);
        GridBagConstraints gbc_lblDenNgay = new GridBagConstraints(); // Object riêng
        gbc_lblDenNgay.anchor = GridBagConstraints.EAST;
        gbc_lblDenNgay.insets = new Insets(8, 10, 8, 10);
        gbc_lblDenNgay.gridx = 2;
        gbc_lblDenNgay.gridy = yPos;
        panel_BoLocPhieuDatHang.add(lblDenNgay, gbc_lblDenNgay);

        dateChooserDenNgay_TKNCC = new JDateChooser();
        dateChooserDenNgay_TKNCC.setFont(FONT_TEXT_FIELD);
        dateChooserDenNgay_TKNCC.setDateFormatString("dd/MM/yyyy");
        GridBagConstraints gbc_dateChooserDenNgay_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_dateChooserDenNgay_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_dateChooserDenNgay_TKNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateChooserDenNgay_TKNCC.gridx = 3;
        gbc_dateChooserDenNgay_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(dateChooserDenNgay_TKNCC, gbc_dateChooserDenNgay_TKNCC);

        yPos++; // Sang hàng 4

        // Hàng 4: Tổng tiền 
        JLabel lblTongTien_TKNCC = new JLabel("Tổng tiền:");
        lblTongTien_TKNCC.setFont(FONT_LABEL_BOLD); lblTongTien_TKNCC.setForeground(COLOR_TEXT_DARK);
        GridBagConstraints gbc_lblTongTien_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_lblTongTien_TKNCC.anchor = GridBagConstraints.EAST;
        gbc_lblTongTien_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_lblTongTien_TKNCC.gridx = 0;
        gbc_lblTongTien_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(lblTongTien_TKNCC, gbc_lblTongTien_TKNCC);

        cboLoaiTongTien_TKNCC = new JComboBox<String>();
        cboLoaiTongTien_TKNCC.setFont(FONT_TEXT_FIELD);
        cboLoaiTongTien_TKNCC.setModel(new DefaultComboBoxModel<>(new String[] {"Bằng", "Lớn hơn", "Nhỏ hơn"}));
        GridBagConstraints gbc_cboLoaiTongTien_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_cboLoaiTongTien_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_cboLoaiTongTien_TKNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_cboLoaiTongTien_TKNCC.gridx = 1;
        gbc_cboLoaiTongTien_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(cboLoaiTongTien_TKNCC, gbc_cboLoaiTongTien_TKNCC);

        txtTongTien_TKNCC = new JTextField();
        txtTongTien_TKNCC.setFont(FONT_TEXT_FIELD);
        GridBagConstraints gbc_txtTongTien_TKNCC = new GridBagConstraints(); // Object riêng
        gbc_txtTongTien_TKNCC.gridwidth = 2; // Kéo dài
        gbc_txtTongTien_TKNCC.insets = new Insets(8, 10, 8, 10);
        gbc_txtTongTien_TKNCC.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtTongTien_TKNCC.gridx = 2;
        gbc_txtTongTien_TKNCC.gridy = yPos;
        panel_BoLocPhieuDatHang.add(txtTongTien_TKNCC, gbc_txtTongTien_TKNCC);

        yPos++; // Sang hàng 5

        // Hàng 5: Nút "Làm mới bộ lọc"
        btnLamMoiBoLoc_PDH = new JButton("Làm mới bộ lọc");
        btnLamMoiBoLoc_PDH.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoiBoLoc_PDH, COLOR_TEXT_MUTED); 
        GridBagConstraints gbc_btnLamMoiBoLoc_PDH = new GridBagConstraints(); // Object riêng
        gbc_btnLamMoiBoLoc_PDH.anchor = GridBagConstraints.EAST;
        gbc_btnLamMoiBoLoc_PDH.insets = new Insets(8, 10, 8, 10);
        gbc_btnLamMoiBoLoc_PDH.gridx = 3;
        gbc_btnLamMoiBoLoc_PDH.gridy = yPos;
        panel_BoLocPhieuDatHang.add(btnLamMoiBoLoc_PDH, gbc_btnLamMoiBoLoc_PDH);


        // --- BẢNG HIỂN THỊ DANH SÁCH PHIẾU ĐẶT HÀNG ---
        JScrollPane scrollPane_PhieuDatHangNCC = new JScrollPane();
        scrollPane_PhieuDatHangNCC.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        panel_QLPhieuDatHangNCC.add(scrollPane_PhieuDatHangNCC, BorderLayout.CENTER);

        table_DSPDDH = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_DSPDDH); 
        table_DSPDDH.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null, null, null, null},
            },
            new String[] {
                "Mã phiếu", "Mã nhà cung cấp", "Tên nhà cung cấp", "Ngày đặt", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false 
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane_PhieuDatHangNCC.setViewportView(table_DSPDDH);


        // --- PANEL NÚT CHỨC NĂNG ---
        JPanel panel_btnPhieuDatHangNCC = new JPanel();
        panel_btnPhieuDatHangNCC.setBackground(COLOR_BACKGROUND_PRIMARY);
        panel_btnPhieuDatHangNCC.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); 
        panel_QLPhieuDatHangNCC.add(panel_btnPhieuDatHangNCC, BorderLayout.SOUTH);

        btnTaoMoiPhieu = new JButton("Tạo mới");
        btnTaoMoiPhieu.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnTaoMoiPhieu, COLOR_PRIMARY_BLUE); 
        panel_btnPhieuDatHangNCC.add(btnTaoMoiPhieu);

        btnXemSuaPhieu = new JButton("Xem/Sửa");
        btnXemSuaPhieu.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnXemSuaPhieu, COLOR_SIDEBAR_BG_START); 
        panel_btnPhieuDatHangNCC.add(btnXemSuaPhieu);

        btnHuyPhieu = new JButton("Hủy phiếu"); 
        btnHuyPhieu.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnHuyPhieu, COLOR_DANGER_RED); 
        panel_btnPhieuDatHangNCC.add(btnHuyPhieu);

        // === HẾT KHỐI CODE panel_QLPhieuDatHangNCC ===
        
     // ===== PANEL QUẢN LÝ TÀI KHOẢN (ADMIN) - REFACTORED =====
        JPanel pn_QuanLyTaiKhoan = new JPanel();
        maincontent.add(pn_QuanLyTaiKhoan, "quanLyTaiKhoan"); 
        pn_QuanLyTaiKhoan.setLayout(new BorderLayout(10, 10)); 
        pn_QuanLyTaiKhoan.setBackground(COLOR_BACKGROUND_PRIMARY); 
        pn_QuanLyTaiKhoan.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

        // 1. Tiêu đề
        JLabel lblTitleQLTK = new JLabel("QUẢN LÝ TÀI KHOẢN HỆ THỐNG");
        lblTitleQLTK.setFont(FONT_TITLE_MAIN); 
        lblTitleQLTK.setForeground(COLOR_PRIMARY_BLUE); 
        lblTitleQLTK.setHorizontalAlignment(SwingConstants.CENTER); 
        pn_QuanLyTaiKhoan.add(lblTitleQLTK, BorderLayout.NORTH); 

        // 2. Bảng dữ liệu
        JScrollPane scrollPaneQLTK = new JScrollPane();
        scrollPaneQLTK.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT)); 
        pn_QuanLyTaiKhoan.add(scrollPaneQLTK, BorderLayout.CENTER); 

        tableQLTK = new JTable() {
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground()); 
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(tableQLTK); 
        // Cập nhật model có thêm cột Trạng thái
        tableQLTK.setModel(new DefaultTableModel(
            new Object[][] {}, 
            new String[] {"Mã TK", "Tên Tài Khoản", "Quyền Hạn", "Trạng Thái"}
        ));
        scrollPaneQLTK.setViewportView(tableQLTK); 

        // 3. Panel Chức năng (Nút bấm) - Đã bỏ hết TextFields
        JPanel pnlChucNangQLTK = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
        pnlChucNangQLTK.setOpaque(false); 
        pn_QuanLyTaiKhoan.add(pnlChucNangQLTK, BorderLayout.SOUTH); 

        JButton btnThemTK = new JButton("Thêm Tài Khoản");
        btnThemTK.setFont(FONT_BUTTON_STANDARD);
        btnThemTK.setPreferredSize(new Dimension(150, 40));
        styleButton(btnThemTK, COLOR_SUCCESS_GREEN);
        
        JButton btnCapNhatTK = new JButton("Cập Nhật");
        btnCapNhatTK.setFont(FONT_BUTTON_STANDARD);
        btnCapNhatTK.setPreferredSize(new Dimension(150, 40));
        styleButton(btnCapNhatTK, COLOR_PRIMARY_BLUE); // Màu xanh dương cho nút sửa
 
        pnlChucNangQLTK.add(btnThemTK);
        pnlChucNangQLTK.add(btnCapNhatTK);

        QuanLyTaiKhoanController qltkController = new QuanLyTaiKhoanController(pn_QuanLyTaiKhoan, tableQLTK, this);

        btnThemTK.addActionListener(e -> qltkController.themTaiKhoan(QuanLyHieuThuocTay)); 
        btnCapNhatTK.addActionListener(e -> qltkController.capNhatTaiKhoan(QuanLyHieuThuocTay));

// ===== KẾT THÚC PANEL QUẢN LÝ TÀI KHOẢN =====
        
     // ===== BẮT ĐẦU KHỐI CODE THÊM KHUYẾN MÃI =====
        JPanel pn_ThemKhuyenMai = new JPanel();
        maincontent.add(pn_ThemKhuyenMai, "themKM"); // Key: "themKM"
        pn_ThemKhuyenMai.setLayout(null);
        pn_ThemKhuyenMai.setBackground(COLOR_BACKGROUND_PRIMARY);

        JLabel lblTitle_ThemKM = new JLabel("THÊM CHƯƠNG TRÌNH KHUYẾN MÃI");
        lblTitle_ThemKM.setFont(FONT_TITLE_MAIN);
        lblTitle_ThemKM.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle_ThemKM.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle_ThemKM.setBounds(0, 11, 1584, 46);
        pn_ThemKhuyenMai.add(lblTitle_ThemKM);

        // --- Panel Form Nhập Liệu ---
        JPanel pnlForm_ThemKM = new JPanel();
        pnlForm_ThemKM.setBackground(COLOR_CARD_BACKGROUND);
        pnlForm_ThemKM.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Khuyến Mãi", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlForm_ThemKM.setBounds(10, 68, 1679, 300); // Tăng chiều cao
        pn_ThemKhuyenMai.add(pnlForm_ThemKM);
        pnlForm_ThemKM.setLayout(null);

        int labelX_tkm = 40;
        int inputX_tkm = 180;
        int labelX2_tkm = 800;
        int inputX2_tkm = 940;
        int startY_tkm = 40;
        int height_tkm = 33;
        int vGap_tkm = 20; // Khoảng cách dọc

        JLabel lblMaKM_Them = new JLabel("Mã Khuyến Mãi:");
        lblMaKM_Them.setFont(FONT_LABEL_BOLD);
        lblMaKM_Them.setForeground(COLOR_TEXT_DARK);
        lblMaKM_Them.setBounds(labelX_tkm, startY_tkm, 130, height_tkm);
        pnlForm_ThemKM.add(lblMaKM_Them);

        txtMaKM_Them = new JTextField();
        txtMaKM_Them.setFont(FONT_TEXT_FIELD);
        txtMaKM_Them.setBounds(180, 40, 632, 33);
        txtMaKM_Them.setEditable(false); 
        txtMaKM_Them.setBackground(new Color(230, 230, 230)); 
        pnlForm_ThemKM.add(txtMaKM_Them);

        JLabel lblTenKM_Them = new JLabel("Tên Chương Trình:");
        lblTenKM_Them.setFont(FONT_LABEL_BOLD);
        lblTenKM_Them.setForeground(COLOR_TEXT_DARK);
        lblTenKM_Them.setBounds(labelX_tkm, startY_tkm + (height_tkm + vGap_tkm), 130, height_tkm);
        pnlForm_ThemKM.add(lblTenKM_Them);

        txtTenKM_Them = new JTextField();
        txtTenKM_Them.setFont(FONT_TEXT_FIELD);
        txtTenKM_Them.setBounds(180, 93, 632, 33);
        pnlForm_ThemKM.add(txtTenKM_Them);

        JLabel lblGiaTri_Them = new JLabel("Giá Trị (%):");
        lblGiaTri_Them.setFont(FONT_LABEL_BOLD);
        lblGiaTri_Them.setForeground(COLOR_TEXT_DARK);
        lblGiaTri_Them.setBounds(labelX_tkm, startY_tkm + 2 * (height_tkm + vGap_tkm), 130, height_tkm);
        pnlForm_ThemKM.add(lblGiaTri_Them);

        txtGiaTri_Them = new JTextField();
        txtGiaTri_Them.setFont(FONT_TEXT_FIELD);
        txtGiaTri_Them.setBounds(180, 146, 632, 33);
        pnlForm_ThemKM.add(txtGiaTri_Them);
        
        JLabel lblSoLuong_Them = new JLabel("Số Lượng Tối Đa:");
        lblSoLuong_Them.setFont(FONT_LABEL_BOLD);
        lblSoLuong_Them.setForeground(COLOR_TEXT_DARK);
        lblSoLuong_Them.setBounds(labelX_tkm, startY_tkm + 3 * (height_tkm + vGap_tkm), 130, height_tkm);
        pnlForm_ThemKM.add(lblSoLuong_Them);

        txtSoLuong_Them = new JTextField();
        txtSoLuong_Them.setFont(FONT_TEXT_FIELD);
        txtSoLuong_Them.setBounds(180, 199, 632, 33);
        pnlForm_ThemKM.add(txtSoLuong_Them);

        // Cột 2
        JLabel lblNgayBD_Them = new JLabel("Ngày Bắt Đầu:");
        lblNgayBD_Them.setFont(FONT_LABEL_BOLD);
        lblNgayBD_Them.setForeground(COLOR_TEXT_DARK);
        lblNgayBD_Them.setBounds(847, 40, 130, height_tkm);
        pnlForm_ThemKM.add(lblNgayBD_Them);

        dateNgayBD_Them = new JDateChooser();
        dateNgayBD_Them.setFont(FONT_TEXT_FIELD);
        dateNgayBD_Them.setDateFormatString("dd/MM/yyyy");
        dateNgayBD_Them.setBounds(987, 40, 629, 33);
        pnlForm_ThemKM.add(dateNgayBD_Them);

        JLabel lblNgayKT_Them = new JLabel("Ngày Kết Thúc:");
        lblNgayKT_Them.setFont(FONT_LABEL_BOLD);
        lblNgayKT_Them.setForeground(COLOR_TEXT_DARK);
        lblNgayKT_Them.setBounds(847, 93, 130, height_tkm);
        pnlForm_ThemKM.add(lblNgayKT_Them);

        dateNgayKT_Them = new JDateChooser();
        dateNgayKT_Them.setFont(FONT_TEXT_FIELD);
        dateNgayKT_Them.setDateFormatString("dd/MM/yyyy");
        dateNgayKT_Them.setBounds(987, 93, 629, 33);
        pnlForm_ThemKM.add(dateNgayKT_Them);

        JLabel lblTrangThai_Them = new JLabel("Trạng Thái:");
        lblTrangThai_Them.setFont(FONT_LABEL_BOLD);
        lblTrangThai_Them.setForeground(COLOR_TEXT_DARK);
        lblTrangThai_Them.setBounds(847, 146, 130, height_tkm);
        pnlForm_ThemKM.add(lblTrangThai_Them);

        cboTrangThai_Them = new JComboBox<>(new String[] {"Đang diễn ra", "Đã kết thúc"});
        cboTrangThai_Them.setFont(FONT_TEXT_FIELD);
        cboTrangThai_Them.setBounds(987, 146, 629, 33);
        pnlForm_ThemKM.add(cboTrangThai_Them);

        // Nút chức năng
        btnLamMoi_ThemKM = new JButton("Làm Mới");
        btnLamMoi_ThemKM.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoi_ThemKM, COLOR_TEXT_MUTED);
        btnLamMoi_ThemKM.setBounds(1213, 238, 150, 40);
        java.net.URL imgLamMoi_ThemKM = getClass().getResource("/icon-refresh.png");
        if (imgLamMoi_ThemKM != null) {
            ImageIcon originalIcon = new ImageIcon(imgLamMoi_ThemKM);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnLamMoi_ThemKM.setIcon(scaledIcon);
            btnLamMoi_ThemKM.setHorizontalAlignment(SwingConstants.LEFT);
            btnLamMoi_ThemKM.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        pnlForm_ThemKM.add(btnLamMoi_ThemKM);

        btnThem_ThemKM = new JButton("Thêm Khuyến Mãi");
        btnThem_ThemKM.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnThem_ThemKM, COLOR_SUCCESS_GREEN);
        btnThem_ThemKM.setBounds(1419, 238, 197, 40);
        java.net.URL imgThem_ThemKM = getClass().getResource("/icon-add.png");
        if (imgThem_ThemKM != null) {
            ImageIcon originalIcon = new ImageIcon(imgThem_ThemKM);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnThem_ThemKM.setIcon(scaledIcon);
            btnThem_ThemKM.setHorizontalAlignment(SwingConstants.LEFT);
            btnThem_ThemKM.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        pnlForm_ThemKM.add(btnThem_ThemKM);

        // --- Bảng Hiển Thị ---
        JScrollPane scrollPane_ThemKM = new JScrollPane();
        scrollPane_ThemKM.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_ThemKM.setBounds(10, 380, 1679, 610);
        pn_ThemKhuyenMai.add(scrollPane_ThemKM);

        table_ThemKM = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_ThemKM);
        table_ThemKM.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã KM", "Tên Chương Trình", "Giá Trị (%)", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Số Lượng Tối Đa", "Trạng Thái"}
        ));
        scrollPane_ThemKM.setViewportView(table_ThemKM);

        // ===== KẾT THÚC KHỐI CODE THÊM KHUYẾN MÃI =====
        
     // ===== BẮT ĐẦU KHỐI CODE CẬP NHẬT KHUYẾN MÃI =====
        JPanel pn_CapNhatKhuyenMai = new JPanel();
        maincontent.add(pn_CapNhatKhuyenMai, "capNhatKM"); // Key: "capNhatKM"
        pn_CapNhatKhuyenMai.setLayout(null);
        pn_CapNhatKhuyenMai.setBackground(COLOR_BACKGROUND_PRIMARY);

        JLabel lblTitle_CapNhatKM = new JLabel("CẬP NHẬT CHƯƠNG TRÌNH KHUYẾN MÃI");
        lblTitle_CapNhatKM.setFont(FONT_TITLE_MAIN);
        lblTitle_CapNhatKM.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle_CapNhatKM.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle_CapNhatKM.setBounds(0, 11, 1584, 46);
        pn_CapNhatKhuyenMai.add(lblTitle_CapNhatKM);

        // --- Panel Form Cập Nhật ---
        JPanel pnlForm_CapNhatKM = new JPanel();
        pnlForm_CapNhatKM.setBackground(COLOR_CARD_BACKGROUND);
        pnlForm_CapNhatKM.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlForm_CapNhatKM.setBounds(10, 68, 1679, 300);
        pn_CapNhatKhuyenMai.add(pnlForm_CapNhatKM);
        pnlForm_CapNhatKM.setLayout(null);

        // (Layout giống hệt panel Thêm)
        int labelX_cnkm = 40;
        int inputX_cnkm = 180;
        int labelX2_cnkm = 800;
        int inputX2_cnkm = 940;
        int startY_cnkm = 40;
        int height_cnkm = 33;
        int vGap_cnkm = 20;

        JLabel lblMaKM_CapNhat = new JLabel("Mã Khuyến Mãi:");
        lblMaKM_CapNhat.setFont(FONT_LABEL_BOLD);
        lblMaKM_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblMaKM_CapNhat.setBounds(labelX_cnkm, startY_cnkm, 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblMaKM_CapNhat);

        txtMaKM_CapNhat = new JTextField();
        txtMaKM_CapNhat.setFont(FONT_TEXT_FIELD);
        txtMaKM_CapNhat.setBounds(180, 40, 646, 33);
        txtMaKM_CapNhat.setEditable(false); // Mã không cho sửa
        txtMaKM_CapNhat.setBackground(new Color(230, 230, 230)); // Nền xám
        pnlForm_CapNhatKM.add(txtMaKM_CapNhat);

        JLabel lblTenKM_CapNhat = new JLabel("Tên Chương Trình:");
        lblTenKM_CapNhat.setFont(FONT_LABEL_BOLD);
        lblTenKM_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblTenKM_CapNhat.setBounds(labelX_cnkm, startY_cnkm + (height_cnkm + vGap_cnkm), 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblTenKM_CapNhat);

        txtTenKM_CapNhat = new JTextField();
        txtTenKM_CapNhat.setFont(FONT_TEXT_FIELD);
        txtTenKM_CapNhat.setBounds(180, 93, 646, 33);
        pnlForm_CapNhatKM.add(txtTenKM_CapNhat);

        JLabel lblGiaTri_CapNhat = new JLabel("Giá Trị (%):");
        lblGiaTri_CapNhat.setFont(FONT_LABEL_BOLD);
        lblGiaTri_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblGiaTri_CapNhat.setBounds(labelX_cnkm, startY_cnkm + 2 * (height_cnkm + vGap_cnkm), 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblGiaTri_CapNhat);

        txtGiaTri_CapNhat = new JTextField();
        txtGiaTri_CapNhat.setFont(FONT_TEXT_FIELD);
        txtGiaTri_CapNhat.setBounds(180, 146, 646, 33);
        pnlForm_CapNhatKM.add(txtGiaTri_CapNhat);
        
        JLabel lblSoLuong_CapNhat = new JLabel("Số Lượng Tối Đa:");
        lblSoLuong_CapNhat.setFont(FONT_LABEL_BOLD);
        lblSoLuong_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblSoLuong_CapNhat.setBounds(labelX_cnkm, startY_cnkm + 3 * (height_cnkm + vGap_cnkm), 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblSoLuong_CapNhat);

        txtSoLuong_CapNhat = new JTextField();
        txtSoLuong_CapNhat.setFont(FONT_TEXT_FIELD);
        txtSoLuong_CapNhat.setBounds(180, 199, 646, 33);
        pnlForm_CapNhatKM.add(txtSoLuong_CapNhat);

        JLabel lblNgayBD_CapNhat = new JLabel("Ngày Bắt Đầu:");
        lblNgayBD_CapNhat.setFont(FONT_LABEL_BOLD);
        lblNgayBD_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblNgayBD_CapNhat.setBounds(836, 40, 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblNgayBD_CapNhat);

        dateNgayBD_CapNhat = new JDateChooser();
        dateNgayBD_CapNhat.setFont(FONT_TEXT_FIELD);
        dateNgayBD_CapNhat.setDateFormatString("dd/MM/yyyy");
        dateNgayBD_CapNhat.setBounds(976, 40, 643, 33);
        pnlForm_CapNhatKM.add(dateNgayBD_CapNhat);

        JLabel lblNgayKT_CapNhat = new JLabel("Ngày Kết Thúc:");
        lblNgayKT_CapNhat.setFont(FONT_LABEL_BOLD);
        lblNgayKT_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblNgayKT_CapNhat.setBounds(836, 93, 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblNgayKT_CapNhat);

        dateNgayKT_CapNhat = new JDateChooser();
        dateNgayKT_CapNhat.setFont(FONT_TEXT_FIELD);
        dateNgayKT_CapNhat.setDateFormatString("dd/MM/yyyy");
        dateNgayKT_CapNhat.setBounds(976, 93, 643, 33);
        pnlForm_CapNhatKM.add(dateNgayKT_CapNhat);

        JLabel lblTrangThai_CapNhat = new JLabel("Trạng Thái:");
        lblTrangThai_CapNhat.setFont(FONT_LABEL_BOLD);
        lblTrangThai_CapNhat.setForeground(COLOR_TEXT_DARK);
        lblTrangThai_CapNhat.setBounds(836, 146, 130, height_cnkm);
        pnlForm_CapNhatKM.add(lblTrangThai_CapNhat);

        cboTrangThai_CapNhat = new JComboBox<>(new String[] {"Đang diễn ra", "Đã kết thúc"});
        cboTrangThai_CapNhat.setFont(FONT_TEXT_FIELD);
        cboTrangThai_CapNhat.setBounds(976, 146, 643, 33);
        pnlForm_CapNhatKM.add(cboTrangThai_CapNhat);

        btnKhoiPhuc_CapNhatKM = new JButton("Khôi Phục");
        btnKhoiPhuc_CapNhatKM.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnKhoiPhuc_CapNhatKM, COLOR_TEXT_MUTED);
        btnKhoiPhuc_CapNhatKM.setBounds(1209, 240, 150, 40);
        java.net.URL imgKhoiPhuc_CapNhatKM = getClass().getResource("/back-icon.png");
        if (imgKhoiPhuc_CapNhatKM != null) {
            ImageIcon originalIcon = new ImageIcon(imgKhoiPhuc_CapNhatKM);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnKhoiPhuc_CapNhatKM.setIcon(scaledIcon);
            btnKhoiPhuc_CapNhatKM.setHorizontalAlignment(SwingConstants.LEFT);
            btnKhoiPhuc_CapNhatKM.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        pnlForm_CapNhatKM.add(btnKhoiPhuc_CapNhatKM);

        btnXoa_CapNhatKM = new JButton("Xóa");
        btnXoa_CapNhatKM.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnXoa_CapNhatKM, COLOR_DANGER_RED);
        btnXoa_CapNhatKM.setBounds(1379, 240, 130, 40);
        java.net.URL imgXoa_CapNhatKM = getClass().getResource("/delete-icon.png");
        if (imgXoa_CapNhatKM != null) {
            ImageIcon originalIcon = new ImageIcon(imgXoa_CapNhatKM);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnXoa_CapNhatKM.setIcon(scaledIcon);
            btnXoa_CapNhatKM.setHorizontalAlignment(SwingConstants.LEFT);
            btnXoa_CapNhatKM.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        pnlForm_CapNhatKM.add(btnXoa_CapNhatKM);

        btnCapNhat_CapNhatKM = new JButton("Cập Nhật");
        btnCapNhat_CapNhatKM.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnCapNhat_CapNhatKM, COLOR_SUCCESS_GREEN);
        btnCapNhat_CapNhatKM.setBounds(1529, 240, 140, 40);
        java.net.URL imgCapNhat_CapNhatKM = getClass().getResource("/update-icon.png");
        if (imgCapNhat_CapNhatKM != null) {
            ImageIcon originalIcon = new ImageIcon(imgCapNhat_CapNhatKM);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnCapNhat_CapNhatKM.setIcon(scaledIcon);
            btnCapNhat_CapNhatKM.setHorizontalAlignment(SwingConstants.LEFT);
            btnCapNhat_CapNhatKM.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        pnlForm_CapNhatKM.add(btnCapNhat_CapNhatKM);

        // --- Panel Tìm Kiếm ---
        JPanel pnlTimKiem_CapNhatKM = new JPanel();
        pnlTimKiem_CapNhatKM.setBackground(COLOR_CARD_BACKGROUND);
        pnlTimKiem_CapNhatKM.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Tìm Kiếm Khuyến Mãi Cần Cập Nhật", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlTimKiem_CapNhatKM.setBounds(10, 380, 1679, 84);
        pn_CapNhatKhuyenMai.add(pnlTimKiem_CapNhatKM);
        pnlTimKiem_CapNhatKM.setLayout(null);

        JLabel lblMaKM_TK_CapNhat = new JLabel("Mã/Tên KM:");
        lblMaKM_TK_CapNhat.setBounds(40, 30, 140, 30);
        lblMaKM_TK_CapNhat.setFont(FONT_LABEL_BOLD);
        lblMaKM_TK_CapNhat.setForeground(COLOR_TEXT_DARK);
        pnlTimKiem_CapNhatKM.add(lblMaKM_TK_CapNhat);

        txtMaTenKM_TK_CapNhat = new JTextField();
        txtMaTenKM_TK_CapNhat.setFont(FONT_TEXT_FIELD);
        txtMaTenKM_TK_CapNhat.setColumns(10);
        txtMaTenKM_TK_CapNhat.setBounds(190, 30, 1305, 33);
        pnlTimKiem_CapNhatKM.add(txtMaTenKM_TK_CapNhat);

        btnLamMoi_TK_CapNhatKM = new JButton("Làm mới TK");
        btnLamMoi_TK_CapNhatKM.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoi_TK_CapNhatKM, COLOR_TEXT_MUTED);
        btnLamMoi_TK_CapNhatKM.setBounds(1539, 29, 130, 35);
        pnlTimKiem_CapNhatKM.add(btnLamMoi_TK_CapNhatKM);

        // --- Bảng Hiển Thị ---
        JScrollPane scrollPane_CapNhatKM = new JScrollPane();
        scrollPane_CapNhatKM.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_CapNhatKM.setBounds(10, 475, 1679, 515);
        pn_CapNhatKhuyenMai.add(scrollPane_CapNhatKM);

        table_CapNhatKM  = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_CapNhatKM);
        table_CapNhatKM.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã KM", "Tên Chương Trình", "Giá Trị (%)", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Số Lượng Tối Đa", "Trạng Thái"}
        ));
        scrollPane_CapNhatKM.setViewportView(table_CapNhatKM);

        // ===== KẾT THÚC KHỐI CODE CẬP NHẬT KHUYẾN MÃI =====
        
     // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM KHUYẾN MÃI =====
        JPanel pn_TimKiemKhuyenMai = new JPanel();
        maincontent.add(pn_TimKiemKhuyenMai, "timKiemKM"); // Key: "timKiemKM"
        pn_TimKiemKhuyenMai.setLayout(null);
        pn_TimKiemKhuyenMai.setBackground(COLOR_BACKGROUND_PRIMARY);

        JLabel lblTitle_TimKiemKM = new JLabel("TÌM KIẾM CHƯƠNG TRÌNH KHUYẾN MÃI");
        lblTitle_TimKiemKM.setFont(FONT_TITLE_MAIN);
        lblTitle_TimKiemKM.setForeground(COLOR_PRIMARY_BLUE);
        lblTitle_TimKiemKM.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle_TimKiemKM.setBounds(0, 11, 1584, 46);
        pn_TimKiemKhuyenMai.add(lblTitle_TimKiemKM);

        // --- Panel Bộ Lọc Tìm Kiếm ---
        JPanel pnlFilter_TimKiemKM = new JPanel();
        pnlFilter_TimKiemKM.setBackground(COLOR_CARD_BACKGROUND);
        pnlFilter_TimKiemKM.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Bộ Lọc Tìm Kiếm", TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pnlFilter_TimKiemKM.setBounds(10, 68, 1679, 150); // Tăng chiều cao
        pn_TimKiemKhuyenMai.add(pnlFilter_TimKiemKM);
        pnlFilter_TimKiemKM.setLayout(null);
        
        int labelX_tkkm = 40;
        int inputX_tkkm = 180;
        int labelX2_tkkm = 550;
        int inputX2_tkkm = 690;
        int labelX3_tkkm = 1050;
        int inputX3_tkkm = 1190;
        int startY_tkkm = 30;
        int height_tkkm = 33;
        int vGap_tkkm = 20;

        JLabel lblMaTenKM_TK = new JLabel("Mã/Tên KM:");
        lblMaTenKM_TK.setFont(FONT_LABEL_BOLD);
        lblMaTenKM_TK.setForeground(COLOR_TEXT_DARK);
        lblMaTenKM_TK.setBounds(labelX_tkkm, startY_tkkm, 130, height_tkkm);
        pnlFilter_TimKiemKM.add(lblMaTenKM_TK);

        txtMaTenKM_TK = new JTextField();
        txtMaTenKM_TK.setFont(FONT_TEXT_FIELD);
        txtMaTenKM_TK.setBounds(inputX_tkkm, startY_tkkm, 320, height_tkkm);
        pnlFilter_TimKiemKM.add(txtMaTenKM_TK);

        JLabel lblNgayBD_TK = new JLabel("Từ Ngày:");
        lblNgayBD_TK.setFont(FONT_LABEL_BOLD);
        lblNgayBD_TK.setForeground(COLOR_TEXT_DARK);
        lblNgayBD_TK.setBounds(560, 30, 130, height_tkkm);
        pnlFilter_TimKiemKM.add(lblNgayBD_TK);

        dateNgayBD_TK = new JDateChooser();
        dateNgayBD_TK.setFont(FONT_TEXT_FIELD);
        dateNgayBD_TK.setDateFormatString("dd/MM/yyyy");
        dateNgayBD_TK.setBounds(700, 30, 320, height_tkkm);
        pnlFilter_TimKiemKM.add(dateNgayBD_TK);

        JLabel lblNgayKT_TK = new JLabel("Đến Ngày:");
        lblNgayKT_TK.setFont(FONT_LABEL_BOLD);
        lblNgayKT_TK.setForeground(COLOR_TEXT_DARK);
        lblNgayKT_TK.setBounds(560, 83, 130, height_tkkm);
        pnlFilter_TimKiemKM.add(lblNgayKT_TK);

        dateNgayKT_TK = new JDateChooser();
        dateNgayKT_TK.setFont(FONT_TEXT_FIELD);
        dateNgayKT_TK.setDateFormatString("dd/MM/yyyy");
        dateNgayKT_TK.setBounds(700, 83, 320, height_tkkm);
        pnlFilter_TimKiemKM.add(dateNgayKT_TK);

        JLabel lblTrangThai_TK = new JLabel("Trạng Thái:");
        lblTrangThai_TK.setFont(FONT_LABEL_BOLD);
        lblTrangThai_TK.setForeground(COLOR_TEXT_DARK);
        lblTrangThai_TK.setBounds(1060, 30, 130, height_tkkm);
        pnlFilter_TimKiemKM.add(lblTrangThai_TK);

        cboTrangThai_TK = new JComboBox<>(new String[] {"Tất cả", "Đang diễn ra", "Đã kết thúc"});
        cboTrangThai_TK.setFont(FONT_TEXT_FIELD);
        cboTrangThai_TK.setBounds(1200, 30, 442, 33);
        pnlFilter_TimKiemKM.add(cboTrangThai_TK);

        btnLamMoi_TK = new JButton("Làm Mới");
        btnLamMoi_TK.setFont(FONT_BUTTON_STANDARD);
        styleButton(btnLamMoi_TK, COLOR_TEXT_MUTED);
        btnLamMoi_TK.setBounds(1482, 80, 160, 40);
        java.net.URL imgLamMoi_TK= getClass().getResource("/icon-refresh.png");
        if (imgLamMoi_TK != null) {
            ImageIcon originalIcon = new ImageIcon(imgLamMoi_TK);
            Image img = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            btnLamMoi_TK.setIcon(scaledIcon);
            btnLamMoi_TK.setHorizontalAlignment(SwingConstants.LEFT);
            btnLamMoi_TK.setIconTextGap(10);
        } else {
            System.err.println("Lỗi: Không tìm thấy ảnh");
        }
        pnlFilter_TimKiemKM.add(btnLamMoi_TK);

        // --- Bảng Hiển Thị ---
        JScrollPane scrollPane_TimKiemKM = new JScrollPane();
        scrollPane_TimKiemKM.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scrollPane_TimKiemKM.setBounds(10, 230, 1679, 760);
        pn_TimKiemKhuyenMai.add(scrollPane_TimKiemKM);

        table_TimKiemKM = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                    c.setForeground(this.getForeground());
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_TimKiemKM);
        table_TimKiemKM.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"Mã KM", "Tên Chương Trình", "Giá Trị (%)", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Số Lượng Tối Đa", "Trạng Thái"}
        ));
        scrollPane_TimKiemKM.setViewportView(table_TimKiemKM);

        // ===== KẾT THÚC KHỐI CODE TÌM KIẾM KHUYẾN MÃI =====
     // ===== BẮT ĐẦU KHỐI CODE TÌM KIẾM Trả thuốc =====
        
     // ===== BẮT ĐẦU KHỐI CODE TRẢ THUỐC ĐÃ LÀM LẠI GIAO DIỆN =====
        JPanel pn_TraThuoc = new JPanel();
        maincontent.add(pn_TraThuoc, "trathuoc");
        pn_TraThuoc.setLayout(null);
        pn_TraThuoc.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        // --- Panel Tiêu đề ---
        JPanel panel_trathuoc_title = new JPanel();
        panel_trathuoc_title.setLayout(null);
        // panel_trathuoc_title.setBackground(new Color(0, 102, 102)); // Bỏ màu cũ
        panel_trathuoc_title.setBackground(COLOR_PRIMARY_BLUE); // Màu xanh dương chủ đạo
        panel_trathuoc_title.setBounds(0, 0, 1699, 67);
        pn_TraThuoc.add(panel_trathuoc_title);

        JLabel lbl_trathuoc_title = new JLabel("TRẢ THUỐC");
        lbl_trathuoc_title.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_trathuoc_title.setForeground(Color.WHITE);
        lbl_trathuoc_title.setFont(FONT_TITLE_MAIN); // Font tiêu đề chính
        lbl_trathuoc_title.setBounds(0, 11, 1699, 45);
        panel_trathuoc_title.add(lbl_trathuoc_title);

        // --- Panel Bên Trái (Tìm kiếm Hóa Đơn) ---
        JPanel pn_trathuoc_tkhd = new JPanel();
        pn_trathuoc_tkhd.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
        pn_trathuoc_tkhd.setBounds(10, 80, 681, 910); // Điều chỉnh vị trí, kích thước
        pn_TraThuoc.add(pn_trathuoc_tkhd);
        pn_trathuoc_tkhd.setLayout(null);

        // Panel Nhập liệu tìm kiếm
        JPanel pn_trathuoc_tkhd_nhap = new JPanel();
        pn_trathuoc_tkhd_nhap.setBounds(0, 0, 671, 259); // Kích thước cũ
        pn_trathuoc_tkhd_nhap.setLayout(null);
        pn_trathuoc_tkhd_nhap.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Tìm Kiếm Hóa Đơn Cần Trả", // Tiêu đề rõ hơn
            TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_trathuoc_tkhd_nhap.setBackground(COLOR_CARD_BACKGROUND);
        pn_trathuoc_tkhd.add(pn_trathuoc_tkhd_nhap);

        JLabel lbl_trathuoc_tkhd_mahd = new JLabel("Mã Hóa Đơn:"); // Font, màu
        lbl_trathuoc_tkhd_mahd.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_tkhd_mahd.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_tkhd_mahd.setBounds(30, 40, 140, 30); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(lbl_trathuoc_tkhd_mahd);

        txt_trathuoc_tkhd_maHD = new JTextField(); // Font
        txt_trathuoc_tkhd_maHD.setFont(FONT_TEXT_FIELD);
        txt_trathuoc_tkhd_maHD.setColumns(10);
        txt_trathuoc_tkhd_maHD.setBounds(180, 40, 460, 30); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(txt_trathuoc_tkhd_maHD);

        JLabel lbl_trathuoc_tkhd_tenKH = new JLabel("Tên Khách Hàng:"); // Font, màu
        lbl_trathuoc_tkhd_tenKH.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_tkhd_tenKH.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_tkhd_tenKH.setBounds(30, 90, 140, 30); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(lbl_trathuoc_tkhd_tenKH);

        txt_trathuoc_tkhd_tenKH = new JTextField(); // Font
        txt_trathuoc_tkhd_tenKH.setFont(FONT_TEXT_FIELD);
        txt_trathuoc_tkhd_tenKH.setColumns(10);
        txt_trathuoc_tkhd_tenKH.setBounds(180, 90, 460, 30); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(txt_trathuoc_tkhd_tenKH);

        JLabel lbl_trathuoc_tkhd_ngayLap = new JLabel("Ngày Lập:"); // Font, màu
        lbl_trathuoc_tkhd_ngayLap.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_tkhd_ngayLap.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_tkhd_ngayLap.setBounds(30, 140, 140, 30); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(lbl_trathuoc_tkhd_ngayLap);

        dateNgayLapHD_TKHD = new JDateChooser(); // Font, format
        dateNgayLapHD_TKHD.setFont(FONT_TEXT_FIELD);
        dateNgayLapHD_TKHD.setDateFormatString("dd/MM/yyyy");
        dateNgayLapHD_TKHD.setBounds(180, 140, 460, 30); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(dateNgayLapHD_TKHD);

        btn_trathuoc_lammoi = new JButton("Làm Mới"); // Style, vị trí
        styleButton(btn_trathuoc_lammoi, COLOR_TEXT_MUTED);
        btn_trathuoc_lammoi.setFont(FONT_BUTTON_STANDARD);
        btn_trathuoc_lammoi.setBounds(270, 190, 136, 40); // Điều chỉnh
        pn_trathuoc_tkhd_nhap.add(btn_trathuoc_lammoi);

        // Bảng kết quả tìm kiếm hóa đơn
        JScrollPane scP_trathuoc_tkhd = new JScrollPane();
        scP_trathuoc_tkhd.setBounds(0, 270, 671, 630); // Điều chỉnh vị trí, kích thước
        scP_trathuoc_tkhd.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        pn_trathuoc_tkhd.add(scP_trathuoc_tkhd);

        table_trathuoc_tkhd = new JTable() { // Thêm style zebra
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_trathuoc_tkhd); // Áp dụng style chung
        table_trathuoc_tkhd.setModel(new DefaultTableModel(
            new Object[][] {}, // Dữ liệu rỗng
            new String[] {
                "Mã HD", "Tên KH", "SĐT KH", "Nhân Viên", "Ngày Lập", "KM", "Tổng Tiền" // Rút gọn tên cột
            }
        ));
        scP_trathuoc_tkhd.setViewportView(table_trathuoc_tkhd);

        // --- Panel Bên Phải (Chi tiết Hóa Đơn Trả) ---
        JPanel pn_trathuoc_hd = new JPanel();
        pn_trathuoc_hd.setLayout(null);
        pn_trathuoc_hd.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
        pn_trathuoc_hd.setBounds(701, 80, 988, 910); // Điều chỉnh
        pn_TraThuoc.add(pn_trathuoc_hd);

        // Panel chứa thông tin hóa đơn trả
        JPanel pn_trathuoc_Hoadonbanle = new JPanel();
        pn_trathuoc_Hoadonbanle.setLayout(null);
        pn_trathuoc_Hoadonbanle.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Hóa Đơn Trả", // Đổi tiêu đề
            TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_trathuoc_Hoadonbanle.setBackground(COLOR_CARD_BACKGROUND);
        pn_trathuoc_Hoadonbanle.setBounds(10, 0, 968, 830); // Điều chỉnh kích thước
        pn_trathuoc_hd.add(pn_trathuoc_Hoadonbanle);

        JLabel lbl_trathuoc_Hoadonbanle = new JLabel("HÓA ĐƠN TRẢ THUỐC"); // Font, màu
        lbl_trathuoc_Hoadonbanle.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Hoadonbanle.setFont(FONT_TITLE_SECTION); // Font section
        lbl_trathuoc_Hoadonbanle.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa
        lbl_trathuoc_Hoadonbanle.setBounds(10, 20, 948, 40); // Vị trí
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Hoadonbanle);

        // Các label hiển thị thông tin hóa đơn gốc (font, màu)
        JLabel lbl_trathuoc_Ngaylaphoadon = new JLabel("Ngày Lập Gốc:");
        lbl_trathuoc_Ngaylaphoadon.setForeground(COLOR_TEXT_MUTED);
        lbl_trathuoc_Ngaylaphoadon.setFont(FONT_DETAIL_ITALIC);
        lbl_trathuoc_Ngaylaphoadon.setBounds(680, 60, 120, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Ngaylaphoadon);

        lbl_trathuoc_hienngaylaphoadon = new JLabel(""); // Sẽ load từ code
        lbl_trathuoc_hienngaylaphoadon.setForeground(COLOR_DANGER_RED);
        lbl_trathuoc_hienngaylaphoadon.setFont(FONT_DETAIL_ITALIC);
        lbl_trathuoc_hienngaylaphoadon.setBounds(810, 60, 140, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_hienngaylaphoadon);

        JLabel lbl_trathuoc_Mahd = new JLabel("Mã Hóa Đơn:"); // Font
        lbl_trathuoc_Mahd.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Mahd.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Mahd.setBounds(40, 100, 120, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Mahd);

        lbl_trathuoc_hienmahd = new JLabel(""); // Font, màu
        lbl_trathuoc_hienmahd.setForeground(COLOR_PRIMARY_BLUE);
        lbl_trathuoc_hienmahd.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_hienmahd.setBounds(170, 100, 300, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_hienmahd);

        JLabel lbl_trathuoc_Nhanvien = new JLabel("Nhân Viên Lập:"); // Font
        lbl_trathuoc_Nhanvien.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Nhanvien.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Nhanvien.setBounds(40, 140, 120, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Nhanvien);

        lbl_trathuoc_Hientennv = new JLabel(""); // Font, màu
        lbl_trathuoc_Hientennv.setForeground(COLOR_PRIMARY_BLUE);
        lbl_trathuoc_Hientennv.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Hientennv.setBounds(170, 140, 300, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Hientennv);

        JLabel lbl_trathuoc_SodienthoaiKH = new JLabel("SĐT Khách Hàng:"); // Font
        lbl_trathuoc_SodienthoaiKH.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_SodienthoaiKH.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_SodienthoaiKH.setBounds(500, 100, 140, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_SodienthoaiKH);

        lbl_trathuoc_hiensdtkh = new JLabel(""); // Font, màu
        lbl_trathuoc_hiensdtkh.setForeground(COLOR_PRIMARY_BLUE);
        lbl_trathuoc_hiensdtkh.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_hiensdtkh.setBounds(650, 100, 300, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_hiensdtkh);

        JLabel lbl_trathuoc_Khachhang = new JLabel("Khách Hàng:"); // Font
        lbl_trathuoc_Khachhang.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Khachhang.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Khachhang.setBounds(500, 140, 140, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Khachhang);

        lbl_trathuoc_Hientenkh = new JLabel(""); // Font, màu
        lbl_trathuoc_Hientenkh.setForeground(COLOR_PRIMARY_BLUE);
        lbl_trathuoc_Hientenkh.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Hientenkh.setBounds(650, 140, 300, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Hientenkh);

        // Bảng danh sách thuốc TRẢ
        JScrollPane scP_trathuoc_dsthuoc = new JScrollPane();
        scP_trathuoc_dsthuoc.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_trathuoc_dsthuoc.setBounds(10, 190, 948, 380); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(scP_trathuoc_dsthuoc);

        table_trathuoc_thuoc = new JTable() { // Thêm style zebra
             @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_trathuoc_thuoc); // Style chung
        table_trathuoc_thuoc.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Mã thuốc", "Tên Thuốc", "Đơn giá", "Số lượng Trả", "Đơn vị tính", "Thành tiền" // Đổi tên cột SL
            }
        ));
        scP_trathuoc_dsthuoc.setViewportView(table_trathuoc_thuoc);

        // Phần tổng kết và nhập liệu
        JLabel lbl_trathuoc_tongtienthuoc = new JLabel("Tổng Tiền Thuốc Gốc:"); // Font
        lbl_trathuoc_tongtienthuoc.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_tongtienthuoc.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_tongtienthuoc.setBounds(40, 590, 160, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_tongtienthuoc);

        lbl_trathuoc_Hientienthuoc = new JLabel(""); // Font, màu
        lbl_trathuoc_Hientienthuoc.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Hientienthuoc.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Hientienthuoc.setBounds(210, 590, 250, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Hientienthuoc);

        JLabel lbl_trathuoc_Thue = new JLabel("Thuế VAT Gốc:"); // Font
        lbl_trathuoc_Thue.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Thue.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Thue.setBounds(500, 590, 140, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Thue);

        lbl_trathuoc_Hienthue = new JLabel(""); // Font, màu
        lbl_trathuoc_Hienthue.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_Hienthue.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_Hienthue.setBounds(650, 590, 300, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Hienthue);

        JLabel lbl_trathuoc_KMapdung = new JLabel("Khuyến Mãi Gốc:"); // Font
        lbl_trathuoc_KMapdung.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_KMapdung.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_KMapdung.setBounds(40, 630, 160, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_KMapdung);

        lbl_trathuoc_HienKmapdung = new JLabel(""); // Font, màu
        lbl_trathuoc_HienKmapdung.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_HienKmapdung.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_HienKmapdung.setBounds(210, 630, 250, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_HienKmapdung);

        JLabel lbl_trathuoc_Tongcong = new JLabel("Tổng Cộng Gốc:"); // Font, màu
        lbl_trathuoc_Tongcong.setFont(FONT_LABEL_BOLD); // Giữ font nhỏ hơn
        lbl_trathuoc_Tongcong.setForeground(COLOR_TEXT_DARK); // Màu chữ thường
        lbl_trathuoc_Tongcong.setBounds(500, 630, 140, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Tongcong);

        lbl_trathuoc_Hientongcong = new JLabel(""); // Font, màu
        lbl_trathuoc_Hientongcong.setForeground(COLOR_TEXT_DARK); // Màu chữ thường
        lbl_trathuoc_Hientongcong.setFont(FONT_LABEL_BOLD); // Font nhỏ
        lbl_trathuoc_Hientongcong.setBounds(650, 630, 300, 25); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_Hientongcong);

        // --- Thông tin trả ---
        JLabel lbl_trathuoc_sldoi = new JLabel("Số Lượng Trả:"); // Font
        lbl_trathuoc_sldoi.setFont(FONT_LABEL_BOLD);
        lbl_trathuoc_sldoi.setForeground(COLOR_TEXT_DARK);
        lbl_trathuoc_sldoi.setBounds(40, 680, 160, 30); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_sldoi);

        txt_trathuoc_sldoi = new JTextField(); // Font
        txt_trathuoc_sldoi.setFont(FONT_TEXT_FIELD);
        txt_trathuoc_sldoi.setColumns(10);
        txt_trathuoc_sldoi.setBounds(210, 680, 250, 30); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(txt_trathuoc_sldoi);

        JLabel lbl_lydo = new JLabel("Lý Do Trả:"); // Đổi tên biến label
        lbl_lydo.setFont(FONT_LABEL_BOLD);
        lbl_lydo.setForeground(COLOR_TEXT_DARK);
        lbl_lydo.setBounds(500, 680, 140, 30); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_lydo);

        txt_trathuoc_lydo = new JTextField(); // Font
        txt_trathuoc_lydo.setFont(FONT_TEXT_FIELD);
        txt_trathuoc_lydo.setColumns(10);
        txt_trathuoc_lydo.setBounds(650, 680, 300, 30); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(txt_trathuoc_lydo);

        // --- Tiền hoàn ---
        JLabel lbl_trathuoc_tienduochoan = new JLabel("Tiền Hoàn Lại:"); // Font, màu
        lbl_trathuoc_tienduochoan.setFont(FONT_SUMMARY_TOTAL); // Font to
        lbl_trathuoc_tienduochoan.setForeground(COLOR_SUCCESS_GREEN); // Màu xanh
        lbl_trathuoc_tienduochoan.setBounds(280, 750, 200, 40); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_tienduochoan);

        lbl_trathuoc_tiendchoan = new JLabel("0 VND"); // Font, màu
        lbl_trathuoc_tiendchoan.setForeground(COLOR_SUCCESS_GREEN);
        lbl_trathuoc_tiendchoan.setFont(FONT_SUMMARY_TOTAL); // Font to
        lbl_trathuoc_tiendchoan.setBounds(490, 750, 300, 40); // Điều chỉnh
        pn_trathuoc_Hoadonbanle.add(lbl_trathuoc_tiendchoan);

        // --- Các nút chức năng dưới Hóa Đơn Trả ---
        btn_trathuoc_LM = new JButton("Làm Mới Thuốc Trả"); // Style, vị trí
        styleButton(btn_trathuoc_LM, COLOR_TEXT_MUTED);
        btn_trathuoc_LM.setFont(FONT_BUTTON_STANDARD);
        btn_trathuoc_LM.setBounds(10, 850, 180, 40); // Điều chỉnh
        pn_trathuoc_hd.add(btn_trathuoc_LM);

        btn_trathuoc_XN = new JButton("Xác Nhận Thuốc Trả"); // Style, vị trí
        styleButton(btn_trathuoc_XN, COLOR_PRIMARY_BLUE);
        btn_trathuoc_XN.setFont(FONT_BUTTON_STANDARD);
        btn_trathuoc_XN.setBounds(220, 850, 200, 40); // Điều chỉnh
        pn_trathuoc_hd.add(btn_trathuoc_XN);

        btn_trathuoc_luu = new JButton("Lưu Hóa Đơn Trả"); // Style, vị trí
        styleButton(btn_trathuoc_luu, COLOR_SUCCESS_GREEN);
        btn_trathuoc_luu.setFont(FONT_BUTTON_STANDARD);
        btn_trathuoc_luu.setBounds(450, 850, 180, 40); // Điều chỉnh
        pn_trathuoc_hd.add(btn_trathuoc_luu);

        btn_trathuoc_xuathd = new JButton("Xuất Hóa Đơn Trả"); // Style, vị trí
        styleButton(btn_trathuoc_xuathd, COLOR_SUCCESS_GREEN);
        btn_trathuoc_xuathd.setFont(FONT_BUTTON_STANDARD);
        btn_trathuoc_xuathd.setBounds(660, 850, 200, 40); // Điều chỉnh
        pn_trathuoc_hd.add(btn_trathuoc_xuathd);

        // ===== KẾT THÚC KHỐI CODE TRẢ THUỐC =====


        // ===== BẮT ĐẦU KHỐI CODE ĐỔI THUỐC ĐÃ LÀM LẠI GIAO DIỆN =====
        JPanel pn_Doithuoc = new JPanel();
        maincontent.add(pn_Doithuoc, "doithuoc");
        pn_Doithuoc.setLayout(null);
        pn_Doithuoc.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính

        // --- Panel Tiêu đề ---
        JPanel panel_doithuoc_title = new JPanel();
        panel_doithuoc_title.setLayout(null);
        panel_doithuoc_title.setBackground(COLOR_PRIMARY_BLUE); // Màu xanh dương chủ đạo
        panel_doithuoc_title.setBounds(0, 0, 1699, 67);
        pn_Doithuoc.add(panel_doithuoc_title);

        JLabel lbl_doithuoc_title = new JLabel("ĐỔI THUỐC");
        lbl_doithuoc_title.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_doithuoc_title.setForeground(Color.WHITE);
        lbl_doithuoc_title.setFont(FONT_TITLE_MAIN); // Font tiêu đề chính
        lbl_doithuoc_title.setBounds(0, 11, 1699, 45);
        panel_doithuoc_title.add(lbl_doithuoc_title);

        // --- Panel Bên Trái (Tìm kiếm Hóa Đơn Gốc) ---
        // (Sử dụng lại cấu trúc và tên biến tương tự phần Trả Thuốc)
        JPanel pn_doithuoc_tkhd_1 = new JPanel(); // Đổi tên biến nếu cần rõ nghĩa hơn
        pn_doithuoc_tkhd_1.setBackground(COLOR_BACKGROUND_PRIMARY);
        pn_doithuoc_tkhd_1.setBounds(10, 80, 681, 910);
        pn_Doithuoc.add(pn_doithuoc_tkhd_1);
        pn_doithuoc_tkhd_1.setLayout(null);

        // Panel Nhập liệu tìm kiếm
        JPanel pn_doithuoc_tkhd_nhap = new JPanel();
        pn_doithuoc_tkhd_nhap.setBounds(0, 0, 671, 279); // Tăng chiều cao nếu cần
        pn_doithuoc_tkhd_nhap.setLayout(null);
        pn_doithuoc_tkhd_nhap.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Tìm Kiếm Hóa Đơn Cần Đổi", // Tiêu đề rõ hơn
            TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_doithuoc_tkhd_nhap.setBackground(COLOR_CARD_BACKGROUND);
        pn_doithuoc_tkhd_1.add(pn_doithuoc_tkhd_nhap);

        JLabel lbl_doithuoc_tkhd_mahd = new JLabel("Mã Hóa Đơn:");
        lbl_doithuoc_tkhd_mahd.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_tkhd_mahd.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_tkhd_mahd.setBounds(30, 40, 140, 30);
        pn_doithuoc_tkhd_nhap.add(lbl_doithuoc_tkhd_mahd);

        txt_doithuoc_tkhd_mahd = new JTextField();
        txt_doithuoc_tkhd_mahd.setFont(FONT_TEXT_FIELD);
        txt_doithuoc_tkhd_mahd.setColumns(10);
        txt_doithuoc_tkhd_mahd.setBounds(180, 40, 460, 30);
        pn_doithuoc_tkhd_nhap.add(txt_doithuoc_tkhd_mahd);

        JLabel lbl_doithuoc_tkhd_tenKH = new JLabel("Tên Khách Hàng:");
        lbl_doithuoc_tkhd_tenKH.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_tkhd_tenKH.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_tkhd_tenKH.setBounds(30, 90, 140, 30);
        pn_doithuoc_tkhd_nhap.add(lbl_doithuoc_tkhd_tenKH);

        txt_doithuoc_tkhd_tenKh = new JTextField(); // Giữ tên biến cũ
        txt_doithuoc_tkhd_tenKh.setFont(FONT_TEXT_FIELD);
        txt_doithuoc_tkhd_tenKh.setColumns(10);
        txt_doithuoc_tkhd_tenKh.setBounds(180, 90, 460, 30);
        pn_doithuoc_tkhd_nhap.add(txt_doithuoc_tkhd_tenKh);

        JLabel lbl_doithuoc_tkhd_ngayLap = new JLabel("Ngày Lập:");
        lbl_doithuoc_tkhd_ngayLap.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_tkhd_ngayLap.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_tkhd_ngayLap.setBounds(30, 140, 140, 30);
        pn_doithuoc_tkhd_nhap.add(lbl_doithuoc_tkhd_ngayLap);

        dateNgayLapHD_TKHD_doithuoc = new JDateChooser();
        dateNgayLapHD_TKHD_doithuoc.setFont(FONT_TEXT_FIELD);
        dateNgayLapHD_TKHD_doithuoc.setDateFormatString("dd/MM/yyyy");
        dateNgayLapHD_TKHD_doithuoc.setBounds(180, 140, 460, 30);
        pn_doithuoc_tkhd_nhap.add(dateNgayLapHD_TKHD_doithuoc);

        btn_doithuoc_lammoi = new JButton("Làm Mới"); // Style, vị trí
        styleButton(btn_doithuoc_lammoi, COLOR_TEXT_MUTED);
        btn_doithuoc_lammoi.setFont(FONT_BUTTON_STANDARD);
        btn_doithuoc_lammoi.setBounds(270, 190, 136, 40); // Điều chỉnh
        pn_doithuoc_tkhd_nhap.add(btn_doithuoc_lammoi);

        // Bảng kết quả tìm kiếm hóa đơn gốc
        JScrollPane scP_doithuoc_tkhd = new JScrollPane();
        scP_doithuoc_tkhd.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_doithuoc_tkhd.setBounds(0, 290, 671, 610); // Điều chỉnh
        pn_doithuoc_tkhd_1.add(scP_doithuoc_tkhd);

        table_doithuoc_hd = new JTable() { // Thêm style zebra
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_doithuoc_hd); // Style chung
        table_doithuoc_hd.setModel(new DefaultTableModel(
            new Object[][] {},
             new String[] {
                "Mã HD", "Tên KH", "SĐT KH", "Nhân Viên", "Ngày Lập", "KM", "Tổng Tiền"
            }
        ));
        scP_doithuoc_tkhd.setViewportView(table_doithuoc_hd);


        // --- Panel Bên Phải (Chi tiết Hóa Đơn Đổi) ---
        JPanel pn_doithuoc_hd = new JPanel();
        pn_doithuoc_hd.setLayout(null);
        pn_doithuoc_hd.setBackground(COLOR_BACKGROUND_PRIMARY); // Nền chính
        pn_doithuoc_hd.setBounds(701, 80, 988, 910); // Điều chỉnh
        pn_Doithuoc.add(pn_doithuoc_hd);

        // Panel chứa thông tin hóa đơn đổi
        JPanel pn_doithuoc_Hoadonbanle = new JPanel();
        pn_doithuoc_Hoadonbanle.setLayout(null);
        pn_doithuoc_Hoadonbanle.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COLOR_BORDER_LIGHT, 1, true),
            "Thông Tin Hóa Đơn Đổi", // Đổi tiêu đề
            TitledBorder.LEADING, TitledBorder.TOP,
            FONT_LABEL_BOLD, COLOR_PRIMARY_BLUE
        ));
        pn_doithuoc_Hoadonbanle.setBackground(COLOR_CARD_BACKGROUND);
        pn_doithuoc_Hoadonbanle.setBounds(10, 0, 968, 830); // Điều chỉnh kích thước
        pn_doithuoc_hd.add(pn_doithuoc_Hoadonbanle);

        JLabel lbl_doithuoc_Hoadonbanle = new JLabel("HÓA ĐƠN ĐỔI THUỐC"); // Font, màu
        lbl_doithuoc_Hoadonbanle.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Hoadonbanle.setFont(FONT_TITLE_SECTION);
        lbl_doithuoc_Hoadonbanle.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_doithuoc_Hoadonbanle.setBounds(10, 20, 948, 40);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Hoadonbanle);

        // Các label hiển thị thông tin hóa đơn gốc (tương tự Trả thuốc)
        JLabel lbl_doithuoc_Ngaylaphoadon = new JLabel("Ngày Lập Gốc:");
        lbl_doithuoc_Ngaylaphoadon.setForeground(COLOR_TEXT_MUTED);
        lbl_doithuoc_Ngaylaphoadon.setFont(FONT_DETAIL_ITALIC);
        lbl_doithuoc_Ngaylaphoadon.setBounds(680, 60, 120, 25);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Ngaylaphoadon);

        lbl_doithuoc_hienngaylaphoadon = new JLabel("");
        lbl_doithuoc_hienngaylaphoadon.setForeground(COLOR_DANGER_RED);
        lbl_doithuoc_hienngaylaphoadon.setFont(FONT_DETAIL_ITALIC);
        lbl_doithuoc_hienngaylaphoadon.setBounds(810, 60, 140, 25);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_hienngaylaphoadon);

        JLabel lbl_doithuoc_Mahd = new JLabel("Mã Hóa Đơn:");
        lbl_doithuoc_Mahd.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Mahd.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Mahd.setBounds(40, 100, 120, 25);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Mahd);

        lbl_doithuoc_hienmahd = new JLabel("");
        lbl_doithuoc_hienmahd.setForeground(COLOR_PRIMARY_BLUE);
        lbl_doithuoc_hienmahd.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_hienmahd.setBounds(170, 100, 300, 25);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_hienmahd);

        // ... (Tương tự cho các label Nhân viên, SĐT KH, Khách hàng) ...
         JLabel lbl_doithuoc_Nhanvien = new JLabel("Nhân Viên Lập:"); // Font
        lbl_doithuoc_Nhanvien.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Nhanvien.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Nhanvien.setBounds(40, 140, 120, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Nhanvien);

        lbl_doithuoc_Hientennv = new JLabel(""); // Font, màu
        lbl_doithuoc_Hientennv.setForeground(COLOR_PRIMARY_BLUE);
        lbl_doithuoc_Hientennv.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Hientennv.setBounds(170, 140, 300, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Hientennv);

        JLabel lbl_doithuoc_SodienthoaiKH = new JLabel("SĐT Khách Hàng:"); // Font
        lbl_doithuoc_SodienthoaiKH.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_SodienthoaiKH.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_SodienthoaiKH.setBounds(500, 100, 140, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_SodienthoaiKH);

        lbl_doithuoc_hiensdtkh = new JLabel(""); // Font, màu
        lbl_doithuoc_hiensdtkh.setForeground(COLOR_PRIMARY_BLUE);
        lbl_doithuoc_hiensdtkh.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_hiensdtkh.setBounds(650, 100, 300, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_hiensdtkh);

        JLabel lbl_doithuoc_Khachhang = new JLabel("Khách Hàng:"); // Font
        lbl_doithuoc_Khachhang.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Khachhang.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Khachhang.setBounds(500, 140, 140, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Khachhang);

        lbl_doithuoc_Hientenkh = new JLabel(""); // Font, màu
        lbl_doithuoc_Hientenkh.setForeground(COLOR_PRIMARY_BLUE);
        lbl_doithuoc_Hientenkh.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Hientenkh.setBounds(650, 140, 300, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Hientenkh);


        // Bảng danh sách thuốc ĐỔI
        JScrollPane scP_doithuoc_dsthuoc = new JScrollPane();
        scP_doithuoc_dsthuoc.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
        scP_doithuoc_dsthuoc.setBounds(10, 190, 948, 380);
        pn_doithuoc_Hoadonbanle.add(scP_doithuoc_dsthuoc);

        table_doithuoc_thuoc = new JTable() { // Thêm style zebra
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD_BACKGROUND : COLOR_BACKGROUND_PRIMARY);
                } else {
                    c.setBackground(COLOR_PRIMARY_BLUE);
                }
                return c;
            }
        };
        applyCommonTableStyling(table_doithuoc_thuoc); // Style chung
        table_doithuoc_thuoc.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                 "Mã thuốc", "Tên Thuốc", "Đơn giá", "Số lượng Đổi", "Đơn vị tính", "Thành tiền" // Đổi tên cột SL
            }
        ));
        scP_doithuoc_dsthuoc.setViewportView(table_doithuoc_thuoc);

        // Phần tổng kết và nhập liệu (tương tự Trả thuốc)
        JLabel lbl_doithuoc_tongtienthuoc = new JLabel("Tổng Tiền Thuốc Gốc:");
        lbl_doithuoc_tongtienthuoc.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_tongtienthuoc.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_tongtienthuoc.setBounds(40, 590, 160, 25);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_tongtienthuoc);

        lbl_doithuoc_Hientienthuoc = new JLabel("");
        lbl_doithuoc_Hientienthuoc.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Hientienthuoc.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Hientienthuoc.setBounds(210, 590, 250, 25);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Hientienthuoc);

        // ... (Tương tự cho các label Thuế, KM, Tổng Cộng Gốc) ...
         JLabel lbl_doithuoc_Thue = new JLabel("Thuế VAT Gốc:"); // Font
        lbl_doithuoc_Thue.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Thue.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Thue.setBounds(500, 590, 140, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Thue);

        lbl_doithuoc_Hienthue = new JLabel(""); // Font, màu
        lbl_doithuoc_Hienthue.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_Hienthue.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_Hienthue.setBounds(650, 590, 300, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Hienthue);

        JLabel lbl_doithuoc_KMapdung = new JLabel("Khuyến Mãi Gốc:"); // Font
        lbl_doithuoc_KMapdung.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_KMapdung.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_KMapdung.setBounds(40, 630, 160, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_KMapdung);

        lbl_doithuoc_HienKmapdung = new JLabel(""); // Font, màu
        lbl_doithuoc_HienKmapdung.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_HienKmapdung.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_HienKmapdung.setBounds(210, 630, 250, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_HienKmapdung);

        JLabel lbl_doithuoc_Tongcong = new JLabel("Tổng Cộng Gốc:"); // Font, màu
        lbl_doithuoc_Tongcong.setFont(FONT_LABEL_BOLD); // Giữ font nhỏ hơn
        lbl_doithuoc_Tongcong.setForeground(COLOR_TEXT_DARK); // Màu chữ thường
        lbl_doithuoc_Tongcong.setBounds(500, 630, 140, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Tongcong);

        lbl_doithuoc_Hientongcong = new JLabel(""); // Font, màu
        lbl_doithuoc_Hientongcong.setForeground(COLOR_TEXT_DARK); // Màu chữ thường
        lbl_doithuoc_Hientongcong.setFont(FONT_LABEL_BOLD); // Font nhỏ
        lbl_doithuoc_Hientongcong.setBounds(650, 630, 300, 25); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_Hientongcong);


        // --- Thông tin đổi ---
        JLabel lbl_doithuoc_sldoi = new JLabel("Số Lượng Đổi:");
        lbl_doithuoc_sldoi.setFont(FONT_LABEL_BOLD);
        lbl_doithuoc_sldoi.setForeground(COLOR_TEXT_DARK);
        lbl_doithuoc_sldoi.setBounds(40, 680, 160, 30);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_sldoi);

        txt_doithuoc_sldoi = new JTextField();
        txt_doithuoc_sldoi.setFont(FONT_TEXT_FIELD);
        txt_doithuoc_sldoi.setColumns(10);
        txt_doithuoc_sldoi.setBounds(210, 680, 250, 30);
        pn_doithuoc_Hoadonbanle.add(txt_doithuoc_sldoi);

        JLabel lbl_lydo_doi = new JLabel("Lý Do Đổi:"); // Đổi tên biến label
        lbl_lydo_doi.setFont(FONT_LABEL_BOLD);
        lbl_lydo_doi.setForeground(COLOR_TEXT_DARK);
        lbl_lydo_doi.setBounds(500, 680, 140, 30);
        pn_doithuoc_Hoadonbanle.add(lbl_lydo_doi);

        txt_doithuoc_lydo = new JTextField(); // Giữ tên biến txt
        txt_doithuoc_lydo.setFont(FONT_TEXT_FIELD);
        txt_doithuoc_lydo.setColumns(10);
        txt_doithuoc_lydo.setBounds(650, 680, 300, 30);
        pn_doithuoc_Hoadonbanle.add(txt_doithuoc_lydo);

        // --- Tiền hoàn (có thể = 0 nếu đổi ngang giá) ---
        JLabel lbl_doithuoc_tienduochoan = new JLabel("Tiền Hoàn/Phụ Thu:"); // Đổi text
        lbl_doithuoc_tienduochoan.setFont(FONT_SUMMARY_TOTAL);
        lbl_doithuoc_tienduochoan.setForeground(COLOR_SUCCESS_GREEN);
        lbl_doithuoc_tienduochoan.setBounds(200, 750, 280, 40); // Điều chỉnh
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_tienduochoan);

        lbl_doithuoc_tiendchoan = new JLabel("0 VND");
        lbl_doithuoc_tiendchoan.setForeground(COLOR_SUCCESS_GREEN); // Giữ màu xanh
        lbl_doithuoc_tiendchoan.setFont(FONT_SUMMARY_TOTAL);
        lbl_doithuoc_tiendchoan.setBounds(490, 750, 300, 40);
        pn_doithuoc_Hoadonbanle.add(lbl_doithuoc_tiendchoan);


        // --- Các nút chức năng dưới Hóa Đơn Đổi ---
        btn_doithuoc_LM = new JButton("Làm Mới Thuốc Đổi"); // Style, vị trí
        styleButton(btn_doithuoc_LM, COLOR_TEXT_MUTED);
        btn_doithuoc_LM.setFont(FONT_BUTTON_STANDARD);
        btn_doithuoc_LM.setBounds(10, 850, 180, 40);
        pn_doithuoc_hd.add(btn_doithuoc_LM);

        btn_doithuoc_XN = new JButton("Xác Nhận Thuốc Đổi"); // Style, vị trí
        styleButton(btn_doithuoc_XN, COLOR_PRIMARY_BLUE);
        btn_doithuoc_XN.setFont(FONT_BUTTON_STANDARD);
        btn_doithuoc_XN.setBounds(220, 850, 200, 40);
        pn_doithuoc_hd.add(btn_doithuoc_XN);

        btn_doithuoc_luu = new JButton("Lưu Hóa Đơn Đổi"); // Style, vị trí
        styleButton(btn_doithuoc_luu, COLOR_SUCCESS_GREEN);
        btn_doithuoc_luu.setFont(FONT_BUTTON_STANDARD);
        btn_doithuoc_luu.setBounds(450, 850, 180, 40);
        pn_doithuoc_hd.add(btn_doithuoc_luu);

        btn_doithuoc_xuathd = new JButton("Xuất Hóa Đơn Đổi"); // Style, vị trí, đổi text
        styleButton(btn_doithuoc_xuathd, COLOR_SUCCESS_GREEN);
        btn_doithuoc_xuathd.setFont(FONT_BUTTON_STANDARD);
        btn_doithuoc_xuathd.setBounds(660, 850, 200, 40);
        pn_doithuoc_hd.add(btn_doithuoc_xuathd);

        // ===== KẾT THÚC KHỐI CODE ĐỔI THUỐC =====
        
        new KhachHang_Controller(this);
        new NhaCungCap_Controller(this);
        hoaDonController = new HoaDon_Controller(this);
        doiTraController = new DoiTra_Controller(this);
        hoaDonController.setDoiTraController(doiTraController);
        new PhieuDatHang_Controller(this);
        new KhuyenMai_Controller(this);
        QuanLyHieuThuocTay.setVisible(true);
        
    }
    
    // Phương thức hỗ trợ tạo nút trong thanh sidebar
    private JButton createSidebarButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(FONT_LABEL_BOLD.deriveFont(16f));
        button.setForeground(COLOR_SIDEBAR_TEXT);
        button.setBackground(COLOR_SIDEBAR_BG_END);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setOpaque(false); // QUAN TRỌNG
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height + 10));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

//        ImageIcon icon = loadIcon(iconPath);
//        if (icon != null) {
//            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//            button.setIcon(new ImageIcon(img));
//            button.setIconTextGap(15);
//        }
        return button;
    }

 // Phương thức hỗ trợ thêm hiệu ứng hover cho nút sidebar (CHỈ HOVER)
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != selectedMainMenuButton) { // Chỉ hover nếu chưa selected
                    button.setOpaque(true);
                    button.setBackground(COLOR_SIDEBAR_BUTTON_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != selectedMainMenuButton) { // Chỉ reset nếu chưa selected
                    button.setOpaque(false);
                    button.setBackground(COLOR_SIDEBAR_BG_END); // Màu nền cũ không quan trọng
                }
            }
        });
    }
 // === CÁC HÀM TẠO SUBMENU PANEL (DẠNG ACCORDION - ĐÃ SỬA LỖI NULL) ===

    // Submenu cho Hệ Thống (JPanel)
    private JPanel createSystemSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnTrangChu;
         if (Beans.isDesignTime()) {
             btnTrangChu = new JButton("Trang Chủ (Design)");
         } else {
             btnTrangChu = createSubmenuButton("Trang Chủ");
             btnTrangChu.addActionListener(e -> {
                // SỬA LỖI: Dùng TrangChu_GUI.this.maincontent
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "trangChu");
            });
         }
        submenuPanel.add(btnTrangChu);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        
        JButton btnTaikhoan;
        if (Beans.isDesignTime()) {
            btnTaikhoan = new JButton("Tài Khoản (Design)");
        } else {
            btnTaikhoan = createSubmenuButton("Tài Khoản");
         // --- SỬA LẠI SỰ KIỆN NÚT TÀI KHOẢN (FIX LỖI NULL POINTER) ---
            btnTaikhoan.addActionListener(e -> {
                if (currentUser != null && "Quản lý".equalsIgnoreCase(currentUser.getQuyenHan())) {
                    try {
                        // 1. Lấy model của bảng
                        DefaultTableModel model = (DefaultTableModel) tableQLTK.getModel();
                        model.setRowCount(0); // Xóa dữ liệu cũ

                        // 2. Gọi DAO để lấy danh sách mới nhất
                        java.util.List<entity.TaiKhoan> list = (java.util.List<entity.TaiKhoan>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_TAI_KHOAN, null)).getData();

                        // 3. Đổ dữ liệu vào bảng (Đảm bảo đủ 4 cột)
                        for (entity.TaiKhoan tk : list) {
                             String trangThaiText = tk.isTrangThai() ? "Hoạt động" : "Ngừng hoạt động";
                             
                             model.addRow(new Object[]{
                                tk.getMaTK(),
                                tk.getTenTK(),
                                tk.getQuyenHan() == null ? "(Chưa cấp)" : tk.getQuyenHan(),
                                trangThaiText // <--- Cột thứ 4 quan trọng
                            });
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(TrangChu_GUI.this.QuanLyHieuThuocTay,
                         "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }

                    // 4. Chuyển màn hình sang tab Tài khoản
                    CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                    cl.show(TrangChu_GUI.this.maincontent, "quanLyTaiKhoan");

                } else {
                    JOptionPane.showMessageDialog(TrangChu_GUI.this.QuanLyHieuThuocTay,
                     "Chỉ có Quản lý mới được truy cập chức năng này!", "Truy cập bị từ chối", JOptionPane.WARNING_MESSAGE);
                }
            });
        }
        submenuPanel.add(btnTaikhoan);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTrogiup;
        if (Beans.isDesignTime()) {
            btnTrogiup = new JButton("Trợ Giúp (Design)");
        } else {
            btnTrogiup = createSubmenuButton("Trợ Giúp");
            // btnTrogiup.addActionListener(e -> { /* Code xử lý */ });
        }
        submenuPanel.add(btnTrogiup);

        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }

    // Submenu cho Nhân Viên (JPanel)
    private JPanel createEmployeeSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnThemnv;
        if (Beans.isDesignTime()) {
            btnThemnv = new JButton("Thêm (Design)");
        } else {
            btnThemnv = createSubmenuButton("Thêm");
            btnThemnv.addActionListener(e -> {
                // SỬA LỖI: Dùng TrangChu_GUI.this.maincontent
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "themNV");
            });
        }
        submenuPanel.add(btnThemnv);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnCapnhatnv;
        if (Beans.isDesignTime()) {
            btnCapnhatnv = new JButton("Cập Nhật (Design)");
        } else {
            btnCapnhatnv = createSubmenuButton("Cập Nhật");
            btnCapnhatnv.addActionListener(e -> {
                // SỬA LỖI: Dùng TrangChu_GUI.this.maincontent
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "capnhatnv");
            });
        }
        submenuPanel.add(btnCapnhatnv);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTimkiemnv;
        if (Beans.isDesignTime()) {
            btnTimkiemnv = new JButton("Tìm Kiếm (Design)");
        } else {
            btnTimkiemnv = createSubmenuButton("Tìm Kiếm");
            btnTimkiemnv.addActionListener(e -> {
                // SỬA LỖI: Dùng TrangChu_GUI.this.maincontent
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "timkiemnv");
            });
        }
        submenuPanel.add(btnTimkiemnv);

        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }

    // Submenu cho Khách Hàng (JPanel)
    private JPanel createCustomerSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnThemkh;
        if (Beans.isDesignTime()) {
            btnThemkh = new JButton("Thêm (Design)");
        } else {
            btnThemkh = createSubmenuButton("Thêm");
            btnThemkh.addActionListener(e -> {
                ThemKH_GUI themKhDialog = new ThemKH_GUI(QuanLyHieuThuocTay); // Hoặc (this)
                new ThemKH_Controller(themKhDialog, this);
                themKhDialog.setVisible(true);
            });
        }
        submenuPanel.add(btnThemkh);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnCapnhatkh;
        if (Beans.isDesignTime()) {
            btnCapnhatkh = new JButton("Cập Nhật (Design)");
        } else {
            btnCapnhatkh = createSubmenuButton("Cập Nhật");
            btnCapnhatkh.addActionListener(e -> {
                // SỬA LỖI: Dùng TrangChu_GUI.this.maincontent
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "capNhatKH");
            });
        }
        submenuPanel.add(btnCapnhatkh);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTimkiemkh;
        if (Beans.isDesignTime()) {
            btnTimkiemkh = new JButton("Tìm Kiếm (Design)");
        } else {
            btnTimkiemkh = createSubmenuButton("Tìm Kiếm");
            btnTimkiemkh.addActionListener(e -> {
                // SỬA LỖI: Dùng TrangChu_GUI.this.maincontent
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "timkiemkh");
            });
        }
        submenuPanel.add(btnTimkiemkh);

        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }

    // Submenu cho Thuốc (JPanel)
    private JPanel createMedicineSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnThemthuocthuong;
        if(Beans.isDesignTime()){ btnThemthuocthuong = new JButton("Thêm (Design)"); }
        else {
            btnThemthuocthuong = createSubmenuButton("Thêm");
            btnThemthuocthuong.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "Themthuocthuong");
            });
        }
        submenuPanel.add(btnThemthuocthuong);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnThemthuoctheofile;
        if(Beans.isDesignTime()){ btnThemthuoctheofile = new JButton("Thêm File (Design)"); }
        else {
            btnThemthuoctheofile = createSubmenuButton("Thêm File");
            btnThemthuoctheofile.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "Themthuocfile");
            });
        }
        submenuPanel.add(btnThemthuoctheofile);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTimkiemthuoc;
        if(Beans.isDesignTime()){ btnTimkiemthuoc = new JButton("Tìm Kiếm (Design)"); }
        else {
            btnTimkiemthuoc = createSubmenuButton("Tìm Kiếm");
            btnTimkiemthuoc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "timkiemSP");
            });
        }
        submenuPanel.add(btnTimkiemthuoc);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnCapnhatthuoc;
        if(Beans.isDesignTime()){ btnCapnhatthuoc = new JButton("Cập Nhật (Design)"); }
        else {
            btnCapnhatthuoc = createSubmenuButton("Cập Nhật");
            btnCapnhatthuoc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "Capnhatthuoc");
            });
        }
        submenuPanel.add(btnCapnhatthuoc);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        
        JButton btnTrathuoc;
        if(Beans.isDesignTime()){ btnTrathuoc = new JButton("Trả thuốc (Design)"); }
        else {
        	btnTrathuoc = createSubmenuButton("Trả thuốc");
        	btnTrathuoc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "trathuoc");
            });
        }
        submenuPanel.add(btnTrathuoc);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        
        JButton btnDoithuoc;
        if(Beans.isDesignTime()){ btnDoithuoc = new JButton("Đổi thuốc (Design)"); }
        else {
        	btnDoithuoc = createSubmenuButton("Đổi thuốc");
        	btnDoithuoc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "doithuoc");
            });
        }
        submenuPanel.add(btnDoithuoc);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnThuocSapHetHan;
        if(Beans.isDesignTime()){ btnThuocSapHetHan = new JButton("Sắp Hết Hạn (Design)"); }
        else {
            btnThuocSapHetHan = createSubmenuButton("Thuốc Sắp Hết Hạn");
            btnThuocSapHetHan.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "Thuocsaphethan");
            });
        }
        submenuPanel.add(btnThuocSapHetHan);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnThuocBanChay;
        if(Beans.isDesignTime()){ btnThuocBanChay = new JButton("Bán Chạy (Design)"); }
        else {
            btnThuocBanChay = createSubmenuButton("Thuốc Bán Chạy");
            btnThuocBanChay.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "Thuocbanchay");
            });
        }
        submenuPanel.add(btnThuocBanChay);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnThuocSapHetHang;
        if(Beans.isDesignTime()){ btnThuocSapHetHang = new JButton("Sắp Hết Hàng (Design)"); }
        else {
            btnThuocSapHetHang = createSubmenuButton("Thuốc Sắp Hết Hàng");
            btnThuocSapHetHang.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "Thuocsaphethang");
            });
        }
        submenuPanel.add(btnThuocSapHetHang);

        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }

    // Submenu cho Hóa Đơn (JPanel)
    private JPanel createInvoiceSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnThemhoadon;
        if(Beans.isDesignTime()){ btnThemhoadon = new JButton("Thêm (Design)"); }
        else {
            btnThemhoadon = createSubmenuButton("Thêm");
            btnThemhoadon.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "themHoaDon");
            });
        }
        submenuPanel.add(btnThemhoadon);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTimkiemhoadon;
        if(Beans.isDesignTime()){ btnTimkiemhoadon = new JButton("Tìm Kiếm (Design)"); }
        else {
            btnTimkiemhoadon = createSubmenuButton("Tìm Kiếm");
            btnTimkiemhoadon.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "timKiemHoaDon");
            });
        }
        submenuPanel.add(btnTimkiemhoadon);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnThongkehoadon;
        if(Beans.isDesignTime()){ btnThongkehoadon = new JButton("Thống Kê (Design)"); }
        else {
            btnThongkehoadon = createSubmenuButton("Thống Kê");
            btnThongkehoadon.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "ThongkeHD");
            });
        }
        submenuPanel.add(btnThongkehoadon);

        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }

     // Submenu cho Nhà Cung Cấp (JPanel)
    private JPanel createSupplierSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnThemncc;
        if(Beans.isDesignTime()){ btnThemncc = new JButton("Thêm (Design)"); }
        else {
            btnThemncc = createSubmenuButton("Thêm");
            btnThemncc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "themNCC");
            });
        }
        submenuPanel.add(btnThemncc);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnCapnhatncc;
        if(Beans.isDesignTime()){ btnCapnhatncc = new JButton("Cập Nhật (Design)"); }
        else {
            btnCapnhatncc = createSubmenuButton("Cập Nhật");
            btnCapnhatncc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "capNhatNCC");
            });
        }
        submenuPanel.add(btnCapnhatncc);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTimkiemncc;
        if(Beans.isDesignTime()){ btnTimkiemncc = new JButton("Tìm Kiếm (Design)"); }
        else {
            btnTimkiemncc = createSubmenuButton("Tìm Kiếm");
            btnTimkiemncc.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "timKiemNCC");
            });
        }
        submenuPanel.add(btnTimkiemncc);
        
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnQuanLyPhieuThu;
        if(Beans.isDesignTime()){ 
            btnQuanLyPhieuThu = new JButton("QL Phiếu Thu (Design)"); 
        } else {
            btnQuanLyPhieuThu = createSubmenuButton("Phiếu đặt hàng"); 
            btnQuanLyPhieuThu.addActionListener(e -> { 
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "quanLyPhieuDatHangNCC");
            });
        }
        submenuPanel.add(btnQuanLyPhieuThu);
        
        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }
    
    // Submenu cho Khuyến Mãi (JPanel)
    private JPanel createKhuyenMaiSubmenuPanel() {
        JPanel submenuPanel = new JPanel();
        submenuPanel.setLayout(new BoxLayout(submenuPanel, BoxLayout.Y_AXIS));
        submenuPanel.setBackground(COLOR_SIDEBAR_BG_END);
        submenuPanel.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 5));
        submenuPanel.setVisible(false);
        submenuPanel.setOpaque(true);
        submenuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnThemKM;
        if(Beans.isDesignTime()){ btnThemKM = new JButton("Thêm KM (Design)"); }
        else {
            btnThemKM = createSubmenuButton("Thêm Khuyến Mãi");
            btnThemKM.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "themKM");
            });
        }
        submenuPanel.add(btnThemKM);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnCapNhatKM;
        if(Beans.isDesignTime()){ btnCapNhatKM = new JButton("Cập Nhật KM (Design)"); }
        else {
            btnCapNhatKM = createSubmenuButton("Cập Nhật Khuyến Mãi");
            btnCapNhatKM.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "capNhatKM");
            });
        }
        submenuPanel.add(btnCapNhatKM);
        submenuPanel.add(Box.createRigidArea(new Dimension(0, 3)));

        JButton btnTimKiemKM;
        if(Beans.isDesignTime()){ btnTimKiemKM = new JButton("Tìm Kiếm KM (Design)"); }
        else {
            btnTimKiemKM = createSubmenuButton("Tìm Kiếm Khuyến Mãi");
            btnTimKiemKM.addActionListener(e -> {
                CardLayout cl = (CardLayout) TrangChu_GUI.this.maincontent.getLayout();
                cl.show(TrangChu_GUI.this.maincontent, "timKiemKM");
            });
        }
        submenuPanel.add(btnTimKiemKM);
        
        submenuPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, submenuPanel.getPreferredSize().height));
        return submenuPanel;
    }
    
    
    public void loadDataTableKH() {
        List<KhachHang> list = (List<KhachHang>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_KHACH_HANG, null)).getData();

        DefaultTableModel model = (DefaultTableModel) table_CapNhatKH.getModel();
        model.setRowCount(0); // Xóa bảng 1

        DefaultTableModel model_TK = (DefaultTableModel) table_tkkh.getModel();
        model_TK.setRowCount(0); // <<< SỬA Ở ĐÂY: Dùng đúng biến model_TK

        // Thêm dữ liệu vào bảng 1 (model)
        for (KhachHang kh : list) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getDiaChi(),     // Đảm bảo thứ tự đúng: Địa chỉ
                kh.getSoDienThoai() // Rồi đến SĐT
            });
        }
        // Thêm dữ liệu vào bảng 2 (model_TK)
        for (KhachHang kh : list) {
            model_TK.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getDiaChi(),     // Đảm bảo thứ tự đúng: Địa chỉ
                kh.getSoDienThoai() // Rồi đến SĐT
            });
        }
    }

 // Hàm tạo nút cho submenu con
    private JButton createSubmenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_TEXT_FIELD.deriveFont(15f)); // Font nhỏ hơn nút chính
        button.setForeground(COLOR_SIDEBAR_TEXT);
        button.setBackground(COLOR_SIDEBAR_BG_END); // Màu nền
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setOpaque(false); // Trong suốt ban đầu
        button.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // Padding
        button.setAlignmentX(Component.LEFT_ALIGNMENT); // Căn trái
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height + 5)); // Giới hạn chiều cao

        // Hiệu ứng hover đơn giản cho nút con
        button.addMouseListener(new MouseAdapter() {
             @Override
            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);
                button.setBackground(COLOR_SIDEBAR_BUTTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
                 button.setBackground(COLOR_SIDEBAR_BG_END); // Không quan trọng vì opaque = false
            }
        });

        return button;
    }
    
    public void refreshNhanVienTable() {
        if (table_CNNV != null) {
            loadDataToTableNV(table_CNNV);
        }
    }
    
    private void loadDataToTableNV(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        List<NhanVien> dsNV = (List<NhanVien>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHAN_VIEN, null)).getData();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (dsNV != null) {
            for (NhanVien nv : dsNV) {
                // Kiểm tra null cho trạng thái để tránh lỗi
                String trangThai = nv.getTrangThai() != null ? nv.getTrangThai() : "Đang làm việc";
                
                model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNgaySinh() != null ? nv.getNgaySinh().format(dtf) : "", 
                    nv.getGioiTinh(),
                    nv.getChucVu() != null ? nv.getChucVu().getTenChucVu() : "",
                    nv.getSoDienThoai(),
                    nv.getDiaChi(),
                    nv.getAnh(),
                    nv.getTaiKhoan() != null ? nv.getTaiKhoan().getTenTK() : "",
                    trangThai // <--- Đã thêm cột trạng thái vào đây
                });
            }
        }
        
        // Load danh sách tạm (khi vừa bấm Thêm nhưng chưa Lưu)
        if (tempListNV != null && !tempListNV.isEmpty()) {
            for (NhanVien nv : tempListNV) {
                String trangThai = nv.getTrangThai() != null ? nv.getTrangThai() : "Đang làm việc";
                model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNgaySinh().format(dtf),
                    nv.getGioiTinh(),
                    nv.getChucVu().getTenChucVu(),
                    nv.getSoDienThoai(),
                    nv.getDiaChi(),
                    nv.getAnh(),
                    nv.getTaiKhoan() != null ? nv.getTaiKhoan().getTenTK() : "",
                    trangThai
                });
            }
        }
    }

    
    private void locNhanVien(JTextField txtTen, JTextField txtSDT, JTextField txtTinh,
            JTextField txtHuyen, JComboBox<String> cboGioiTinh,
            JComboBox<ChucVu> cboChucVu, JComboBox<String> cboTrangThai, JTable table) { // <--- Thêm tham số

        String tenNV = txtTen.getText().trim().toLowerCase();
        String sdt = txtSDT.getText().trim().toLowerCase();
        String tinh = txtTinh.getText().trim().toLowerCase();
        String huyen = txtHuyen.getText().trim().toLowerCase();

        String gioiTinh = (String) cboGioiTinh.getSelectedItem();
        String trangThaiSel = (String) cboTrangThai.getSelectedItem(); // Lấy trạng thái đang chọn
        ChucVu chucVuSelected = (ChucVu) cboChucVu.getSelectedItem();

        List<NhanVien> dsNV = (List<NhanVien>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHAN_VIEN, null)).getData(); 

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (NhanVien nv : dsNV) {
            boolean match = true;

            if (!tenNV.isEmpty() && (nv.getTenNV() == null || !nv.getTenNV().toLowerCase().contains(tenNV))) match = false;
            if (!sdt.isEmpty() && (nv.getSoDienThoai() == null || !nv.getSoDienThoai().toLowerCase().contains(sdt))) match = false;
            if (!"Tất cả".equals(gioiTinh) && !nv.getGioiTinh().equalsIgnoreCase(gioiTinh)) match = false;
            
            // --- Lọc theo Chức Vụ ---
            if (chucVuSelected != null && nv.getChucVu() != null) {
                if (!nv.getChucVu().getMaChucVu().equals(chucVuSelected.getMaChucVu())) {
                    match = false;
                }
            }
            
            // --- Lọc theo Trạng Thái (Mới thêm) ---
            // Nếu không chọn "Tất cả" VÀ trạng thái của NV khác với lựa chọn thì loại
            if (trangThaiSel != null && !"Tất cả".equals(trangThaiSel)) {
                 String ttNV = nv.getTrangThai() != null ? nv.getTrangThai() : "";
                 if (!ttNV.equalsIgnoreCase(trangThaiSel)) match = false;
            }
            
            if (!tinh.isEmpty() && (nv.getDiaChi() == null || !nv.getDiaChi().toLowerCase().contains(tinh))) match = false;
            if (!huyen.isEmpty() && (nv.getDiaChi() == null || !nv.getDiaChi().toLowerCase().contains(huyen))) match = false;

            if (match) {
                model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNgaySinh().format(dtf),
                    nv.getGioiTinh(),
                    nv.getChucVu() != null ? nv.getChucVu().getTenChucVu() : "",
                    nv.getSoDienThoai(),
                    nv.getDiaChi(),
                    nv.getAnh(),
                    nv.getTaiKhoan() != null ? nv.getTaiKhoan().getTenTK() : "",
                    nv.getTrangThai()
                });
            }
        }
    }

 // Chú ý: Thêm tham số cboTrangThai vào hàm này
    private void addLocSuKien(JTextField txtTen, JTextField txtSDT, JTextField txtTinh,
            JTextField txtHuyen, JComboBox<String> cboGioiTinh,
            JComboBox<ChucVu> cboChucVu, JComboBox<String> cboTrangThai, JTable table) { // <--- Thêm cboTrangThai

        // --- Sự kiện cho text field ---
        DocumentListener docListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { locNhanVien(txtTen, txtSDT, txtTinh, txtHuyen, cboGioiTinh, cboChucVu, cboTrangThai, table); }
            @Override public void removeUpdate(DocumentEvent e) { locNhanVien(txtTen, txtSDT, txtTinh, txtHuyen, cboGioiTinh, cboChucVu, cboTrangThai, table); }
            @Override public void changedUpdate(DocumentEvent e) { locNhanVien(txtTen, txtSDT, txtTinh, txtHuyen, cboGioiTinh, cboChucVu, cboTrangThai, table); }
        };

        txtTen.getDocument().addDocumentListener(docListener);
        txtSDT.getDocument().addDocumentListener(docListener);
        txtTinh.getDocument().addDocumentListener(docListener);
        txtHuyen.getDocument().addDocumentListener(docListener);

        // --- Sự kiện cho combo box ---
        cboGioiTinh.addActionListener(e -> locNhanVien(txtTen, txtSDT, txtTinh, txtHuyen, cboGioiTinh, cboChucVu, cboTrangThai, table));
        cboChucVu.addActionListener(e -> locNhanVien(txtTen, txtSDT, txtTinh, txtHuyen, cboGioiTinh, cboChucVu, cboTrangThai, table));
        // Thêm sự kiện cho trạng thái
        cboTrangThai.addActionListener(e -> locNhanVien(txtTen, txtSDT, txtTinh, txtHuyen, cboGioiTinh, cboChucVu, cboTrangThai, table));
    }
    private void loadImageToLabel(String path, JLabel lblAnh) {
        if (path != null && !path.trim().isEmpty()) {
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), Image.SCALE_SMOOTH);
            lblAnh.setIcon(new ImageIcon(img));
            lblAnh.setText("");
        } else {
            lblAnh.setIcon(null);
            lblAnh.setText("Chưa có ảnh");
        }
    }
    
    /**
     * Hàm để style các nút bấm cho đồng bộ
     * @param button Nút cần style
     * @param background Màu nền
     */
    private void styleButton(JButton button, Color background) {
        button.setBackground(background);
        button.setForeground(Color.WHITE); // Màu chữ trắng cho các nút có nền màu
        button.setFocusPainted(false); // Bỏ viền focus khi click
        // Đại Ca có thể bỏ dòng setBorder nếu muốn giữ kích thước gốc
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
    }

    /**
     * Hàm này áp dụng style CHUNG cho một JTable đã được tạo
     * @param table Bảng cần áp dụng style
     */
    private void applyCommonTableStyling(JTable table) {
        // ----- TÙY CHỈNH CHUNG CHO BẢNG -----
        table.setFont(FONT_TABLE_CELL);
        table.setRowHeight(30); // Chiều cao dòng
        table.setGridColor(COLOR_BORDER_LIGHT);
        table.setShowGrid(true); // Hiển thị đường kẻ
        table.setSelectionBackground(COLOR_PRIMARY_BLUE);
        table.setSelectionForeground(Color.WHITE);
        
        // ----- TÙY CHỈNH HEADER CỦA BẢNG -----
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(FONT_TABLE_HEADER);
            header.setBackground(new Color(230, 235, 240)); // Màu nền header
            header.setForeground(COLOR_TEXT_DARK);
            header.setBorder(BorderFactory.createLineBorder(COLOR_BORDER_LIGHT));
            header.setReorderingAllowed(false); // Không cho phép sắp xếp lại cột
            header.setResizingAllowed(true); // Cho phép thay đổi kích thước cột
        }
    }
    
 // Hàm thêm listener cho hiệu ứng accordion (ĐÃ SỬA)
    private void addAccordionListener(JButton mainButton, JPanel submenuPanel, JPanel sidebarContainer) {
        // Áp dụng hiệu ứng hover cho nút chính này
        addHoverEffect(mainButton); // <<< GỌI Ở ĐÂY

        mainButton.addActionListener(e -> {
            // Kiểm tra xem có panel con nào đang mở không
            if (currentVisibleSubmenu != null && currentVisibleSubmenu != submenuPanel) {
                currentVisibleSubmenu.setVisible(false); // Ẩn panel con cũ
                // Reset style nút chính cũ (nếu muốn)
                 if (selectedMainMenuButton != null) {
                    selectedMainMenuButton.setOpaque(false);
                    selectedMainMenuButton.setBackground(COLOR_SIDEBAR_BG_END);
                 }
            }

            // Đảo trạng thái hiển thị của panel con được click
            boolean isVisible = !submenuPanel.isVisible();
            submenuPanel.setVisible(isVisible);

            // Cập nhật nút chính đang được chọn và style của nó
            if (isVisible) {
                selectedMainMenuButton = mainButton;
                selectedMainMenuButton.setOpaque(true); // Hiện màu nền selected
                selectedMainMenuButton.setBackground(COLOR_SIDEBAR_BUTTON_SELECTED);
                currentVisibleSubmenu = submenuPanel;
            } else {
                // Nếu đóng panel đang mở, bỏ chọn nút chính
                mainButton.setOpaque(false); // Trở lại trong suốt
                mainButton.setBackground(COLOR_SIDEBAR_BG_END);
                selectedMainMenuButton = null;
                currentVisibleSubmenu = null;
            }
            
            sidebarContainer.revalidate();
            sidebarContainer.repaint();
        });
    }
    
    /**
     * Hàm hỗ trợ xuất dữ liệu từ JTable ra file Excel (dùng Apache POI)
     * Lưu tự động vào thư mục 'xuat_excel' trong project.
     * @param tableToExport Bảng JTable chứa dữ liệu cần xuất
     * @param defaultFileName Tên file gợi ý (không bao gồm .xlsx) - SẼ BỊ GHI ĐÈ BỞI FORMAT MỚI
     */
    private void exportTableToExcel(JTable tableToExport, String defaultFileName) {
        // --- BẮT ĐẦU THAY ĐỔI ---
        // 1. Lấy ngày từ JDateChooser để đặt tên file (Ưu tiên) hoặc dùng ngày hiện tại
        String ngayXuatStr;
        if (date_tktn_ngay.getDate() != null) {
            ngayXuatStr = new SimpleDateFormat("dd-MM-yyyy").format(date_tktn_ngay.getDate());
        } else {
            // Nếu không chọn ngày, lấy ngày hiện tại
            ngayXuatStr = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        }
        String finalFileName = "Thong Ke Hoa Don Ngay " + ngayXuatStr + ".xlsx";

        // 2. Tạo đường dẫn thư mục 'xuat_excel' trong thư mục gốc của project
        String projectDir = System.getProperty("user.dir"); // Lấy thư mục gốc của project
        String exportDirPath = projectDir + File.separator + "xuat_excel"; // Đường dẫn thư mục xuất file
        File exportDir = new File(exportDirPath);

        // 3. Tạo thư mục nếu chưa tồn tại
        if (!exportDir.exists()) {
            boolean created = exportDir.mkdirs(); // Tạo thư mục (và các thư mục cha nếu cần)
            if (!created) {
                JOptionPane.showMessageDialog(QuanLyHieuThuocTay,
                        "Không thể tạo thư mục lưu file tại:\n" + exportDirPath,
                        "Lỗi Tạo Thư Mục", JOptionPane.ERROR_MESSAGE);
                return; // Dừng lại nếu không tạo được thư mục
            }
        }

        // 4. Tạo đường dẫn file đầy đủ
        String filePath = exportDirPath + File.separator + finalFileName;
        // --- KẾT THÚC THAY ĐỔI ---

        // 5. Bắt đầu quá trình tạo file Excel (Giữ nguyên phần try-catch)
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("ThongKeNgay_" + ngayXuatStr); // Tên sheet có thể giữ hoặc đổi
            TableModel model = tableToExport.getModel();

            // 6. Tạo hàng tiêu đề (Header)
            Row headerRow = sheet.createRow(0);
            for (int j = 0; j < model.getColumnCount(); j++) {
                Cell cell = headerRow.createCell(j);
                cell.setCellValue(model.getColumnName(j));
            }

            // 7. Ghi dữ liệu từ JTable vào file
            for (int i = 0; i < model.getRowCount(); i++) {
                Row dataRow = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    Cell cell = dataRow.createCell(j);
                    // Set giá trị (Giữ nguyên logic xử lý kiểu dữ liệu)
                    if (value instanceof String) {
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
            
            // Tự động điều chỉnh độ rộng cột
            for(int j = 0; j < model.getColumnCount(); j++) {
                sheet.autoSizeColumn(j);
            }

            // 8. Lưu file (Đường dẫn đã được xác định ở trên)
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(QuanLyHieuThuocTay,
                    "Xuất file Excel thành công!\nĐã lưu tại thư mục 'xuat_excel' trong project.", // Thông báo mới
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(QuanLyHieuThuocTay,
                    "Có lỗi xảy ra khi xuất file Excel:\n" + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        // Bỏ hoàn toàn phần else của if (userSelection == JFileChooser.APPROVE_OPTION)
    }
    
    private ImageIcon loadIcon(String path) {
        // Chuẩn hóa đường dẫn: bỏ dấu gạch chéo đầu, thêm img/ nếu thiếu
        if (path.startsWith("/")) path = path.substring(1);
        if (!path.startsWith("img/")) path = "img/" + path;
        
        try {
            java.io.File file = new java.io.File(path);
            if (file.exists()) {
                return new ImageIcon(path);
            } else {
                // Thử trường hợp chạy từ jar hoặc classpath khác
                java.net.URL imgURL = getClass().getResource("/" + path);
                if (imgURL != null) return new ImageIcon(imgURL);
                
                System.err.println("Không tìm thấy icon tại: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi load icon: " + path + " - " + e.getMessage());
            return null;
        }
    }
 // Hàm lọc dữ liệu cho Tab Cập Nhật
    private void locNhanVienCapNhat(JTextField txtTen, JComboBox<ChucVu> cboChucVu, JTable table) {
        String keyword = txtTen.getText().trim().toLowerCase();
        ChucVu selectedCV = (ChucVu) cboChucVu.getSelectedItem();
        String maChucVu = (selectedCV != null) ? selectedCV.getMaChucVu() : "";

        List<NhanVien> dsNV = (List<NhanVien>) SocketClient.sendRequest(new Request(ActionType.GET_ALL_NHAN_VIEN, null)).getData(); // Lấy tất cả nhân viên

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Định dạng ngày chuẩn

        for (NhanVien nv : dsNV) {
            // 1. Kiểm tra Tên
            boolean matchTen = keyword.isEmpty() || (nv.getTenNV() != null && nv.getTenNV().toLowerCase().contains(keyword));
            
            // 2. Kiểm tra Chức vụ (so sánh theo Mã)
            boolean matchCV = maChucVu.isEmpty() || (nv.getChucVu() != null && nv.getChucVu().getMaChucVu().equals(maChucVu));

            if (matchTen && matchCV) {
                String trangThai = nv.getTrangThai() != null ? nv.getTrangThai() : "Đang làm việc";
                
                model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getNgaySinh().format(dtf), // <--- Format ngày sinh cho đúng chuẩn hiển thị
                    nv.getGioiTinh(),
                    nv.getChucVu() != null ? nv.getChucVu().getTenChucVu() : "",
                    nv.getSoDienThoai(),
                    nv.getDiaChi(),
                    nv.getAnh(),
                    nv.getTaiKhoan() != null ? nv.getTaiKhoan().getTenTK() : "",
                    trangThai // <--- QUAN TRỌNG: Đã thêm cột trạng thái
                });
            }
        }
    }
}