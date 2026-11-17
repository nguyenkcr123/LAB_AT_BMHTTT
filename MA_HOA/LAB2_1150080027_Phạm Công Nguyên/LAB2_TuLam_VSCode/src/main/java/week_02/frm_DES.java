package week_02;

public class frm_DES extends CipherForm {
    public frm_DES() {
        super("DES", "12345678", "Key (8 byte)", "", "", "", null, null,
                (text, key, parameter) -> DESCipher.encrypt(text, key),
                (text, key, parameter) -> DESCipher.decrypt(text, key));
        finishLayout();
    }

    public static void main(String[] args) {
        launch(frm_DES::new);
    }
}