package entity;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.Objects;

public class KhuyenMai implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maKM;
    private String tenChuongTrinh;
    private double giaTri; // %
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private int soLuongToiDa;
    private int trangThai; // SỬA THÀNH INT (1: Đang diễn ra, 0: Đã kết thúc)

    public KhuyenMai() {
    }
    
    public KhuyenMai(String maKM) {
        this.maKM = maKM;
    }

    // SỬA LẠI CONSTRUCTOR: Bỏ dieuKienApDung/phamViApDung, dùng int trangThai
    public KhuyenMai(String maKM, String tenChuongTrinh, double giaTri, LocalDate ngayBatDau, LocalDate ngayKetThuc, int soLuongToiDa, int trangThai) {
        this.maKM = maKM;
        this.tenChuongTrinh = tenChuongTrinh;
        this.giaTri = giaTri;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuongToiDa = soLuongToiDa;
        this.trangThai = trangThai;
    }

    // Getters and Setters
    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getTenChuongTrinh() {
        return tenChuongTrinh;
    }

    public void setTenChuongTrinh(String tenChuongTrinh) {
        this.tenChuongTrinh = tenChuongTrinh;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
    
    public int getSoLuongToiDa() {
        return soLuongToiDa;
    }

    public void setSoLuongToiDa(int soLuongToiDa) {
        this.soLuongToiDa = soLuongToiDa;
    }

    // SỬA LẠI GETTER/SETTER CHO TRẠNG THÁI
    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    // (Giữ nguyên hashCode, equals, toString)
    @Override
    public int hashCode() {
        return Objects.hash(maKM);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KhuyenMai other = (KhuyenMai) obj;
        return Objects.equals(maKM, other.maKM);
    }

    @Override
    public String toString() {
        return tenChuongTrinh;
    }
}
