package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.NhaCungCap;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;

public class nhaCungCap_DAO {
    private Driver driver;

    public nhaCungCap_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (ncc:NhaCungCap) RETURN ncc";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapNodeToNCC(result.next().get("ncc").asNode()));
            }
        }
        return ds;
    }

    public NhaCungCap getNhaCungCapTheoMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (ncc:NhaCungCap {maNhaCungCap: $ma}) RETURN ncc";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                return mapNodeToNCC(result.next().get("ncc").asNode());
            }
        }
        return null;
    }

    public NhaCungCap getNhaCungCapTheoTen(String ten) {
        try (Session session = driver.session()) {
            String query = "MATCH (ncc:NhaCungCap {tenNhaCungCap: $ten}) RETURN ncc";
            Result result = session.run(query, Values.parameters("ten", ten));
            if (result.hasNext()) {
                return mapNodeToNCC(result.next().get("ncc").asNode());
            }
        }
        return null;
    }

    public boolean themNhaCungCap(NhaCungCap ncc) {
        try (Session session = driver.session()) {
            String query = "CREATE (ncc:NhaCungCap {maNhaCungCap: $ma, tenNhaCungCap: $ten, soDienThoai: $sdt, email: $email, diaChi: $dc, trangThai: $tt, ghiChu: $gc}) RETURN ncc";
            Result result = session.run(query, Values.parameters(
                "ma", ncc.getMaNhaCungCap(),
                "ten", ncc.getTenNhaCungCap(),
                "sdt", ncc.getSoDienThoai(),
                "email", ncc.getEmail(),
                "dc", ncc.getDiaChi(),
                "tt", ncc.isTrangThai(),
                "gc", ncc.getGhiChu()
            ));
            return result.hasNext();
        }
    }

    public boolean updateNhaCungCap(NhaCungCap ncc) {
        try (Session session = driver.session()) {
            String query = "MATCH (ncc:NhaCungCap {maNhaCungCap: $ma}) " +
                           "SET ncc.tenNhaCungCap = $ten, ncc.soDienThoai = $sdt, ncc.email = $email, ncc.diaChi = $dc, ncc.trangThai = $tt, ncc.ghiChu = $gc " +
                           "RETURN ncc";
            Result result = session.run(query, Values.parameters(
                "ma", ncc.getMaNhaCungCap(),
                "ten", ncc.getTenNhaCungCap(),
                "sdt", ncc.getSoDienThoai(),
                "email", ncc.getEmail(),
                "dc", ncc.getDiaChi(),
                "tt", ncc.isTrangThai(),
                "gc", ncc.getGhiChu()
            ));
            return result.hasNext();
        }
    }

    public String generateNewMaNCC() {
        try (Session session = driver.session()) {
            String query = "MATCH (ncc:NhaCungCap) RETURN ncc.maNhaCungCap ORDER BY ncc.maNhaCungCap DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(3)) + 1;
                return String.format("NCC%03d", number);
            }
        }
        return "NCC001";
    }

    private NhaCungCap mapNodeToNCC(org.neo4j.driver.types.Node node) {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNhaCungCap(node.get("maNhaCungCap").asString());
        ncc.setTenNhaCungCap(node.get("tenNhaCungCap").asString());
        ncc.setSoDienThoai(node.get("soDienThoai").asString());
        ncc.setEmail(node.get("email").asString());
        ncc.setDiaChi(node.get("diaChi").asString());
        ncc.setTrangThai(node.get("trangThai").asBoolean());
        ncc.setGhiChu(node.get("ghiChu").asString());
        return ncc;
    }
}
