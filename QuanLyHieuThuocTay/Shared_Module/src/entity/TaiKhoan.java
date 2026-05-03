package entity;
import java.io.Serializable;

public class TaiKhoan implements Serializable {
    private static final long serialVersionUID = 1L;
	private String maTK;
	private String tenTK;
	private String matKhau;
	private String quyenHan;
	private boolean trangThai; 
	
	public String getMaTK() {
		return maTK;
	}
	public void setMaTK(String maTK) {
		this.maTK = maTK;
	}
	public String getTenTK() {
		return tenTK;
	}
	public void setTenTK(String tenTK) {
		this.tenTK = tenTK;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public String getQuyenHan() {
		return quyenHan;
	}
	public void setQuyenHan(String quyenHan) {
		this.quyenHan = quyenHan;
	}
	
	public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }
	
	public TaiKhoan(String maTK, String tenTK, String matKhau, String quyenHan,boolean trangThai) {
		super();
		this.maTK = maTK;
		this.tenTK = tenTK;
		this.matKhau = matKhau;
		this.quyenHan = quyenHan;
		this.trangThai = trangThai;
	}
	
	public TaiKhoan() {
		super();
	}
	@Override
	public String toString() {
		return tenTK;
	}
	
	
}

