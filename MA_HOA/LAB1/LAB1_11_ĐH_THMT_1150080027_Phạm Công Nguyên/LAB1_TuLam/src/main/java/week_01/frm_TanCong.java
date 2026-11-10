package week_01;

import javax.swing.JOptionPane;

public class frm_TanCong extends javax.swing.JFrame {
    public frm_TanCong() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Phân tích mật mã cổ điển");

        lbl_title = new javax.swing.JLabel();
        lbl_title.setText("PHÂN TÍCH (TẤN CÔNG) MẬT MÃ CỔ ĐIỂN");
        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 18));

        lbl_cipher = new javax.swing.JLabel();
        lbl_cipher.setText("Ciphertext:");

        sp_txt_cipher = new javax.swing.JScrollPane();
        txt_cipher = new javax.swing.JTextArea();
        txt_cipher.setRows(2);
        txt_cipher.setEditable(true);
        txt_cipher.setLineWrap(true);
        sp_txt_cipher.setViewportView(txt_cipher);

        btn_bruteforce = new javax.swing.JButton();
        btn_bruteforce.setText("Brute Force Caesar");
        btn_bruteforce.addActionListener(evt -> btn_bruteforceActionPerformed(evt));

        btn_frequency = new javax.swing.JButton();
        btn_frequency.setText("Frequency Analysis");
        btn_frequency.addActionListener(evt -> btn_frequencyActionPerformed(evt));

        lbl_known_plain = new javax.swing.JLabel();
        lbl_known_plain.setText("Bản rõ đã biết:");

        txt_known_plain = new javax.swing.JTextField();
        txt_known_plain.setText("HELLO");

        lbl_known_cipher = new javax.swing.JLabel();
        lbl_known_cipher.setText("Bản mã:");

        txt_known_cipher = new javax.swing.JTextField();
        txt_known_cipher.setText("RIJVS");

        btn_known = new javax.swing.JButton();
        btn_known.setText("Known Plaintext (Vigenere)");
        btn_known.addActionListener(evt -> btn_knownActionPerformed(evt));

        lbl_result = new javax.swing.JLabel();
        lbl_result.setText("Kết quả:");

        sp_txt_result = new javax.swing.JScrollPane();
        txt_result = new javax.swing.JTextArea();
        txt_result.setRows(3);
        txt_result.setEditable(false);
        txt_result.setLineWrap(false);
        sp_txt_result.setViewportView(txt_result);

        lbl_status = new javax.swing.JLabel();
        lbl_status.setText("Sẵn sàng.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_title, 680, 680, 680)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_cipher, 110, 110, 110)
                    .addGap(12)
                    .addComponent(sp_txt_cipher, 558, 558, 558)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_bruteforce, 170, 170, 170)
                    .addGap(12)
                    .addComponent(btn_frequency, 170, 170, 170)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_known_plain, 110, 110, 110)
                    .addGap(12)
                    .addComponent(txt_known_plain, 130, 130, 130)
                    .addGap(12)
                    .addComponent(lbl_known_cipher, 68, 68, 68)
                    .addGap(12)
                    .addComponent(txt_known_cipher, 130, 130, 130)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_known, 225, 225, 225)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_result, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(sp_txt_result, 680, 680, 680)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_status, 680, 680, 680)
                    .addGap(12))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup().addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, 36, 36, 36))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_cipher, 24, 24, 24)
                    .addComponent(sp_txt_cipher, 52, 52, 52))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_bruteforce, 30, 30, 30)
                    .addComponent(btn_frequency, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_known_plain, 24, 24, 24)
                    .addComponent(txt_known_plain, 28, 28, 28)
                    .addComponent(lbl_known_cipher, 24, 24, 24)
                    .addComponent(txt_known_cipher, 28, 28, 28))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_known, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_result, 24, 24, 24))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sp_txt_result, 264, 264, 264))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_status, 24, 24, 24))
                .addGap(8)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_bruteforceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bruteforceActionPerformed
        String cipher = txt_cipher.getText().trim();
        if (cipher.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext.");
        return;
        }
        txt_result.setText(AttackDemos.caesarBruteForce(cipher));
        txt_result.setCaretPosition(0);
        lbl_status.setText("Đã thử đủ 26 khóa Caesar.");
    }//GEN-LAST:event_btn_bruteforceActionPerformed

    private void btn_frequencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_frequencyActionPerformed
        String cipher = txt_cipher.getText().trim();
        if (cipher.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext.");
        return;
        }
        txt_result.setText(AttackDemos.frequency(cipher));
        txt_result.setCaretPosition(0);
        lbl_status.setText("Đã thống kê tần suất chữ cái.");
    }//GEN-LAST:event_btn_frequencyActionPerformed

    private void btn_knownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_knownActionPerformed
        try {
        String key = AttackDemos.knownPlaintext(txt_known_plain.getText(), txt_known_cipher.getText());  txt_result.setText("Khóa Vigenère suy ra từng vị trí: " + key
        + "\nNếu khóa ngắn hơn bản rõ, khóa sẽ lặp lại (KEY KEY ...).");
        lbl_status.setText("Khóa suy ra: " + key);
        } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(),
        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_knownActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_TanCong().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_cipher;
    private javax.swing.JScrollPane sp_txt_cipher;
    private javax.swing.JTextArea txt_cipher;
    private javax.swing.JButton btn_bruteforce;
    private javax.swing.JButton btn_frequency;
    private javax.swing.JLabel lbl_known_plain;
    private javax.swing.JTextField txt_known_plain;
    private javax.swing.JLabel lbl_known_cipher;
    private javax.swing.JTextField txt_known_cipher;
    private javax.swing.JButton btn_known;
    private javax.swing.JLabel lbl_result;
    private javax.swing.JScrollPane sp_txt_result;
    private javax.swing.JTextArea txt_result;
    private javax.swing.JLabel lbl_status;
    // End of variables declaration//GEN-END:variables
}
