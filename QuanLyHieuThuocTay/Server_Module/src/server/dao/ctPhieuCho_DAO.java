package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.ChiTietPhieuCho;
import entity.Thuoc;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;

public class ctPhieuCho_DAO {
    private Driver driver;

    public ctPhieuCho_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<ChiTietPhieuCho> getChiTietTheoMaPhieuCho(String ma) {
        List<ChiTietPhieuCho> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuCho {maPhieuCho: $ma})-[r:HAS_DETAIL]->(t:Thuoc) " +
                           "RETURN t.maThuoc, r.soLuong, r.donGia";
            Result result = session.run(query, Values.parameters("ma", ma));
            while (result.hasNext()) {
                Record rec = result.next();
                ChiTietPhieuCho ct = new ChiTietPhieuCho();
                ct.setThuoc(new Thuoc(rec.get("t.maThuoc").asString()));
                ct.setSoLuong(rec.get("r.soLuong").asInt());
                ct.setDonGia(rec.get("r.donGia").asDouble());
                ds.add(ct);
            }
        }
        return ds;
    }
}
