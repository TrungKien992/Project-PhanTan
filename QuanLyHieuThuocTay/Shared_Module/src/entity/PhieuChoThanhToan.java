package entity;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PhieuChoThanhToan implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maPhieuCho;
    private KhachHang khachHang; // Mã KH (nếu có)
    private String tenKH;        // Tên KH
    private String sdtKH;        // SĐT KH
    private NhanVien nhanVienLap;  // Mã Nhân Viên lập phiếu
    private LocalDate ngayLap;
    private double tongTienHang;
    private double thueVAT;
    private double tongCong;

    // Sẽ cần thêm 1 list chi tiết phiếu chờ để dễ thao tác
    private List<ChiTietPhieuCho> dsChiTietPhieuCho;

    // Constructors
    public PhieuChoThanhToan() {
    }

    public PhieuChoThanhToan(String maPhieuCho) {
        this.maPhieuCho = maPhieuCho;
    }
    
    // Constructor đầy đủ (trừ list chi tiết)
    public PhieuChoThanhToan(String maPhieuCho, KhachHang khachHang, String tenKH, String sdtKH, NhanVien nhanVienLap, LocalDate ngayLap, double tongTienHang, double thueVAT, double tongCong) {
        this.maPhieuCho = maPhieuCho;
        this.khachHang = khachHang;
        this.tenKH = tenKH;
        this.sdtKH = sdtKH;
        this.nhanVienLap = nhanVienLap;
        this.ngayLap = ngayLap;
        this.tongTienHang = tongTienHang;
        this.thueVAT = thueVAT;
        this.tongCong = tongCong;
    }

    // Getters and Setters
    public String getMaPhieuCho() {
        return maPhieuCho;
    }

    public void setMaPhieuCho(String maPhieuCho) {
        this.maPhieuCho = maPhieuCho;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public NhanVien getNhanVienLap() {
        return nhanVienLap;
    }

    public void setNhanVienLap(NhanVien nhanVienLap) {
        this.nhanVienLap = nhanVienLap;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public double getTongTienHang() {
        return tongTienHang;
    }

    public void setTongTienHang(double tongTienHang) {
        this.tongTienHang = tongTienHang;
    }

    public double getThueVAT() {
        return thueVAT;
    }

    public void setThueVAT(double thueVAT) {
        this.thueVAT = thueVAT;
    }

    public double getTongCong() {
        return tongCong;
    }

    public void setTongCong(double tongCong) {
        this.tongCong = tongCong;
    }

    public List<ChiTietPhieuCho> getDsChiTietPhieuCho() {
        return dsChiTietPhieuCho;
    }

    public void setDsChiTietPhieuCho(List<ChiTietPhieuCho> dsChiTietPhieuCho) {
        this.dsChiTietPhieuCho = dsChiTietPhieuCho;
    }

    // hashCode and equals (chỉ dựa trên maPhieuCho)
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.maPhieuCho);
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
        final PhieuChoThanhToan other = (PhieuChoThanhToan) obj;
        return Objects.equals(this.maPhieuCho, other.maPhieuCho);
    }
}
