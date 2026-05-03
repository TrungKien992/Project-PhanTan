package entity;
import java.io.Serializable;

import java.util.Objects;

public class ChiTietPhieuCho implements Serializable {
    private static final long serialVersionUID = 1L;
    private PhieuChoThanhToan phieuChoThanhToan;
    private Thuoc thuoc;
    private int soLuong;
    private double donGia; // Giá bán tại thời điểm lập phiếu
    
    // Constructors
    public ChiTietPhieuCho() {
    }

    public ChiTietPhieuCho(PhieuChoThanhToan phieuChoThanhToan, Thuoc thuoc, int soLuong, double donGia) {
        this.phieuChoThanhToan = phieuChoThanhToan;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    // Getters and Setters
    public PhieuChoThanhToan getPhieuChoThanhToan() {
        return phieuChoThanhToan;
    }

    public void setPhieuChoThanhToan(PhieuChoThanhToan phieuChoThanhToan) {
        this.phieuChoThanhToan = phieuChoThanhToan;
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

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    
    // Cột tính toán
    public double getThanhTien() {
        return this.soLuong * this.donGia;
    }

    // hashCode and equals (dựa trên cả mã phiếu chờ và mã thuốc)
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.phieuChoThanhToan);
        hash = 97 * hash + Objects.hashCode(this.thuoc);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChiTietPhieuCho other = (ChiTietPhieuCho) obj;
        if (!Objects.equals(this.phieuChoThanhToan, other.phieuChoThanhToan)) {
            return false;
        }
        return Objects.equals(this.thuoc, other.thuoc);
    }
}
