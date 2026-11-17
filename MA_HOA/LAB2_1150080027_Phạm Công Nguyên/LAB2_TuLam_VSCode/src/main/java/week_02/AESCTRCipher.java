package week_02;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.crypto.Cipher;

public final class AESCTRCipher {
    private AESCTRCipher() {}

    public static String newKey() {
        return CipherUtil.b64(CipherUtil.randomBytes(32));
    }

    public static String newNonce() {
        return CipherUtil.b64(CipherUtil.randomBytes(12));
    }

    public static String encrypt(String plaintext, String key, String nonce)
            throws GeneralSecurityException {
        return CipherUtil.b64(run(true, CipherUtil.utf8(plaintext), key, nonce));
    }

    public static String decrypt(String ciphertext, String key, String nonce)
            throws GeneralSecurityException {
        return CipherUtil.text(run(false, CipherUtil.fromB64(ciphertext), key, nonce));
    }

    private static byte[] run(boolean encrypt, byte[] input, String keyText, String nonceText)
            throws GeneralSecurityException {
        byte[] key = CipherUtil.requireLength(CipherUtil.fromB64(keyText), 32, "Key");
        byte[] nonce = CipherUtil.requireLength(CipherUtil.fromB64(nonceText), 12, "Nonce");
        byte[] counterBlock = Arrays.copyOf(nonce, 16);
        counterBlock[15] = 1;

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        return CipherUtil.withIv(cipher, encrypt, "AES", key, counterBlock, input);
    }
}