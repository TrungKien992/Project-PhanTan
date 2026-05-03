package entity;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.Objects;

public class PhieuDoiTra implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maPDT;
    private LocalDate ngayDoiTra;
    private String lyDo;
    private String loaiPhieu;
    private double tienHoanLai;
    private NhanVien nhanVien;
    private HoaDon hoaDon;

    public PhieuDoiTra() {
    }

    public PhieuDoiTra(String maPDT) {
        this.maPDT = maPDT;
    }

    

    public PhieuDoiTra(String maPDT, LocalDate ngayDoiTra, String lyDo, String loaiPhieu,
                       double tienHoanLai, NhanVien nhanVien, HoaDon hoaDon) {
        this.maPDT = maPDT;
        this.ngayDoiTra = ngayDoiTra;
        this.lyDo = lyDo;
        this.loaiPhieu = loaiPhieu;
        this.tienHoanLai = tienHoanLai;
        this.nhanVien = nhanVien;
        this.hoaDon = hoaDon;
    }

    public String getMaPDT() {
        return maPDT;
    }

    public void setMaPDT(String maPDT) {
        this.maPDT = maPDT;
    }

    public LocalDate getNgayDoiTra() {
        return ngayDoiTra;
    }

    public void setNgayDoiTra(LocalDate ngayDoiTra) {
        this.ngayDoiTra = ngayDoiTra;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public String getLoaiPhieu() {
        return loaiPhieu;
    }

    public void setLoaiPhieu(String loaiPhieu) {
        this.loaiPhieu = loaiPhieu;
    }

    public double getTienHoanLai() {
        return tienHoanLai;
    }

    public void setTienHoanLai(double tienHoanLai) {
        this.tienHoanLai = tienHoanLai;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPDT);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PhieuDoiTra other = (PhieuDoiTra) obj;
        return Objects.equals(maPDT, other.maPDT);
    }

    @Override
    public String toString() {
        return "PhieuDoiTra{" +
                "maPDT='" + maPDT + '\'' +
                ", ngayDoiTra=" + ngayDoiTra +
                ", lyDo='" + lyDo + '\'' +
                ", loaiPhieu='" + loaiPhieu + '\'' +
                ", tienHoanLai=" + tienHoanLai +
                ", nhanVien=" + nhanVien +
                ", hoaDon=" + hoaDon +
                '}';
    }
}

