package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.KhuyenMai;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class khuyenMai_DAO {
    private Driver driver;

    public khuyenMai_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<KhuyenMai> getAllKhuyenMai() {
        List<KhuyenMai> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (km:KhuyenMai) RETURN km";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapNodeToKM(result.next().get("km").asNode()));
            }
        }
        return ds;
    }

    public boolean addKhuyenMai(KhuyenMai km) {
        try (Session session = driver.session()) {
            String query = "CREATE (km:KhuyenMai {maKM: $ma, tenChuongTrinh: $ten, giaTri: $giaTri, ngayBatDau: $ngayBD, ngayKetThuc: $ngayKT, soLuongToiDa: $sl, trangThai: $tt}) RETURN km";
            Result result = session.run(query, Values.parameters(
                "ma", km.getMaKM(),
                "ten", km.getTenChuongTrinh(),
                "giaTri", km.getGiaTri(),
                "ngayBD", km.getNgayBatDau().toString(),
                "ngayKT", km.getNgayKetThuc().toString(),
                "sl", km.getSoLuongToiDa(),
                "tt", km.getTrangThai()
            ));
            return result.hasNext();
        }
    }

    public boolean updateKhuyenMai(KhuyenMai km) {
        try (Session session = driver.session()) {
            String query = "MATCH (km:KhuyenMai {maKM: $ma}) " +
                           "SET km.tenChuongTrinh = $ten, km.giaTri = $giaTri, km.ngayBatDau = $ngayBD, km.ngayKetThuc = $ngayKT, km.soLuongToiDa = $sl, km.trangThai = $tt " +
                           "RETURN km";
            Result result = session.run(query, Values.parameters(
                "ma", km.getMaKM(),
                "ten", km.getTenChuongTrinh(),
                "giaTri", km.getGiaTri(),
                "ngayBD", km.getNgayBatDau().toString(),
                "ngayKT", km.getNgayKetThuc().toString(),
                "sl", km.getSoLuongToiDa(),
                "tt", km.getTrangThai()
            ));
            return result.hasNext();
        }
    }

    public boolean deleteKhuyenMai(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (km:KhuyenMai {maKM: $ma}) SET km.trangThai = 0 RETURN km";
            Result result = session.run(query, Values.parameters("ma", ma));
            return result.hasNext();
        }
    }

    public List<KhuyenMai> searchKhuyenMai(String maTen, LocalDate tuNgay, LocalDate denNgay, int trangThai) {
        List<KhuyenMai> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            StringBuilder query = new StringBuilder("MATCH (km:KhuyenMai) WHERE (toLower(km.maKM) CONTAINS toLower($maTen) OR toLower(km.tenChuongTrinh) CONTAINS toLower($maTen)) ");
            if (tuNgay != null) query.append("AND km.ngayBatDau >= $tuNgay ");
            if (denNgay != null) query.append("AND km.ngayKetThuc <= $denNgay ");
            if (trangThai != -1) query.append("AND km.trangThai = $trangThai ");
            query.append("RETURN km");

            Result result = session.run(query.toString(), Values.parameters(
                "maTen", maTen,
                "tuNgay", tuNgay != null ? tuNgay.toString() : null,
                "denNgay", denNgay != null ? denNgay.toString() : null,
                "trangThai", trangThai
            ));
            while (result.hasNext()) {
                ds.add(mapNodeToKM(result.next().get("km").asNode()));
            }
        }
        return ds;
    }

    public KhuyenMai getKhuyenMaiByMa(String maKM) {
        try (Session session = driver.session()) {
            String query = "MATCH (km:KhuyenMai {maKM: $ma}) RETURN km";
            Result result = session.run(query, Values.parameters("ma", maKM));
            if (result.hasNext()) {
                return mapNodeToKM(result.next().get("km").asNode());
            }
        }
        return null;
    }

    public String generateNewMaKM() {
        try (Session session = driver.session()) {
            String query = "MATCH (km:KhuyenMai) RETURN km.maKM ORDER BY km.maKM DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("KM%02d", number);
            }
        }
        return "KM01";
    }

    private KhuyenMai mapNodeToKM(org.neo4j.driver.types.Node node) {
        KhuyenMai km = new KhuyenMai();
        km.setMaKM(node.get("maKM").asString());
        km.setTenChuongTrinh(node.get("tenChuongTrinh").asString());
        km.setGiaTri(node.get("giaTri").asDouble());
        km.setNgayBatDau(LocalDate.parse(node.get("ngayBatDau").asString()));
        km.setNgayKetThuc(LocalDate.parse(node.get("ngayKetThuc").asString()));
        km.setSoLuongToiDa(node.get("soLuongToiDa").asInt());
        km.setTrangThai(node.get("trangThai").asInt());
        return km;
    }
}
