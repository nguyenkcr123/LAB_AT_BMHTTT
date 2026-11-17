package week_02;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class frm_Menu extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel pages = new JPanel(layout);
    private final frm_LichSu history = new frm_LichSu();

    public frm_Menu() {
        super("LAB 2 - Mã hóa đối xứng hiện đại");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 6));
        menu.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        addPage(menu, "DES", "2.1 DES", new frm_DES());
        addPage(menu, "3DES", "2.2 TripleDES", new frm_TripleDES());
        addPage(menu, "AES", "2.3 AES + Login", new frm_AES(() -> showPage("SQL")));
        addPage(menu, "RC4", "2.4 RC4", new frm_RC4());
        addPage(menu, "TWOFISH", "2.5 Twofish", new frm_Twofish());
        addPage(menu, "CTR", "2.6 AES-CTR", new frm_AES_CTR());
        addPage(menu, "SQL", "Kết nối SQL Server", new frm_KetNoiSQL(this::showHistory));

        pages.add(history, "HISTORY");
        menu.add(CipherForm.button("Xem lịch sử", this::showHistory));

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(menu, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(pages, BorderLayout.CENTER);
        pack();
        setSize(Math.max(getWidth(), 960), Math.max(getHeight(), 640));
        setLocationRelativeTo(null);
        showPage("DES");
    }

    private void addPage(JPanel menu, String id, String label, JPanel page) {
        pages.add(page, id);
        menu.add(CipherForm.button(label, () -> showPage(id)));
    }

    private void showPage(String id) {
        layout.show(pages, id);
    }

    private void showHistory() {
        showPage("HISTORY");
        history.loadData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}
