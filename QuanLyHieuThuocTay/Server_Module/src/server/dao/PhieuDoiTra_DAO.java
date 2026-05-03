package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.PhieuDoiTra;
import entity.CTPhieuDoiTra;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;

public class PhieuDoiTra_DAO {
    private Driver driver;

    public PhieuDoiTra_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public boolean themPhieuDoiTra(PhieuDoiTra p) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {maNV: $maNV}), (hd:HoaDon {maHD: $maHD}) " +
                           "CREATE (p:PhieuDoiTra {maPDT: $ma, ngayDoiTra: $ngay, lyDo: $lyDo, loaiPhieu: $loai, tienHoanLai: $tien}) " +
                           "CREATE (p)-[:LAP_BOI]->(nv) " +
                           "CREATE (p)-[:DOI_TRA_CHO]->(hd) " +
                           "RETURN p";
            Result result = session.run(query, Values.parameters(
                "maNV", p.getNhanVien().getMaNV(),
                "maHD", p.getHoaDon().getMaHD(),
                "ma", p.getMaPDT(),
                "ngay", p.getNgayDoiTra().toString(),
                "lyDo", p.getLyDo(),
                "loai", p.getLoaiPhieu(),
                "tien", p.getTienHoanLai()
            ));
            return result.hasNext();
        }
    }

    public boolean themCTPhieuDoiTra(CTPhieuDoiTra ct) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDoiTra {maPDT: $maPDT}), (t:Thuoc {maThuoc: $maThuoc}) " +
                           "CREATE (p)-[:HAS_DETAIL {soLuong: $sl}]->(t) " +
                           "RETURN p";
            Result result = session.run(query, Values.parameters(
                "maPDT", ct.getPhieuDoiTra().getMaPDT(),
                "maThuoc", ct.getThuoc().getMaThuoc(),
                "sl", ct.getSoLuong()
            ));
            return result.hasNext();
        }
    }

    public String taoMaPDTMoi() {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDoiTra) RETURN p.maPDT ORDER BY p.maPDT DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(3)) + 1;
                return String.format("PDT%03d", number);
            }
        }
        return "PDT001";
    }

    /**
     * Kiểm tra xem HoaDon đã có PhieuDoiTra nào chưa
     * Trả về danh sách mô tả các phiếu đã tồn tại
     */
    public List<String> getPhieuDoiTraByMaHD(String maHD) {
        List<String> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDoiTra)-[:DOI_TRA_CHO]->(hd:HoaDon {maHD: $maHD}) " +
                           "RETURN p.maPDT, p.loaiPhieu, p.ngayDoiTra, p.lyDo ORDER BY p.ngayDoiTra DESC";
            Result result = session.run(query, Values.parameters("maHD", maHD));
            while (result.hasNext()) {
                Record rec = result.next();
                String info = rec.get(0).asString() + " | " + 
                              rec.get(1).asString() + " | " + 
                              rec.get(2).asString() + " | " + 
                              rec.get(3).asString();
                ds.add(info);
            }
        }
        return ds;
    }

    /**
     * Cập nhật tồn kho khi trả thuốc (tăng số lượng thuốc)
     */
    public boolean capNhatTonKhoTraThuoc(String maThuoc, int soLuongTra) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {maThuoc: $ma}) SET t.soLuong = t.soLuong + $sl RETURN t";
            Result result = session.run(query, Values.parameters("ma", maThuoc, "sl", soLuongTra));
            return result.hasNext();
        }
    }
}
