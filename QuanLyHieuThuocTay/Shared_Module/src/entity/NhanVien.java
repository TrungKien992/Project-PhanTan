package entity;
import java.io.Serializable;

import java.time.LocalDate;

public class NhanVien implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maNV;
    private String tenNV;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private ChucVu chucVu;
    private String soDienThoai;
    private String diaChi;
    private String anh;
    private String trangThai; // Đã có sẵn field này
    private TaiKhoan taiKhoan;

    // Constructor 1 tham số (giữ nguyên)
    public NhanVien(String maNV) {
        super();
        this.maNV = maNV;
    }

    // Constructor đầy đủ tham số (Đã thêm trangThai)
    public NhanVien(String maNV, String tenNV, LocalDate ngaySinh, String gioiTinh, ChucVu chucVu,
            String soDienThoai, String diaChi, String anh, String trangThai, TaiKhoan taiKhoan) {
        super();
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.chucVu = chucVu;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.anh = anh;
        this.trangThai = trangThai; // Cập nhật trạng thái
        this.taiKhoan = taiKhoan;
    }

    // Constructor mặc định (giữ nguyên)
    public NhanVien() {
        super();
    }

    // --- Các Getter và Setter ---
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    public String getTenNV() {
        return tenNV;
    }
    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    public String getGioiTinh() {
        return gioiTinh;
    }
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    public ChucVu getChucVu() {
        return chucVu;
    }
    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }
    public String getSoDienThoai() {
        return soDienThoai;
    }
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public String getDiaChi() {
        return diaChi;
    }
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    public String getAnh() {
        return anh;
    }
    public void setAnh(String anh) {
        this.anh = anh;
    }
    public String getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }
    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    // toString đã cập nhật thêm trangThai
    @Override
    public String toString() {
        return maNV + ";" + tenNV + ";" + ngaySinh + ";" + gioiTinh + ";" + chucVu + ";" 
               + soDienThoai + ";" + diaChi + ";" + anh + ";" + trangThai + ";" + taiKhoan;
    }
}
