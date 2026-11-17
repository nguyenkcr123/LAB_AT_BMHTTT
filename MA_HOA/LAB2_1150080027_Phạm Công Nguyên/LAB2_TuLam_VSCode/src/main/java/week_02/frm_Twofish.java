package week_02;

public class frm_Twofish extends CipherForm {
    public frm_Twofish() {
        super("Twofish", "Twofish@Lab2", "Key", "IV=", "IV (HEX)", "Generate IV",
                null, TwofishCipher::newIv, TwofishCipher::encrypt, TwofishCipher::decrypt);
        finishLayout();
    }

    public static void main(String[] args) {
        launch(frm_Twofish::new);
    }
}