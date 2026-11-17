package week_02;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.security.GeneralSecurityException;
import java.util.Locale;
import java.util.function.Supplier;
import javax.crypto.AEADBadTagException;
import javax.swing.*;

abstract class CipherForm extends JPanel {
    @FunctionalInterface
    interface Transform {
        String apply(String input, String key, String parameter) throws GeneralSecurityException;
    }

    private record SavedCipher(String text, String parameter) {}

    protected final JPanel content = new JPanel();
    protected final JLabel status = new JLabel("Sẵn sàng.");

    private final JTextArea plaintext = textArea();
    private final JTextArea ciphertext = textArea();
    private final JTextField key = new JTextField(30);
    private final JTextField parameter = new JTextField(32);
    private final String algorithm;
    private final String filePrefix;
    private final Transform encrypt;
    private final Transform decrypt;
    private SavedCipher savedCipher;

    protected CipherForm(String algorithm, String initialKey, String keyLabel,
                         String filePrefix, String parameterLabel,
                         String parameterButton, Supplier<String> generateKey,
                         Supplier<String> generateParameter,
                         Transform encrypt, Transform decrypt) {
        super(new BorderLayout());
        this.algorithm = algorithm;
        this.filePrefix = filePrefix;
        this.encrypt = encrypt;
        this.decrypt = decrypt;

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(content, BorderLayout.CENTER);

        key.setText(initialKey);
        content.add(row(new JLabel("Plaintext")));
        content.add(new JScrollPane(plaintext));

        JPanel keyRow = row(new JLabel(keyLabel), key);
        if (generateKey != null) {
            keyRow.add(button("Generate Key", () -> key.setText(generateKey.get())));
        }
        content.add(keyRow);

        if (generateParameter != null) {
            content.add(row(new JLabel(parameterLabel), parameter,
                    button(parameterButton, () -> parameter.setText(generateParameter.get()))));
        }

        content.add(row(new JLabel("Ciphertext")));
        content.add(new JScrollPane(ciphertext));
        content.add(row(button("Encrypt", () -> process(true)),
                button("Decrypt", () -> process(false)),
                button("Save File", this::saveFile),
                button("Open File", this::openFile)));
        content.add(row(status));
    }

    protected final void finishLayout() {
        setName(algorithm);
    }

    protected String userForOperation() {
        return null;
    }

    private void process(boolean encrypting) {
        try {
            String user = userForOperation();
            String input = (encrypting ? plaintext : ciphertext).getText();
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Hãy nhập dữ liệu vào ô cần xử lý.");
            }

            String parameterValue = parameter.getText().trim();
            Transform operation = encrypting ? encrypt : decrypt;
            String output = operation.apply(input, key.getText(), parameterValue);
            if (encrypting) {
                ciphertext.setText(output);
                savedCipher = new SavedCipher(output, parameterValue);
            } else {
                plaintext.setText(output);
            }

            String action = encrypting ? "ENCRYPT" : "DECRYPT";
            status.setText(action + " thành công. " + HistoryDao.tryInsert(
                    algorithm, action, user, input.length(), output.length()));
        } catch (Exception ex) {
            showError(this, ex);
        }
    }

    private void saveFile() {
        try {
            String data = ciphertext.getText().trim();
            if (data.isEmpty()) {
                throw new IllegalArgumentException("Chưa có bản mã để lưu.");
            }

            String output = data;
            if (!filePrefix.isEmpty()) {
                String value = savedCipher != null && savedCipher.text().equals(data)
                        ? savedCipher.parameter() : parameter.getText().trim();
                if (value.isEmpty()) {
                    throw new IllegalArgumentException("Hãy nhập IV hoặc nonce tương ứng.");
                }
                output = filePrefix + value + "\n" + data;
            }

            String name = algorithm.toLowerCase(Locale.ROOT) + "_ciphertext.txt";
            if (FileTextIO.saveString(this, output + "\n", name)) {
                status.setText("Đã lưu tệp.");
            }
        } catch (Exception ex) {
            showError(this, ex);
        }
    }

    private void openFile() {
        try {
            String input = FileTextIO.openString(this);
            if (input == null) {
                return;
            }

            String data = input.trim();
            if (!filePrefix.isEmpty()) {
                String[] lines = data.split("\\R", 2);
                if (lines.length != 2 || !lines[0].startsWith(filePrefix)) {
                    throw new IllegalArgumentException("Dòng đầu phải bắt đầu bằng " + filePrefix);
                }
                String value = lines[0].substring(filePrefix.length()).trim();
                data = lines[1].trim();
                parameter.setText(value);
                savedCipher = new SavedCipher(data, value);
            }
            ciphertext.setText(data);
            status.setText("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.");
        } catch (Exception ex) {
            showError(this, ex);
        }
    }

    private static JTextArea textArea() {
        JTextArea area = new JTextArea(5, 50);
        area.setLineWrap(true);
        return area;
    }

    static JPanel row(Component... controls) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (Component control : controls) {
            row.add(control);
        }
        return row;
    }

    static JButton button(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(event -> action.run());
        return button;
    }

    static void showError(Component parent, Exception exception) {
        String message = exception instanceof AEADBadTagException
                ? "AES-GCM không xác thực được bản mã. Kiểm tra Key và Ciphertext."
                : exception.getMessage();
        JOptionPane.showMessageDialog(parent, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    static void launch(Supplier<? extends JPanel> createPanel) {
        SwingUtilities.invokeLater(() -> {
            JPanel panel = createPanel.get();
            JFrame window = new JFrame(panel.getName());
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            window.setContentPane(panel);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }
}
