package week_02;

public final class RC4Cipher {
    private RC4Cipher() {}

    public static String encrypt(String plaintext, String key) {
        return CipherUtil.hex(apply(CipherUtil.utf8(plaintext), CipherUtil.utf8(key)));
    }

    public static String decrypt(String ciphertext, String key) {
        return CipherUtil.text(apply(CipherUtil.fromHex(ciphertext), CipherUtil.utf8(key)));
    }

    private static byte[] apply(byte[] input, byte[] key) {
        if (key.length < 1 || key.length > 256) {
            throw new IllegalArgumentException("Key RC4 phải từ 1 đến 256 byte.");
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

        byte[] output = new byte[input.length];
        int i = 0;
        j = 0;
        for (int position = 0; position < input.length; position++) {
            i = (i + 1) % 256;
            j = (j + state[i]) % 256;
            swap(state, i, j);
            int keyByte = state[(state[i] + state[j]) % 256];
            output[position] = (byte) (input[position] ^ keyByte);
        }
        return output;
    }

    private static void swap(int[] state, int i, int j) {
        int temporary = state[i];
        state[i] = state[j];
        state[j] = temporary;
    }
}