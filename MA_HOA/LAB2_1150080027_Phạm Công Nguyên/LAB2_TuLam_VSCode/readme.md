BÁO CÁO VÀ MÃ NGUỒN LAB 2

MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI

Họ tên: Phạm Công Nguyên
MSSV: 1150080027
Lớp: 11 ĐH THMT



CHẠY CHƯƠNG TRÌNH JAVA

Mở thư mục project trong VS Code
Cần JDK 17 trở lên và Maven để biên dịch project
Mở lớp menu chính rồi bấm Run ở hàm main

THIẾT LẬP SQL SERVER

Mở SQL Server Management Studio và kết nối bằng Windows Authentication
Trong thư mục SQL chạy tệp 01 để tạo cơ sở dữ liệu và tệp 02 để tạo tài khoản kết nối
Trong chương trình Java mở trang kết nối SQL Server và bấm Test Connection
SQL Server chạy cục bộ qua cổng 14333
Sau khi kết nối thành công có thể đăng ký đăng nhập và mã hóa trong bài AES
Chạy tệp 03 để kiểm tra tài khoản và lịch sử thao tác

CHẠY BÀI AES CBC PYTHON

Mở tệp chương trình trong thư mục python bằng VS Code
Cài thư viện cryptography nếu môi trường Python chưa có
Chạy chương trình rồi bấm Generate Key và Generate IV
Nhập văn bản bấm Encrypt rồi bấm Decrypt để kiểm tra nội dung được khôi phục

KIỂM TRA NHANH

Với RC4 nhập Plaintext vào ô bản rõ và Key vào ô khóa
Kết quả mã hóa phải là BBF316E8D940AF0AD3
Sau khi thử AES mở trang xem lịch sử để kiểm tra các thao tác REGISTER LOGIN ENCRYPT và DECRYPT

