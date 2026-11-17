package week_02;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.HexFormat;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

final class Crypto {
    enum Mode { DES, TRIPLE_DES, AES_GCM, RC4, TWOFISH, AES_CTR }

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final HexFormat HEX = HexFormat.of().withUpperCase();
    private static final BouncyCastleProvider BC = new BouncyCastleProvider();

    private Crypto() {}

    static byte[] random(int size) {
        byte[] bytes = new byte[size];
        RANDOM.nextBytes(bytes);
        return bytes;
    }

    static byte[] utf8(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }

    static String base64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    static String hex(byte[] bytes) {
        return HEX.formatHex(bytes);
    }

    static byte[] derive(char[] password, byte[] salt) throws GeneralSecurityException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 120_000, 256);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword();
        }
    }

    static String transform(Mode mode, boolean encrypt, String input, String key,
                            String parameter) throws GeneralSecurityException {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Hãy nhập Key.");
        }

        byte[] data = encrypt
                ? utf8(input)
                : mode == Mode.RC4 ? HEX.parseHex(input.trim()) : decode(input);

        byte[] result = switch (mode) {
            case DES -> des(encrypt, data, key, "DES", 8);
            case TRIPLE_DES -> des(encrypt, data, key, "DESede", 24);
            case AES_GCM -> gcm(encrypt, data, key);
            case RC4 -> rc4(data, utf8(key));
            case TWOFISH -> twofish(encrypt, data, key, parameter);
            case AES_CTR -> ctr(encrypt, data, key, parameter);
        };

        if (!encrypt) {
            return new String(result, StandardCharsets.UTF_8);
        }
        return mode == Mode.RC4 ? hex(result) : base64(result);
    }

    private static byte[] des(boolean encrypt, byte[] data, String password,
                              String algorithm, int keySize) throws GeneralSecurityException {
        byte[] key = requireLength(utf8(password), keySize, "Key");
        if (!encrypt && (data.length < 16 || data.length % 8 != 0)) {
            throw new IllegalArgumentException("Bản mã CBC sai độ dài.");
        }
        byte[] iv = encrypt ? random(8) : Arrays.copyOf(data, 8);
        byte[] payload = encrypt ? data : Arrays.copyOfRange(data, 8, data.length);
        Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
        byte[] result = run(cipher, encrypt, algorithm, key, iv, payload);
        return encrypt ? join(iv, result) : result;
    }

    private static byte[] gcm(boolean encrypt, byte[] data, String password)
            throws GeneralSecurityException {
        if (!encrypt && data.length < 44) {
            throw new IllegalArgumentException("Bản mã AES-GCM quá ngắn.");
        }
        byte[] salt = encrypt ? random(16) : Arrays.copyOf(data, 16);
        byte[] nonce = encrypt ? random(12) : Arrays.copyOfRange(data, 16, 28);
        byte[] payload = encrypt ? data : Arrays.copyOfRange(data, 28, data.length);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(cipherMode(encrypt),
                new SecretKeySpec(derive(password.toCharArray(), salt), "AES"),
                new GCMParameterSpec(128, nonce));
        byte[] result = cipher.doFinal(payload);
        return encrypt ? join(join(salt, nonce), result) : result;
    }

    private static byte[] twofish(boolean encrypt, byte[] data, String password, String ivText)
            throws GeneralSecurityException {
        byte[] iv = requireLength(HEX.parseHex(ivText.trim()), 16, "IV");
        byte[] key = MessageDigest.getInstance("SHA-256").digest(utf8(password));
        Cipher cipher = Cipher.getInstance("Twofish/CBC/PKCS7Padding", BC);
        return run(cipher, encrypt, "Twofish", key, iv, data);
    }

    private static byte[] ctr(boolean encrypt, byte[] data, String keyText, String nonceText)
            throws GeneralSecurityException {
        byte[] key = requireLength(decode(keyText), 32, "Key AES-CTR");
        byte[] nonce = requireLength(decode(nonceText), 12, "Nonce");
        byte[] counterBlock = Arrays.copyOf(nonce, 16);
        counterBlock[15] = 1;

        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        return run(cipher, encrypt, "AES", key, counterBlock, data);
    }

    private static byte[] rc4(byte[] data, byte[] key) {
        if (key.length == 0 || key.length > 256) {
            throw new IllegalArgumentException("Key RC4 phải có từ 1 đến 256 byte.");
        }

        int[] state = new int[256];
        for (int i = 0; i < state.length; i++) {
            state[i] = i;
        }
        int j = 0;
        for (int i = 0; i < state.length; i++) {
            j = (j + state[i] + Byte.toUnsignedInt(key[i % key.length])) % 256;
            swap(state, i, j);
        }

        byte[] result = new byte[data.length];
        int i = 0;
        j = 0;
        for (int position = 0; position < data.length; position++) {
            i = (i + 1) % 256;
            j = (j + state[i]) % 256;
            swap(state, i, j);
            int keyByte = state[(state[i] + state[j]) % 256];
            result[position] = (byte) (data[position] ^ keyByte);
        }
        return result;
    }

    private static void swap(int[] state, int i, int j) {
        int value = state[i];
        state[i] = state[j];
        state[j] = value;
    }

    private static byte[] run(Cipher cipher, boolean encrypt, String algorithm,
                              byte[] key, byte[] iv, byte[] data)
            throws GeneralSecurityException {
        cipher.init(cipherMode(encrypt), new SecretKeySpec(key, algorithm),
                new IvParameterSpec(iv));
        return cipher.doFinal(data);
    }

    private static int cipherMode(boolean encrypt) {
        return encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
    }

    private static byte[] requireLength(byte[] bytes, int size, String name) {
        if (bytes.length != size) {
            throw new IllegalArgumentException(name + " phải đúng " + size + " byte.");
        }
        return bytes;
    }

    private static byte[] decode(String value) {
        return Base64.getDecoder().decode(value.trim());
    }

    private static byte[] join(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}