package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.TaiKhoan;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;

public class taiKhoan_DAO {
    private Driver driver;

    public taiKhoan_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<TaiKhoan> getAllTaiKhoan() {
        List<TaiKhoan> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (tk:TaiKhoan) RETURN tk";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapNodeToTK(result.next().get("tk").asNode()));
            }
        }
        return ds;
    }

    public TaiKhoan checkLogin(String username, String password) {
        try (Session session = driver.session()) {
            String query = "MATCH (tk:TaiKhoan {tenTK: $user, matKhau: $pass}) RETURN tk";
            Result result = session.run(query, Values.parameters("user", username, "pass", password));
            if (result.hasNext()) {
                return mapNodeToTK(result.next().get("tk").asNode());
            }
        }
        return null;
    }

    public TaiKhoan getTaiKhoanByMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (tk:TaiKhoan {maTK: $ma}) RETURN tk";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                return mapNodeToTK(result.next().get("tk").asNode());
            }
        }
        return null;
    }

    public boolean create(TaiKhoan tk) {
        try (Session session = driver.session()) {
            String query = "CREATE (tk:TaiKhoan {maTK: $ma, tenTK: $ten, matKhau: $pass, quyenHan: $quyen, trangThai: $tt}) RETURN tk";
            Result result = session.run(query, Values.parameters(
                "ma", tk.getMaTK(),
                "ten", tk.getTenTK(),
                "pass", tk.getMatKhau(),
                "quyen", tk.getQuyenHan(),
                "tt", tk.isTrangThai()
            ));
            return result.hasNext();
        }
    }

    public boolean update(TaiKhoan tk) {
        try (Session session = driver.session()) {
            // Cập nhật trạng thái tài khoản và đồng bộ sang nhân viên tương ứng
            String query = "MATCH (tk:TaiKhoan {maTK: $ma}) " +
                           "SET tk.tenTK = $ten, tk.matKhau = $pass, tk.quyenHan = $quyen, tk.trangThai = $tt " +
                           "WITH tk " +
                           "OPTIONAL MATCH (nv:NhanVien)-[:CO_TAI_KHOAN]->(tk) " +
                           "SET nv.trangThai = CASE WHEN $tt = true THEN 'Đang làm việc' ELSE 'Đã nghỉ việc' END " +
                           "RETURN tk";
            Result result = session.run(query, Values.parameters(
                "ma", tk.getMaTK(),
                "ten", tk.getTenTK(),
                "pass", tk.getMatKhau(),
                "quyen", tk.getQuyenHan(),
                "tt", tk.isTrangThai()
            ));
            return result.hasNext();
        }
    }

    public boolean deleteTaiKhoanPermanently(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (tk:TaiKhoan {maTK: $ma}) DETACH DELETE tk";
            Result result = session.run(query, Values.parameters("ma", ma));
            return true; // Neo4j always returns true for delete if successful
        }
    }

    public String generateNewMaTK() {
        try (Session session = driver.session()) {
            String query = "MATCH (tk:TaiKhoan) RETURN tk.maTK ORDER BY tk.maTK DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("TK%03d", number);
            }
        }
        return "TK001";
    }

    private TaiKhoan mapNodeToTK(org.neo4j.driver.types.Node node) {
        TaiKhoan tk = new TaiKhoan();
        tk.setMaTK(node.get("maTK").asString());
        tk.setTenTK(node.get("tenTK").asString());
        tk.setMatKhau(node.get("matKhau").asString());
        tk.setQuyenHan(node.get("quyenHan").asString());
        tk.setTrangThai(node.get("trangThai").asBoolean());
        return tk;
    }
}
