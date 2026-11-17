package week_02;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class frm_AES extends CipherForm {
    private final JTextField username = new JTextField("sinhvien01", 14);
    private final JPasswordField password = new JPasswordField(14);
    private final JTextField ticket = new JTextField(32);
    private String currentUser;

    public frm_AES() {
        this(() -> CipherForm.launch(frm_KetNoiSQL::new));
    }

    public frm_AES(Runnable openSql) {
        super("AES", "", "Key AES", "", "", "", null, null,
                (text, key, parameter) -> AESCipher.encrypt(text, key),
                (text, key, parameter) -> AESCipher.decrypt(text, key));

        JPanel account = new JPanel();
        account.setLayout(new BoxLayout(account, BoxLayout.Y_AXIS));
        account.add(row(new JLabel("Username"), username, new JLabel("Password"), password));
        account.add(row(button("Kết nối SQL Server", openSql),
                button("Register", this::register), button("Login", this::login)));
        ticket.setEditable(false);
        account.add(row(new JLabel("Ticket"), ticket));
        content.add(account, 0);
        finishLayout();
    }

    private void register() {
        try {
            String user = username.getText().trim();
            UserDao.register(user, password.getPassword());
            status.setText("Register thành công. "
                    + HistoryDao.tryInsert("AES", "REGISTER", user, 0, 0));
        } catch (Exception ex) {
            showError(this, ex);
        }
    }

    private void login() {
        currentUser = null;
        ticket.setText("");
        try {
            String user = username.getText().trim();
            if (!UserDao.login(user, password.getPassword())) {
                throw new IllegalArgumentException("Sai username hoặc mật khẩu.");
            }
            currentUser = user;
            ticket.setText(UserDao.newTicket());
            status.setText("Login thành công. "
                    + HistoryDao.tryInsert("AES", "LOGIN", user, 0, 0));
        } catch (Exception ex) {
            showError(this, ex);
        }
    }

    @Override
    protected String userForOperation() {
        if (currentUser == null) {
            throw new IllegalArgumentException("Hãy Login trước khi dùng AES.");
        }
        return currentUser;
    }

    public static void main(String[] args) {
        launch(frm_AES::new);
    }
}
