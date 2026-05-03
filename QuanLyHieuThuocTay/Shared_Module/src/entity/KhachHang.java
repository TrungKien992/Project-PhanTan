package entity;

import java.util.Objects;

import java.io.Serializable;

public class KhachHang implements Serializable {
    private static final long serialVersionUID = 1L;

    private String maKH;
    private String tenKH;
    private String diaChi;
    private String soDienThoai;
    private boolean trangThai; // THÊM TRƯỜNG TRẠNG THÁI (true=1, false=0)

    public KhachHang() {
    }

    public KhachHang(String maKH) {
        this.maKH = maKH;
    }

    // Cập nhật Constructor full tham số
    public KhachHang(String maKH, String tenKH, String soDienThoai, String diaChi, boolean trangThai) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDienThoai = soDienThoai;
        this.diaChi = diaChi;
        this.trangThai = trangThai; // THÊM TRẠNG THÁI
    }
    
    // Constructor cũ (giữ lại và set mặc định trạng thái là true)
    public KhachHang(String maKH, String tenKH, String soDienThoai,String diaChi) {
        this(maKH, tenKH, soDienThoai, diaChi, true); 
    }

    // Getters and Setters
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    // THÊM GETTER VÀ SETTER CHO TRẠNG THÁI
    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKH);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KhachHang other = (KhachHang) obj;
        return Objects.equals(maKH, other.maKH);
    }

    @Override
    public String toString() {
        // Có thể thêm trạng thái vào toString nếu cần
        return tenKH;
    }
}