package week_02;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class frm_KetNoiSQL extends JPanel {
    private final JTextField server = new JTextField("127.0.0.1", 24);
    private final JTextField port = new JTextField("14333");
    private final JTextField database = new JTextField("LAB2_MaHoaHienDai");
    private final JTextField user = new JTextField("lab2_user");
    private final JPasswordField password = new JPasswordField();
    private final JLabel status = new JLabel("Chưa kết nối.");

    public frm_KetNoiSQL() {
        this(() -> CipherForm.launch(frm_LichSu::new));
    }

    public frm_KetNoiSQL(Runnable openHistory) {
        super(new BorderLayout());
        setName("Kết nối Microsoft SQL Server");

        JPanel fields = new JPanel(new GridLayout(0, 2, 8, 8));
        fields.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        fields.add(new JLabel("Server"));
        fields.add(server);
        fields.add(new JLabel("Port"));
        fields.add(port);
        fields.add(new JLabel("Database"));
        fields.add(database);
        fields.add(new JLabel("User"));
        fields.add(user);
        fields.add(new JLabel("Password"));
        fields.add(password);
        fields.add(CipherForm.button("Test Connection", this::testConnection));
        fields.add(CipherForm.button("Xem lịch sử", openHistory));
        fields.add(new JLabel("Trạng thái"));
        fields.add(status);
        add(fields, BorderLayout.NORTH);
    }

    private void testConnection() {
        try {
            DbConfig.configure(server.getText().trim(), Integer.parseInt(port.getText().trim()),
                    database.getText().trim(), user.getText().trim(), password.getPassword());
            status.setText("Kết nối thành công.");
        } catch (Exception ex) {
            status.setText("Kết nối thử thất bại.");
            CipherForm.showError(this, ex);
        }
    }

    public static void main(String[] args) {
        CipherForm.launch(frm_KetNoiSQL::new);
    }
}
