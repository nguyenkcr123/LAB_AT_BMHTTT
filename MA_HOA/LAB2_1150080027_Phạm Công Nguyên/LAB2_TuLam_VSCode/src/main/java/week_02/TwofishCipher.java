package week_02;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class TwofishCipher {
    private static final BouncyCastleProvider PROVIDER = new BouncyCastleProvider();

    private TwofishCipher() {}

    public static String newIv() {
        return CipherUtil.hex(CipherUtil.randomBytes(16));
    }

    public static String encrypt(String plaintext, String key, String iv)
            throws GeneralSecurityException {
        return CipherUtil.b64(run(true, CipherUtil.utf8(plaintext), key, iv));
    }

    public static String decrypt(String ciphertext, String key, String iv)
            throws GeneralSecurityException {
        return CipherUtil.text(run(false, CipherUtil.fromB64(ciphertext), key, iv));
    }

    private static byte[] run(boolean encrypt, byte[] input, String password, String ivText)
            throws GeneralSecurityException {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Hãy nhập Key.");
        }

        byte[] key = MessageDigest.getInstance("SHA-256").digest(CipherUtil.utf8(password));
        byte[] iv = CipherUtil.requireLength(CipherUtil.fromHex(ivText), 16, "IV");
        Cipher cipher = Cipher.getInstance("Twofish/CBC/PKCS7Padding", PROVIDER);
        return CipherUtil.withIv(cipher, encrypt, "Twofish", key, iv, input);
    }
}