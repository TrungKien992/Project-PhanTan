package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.PhieuChoThanhToan;
import entity.ChiTietPhieuCho;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;

public class phieuCho_DAO {
    private Driver driver;

    public phieuCho_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<PhieuChoThanhToan> getAllPhieuCho() {
        List<PhieuChoThanhToan> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuCho) RETURN p";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapNodeToPhieuCho(result.next().get("p").asNode()));
            }
        }
        return ds;
    }

    public boolean addPhieuCho(PhieuChoThanhToan p) {
        try (Session session = driver.session()) {
            // 1. Tạo Node PhieuCho
            String query = "CREATE (p:PhieuCho {maPhieuCho: $ma, tenKH: $tenKH, sdtKH: $sdt, ngayLap: $ngay, tongTienHang: $tth, thueVAT: $thue, tongCong: $tc}) RETURN p";
            Result result = session.run(query, Values.parameters(
                "ma", p.getMaPhieuCho(),
                "tenKH", p.getTenKH(),
                "sdt", p.getSdtKH(),
                "ngay", p.getNgayLap().toString(),
                "tth", p.getTongTienHang(),
                "thue", p.getThueVAT(),
                "tc", p.getTongCong()
            ));
            
            if (!result.hasNext()) return false;

            // 2. Tạo các quan hệ HAS_DETAIL tới Thuoc
            if (p.getDsChiTietPhieuCho() != null) {
                for (ChiTietPhieuCho ct : p.getDsChiTietPhieuCho()) {
                    String detailQuery = "MATCH (p:PhieuCho {maPhieuCho: $maP}), (t:Thuoc {maThuoc: $maT}) " +
                                         "CREATE (p)-[r:HAS_DETAIL {soLuong: $sl, donGia: $dg}]->(t)";
                    session.run(detailQuery, Values.parameters(
                        "maP", p.getMaPhieuCho(),
                        "maT", ct.getThuoc().getMaThuoc(),
                        "sl", ct.getSoLuong(),
                        "dg", ct.getDonGia()
                    ));
                }
            }
            return true;
        }
    }

    public PhieuChoThanhToan getPhieuChoTheoMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuCho {maPhieuCho: $ma}) RETURN p";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                return mapNodeToPhieuCho(result.next().get("p").asNode());
            }
        }
        return null;
    }

    public boolean xoaPhieuCho(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuCho {maPhieuCho: $ma}) DETACH DELETE p";
            session.run(query, Values.parameters("ma", ma));
            return true;
        }
    }

    public boolean xoaTatCaPhieuCho() {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuCho) DETACH DELETE p";
            session.run(query);
            return true;
        }
    }

    public String generateNextMaPhieuCho() {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuCho) RETURN p.maPhieuCho ORDER BY p.maPhieuCho DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("PC%03d", number);
            }
        }
        return "PC001";
    }

    private PhieuChoThanhToan mapNodeToPhieuCho(org.neo4j.driver.types.Node node) {
        PhieuChoThanhToan p = new PhieuChoThanhToan();
        p.setMaPhieuCho(node.get("maPhieuCho").asString());
        p.setTenKH(node.get("tenKH").asString());
        p.setSdtKH(node.get("sdtKH").asString());
        p.setNgayLap(java.time.LocalDate.parse(node.get("ngayLap").asString()));
        p.setTongTienHang(node.get("tongTienHang").asDouble());
        p.setThueVAT(node.get("thueVAT").asDouble());
        p.setTongCong(node.get("tongCong").asDouble());
        return p;
    }
}
