package entity;
import java.io.Serializable;

public class ChucVu implements Serializable {
    private static final long serialVersionUID = 1L;
	private String maChucVu;
	private String tenChucVu;
	private String moTa;
	
	public String getMaChucVu() {
		return maChucVu;
	}
	public void setMaChucVu(String maChucVu) {
		this.maChucVu = maChucVu;
	}
	public String getTenChucVu() {
		return tenChucVu;
	}
	public void setTenChucVu(String tenChucVu) {
		this.tenChucVu = tenChucVu;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	public ChucVu(String maChucVu, String tenChucVu, String moTa) {
		super();
		this.maChucVu = maChucVu;
		this.tenChucVu = tenChucVu;
		this.moTa = moTa;
	}
	
	public ChucVu() {
		super();
	}
	@Override
	public String toString() {
		return tenChucVu;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    ChucVu other = (ChucVu) obj;
	    return maChucVu != null && maChucVu.equals(other.maChucVu);
	}

	@Override
	public int hashCode() {
	    return maChucVu != null ? maChucVu.hashCode() : 0;
	}
}

