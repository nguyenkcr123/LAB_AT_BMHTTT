package week_02;

public class frm_TripleDES extends CipherForm {
    public frm_TripleDES() {
        super("TripleDES", "123456789012345678901234", "Key (24 byte)",
                "", "", "", null, null,
                (text, key, parameter) -> TripleDESCipher.encrypt(text, key),
                (text, key, parameter) -> TripleDESCipher.decrypt(text, key));
        finishLayout();
    }

    public static void main(String[] args) {
        launch(frm_TripleDES::new);
    }
}