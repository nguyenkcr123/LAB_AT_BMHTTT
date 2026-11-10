package week_01;

import javax.swing.JOptionPane;

public class frm_Menu extends javax.swing.JFrame {
    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LAB 1 - Mã hóa đối xứng cổ điển");

        lbl_title = new javax.swing.JLabel();
        lbl_title.setText("LAB 1 - MÃ HÓA ĐỐI XỨNG CỔ ĐIỂN");
        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 18));

        lbl_prompt = new javax.swing.JLabel();
        lbl_prompt.setText("Chọn một bài để mở form tương ứng:");

        btn_caesar = new javax.swing.JButton();
        btn_caesar.setText("1.1 Caesar");
        btn_caesar.addActionListener(evt -> btn_caesarActionPerformed(evt));

        btn_vigenere = new javax.swing.JButton();
        btn_vigenere.setText("1.2 Vigenère");
        btn_vigenere.addActionListener(evt -> btn_vigenereActionPerformed(evt));

        btn_railfence = new javax.swing.JButton();
        btn_railfence.setText("1.3 Rail Fence");
        btn_railfence.addActionListener(evt -> btn_railfenceActionPerformed(evt));

        btn_playfair = new javax.swing.JButton();
        btn_playfair.setText("1.4 Playfair");
        btn_playfair.addActionListener(evt -> btn_playfairActionPerformed(evt));

        btn_transposition = new javax.swing.JButton();
        btn_transposition.setText("1.5 Transposition");
        btn_transposition.addActionListener(evt -> btn_transpositionActionPerformed(evt));

        btn_autokey = new javax.swing.JButton();
        btn_autokey.setText("1.6 Autokey");
        btn_autokey.addActionListener(evt -> btn_autokeyActionPerformed(evt));

        btn_tonghop = new javax.swing.JButton();
        btn_tonghop.setText("Nâng cao 01 - Tổng hợp");
        btn_tonghop.addActionListener(evt -> btn_tonghopActionPerformed(evt));

        btn_tancong = new javax.swing.JButton();
        btn_tancong.setText("Nâng cao 02 - Tấn công");
        btn_tancong.addActionListener(evt -> btn_tancongActionPerformed(evt));

        btn_sql = new javax.swing.JButton();
        btn_sql.setText("Kết nối SQL Server");
        btn_sql.addActionListener(evt -> btn_sqlActionPerformed(evt));

        lbl_note = new javax.swing.JLabel();
        lbl_note.setText("Mỗi form mở ra là một cửa sổ riêng; đóng form con không làm tắt chương trình.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_title, 675, 675, 675)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_prompt, 675, 675, 675)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_caesar, 215, 215, 215)
                    .addGap(12)
                    .addComponent(btn_vigenere, 215, 215, 215)
                    .addGap(12)
                    .addComponent(btn_railfence, 215, 215, 215)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_playfair, 215, 215, 215)
                    .addGap(12)
                    .addComponent(btn_transposition, 215, 215, 215)
                    .addGap(12)
                    .addComponent(btn_autokey, 215, 215, 215)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_tonghop, 215, 215, 215)
                    .addGap(12)
                    .addComponent(btn_tancong, 215, 215, 215)
                    .addGap(12)
                    .addComponent(btn_sql, 215, 215, 215)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_note, 675, 675, 675)
                    .addGap(12))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup().addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, 36, 36, 36))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_prompt, 24, 24, 24))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_caesar, 30, 30, 30)
                    .addComponent(btn_vigenere, 30, 30, 30)
                    .addComponent(btn_railfence, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_playfair, 30, 30, 30)
                    .addComponent(btn_transposition, 30, 30, 30)
                    .addComponent(btn_autokey, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_tonghop, 30, 30, 30)
                    .addComponent(btn_tancong, 30, 30, 30)
                    .addComponent(btn_sql, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_note, 24, 24, 24))
                .addGap(8)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_caesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caesarActionPerformed
        new frm_Caesar().setVisible(true);
    }//GEN-LAST:event_btn_caesarActionPerformed

    private void btn_vigenereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vigenereActionPerformed
        new frm_Vigenere().setVisible(true);
    }//GEN-LAST:event_btn_vigenereActionPerformed

    private void btn_railfenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_railfenceActionPerformed
        new frm_RailFence().setVisible(true);
    }//GEN-LAST:event_btn_railfenceActionPerformed

    private void btn_playfairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_playfairActionPerformed
        new frm_Playfair().setVisible(true);
    }//GEN-LAST:event_btn_playfairActionPerformed

    private void btn_transpositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transpositionActionPerformed
        new frm_Transposition().setVisible(true);
    }//GEN-LAST:event_btn_transpositionActionPerformed

    private void btn_autokeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_autokeyActionPerformed
        new frm_Autokey().setVisible(true);
    }//GEN-LAST:event_btn_autokeyActionPerformed

    private void btn_tonghopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tonghopActionPerformed
        new frm_TongHop().setVisible(true);
    }//GEN-LAST:event_btn_tonghopActionPerformed

    private void btn_tancongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tancongActionPerformed
        new frm_TanCong().setVisible(true);
    }//GEN-LAST:event_btn_tancongActionPerformed

    private void btn_sqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sqlActionPerformed
        new frm_KetNoiSQL().setVisible(true);
    }//GEN-LAST:event_btn_sqlActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_Menu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_prompt;
    private javax.swing.JButton btn_caesar;
    private javax.swing.JButton btn_vigenere;
    private javax.swing.JButton btn_railfence;
    private javax.swing.JButton btn_playfair;
    private javax.swing.JButton btn_transposition;
    private javax.swing.JButton btn_autokey;
    private javax.swing.JButton btn_tonghop;
    private javax.swing.JButton btn_tancong;
    private javax.swing.JButton btn_sql;
    private javax.swing.JLabel lbl_note;
    // End of variables declaration//GEN-END:variables
}
