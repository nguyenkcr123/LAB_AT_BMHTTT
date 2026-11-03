Tên: Phạm Công Nguyên
Lớp: 11_ĐH_THMT
MSSV: 1150080027

LAB10 — Remote DNS Cache Poisoning

LAB10 mô phỏng cơ chế Kaminsky trong mạng pfSense cô lập. Kali nằm trên ATTACKNET, tách khỏi DNSLAB. BIND9 phục vụ giai đoạn baseline và khôi phục. Giai đoạn mô phỏng dùng resolver Python có cổng nguồn UDP cố định và một race relay dành riêng cho lab.

Chỉ chạy trong các mạng máy ảo của LAB10. Dùng zone `example.lab10.test` và các IP trong bảng dưới đây. Ngắt NAT của Kali trước pha mô phỏng và chặn ATTACKNET ra WAN.


Giải nén trên máy host bằng PowerShell rồi chuyển từng tài nguyên sang đúng VM. Các lệnh Linux bên dưới chạy từ thư mục tài nguyên tương ứng trên VM.

```powershell
Expand-Archive -LiteralPath .\LAB10.zip -DestinationPath .\LAB10_GiaiNen
```

| Máy / interface | IPv4 | Vai trò |
| --- | --- | --- |
| pfSense DNSLAB | 10.9.0.1/24 | Gateway DNSLAB |
| DNS-SERVER | 10.9.0.10/24 | BIND9 / resolver mô phỏng |
| VICTIM | 10.9.0.100/24 | Gateway 10.9.0.1, DNS 10.9.0.10 |
| pfSense ATTACKNET | 10.10.0.1/24 | Gateway mạng Kali |
| Kali | 10.10.0.200/24 | Gateway 10.10.0.1, evil DNS và web |
| APP-TEST | 172.16.6.20 | AUTH-DNS và race relay |
| DMZ-WEB | 172.16.6.10 | Web hợp lệ |

## 1. Chuẩn bị mạng

1. Tạo snapshot máy ảo từ LAB9. Gắn thêm card nội bộ `attack-net` cho pfSense và tạo interface ATTACKNET với IP `10.10.0.1/24`. Để trống Upstream Gateway.
2. Chuyển Kali sang `attack-net`, tháo card DNSLAB, đặt Promiscuous Mode = Deny theo đề nếu dùng VirtualBox. Với VMware, dùng một LAN Segment riêng cho ATTACKNET. Kiểm tra địa chỉ và route bằng `ip -br addr` và `ip route`.
3. Tạo rule từ `05_PFSENSE/firewall_policy_matrix.csv`. Đặt các rule Pass cụ thể trước rule Block cuối. Hai luồng riêng từ Kali là đến resolver cổng 53 và đến APP-TEST UDP/5300. Cho phép resolver truy vấn AUTH-DNS và evil DNS, cùng luồng HTTP từ VICTIM đến Kali theo ma trận.
4. Trên các VM Linux, cài gói theo vai trò trước khi cô lập mạng. DNS-SERVER cần `bind9`; DNS-SERVER, Kali và APP-TEST cần `python3-dnspython`; máy chạy `dig` cần `dnsutils`; máy bắt gói cần `tcpdump` hoặc Wireshark.

## 2. Kiểm tra baseline

Trên APP-TEST, dừng DNS cũ nếu đang chiếm UDP/53. Trong bản gốc của `auth_dns_relay_lab10.py`, hàm `dns_loop` bỏ qua gói nếu cổng nguồn khác `33333`. Vì BIND9 dùng cổng nguồn động, baseline và khôi phục có thể bị timeout. Chỉnh **bản sao trên APP-TEST** như sau để AUTH-DNS trả lời BIND9 mà vẫn chỉ ghi nhận race trên UDP/33333.

1. Trong `dns_loop`, đổi điều kiện lọc đầu hàm thành `if addr[0] != RESOLVER: continue`.
2. Sau nhánh `if qname == WWW` và ngay trước `with LOCK:` ghi `PENDING`, thêm đoạn sau ở cùng mức thụt lề với `with LOCK:`.

```python
        if addr[1] != RESOLVER_PORT:
            sock.sendto(legit_response(q).to_wire(), addr)
            continue
```

Giữ nguyên bộ lọc địa chỉ Kali trong `relay_loop`. Từ `04_AUTH_DNS_RELAY`, chạy bản sao đã chỉnh và giữ terminal mở.

```bash
sudo python3 auth_dns_relay_lab10.py
```

Trên DNS-SERVER, từ `01_DNS_SERVER`, sao lưu hai tệp hiện tại rồi nạp cấu hình baseline. Nếu đã có các tệp `.lab9.bak`, giữ lại bản sao cũ.

```bash
sudo cp -n /etc/bind/named.conf.options /etc/bind/named.conf.options.lab9.bak
sudo cp -n /etc/bind/named.conf.local /etc/bind/named.conf.local.lab9.bak
sudo cp named.conf.options.baseline /etc/bind/named.conf.options
sudo cp named.conf.local.baseline /etc/bind/named.conf.local
sudo named-checkconf && sudo systemctl restart bind9
```

Trên VICTIM, xác nhận truy vấn sau trả `172.16.6.10` trước khi chuyển pha.

```bash
dig @10.9.0.10 www.example.lab10.test A +short
```

Để kiểm tra cô lập, bắt gói trên card ATTACKNET của Kali trong lúc VICTIM truy vấn một tên mới dưới zone. Đồng thời bắt trên DNS-SERVER hoặc APP-TEST để xác nhận đã có lưu lượng đối chứng.

```bash
sudo tcpdump -ni any 'host 10.9.0.10 and host 172.16.6.20 and udp port 53'
```

## 3. Chạy mô phỏng trong lab

Trên DNS-SERVER, giữ resolver Python chạy ở foreground.

```bash
sudo systemctl stop bind9
sudo python3 vuln_resolver_lab10.py
```

Trên Kali, mở ba terminal tại `03_ATTACKER`. Chuẩn bị thư mục web riêng và chạy từng lệnh theo thứ tự.

```bash
# Terminal 1
sudo python3 evil_dns_lab10.py

# Terminal 2
mkdir -p fake-site
cp fake_index.html fake-site/index.html
sudo python3 -m http.server 80 --bind 10.10.0.200 --directory fake-site

# Terminal 3
python3 kaminsky_remote_lab10.py
```

Bắt gói trên DNS-SERVER hoặc APP-TEST trước khi chạy terminal 3. Trong Wireshark, dùng `ip.addr == 10.9.0.10 && udp.port == 33333`. Tìm query đến `172.16.6.20:53`, phản hồi có cùng TXID và các bản ghi NS/glue, rồi query tiếp đến `10.10.0.200:53`.

Đối chiếu `[PENDING]` và `[HIT]` trên APP-TEST với `[CACHE]` trên resolver. Từ VICTIM, chạy lại `dig` và mở `http://www.example.lab10.test/`. Kết quả dự kiến là `10.10.0.200` và trang LAB10 Fake Site. Kiểm tra HTTP bằng trình duyệt dùng DNS của lab hoặc bằng `curl` với tên miền trên.

## 4. Khôi phục và kiểm tra

1. Dừng resolver Python bằng Ctrl+C. Giữ AUTH-DNS chạy để kiểm tra đường truy vấn hợp lệ.
2. Trên DNS-SERVER, chạy `sudo named-checkconf && sudo systemctl start bind9`, sau đó kiểm tra `systemctl is-active bind9`.
3. Từ VICTIM, kiểm tra lại bản ghi A. Bắt gói trên DNS-SERVER khi tạo nhiều truy vấn mới và ghi các cổng nguồn quan sát được.

```bash
for i in 1 2 3 4 5; do
  dig @10.9.0.10 "restore-${i}-$(date +%s).example.lab10.test" A +time=5 +tries=1
done
```

4. Kiểm tra ACL BIND chỉ cho loopback và DNSLAB truy vấn đệ quy. Nếu còn rule Pass từ Kali đến DNS-SERVER để kiểm tra ACL, truy vấn từ Kali dự kiến nhận `REFUSED`. Sau đó dừng dịch vụ mô phỏng trên Kali và APP-TEST, tắt rule UDP/5300 và các rule tạm, rồi tắt ATTACKNET hoặc khôi phục snapshot theo nhu cầu.

| Dấu hiệu dự kiến | Baseline | Mô phỏng thành công | Khôi phục |
| --- | --- | --- | --- |
| IP trả cho `www.example.lab10.test` | `172.16.6.10` | `10.10.0.200` | `172.16.6.10` |
| DNS upstream | AUTH-DNS | Evil DNS sau khi cache delegation | AUTH-DNS |
| UDP source port upstream | BIND chọn động | `33333` cố định | BIND chọn động |

Nếu baseline sai, kiểm tra forward zone, cổng 53 và route. Nếu có `[PENDING]` nhưng thiếu `[HIT]`, kiểm tra IP Kali, rule UDP/5300 và thứ tự khởi chạy. Nếu có `[CACHE]` nhưng HTTP sai, kiểm tra cache DNS trên VICTIM, cổng TCP/80 và dịch vụ HTTP trên Kali.

## Minh chứng nộp bài

Chụp ATTACKNET và ruleset pfSense, card mạng Kali, kết quả `dig` baseline, log `[PENDING]`/`[HIT]`/`[CACHE]`, capture trước và sau poisoning, Fake Site trên VICTIM và các cổng nguồn khi BIND9 chạy lại. Ảnh video trong báo cáo minh họa giao diện quản trị. Bảng trên là tiêu chí đối chiếu khi chạy lab.

Điền họ tên, MSSV, lớp và ngày thực hành. Đổi tên báo cáo theo mẫu `[Mã lớp]-Lab10_MSSV-HoTen.docx` trước khi nộp.
