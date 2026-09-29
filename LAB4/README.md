Họ và tên: Phạm Công Nguyên

Mã sinh viên: 1150080027

Lớp: 11 ĐH THMT





Tên bài thực hành: Lab 4 Khảo sát và đánh giá bề mặt mạng bằng Nmap

Link video thực hành: https://youtu.be/SjH8QdCQHA8?si=ye7JTTJiSrxl-3bX





PHIÊN BẢN MÔI TRƯỜNG THỰC HÀNH

Nền tảng ảo hóa: VMware Workstation chạy mạng Host-Only dải 192.168.200.0/24 hoàn toàn cô lập.





Máy quét chính: Kali Linux 2026.2, nhân Linux 6.19, cài Nmap phiên bản 7.99, Zenmap 7.99, xsltproc, địa chỉ IP 192.168.200.134.

Máy đích có sẵn lỗ hổng: Metasploitable 2 nhân Linux 2.6, địa chỉ IP 192.168.200.135.

Máy đích đối chiếu: Windows Server 2025, Nmap phiên bản 7.80, Npcap 0.9982, địa chỉ IP 192.168.200.128, Gateway 192.168.200.2.





CÁCH DỰNG MÔI TRƯỜNG

Bước 1: Cấu hình card mạng của cả ba máy ảo sang chế độ Host-Only để cùng hoạt động trong dải mạng nội bộ 192.168.200.0/24.

Bước 2: Cài đặt bộ công cụ Nmap, Npcap và Zenmap trên máy ảo Windows Server 2025 bằng công cụ winget.

Bước 3: Mở quy tắc cho phép lưu lượng ICMPv4 trên Windows Defender Firewall để máy Kali có thể ping kiểm tra kết nối.

Bước 4: Chạy lệnh ping từ máy Kali tới cả hai máy đích, xác nhận kết nối mạng thông suốt với tỷ lệ mất gói 0 phần trăm.





CÁC TÌNH HUỐNG ĐÃ THỰC HIỆN

Tình huống 1: Rà soát phát hiện host đang hoạt động trong toàn mạng bằng kỹ thuật nmap -sn 192.168.200.0/24, phát hiện 6 host đang hoạt động trong dải mạng.

Tình huống 2: Khảo sát cổng TCP Connect bằng lệnh nmap -sT và quét SYN Stealth bằng lệnh nmap -sS, ghi nhận 23 cổng mở và 977 cổng đóng.

Tình huống 3: Thăm dò cờ nâng cao Xmas scan -sX và ACK scan -sA nhằm kiểm tra phản ứng của ngăn xếp mạng TCP và khảo sát chính sách lọc của tường lửa.

Tình huống 4: Khảo sát 20 cổng UDP phổ biến bằng lệnh nmap -sU --top-ports 20, ghi nhận 2 cổng mở, 3 cổng open/filtered và 15 cổng đóng.

Tình huống 5: Nhận diện chính xác phiên bản dịch vụ bằng lệnh nmap -sV và nhận diện hệ điều hành toàn diện bằng lệnh nmap -A.

Tình huống 6: Thu thập thông tin giao thức SMB và kiểm tra lỗ hổng bảo mật MS17-010 bằng các script NSE trên cổng 445 cho cả Linux và Windows.

Tình huống 7: Xuất dữ liệu ra ba định dạng bằng tùy chọn -oA ketqua-lab4 và chuyển đổi file XML thành giao diện web HTML bằng xsltproc.

Tình huống 8: Thực hành kịch bản làm cứng hệ thống hardening bằng cách cấu hình iptables DROP cổng FTP 21 trên Metasploitable 2 và quét lại để chứng minh cổng chuyển từ open sang filtered.





KẾT QUẢ ĐÁNH GIÁ

Kết quả chung: PASS.

Hoàn thành đầy đủ 100 phần trăm các bài thực nghiệm theo yêu cầu, thu thập đủ danh mục ảnh minh chứng và trả lời trọn vẹn các câu hỏi phân tích lý thuyết.





LỖI GẶP PHẢI VÀ CÁCH KHẮC PHỤC

Lỗi dừng dịch vụ FTP trên Metasploitable 2: Khi gõ lệnh dừng dịch vụ trong thư mục /etc/init.d/vsftpd hệ thống báo lỗi command not found do không có script khởi động sẵn. Cách khắc phục: Sử dụng quy tắc lọc gói mạng bằng lệnh sudo iptables -A INPUT -p tcp --dport 21 -j DROP để chặn gói tin thăm dò, làm trạng thái cổng 21 chuyển từ open sang filtered thành công.

Lỗi ping máy ảo Windows Server 2025: Máy Kali ban đầu không nhận được phản hồi ping từ Windows do tường lửa mặc định chặn ICMP. Cách khắc phục: Mở Command Prompt quyền quản trị trên Windows và chạy lệnh netsh advfirewall firewall add rule name=Allow ICMPv4 protocol=icmpv4:8,any dir=in action=allow để mở thông luồng kiểm tra.



