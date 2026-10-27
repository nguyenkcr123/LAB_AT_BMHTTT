
Họ tên: Phạm Công Nguyên
MSSV: 1150080027
Lớp: 11_ĐH_THMT

BÀI THỰC HÀNH 8 TẤN CÔNG DNS CỤC BỘ

KỊCH BẢN THỰC HÀNH MẪU

Môn An toàn hệ thống thông tin
Môi trường VMware Workstation
Bìa đề ghi LAB 8 còn gói tài nguyên dùng tên LAB 9
Tài liệu này mô tả quy trình đầy đủ và kết quả mong đợi
Các kết quả đo thực tế và ảnh chụp hiện có được ghi riêng ở cuối

MỤC TIÊU

Dựng máy chủ BIND9 phục vụ vùng DNS thuận và nghịch trong mạng ảo cô lập
So sánh chuyển hướng bằng tệp hosts với giả mạo phản hồi DNS trực tiếp và đầu độc bộ nhớ đệm
Quan sát mã giao dịch tên truy vấn cổng UDP và thời điểm phản hồi
Khôi phục cấu hình và kiểm tra lại kết quả phân giải sau mỗi phép thử
Chỉ sử dụng máy ảo được chỉ định và tên miền có hậu tố test

MÔ HÌNH MẠNG

pfSense kết nối mạng DNS LAB và mạng DMZ
Giao diện DNSLAB dùng địa chỉ 10 chấm 9 chấm 0 chấm 1 với tiền tố 24
DNS SERVER dùng địa chỉ 10 chấm 9 chấm 0 chấm 10 với tiền tố 24
VICTIM dùng địa chỉ 10 chấm 9 chấm 0 chấm 100 với tiền tố 24
ATTACKER dùng địa chỉ 10 chấm 9 chấm 0 chấm 200 với tiền tố 24
APP TEST dùng địa chỉ 172 chấm 16 chấm 6 chấm 20 với tiền tố 24
DMZ WEB dùng địa chỉ 172 chấm 16 chấm 6 chấm 10 với tiền tố 24

CHUẨN BỊ VÀ CÔ LẬP

Tạo bản sao hoặc ảnh chụp trạng thái máy ảo trước khi sửa cấu hình
Gắn ba máy DNS SERVER VICTIM và ATTACKER vào cùng một mạng LAN Segment riêng
Thêm card mạng cho pfSense trên LAN Segment đó và gán giao diện DNSLAB
Đặt địa chỉ tĩnh cho DNSLAB và để trống cổng mặc định của giao diện này
Cho phép DNS SERVER truy vấn APP TEST qua TCP và UDP cổng 53
Cho phép các máy trong DNS LAB truy cập DMZ WEB qua TCP cổng 80
Chặn và ghi nhật ký lưu lượng tới LAN quản trị và các đích còn lại
Ngắt NAT tạm của ba máy thử nghiệm và xác minh không có đường ra Internet
Xác nhận máy bắt gói quan sát được truy vấn giữa các máy ảo trên đúng mạng

CẤU HÌNH VÀ ĐO KẾT QUẢ CHUẨN

Cài BIND9 trên DNS SERVER và sao lưu cấu hình gốc
Khai báo vùng thuận lab9 chấm test và vùng nghịch cho mạng DNS LAB
Cấu hình chuyển tiếp vùng external chấm lab9 chấm test tới APP TEST
Kiểm tra cấu hình và nạp hai vùng DNS
Cấu hình VICTIM chỉ hỏi DNS SERVER trong pha thử nghiệm
Chạy dịch vụ DNS mô phỏng trên APP TEST với độ trễ một giây rưỡi
Kiểm tra web hợp lệ trên DMZ WEB
Từ VICTIM truy vấn tên www chấm external chấm lab9 chấm test qua DNS SERVER
Kết quả chuẩn mong đợi là địa chỉ 172 chấm 16 chấm 6 chấm 10
Chỉ tiếp tục khi kết quả chuẩn đã được xác nhận

PHÉP THỬ 1 CHUYỂN HƯỚNG BẰNG TỆP HOSTS

Sao lưu tệp hosts trên VICTIM
Thêm ánh xạ tên www chấm lab9 chấm test tới web mô phỏng tại địa chỉ 10 chấm 9 chấm 0 chấm 200
Kiểm tra tên và nội dung web từ ứng dụng dùng bộ phân giải của hệ điều hành
Đối chiếu bằng truy vấn DNS gửi thẳng tới BIND9
Kết quả mong đợi là ứng dụng mở web mô phỏng trong khi truy vấn DNS trực tiếp vẫn trả 172 chấm 16 chấm 6 chấm 10
Khôi phục tệp hosts sau phép thử

PHÉP THỬ 2 GIẢ MẠO PHẢN HỒI DNS TRỰC TIẾP

Xóa bộ nhớ đệm và bắt đầu ghi gói trên mạng DNS LAB
Tạo truy vấn mới từ VICTIM
Đối chiếu mã giao dịch tên truy vấn cổng UDP và thời điểm nhận phản hồi thật và phản hồi mô phỏng
Kết quả mong đợi là VICTIM nhận 10 chấm 9 chấm 0 chấm 200 khi phản hồi giả được chấp nhận trước
Lưu ảnh truy vấn và phản hồi trong Wireshark cùng kết quả trên VICTIM

PHÉP THỬ 3 ĐẦU ĐỘC BỘ NHỚ ĐỆM DNS

Xóa bộ nhớ đệm BIND9 trước khi thử
Tạo truy vấn khiến DNS SERVER phải hỏi APP TEST qua pfSense
Quan sát phản hồi mô phỏng gửi tới DNS SERVER
Dừng chương trình giả mạo rồi truy vấn lại từ VICTIM
Kiểm tra nội dung bộ nhớ đệm của BIND9
Kết quả mong đợi là bộ nhớ đệm chứa 10 chấm 9 chấm 0 chấm 200 và trả địa chỉ này cho truy vấn mới trước khi hết thời gian lưu
Lưu ảnh phản hồi gửi tới máy chủ và ảnh bản ghi trong bộ nhớ đệm

KHÔI PHỤC VÀ PHÒNG THỦ

Dừng chương trình giả mạo và web mô phỏng
Khôi phục tệp hosts rồi xóa bộ nhớ đệm DNS SERVER và VICTIM
Truy vấn lại và đối chiếu với địa chỉ hợp lệ 172 chấm 16 chấm 6 chấm 10
Dừng dịch vụ mô phỏng trên APP TEST và trả quyền bắt gói của ATTACKER về chế độ hạn chế
Bật lại DNS Cookie cho APP TEST trong cấu hình BIND9 nếu đã tắt khi thử
Có thể thử lại phép thử 3 để so sánh sau khi thay đổi quyền bắt gói và DNS Cookie
Phòng thủ lâu dài bằng phân đoạn mạng kiểm soát DNS đi ra xác thực DNSSEC cho dữ liệu đã ký ngẫu nhiên hóa cổng nguồn và giám sát phản hồi bất thường


KẾT QUẢ VÀ ẢNH ĐÃ GHI NHẬN

Đã kiểm tra web hợp lệ trong DMZ và nhận mã HTTP 200
Đã xác nhận BIND9 nạp vùng thuận và nghịch rồi trả kết quả qua địa chỉ lặp nội bộ
Đã xác nhận APP TEST có địa chỉ trong DMZ và lắng nghe trên UDP cổng 53
Trang 1 của báo cáo Word có ảnh console pfSense trước khi thêm DNSLAB
Trang 2 có ảnh kiểm tra BIND9 và ảnh cấu hình APP TEST
Báo cáo có mười câu trả lời phân tích
Kết quả chuẩn qua pfSense ba phép thử và bước khôi phục chưa có ảnh thực nghiệm



