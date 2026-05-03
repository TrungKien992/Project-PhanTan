package entity;
import java.io.Serializable;

import java.util.Objects;

public class KeThuoc implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maKe;
    private int viTri;
    private String loaiKe;

    public KeThuoc() {
    }

    public KeThuoc(String maKe) {
        this.maKe = maKe;
    }
    
    public KeThuoc(String maKe, String loaiKe) {
        this.maKe = maKe;
        this.loaiKe = loaiKe;
    }
    
    public KeThuoc(String maKe, int viTri, String loaiKe) {
        this.maKe = maKe;
        this.viTri = viTri;
        this.loaiKe = loaiKe;
    }

    // Getters and Setters
    public String getMaKe() {
        return maKe;
    }

    public void setMaKe(String maKe) {
        this.maKe = maKe;
    }

    public int getViTri() {
        return viTri;
    }

    public void setViTri(int viTri) {
        this.viTri = viTri;
    }

    public String getLoaiKe() {
        return loaiKe;
    }

    public void setLoaiKe(String loaiKe) {
        this.loaiKe = loaiKe;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKe);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeThuoc other = (KeThuoc) obj;
        return Objects.equals(maKe, other.maKe);
    }

    @Override
    public String toString() {
        // Giúp hiển thị tên kệ trong JComboBox dễ dàng hơn
        return loaiKe; 
    }
}
