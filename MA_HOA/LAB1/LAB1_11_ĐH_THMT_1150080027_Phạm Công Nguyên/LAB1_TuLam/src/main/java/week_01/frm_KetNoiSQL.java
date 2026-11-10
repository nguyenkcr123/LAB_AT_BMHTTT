package week_01;

import javax.swing.JOptionPane;

public class frm_KetNoiSQL extends javax.swing.JFrame {
    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kết nối SQL Server");

        lbl_title = new javax.swing.JLabel();
        lbl_title.setText("KẾT NỐI MICROSOFT SQL SERVER");
        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 18));

        lbl_server = new javax.swing.JLabel();
        lbl_server.setText("Server:");

        txt_server = new javax.swing.JTextField();
        txt_server.setText("127.0.0.1");

        lbl_port = new javax.swing.JLabel();
        lbl_port.setText("Port:");

        txt_port = new javax.swing.JTextField();
        txt_port.setText("14333");

        lbl_database = new javax.swing.JLabel();
        lbl_database.setText("Database:");

        txt_database = new javax.swing.JTextField();
        txt_database.setText("LAB1_MaHoaCoDien");

        lbl_user = new javax.swing.JLabel();
        lbl_user.setText("User:");

        txt_user = new javax.swing.JTextField();
        txt_user.setText("lab1_user");

        lbl_password = new javax.swing.JLabel();
        lbl_password.setText("Password:");

        txt_password = new javax.swing.JPasswordField();
        txt_password.setText("");

        btn_test = new javax.swing.JButton();
        btn_test.setText("Test Connection");
        btn_test.addActionListener(evt -> btn_testActionPerformed(evt));

        btn_history = new javax.swing.JButton();
        btn_history.setText("Xem lịch sử");
        btn_history.addActionListener(evt -> btn_historyActionPerformed(evt));

        lbl_status = new javax.swing.JLabel();
        lbl_status.setText("Chưa kết nối.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_title, 450, 450, 450)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_server, 110, 110, 110)
                    .addGap(12)
                    .addComponent(txt_server, 320, 320, 320)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_port, 110, 110, 110)
                    .addGap(12)
                    .addComponent(txt_port, 320, 320, 320)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_database, 110, 110, 110)
                    .addGap(12)
                    .addComponent(txt_database, 320, 320, 320)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_user, 110, 110, 110)
                    .addGap(12)
                    .addComponent(txt_user, 320, 320, 320)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_password, 110, 110, 110)
                    .addGap(12)
                    .addComponent(txt_password, 320, 320, 320)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_test, 150, 150, 150)
                    .addGap(12)
                    .addComponent(btn_history, 130, 130, 130)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_status, 450, 450, 450)
                    .addGap(12))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup().addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, 36, 36, 36))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_server, 24, 24, 24)
                    .addComponent(txt_server, 28, 28, 28))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_port, 24, 24, 24)
                    .addComponent(txt_port, 28, 28, 28))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_database, 24, 24, 24)
                    .addComponent(txt_database, 28, 28, 28))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_user, 24, 24, 24)
                    .addComponent(txt_user, 28, 28, 28))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_password, 24, 24, 24)
                    .addComponent(txt_password, 28, 28, 28))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_test, 30, 30, 30)
                    .addComponent(btn_history, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_status, 44, 44, 44))
                .addGap(8)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_testActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_testActionPerformed
        try {
        int port = Integer.parseInt(txt_port.getText().trim());
        DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
        txt_user.getText().trim(), txt_password.getPassword());
        lbl_status.setText("<html>" + DbConfig.test() + "</html>");
        } catch (Exception ex) {
        DbConfig.clear();
        lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
        JOptionPane.showMessageDialog(this, ex.getMessage(),
        "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_testActionPerformed

    private void btn_historyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_historyActionPerformed
        new frm_LichSu().setVisible(true);
    }//GEN-LAST:event_btn_historyActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_KetNoiSQL().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_server;
    private javax.swing.JTextField txt_server;
    private javax.swing.JLabel lbl_port;
    private javax.swing.JTextField txt_port;
    private javax.swing.JLabel lbl_database;
    private javax.swing.JTextField txt_database;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JTextField txt_user;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JButton btn_test;
    private javax.swing.JButton btn_history;
    private javax.swing.JLabel lbl_status;
    // End of variables declaration//GEN-END:variables
}
