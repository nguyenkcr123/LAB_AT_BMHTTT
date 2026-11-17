package week_02;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AESCipher {
    private AESCipher() {}

    public static String encrypt(String plaintext, String password) throws GeneralSecurityException {
        byte[] salt = CipherUtil.randomBytes(16);
        byte[] nonce = CipherUtil.randomBytes(12);
        byte[] payload = run(Cipher.ENCRYPT_MODE, CipherUtil.utf8(plaintext), password, salt, nonce);

        return CipherUtil.b64(CipherUtil.concat(CipherUtil.concat(salt, nonce), payload));
    }

    public static String decrypt(String ciphertext, String password) throws GeneralSecurityException {
        byte[] data = CipherUtil.fromB64(ciphertext);
        if (data.length < 44) {
            throw new IllegalArgumentException("Bản mã AES-GCM quá ngắn.");
        }

        byte[] salt = Arrays.copyOfRange(data, 0, 16);
        byte[] nonce = Arrays.copyOfRange(data, 16, 28);
        byte[] payload = Arrays.copyOfRange(data, 28, data.length);
        return CipherUtil.text(run(Cipher.DECRYPT_MODE, payload, password, salt, nonce));
    }

    private static byte[] run(int mode, byte[] input, String password, byte[] salt, byte[] nonce)
            throws GeneralSecurityException {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Hãy nhập Key AES.");
        }

        byte[] key = CipherUtil.pbkdf2(password.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(mode, new SecretKeySpec(key, "AES"), new GCMParameterSpec(128, nonce));
        return cipher.doFinal(input);
    }
}