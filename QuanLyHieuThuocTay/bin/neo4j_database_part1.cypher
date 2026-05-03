// ============================================================
// PHẦN 1: XÓA DỮ LIỆU CŨ + TẠO CONSTRAINTS + DỮ LIỆU NỀN
// ============================================================

// --- XÓA TOÀN BỘ DỮ LIỆU CŨ ---
MATCH (n) DETACH DELETE n;

// --- TẠO CONSTRAINTS (Chạy từng dòng một trong Neo4j Browser) ---
CREATE CONSTRAINT IF NOT EXISTS FOR (cv:ChucVu) REQUIRE cv.maChucVu IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (tk:TaiKhoan) REQUIRE tk.maTK IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (nv:NhanVien) REQUIRE nv.maNV IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (kh:KhachHang) REQUIRE kh.maKH IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (ncc:NhaCungCap) REQUIRE ncc.maNhaCungCap IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (ke:KeThuoc) REQUIRE ke.maKe IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (t:Thuoc) REQUIRE t.maThuoc IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (km:KhuyenMai) REQUIRE km.maKM IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (th:Thue) REQUIRE th.maThue IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (hd:HoaDon) REQUIRE hd.maHD IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (p:PhieuDatHang) REQUIRE p.maPhieu IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (pdt:PhieuDoiTra) REQUIRE pdt.maPDT IS UNIQUE;
CREATE CONSTRAINT IF NOT EXISTS FOR (pc:PhieuCho) REQUIRE pc.maPhieuCho IS UNIQUE;

// ============================================================
// 1. TẠO NODE: ChucVu
// ============================================================
CREATE (cv1:ChucVu {maChucVu: 'CV001', tenChucVu: 'Quản lý', moTa: 'Quản lý cửa hàng'});
CREATE (cv2:ChucVu {maChucVu: 'CV002', tenChucVu: 'Nhân viên bán hàng', moTa: 'Bán thuốc và tư vấn'});
CREATE (cv3:ChucVu {maChucVu: 'CV003', tenChucVu: 'Dược sĩ', moTa: 'Tư vấn và kiểm tra thuốc'});
CREATE (cv4:ChucVu {maChucVu: 'CV004', tenChucVu: 'Thủ kho', moTa: 'Quản lý kho thuốc'});

// ============================================================
// 2. TẠO NODE: TaiKhoan
// ============================================================
CREATE (tk1:TaiKhoan {maTK: 'TK001', tenTK: 'admin', matKhau: 'admin', quyenHan: 'Quản lý', trangThai: true});
CREATE (tk2:TaiKhoan {maTK: 'TK002', tenTK: 'nhanvien1', matKhau: '123456', quyenHan: 'Nhân viên bán hàng', trangThai: true});
CREATE (tk3:TaiKhoan {maTK: 'TK003', tenTK: 'nhanvien2', matKhau: '123456', quyenHan: 'Nhân viên bán hàng', trangThai: true});
CREATE (tk4:TaiKhoan {maTK: 'TK004', tenTK: 'duocsi1', matKhau: '123456', quyenHan: 'Nhân viên bán hàng', trangThai: true});
CREATE (tk5:TaiKhoan {maTK: 'TK005', tenTK: 'thukho1', matKhau: '123456', quyenHan: 'Nhân viên bán hàng', trangThai: true});

// ============================================================
// 3. TẠO NODE: NhanVien + RELATIONSHIPS
// ============================================================
CREATE (nv1:NhanVien {maNV: 'NV001', tenNV: 'Nguyễn Văn An', ngaySinh: '1985-03-15', gioiTinh: 'Nam', soDienThoai: '0901234567', diaChi: '123 Nguyễn Trãi, Q.5, TP.HCM', anh: 'img/anhnhanvien/nam2.jpg', trangThai: 'Đang làm việc'});
CREATE (nv2:NhanVien {maNV: 'NV002', tenNV: 'Trần Thị Bình', ngaySinh: '1995-07-22', gioiTinh: 'Nữ', soDienThoai: '0912345678', diaChi: '45 Lê Lợi, Q.1, TP.HCM', anh: 'img/anhnhanvien/nu1.jpg', trangThai: 'Đang làm việc'});
CREATE (nv3:NhanVien {maNV: 'NV003', tenNV: 'Lê Hoàng Cường', ngaySinh: '1992-11-08', gioiTinh: 'Nam', soDienThoai: '0923456789', diaChi: '78 Trần Hưng Đạo, Q.5, TP.HCM', anh: 'img/anhnhanvien/nam3.jpg', trangThai: 'Đang làm việc'});
CREATE (nv4:NhanVien {maNV: 'NV004', tenNV: 'Phạm Thị Dung', ngaySinh: '1998-01-30', gioiTinh: 'Nữ', soDienThoai: '0934567890', diaChi: '12 Hai Bà Trưng, Q.3, TP.HCM', anh: 'img/anhnhanvien/nu2.jpg', trangThai: 'Đang làm việc'});
CREATE (nv5:NhanVien {maNV: 'NV005', tenNV: 'Võ Minh Tuấn', ngaySinh: '1990-05-20', gioiTinh: 'Nam', soDienThoai: '0945678901', diaChi: '56 Cách Mạng Tháng 8, Q.10, TP.HCM', anh: 'img/anhnhanvien/nam4.jpg', trangThai: 'Đang làm việc'});

// Tạo relationships NhanVien -> ChucVu
MATCH (nv:NhanVien {maNV: 'NV001'}), (cv:ChucVu {maChucVu: 'CV001'}) CREATE (nv)-[:CO_CHUC_VU]->(cv);
MATCH (nv:NhanVien {maNV: 'NV002'}), (cv:ChucVu {maChucVu: 'CV002'}) CREATE (nv)-[:CO_CHUC_VU]->(cv);
MATCH (nv:NhanVien {maNV: 'NV003'}), (cv:ChucVu {maChucVu: 'CV002'}) CREATE (nv)-[:CO_CHUC_VU]->(cv);
MATCH (nv:NhanVien {maNV: 'NV004'}), (cv:ChucVu {maChucVu: 'CV003'}) CREATE (nv)-[:CO_CHUC_VU]->(cv);
MATCH (nv:NhanVien {maNV: 'NV005'}), (cv:ChucVu {maChucVu: 'CV004'}) CREATE (nv)-[:CO_CHUC_VU]->(cv);

// Tạo relationships NhanVien -> TaiKhoan
MATCH (nv:NhanVien {maNV: 'NV001'}), (tk:TaiKhoan {maTK: 'TK001'}) CREATE (nv)-[:CO_TAI_KHOAN]->(tk);
MATCH (nv:NhanVien {maNV: 'NV002'}), (tk:TaiKhoan {maTK: 'TK002'}) CREATE (nv)-[:CO_TAI_KHOAN]->(tk);
MATCH (nv:NhanVien {maNV: 'NV003'}), (tk:TaiKhoan {maTK: 'TK003'}) CREATE (nv)-[:CO_TAI_KHOAN]->(tk);
MATCH (nv:NhanVien {maNV: 'NV004'}), (tk:TaiKhoan {maTK: 'TK004'}) CREATE (nv)-[:CO_TAI_KHOAN]->(tk);
MATCH (nv:NhanVien {maNV: 'NV005'}), (tk:TaiKhoan {maTK: 'TK005'}) CREATE (nv)-[:CO_TAI_KHOAN]->(tk);

// ============================================================
// 4. TẠO NODE: KhachHang
// ============================================================
CREATE (kh1:KhachHang {maKH: 'KH001', tenKH: 'Nguyễn Thị Mai', soDienThoai: '0961111111', diaChi: '10 Phan Đình Phùng, Q.Phú Nhuận', trangThai: true});
CREATE (kh2:KhachHang {maKH: 'KH002', tenKH: 'Trần Văn Nam', soDienThoai: '0962222222', diaChi: '25 Nguyễn Huệ, Q.1, TP.HCM', trangThai: true});
CREATE (kh3:KhachHang {maKH: 'KH003', tenKH: 'Lê Thị Hoa', soDienThoai: '0963333333', diaChi: '88 Lý Tự Trọng, Q.1, TP.HCM', trangThai: true});
CREATE (kh4:KhachHang {maKH: 'KH004', tenKH: 'Phạm Quốc Đạt', soDienThoai: '0964444444', diaChi: '33 Võ Văn Tần, Q.3, TP.HCM', trangThai: true});
CREATE (kh5:KhachHang {maKH: 'KH005', tenKH: 'Hoàng Thị Lan', soDienThoai: '0965555555', diaChi: '67 Điện Biên Phủ, Q.Bình Thạnh', trangThai: true});
CREATE (kh6:KhachHang {maKH: 'KH006', tenKH: 'Vũ Đức Minh', soDienThoai: '0966666666', diaChi: '99 Trường Chinh, Q.Tân Phú', trangThai: true});
CREATE (kh7:KhachHang {maKH: 'KH007', tenKH: 'Đỗ Thị Ngọc', soDienThoai: '0967777777', diaChi: '42 Pasteur, Q.3, TP.HCM', trangThai: true});
CREATE (kh8:KhachHang {maKH: 'KH008', tenKH: 'Bùi Văn Phong', soDienThoai: '0968888888', diaChi: '15 Nam Kỳ Khởi Nghĩa, Q.1', trangThai: true});

// ============================================================
// 5. TẠO NODE: NhaCungCap
// ============================================================
CREATE (ncc1:NhaCungCap {maNhaCungCap: 'NCC001', tenNhaCungCap: 'Công ty Dược Hậu Giang', soDienThoai: '02923891433', email: 'info@dhgpharma.com.vn', diaChi: '288 Bis Nguyễn Văn Cừ, TP.Cần Thơ', trangThai: true, ghiChu: 'Nhà cung cấp chính'});
CREATE (ncc2:NhaCungCap {maNhaCungCap: 'NCC002', tenNhaCungCap: 'Công ty Dược phẩm Imexpharm', soDienThoai: '02773822433', email: 'info@imexpharm.com', diaChi: '04 Đường 30/4, TP.Cao Lãnh, Đồng Tháp', trangThai: true, ghiChu: 'Kháng sinh, vitamin'});
CREATE (ncc3:NhaCungCap {maNhaCungCap: 'NCC003', tenNhaCungCap: 'Công ty Pymepharco', soDienThoai: '02573824225', email: 'info@pymepharco.com', diaChi: '166-170 Nguyễn Huệ, TP.Tuy Hòa, Phú Yên', trangThai: true, ghiChu: 'Thuốc đặc trị'});
CREATE (ncc4:NhaCungCap {maNhaCungCap: 'NCC004', tenNhaCungCap: 'Công ty Traphaco', soDienThoai: '02438533388', email: 'info@traphaco.com.vn', diaChi: '75 Yên Ninh, Ba Đình, Hà Nội', trangThai: true, ghiChu: 'Đông dược'});
CREATE (ncc5:NhaCungCap {maNhaCungCap: 'NCC005', tenNhaCungCap: 'Công ty OPC Pharma', soDienThoai: '02838460318', email: 'info@opcpharma.com', diaChi: '1017 Hồng Bàng, Q.6, TP.HCM', trangThai: true, ghiChu: 'Thực phẩm chức năng'});

// ============================================================
// 6. TẠO NODE: KeThuoc
// ============================================================
CREATE (ke1:KeThuoc {maKe: 'KE01', viTri: 1, loaiKe: 'Thuốc giảm đau - hạ sốt'});
CREATE (ke2:KeThuoc {maKe: 'KE02', viTri: 2, loaiKe: 'Kháng sinh'});
CREATE (ke3:KeThuoc {maKe: 'KE03', viTri: 3, loaiKe: 'Thuốc ho - cảm cúm'});
CREATE (ke4:KeThuoc {maKe: 'KE04', viTri: 4, loaiKe: 'Vitamin - Khoáng chất'});
CREATE (ke5:KeThuoc {maKe: 'KE05', viTri: 5, loaiKe: 'Thuốc tiêu hóa'});
CREATE (ke6:KeThuoc {maKe: 'KE06', viTri: 6, loaiKe: 'Thuốc tim mạch'});
CREATE (ke7:KeThuoc {maKe: 'KE07', viTri: 7, loaiKe: 'Thuốc da liễu'});
CREATE (ke8:KeThuoc {maKe: 'KE08', viTri: 8, loaiKe: 'Thuốc mắt - tai mũi họng'});

// ============================================================
// 7. TẠO NODE: Thue
// ============================================================
CREATE (th1:Thue {maThue: 'TH001', tiLe: 0.05, moTa: 'Thuế VAT 5%', ngayApDung: '2024-01-01'});
CREATE (th2:Thue {maThue: 'TH002', tiLe: 0.08, moTa: 'Thuế VAT 8%', ngayApDung: '2024-01-01'});
CREATE (th3:Thue {maThue: 'TH003', tiLe: 0.0, moTa: 'Miễn thuế', ngayApDung: '2024-01-01'});

// ============================================================
// 8. TẠO NODE: KhuyenMai
// ============================================================
CREATE (km1:KhuyenMai {maKM: 'KM01', tenChuongTrinh: 'Giảm giá mùa hè', giaTri: 10.0, ngayBatDau: '2026-06-01', ngayKetThuc: '2026-08-31', soLuongToiDa: 100, trangThai: 1});
CREATE (km2:KhuyenMai {maKM: 'KM02', tenChuongTrinh: 'Khuyến mãi Tết', giaTri: 15.0, ngayBatDau: '2026-01-15', ngayKetThuc: '2026-02-15', soLuongToiDa: 200, trangThai: 0});
CREATE (km3:KhuyenMai {maKM: 'KM03', tenChuongTrinh: 'Ưu đãi khách hàng thân thiết', giaTri: 5.0, ngayBatDau: '2026-01-01', ngayKetThuc: '2026-12-31', soLuongToiDa: 500, trangThai: 1});
CREATE (km4:KhuyenMai {maKM: 'KM04', tenChuongTrinh: 'Flash Sale cuối tuần', giaTri: 20.0, ngayBatDau: '2026-05-01', ngayKetThuc: '2026-05-31', soLuongToiDa: 50, trangThai: 1});
