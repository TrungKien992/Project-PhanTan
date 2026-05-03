package server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.HoaDon;
import entity.ChiTietHoaDon;
import entity.Thuoc;
import entity.KhachHang;
import entity.NhanVien;
import entity.Thue;
import entity.KhuyenMai;
import server.config.Neo4jConfig;

public class hoaDon_DAO {
    private Driver driver;

    public hoaDon_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public boolean themHoaDon(HoaDon hd) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {maNV: $maNV}), (thue:Thue {maThue: $maThue}) " +
                           "OPTIONAL MATCH (kh:KhachHang {maKH: $maKH}) " +
                           "OPTIONAL MATCH (km:KhuyenMai {maKM: $maKM}) " +
                           "CREATE (h:HoaDon {maHD: $maHD, ngayLap: $ngayLap, tienKhachDua: $tienKhachDua, tienThua: $tienThua, tongCong: $tongCong}) " +
                           "CREATE (h)-[:LAP_BOI]->(nv) " +
                           "CREATE (h)-[:AP_DUNG_THUE]->(thue) " +
                           "FOREACH (i IN CASE WHEN kh IS NOT NULL THEN [1] ELSE [] END | CREATE (h)-[:MUA_BOI]->(kh)) " +
                           "FOREACH (i IN CASE WHEN km IS NOT NULL THEN [1] ELSE [] END | CREATE (h)-[:SU_DUNG_KM]->(km)) " +
                           "WITH h " +
                           "UNWIND $details as d " +
                           "MATCH (t:Thuoc {maThuoc: d.maThuoc}) " +
                           "CREATE (h)-[:HAS_DETAIL {soLuong: d.soLuong, donGia: d.donGia}]->(t) " +
                           "RETURN h";
            
            double tongCong = hd.getDanhSachChiTiet().stream().mapToDouble(d -> d.getSoLuong() * d.getDonGia()).sum();
            // Simplify for now, real logic should include tax/discount
            
            List<Map<String, Object>> details = hd.getDanhSachChiTiet().stream().map(d -> {
                Map<String, Object> map = new HashMap<>();
                map.put("maThuoc", d.getThuoc().getMaThuoc());
                map.put("soLuong", d.getSoLuong());
                map.put("donGia", d.getDonGia());
                return map;
            }).collect(Collectors.toList());

            Result result = session.run(query, Values.parameters(
                "maNV", hd.getNhanVien().getMaNV(),
                "maThue", hd.getThue() != null ? hd.getThue().getMaThue() : "TH001", // Default TH001 nếu null
                "maKH", hd.getKhachHang() != null ? hd.getKhachHang().getMaKH() : null,
                "maKM", hd.getKhuyenMai() != null ? hd.getKhuyenMai().getMaKM() : null,
                "maHD", hd.getMaHD(),
                "ngayLap", hd.getNgayLap().toString(),
                "tienKhachDua", hd.getTienKhachDua(),
                "tienThua", hd.getTienThua(),
                "tongCong", hd.tinhTongTien(),
                "details", details
            ));
            return result.hasNext();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDon> getAllHoaDon() {
        List<HoaDon> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (h:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "OPTIONAL MATCH (h)-[:AP_DUNG_THUE]->(th:Thue) " +
                           "OPTIONAL MATCH (h)-[:MUA_BOI]->(kh:KhachHang) " +
                           "OPTIONAL MATCH (h)-[:SU_DUNG_KM]->(km:KhuyenMai) " +
                           "RETURN h, nv, th, kh, km";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapRecordToHoaDon(result.next()));
            }
        }
        return ds;
    }

    private HoaDon mapRecordToHoaDon(Record record) {
        HoaDon hd = new HoaDon();
        var nodeH = record.get("h").asNode();
        hd.setMaHD(nodeH.get("maHD").asString());
        hd.setNgayLap(java.time.LocalDate.parse(nodeH.get("ngayLap").asString()));
        hd.setTienKhachDua(nodeH.get("tienKhachDua").asDouble());
        hd.setTienThua(nodeH.get("tienThua").asDouble());
        hd.setTongCong(nodeH.get("tongCong").asDouble());

        NhanVien nv = new NhanVien();
        nv.setMaNV(record.get("nv").asNode().get("maNV").asString());
        hd.setNhanVien(nv);

        if (!record.get("th").isNull()) {
            Thue th = new Thue();
            th.setMaThue(record.get("th").asNode().get("maThue").asString());
            hd.setThue(th);
        }

        if (!record.get("kh").isNull()) {
            KhachHang kh = new KhachHang();
            kh.setMaKH(record.get("kh").asNode().get("maKH").asString());
            hd.setKhachHang(kh);
        }

        if (!record.get("km").isNull()) {
            KhuyenMai km = new KhuyenMai();
            km.setMaKM(record.get("km").asNode().get("maKM").asString());
            hd.setKhuyenMai(km);
        }

        return hd;
    }

    public List<ChiTietHoaDon> getChiTietHoaDonTheoMa(String maHD) {
        List<ChiTietHoaDon> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (h:HoaDon {maHD: $ma})-[r:HAS_DETAIL]->(t:Thuoc) " +
                           "RETURN r.soLuong as soLuong, r.donGia as donGia, t.maThuoc as maThuoc";
            Result result = session.run(query, Values.parameters("ma", maHD));
            while (result.hasNext()) {
                Record rec = result.next();
                ChiTietHoaDon ct = new ChiTietHoaDon();
                ct.setSoLuong(rec.get("soLuong").asInt());
                ct.setDonGia(rec.get("donGia").asDouble());
                Thuoc t = new Thuoc();
                t.setMaThuoc(rec.get("maThuoc").asString());
                ct.setThuoc(t);
                ds.add(ct);
            }
        }
        return ds;
    }

    public String generateNewMaHD() {
        try (Session session = driver.session()) {
            String query = "MATCH (h:HoaDon) RETURN h.maHD ORDER BY h.maHD DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(2)) + 1;
                return String.format("HD%03d", number);
            }
        }
        return "HD001";
    }

    public HoaDon getHoaDonByMa(String maHD) {
        try (Session session = driver.session()) {
            String query = "MATCH (h:HoaDon {maHD: $ma})-[:LAP_BOI]->(nv:NhanVien) " +
                           "OPTIONAL MATCH (h)-[:AP_DUNG_THUE]->(th:Thue) " +
                           "OPTIONAL MATCH (h)-[:MUA_BOI]->(kh:KhachHang) " +
                           "OPTIONAL MATCH (h)-[:SU_DUNG_KM]->(km:KhuyenMai) " +
                           "RETURN h, nv, th, kh, km";
            Result result = session.run(query, Values.parameters("ma", maHD));
            if (result.hasNext()) {
                return mapRecordToHoaDon(result.next());
            }
        } catch (Exception e) {
            System.err.println("Lỗi getHoaDonByMa(" + maHD + "): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Object[]> getAllHoaDonChiTietForTable() {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "OPTIONAL MATCH (hd)-[:MUA_BOI]->(kh:KhachHang) " +
                           "RETURN hd.maHD, kh.maKH, hd.ngayLap, kh.tenKH, kh.soDienThoai, nv.tenNV, nv.soDienThoai, hd.tongCong";
            Result result = session.run(query);
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{
                    rec.get(0).asString(),
                    rec.get(1).isNull() ? "" : rec.get(1).asString(),
                    rec.get(2).asString(),
                    rec.get(3).isNull() ? "Khách vãng lai" : rec.get(3).asString(),
                    rec.get(4).isNull() ? "" : rec.get(4).asString(),
                    rec.get(5).asString(),
                    rec.get(6).isNull() ? "" : rec.get(6).asString(),
                    rec.get(7).isNull() ? 0.0 : rec.get(7).asDouble()
                });
            }
        }
        return ds;
    }

    public List<Object[]> searchHoaDonChiTiet(String maHD, String tenKH, String sdtKH, String tenNV, String sdtNV, String ngayLap) {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            StringBuilder query = new StringBuilder("MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) OPTIONAL MATCH (hd)-[:MUA_BOI]->(kh:KhachHang) WHERE 1=1 ");
            Map<String, Object> params = new HashMap<>();
            if (maHD != null && !maHD.isEmpty()) { query.append("AND toLower(hd.maHD) CONTAINS toLower($ma) "); params.put("ma", maHD); }
            if (tenKH != null && !tenKH.isEmpty()) { query.append("AND toLower(kh.tenKH) CONTAINS toLower($tenKH) "); params.put("tenKH", tenKH); }
            if (sdtKH != null && !sdtKH.isEmpty()) { query.append("AND toLower(kh.soDienThoai) CONTAINS toLower($sdtKH) "); params.put("sdtKH", sdtKH); }
            if (tenNV != null && !tenNV.isEmpty()) { query.append("AND toLower(nv.tenNV) CONTAINS toLower($tenNV) "); params.put("tenNV", tenNV); }
            if (sdtNV != null && !sdtNV.isEmpty()) { query.append("AND toLower(nv.soDienThoai) CONTAINS toLower($sdtNV) "); params.put("sdtNV", sdtNV); }
            if (ngayLap != null && !ngayLap.isEmpty()) { query.append("AND hd.ngayLap = $ngay "); params.put("ngay", ngayLap); }
            query.append("RETURN hd.maHD, kh.maKH, hd.ngayLap, kh.tenKH, kh.soDienThoai, nv.tenNV, nv.soDienThoai, hd.tongCong");
            
            Result result = session.run(query.toString(), Values.parameters(params));
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{
                    rec.get(0).asString(),
                    rec.get(1).isNull() ? "" : rec.get(1).asString(),
                    rec.get(2).asString(),
                    rec.get(3).isNull() ? "Khách vãng lai" : rec.get(3).asString(),
                    rec.get(4).isNull() ? "" : rec.get(4).asString(),
                    rec.get(5).asString(),
                    rec.get(6).isNull() ? "" : rec.get(6).asString(),
                    rec.get(7).isNull() ? 0.0 : rec.get(7).asDouble()
                });
            }
        }
        return ds;
    }

    public List<Object[]> getHoaDonChiTietTrongNgay(String ngay, String maNV) {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            StringBuilder query = new StringBuilder("MATCH (hd:HoaDon {ngayLap: $ngay})-[:LAP_BOI]->(nv:NhanVien) ");
            query.append("OPTIONAL MATCH (hd)-[:MUA_BOI]->(kh:KhachHang) ");
            if (maNV != null && !maNV.isEmpty() && !maNV.equals("Tất cả")) {
                query.append("WHERE nv.maNV = $maNV ");
            }
            query.append("RETURN hd.maHD, kh.soDienThoai, kh.tenKH, nv.maNV, nv.tenNV, hd.tongCong");
            
            Result result = session.run(query.toString(), Values.parameters("ngay", ngay, "maNV", maNV));
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{
                    rec.get(0).asString(),
                    rec.get(1).isNull() ? "" : rec.get(1).asString(),
                    rec.get(2).isNull() ? "Khách vãng lai" : rec.get(2).asString(),
                    rec.get(3).asString(),
                    rec.get(4).asString(),
                    rec.get(5).isNull() ? 0.0 : rec.get(5).asDouble()
                });
            }
        }
        return ds;
    }

    public Map<String, Object> getTongKetTrongNgay(String ngay, String maNV) {
        Map<String, Object> map = new HashMap<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon {ngayLap: $ngay})-[:LAP_BOI]->(nv:NhanVien) ";
            if (maNV != null && !maNV.isEmpty()) query += "WHERE nv.maNV = $maNV ";
            query += "RETURN count(hd) as count, sum(hd.tongCong) as total";
            Result result = session.run(query, Values.parameters("ngay", ngay, "maNV", maNV));
            if (result.hasNext()) {
                Record rec = result.next();
                map.put("tongSoHD", rec.get("count").asInt());
                map.put("tongTien", rec.get("total").isNull() ? 0.0 : rec.get("total").asDouble());
            }
        }
        return map;
    }
    
    public List<Thuoc> getDSThuocTheoHoaDon(String maHD) {
        List<Thuoc> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon {maHD: $ma})-[r:HAS_DETAIL]->(t:Thuoc) " +
                           "RETURN t.maThuoc, t.tenThuoc, r.soLuong, r.donGia, t.donViTinh";
            Result result = session.run(query, Values.parameters("ma", maHD));
            while (result.hasNext()) {
                Record rec = result.next();
                Thuoc t = new Thuoc();
                t.setMaThuoc(rec.get(0).asString());
                t.setTenThuoc(rec.get(1).asString());
                t.setSoLuong(rec.get(2).asInt());
                t.setGiaBan(rec.get(3).asDouble());
                t.setDonViTinh(rec.get(4).asString());
                ds.add(t);
            }
        }
        return ds;
    }

    public List<Object[]> getThongKeTheoNgayTrongThang(int thang, int nam, String maNV) {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "WHERE date(hd.ngayLap).month = $thang AND date(hd.ngayLap).year = $nam ";
            if (maNV != null && !maNV.isEmpty() && !maNV.equals("Tất cả")) {
                query += "AND nv.maNV = $maNV ";
            }
            query += "RETURN date(hd.ngayLap).day as day, count(hd) as count, sum(hd.tongCong) as total ORDER BY day";
            
            Result result = session.run(query, Values.parameters("thang", thang, "nam", nam, "maNV", maNV));
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{rec.get("day").asInt(), rec.get("count").asInt(), rec.get("total").asDouble()});
            }
        }
        return ds;
    }

    public List<Object[]> getThongKeTheoThangTrongNam(int nam, String maNV) {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "WHERE date(hd.ngayLap).year = $nam ";
            if (maNV != null && !maNV.isEmpty() && !maNV.equals("Tất cả")) {
                query += "AND nv.maNV = $maNV ";
            }
            query += "RETURN date(hd.ngayLap).month as month, count(hd) as count, sum(hd.tongCong) as total ORDER BY month";
            
            Result result = session.run(query, Values.parameters("nam", nam, "maNV", maNV));
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{rec.get("month").asInt(), rec.get("count").asInt(), rec.get("total").asDouble()});
            }
        }
        return ds;
    }

    public Map<String, Object> getTongKetTrongThang(int thang, int nam, String maNV) {
        Map<String, Object> map = new HashMap<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "WHERE date(hd.ngayLap).month = $thang AND date(hd.ngayLap).year = $nam ";
            if (maNV != null && !maNV.isEmpty() && !maNV.equals("Tất cả")) {
                query += "AND nv.maNV = $maNV ";
            }
            query += "RETURN count(hd) as count, sum(hd.tongCong) as total";
            Result result = session.run(query, Values.parameters("thang", thang, "nam", nam, "maNV", maNV));
            if (result.hasNext()) {
                Record rec = result.next();
                map.put("tongSoHD", rec.get("count").asInt());
                map.put("tongTien", rec.get("total").isNull() ? 0.0 : rec.get("total").asDouble());
            }
        }
        return map;
    }

    public Map<String, Object> getTongKetTrongNam(int nam, String maNV) {
        Map<String, Object> map = new HashMap<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "WHERE date(hd.ngayLap).year = $nam ";
            if (maNV != null && !maNV.isEmpty() && !maNV.equals("Tất cả")) {
                query += "AND nv.maNV = $maNV ";
            }
            query += "RETURN count(hd) as count, sum(hd.tongCong) as total";
            Result result = session.run(query, Values.parameters("nam", nam, "maNV", maNV));
            if (result.hasNext()) {
                Record rec = result.next();
                map.put("tongSoHD", rec.get("count").asInt());
                map.put("tongTien", rec.get("total").isNull() ? 0.0 : rec.get("total").asDouble());
            }
        }
        return map;
    }

    public List<Object[]> getHoaDonChiTietTrongThangNam(int thang, int nam) {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "OPTIONAL MATCH (hd)-[:MUA_BOI]->(kh:KhachHang) " +
                           "WHERE date(hd.ngayLap).month = $thang AND date(hd.ngayLap).year = $nam " +
                           "RETURN hd.maHD, kh.soDienThoai, kh.tenKH, nv.maNV, nv.tenNV, hd.tongCong";
            Result result = session.run(query, Values.parameters("thang", thang, "nam", nam));
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{
                    rec.get(0).asString(),
                    rec.get(1).isNull() ? "" : rec.get(1).asString(),
                    rec.get(2).isNull() ? "Khách vãng lai" : rec.get(2).asString(),
                    rec.get(3).asString(),
                    rec.get(4).asString(),
                    rec.get(5).isNull() ? 0.0 : rec.get(5).asDouble()
                });
            }
        }
        return ds;
    }

    public List<Object[]> getHoaDonChiTietTrongNam(int nam) {
        List<Object[]> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (hd:HoaDon)-[:LAP_BOI]->(nv:NhanVien) " +
                           "OPTIONAL MATCH (hd)-[:MUA_BOI]->(kh:KhachHang) " +
                           "WHERE date(hd.ngayLap).year = $nam " +
                           "RETURN hd.maHD, kh.soDienThoai, kh.tenKH, nv.maNV, nv.tenNV, hd.tongCong";
            Result result = session.run(query, Values.parameters("nam", nam));
            while (result.hasNext()) {
                Record rec = result.next();
                ds.add(new Object[]{
                    rec.get(0).asString(),
                    rec.get(1).isNull() ? "" : rec.get(1).asString(),
                    rec.get(2).isNull() ? "Khách vãng lai" : rec.get(2).asString(),
                    rec.get(3).asString(),
                    rec.get(4).asString(),
                    rec.get(5).isNull() ? 0.0 : rec.get(5).asDouble()
                });
            }
        }
        return ds;
    }
}
