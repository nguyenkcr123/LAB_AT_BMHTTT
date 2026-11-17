package week_02;

import java.security.GeneralSecurityException;

public final class DESCipher {
    private DESCipher() {}

    public static String encrypt(String plaintext, String key) throws GeneralSecurityException {
        return CipherUtil.desCbc(true, plaintext, key, "DES", 8);
    }

    public static String decrypt(String ciphertext, String key) throws GeneralSecurityException {
        return CipherUtil.desCbc(false, ciphertext, key, "DES", 8);
    }
}