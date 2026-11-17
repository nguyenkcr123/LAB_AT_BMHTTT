package week_02;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class FileTextIO {
    private FileTextIO() {}

    public static String openString(Component parent) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Tệp văn bản", "txt"));
        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return Files.readString(chooser.getSelectedFile().toPath());
    }

    public static boolean saveString(Component parent, String content, String filename)
            throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(filename));
        if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return false;
        }
        Files.writeString(chooser.getSelectedFile().toPath(), content);
        return true;
    }
}