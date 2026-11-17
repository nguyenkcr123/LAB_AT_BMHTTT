package week_02;

public class frm_RC4 extends CipherForm {
    public frm_RC4() {
        super("RC4", "Key", "Key", "", "", "", null, null,
                (text, key, parameter) -> RC4Cipher.encrypt(text, key),
                (text, key, parameter) -> RC4Cipher.decrypt(text, key));
        finishLayout();
    }

    public static void main(String[] args) {
        launch(frm_RC4::new);
    }
}