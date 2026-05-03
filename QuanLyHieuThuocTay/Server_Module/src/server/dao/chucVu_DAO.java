package server.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.ChucVu;
import server.config.Neo4jConfig;

public class chucVu_DAO {
    private Driver driver;

    public chucVu_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<ChucVu> getAllChucVu() {
        List<ChucVu> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (cv:ChucVu) RETURN cv";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapNodeToChucVu(result.next().get("cv").asNode()));
            }
        }
        return ds;
    }

    public ChucVu getChucVuTheoTen(String ten) {
        try (Session session = driver.session()) {
            String query = "MATCH (cv:ChucVu {tenChucVu: $ten}) RETURN cv";
            Result result = session.run(query, Values.parameters("ten", ten));
            if (result.hasNext()) {
                return mapNodeToChucVu(result.next().get("cv").asNode());
            }
        }
        return null;
    }

    public ChucVu getChucVuTheoMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (cv:ChucVu {maChucVu: $ma}) RETURN cv";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                return mapNodeToChucVu(result.next().get("cv").asNode());
            }
        }
        return null;
    }

    private ChucVu mapNodeToChucVu(org.neo4j.driver.types.Node node) {
        ChucVu cv = new ChucVu();
        cv.setMaChucVu(node.get("maChucVu").asString());
        cv.setTenChucVu(node.get("tenChucVu").asString());
        return cv;
    }
}
