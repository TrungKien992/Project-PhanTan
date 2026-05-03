package server.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.KhachHang;
import server.config.Neo4jConfig;

public class khachHang_DAO {
    private Driver driver;

    public khachHang_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang) RETURN kh";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapNodeToKhachHang(result.next().get("kh").asNode()));
            }
        }
        return ds;
    }

    public KhachHang getKhachHangTheoSDT(String sdt) {
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang {soDienThoai: $sdt}) RETURN kh";
            Result result = session.run(query, Values.parameters("sdt", sdt));
            if (result.hasNext()) {
                return mapNodeToKhachHang(result.next().get("kh").asNode());
            }
        }
        return null;
    }

    public List<KhachHang> searchKhachHang(String ma, String ten, String sdt, String diaChi) {
        List<KhachHang> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang) " +
                           "WHERE toLower(kh.maKH) CONTAINS toLower($ma) AND toLower(kh.tenKH) CONTAINS toLower($ten) " +
                           "AND toLower(kh.soDienThoai) CONTAINS toLower($sdt) AND toLower(kh.diaChi) CONTAINS toLower($diaChi) " +
                           "RETURN kh";
            Result result = session.run(query, Values.parameters(
                "ma", ma, "ten", ten, "sdt", sdt, "diaChi", diaChi
            ));
            while (result.hasNext()) {
                ds.add(mapNodeToKhachHang(result.next().get("kh").asNode()));
            }
        }
        return ds;
    }

    public boolean themKhachHang(KhachHang kh) {
        try (Session session = driver.session()) {
            String query = "CREATE (kh:KhachHang {maKH: $ma, tenKH: $ten, soDienThoai: $sdt, diaChi: $dc, trangThai: $tt}) RETURN kh";
            Result result = session.run(query, Values.parameters(
                "ma", kh.getMaKH(), "ten", kh.getTenKH(), "sdt", kh.getSoDienThoai(), "dc", kh.getDiaChi(), "tt", kh.isTrangThai()
            ));
            return result.hasNext();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKhachHang(KhachHang kh) {
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang {maKH: $ma}) SET kh.tenKH = $ten, kh.soDienThoai = $sdt, kh.diaChi = $dc, kh.trangThai = $tt RETURN kh";
            Result result = session.run(query, Values.parameters(
                "ma", kh.getMaKH(), "ten", kh.getTenKH(), "sdt", kh.getSoDienThoai(), "dc", kh.getDiaChi(), "tt", kh.isTrangThai()
            ));
            return result.hasNext();
        }
    }

    public boolean deleteKhachHang(String maKH) {
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang {maKH: $ma}) SET kh.trangThai = false RETURN kh";
            Result result = session.run(query, Values.parameters("ma", maKH));
            return result.hasNext();
        }
    }

    public KhachHang getKhachHangTheoMa(String maKH) {
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang {maKH: $ma}) RETURN kh";
            Result result = session.run(query, Values.parameters("ma", maKH));
            if (result.hasNext()) {
                return mapNodeToKhachHang(result.next().get("kh").asNode());
            }
        }
        return null;
    }

    public String generateNewMaKH() {
        try (Session session = driver.session()) {
            String query = "MATCH (kh:KhachHang) RETURN kh.maKH ORDER BY kh.maKH DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("KH%03d", number);
            }
        }
        return "KH001";
    }

    private KhachHang mapNodeToKhachHang(org.neo4j.driver.types.Node node) {
        KhachHang kh = new KhachHang();
        kh.setMaKH(node.get("maKH").asString());
        kh.setTenKH(node.get("tenKH").asString());
        kh.setSoDienThoai(node.get("soDienThoai").asString());
        kh.setDiaChi(node.get("diaChi").asString());
        kh.setTrangThai(node.get("trangThai").asBoolean());
        return kh;
    }
}
