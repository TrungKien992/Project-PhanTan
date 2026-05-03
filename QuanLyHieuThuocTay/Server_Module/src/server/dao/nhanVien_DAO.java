package server.dao;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.NhanVien;
import entity.ChucVu;
import entity.TaiKhoan;
import server.config.Neo4jConfig;

public class nhanVien_DAO {
    private Driver driver;

    public nhanVien_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien) " +
                           "OPTIONAL MATCH (nv)-[:CO_CHUC_VU]->(cv:ChucVu) " +
                           "OPTIONAL MATCH (nv)-[:CO_TAI_KHOAN]->(tk:TaiKhoan) " +
                           "RETURN nv, cv, tk";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapRecordToNhanVien(result.next()));
            }
        }
        return ds;
    }

    public NhanVien getNhanVienByMaTK(String maTK) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien)-[:CO_TAI_KHOAN]->(tk:TaiKhoan {maTK: $maTK}) " +
                           "OPTIONAL MATCH (nv)-[:CO_CHUC_VU]->(cv:ChucVu) " +
                           "RETURN nv, cv, tk";
            Result result = session.run(query, Values.parameters("maTK", maTK));
            if (result.hasNext()) {
                return mapRecordToNhanVien(result.next());
            }
        }
        return null;
    }

    public NhanVien getNhanVienTheoMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {maNV: $ma}) " +
                           "OPTIONAL MATCH (nv)-[:CO_CHUC_VU]->(cv:ChucVu) " +
                           "OPTIONAL MATCH (nv)-[:CO_TAI_KHOAN]->(tk:TaiKhoan) " +
                           "RETURN nv, cv, tk";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                return mapRecordToNhanVien(result.next());
            }
        }
        return null;
    }

    public boolean themNhanVien(NhanVien nv) {
        try (Session session = driver.session()) {
            String query = "MATCH (cv:ChucVu {maChucVu: $maCV}) " +
                           "MATCH (tk:TaiKhoan {maTK: $maTK}) " +
                           "CREATE (nv:NhanVien {maNV: $maNV, tenNV: $tenNV, ngaySinh: $ngaySinh, gioiTinh: $gioiTinh, soDienThoai: $sdt, diaChi: $diaChi, anh: $anh, trangThai: $trangThai}) " +
                           "CREATE (nv)-[:CO_CHUC_VU]->(cv) " +
                           "CREATE (nv)-[:CO_TAI_KHOAN]->(tk) " +
                           "RETURN nv";
            Result result = session.run(query, Values.parameters(
                "maNV", nv.getMaNV(),
                "tenNV", nv.getTenNV(),
                "ngaySinh", nv.getNgaySinh().toString(),
                "gioiTinh", nv.getGioiTinh(),
                "sdt", nv.getSoDienThoai(),
                "diaChi", nv.getDiaChi(),
                "anh", nv.getAnh(),
                "trangThai", nv.getTrangThai(),
                "maCV", nv.getChucVu().getMaChucVu(),
                "maTK", nv.getTaiKhoan().getMaTK()
            ));
            return result.hasNext();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNhanVien(NhanVien nv) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {maNV: $maNV}) " +
                           "SET nv.tenNV = $tenNV, nv.ngaySinh = $ngaySinh, nv.gioiTinh = $gioiTinh, nv.soDienThoai = $sdt, nv.diaChi = $diaChi, nv.anh = $anh, nv.trangThai = $trangThai " +
                           "WITH nv " +
                           "MATCH (cv:ChucVu {maChucVu: $maCV}) " +
                           "MATCH (nv)-[r:CO_CHUC_VU]->() DELETE r " +
                           "CREATE (nv)-[:CO_CHUC_VU]->(cv) " +
                           "RETURN nv";
            Result result = session.run(query, Values.parameters(
                "maNV", nv.getMaNV(),
                "tenNV", nv.getTenNV(),
                "ngaySinh", nv.getNgaySinh().toString(),
                "gioiTinh", nv.getGioiTinh(),
                "sdt", nv.getSoDienThoai(),
                "diaChi", nv.getDiaChi(),
                "anh", nv.getAnh(),
                "trangThai", nv.getTrangThai(),
                "maCV", nv.getChucVu().getMaChucVu()
            ));
            return result.hasNext();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNhanVien(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {maNV: $ma}) DETACH DELETE nv";
            session.run(query, Values.parameters("ma", ma));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String generateNewMaNV() {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien) RETURN nv.maNV ORDER BY nv.maNV DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("NV%03d", number);
            }
        }
        return "NV001";
    }

    public String generateNewMaNV_FromTable() {
        return generateNewMaNV();
    }

    public boolean checkSdtTonTai(String sdt) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {soDienThoai: $sdt}) RETURN count(nv) > 0";
            Result result = session.run(query, Values.parameters("sdt", sdt));
            return result.next().get(0).asBoolean();
        }
    }

    private NhanVien mapRecordToNhanVien(Record record) {
        NhanVien nv = new NhanVien();
        var nodeNV = record.get("nv").asNode();
        nv.setMaNV(nodeNV.get("maNV").asString());
        nv.setTenNV(nodeNV.get("tenNV").asString());
        nv.setNgaySinh(java.time.LocalDate.parse(nodeNV.get("ngaySinh").asString()));
        nv.setGioiTinh(nodeNV.get("gioiTinh").asString());
        nv.setSoDienThoai(nodeNV.get("soDienThoai").asString());
        nv.setDiaChi(nodeNV.get("diaChi").asString());
        nv.setAnh(nodeNV.get("anh").isNull() ? null : nodeNV.get("anh").asString());
        nv.setTrangThai(nodeNV.get("trangThai").asString());

        if (!record.get("cv").isNull()) {
            ChucVu cv = new ChucVu();
            var nodeCV = record.get("cv").asNode();
            cv.setMaChucVu(nodeCV.get("maChucVu").asString());
            cv.setTenChucVu(nodeCV.get("tenChucVu").asString());
            nv.setChucVu(cv);
        }

        if (!record.get("tk").isNull()) {
            TaiKhoan tk = new TaiKhoan();
            var nodeTK = record.get("tk").asNode();
            tk.setMaTK(nodeTK.get("maTK").asString());
            tk.setTenTK(nodeTK.get("tenTK").asString());
            tk.setQuyenHan(nodeTK.get("quyenHan").asString());
            nv.setTaiKhoan(tk);
        }

        return nv;
    }
}
