LAB3: Các Mối Đe Dọa An Toàn Thông Tin (Threats and Assets)

Thông tin sinh viên:
- Họ và tên: Phạm Công Nguyên,
- Mã sinh viên (MSSV): 1150080027
- Lớp: 11_ĐH_THMT
- Tên Lab: LAB3 - Các mối đe dọa an toàn thông tin và Giám sát hệ thống

Phiên bản môi trường thực hành:
- Hệ điều hành: Windows Server 2025 Datacenter Evaluation - Version 24H2 (Build 26100.32230),
- Công cụ hỗ trợ: 
  + Python (dùng cho HTTP Server và kịch bản kiểm thử tải cục bộ),
  + Sysinternals Sysmon v15.22 (dùng để giám sát hệ thống và sự kiện Process, Registry),
  + Windows Event Viewer.

Cách dựng môi trường và thực hiện:
1. Chuẩn bị thư mục làm việc:
   - Tải và giải nén bộ tài liệu lab (LAB3_Threats_Assets.zip) vào thư mục làm việc trên máy ảo.
2. Triển khai Web Server nội bộ:
   - Sử dụng Python để khởi chạy HTTP server tại thư mục chứa mã nguồn web (www) trên cổng 8080:
     python -m http.server 8080
3. Thực thi kiểm thử tải (Load Test) cục bộ:
   - Chạy kịch bản kiểm thử hướng tới địa chỉ 127.0.0.1:8080 để mô phỏng tải an toàn trong môi trường LAB:
     python local_load_test.py
4. Cấu hình và Cài đặt Sysmon:
   - Tải công cụ Sysmon từ Microsoft Sysinternals.
   - Cài đặt dịch vụ Sysmon sử dụng tệp cấu hình XML chuẩn của lab (sysmon-lab.xml):
     Sysmon64.exe -i sysmon-lab.xml
   - Kiểm tra log sự kiện thông qua Event Viewer tại đường dẫn: Applications and Services Logs / Microsoft / Windows / Sysmon / Operational.

Các tình huống đã thực hiện:
- Khảo sát các dạng mẫu dữ liệu mô phỏng mối đe dọa, bao gồm DDOS, Mailbomb, Phishing email, Social Engineering cases.
- Dựng dịch vụ Web cục bộ và kiểm tra phản hồi kết nối HTTP thành công.
- Thực hiện kịch bản kiểm thử tải cục bộ (local_load_test.py) với giới hạn mục tiêu là 127.0.0.1:8080.
- Cài đặt cấu hình Sysmon để ghi nhận các sự kiện hệ thống, như sự kiện liên quan đến tiến trình, tạo tệp, và kết nối mạng.
- Kiểm tra, phân tích log thông qua Windows Event Viewer.

Kết quả (PASS / FAIL):
- Trạng thái chung: PASS.
- Chi tiết:
  + Khởi chạy thành công HTTP Server và kịch bản local_load_test.py đạt kết quả phản hồi tốt (ok=50, failures=0).
  + Cài đặt thành công Sysmon với tệp cấu hình XML và ghi nhận đầy đủ các sự kiện hoạt động trên hệ thống vào Event Viewer.

Lỗi gặp phải và cách khắc phục:
1. Lỗi đường dẫn cấu hình Sysmon ban đầu:
   - Mô tả: Lệnh cài đặt Sysmon ban đầu báo lỗi không tìm thấy tệp sysmon-lab.xml do chưa trỏ đúng đường dẫn thực tế hoặc thư mục hiện tại.
   - Cách khắc phục: Cung cấp đầy đủ đường dẫn tuyệt đối chính xác của tệp cấu hình XML khi gọi lệnh cài đặt Sysmon (Sysmon64.exe -i "C:\...\sysmon-lab.xml").
2. Quyền quản trị (Administrator):
   - Mô tả: Lệnh cài đặt dịch vụ Sysmon yêu cầu quyền nâng cao của hệ thống.
   - Cách khắc phục: Luôn chạy cửa sổ Command Prompt hoặc PowerShell dưới quyền Administrator trước khi thực hiện cài đặt Sysmon.