# Hướng Dẫn Chạy Dự Án Quản Lý Hiệu Thuốc Tây (Distributed)

Dự án này là hệ thống quản lý hiệu thuốc tây được xây dựng theo kiến trúc phân tán (Client-Server) sử dụng Java Swing cho giao diện và Neo4j cho cơ sở dữ liệu.

## 1. Yêu cầu hệ thống
- **Java SDK**: Phiên bản 17 hoặc 21 (Dự án đang chạy tốt trên JDK 21).
- **Cơ sở dữ liệu**: Neo4j (Phiên bản 4.x hoặc 5.x).
- **IDE**: Eclipse (Khuyên dùng) hoặc IntelliJ IDEA.

## 2. Cấu hình Cơ sở dữ liệu (Neo4j)
1. Mở **Neo4j Desktop**.
2. Tạo một Database mới (hoặc dùng Database hiện có).
3. Đảm bảo mật khẩu của tài khoản `neo4j` là: **`12345678`** 
   *(Nếu bạn dùng mật khẩu khác, hãy cập nhật tại file `Server_Module/src/server/config/Neo4jConfig.java`)*.
4. Nhấn **Start** để khởi chạy Database.
5. (Tùy chọn) Để đăng nhập được lần đầu, bạn cần chạy tập lệnh Cypher để tạo tài khoản admin (xem phần dữ liệu mẫu nếu Database trống).

## 3. Các bước khởi chạy dự án

### Bước 1: Khởi động Server
- Tìm đến file: `Server_Module/src/server/net/SocketServer.java`.
- Chuột phải chọn **Run As -> Java Application**.
- Kiểm tra Console: Nếu hiện dòng `Server is running on port 12345...` là thành công.

### Bước 2: Khởi động Client
- Tìm đến file: `Client_Module/src/gui/Dangnhap_GUI.java`.
- Chuột phải chọn **Run As -> Java Application**.
- Màn hình Đăng nhập sẽ hiện ra.

## 4. Thông tin Đăng nhập mặc định
Nếu bạn đã khởi tạo dữ liệu theo hướng dẫn trước đó:
- **Tên đăng nhập**: `admin`
- **Mật khẩu**: `admin`

## 5. Lưu ý quan trọng
- **Lỗi Socket**: Đảm bảo Server phải được chạy **TRƯỚC** khi mở Client.
- **Lỗi Hình ảnh**: Nếu icon hoặc ảnh nền không hiển thị, hãy đảm bảo thư mục `img` nằm ở thư mục gốc của project (ngang hàng với `Client_Module`, `Server_Module`).
- **Lỗi Cổng (Port)**: Nếu báo lỗi `Address already in use`, hãy kiểm tra xem có bản Server nào đang chạy ngầm không và tắt nó đi (Terminated trong Eclipse Console).

---
*Chúc bạn vận hành hệ thống thành công!*
