package week_01;

import javax.swing.JOptionPane;

public class frm_LichSu extends javax.swing.JFrame {
    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    /** Đọc 50 dòng mới nhất từ SQL Server và hiện lên JTable. */
    private void loadData() {
     try {
     tbl_history.setModel(HistoryDao.loadLatest(50));
     lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng mới nhất từ dbo.history.");
     } catch (java.sql.SQLException ex) {
     lbl_status.setText("Không đọc được lịch sử: " + ex.getMessage());
     }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lịch sử thao tác");

        lbl_title = new javax.swing.JLabel();
        lbl_title.setText("LỊCH SỬ THAO TÁC - BẢNG DBO.HISTORY");
        lbl_title.setFont(new java.awt.Font("Segoe UI", 1, 18));

        sp_tbl_history = new javax.swing.JScrollPane();
        tbl_history = new javax.swing.JTable();
        sp_tbl_history.setViewportView(tbl_history);

        btn_refresh = new javax.swing.JButton();
        btn_refresh.setText("Tải lại");
        btn_refresh.addActionListener(evt -> btn_refreshActionPerformed(evt));

        lbl_status = new javax.swing.JLabel();
        lbl_status.setText("Đang tải...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_title, 810, 810, 810)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(sp_tbl_history, 810, 810, 810)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(btn_refresh, 110, 110, 110)
                    .addGap(12))
                .addGroup(layout.createSequentialGroup()
                    .addGap(16)
                    .addComponent(lbl_status, 810, 810, 810)
                    .addGap(12))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup().addGap(12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_title, 36, 36, 36))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sp_tbl_history, 280, 280, 280))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_refresh, 30, 30, 30))
                .addGap(8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_status, 24, 24, 24))
                .addGap(8)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        loadData();
    }//GEN-LAST:event_btn_refreshActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new frm_LichSu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_title;
    private javax.swing.JScrollPane sp_tbl_history;
    private javax.swing.JTable tbl_history;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JLabel lbl_status;
    // End of variables declaration//GEN-END:variables
}
