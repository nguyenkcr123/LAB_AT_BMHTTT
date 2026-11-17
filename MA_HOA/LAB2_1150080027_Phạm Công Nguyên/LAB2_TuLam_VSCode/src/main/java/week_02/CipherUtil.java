package week_02;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public final class CipherUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final HexFormat HEX = HexFormat.of().withUpperCase();

    private CipherUtil() {}

    public static byte[] utf8(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }

    public static String text(byte[] value) {
        return new String(value, StandardCharsets.UTF_8);
    }

    public static String b64(byte[] value) {
        return Base64.getEncoder().encodeToString(value);
    }

    public static byte[] fromB64(String value) {
        return Base64.getDecoder().decode(value.trim());
    }

    public static String hex(byte[] value) {
        return HEX.formatHex(value);
    }

    public static byte[] fromHex(String value) {
        return HEX.parseHex(value.trim());
    }

    public static byte[] randomBytes(int size) {
        byte[] bytes = new byte[size];
        RANDOM.nextBytes(bytes);
        return bytes;
    }

    public static byte[] requireLength(byte[] bytes, int size, String name) {
        if (bytes.length != size) {
            throw new IllegalArgumentException(name + " phải đúng " + size + " byte.");
        }
        return bytes;
    }

    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static byte[] pbkdf2(char[] password, byte[] salt) throws GeneralSecurityException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 120_000, 256);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword();
        }
    }

    static byte[] withIv(Cipher cipher, boolean encrypt, String algorithm,
                         byte[] key, byte[] iv, byte[] input)
            throws GeneralSecurityException {
        cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
                new SecretKeySpec(key, algorithm), new IvParameterSpec(iv));
        return cipher.doFinal(input);
    }

    static String desCbc(boolean encrypt, String input, String key,
                         String algorithm, int keyBytes) throws GeneralSecurityException {
        byte[] secret = requireLength(utf8(key), keyBytes, "Key");
        byte[] data = encrypt ? utf8(input) : fromB64(input);
        if (!encrypt && (data.length < 16 || data.length % 8 != 0)) {
            throw new IllegalArgumentException("Bản mã CBC sai độ dài.");
        }

        byte[] iv = encrypt ? randomBytes(8) : Arrays.copyOf(data, 8);
        byte[] payload = encrypt ? data : Arrays.copyOfRange(data, 8, data.length);
        Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
        byte[] output = withIv(cipher, encrypt, algorithm, secret, iv, payload);
        return encrypt ? b64(concat(iv, output)) : text(output);
    }
}