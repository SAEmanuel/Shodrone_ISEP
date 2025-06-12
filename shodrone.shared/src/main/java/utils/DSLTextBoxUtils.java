package utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DSLTextBoxUtils {

    private static final String INSTRUCTIONS =
            "Enter the DSL content for this version.\n\n" +
                    "You can paste or escrever múltiplas linhas.\n" +
                    "Press 'Submit' to confirm or feche/cancele para abortar.\n";


    public static List<String> getDslLinesFromTextBox(String titulo) {
        List<String> lines = new ArrayList<>();

        JTextArea textArea = new JTextArea(20, 60);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel panel = new JPanel();
        panel.add(scrollPane);

        JOptionPane.showMessageDialog(
                null,
                INSTRUCTIONS,
                "DSL Input Instructions",
                JOptionPane.INFORMATION_MESSAGE
        );

        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                titulo,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String text = textArea.getText();
            String[] array = text.split("\\r?\\n");
            for (String l : array) {
                lines.add(l);
            }
        }
        return lines;
    }
}