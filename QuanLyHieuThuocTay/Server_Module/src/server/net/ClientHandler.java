package server.net;

import java.io.*;
import java.net.Socket;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import server.dao.*;
import entity.*;
import java.time.LocalDate;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private thuoc_DAO thuocDao;
    private hoaDon_DAO hoaDonDao;
    private keThuoc_DAO keThuocDao;
    private khuyenMai_DAO khuyenMaiDao;
    private khachHang_DAO khachHangDao;
    private phieuDatHang_DAO phieuDatHangDao;
    private nhaCungCap_DAO nhaCungCapDao;
    private nhanVien_DAO nhanVienDao;
    private taiKhoan_DAO taiKhoanDao;
    private phieuCho_DAO phieuChoDao;
    private ctPhieuCho_DAO ctPhieuChoDao;
    private thue_DAO thueDao;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.thuocDao = new thuoc_DAO();
        this.hoaDonDao = new hoaDon_DAO();
        this.keThuocDao = new keThuoc_DAO();
        this.khuyenMaiDao = new khuyenMai_DAO();
        this.khachHangDao = new khachHang_DAO();
        this.phieuDatHangDao = new phieuDatHang_DAO();
        this.nhaCungCapDao = new nhaCungCap_DAO();
        this.nhanVienDao = new nhanVien_DAO();
        this.taiKhoanDao = new taiKhoan_DAO();
        this.phieuChoDao = new phieuCho_DAO();
        this.ctPhieuChoDao = new ctPhieuCho_DAO();
        this.thueDao = new thue_DAO();
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Request request = (Request) in.readObject();
                if (request == null) break;

                Response response = handleRequest(request);
                out.writeObject(response);
                out.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Response handleRequest(Request request) {
        ActionType action = request.getAction();
        Object data = request.getData();

        try {
            switch (action) {
                case GET_ALL_THUOC:
                    return new Response("OK", thuocDao.getAllThuoc());
                case GET_ALL_ACTIVE_THUOC:
                    return new Response("OK", thuocDao.getAllActiveThuoc());
                case ADD_THUOC:
                    boolean added = thuocDao.themThuoc((Thuoc) data);
                    return new Response(added ? "OK" : "ERROR", added);
                case UPDATE_THUOC:
                    boolean updated = thuocDao.updateThuoc((Thuoc) data);
                    return new Response(updated ? "OK" : "ERROR", updated);
                case GET_THUOC_BY_MA:
                    return new Response("OK", thuocDao.getThuocTheoMa((String) data));
                case GET_NEXT_MA_THUOC:
                    return new Response("OK", thuocDao.getNextMaThuoc());
                case GET_ALL_TEN_THUOC:
                    return new Response("OK", thuocDao.getAllTenThuoc());
                case SEARCH_THUOC_FOR_SALE:
                    Object[] params = (Object[]) data;
                    return new Response("OK", thuocDao.searchThuoc((String)params[0], (String)params[1], (String)params[2]));
                case UPDATE_TRANG_THAI_THUOC:
                    Object[] ttParams = (Object[]) data;
                    return new Response("OK", thuocDao.updateTrangThai((String)ttParams[0], (String)ttParams[1]));
                case UPDATE_THUOC_QUANTITY:
                    Object[] qParams = (Object[]) data;
                    return new Response("OK", thuocDao.updateSoLuong((String)qParams[0], (int)qParams[1]));
                
                case ADD_HOA_DON:
                    boolean hdAdded = hoaDonDao.themHoaDon((HoaDon) data);
                    return new Response(hdAdded ? "OK" : "ERROR", hdAdded);
                case GET_ALL_HOA_DON:
                    return new Response("OK", hoaDonDao.getAllHoaDon());
                case GET_DETAILS_BY_HOA_DON:
                    return new Response("OK", hoaDonDao.getChiTietHoaDonTheoMa((String) data));
                case GET_NEXT_MA_HOA_DON:
                    return new Response("OK", hoaDonDao.generateNewMaHD());
                case GET_HOA_DON_BY_MA:
                    return new Response("OK", hoaDonDao.getHoaDonByMa((String) data));

                case GET_ALL_KE_THUOC:
                    return new Response("OK", keThuocDao.getAllKeThuoc());
                case GET_ALL_KHUYEN_MAI:
                    return new Response("OK", khuyenMaiDao.getAllKhuyenMai());
                case GET_KHUYEN_MAI_BY_MA:
                    return new Response("OK", khuyenMaiDao.getKhuyenMaiByMa((String) data));
                case ADD_KHUYEN_MAI:
                    return new Response("OK", khuyenMaiDao.addKhuyenMai((KhuyenMai) data));
                case UPDATE_KHUYEN_MAI:
                    return new Response("OK", khuyenMaiDao.updateKhuyenMai((KhuyenMai) data));
                case DELETE_KHUYEN_MAI:
                    return new Response("OK", khuyenMaiDao.deleteKhuyenMai((String) data));
                case SEARCH_KHUYEN_MAI:
                    Object[] kmParams = (Object[]) data;
                    return new Response("OK", khuyenMaiDao.searchKhuyenMai((String)kmParams[0], (LocalDate)kmParams[1], (LocalDate)kmParams[2], (int)kmParams[3]));
                case GET_NEXT_MA_KHUYEN_MAI:
                    return new Response("OK", khuyenMaiDao.generateNewMaKM());
                case GET_THUE_BY_MA:
                    return new Response("OK", thueDao.getThueByMa((String) data));
                case GET_KHACH_HANG_BY_SDT:
                    return new Response("OK", khachHangDao.getKhachHangTheoSDT((String) data));
                case SEARCH_KHACH_HANG:
                    Object[] khParams = (Object[]) data;
                    return new Response("OK", khachHangDao.searchKhachHang((String)khParams[0], (String)khParams[1], (String)khParams[2], (String)khParams[3]));
                case GET_ALL_KHACH_HANG:
                    return new Response("OK", khachHangDao.getAllKhachHang());
                case ADD_KHACH_HANG:
                    return new Response("OK", khachHangDao.themKhachHang((KhachHang) data));
                case UPDATE_KHACH_HANG:
                    return new Response("OK", khachHangDao.updateKhachHang((KhachHang) data));
                case DELETE_KHACH_HANG:
                    return new Response("OK", khachHangDao.deleteKhachHang((String) data));
                case GET_KHACH_HANG_BY_MA:
                    return new Response("OK", khachHangDao.getKhachHangTheoMa((String) data));
                case GET_NEXT_MA_KHACH_HANG:
                    return new Response("OK", khachHangDao.generateNewMaKH());
                case GET_ALL_PHIEU_DAT_HANG:
                    return new Response("OK", phieuDatHangDao.layTatCaPhieuDatHang());
                case ADD_PHIEU_DAT_HANG:
                    return new Response("OK", phieuDatHangDao.themPhieuDatHang((PhieuDatHang) data));
                case UPDATE_PHIEU_DAT_HANG:
                    return new Response("OK", phieuDatHangDao.capNhatPhieuDatHang((PhieuDatHang) data));
                case CANCEL_PHIEU_DAT_HANG:
                    return new Response("OK", phieuDatHangDao.huyPhieuDatHang((String) data));
                case GET_PHIEU_DAT_HANG_BY_MA:
                    return new Response("OK", phieuDatHangDao.layPhieuDatHangTheoMa((String) data));
                case GET_DETAILS_BY_PHIEU_DAT_HANG:
                    return new Response("OK", phieuDatHangDao.layChiTietTheoMaPhieu((String) data));
                case GET_NEXT_MA_PHIEU_DAT_HANG:
                    return new Response("OK", phieuDatHangDao.taoMaPhieuMoi());
                case GET_NHA_CUNG_CAP_BY_TEN:
                    return new Response("OK", nhaCungCapDao.getNhaCungCapTheoTen((String) data));
                case GET_ALL_NHA_CUNG_CAP:
                    return new Response("OK", nhaCungCapDao.getAllNhaCungCap());
                case ADD_NHA_CUNG_CAP:
                    return new Response("OK", nhaCungCapDao.themNhaCungCap((NhaCungCap) data));
                case UPDATE_NHA_CUNG_CAP:
                    return new Response("OK", nhaCungCapDao.updateNhaCungCap((NhaCungCap) data));
                case GET_NHA_CUNG_CAP_BY_MA:
                    return new Response("OK", nhaCungCapDao.getNhaCungCapTheoMa((String) data));
                case GET_NEXT_MA_NHA_CUNG_CAP:
                    return new Response("OK", nhaCungCapDao.generateNewMaNCC());

                case GET_DS_THUOC_BY_HOA_DON:
                    return new Response("OK", hoaDonDao.getDSThuocTheoHoaDon((String) data));
                case ADD_PHIEU_DOI_TRA:
                    return new Response("OK", new server.dao.PhieuDoiTra_DAO().themPhieuDoiTra((PhieuDoiTra) data));
                case ADD_CT_PHIEU_DOI_TRA:
                    return new Response("OK", new server.dao.PhieuDoiTra_DAO().themCTPhieuDoiTra((CTPhieuDoiTra) data));
                case GET_NEXT_MA_PHIEU_DOI_TRA:
                    return new Response("OK", new server.dao.PhieuDoiTra_DAO().taoMaPDTMoi());
                case CHECK_PHIEU_DOI_TRA_BY_HD:
                    return new Response("OK", new server.dao.PhieuDoiTra_DAO().getPhieuDoiTraByMaHD((String) data));
                case GET_ALL_NHAN_VIEN:
                    return new Response("OK", nhanVienDao.getAllNhanVien());
                case GET_NHAN_VIEN_BY_MA_TK:
                    return new Response("OK", nhanVienDao.getNhanVienByMaTK((String) data));
                case GET_NHAN_VIEN_BY_MA:
                    return new Response("OK", nhanVienDao.getNhanVienTheoMa((String) data));
                case GET_NEXT_MA_NHAN_VIEN:
                    return new Response("OK", nhanVienDao.generateNewMaNV());
                case GENERATE_NEW_MA_NV_FROM_TABLE:
                    return new Response("OK", nhanVienDao.generateNewMaNV_FromTable());
                case DELETE_NHAN_VIEN:
                    return new Response("OK", nhanVienDao.deleteNhanVien((String) data));
                case CHECK_SDT_TON_TAI:
                case CHECK_SDT_TON_TAI_NV:
                    return new Response("OK", nhanVienDao.checkSdtTonTai((String) data));
                case ADD_NHAN_VIEN:
                    return new Response("OK", nhanVienDao.themNhanVien((NhanVien) data));
                case UPDATE_NHAN_VIEN:
                    return new Response("OK", nhanVienDao.updateNhanVien((NhanVien) data));

                case GET_ALL_CHUC_VU:
                    return new Response("OK", new server.dao.chucVu_DAO().getAllChucVu());
                case GET_CHUC_VU_BY_MA:
                    return new Response("OK", new server.dao.chucVu_DAO().getChucVuTheoMa((String) data));
                case GET_CHUC_VU_BY_TEN:
                    return new Response("OK", new server.dao.chucVu_DAO().getChucVuTheoTen((String) data));
                case GET_ALL_TAI_KHOAN:
                    return new Response("OK", taiKhoanDao.getAllTaiKhoan());
                case ADD_TAI_KHOAN:
                    return new Response("OK", taiKhoanDao.create((TaiKhoan) data));
                case UPDATE_TAI_KHOAN:
                    return new Response("OK", taiKhoanDao.update((TaiKhoan) data));
                case DELETE_TAI_KHOAN:
                    return new Response("OK", taiKhoanDao.deleteTaiKhoanPermanently((String) data));
                case GET_TAI_KHOAN_BY_MA:
                    return new Response("OK", taiKhoanDao.getTaiKhoanByMa((String) data));
                case GET_NEXT_MA_TAI_KHOAN:
                    return new Response("OK", taiKhoanDao.generateNewMaTK());
                case LOGIN:
                    String[] credentials = (String[]) data;
                    return new Response("OK", taiKhoanDao.checkLogin(credentials[0], credentials[1]));

                case GET_ALL_PHIEU_CHO:
                    return new Response("OK", phieuChoDao.getAllPhieuCho());
                case ADD_PHIEU_CHO:
                    return new Response("OK", phieuChoDao.addPhieuCho((PhieuChoThanhToan) data));
                case DELETE_PHIEU_CHO:
                    return new Response("OK", phieuChoDao.xoaPhieuCho((String) data));
                case DELETE_ALL_PHIEU_CHO:
                    return new Response("OK", phieuChoDao.xoaTatCaPhieuCho());
                case GET_NEXT_MA_PHIEU_CHO:
                    return new Response("OK", phieuChoDao.generateNextMaPhieuCho());
                case GET_DETAILS_BY_PHIEU_CHO:
                    return new Response("OK", ctPhieuChoDao.getChiTietTheoMaPhieuCho((String) data));
                case GET_PHIEU_CHO_BY_MA:
                    return new Response("OK", phieuChoDao.getPhieuChoTheoMa((String) data));
                case GET_ALL_HOA_DON_CHI_TIET_FOR_TABLE:
                    return new Response("OK", hoaDonDao.getAllHoaDonChiTietForTable());
                case SEARCH_HOA_DON_CHI_TIET:
                    Object[] sParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.searchHoaDonChiTiet((String)sParams[0], (String)sParams[1], (String)sParams[2], (String)sParams[3], (String)sParams[4], (String)sParams[5]));
                case GET_HOA_DON_CHI_TIET_TRONG_NGAY:
                    Object[] dParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getHoaDonChiTietTrongNgay(dParams[0].toString(), (String)dParams[1]));
                case GET_TONG_KET_TRONG_NGAY:
                    Object[] tkParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getTongKetTrongNgay(tkParams[0].toString(), (String)tkParams[1]));
                case GET_THONG_KE_THEO_NGAY_TRONG_THANG:
                    Object[] nParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getThongKeTheoNgayTrongThang((int)nParams[0], (int)nParams[1], (String)nParams[2]));
                case GET_THONG_KE_THEO_THANG_TRONG_NAM:
                    Object[] yParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getThongKeTheoThangTrongNam((int)yParams[0], (String)yParams[1]));
                case GET_TONG_KET_TRONG_THANG:
                    Object[] tktParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getTongKetTrongThang((int)tktParams[0], (int)tktParams[1], (String)tktParams[2]));
                case GET_TONG_KET_TRONG_NAM:
                    Object[] tknParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getTongKetTrongNam((int)tknParams[0], (String)tknParams[1]));
                case GET_HOA_DON_CHI_TIET_TRONG_THANG_NAM:
                    Object[] hdtParams = (Object[]) data;
                    return new Response("OK", hoaDonDao.getHoaDonChiTietTrongThangNam((int)hdtParams[0], (int)hdtParams[1]));
                case GET_HOA_DON_CHI_TIET_TRONG_NAM:
                    return new Response("OK", hoaDonDao.getHoaDonChiTietTrongNam((int) data));
                case GET_THONG_KE_THUOC_BAN_CHAY:
                    return new Response("OK", new server.dao.chiTietHoaDon_DAO().getThongKeThuocBanChay());
                default:
                    return new Response("ERROR", null, "Action not supported yet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response("ERROR", null, e.getMessage());
        }
    }
}
