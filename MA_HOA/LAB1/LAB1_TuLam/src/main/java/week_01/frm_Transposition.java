package week_01;

import javax.swing.JOptionPane;

public class frm_Transposition extends javax.swing.JFrame {
    public frm_Transposition() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transposition Cipher");

        lbl_title = new javax.swing.JLabel();
        lbl_title.setText("MẬT MÃ HOÁN VỊ CỘT (TRANSPOSITION)");
        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 18));

        lbl_plaintext = new javax.swing.JLabel();
        lbl_plaintext.setText("Plaintext:");

        sp_txt_plaintext = new javax.swing.JScrollPane();
        txt_plaintext = new javax.swing.JTextArea();
        txt_plaintext.setRows(3);
        txt_plaintext.setEditable(true);
        txt_plaintext.setLineWrap(true);
        sp_txt_plaintext.setViewportView(txt_plaintext);

        lbl_key = new javax.swing.JLabel();
        lbl_key.setText("Key:");

        txt_key = new javax.swing.JTextField();
        txt_key.setText("2,3,1");

        lbl_key_hint = new javax.swing.JLabel();
        lbl_key_hint.setText("Hoán vị 1..m, ví dụ 2,3,1");

        lbl_ciphertext = new javax.swing.JLabel();
        lbl_ciphertext.setText("Ciphertext:");

        sp_txt_ciphertext = new javax.swing.JScrollPane();
        txt_ciphertext = new javax.swing.JTextArea();
        txt_ciphertext.setRows(3);
        txt_ciphertext.setEditable(true);
        txt_ciphertext.setLineWrap(true);
        sp_txt_ciphertext.setViewportView(txt_ciphertext);

        btn_encrypt = new javax.swing.JButton();
        btn_encrypt.setText("Encrypt");
        btn_encrypt.addActionListener(evt -> btn_encryptActionPerformed(evt));

        btn_decrypt = new javax.swing.JButton();
        btn_decrypt.setText("Decrypt");
        btn_decrypt.addActionListener(evt -> btn_decryptActionPerformed(evt));

        btn_open = new javax.swing.JButton();
        btn_open.setText("Open Ciphertext File");
        btn_open.addActionListener(evt -> btn_openActionPerformed(evt));

        btn_save = new javax.swing.JButton();
        btn_save.setText("Save Ciphertext File");
        btn_save.addActionListener(evt -> btn_saveActionPerformed(evt));

        lbl_status = new javax.swing.JLabel();
        lbl_status.setText("Sẵn sàng.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_title, 630, 630, 630)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_plaintext, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(sp_txt_plaintext, 630, 630, 630)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_key, 60, 60, 60)
                    .addGap(12)
                    .addComponent(txt_key, 130, 130, 130)
                    .addGap(12)
                    .addComponent(lbl_key_hint, 400, 400, 400)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_ciphertext, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(sp_txt_ciphertext, 630, 630, 630)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_encrypt, 104, 104, 104)
                    .addGap(12)
                    .addComponent(btn_decrypt, 104, 104, 104)
                    .addGap(12)
                    .addComponent(btn_open, 174, 174, 174)
                    .addGap(12)
                    .addComponent(btn_save, 174, 174, 174)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_status, 630, 630, 630)
                    .addGap(12))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup().addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, 36, 36, 36))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_plaintext, 24, 24, 24))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sp_txt_plaintext, 74, 74, 74))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_key, 24, 24, 24)
                    .addComponent(txt_key, 28, 28, 28)
                    .addComponent(lbl_key_hint, 24, 24, 24))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_ciphertext, 24, 24, 24))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sp_txt_ciphertext, 74, 74, 74))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_encrypt, 30, 30, 30)
                    .addComponent(btn_decrypt, 30, 30, 30)
                    .addComponent(btn_open, 30, 30, 30)
                    .addComponent(btn_save, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_status, 24, 24, 24))
                .addGap(8)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_encryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_encryptActionPerformed
        String plaintext = txt_plaintext.getText();
        if (plaintext.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
        return;
        }
        try {
        int[] key = TranspositionCipher.parseKey(txt_key.getText());
        txt_ciphertext.setText(TranspositionCipher.encrypt(plaintext, key));
        lbl_status.setText("Đã mã hóa với " + key.length + " cột.");
        } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(),
        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_encryptActionPerformed

    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_decryptActionPerformed
        String ciphertext = txt_ciphertext.getText();
        if (ciphertext.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
        return;
        }
        try {
        int[] key = TranspositionCipher.parseKey(txt_key.getText());
        txt_plaintext.setText(TranspositionCipher.decrypt(ciphertext, key));
        lbl_status.setText("Đã giải mã với " + key.length + " cột.");
        } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(),
        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_decryptActionPerformed

    private void btn_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_openActionPerformed
        String path = FileTextIO.openText(this, txt_ciphertext);
        if (path != null) {
        lbl_status.setText("Đã mở tệp: " + path);
        }
    }//GEN-LAST:event_btn_openActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String path = FileTextIO.saveText(this, txt_ciphertext, "transposition_ciphertext.txt");
        if (path != null) {
        lbl_status.setText("Đã lưu tệp: " + path);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Transposition().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_plaintext;
    private javax.swing.JScrollPane sp_txt_plaintext;
    private javax.swing.JTextArea txt_plaintext;
    private javax.swing.JLabel lbl_key;
    private javax.swing.JTextField txt_key;
    private javax.swing.JLabel lbl_key_hint;
    private javax.swing.JLabel lbl_ciphertext;
    private javax.swing.JScrollPane sp_txt_ciphertext;
    private javax.swing.JTextArea txt_ciphertext;
    private javax.swing.JButton btn_encrypt;
    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_open;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel lbl_status;
    // End of variables declaration//GEN-END:variables
}
