package week_02;

import java.security.GeneralSecurityException;

public final class TripleDESCipher {
    private TripleDESCipher() {}

    public static String encrypt(String plaintext, String key) throws GeneralSecurityException {
        return CipherUtil.desCbc(true, plaintext, key, "DESede", 24);
    }

    public static String decrypt(String ciphertext, String key) throws GeneralSecurityException {
        return CipherUtil.desCbc(false, ciphertext, key, "DESede", 24);
    }
}