package server.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Values;
import org.neo4j.driver.Record;
import entity.PhieuDatHang;
import entity.ChiTietPhieuDatHang;
import entity.NhaCungCap;
import entity.NhanVien;
import entity.Thuoc;
import server.config.Neo4jConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class phieuDatHang_DAO {
    private Driver driver;

    public phieuDatHang_DAO() {
        this.driver = Neo4jConfig.getDriver();
    }

    public boolean themPhieuDatHang(PhieuDatHang p) {
        try (Session session = driver.session()) {
            String query = "MATCH (nv:NhanVien {maNV: $maNV}), (ncc:NhaCungCap {maNhaCungCap: $maNCC}) " +
                           "CREATE (p:PhieuDatHang {maPhieu: $ma, ngayDat: $ngay, ghiChu: $ghiChu, trangThai: $tt, tongTien: $tongTien}) " +
                           "CREATE (p)-[:LAP_BOI]->(nv) " +
                           "CREATE (p)-[:DAT_TU]->(ncc) " +
                           "WITH p " +
                           "UNWIND $details as d " +
                           "MATCH (t:Thuoc {maThuoc: d.maThuoc}) " +
                           "CREATE (p)-[:HAS_DETAIL {soLuong: d.soLuong, donGia: d.donGia}]->(t) " +
                           "RETURN p";
            
            List<Map<String, Object>> details = p.getChiTietList().stream().map(d -> {
                Map<String, Object> map = new HashMap<>();
                map.put("maThuoc", d.getThuoc().getMaThuoc());
                map.put("soLuong", d.getSoLuong());
                map.put("donGia", d.getDonGia());
                return map;
            }).collect(Collectors.toList());

            Result result = session.run(query, Values.parameters(
                "maNV", p.getNhanVien().getMaNV(),
                "maNCC", p.getNhaCungCap().getMaNhaCungCap(),
                "ma", p.getMaPhieu(),
                "ngay", p.getNgayDat().toString(),
                "ghiChu", p.getGhiChu(),
                "tt", p.getTrangThai(),
                "tongTien", p.getTongTien(),
                "details", details
            ));
            return result.hasNext();
        }
    }

    public boolean capNhatPhieuDatHang(PhieuDatHang p) {
        // Simple implementation: delete relations and recreate
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDatHang {maPhieu: $ma}) " +
                           "SET p.ngayDat = $ngay, p.ghiChu = $ghiChu, p.trangThai = $tt, p.tongTien = $tongTien " +
                           "WITH p " +
                           "MATCH (p)-[r:HAS_DETAIL]->() DELETE r " +
                           "WITH p " +
                           "UNWIND $details as d " +
                           "MATCH (t:Thuoc {maThuoc: d.maThuoc}) " +
                           "CREATE (p)-[:HAS_DETAIL {soLuong: d.soLuong, donGia: d.donGia}]->(t) " +
                           "RETURN p";
            
            List<Map<String, Object>> details = p.getChiTietList().stream().map(d -> {
                Map<String, Object> map = new HashMap<>();
                map.put("maThuoc", d.getThuoc().getMaThuoc());
                map.put("soLuong", d.getSoLuong());
                map.put("donGia", d.getDonGia());
                return map;
            }).collect(Collectors.toList());

            Result result = session.run(query, Values.parameters(
                "ma", p.getMaPhieu(),
                "ngay", p.getNgayDat().toString(),
                "ghiChu", p.getGhiChu(),
                "tt", p.getTrangThai(),
                "tongTien", p.getTongTien(),
                "details", details
            ));
            return result.hasNext();
        }
    }

    public boolean huyPhieuDatHang(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDatHang {maPhieu: $ma}) SET p.trangThai = 'Đã hủy' RETURN p";
            Result result = session.run(query, Values.parameters("ma", ma));
            return result.hasNext();
        }
    }

    public List<PhieuDatHang> layTatCaPhieuDatHang() {
        List<PhieuDatHang> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDatHang)-[:LAP_BOI]->(nv:NhanVien), (p)-[:DAT_TU]->(ncc:NhaCungCap) " +
                           "RETURN p, nv, ncc";
            Result result = session.run(query);
            while (result.hasNext()) {
                ds.add(mapRecordToPhieu(result.next()));
            }
        }
        return ds;
    }

    public PhieuDatHang layPhieuDatHangTheoMa(String ma) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDatHang {maPhieu: $ma})-[:LAP_BOI]->(nv:NhanVien), (p)-[:DAT_TU]->(ncc:NhaCungCap) " +
                           "RETURN p, nv, ncc";
            Result result = session.run(query, Values.parameters("ma", ma));
            if (result.hasNext()) {
                return mapRecordToPhieu(result.next());
            }
        }
        return null;
    }

    public List<ChiTietPhieuDatHang> layChiTietTheoMaPhieu(String ma) {
        List<ChiTietPhieuDatHang> ds = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDatHang {maPhieu: $ma})-[r:HAS_DETAIL]->(t:Thuoc) " +
                           "RETURN r.soLuong as soLuong, r.donGia as donGia, t.maThuoc as maThuoc, t.tenThuoc as tenThuoc";
            Result result = session.run(query, Values.parameters("ma", ma));
            while (result.hasNext()) {
                Record rec = result.next();
                ChiTietPhieuDatHang ct = new ChiTietPhieuDatHang();
                ct.setSoLuong(rec.get("soLuong").asInt());
                ct.setDonGia(rec.get("donGia").asDouble());
                Thuoc t = new Thuoc();
                t.setMaThuoc(rec.get("maThuoc").asString());
                t.setTenThuoc(rec.get("tenThuoc").asString());
                ct.setThuoc(t);
                ds.add(ct);
            }
        }
        return ds;
    }

    public String taoMaPhieuMoi() {
        try (Session session = driver.session()) {
            String query = "MATCH (p:PhieuDatHang) RETURN p.maPhieu ORDER BY p.maPhieu DESC LIMIT 1";
            Result result = session.run(query);
            if (result.hasNext()) {
                String lastMa = result.next().get(0).asString();
                int number = Integer.parseInt(lastMa.substring(3)) + 1;
                return String.format("PDH%03d", number);
            }
        }
        return "PDH001";
    }

    private PhieuDatHang mapRecordToPhieu(Record record) {
        PhieuDatHang p = new PhieuDatHang();
        var nodeP = record.get("p").asNode();
        p.setMaPhieu(nodeP.get("maPhieu").asString());
        p.setNgayDat(java.time.LocalDate.parse(nodeP.get("ngayDat").asString()));
        p.setGhiChu(nodeP.get("ghiChu").asString());
        p.setTrangThai(nodeP.get("trangThai").asString());
        p.setTongTien(nodeP.get("tongTien").asDouble());

        NhanVien nv = new NhanVien();
        nv.setMaNV(record.get("nv").asNode().get("maNV").asString());
        p.setNhanVien(nv);

        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNhaCungCap(record.get("ncc").asNode().get("maNhaCungCap").asString());
        ncc.setTenNhaCungCap(record.get("ncc").asNode().get("tenNhaCungCap").asString());
        p.setNhaCungCap(ncc);

        return p;
    }
}
