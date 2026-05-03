// ============================================================
// PHẦN 2: THUỐC + HÓA ĐƠN + PHIẾU ĐẶT HÀNG + PHIẾU ĐỔI TRẢ
// ============================================================

// ============================================================
// 9. TẠO NODE: Thuoc + RELATIONSHIPS (THUOC_TAI, CUNG_CAP_BOI)
// ============================================================
CREATE (t:Thuoc {maThuoc: 'T01', tenThuoc: 'Paracetamol 500mg', giaNhap: 1500, giaBan: 3000, soLuong: 200, hanSuDung: '2027-06-15', thanhPhan: 'Paracetamol', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T02', tenThuoc: 'Amoxicillin 500mg', giaNhap: 2000, giaBan: 4500, soLuong: 150, hanSuDung: '2027-03-20', thanhPhan: 'Amoxicillin trihydrate', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T03', tenThuoc: 'Vitamin C 1000mg', giaNhap: 3000, giaBan: 6000, soLuong: 300, hanSuDung: '2027-12-01', thanhPhan: 'Acid ascorbic', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T04', tenThuoc: 'Thuốc ho Bảo Thanh', giaNhap: 15000, giaBan: 28000, soLuong: 80, hanSuDung: '2027-09-10', thanhPhan: 'Mật ong, tỳ bà diệp', donViTinh: 'Chai', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T05', tenThuoc: 'Omeprazol 20mg', giaNhap: 1800, giaBan: 4000, soLuong: 120, hanSuDung: '2027-05-25', thanhPhan: 'Omeprazol', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T06', tenThuoc: 'Ibuprofen 400mg', giaNhap: 1200, giaBan: 2500, soLuong: 250, hanSuDung: '2027-08-30', thanhPhan: 'Ibuprofen', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T07', tenThuoc: 'Cetirizin 10mg', giaNhap: 800, giaBan: 2000, soLuong: 180, hanSuDung: '2027-04-18', thanhPhan: 'Cetirizin dihydrochlorid', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T08', tenThuoc: 'Amlodipine 5mg', giaNhap: 2500, giaBan: 5000, soLuong: 100, hanSuDung: '2027-07-22', thanhPhan: 'Amlodipine besilat', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T09', tenThuoc: 'Kem bôi Gentrisone', giaNhap: 18000, giaBan: 35000, soLuong: 60, hanSuDung: '2027-02-14', thanhPhan: 'Gentamicin, Clotrimazol', donViTinh: 'Tuýp', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T10', tenThuoc: 'Nhỏ mắt Natri Clorid 0.9%', giaNhap: 5000, giaBan: 10000, soLuong: 90, hanSuDung: '2027-01-05', thanhPhan: 'Natri clorid', donViTinh: 'Lọ', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T11', tenThuoc: 'Azithromycin 250mg', giaNhap: 3500, giaBan: 7000, soLuong: 130, hanSuDung: '2027-11-11', thanhPhan: 'Azithromycin', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T12', tenThuoc: 'Multivitamin Centrum', giaNhap: 5000, giaBan: 9500, soLuong: 200, hanSuDung: '2028-01-15', thanhPhan: 'Vitamin A,B,C,D,E, khoáng chất', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T13', tenThuoc: 'Thuốc cảm Tiffy', giaNhap: 1000, giaBan: 2200, soLuong: 300, hanSuDung: '2027-10-20', thanhPhan: 'Paracetamol, Chlorpheniramine', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T14', tenThuoc: 'Smecta', giaNhap: 4000, giaBan: 8000, soLuong: 70, hanSuDung: '2027-06-30', thanhPhan: 'Diosmectite', donViTinh: 'Gói', anh: '', trangThai: 'Đang kinh doanh'});
CREATE (t:Thuoc {maThuoc: 'T15', tenThuoc: 'Aspirin 81mg', giaNhap: 900, giaBan: 2000, soLuong: 5, hanSuDung: '2026-06-10', thanhPhan: 'Acid acetylsalicylic', donViTinh: 'Viên', anh: '', trangThai: 'Đang kinh doanh'});

// Thuoc -> KeThuoc
MATCH (t:Thuoc {maThuoc:'T01'}),(k:KeThuoc {maKe:'KE01'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T02'}),(k:KeThuoc {maKe:'KE02'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T03'}),(k:KeThuoc {maKe:'KE04'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T04'}),(k:KeThuoc {maKe:'KE03'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T05'}),(k:KeThuoc {maKe:'KE05'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T06'}),(k:KeThuoc {maKe:'KE01'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T07'}),(k:KeThuoc {maKe:'KE03'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T08'}),(k:KeThuoc {maKe:'KE06'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T09'}),(k:KeThuoc {maKe:'KE07'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T10'}),(k:KeThuoc {maKe:'KE08'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T11'}),(k:KeThuoc {maKe:'KE02'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T12'}),(k:KeThuoc {maKe:'KE04'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T13'}),(k:KeThuoc {maKe:'KE03'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T14'}),(k:KeThuoc {maKe:'KE05'}) CREATE (t)-[:THUOC_TAI]->(k);
MATCH (t:Thuoc {maThuoc:'T15'}),(k:KeThuoc {maKe:'KE06'}) CREATE (t)-[:THUOC_TAI]->(k);

// Thuoc -> NhaCungCap
MATCH (t:Thuoc {maThuoc:'T01'}),(n:NhaCungCap {maNhaCungCap:'NCC001'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T02'}),(n:NhaCungCap {maNhaCungCap:'NCC002'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T03'}),(n:NhaCungCap {maNhaCungCap:'NCC001'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T04'}),(n:NhaCungCap {maNhaCungCap:'NCC004'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T05'}),(n:NhaCungCap {maNhaCungCap:'NCC003'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T06'}),(n:NhaCungCap {maNhaCungCap:'NCC002'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T07'}),(n:NhaCungCap {maNhaCungCap:'NCC003'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T08'}),(n:NhaCungCap {maNhaCungCap:'NCC003'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T09'}),(n:NhaCungCap {maNhaCungCap:'NCC005'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T10'}),(n:NhaCungCap {maNhaCungCap:'NCC001'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T11'}),(n:NhaCungCap {maNhaCungCap:'NCC002'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T12'}),(n:NhaCungCap {maNhaCungCap:'NCC005'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T13'}),(n:NhaCungCap {maNhaCungCap:'NCC004'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T14'}),(n:NhaCungCap {maNhaCungCap:'NCC001'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);
MATCH (t:Thuoc {maThuoc:'T15'}),(n:NhaCungCap {maNhaCungCap:'NCC003'}) CREATE (t)-[:CUNG_CAP_BOI]->(n);

// ============================================================
// 10. TẠO NODE: HoaDon + RELATIONSHIPS
// ============================================================
// Hóa đơn 1 - Có khách hàng, có khuyến mãi
MATCH (nv:NhanVien {maNV:'NV002'}),(th:Thue {maThue:'TH001'}),(kh:KhachHang {maKH:'KH001'}),(km:KhuyenMai {maKM:'KM03'})
CREATE (hd:HoaDon {maHD:'HD001', ngayLap:'2026-05-01', tienKhachDua:50000, tienThua:4250, tongCong:45750})
CREATE (hd)-[:LAP_BOI]->(nv)
CREATE (hd)-[:AP_DUNG_THUE]->(th)
CREATE (hd)-[:MUA_BOI]->(kh)
CREATE (hd)-[:SU_DUNG_KM]->(km);

// Chi tiết HD001
MATCH (hd:HoaDon {maHD:'HD001'}),(t:Thuoc {maThuoc:'T01'}) CREATE (hd)-[:HAS_DETAIL {soLuong:5, donGia:3000}]->(t);
MATCH (hd:HoaDon {maHD:'HD001'}),(t:Thuoc {maThuoc:'T03'}) CREATE (hd)-[:HAS_DETAIL {soLuong:3, donGia:6000}]->(t);

// Hóa đơn 2 - Khách vãng lai, không KM
MATCH (nv:NhanVien {maNV:'NV003'}),(th:Thue {maThue:'TH001'})
CREATE (hd:HoaDon {maHD:'HD002', ngayLap:'2026-05-01', tienKhachDua:100000, tienThua:62500, tongCong:37500})
CREATE (hd)-[:LAP_BOI]->(nv)
CREATE (hd)-[:AP_DUNG_THUE]->(th);

MATCH (hd:HoaDon {maHD:'HD002'}),(t:Thuoc {maThuoc:'T02'}) CREATE (hd)-[:HAS_DETAIL {soLuong:5, donGia:4500}]->(t);
MATCH (hd:HoaDon {maHD:'HD002'}),(t:Thuoc {maThuoc:'T06'}) CREATE (hd)-[:HAS_DETAIL {soLuong:4, donGia:2500}]->(t);

// Hóa đơn 3
MATCH (nv:NhanVien {maNV:'NV002'}),(th:Thue {maThue:'TH001'}),(kh:KhachHang {maKH:'KH002'})
CREATE (hd:HoaDon {maHD:'HD003', ngayLap:'2026-05-02', tienKhachDua:200000, tienThua:113400, tongCong:86600})
CREATE (hd)-[:LAP_BOI]->(nv)
CREATE (hd)-[:AP_DUNG_THUE]->(th)
CREATE (hd)-[:MUA_BOI]->(kh);

MATCH (hd:HoaDon {maHD:'HD003'}),(t:Thuoc {maThuoc:'T04'}) CREATE (hd)-[:HAS_DETAIL {soLuong:2, donGia:28000}]->(t);
MATCH (hd:HoaDon {maHD:'HD003'}),(t:Thuoc {maThuoc:'T05'}) CREATE (hd)-[:HAS_DETAIL {soLuong:3, donGia:4000}]->(t);
MATCH (hd:HoaDon {maHD:'HD003'}),(t:Thuoc {maThuoc:'T12'}) CREATE (hd)-[:HAS_DETAIL {soLuong:2, donGia:9500}]->(t);

// Hóa đơn 4
MATCH (nv:NhanVien {maNV:'NV003'}),(th:Thue {maThue:'TH001'}),(kh:KhachHang {maKH:'KH003'})
CREATE (hd:HoaDon {maHD:'HD004', ngayLap:'2026-05-02', tienKhachDua:50000, tienThua:20750, tongCong:29250})
CREATE (hd)-[:LAP_BOI]->(nv)
CREATE (hd)-[:AP_DUNG_THUE]->(th)
CREATE (hd)-[:MUA_BOI]->(kh);

MATCH (hd:HoaDon {maHD:'HD004'}),(t:Thuoc {maThuoc:'T13'}) CREATE (hd)-[:HAS_DETAIL {soLuong:10, donGia:2200}]->(t);
MATCH (hd:HoaDon {maHD:'HD004'}),(t:Thuoc {maThuoc:'T07'}) CREATE (hd)-[:HAS_DETAIL {soLuong:3, donGia:2000}]->(t);

// Hóa đơn 5
MATCH (nv:NhanVien {maNV:'NV002'}),(th:Thue {maThue:'TH001'}),(kh:KhachHang {maKH:'KH004'}),(km:KhuyenMai {maKM:'KM04'})
CREATE (hd:HoaDon {maHD:'HD005', ngayLap:'2026-05-03', tienKhachDua:100000, tienThua:52300, tongCong:47700})
CREATE (hd)-[:LAP_BOI]->(nv)
CREATE (hd)-[:AP_DUNG_THUE]->(th)
CREATE (hd)-[:MUA_BOI]->(kh)
CREATE (hd)-[:SU_DUNG_KM]->(km);

MATCH (hd:HoaDon {maHD:'HD005'}),(t:Thuoc {maThuoc:'T08'}) CREATE (hd)-[:HAS_DETAIL {soLuong:5, donGia:5000}]->(t);
MATCH (hd:HoaDon {maHD:'HD005'}),(t:Thuoc {maThuoc:'T09'}) CREATE (hd)-[:HAS_DETAIL {soLuong:1, donGia:35000}]->(t);

// ============================================================
// 11. TẠO NODE: PhieuDatHang + RELATIONSHIPS
// ============================================================
MATCH (nv:NhanVien {maNV:'NV005'}),(ncc:NhaCungCap {maNhaCungCap:'NCC001'})
CREATE (p:PhieuDatHang {maPhieu:'PDH001', ngayDat:'2026-04-25', ghiChu:'Đặt hàng định kỳ', trangThai:'Đã nhận', tongTien:750000})
CREATE (p)-[:LAP_BOI]->(nv)
CREATE (p)-[:DAT_TU]->(ncc);

MATCH (p:PhieuDatHang {maPhieu:'PDH001'}),(t:Thuoc {maThuoc:'T01'}) CREATE (p)-[:HAS_DETAIL {soLuong:200, donGia:1500}]->(t);
MATCH (p:PhieuDatHang {maPhieu:'PDH001'}),(t:Thuoc {maThuoc:'T03'}) CREATE (p)-[:HAS_DETAIL {soLuong:100, donGia:3000}]->(t);

MATCH (nv:NhanVien {maNV:'NV005'}),(ncc:NhaCungCap {maNhaCungCap:'NCC002'})
CREATE (p:PhieuDatHang {maPhieu:'PDH002', ngayDat:'2026-04-28', ghiChu:'Bổ sung kháng sinh', trangThai:'Đang xử lý', tongTien:600000})
CREATE (p)-[:LAP_BOI]->(nv)
CREATE (p)-[:DAT_TU]->(ncc);

MATCH (p:PhieuDatHang {maPhieu:'PDH002'}),(t:Thuoc {maThuoc:'T02'}) CREATE (p)-[:HAS_DETAIL {soLuong:100, donGia:2000}]->(t);
MATCH (p:PhieuDatHang {maPhieu:'PDH002'}),(t:Thuoc {maThuoc:'T11'}) CREATE (p)-[:HAS_DETAIL {soLuong:80, donGia:3500}]->(t);

MATCH (nv:NhanVien {maNV:'NV001'}),(ncc:NhaCungCap {maNhaCungCap:'NCC003'})
CREATE (p:PhieuDatHang {maPhieu:'PDH003', ngayDat:'2026-05-01', ghiChu:'Đặt thuốc đặc trị', trangThai:'Chờ duyệt', tongTien:450000})
CREATE (p)-[:LAP_BOI]->(nv)
CREATE (p)-[:DAT_TU]->(ncc);

MATCH (p:PhieuDatHang {maPhieu:'PDH003'}),(t:Thuoc {maThuoc:'T05'}) CREATE (p)-[:HAS_DETAIL {soLuong:100, donGia:1800}]->(t);
MATCH (p:PhieuDatHang {maPhieu:'PDH003'}),(t:Thuoc {maThuoc:'T08'}) CREATE (p)-[:HAS_DETAIL {soLuong:50, donGia:2500}]->(t);

// ============================================================
// 12. TẠO NODE: PhieuDoiTra + RELATIONSHIPS
// ============================================================
MATCH (nv:NhanVien {maNV:'NV002'}),(hd:HoaDon {maHD:'HD002'})
CREATE (pdt:PhieuDoiTra {maPDT:'PDT001', ngayDoiTra:'2026-05-02', lyDo:'Thuốc bị hư hộp', loaiPhieu:'Trả hàng', tienHoanLai:9000})
CREATE (pdt)-[:LAP_BOI]->(nv)
CREATE (pdt)-[:DOI_TRA_CHO]->(hd);

MATCH (pdt:PhieuDoiTra {maPDT:'PDT001'}),(t:Thuoc {maThuoc:'T02'}) CREATE (pdt)-[:HAS_DETAIL {soLuong:2}]->(t);

MATCH (nv:NhanVien {maNV:'NV003'}),(hd:HoaDon {maHD:'HD004'})
CREATE (pdt:PhieuDoiTra {maPDT:'PDT002', ngayDoiTra:'2026-05-03', lyDo:'Khách mua nhầm loại', loaiPhieu:'Đổi hàng', tienHoanLai:0})
CREATE (pdt)-[:LAP_BOI]->(nv)
CREATE (pdt)-[:DOI_TRA_CHO]->(hd);

MATCH (pdt:PhieuDoiTra {maPDT:'PDT002'}),(t:Thuoc {maThuoc:'T13'}) CREATE (pdt)-[:HAS_DETAIL {soLuong:5}]->(t);
