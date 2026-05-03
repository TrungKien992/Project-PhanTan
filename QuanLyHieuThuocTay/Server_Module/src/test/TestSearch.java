package test;

import server.dao.hoaDon_DAO;
import java.util.List;

public class TestSearch {
    public static void main(String[] args) {
        hoaDon_DAO dao = new hoaDon_DAO();
        System.out.println("Searching with sdtNV='lai'");
        List<Object[]> results = dao.searchHoaDonChiTiet("", "", "", "", "lai", "");
        System.out.println("Results found: " + results.size());
        for (Object[] row : results) {
            System.out.println(java.util.Arrays.toString(row));
        }
    }
}
