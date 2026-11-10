package week_01;

import javax.swing.JOptionPane;

public class frm_TongHop extends javax.swing.JFrame {
    public frm_TongHop() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chương trình tổng hợp mã hóa cổ điển");

        lbl_title = new javax.swing.JLabel();
        lbl_title.setText("CHƯƠNG TRÌNH TỔNG HỢP MÃ HÓA CỔ ĐIỂN");
        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 18));

        lbl_algorithm = new javax.swing.JLabel();
        lbl_algorithm.setText("Thuật toán:");

        cbo_algorithm = new javax.swing.JComboBox<>();
        cbo_algorithm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Caesar", "Vigenere", "Rail Fence", "Playfair", "Transposition", "Autokey"}));
        cbo_algorithm.addActionListener(evt -> cbo_algorithmActionPerformed(evt));

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
        txt_key.setText("3");

        lbl_hint = new javax.swing.JLabel();
        lbl_hint.setText("Khóa mẫu: 3");

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
        btn_open.setText("Open File");
        btn_open.addActionListener(evt -> btn_openActionPerformed(evt));

        btn_save = new javax.swing.JButton();
        btn_save.setText("Save File");
        btn_save.addActionListener(evt -> btn_saveActionPerformed(evt));

        btn_sql = new javax.swing.JButton();
        btn_sql.setText("Kết nối SQL Server");
        btn_sql.addActionListener(evt -> btn_sqlActionPerformed(evt));

        btn_history = new javax.swing.JButton();
        btn_history.setText("Xem lịch sử");
        btn_history.addActionListener(evt -> btn_historyActionPerformed(evt));

        lbl_status = new javax.swing.JLabel();
        lbl_status.setText("Sẵn sàng.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_title, 710, 710, 710)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_algorithm, 110, 110, 110)
                    .addGap(12)
                    .addComponent(cbo_algorithm, 230, 230, 230)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_plaintext, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(sp_txt_plaintext, 710, 710, 710)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_key, 52, 52, 52)
                    .addGap(12)
                    .addComponent(txt_key, 150, 150, 150)
                    .addGap(12)
                    .addComponent(lbl_hint, 470, 470, 470)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_ciphertext, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(sp_txt_ciphertext, 710, 710, 710)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_encrypt, 110, 110, 110)
                    .addGap(12)
                    .addComponent(btn_decrypt, 110, 110, 110)
                    .addGap(12)
                    .addComponent(btn_open, 110, 110, 110)
                    .addGap(12)
                    .addComponent(btn_save, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_sql, 172, 172, 172)
                    .addGap(12)
                    .addComponent(btn_history, 140, 140, 140)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_status, 710, 710, 710)
                    .addGap(12))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup().addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, 36, 36, 36))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_algorithm, 24, 24, 24)
                    .addComponent(cbo_algorithm, 30, 30, 30))
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
                    .addComponent(lbl_hint, 24, 24, 24))
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
                    .addComponent(btn_sql, 30, 30, 30)
                    .addComponent(btn_history, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_status, 24, 24, 24))
                .addGap(8)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbo_algorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_algorithmActionPerformed
        String algorithm = (String) cbo_algorithm.getSelectedItem();
        txt_key.setText(CipherTongHop.sampleKey(algorithm));
        lbl_hint.setText("Khóa mẫu: " + CipherTongHop.sampleKey(algorithm));
    }//GEN-LAST:event_cbo_algorithmActionPerformed

    private void btn_encryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_encryptActionPerformed
        String algorithm = (String) cbo_algorithm.getSelectedItem();
        String input = txt_plaintext.getText();
        try {
        String result = CipherTongHop.encrypt(algorithm, input, txt_key.getText());
        txt_ciphertext.setText(result);
        String saved = HistoryDao.tryInsert(algorithm, "ENCRYPT", input, result);
        lbl_status.setText("Đã mã hóa " + algorithm + ". " + saved);
        } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(),
        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_encryptActionPerformed

    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_decryptActionPerformed
        String algorithm = (String) cbo_algorithm.getSelectedItem();
        String input = txt_ciphertext.getText();
        try {
        String result = CipherTongHop.decrypt(algorithm, input, txt_key.getText());
        txt_plaintext.setText(result);
        String saved = HistoryDao.tryInsert(algorithm, "DECRYPT", input, result);
        lbl_status.setText("Đã giải mã " + algorithm + ". " + saved);
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
        String path = FileTextIO.saveText(this, txt_ciphertext, "ciphertext.txt");
        if (path != null) {
        lbl_status.setText("Đã lưu tệp: " + path);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sqlActionPerformed
        new frm_KetNoiSQL().setVisible(true);
    }//GEN-LAST:event_btn_sqlActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        new frm_LichSu().setVisible(true);
    }//GEN-LAST:event_btn_historyActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_TongHop().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_algorithm;
    private javax.swing.JComboBox<String> cbo_algorithm;
    private javax.swing.JLabel lbl_plaintext;
    private javax.swing.JScrollPane sp_txt_plaintext;
    private javax.swing.JTextArea txt_plaintext;
    private javax.swing.JLabel lbl_key;
    private javax.swing.JTextField txt_key;
    private javax.swing.JLabel lbl_hint;
    private javax.swing.JLabel lbl_ciphertext;
    private javax.swing.JScrollPane sp_txt_ciphertext;
    private javax.swing.JTextArea txt_ciphertext;
    private javax.swing.JButton btn_encrypt;
    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_open;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_sql;
    private javax.swing.JButton btn_history;
    private javax.swing.JLabel lbl_status;
    // End of variables declaration//GEN-END:variables
}
