package server.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import entity.KeThuoc;
import server.config.Neo4jConfig;

public class keThuoc_DAO {
    private Driver driver;

    public keThuoc_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<KeThuoc> getAllKeThuoc() {
        List<KeThuoc> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (ke:KeThuoc) RETURN ke";
            Result result = session.run(query);
            while (result.hasNext()) {
                org.neo4j.driver.types.Node node = result.next().get("ke").asNode();
                KeThuoc ke = new KeThuoc();
                ke.setMaKe(node.get("maKe").asString());
                ke.setLoaiKe(node.get("loaiKe").asString());
                ds.add(ke);
            }
        }
        return ds;
    }
}
