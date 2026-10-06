Tên: Phạm Công Nguyên

Lớp: 11\_ĐH\_THMT

MSSV: 1150080027



BÁO CÁO TÓM TẮT THỰC HÀNH: XÂY DỰNG VÀ CẤU HÌNH TƯỜNG LỬA PFSENSE   



1. Thiết lập hạ tầng và cấu hình nền tảng

Cấu hình máy ảo pfSense trên VMware với ba giao diện mạng: cổng WAN nhận DHCP, cổng LAN đặt địa chỉ 10.0.0.1/8, cổng DMZ (giao diện OPT1) đặt địa chỉ 172.16.0.1/16.

Cấu hình máy Windows Server đóng vai trò quản trị trong mạng LAN với địa chỉ 10.0.0.2/8, trỏ Gateway về 10.0.0.1 và DNS về 8.8.8.8.

Hoàn tất các bước Setup Wizard trên WebGUI pfSense, tắt các tùy chọn chặn dải mạng ảo và kích hoạt cơ chế Hybrid Outbound NAT.

Vô hiệu hóa hai luật mặc định trên nhánh LAN, tạo luật nền tảng cho phép LAN ra Internet và thực hiện kiểm thử đối chứng thành công qua lệnh ping trước và sau khi tắt luật.   

2\. Tình huống 1: Chặn ICMP, cho phép Web và DNS từ mạng LAN

Thiết lập bộ luật trên nhánh LAN theo thứ tự từ trên xuống dưới: Anti-Lockout Rule, luật Block ICMP, luật Pass DNS qua cổng 53, luật Pass Web qua cổng 80 và 443.

Xóa bảng trạng thái cũ bằng chức năng Reset States.

Kết quả kiểm thử từ máy Windows Server: Lệnh ping 8.8.8.8 bị chặn hoàn toàn (Request timed out), lệnh nslookup và lệnh curl truy cập trang web thành công sau khi đồng bộ DNS trên card mạng về 8.8.8.8.   



3\. Tình huống 2: Chỉ cho phép duy nhất một máy trạm ra Internet

Bổ sung máy ảo Ubuntu vào nhánh LAN với địa chỉ tĩnh 10.0.0.3/8 và gateway 10.0.0.1 để làm thiết bị kiểm thử đối chứng.

Thiết lập bộ luật trên nhánh LAN gồm hai luật chính: Cho phép duy nhất địa chỉ máy quản trị 10.0.0.2 giao thức Any ra Internet, bên dưới là luật chặn toàn bộ dải mạng LAN subnets còn lại.

Kết quả kiểm thử: Máy Windows Server 10.0.0.2 ping ra Internet thành công, máy Ubuntu 10.0.0.3 bị tường lửa chặn hoàn toàn với tỉ lệ mất gói 100 phần trăm.   



4\. Tình huống 3: Cô lập vùng DMZ khỏi mạng nội bộ LAN

Chuyển card mạng máy ảo Ubuntu sang phân vùng DMZ và gán địa chỉ 172.16.0.2/16 với gateway 172.16.0.1.

Thiết lập bộ luật trên nhánh DMZ theo thứ tự: Luật chặn kết nối từ DMZ sang dải mạng LAN subnets đặt ở trên, luật cho phép DMZ truy cập ra Internet đặt ở dưới.

Kết quả kiểm thử từ máy Ubuntu: Lệnh ping ra Internet 8.8.8.8 thành công với 0 phần trăm mất gói, lệnh ping vào máy Windows Server 10.0.0.2 trong mạng LAN bị chặn hoàn toàn.   



5\. Tình huống 4: Cấu hình Port Forwarding dịch vụ Web từ DMZ

Khởi chạy dịch vụ Web trên máy chủ Ubuntu trong vùng DMZ lắng nghe tại cổng 80.

Cấu hình NAT Port Forward trên giao diện WAN của pfSense: Chuyển tiếp cổng 80 từ địa chỉ IP WAN trỏ thẳng về IP 172.16.0.2 tại cổng 80, tự động liên kết tạo luật mở tường lửa tương ứng.

Kết quả kiểm thử: Thiết bị từ bên ngoài mạng WAN truy cập vào IP WAN của pfSense qua giao thức HTTP được chuyển hướng thành công đến trang web của máy chủ DMZ.   



6\. Nguyên lý kỹ thuật cốt lõi

Nguyên lý First-match: Tường lửa pfSense kiểm tra các gói tin theo thứ tự từ trên xuống dưới, gói tin khớp luật nào sẽ áp dụng ngay hành động đó và bỏ qua các luật phía sau. Do đó các luật chặn cụ thể luôn phải đặt trước luật cho phép tổng quát.

Cơ chế Stateful Firewall: pfSense theo dõi phiên kết nối qua bảng State Table, do đó bắt buộc phải thực hiện Reset States sau mỗi lần sửa đổi luật để áp dụng chính sách mới tức thì.  

