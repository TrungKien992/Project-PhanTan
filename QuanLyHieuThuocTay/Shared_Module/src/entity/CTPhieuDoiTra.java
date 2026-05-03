package entity;
import java.io.Serializable;

import java.util.Objects;

public class CTPhieuDoiTra implements Serializable {
    private static final long serialVersionUID = 1L;
    private PhieuDoiTra phieuDoiTra;
    private Thuoc thuoc;
    private int soLuong;

    public CTPhieuDoiTra() {
    }

    public CTPhieuDoiTra(PhieuDoiTra phieuDoiTra, Thuoc thuoc, int soLuong) {
        this.phieuDoiTra = phieuDoiTra;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
    }

    public PhieuDoiTra getPhieuDoiTra() {
        return phieuDoiTra;
    }

    public void setPhieuDoiTra(PhieuDoiTra phieuDoiTra) {
        this.phieuDoiTra = phieuDoiTra;
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

    @Override
    public int hashCode() {
        return Objects.hash(phieuDoiTra, thuoc);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CTPhieuDoiTra other = (CTPhieuDoiTra) obj;
        return Objects.equals(phieuDoiTra, other.phieuDoiTra) &&
                Objects.equals(thuoc, other.thuoc);
    }

    @Override
    public String toString() {
        return "CTPhieuDoiTra{" +
                "phieuDoiTra=" + phieuDoiTra +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                '}';
    }
}

