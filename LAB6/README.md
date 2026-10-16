# Hướng dẫn hoàn thiện LAB6 pfSense và Active Directory

Sinh viên: Phạm Công Nguyên  
MSSV: 1150080027  
Lớp: 11_ĐH_THMT

Tài liệu hướng dẫn sửa cấu hình hiện tại, kiểm thử kịch bản 1–5 và hoàn thiện báo cáo. Không bao gồm thực hành kịch bản 6–7 theo phạm vi người học yêu cầu. Đề gốc vẫn yêu cầu các phần này; việc được miễn cần theo hướng dẫn của giảng viên.

Các bước dưới đây là hướng dẫn cần thực hiện, không phải xác nhận hệ thống đã đạt. Chỉ đánh dấu hoàn thành sau khi kiểm thử thực tế.

## 1. Sao lưu và đường quản trị

1. Quản trị qua mạng MGMT, tại địa chỉ đang sử dụng `http://192.168.56.254` (dùng HTTPS nếu đã cấu hình HTTPS).
2. Vào **Diagnostics → Backup & Restore**.
3. Nhấn **Download configuration as XML**, giữ làm bản dự phòng.
4. Tạo đủ rule mới rồi mới tắt `Allow all traffic on LAN to test`.
5. Không sửa hoặc xóa rule MGMT khi chưa bảo đảm có đường quản trị thay thế.

## 2. Mô hình mạng và hai client
Thành phần	IP					Gateway				DNS
pfSense WAN	DHCP trên 192.168.250.0/24		Theo DHCP			Theo cấu hình WAN
pfSense MGMT	192.168.56.254/24			Không đặt gateway upstream	—
pfSense LAN	10.6.0.1/24				—				—
pfSense DMZ	172.16.6.1/24				—				—
DC01		10.6.0.10/24				10.6.0.1			10.6.0.10
Client-A	10.6.0.21/24				10.6.0.1			10.6.0.10
Client-B	10.6.0.22/24				10.6.0.1			10.6.0.10
DMZ-WEB		172.16.6.10/24, thêm .20/.30/.40	172.16.6.1			Theo nhu cầu máy chủ


### Kiểm tra VMware

- pfSense có bốn Network Adapter tương ứng WAN, MGMT, LAN và DMZ.
- DC01, Client-A và Client-B cùng mạng ảo với LAN của pfSense.
- DMZ-WEB cùng mạng ảo với DMZ của pfSense.
- LAN và DMZ là hai phân đoạn riêng.
- Chụp cấu hình bốn card mạng, đối chiếu MAC với interface pfSense.
- Đề sử dụng VirtualBox; nếu dùng VMware, cần được giảng viên chấp nhận.

### Đặt IP trên Windows

1. Nhấn **Windows + R**, nhập `ncpa.cpl`.
2. Nhấp phải Ethernet → **Properties**.
3. Chọn **Internet Protocol Version 4 → Properties**.
4. Nhập IP, gateway và DNS theo bảng; subnet mask là `255.255.255.0`.
5. Mở CMD, chạy:

```bat
ipconfig /all
```

Chụp ảnh riêng Client-A và Client-B. Không dùng DC01 `.10` thay Client-A. Có thể bật lần lượt hai client khi thiếu RAM.

## 3. Sửa Alias

Vào **Firewall → Aliases**. Nhấn bút chì để sửa, Save rồi Apply Changes.

| Alias 	| Type 		| Giá trị cuối cùng 	|
|---		|---		|---			|
| CLIENT_A 	| Host(s) 	| 10.6.0.21 |
| CLIENT_B 	| Host(s) 	| 10.6.0.22 |
| POLICY_CLIENTS | Host(s) 	| 10.6.0.21, 10.6.0.22 |
| GENERAL_SITE	 | Host(s) 	| 172.16.6.10 |
| JOB_SITE 	| Host(s) 	| 172.16.6.20 |
| SOCIAL_SITE 	| Host(s) 	| 172.16.6.30 |
| RESTRICTED_SITE | Host(s) 	| 172.16.6.40 |
| WEB_PORTS	| Port(s) 	| 80, 443 |

Xóa `10.6.0.10` khỏi CLIENT_A và POLICY_CLIENTS. WEB_PORTS gồm hai mục riêng 80 và 443, không phải khoảng 80–443.

Chụp tab IP và Ports. Có thể dùng **Diagnostics → Tables** kiểm tra alias địa chỉ đã nạp.

## 4. Sửa Schedule

Kiểm tra **System → General Setup**, múi giờ `Asia/Ho_Chi_Minh`, ngày giờ hệ thống đúng.

Vào **Firewall → Schedules**:

| Schedule | Ngày | Giờ |
|---|---|---|
| WORK_HOURS | Thứ Hai–thứ Sáu, lặp theo tuần | 08:00–17:00 |
| LUNCH_WINDOW | Thứ Hai–thứ Sáu, lặp theo tuần | 11:30–13:00 |
| TEST_NOW | Ngày thực hành | Khoảng 10–15 phút chứa giờ hiện tại |

1. Sửa WORK_HOURS, xóa khoảng ngày cụ thể cũ.
2. Trong lịch chọn tiêu đề các thứ Monday–Friday để tạo lịch lặp theo thứ.
3. Nhập Start time và Stop time, nhấn **Add Time**.
4. Kiểm tra khoảng thời gian vừa thêm rồi Save.
5. Làm tương tự với LUNCH_WINDOW.
6. TEST_NOW chỉ dùng kiểm thử nhanh; ví dụ đồng hồ pfSense là 19:20 thì chọn 19:15–19:30 cùng ngày.

Không giữ TEST_NOW bao phủ nhiều ngày cả ngày. Chụp cấu hình lịch thấy rõ ngày và giờ.

## 5. Bộ rule LAN cuối cùng

Vào **Firewall → Rules → LAN**. Giữ rule hệ thống; sắp xếp các rule chính sách theo thứ tự:

| STT | Action | Protocol | Source | Destination | Cổng đích | Schedule |
|---|---|---|---|---|---|---|
| 1 | Block | IPv4 TCP | CLIENT_A | JOB_SITE | 80 | Không |
| 2 | Block | IPv4 TCP | POLICY_CLIENTS | RESTRICTED_SITE | 80 | Không |
| 3 | Pass | IPv4 TCP | CLIENT_B | SOCIAL_SITE | 80 | LUNCH_WINDOW |
| 4 | Block | IPv4 TCP | CLIENT_B | SOCIAL_SITE | 80 | Không |
| 5 | Pass | IPv4 TCP | POLICY_CLIENTS | GENERAL_SITE | 80 | Không |
| 6 | Pass | IPv4 TCP | POLICY_CLIENTS | any | WEB_PORTS | WORK_HOURS |
| 7 | Block | IPv4 any | POLICY_CLIENTS | any | any | Không |

STT 7 là rule chặn cuối, vẫn cần khi bỏ thực hành kịch bản 7. Source port của các rule để any.

### Cách tạo hoặc sửa rule

1. Nhấn **Add** hoặc bút chì tại rule cần sửa.
2. Nhập Action, Interface LAN, Address Family IPv4 và Protocol theo bảng.
3. Source chọn **Single host or alias**, nhập alias tương ứng.
4. Destination chọn alias tương ứng; các dòng any chọn Any.
5. Destination port chọn HTTP (80), hoặc trường Other/Custom và nhập WEB_PORTS.
6. Schedule chọn trong phần tùy chọn nâng cao nếu có.
7. Bật **Log packets that are handled by this rule** cho các rule Block quan trọng.
8. Đặt Description rõ mục đích, Save.

### Sửa các rule đang có

- JOB_SITE: đổi Reject thành Block; Source từ LAN net thành CLIENT_A.
- Restricted: đổi Reject thành Block; Source thành POLICY_CLIENTS.
- Disable rule cũ `Block Social Site during Work Hours`; thay bằng cặp Pass/Block Social ở bảng.
- Thêm Pass GENERAL_SITE luôn có hiệu lực.
- Sửa Pass Web: Source POLICY_CLIENTS, cổng WEB_PORTS, lịch WORK_HOURS, bỏ Disable.
- Sửa Block cuối: Source POLICY_CLIENTS, Destination any, Protocol Any, bật log.
- Tạo đủ và sắp xếp xong mới Disable `Allow all traffic on LAN to test`.
- Giữ các rule cũ trùng hoặc không còn dùng ở trạng thái tắt.
- Save thứ tự và **Apply Changes**.

GENERAL_SITE được cho phép cả ngoài giờ vì có rule riêng phía trên rule giới hạn dịch vụ. Không có Pass rộng khác được phép vượt qua chính sách cho hai client.

## 6. LDAP service account và test hai user

### Trên DC01

1. Mở **Active Directory Users and Computers**.
2. Kiểm tra OU LAB6-Users có `sv_a` và `sv_b`, hai tài khoản được bật.
3. Nếu tạo user kiểm thử, bỏ **User must change password at next logon**.
4. Trong container Users tạo `svc_pfsense_ldap`, đặt mật khẩu riêng.
5. Không thêm service account vào Domain Admins.

### Trên pfSense

Vào **System → User Manager → Authentication Servers → LAB6-AD**:

| Trường | Giá trị |
|---|---|
| Host | 10.6.0.10 |
| Port | 389 |
| Transport | Standard TCP, theo mạng lab cô lập |
| Protocol version | 3 |
| Search scope | Entire Subtree |
| Base DN | DC=lab6,DC=test |
| Authentication Containers | OU=LAB6-Users,DC=lab6,DC=test |
| Bind DN | CN=svc_pfsense_ldap,CN=Users,DC=lab6,DC=test |
| User Naming Attribute | samAccountName |
| Group Naming Attribute | cn |
| Group Member Attribute | memberOf |

Nhập mật khẩu service account, Save. DN phải khớp tên và vị trí tài khoản thực tế.

Vào **Diagnostics → Authentication**, chọn LAB6-AD, test riêng `sv_a` và `sv_b`. Chụp hai thông báo thành công. Nếu lỗi sau đổi bind, kiểm tra DN, vị trí tài khoản và mật khẩu trước.

## 7. Captive Portal đúng client

Vào **Services → Captive Portal → LAB6_CP**, kiểm tra Enable, interface LAN và backend LAB6-AD. Giữ cấu hình đang đúng.

1. Vào **Status → Captive Portal**, ngắt session cũ của sv_a tại 10.6.0.10.
2. Trên Client-A mở `http://172.16.6.10`, đăng nhập sv_a.
3. Kiểm tra session phải là `10.6.0.21 → sv_a`.
4. Trên Client-B làm tương tự bằng sv_b, session `10.6.0.22 → sv_b`.
5. Chụp trang login và session từng client.

Nếu Portal không hiện: kiểm tra zone/interface, session cũ, đúng mạng LAN và dùng URL HTTP.

## 8. Chuẩn bị kiểm thử và xử lý state

Trên DMZ-WEB kiểm tra cả bốn site hoạt động:

```bash
curl --max-time 5 http://172.16.6.10
curl --max-time 5 http://172.16.6.20
curl --max-time 5 http://172.16.6.30
curl --max-time 5 http://172.16.6.40
```

Nếu site không trả nội dung, kiểm tra IP phụ và Nginx trước khi kết luận firewall chặn.

Sau thay rule hoặc schedule, Apply rồi tạo kết nối mới. Nếu còn state cũ:

1. Vào **Diagnostics → States**.
2. Lọc theo IP client, tìm luồng đến đích đang thử.
3. Xóa state liên quan và thử lại.

Reset States toàn bộ sẽ ngắt các kết nối khác; ưu tiên xóa state của phép thử. Có thể đăng nhập Portal lại nếu cần. Thao tác này phục vụ kiểm thử, không phải yêu cầu làm riêng kịch bản 7.

## 9. Kịch bản 1 — Client-A bị chặn JOB_SITE

Trên Client-A đã đăng nhập Portal:

```powershell
ipconfig
curl.exe --noproxy "*" --connect-timeout 5 --max-time 10 http://172.16.6.10
curl.exe --noproxy "*" --connect-timeout 5 --max-time 10 http://172.16.6.20
```

.10 thành công, .20 bị chặn. Chụp rule và kết quả thấy IP nguồn .21. Nếu không đạt, kiểm tra source CLIENT_A, thứ tự rule và state cũ.

Nhận xét khi đúng: Client-A được phép vào GENERAL_SITE nhưng bị chặn JOB_SITE bởi rule cụ thể nằm trước các rule Pass rộng hơn.

## 10. Kịch bản 2 — Restricted bị chặn cho A và B

Thực hiện riêng trên cả hai client:

```powershell
ipconfig
curl.exe --noproxy "*" --connect-timeout 5 --max-time 10 http://172.16.6.10
curl.exe --noproxy "*" --connect-timeout 5 --max-time 10 http://172.16.6.40
```

Cả hai phải mở được .10 và bị chặn .40. Chụp rule dùng POLICY_CLIENTS và hai bộ kết quả riêng. Ảnh trình duyệt lỗi không thấy IP nguồn chưa đủ phân biệt A/B.

## 11. Kịch bản 3 — Social theo giờ nghỉ

Thực hiện trên Client-B .22, đăng nhập sv_b.

### Trong lịch

1. Tạm đổi Schedule của rule Pass CLIENT_B → SOCIAL_SITE sang TEST_NOW.
2. Chỉnh TEST_NOW chứa thời điểm hiện tại của pfSense.
3. Save → Apply.
4. Chạy:

```powershell
curl.exe --noproxy "*" --connect-timeout 5 --max-time 10 http://172.16.6.30
```

Chụp lịch Active, rule và kết quả.

### Ngoài lịch

1. Chỉnh TEST_NOW sang khoảng không chứa thời điểm hiện tại.
2. Save → Apply, xóa state phép thử nếu còn.
3. Chạy lại lệnh, mong đợi bị chặn bởi Block Social phía dưới.
4. Chụp kết quả.
5. Gắn lại **LUNCH_WINDOW** cho rule Pass, Save → Apply.

Nhận xét: khi lịch không hoạt động, Pass không được áp dụng; Block phía dưới xử lý kết nối mới.

## 12. Kịch bản 4 — HTTP/HTTPS theo giờ hành chính

### Trong giờ

1. Tạm gắn TEST_NOW đang Active cho rule Pass web dùng WEB_PORTS.
2. Save → Apply.
3. Trên client chạy:

```powershell
nslookup example.com
curl.exe --noproxy "*" --connect-timeout 5 --max-time 15 http://example.com
curl.exe --noproxy "*" --connect-timeout 5 --max-time 15 https://example.com
ping 1.1.1.1
```

Mong đợi DNS có kết quả, HTTP/HTTPS có phản hồi, ICMP bị chặn. Ping thất bại cần đối chiếu log để xác nhận nguyên nhân là rule, không chỉ dựa vào timeout.

Nếu DNS lỗi, kiểm tra DNS Forwarders của DC01 và đường DNS upstream. Tạo rule riêng trên LAN nếu cần: Pass TCP/UDP, Source 10.6.0.10, Destination là DNS upstream thực tế, cổng 53. Không đưa DC trở lại POLICY_CLIENTS để xử lý lỗi này.

Nếu DNS đúng nhưng web lỗi, kiểm tra WAN, gateway và outbound NAT trước khi đánh giá policy.

### Ngoài giờ

1. Chỉnh TEST_NOW ra ngoài giờ hiện tại, Apply.
2. Xóa state web thử cũ, chạy lại HTTP/HTTPS.
3. Mong đợi kết nối Internet mới bị chặn.
4. Thử GENERAL_SITE .10: vẫn phải truy cập được theo rule ngoại lệ.
5. Gắn lại **WORK_HOURS** cho rule Pass web, Save → Apply.

Chụp kết quả trong/ngoài giờ, rule dùng WEB_PORTS và lịch chính thức.

## 13. Kịch bản 5 — Cô lập DMZ khỏi LAN

Vào **Firewall → Rules → DMZ**. Rule đầu:

```text
Action: Block
Address Family: IPv4
Protocol: Any
Source: DMZ net / DMZ subnets
Destination: LAN net / LAN subnets
Log: bật
```

Save → Apply. Mọi Pass rộng trên DMZ phải nằm dưới rule này.

Trên Client-A:

```powershell
curl.exe --noproxy "*" --connect-timeout 5 --max-time 10 http://172.16.6.10
```



Trên DMZ-WEB:

```bash
ping -c 4 10.6.0.10
# Chỉ chạy lệnh dưới nếu đã có nc:
nc -vz -w 5 10.6.0.10 389
```

Mong đợi không khởi tạo được kết nối vào DC01. Vào **Status → System Logs → Firewall**, lọc source 172.16.6.10 và destination 10.6.0.10, đối chiếu action Block.

Chụp rule sau Apply, kết quả LAN → DMZ, DMZ → LAN và log. Ảnh ping trước/sau đã có có thể giữ lại, kèm chú thích rõ nguồn và thời điểm.

Trên tab MGMT còn rule cũ mang tên DMZ: tên rule không quyết định interface. Chỉ dọn hoặc đổi tên sau khi kiểm tra mục đích và giữ được đường quản trị.

## 14. Bảng kết quả để điền

| Phép thử | Mong đợi | Thực tế | Ảnh/nhận xét |
|---|---|---|---|
| LDAP sv_a | Thành công | Chưa điền | |
| LDAP sv_b | Thành công | Chưa điền | |
| Portal .21/sv_a | Thành công | Chưa điền | |
| Portal .22/sv_b | Thành công | Chưa điền | |
| A → GENERAL_SITE | Allow cả trong/ngoài lịch | Chưa điền | |
| A → JOB_SITE | Block | Chưa điền | |
| A → RESTRICTED_SITE | Block | Chưa điền | |
| B → GENERAL_SITE | Allow | Chưa điền | |
| B → RESTRICTED_SITE | Block | Chưa điền | |
| B → SOCIAL_SITE trong LUNCH | Allow | Chưa điền | |
| B → SOCIAL_SITE ngoài LUNCH | Block | Chưa điền | |
| Client → HTTP/HTTPS Internet trong WORK | Allow | Chưa điền | |
| Client → HTTP/HTTPS Internet ngoài WORK | Block | Chưa điền | |
| Client → ICMP Internet | Block | Chưa điền | |
| LAN → GENERAL_SITE | Allow | Chưa điền | |
| DMZ → DC01 | Block | Chưa điền | |


## 15. Hoàn thiện Word và câu hỏi phân tích

Chia báo cáo: mô hình và IP; LDAP; Portal; Alias; Schedule; kịch bản 1–5; bảng kết quả; câu hỏi phân tích. Mỗi kịch bản gồm mục tiêu, cấu hình, ảnh rule, ảnh kiểm thử, thực tế và nhận xét.


