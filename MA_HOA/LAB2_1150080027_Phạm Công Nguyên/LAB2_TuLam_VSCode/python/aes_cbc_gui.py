import base64
import secrets
import sys
import tkinter as tk
from pathlib import Path
from tkinter import filedialog, messagebox, ttk

from cryptography.hazmat.primitives import padding
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes


def random_base64(size):
    return base64.b64encode(secrets.token_bytes(size)).decode("ascii")


def decode_base64(text):
    return base64.b64decode(text.strip(), validate=True)


def make_cipher(key_text, iv_text):
    key = decode_base64(key_text)
    iv = decode_base64(iv_text)
    if len(key) != 32 or len(iv) != 16:
        raise ValueError("Key phải 32 byte và IV phải 16 byte. Hãy dùng nút Generate.")
    return Cipher(algorithms.AES(key), modes.CBC(iv))


def encrypt_text(text, key, iv):
    padder = padding.PKCS7(128).padder()
    padded = padder.update(text.encode("utf-8")) + padder.finalize()
    encryptor = make_cipher(key, iv).encryptor()
    result = encryptor.update(padded) + encryptor.finalize()
    return base64.b64encode(result).decode("ascii")


def decrypt_text(text, key, iv):
    data = decode_base64(text)
    if not data or len(data) % 16:
        raise ValueError("Bản mã CBC phải có độ dài là bội số dương của 16 byte.")
    decryptor = make_cipher(key, iv).decryptor()
    padded = decryptor.update(data) + decryptor.finalize()
    unpadder = padding.PKCS7(128).unpadder()
    plain = unpadder.update(padded) + unpadder.finalize()
    return plain.decode("utf-8")


class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("LAB 2 - AES-256-CBC Python")
        self.key = tk.StringVar()
        self.iv = tk.StringVar()
        self.status = tk.StringVar(value="Bấm Generate Key và Generate IV.")
        self.saved_cipher = None

        panel = ttk.Frame(self, padding=12)
        panel.pack(fill="both", expand=True)
        panel.columnconfigure(1, weight=1)

        ttk.Label(panel, text="Plaintext").grid(row=0, column=0, sticky="nw")
        self.plaintext = tk.Text(panel, width=60, height=5, wrap="word")
        self.plaintext.grid(row=0, column=1, columnspan=2, sticky="ew")

        for row, name, variable, size in (
            (1, "Key", self.key, 32),
            (2, "IV", self.iv, 16),
        ):
            ttk.Label(panel, text=f"{name} (Base64)").grid(row=row, column=0, sticky="w")
            ttk.Entry(panel, textvariable=variable).grid(row=row, column=1, sticky="ew")
            ttk.Button(
                panel, text=f"Generate {name}",
                command=lambda value=variable, n=size: value.set(random_base64(n)),
            ).grid(row=row, column=2, sticky="ew")

        ttk.Label(panel, text="Ciphertext").grid(row=3, column=0, sticky="nw")
        self.ciphertext = tk.Text(panel, width=60, height=5, wrap="char")
        self.ciphertext.grid(row=3, column=1, columnspan=2, sticky="ew")

        buttons = ttk.Frame(panel)
        buttons.grid(row=4, column=1, columnspan=2, sticky="w")
        for caption, action in (
            ("Encrypt", self.encrypt),
            ("Decrypt", self.decrypt),
            ("Save File", self.save_file),
            ("Open File", self.open_file),
        ):
            ttk.Button(buttons, text=caption, command=action).pack(side="left", padx=3, pady=6)

        ttk.Label(panel, textvariable=self.status).grid(row=5, column=1, columnspan=2, sticky="w")

    @staticmethod
    def read_text(widget):
        return widget.get("1.0", "end-1c")

    @staticmethod
    def write_text(widget, value):
        widget.delete("1.0", "end")
        widget.insert("1.0", value)

    def encrypt(self):
        try:
            iv = self.iv.get().strip()
            result = encrypt_text(self.read_text(self.plaintext), self.key.get(), iv)
            self.write_text(self.ciphertext, result)
            self.saved_cipher = (result, iv)
            self.status.set("Đã mã hóa.")
        except ValueError as error:
            messagebox.showerror("Lỗi mã hóa", str(error), parent=self)

    def decrypt(self):
        try:
            result = decrypt_text(self.read_text(self.ciphertext), self.key.get(), self.iv.get())
            self.write_text(self.plaintext, result)
            self.status.set("Đã giải mã.")
        except ValueError as error:
            messagebox.showerror("Lỗi giải mã", str(error), parent=self)

    def save_file(self):
        ciphertext = self.read_text(self.ciphertext).strip()
        if not ciphertext:
            messagebox.showerror("Lỗi", "Chưa có bản mã để lưu.", parent=self)
            return

        iv = self.iv.get().strip()
        if self.saved_cipher and self.saved_cipher[0] == ciphertext:
            iv = self.saved_cipher[1]
        if not iv:
            messagebox.showerror("Lỗi", "Hãy nhập IV tương ứng.", parent=self)
            return

        file = filedialog.asksaveasfilename(
            parent=self, defaultextension=".txt", initialfile="aes_cbc_ciphertext.txt"
        )
        if file:
            try:
                Path(file).write_text(f"IV={iv}\n{ciphertext}\n", encoding="utf-8")
                self.status.set("Đã lưu tệp.")
            except OSError as error:
                messagebox.showerror("Lỗi lưu tệp", str(error), parent=self)

    def open_file(self):
        file = filedialog.askopenfilename(parent=self, filetypes=[("Text", "*.txt")])
        if not file:
            return
        try:
            lines = Path(file).read_text(encoding="utf-8").strip().splitlines()
            if len(lines) != 2 or not lines[0].startswith("IV="):
                raise ValueError("Tệp phải có dòng IV=... và một dòng bản mã.")
            iv = lines[0][3:].strip()
            ciphertext = lines[1].strip()
            self.iv.set(iv)
            self.write_text(self.ciphertext, ciphertext)
            self.saved_cipher = (ciphertext, iv)
            self.status.set("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.")
        except (OSError, ValueError) as error:
            messagebox.showerror("Lỗi mở tệp", str(error), parent=self)


def self_test():
    key = random_base64(32)
    texts = ("", "abc", "1234567890123456", "Văn bản dài có dấu tiếng Việt. " * 200)
    for text in texts:
        iv = random_base64(16)
        encrypted = encrypt_text(text, key, iv)
        assert decrypt_text(encrypted, key, iv) == text

    encrypted = encrypt_text("abc", key, random_base64(16))
    assert len(decode_base64(encrypted)) == 16
    print("SELF-TEST OK")


if __name__ == "__main__":
    if "--self-test" in sys.argv:
        self_test()
    else:
        App().mainloop()