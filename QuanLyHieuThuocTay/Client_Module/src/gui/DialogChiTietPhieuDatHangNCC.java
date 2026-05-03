//package gui;
//
//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.JLabel;
//import java.awt.Font;
//import javax.swing.JTextField;
//import javax.swing.JComboBox;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import javax.swing.JTextArea;
//
//public class DialogChiTietPhieuDatHangNCC extends JDialog {
//
//	private static final long serialVersionUID = 1L;
//	private final JPanel contentPanel = new JPanel();
//	private JTextField textField;
//	private JTextField textField_2;
//	private JTable table;
//	private JTextField textField_1;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			DialogChiTietPhieuDatHangNCC dialog = new DialogChiTietPhieuDatHangNCC();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Create the dialog.
//	 */
//	public DialogChiTietPhieuDatHangNCC() {
//		setBounds(100, 100, 808, 542);
//		getContentPane().setLayout(new BorderLayout());
//		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		contentPanel.setLayout(new BorderLayout(0, 0));
//		{
//			JPanel panel = new JPanel();
//			contentPanel.add(panel, BorderLayout.CENTER);
//			panel.setLayout(null);
//			
//			JLabel lblNewLabel = new JLabel("Mã Phiếu:");
//			lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			lblNewLabel.setBounds(78, 11, 157, 28);
//			panel.add(lblNewLabel);
//			
//			JLabel lblNhCungCp = new JLabel("Nhà cung cấp:");
//			lblNhCungCp.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			lblNhCungCp.setBounds(78, 50, 157, 28);
//			panel.add(lblNhCungCp);
//			
//			JLabel lblNgyt = new JLabel("Ngày đăt:");
//			lblNgyt.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			lblNgyt.setBounds(78, 89, 157, 28);
//			panel.add(lblNgyt);
//			
//			textField = new JTextField();
//			textField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//			textField.setBounds(245, 11, 474, 28);
//			panel.add(textField);
//			textField.setColumns(10);
//			
//			textField_2 = new JTextField();
//			textField_2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//			textField_2.setColumns(10);
//			textField_2.setBounds(245, 89, 474, 28);
//			panel.add(textField_2);
//			
//			JComboBox comboBox = new JComboBox();
//			comboBox.setBounds(245, 52, 474, 28);
//			panel.add(comboBox);
//			
//			JScrollPane scrollPane = new JScrollPane();
//			scrollPane.setBounds(10, 128, 762, 152);
//			panel.add(scrollPane);
//			
//			table = new JTable();
//			table.setModel(new DefaultTableModel(
//				new Object[][] {
//					{null, null, null, null, null},
//				},
//				new String[] {
//					"M\u00E3 thu\u1ED1c", "T\u00EAn thu\u1ED1c", "S\u1ED1 l\u01B0\u1EE3ng", "\u0110\u01A1n gi\u00E1", "Th\u00E0nh ti\u1EC1n"
//				}
//			));
//			scrollPane.setViewportView(table);
//			
//			JButton btnNewButton = new JButton("Thêm thuốc");
//			btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			btnNewButton.setBounds(245, 291, 139, 33);
//			panel.add(btnNewButton);
//			
//			JButton btnXaThuc = new JButton("Xóa thuốc");
//			btnXaThuc.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//				}
//			});
//			btnXaThuc.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			btnXaThuc.setBounds(416, 291, 139, 33);
//			panel.add(btnXaThuc);
//			
//			JLabel lblNewLabel_1 = new JLabel("Tổng tiền:");
//			lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			lblNewLabel_1.setBounds(10, 337, 114, 28);
//			panel.add(lblNewLabel_1);
//			
//			JLabel lblNewLabel_1_1 = new JLabel("Ghi chú:");
//			lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
//			lblNewLabel_1_1.setBounds(10, 376, 114, 28);
//			panel.add(lblNewLabel_1_1);
//			
//			textField_1 = new JTextField();
//			textField_1.setEditable(false);
//			textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
//			textField_1.setColumns(10);
//			textField_1.setBounds(133, 335, 474, 28);
//			panel.add(textField_1);
//			
//			JTextArea textArea = new JTextArea();
//			textArea.setBounds(134, 380, 473, 55);
//			panel.add(textArea);
//		}
//		{
//			JPanel buttonPane = new JPanel();
//			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
//			getContentPane().add(buttonPane, BorderLayout.SOUTH);
//			{
//				JButton okButton = new JButton("Lưu");
//				okButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
//				okButton.setActionCommand("OK");
//				buttonPane.add(okButton);
//				getRootPane().setDefaultButton(okButton);
//			}
//			{
//				JButton cancelButton = new JButton("Hủy");
//				cancelButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
//				cancelButton.setActionCommand("Cancel");
//				buttonPane.add(cancelButton);
//			}
//		}
//	}
//}


package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame; // Thêm import
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder; // Thêm import
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints; // Thêm import
import java.awt.GridBagLayout; // Thêm import
import java.awt.Insets; // Thêm import
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import com.toedter.calendar.JDateChooser; // Thêm import JDateChooser
import java.awt.Color; // Thêm import
import javax.swing.DefaultComboBoxModel; // Thêm import


public class DialogChiTietPhieuDatHangNCC extends JDialog {

	private static final long serialVersionUID = 1L;
	

	public JTextField txtMaPhieu;
	public JComboBox<String> cboNhaCungCap;
	public JDateChooser dateChooserNgayDat;
	public JTable tableChiTietDatHang;
	public JTextField txtTongTien;
	public JTextArea txtGhiChu;
	public JScrollPane scrollPaneGhiChu; 
	
	
	public JComboBox<String> cboChonThuoc;
	public JTextField txtSoLuongThuoc;
	public JTextField txtDonGiaThuoc;
	public JButton btnThemThuocVaoBang;
	public JButton btnXoaThuocKhoiBang;
	
	public JButton btnLuu;
	public JButton btnHuy;

	/**
	 * Create the dialog.
	 * Sửa lại Constructor: Thêm "JFrame parent" để nó là 1 dialog modal
	 */
	public DialogChiTietPhieuDatHangNCC(JFrame parent) {
		super(parent, "Chi tiết Phiếu Đặt Hàng", true); 
		
		setResizable(false); // Không cho phép đổi kích thước
		setBounds(100, 100, 850, 650); // Tăng chiều cao 
		setLocationRelativeTo(parent); // Hiển thị dialog ở giữa màn hình cha
		
		getContentPane().setLayout(new BorderLayout());
		
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(10, 10));
		
		
		JPanel panelThongTinChung = new JPanel();
		panelThongTinChung.setBorder(new TitledBorder(null, "Th\u00F4ng tin chung", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59, 59)));
		contentPanel.add(panelThongTinChung, BorderLayout.NORTH);
		GridBagLayout gbl_panelThongTinChung = new GridBagLayout();
		gbl_panelThongTinChung.columnWidths = new int[]{100, 0};
		gbl_panelThongTinChung.columnWeights = new double[]{0.0, 1.0};
		panelThongTinChung.setLayout(gbl_panelThongTinChung);
		
		// Mã Phiếu
		JLabel lblMaPhieu = new JLabel("Mã Phiếu:");
		lblMaPhieu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblMaPhieu = new GridBagConstraints();
		gbc_lblMaPhieu.insets = new Insets(5, 5, 5, 5);
		gbc_lblMaPhieu.anchor = GridBagConstraints.EAST;
		gbc_lblMaPhieu.gridx = 0;
		gbc_lblMaPhieu.gridy = 0;
		panelThongTinChung.add(lblMaPhieu, gbc_lblMaPhieu);
		
		txtMaPhieu = new JTextField();
		txtMaPhieu.setEditable(false); // Mã phiếu nên được tạo tự động
		txtMaPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		GridBagConstraints gbc_txtMaPhieu = new GridBagConstraints();
		gbc_txtMaPhieu.insets = new Insets(5, 5, 5, 5);
		gbc_txtMaPhieu.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtMaPhieu.gridx = 1;
		gbc_txtMaPhieu.gridy = 0;
		panelThongTinChung.add(txtMaPhieu, gbc_txtMaPhieu);
		txtMaPhieu.setColumns(10);
		
		// Nhà cung cấp
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp:");
		lblNhaCungCap.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNhaCungCap = new GridBagConstraints();
		gbc_lblNhaCungCap.anchor = GridBagConstraints.EAST;
		gbc_lblNhaCungCap.insets = new Insets(5, 5, 5, 5);
		gbc_lblNhaCungCap.gridx = 0;
		gbc_lblNhaCungCap.gridy = 1;
		panelThongTinChung.add(lblNhaCungCap, gbc_lblNhaCungCap);
		
		cboNhaCungCap = new JComboBox<String>();
		cboNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		// (Controller sẽ load danh sách NCC vào đây)
		GridBagConstraints gbc_cboNhaCungCap = new GridBagConstraints();
		gbc_cboNhaCungCap.insets = new Insets(5, 5, 5, 5);
		gbc_cboNhaCungCap.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboNhaCungCap.gridx = 1;
		gbc_cboNhaCungCap.gridy = 1;
		panelThongTinChung.add(cboNhaCungCap, gbc_cboNhaCungCap);
		
		// Ngày đặt (Dùng JDateChooser)
		JLabel lblNgayDat = new JLabel("Ngày đăt:");
		lblNgayDat.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblNgayDat = new GridBagConstraints();
		gbc_lblNgayDat.anchor = GridBagConstraints.EAST;
		gbc_lblNgayDat.insets = new Insets(5, 5, 5, 5);
		gbc_lblNgayDat.gridx = 0;
		gbc_lblNgayDat.gridy = 2;
		panelThongTinChung.add(lblNgayDat, gbc_lblNgayDat);
		
		dateChooserNgayDat = new JDateChooser();
		dateChooserNgayDat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dateChooserNgayDat.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_dateChooserNgayDat = new GridBagConstraints();
		gbc_dateChooserNgayDat.insets = new Insets(5, 5, 5, 5);
		gbc_dateChooserNgayDat.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateChooserNgayDat.gridx = 1;
		gbc_dateChooserNgayDat.gridy = 2;
		panelThongTinChung.add(dateChooserNgayDat, gbc_dateChooserNgayDat);

		
		// --- 2. Panel Chi Tiết Đặt Hàng (Bảng + Nút Thêm/Xóa) ---
		JPanel panelChiTiet = new JPanel();
		panelChiTiet.setBorder(new TitledBorder(null, "Chi ti\u1EBFt \u0111\u1EB7t h\u00E0ng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelChiTiet, BorderLayout.CENTER);
		panelChiTiet.setLayout(new BorderLayout(0, 5)); // Dùng BorderLayout
		
		// --- Panel Nhập liệu Thêm thuốc (MỚI) ---
		JPanel panelThemThuocInput = new JPanel();
		panelChiTiet.add(panelThemThuocInput, BorderLayout.NORTH);
		panelThemThuocInput.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblChonThuoc = new JLabel("Thuốc:");
		lblChonThuoc.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelThemThuocInput.add(lblChonThuoc);
		
		cboChonThuoc = new JComboBox<String>();
		cboChonThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		// (Controller sẽ load danh sách Thuốc vào đây)
		panelThemThuocInput.add(cboChonThuoc);
		
		JLabel lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelThemThuocInput.add(lblSoLuong);
		
		txtSoLuongThuoc = new JTextField();
		txtSoLuongThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThemThuocInput.add(txtSoLuongThuoc);
		txtSoLuongThuoc.setColumns(5); // Kích thước vừa phải
		
		JLabel lblDonGia = new JLabel("Đơn giá:");
		lblDonGia.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelThemThuocInput.add(lblDonGia);
		
		txtDonGiaThuoc = new JTextField();
		txtDonGiaThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThemThuocInput.add(txtDonGiaThuoc);
		txtDonGiaThuoc.setColumns(10);
		
		btnThemThuocVaoBang = new JButton("Thêm");
		btnThemThuocVaoBang.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelThemThuocInput.add(btnThemThuocVaoBang);
		
		btnXoaThuocKhoiBang = new JButton("Xóa thuốc");
		btnXoaThuocKhoiBang.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelThemThuocInput.add(btnXoaThuocKhoiBang);
		// --- Hết Panel Nhập liệu ---
		
		
		// Bảng chi tiết
		JScrollPane scrollPaneBangChiTiet = new JScrollPane();
		panelChiTiet.add(scrollPaneBangChiTiet, BorderLayout.CENTER);
		
		tableChiTietDatHang = new JTable();
		tableChiTietDatHang.setModel(new DefaultTableModel(
			new Object[][] {
				// Dữ liệu sẽ được Controller thêm vào
			},
			new String[] {
				"Mã thuốc", "Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"
			}
		) {
			// Không cho phép sửa trực tiếp trên bảng
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPaneBangChiTiet.setViewportView(tableChiTietDatHang);

		
		// --- 3. Panel Tổng Kết (Tổng tiền, Ghi chú) ---
		JPanel panelTongKet = new JPanel();
		panelTongKet.setBorder(new TitledBorder(null, "T\u1ED5ng k\u1EBFt", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPanel.add(panelTongKet, BorderLayout.SOUTH);
		// Dùng GridBagLayout
		GridBagLayout gbl_panelTongKet = new GridBagLayout();
		gbl_panelTongKet.columnWidths = new int[]{100, 0};
		gbl_panelTongKet.columnWeights = new double[]{0.0, 1.0};
		panelTongKet.setLayout(gbl_panelTongKet);
		
		// Tổng tiền
		JLabel lblTongTien = new JLabel("Tổng tiền:");
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblTongTien = new GridBagConstraints();
		gbc_lblTongTien.anchor = GridBagConstraints.EAST;
		gbc_lblTongTien.insets = new Insets(5, 5, 5, 5);
		gbc_lblTongTien.gridx = 0;
		gbc_lblTongTien.gridy = 0;
		panelTongKet.add(lblTongTien, gbc_lblTongTien);
		
		txtTongTien = new JTextField();
		txtTongTien.setEditable(false); // Tổng tiền được tính tự động
		txtTongTien.setFont(new Font("Segoe UI", Font.BOLD, 14));
		txtTongTien.setForeground(Color.RED); // Nổi bật
		GridBagConstraints gbc_txtTongTien = new GridBagConstraints();
		gbc_txtTongTien.insets = new Insets(5, 5, 5, 5);
		gbc_txtTongTien.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTongTien.gridx = 1;
		gbc_txtTongTien.gridy = 0;
		panelTongKet.add(txtTongTien, gbc_txtTongTien);
		txtTongTien.setColumns(10);
		
		// Ghi chú
		JLabel lblGhiChu = new JLabel("Ghi chú:");
		lblGhiChu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		GridBagConstraints gbc_lblGhiChu = new GridBagConstraints();
		gbc_lblGhiChu.anchor = GridBagConstraints.NORTHEAST; // Căn lề trên
		gbc_lblGhiChu.insets = new Insets(5, 5, 5, 5);
		gbc_lblGhiChu.gridx = 0;
		gbc_lblGhiChu.gridy = 1;
		panelTongKet.add(lblGhiChu, gbc_lblGhiChu);
		
		// JScrollPane cho Ghi chú (ĐÃ SỬA)
		scrollPaneGhiChu = new JScrollPane();
		GridBagConstraints gbc_scrollPaneGhiChu = new GridBagConstraints();
		gbc_scrollPaneGhiChu.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneGhiChu.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneGhiChu.gridx = 1;
		gbc_scrollPaneGhiChu.gridy = 1;
		panelTongKet.add(scrollPaneGhiChu, gbc_scrollPaneGhiChu);
		
		txtGhiChu = new JTextArea();
		txtGhiChu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		txtGhiChu.setRows(3); // Giới hạn 3 dòng
		txtGhiChu.setWrapStyleWord(true);
		txtGhiChu.setLineWrap(true);
		scrollPaneGhiChu.setViewportView(txtGhiChu); // Đặt JTextArea vào JScrollPane
		
		
		// --- Panel Nút Bấm (Lưu, Hủy) ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnLuu = new JButton("Lưu");
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLuu.setActionCommand("OK");
		buttonPane.add(btnLuu);
		getRootPane().setDefaultButton(btnLuu);
		
		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnHuy.setActionCommand("Cancel");
		buttonPane.add(btnHuy);
		
		// Xóa hàm main, vì đây là Dialog
	}
}
