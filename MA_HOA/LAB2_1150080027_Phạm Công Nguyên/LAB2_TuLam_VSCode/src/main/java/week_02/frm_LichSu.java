package week_02;

import java.awt.BorderLayout;
import javax.swing.*;

public class frm_LichSu extends JPanel {
    private final JTable table = new JTable(HistoryDao.emptyModel());
    private final JLabel status = new JLabel("Chưa tải lịch sử.");

    public frm_LichSu() {
        super(new BorderLayout());
        setName("Lịch sử thao tác SQL Server");
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(CipherForm.row(CipherForm.button("Tải lại", this::loadData), status),
                BorderLayout.SOUTH);
    }

    public void loadData() {
        try {
            table.setModel(HistoryDao.loadLatest());
            status.setText("Đã tải " + table.getRowCount() + " dòng.");
        } catch (Exception ex) {
            status.setText(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        CipherForm.launch(() -> {
            frm_LichSu history = new frm_LichSu();
            history.loadData();
            return history;
        });
    }
}
