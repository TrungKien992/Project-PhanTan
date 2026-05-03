package entity;
import java.io.Serializable;

import java.util.Objects;

public class NhaCungCap implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private boolean trangThai; // true: Đang hợp tác, false: Ngừng hợp tác
    private String ghiChu;
    
	public NhaCungCap() {
    }

    public NhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }
    
    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String soDienThoai, String email, String diaChi, boolean trangThai,String ghiChu) {
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

    // Getters and Setters
    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNhaCungCap);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NhaCungCap other = (NhaCungCap) obj;
        return Objects.equals(maNhaCungCap, other.maNhaCungCap);
    }

    @Override
    public String toString() {
        return tenNhaCungCap;
    }
}
