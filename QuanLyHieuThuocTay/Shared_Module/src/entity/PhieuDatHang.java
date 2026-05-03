package entity;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;

public class PhieuDatHang implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maPhieu;
    private NhaCungCap nhaCungCap;
    private NhanVien nhanVien; 
    private LocalDate ngayDat;
    private double tongTien;
    private String trangThai;
    private String ghiChu;
    private List<ChiTietPhieuDatHang> chiTietList; 

    // Constructors
    public PhieuDatHang() {}

    // Cập nhật constructor đầy đủ
    public PhieuDatHang(String maPhieu, NhaCungCap nhaCungCap, NhanVien nhanVien, LocalDate ngayDat, 
                        double tongTien, String trangThai, String ghiChu) {
        this.maPhieu = maPhieu;
        this.nhaCungCap = nhaCungCap;
        this.nhanVien = nhanVien; 
        this.ngayDat = ngayDat;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getters and Setters
    public String getMaPhieu() { return maPhieu; }
    public void setMaPhieu(String maPhieu) { this.maPhieu = maPhieu; }
    public NhaCungCap getNhaCungCap() { return nhaCungCap; }
    public void setNhaCungCap(NhaCungCap nhaCungCap) { this.nhaCungCap = nhaCungCap; }
    
    // Getter/Setter cho nhanVien
    public NhanVien getNhanVien() { return nhanVien; } 
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; } 
    
    public LocalDate getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDate ngayDat) { this.ngayDat = ngayDat; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public List<ChiTietPhieuDatHang> getChiTietList() { return chiTietList; }
    public void setChiTietList(List<ChiTietPhieuDatHang> chiTietList) { this.chiTietList = chiTietList; }
}
