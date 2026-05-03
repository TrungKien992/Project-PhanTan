package controller;

import client.net.SocketClient;
import shared.net.Request;
import shared.net.Response;
import shared.net.ActionType;
import entity.TaiKhoan;
import gui.TaiKhoan_GUI;
import gui.ThemNhanVienSauKhiTaoTK_GUI;
import gui.TrangChu_GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

public class QuanLyTaiKhoanController {
    private JPanel view; 
    private JTable table;
    private TrangChu_GUI trangChu;

    public QuanLyTaiKhoanController(JPanel view, JTable table, TrangChu_GUI trangChu) {
        this.view = view;
        this.table = table;
        this.trangChu = trangChu;
        loadData(); 
    }

    public void loadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        Response res = SocketClient.sendRequest(new Request(ActionType.GET_ALL_TAI_KHOAN, null));
        if (res != null && res.getData() instanceof List) {
            List<TaiKhoan> list = (List<TaiKhoan>) res.getData();
            for (TaiKhoan tk : list) {
                String trangThaiText = tk.isTrangThai() ? "Hoạt động" : "Ngừng hoạt động";
                model.addRow(new Object[]{
                    tk.getMaTK(), 
                    tk.getTenTK(), 
                    tk.getQuyenHan(),
                    trangThaiText 
                });
            }
        }
    }

    public void capNhatTaiKhoan(JFrame parentFrame) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn tài khoản cần sửa!");
            return;
        }
        String maTK = table.getValueAt(row, 0).toString();
        
        TaiKhoan tkCu = (TaiKhoan) SocketClient.sendRequest(new Request(ActionType.GET_TAI_KHOAN_BY_MA, maTK)).getData(); 

        if (tkCu != null) {
            TaiKhoan_GUI dialog = new TaiKhoan_GUI(parentFrame, "Cập Nhật Tài Khoản", tkCu, null);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                TaiKhoan tkMoi = dialog.getTaiKhoan();
                tkMoi.setMaTK(tkCu.getMaTK()); 
                
                if ((boolean) SocketClient.sendRequest(new Request(ActionType.UPDATE_TAI_KHOAN, tkMoi)).getData()) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                    loadData();
                    if (trangChu != null) {
                        trangChu.refreshNhanVienTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
                }
            }
        }
    }

    public void themTaiKhoan(JFrame parentFrame) {
         String maMoi = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_TAI_KHOAN, null)).getData();
         TaiKhoan_GUI dialog = new TaiKhoan_GUI(parentFrame, "Thêm Tài Khoản Mới", null, maMoi);
         dialog.setVisible(true);

         if (dialog.isConfirmed()) {
             TaiKhoan tkMoi = dialog.getTaiKhoan();
             if ((boolean) SocketClient.sendRequest(new Request(ActionType.ADD_TAI_KHOAN, tkMoi)).getData()) {
                 try {
                     String maNVMoi = (String) SocketClient.sendRequest(new Request(ActionType.GET_NEXT_MA_NHAN_VIEN, null)).getData();
                     ThemNhanVienSauKhiTaoTK_GUI nvDialog = new ThemNhanVienSauKhiTaoTK_GUI(parentFrame, tkMoi, maNVMoi);
                     nvDialog.setVisible(true);
                     
                     if (nvDialog.isSuccess()) {
                         loadData();
                         if (trangChu != null) {
                             trangChu.refreshNhanVienTable();
                         }
                         JOptionPane.showMessageDialog(view, "Thêm thành công!");
                     } else {
                         SocketClient.sendRequest(new Request(ActionType.DELETE_TAI_KHOAN, tkMoi.getMaTK()));
                         loadData();
                         JOptionPane.showMessageDialog(view, "Đã hủy thao tác, xóa tài khoản tạm.", "Hủy", JOptionPane.WARNING_MESSAGE);
                     }
                 } catch (Exception e) { 
                     e.printStackTrace(); 
                     JOptionPane.showMessageDialog(view, "Lỗi khi tạo thông tin nhân viên!");
                 }
             } else {
                 JOptionPane.showMessageDialog(view, "Lỗi tạo tài khoản (Trùng tên?)");
             }
         }
    }
}