package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import server.config.Neo4jConfig;
import java.util.HashMap;
import java.util.Map;

public class chiTietHoaDon_DAO {
    private Driver driver;

    public chiTietHoaDon_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public Map<String, Integer> getThongKeThuocBanChay() {
        Map<String, Integer> map = new HashMap<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[r:CHI_TIET_HOA_DON]->(t:Thuoc) " +
                           "RETURN t.maThuoc as ma, sum(r.soLuong) as total " +
                           "ORDER BY total DESC";
            Result result = session.run(query);
            while (result.hasNext()) {
                Record rec = result.next();
                map.put(rec.get("ma").asString(), rec.get("total").asInt());
            }
        }
        return map;
    }
}
