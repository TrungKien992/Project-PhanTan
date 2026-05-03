package entity;
import java.io.Serializable;

public class ChiTietPhieuDatHang implements Serializable {
    private static final long serialVersionUID = 1L;
    private PhieuDatHang phieuDatHang; // Tham chiếu phiếu đặt hàng chứa chi tiết này
    private Thuoc thuoc;             // Tham chiếu thuốc trong chi tiết này
    private int soLuong;
    private double donGia;
    private double thanhTien;         // Thường được tính = soLuong * donGia

    // --- Constructors ---
    public ChiTietPhieuDatHang() {
        // Constructor rỗng
    }

    public ChiTietPhieuDatHang(PhieuDatHang phieuDatHang, Thuoc thuoc, int soLuong, double donGia) {
        super();
        this.phieuDatHang = phieuDatHang;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
        // Tự động tính thành tiền khi tạo đối tượng
        this.thanhTien = tinhThanhTien(); 
    }

    // --- Getters and Setters ---
    public PhieuDatHang getPhieuDatHang() {
        return phieuDatHang;
    }

    public void setPhieuDatHang(PhieuDatHang phieuDatHang) {
        this.phieuDatHang = phieuDatHang;
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
        // Cập nhật lại thành tiền khi số lượng thay đổi
        this.thanhTien = tinhThanhTien(); 
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
        // Cập nhật lại thành tiền khi đơn giá thay đổi
        this.thanhTien = tinhThanhTien(); 
    }

    public double getThanhTien() {
        // Có thể tính toán lại ở đây nếu cần, nhưng tốt hơn là tính khi setSoLuong/setDonGia
        return thanhTien; 
    }
    
    // Không nên có setThanhTien trực tiếp, vì nó phụ thuộc vào số lượng và đơn giá
    // public void setThanhTien(double thanhTien) {
    //     this.thanhTien = thanhTien;
    // }

    // --- Phương thức tiện ích ---
    /**
     * Tính toán thành tiền dựa trên số lượng và đơn giá hiện tại.
     * @return Thành tiền (số lượng * đơn giá).
     */
    public double tinhThanhTien() {
        return this.soLuong * this.donGia;
    }

    // --- toString (Tùy chọn, dùng để debug) ---
    @Override
    public String toString() {
        return "ChiTietPhieuDatHang{" +
               "maPhieu=" + (phieuDatHang != null ? phieuDatHang.getMaPhieu() : "null") +
               ", maThuoc=" + (thuoc != null ? thuoc.getMaThuoc() : "null") +
               ", soLuong=" + soLuong +
               ", donGia=" + donGia +
               ", thanhTien=" + thanhTien +
               '}';
    }
}
