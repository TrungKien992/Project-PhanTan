package entity;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class HoaDon implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maHD;
    private LocalDate ngayLap;
    private KhuyenMai khuyenMai; // Có thể null
    private Thue thue;
    private NhanVien nhanVien;
    private KhachHang khachHang; // Có thể null
    private List<ChiTietHoaDon> danhSachChiTiet; // Để lưu các chi tiết hóa đơn
    private double tienKhachDua;
    private double tienThua;
    private double tongCong;

    public HoaDon() {
    }
    public HoaDon(String maHD) {
        this.maHD = maHD;
    }

    public HoaDon(String maHD, LocalDate ngayLap, NhanVien nhanVien, KhachHang khachHang, Thue thue) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.thue = thue;
        // Khởi tạo giá trị mặc định
        this.tienKhachDua = 0.0;
        this.tienThua = 0.0;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public LocalDate getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(LocalDate ngayLap) {
        this.ngayLap = ngayLap;
    }

    public KhuyenMai getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(KhuyenMai khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public Thue getThue() {
        return thue;
    }

    public void setThue(Thue thue) {
        this.thue = thue;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public List<ChiTietHoaDon> getDanhSachChiTiet() {
        return danhSachChiTiet;
    }

    public void setDanhSachChiTiet(List<ChiTietHoaDon> danhSachChiTiet) {
        this.danhSachChiTiet = danhSachChiTiet;
    }

    // === THÊM MỚI GETTERS/SETTERS (Yêu cầu 1) ===
    public double getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(double tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public double getTienThua() {
        return tienThua;
    }

    public void setTienThua(double tienThua) {
        this.tienThua = tienThua;
    }

    public double getTongCong() {
        return tongCong;
    }

    public void setTongCong(double tongCong) {
        this.tongCong = tongCong;
    }

    // Phương thức tính tổng tiền cũ giữ nguyên
    public double tinhTongTien() {
        double tongTien = 0;
        if (danhSachChiTiet != null) {
            for (ChiTietHoaDon cthd : danhSachChiTiet) {
                tongTien += cthd.tinhThanhTien();
            }
        }

        // Áp dụng thuế
        if (thue != null) {
            tongTien = tongTien * (1 + thue.getTiLe());
        }

        // Áp dụng khuyến mãi
        if (khuyenMai != null) {
            tongTien = tongTien * (1 - khuyenMai.getGiaTri() / 100);
        }

        return tongTien;
    }


    // hashCode() và equals() giữ nguyên
    @Override
    public int hashCode() {
        return Objects.hash(maHD);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HoaDon other = (HoaDon) obj;
        return Objects.equals(maHD, other.maHD);
    }
}
