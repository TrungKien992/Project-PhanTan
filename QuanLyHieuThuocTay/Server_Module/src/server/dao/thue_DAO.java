package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import entity.Thue;
import server.config.Neo4jConfig;

public class thue_DAO {
    private Driver driver;

    public thue_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public Thue getThueByMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (t:Thue {maThue: $ma}) RETURN t";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                org.neo4j.driver.types.Node node = result.next().get("t").asNode();
                Thue t = new Thue();
                t.setMaThue(node.get("maThue").asString());
                t.setMoTa(node.get("moTa").asString());
                t.setTiLe(node.get("tiLe").asDouble());
                return t;
            }
        }
        return null;
    }
}
