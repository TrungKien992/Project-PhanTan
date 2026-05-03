package server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.Thuoc;
import entity.KeThuoc;
import entity.NhaCungCap;
import server.config.Neo4jConfig;

public class thuoc_DAO {
    private Driver driver;

    public thuoc_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public Thuoc getThuocTheoMa(String maThuoc) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {maThuoc: $ma})-[:THUOC_TAI]->(k:KeThuoc), " +
                           "(t)-[:CUNG_CAP_BOI]->(ncc:NhaCungCap) " +
                           "RETURN t, k, ncc";
            Result result = session.run(query, Values.parameters("ma", maThuoc));
            if (result.hasNext()) {
                return mapRecordToThuoc(result.next());
            }
        }
        return null;
    }

    public List<Thuoc> getAllThuoc() {
        List<Thuoc> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc)-[:THUOC_TAI]->(k:KeThuoc), " +
                           "(t)-[:CUNG_CAP_BOI]->(ncc:NhaCungCap) RETURN t, k, ncc";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapRecordToThuoc(result.next()));
            }
        }
        return ds;
    }

    public boolean themThuoc(Thuoc t) {
        try (Session session = driver.session()) {
            String query = "MATCH (k:KeThuoc {maKe: $maKe}), (ncc:NhaCungCap {maNhaCungCap: $maNCC}) " +
                           "CREATE (t:Thuoc {maThuoc: $ma, tenThuoc: $ten, giaNhap: $giaNhap, giaBan: $giaBan, " +
                           "soLuong: $soLuong, hanSuDung: $hsd, thanhPhan: $thanhPhan, donViTinh: $dvt, " +
                           "anh: $anh, trangThai: $trangThai}) " +
                           "CREATE (t)-[:THUOC_TAI]->(k) " +
                           "CREATE (t)-[:CUNG_CAP_BOI]->(ncc) " +
                           "RETURN t";
            Result result = session.run(query, Values.parameters(
                "maKe", t.getKeThuoc().getMaKe(),
                "maNCC", t.getNhaCungCap().getMaNhaCungCap(),
                "ma", t.getMaThuoc(),
                "ten", t.getTenThuoc(),
                "giaNhap", t.getGiaNhap(),
                "giaBan", t.getGiaBan(),
                "soLuong", t.getSoLuong(),
                "hsd", t.getHanSuDung().toString(),
                "thanhPhan", t.getThanhPhan(),
                "dvt", t.getDonViTinh(),
                "anh", t.getAnh(),
                "trangThai", t.getTrangThai()
            ));
            return result.hasNext();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateThuoc(Thuoc t) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {maThuoc: $ma}) " +
                           "SET t.tenThuoc = $ten, t.giaNhap = $giaNhap, t.giaBan = $giaBan, t.soLuong = $soLuong, " +
                           "t.hanSuDung = $hsd, t.thanhPhan = $thanhPhan, t.donViTinh = $dvt, t.anh = $anh, t.trangThai = $trangThai " +
                           "WITH t " +
                           "MATCH (t)-[r1:THUOC_TAI]->() DELETE r1 " +
                           "MATCH (t)-[r2:CUNG_CAP_BOI]->() DELETE r2 " +
                           "WITH t " +
                           "MATCH (k:KeThuoc {maKe: $maKe}), (ncc:NhaCungCap {maNhaCungCap: $maNCC}) " +
                           "CREATE (t)-[:THUOC_TAI]->(k) " +
                           "CREATE (t)-[:CUNG_CAP_BOI]->(ncc) " +
                           "RETURN t";
            Result result = session.run(query, Values.parameters(
                "ma", t.getMaThuoc(),
                "ten", t.getTenThuoc(),
                "giaNhap", t.getGiaNhap(),
                "giaBan", t.getGiaBan(),
                "soLuong", t.getSoLuong(),
                "hsd", t.getHanSuDung().toString(),
                "thanhPhan", t.getThanhPhan(),
                "dvt", t.getDonViTinh(),
                "anh", t.getAnh(),
                "trangThai", t.getTrangThai(),
                "maKe", t.getKeThuoc().getMaKe(),
                "maNCC", t.getNhaCungCap().getMaNhaCungCap()
            ));
            return result.hasNext();
        }
    }

    public boolean updateSoLuongSauKhiBan(String maThuoc, int soLuongBan) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {maThuoc: $ma}) SET t.soLuong = t.soLuong - $sl RETURN t";
            Result result = session.run(query, Values.parameters("ma", maThuoc, "sl", soLuongBan));
            return result.hasNext();
        }
    }

    public String getNextMaThuoc() {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc) RETURN t.maThuoc ORDER BY t.maThuoc DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(1)) + 1;
                return String.format("T%02d", number);
            }
        }
        return "T01";
    }

    private Thuoc mapRecordToThuoc(Record record) {
        Thuoc t = new Thuoc();
        var nodeT = record.get("t").asNode();
        t.setMaThuoc(nodeT.get("maThuoc").asString());
        t.setTenThuoc(nodeT.get("tenThuoc").asString());
        t.setGiaNhap(nodeT.get("giaNhap").asDouble());
        t.setGiaBan(nodeT.get("giaBan").asDouble());
        t.setSoLuong(nodeT.get("soLuong").asInt());
        t.setHanSuDung(java.time.LocalDate.parse(nodeT.get("hanSuDung").asString()));
        t.setThanhPhan(nodeT.get("thanhPhan").asString());
        t.setDonViTinh(nodeT.get("donViTinh").asString());
        t.setAnh(nodeT.get("anh").asString());
        t.setTrangThai(nodeT.get("trangThai").asString());

        var nodeK = record.get("k").asNode();
        KeThuoc k = new KeThuoc();
        k.setMaKe(nodeK.get("maKe").asString());
        k.setLoaiKe(nodeK.get("loaiKe").asString());
        t.setKeThuoc(k);

        var nodeNCC = record.get("ncc").asNode();
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNhaCungCap(nodeNCC.get("maNhaCungCap").asString());
        ncc.setTenNhaCungCap(nodeNCC.get("tenNhaCungCap").asString());
        t.setNhaCungCap(ncc);

        return t;
    }
    
    public List<Thuoc> getAllActiveThuoc() {
        List<Thuoc> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {trangThai: 'Đang kinh doanh'})-[:THUOC_TAI]->(k:KeThuoc), " +
                           "(t)-[:CUNG_CAP_BOI]->(ncc:NhaCungCap) RETURN t, k, ncc";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapRecordToThuoc(result.next()));
            }
        }
        return ds;
    }

    public List<String> getAllTenThuoc() {
        List<String> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc) RETURN t.tenThuoc";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(result.next().get(0).asString());
            }
        }
        return ds;
    }

    public List<Thuoc> searchThuoc(String ten, String loai, String ncc) {
        List<Thuoc> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc)-[:THUOC_TAI]->(k:KeThuoc), " +
                           "(t)-[:CUNG_CAP_BOI]->(ncc:NhaCungCap) " +
                           "WHERE toLower(t.tenThuoc) CONTAINS toLower($ten) AND toLower(k.loaiKe) CONTAINS toLower($loai) " +
                           "AND toLower(ncc.tenNhaCungCap) CONTAINS toLower($ncc) " +
                           "RETURN t, k, ncc";
            Result result = session.run(query, Values.parameters(
                "ten", ten == null ? "" : ten,
                "loai", loai == null ? "" : loai,
                "ncc", ncc == null ? "" : ncc
            ));
            while (result.hasNext()) {
                ds.add(mapRecordToThuoc(result.next()));
            }
        }
        return ds;
    }

    public List<Thuoc> searchThuocForSale(String ma, String ten, String loai) {
        List<Thuoc> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc)-[:THUOC_TAI]->(k:KeThuoc), " +
                           "(t)-[:CUNG_CAP_BOI]->(ncc:NhaCungCap) " +
                           "WHERE toLower(t.maThuoc) CONTAINS toLower($ma) " +
                           "AND toLower(t.tenThuoc) CONTAINS toLower($ten) " +
                           "AND toLower(k.loaiKe) CONTAINS toLower($loai) " +
                           "AND t.trangThai = 'Đang kinh doanh' " +
                           "RETURN t, k, ncc";
            Result result = session.run(query, Values.parameters(
                "ma", ma == null ? "" : ma,
                "ten", ten == null ? "" : ten,
                "loai", loai == null ? "" : loai
            ));
            while (result.hasNext()) {
                ds.add(mapRecordToThuoc(result.next()));
            }
        }
        return ds;
    }

    public boolean updateTrangThai(String maThuoc, String trangThai) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {maThuoc: $ma}) SET t.trangThai = $tt RETURN t";
            Result result = session.run(query, Values.parameters("ma", maThuoc, "tt", trangThai));
            return result.hasNext();
        }
    }

    public boolean updateSoLuong(String maThuoc, int soLuongGiam) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thuoc {maThuoc: $ma}) SET t.soLuong = t.soLuong - $sl RETURN t";
            Result result = session.run(query, Values.parameters("ma", maThuoc, "sl", soLuongGiam));
            return result.hasNext();
        }
    }
}
