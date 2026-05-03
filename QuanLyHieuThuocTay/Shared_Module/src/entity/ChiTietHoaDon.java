package entity;
import java.io.Serializable;

import java.util.Objects;

public class ChiTietHoaDon implements Serializable {
    private static final long serialVersionUID = 1L;
    private HoaDon hoaDon;
    private Thuoc thuoc;
    private int soLuong;
    
    // --- THÊM MỚI: Biến đơn giá để lưu vào CSDL ---
    private double donGia; 

    public ChiTietHoaDon() {
    }

    // --- GIỮ NGUYÊN: Constructor cũ của Đại Ca ---
    public ChiTietHoaDon(HoaDon hoaDon, Thuoc thuoc, int soLuong) {
        this.hoaDon = hoaDon;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
    }

    // --- THÊM MỚI: Constructor đầy đủ 4 tham số (Dùng để sửa lỗi DB) ---
    public ChiTietHoaDon(HoaDon hoaDon, Thuoc thuoc, int soLuong, double donGia) {
        this.hoaDon = hoaDon;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getters and Setters
    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    // --- THÊM MỚI: Getter và Setter cho đơn giá ---
    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    
    // --- GIỮ NGUYÊN: Phương thức tính thành tiền cũ của Đại Ca ---
    public double tinhThanhTien() {
        if(thuoc != null) {
            return thuoc.getGiaBan() * soLuong;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hoaDon, thuoc);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChiTietHoaDon other = (ChiTietHoaDon) obj;
        return Objects.equals(hoaDon, other.hoaDon) && Objects.equals(thuoc, other.thuoc);
    }
}
