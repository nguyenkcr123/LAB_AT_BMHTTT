BÁO CÁO LAB 7 WEB PROTECTION

Họ tên: Phạm Công Nguyên
MSSV: 1150080027
Lớp: 11_ĐH_THMT

Mục tiêu

Xây dựng chính sách truy cập web trên các máy ảo VMware, sử dụng lại môi trường LAB 6. Không cài Squid trực tiếp trên pfSense.

Thành phần

pfSense làm firewall và gateway. Ubuntu chạy E2Guardian, Squid và ClamAV. Windows Server cung cấp AD, DNS và trình duyệt kiểm thử. Kali cung cấp website mô phỏng trong DMZ.

Nội dung đã thực hiện

Chặn website theo danh sách, cảnh báo và mở khóa tạm thời trong 5 phút, cho phép website ngoại lệ, giới hạn truy cập theo khung giờ 60 phút, quét file tải xuống và tùy biến trang chặn.

Cấu hình Squid giới hạn kích thước tải xuống. Kiểm tra log và chặn truy cập HTTP trực tiếp trên máy thử nghiệm.

Kết quả chính

Jobs bị chặn và không mở khóa được. Politics mở được sau khi xác nhận và bị cảnh báo lại khi hết hạn. Normal và Education truy cập được.

Entertainment và Games chỉ truy cập được trong khung giờ cho phép. File sạch tải được, EICAR bị chặn. File 100 MB tải được, file 110 MB bị từ chối.

Giới hạn

Chưa thực hiện HTTPS inspection. Firewall vẫn còn rule cho phép rộng để kiểm thử, chưa hoàn tất chính sách hạn chế cho toàn bộ máy trạm.

Lịch 60 phút là khung giờ truy cập, không phải thời gian sử dụng cộng dồn. Một số kết quả chưa có ảnh minh chứng riêng.

