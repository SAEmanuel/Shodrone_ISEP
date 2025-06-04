package utils;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class TextBoxUtils {

    public static List<String> getLinesFromTextBox(String titulo) {
        List<String> line = new ArrayList<>();

        final Object lock = new Object();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(titulo);
            JTextArea textArea = new JTextArea(30, 60);
            JButton finishButton = new JButton("Submit");

            finishButton.addActionListener(e -> {
                String text = textArea.getText();
                String[] array = text.split("\\r?\\n");
                for (String l : array) {
                    line.add(l);
                }
                frame.dispose();
                synchronized (lock) {
                    lock.notify();
                }
            });

            JPanel panel = new JPanel();
            panel.add(new JScrollPane(textArea));
            panel.add(finishButton);

            frame.add(panel);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return line;
    }
}