package entity;
import java.io.Serializable;

import java.time.LocalDate;
import java.util.Objects;

public class Thue implements Serializable {
    private static final long serialVersionUID = 1L;
    private String maThue;
    private double tiLe;
    private String moTa;
    private LocalDate ngayApDung;

    public Thue() {
    }
    
    public Thue(String maThue) {
        this.maThue = maThue;
    }

    public Thue(String maThue, double tiLe, String moTa, LocalDate ngayApDung) {
        this.maThue = maThue;
        this.tiLe = tiLe;
        this.moTa = moTa;
        this.ngayApDung = ngayApDung;
    }

    // Getters and Setters
    public String getMaThue() {
        return maThue;
    }

    public void setMaThue(String maThue) {
        this.maThue = maThue;
    }

    public double getTiLe() {
        return tiLe;
    }

    public void setTiLe(double tiLe) {
        this.tiLe = tiLe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public LocalDate getNgayApDung() {
        return ngayApDung;
    }

    public void setNgayApDung(LocalDate ngayApDung) {
        this.ngayApDung = ngayApDung;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maThue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Thue other = (Thue) obj;
        return Objects.equals(maThue, other.maThue);
    }

    @Override
    public String toString() {
        return moTa;
    }
}
