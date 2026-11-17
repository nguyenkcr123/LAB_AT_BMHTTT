package week_02;

public class frm_AES_CTR extends CipherForm {
    public frm_AES_CTR() {
        super("AES-CTR", "", "Key (Base64)", "NONCE=", "Nonce (Base64)", "Generate Nonce",
                AESCTRCipher::newKey, AESCTRCipher::newNonce,
                AESCTRCipher::encrypt, AESCTRCipher::decrypt);
        finishLayout();
    }

    public static void main(String[] args) {
        launch(frm_AES_CTR::new);
    }
}