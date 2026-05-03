package entity;

import java.time.LocalDate;
import java.util.Objects;

import java.io.Serializable;

public class Thuoc implements Serializable {
    private static final long serialVersionUID = 1L;

    private String maThuoc;
    private String tenThuoc;
    private double giaNhap; 
    private double giaBan;
    private int soLuong; 
    private LocalDate hanSuDung;
    private String thanhPhan;
    private String donViTinh;
    private String anh; 
    private KeThuoc keThuoc; 
    private NhaCungCap nhaCungCap; 
    private String trangThai; // THÊM TRƯỜNG TRẠNG THÁI

    public Thuoc() {
    }
    
    public Thuoc(String maThuoc) {
    	this.maThuoc = maThuoc;
    }

    // Cập nhật Constructor full tham số (Thêm trangThai)
    public Thuoc(String maThuoc, String tenThuoc, double giaNhap, double giaBan, int soLuong, LocalDate hanSuDung,
            String thanhPhan, String donViTinh, String anh, KeThuoc keThuoc, NhaCungCap nhaCungCap, String trangThai) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.hanSuDung = hanSuDung;
        this.thanhPhan = thanhPhan;
        this.donViTinh = donViTinh;
        this.anh = anh;
        this.keThuoc = keThuoc;
        this.nhaCungCap = nhaCungCap;
        this.trangThai = trangThai; // Gán trạng thái
    }
    
    // Constructor cũ (dùng khi thêm, mặc định trạng thái là "Đang kinh doanh")
     public Thuoc(String maThuoc, String tenThuoc, double giaNhap, double giaBan, int soLuong, LocalDate hanSuDung,
            String thanhPhan, String donViTinh, String anh, KeThuoc keThuoc, NhaCungCap nhaCungCap) {
        this(maThuoc, tenThuoc, giaNhap, giaBan, soLuong, hanSuDung, thanhPhan, donViTinh, anh, keThuoc, nhaCungCap, "Đang kinh doanh");
    }

    /**
	 * @return the trangThai
	 */
	public String getTrangThai() {
		return trangThai;
	}

	/**
	 * @param trangThai the trangThai to set
	 */
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	// Getters and Setters đầy đủ
    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public KeThuoc getKeThuoc() {
        return keThuoc;
    }

    public void setKeThuoc(KeThuoc keThuoc) {
        this.keThuoc = keThuoc;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maThuoc);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Thuoc other = (Thuoc) obj;
        return Objects.equals(maThuoc, other.maThuoc);
    }

    @Override
    public String toString() {
        return tenThuoc;
    }
}