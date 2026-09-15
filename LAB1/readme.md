Báo cáo Lab 1: An Toàn Bảo Mật Hệ Thống

Họ và tên sinh viên: Phạm Công Nguyên
Lớp: 11_ĐH_THMT
Mã số sinh viên (MSSV): 1150080027
Chuẩn bị môi trường và Công cụ (Windows)
Thư mục làm việc: Quản lý các file cài đặt, bộ công cụ (Rufus, Kali Linux ISO, Fortect, SophosInstall.exe) trong thư mục Downloads.  
Wireshark trên Windows: Khởi chạy Wireshark và ghi nhận trạng thái cảnh báo yêu cầu cài đặt driver bắt gói tin (Local interfaces are unavailable because no packet capture driver is installed. You can fix this by installing Npcap).  
Phần mềm bảo mật: Kiểm tra trạng thái hoạt động của Sophos Home Premium Trial (Đã kích hoạt bảo vệ toàn diện với Malware Protection, Web Protection, Ransomware Protection và Malicious Traffic Detection ở trạng thái You are protected!).  T
hử nghiệm trên Máy ảo Linux (VirtualBox)Môi trường: Chạy máy ảo Ubuntu/Kali Linux trên VirtualBox.  
Xác thực hệ thống: Thực hiện câu lệnh kiểm tra danh tính người dùng trên terminal:
whoami
Kết quả trả về: phamcongnguyen  
Khảo sát mã nguồn và Thử nghiệm Keylogger trên WindowsCấu trúc mã nguồn: Thư mục mã nguồn keylogger bao gồm các thành phần chính:klog_main.cpp (File mã nguồn C++ chính)  README.md (Tài liệu hướng dẫn)  
Thư mục logs (Nơi lưu trữ file ghi log hoạt động)  
Thực thi chương trình: Biên dịch và chạy file keylogger.exe trên cửa sổ Developer Command Prompt cho VS 2022.  