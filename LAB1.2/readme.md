Báo cáo Lab 1: Bắt và Phân tích gói tin Telnet - SSH



Họ và tên: Phạm Công Nguyên

MSSV: 1150080027

Lab 1 - Examining SSH & Telnet in Wireshark







1. Nội dung đã thực hiện

Thiết lập môi trường thực hành phân tích mạng

Cài đặt và cấu hình dịch vụ SSH Server trên môi trường Linux

Sử dụng công cụ Wireshark để bắt các gói tin mạng trên giao diện card loopback lo

Thực hiện kết nối từ Client đến Server thông qua giao thức SSH (Cổng 22)

Quan sát, phân tích và so sánh đặc tính bảo mật của kênh truyền





2. Kết quả thực hiện

Bắt gói tin SSH: Đã ghi nhận thành công các gói tin bắt tay TCP (SYN, ACK) và quá trình trao đổi khóa mã hóa SSHv2 qua bộ lọc `tcp.port == 22`

Phân tích bảo mật: Chứng minh rằng toàn bộ nội dung trao đổi (payload, thông tin xác thực, câu lệnh) đã được mã hóa hoàn toàn, không thể đọc được bằng mắt thường trên Wireshark





3. Link Video Demo

Link YouTube quay quá trình thực hiện Lab: "https://www.youtube.com/watch?v=wFTHtRF26vw&t=1s"






Bài làm được thực hiện và kiểm chứng trực tiếp trên môi trường máy ảo Kali Linux.

Sử dụng card mạng Loopback (`127.0.0.1`) để mô phỏng kết nối nội bộ giữa Client và Server

