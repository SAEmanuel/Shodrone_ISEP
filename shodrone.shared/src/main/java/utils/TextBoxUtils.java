package utils;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

import static more.ColorfulOutput.*;

public class TextBoxUtils {

    private static final String INSTRUCTIONS =
            "IMPORTANT: Each required field must be specified as ${fieldName} inside the template.\n\n" +
                    "Required fields (possible names):\n" +
                    "- customer, customerName, client, clientName, cliente, nomeCliente\n" +
                    "- showDate, dataShow, data\n" +
                    "- showLocation, local, location\n" +
                    "- duration, showDuration, duracao, duracaoShow\n" +
                    "- figures, figuras\n" +
                    "- drones\n" +
                    "- video, linkVideo, videoLink\n" +
                    "- manager, crmManager, crm_manager, gestor_responsavel, gestorResponsavel\n\n";


    public static List<String> getLinesFromTextBox(String titulo) {
        List<String> line = new ArrayList<>();
        final Object lock = new Object();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(titulo);
            JTextArea textArea = new JTextArea(30, 60);
            JButton finishButton = new JButton("Submit");
            JButton instructionsButton = new JButton("Instructions");

            JOptionPane.showMessageDialog(
                    frame,
                    INSTRUCTIONS,
                    "Template Instructions",
                    JOptionPane.WARNING_MESSAGE
            );

            instructionsButton.addActionListener(e ->
                    JOptionPane.showMessageDialog(
                            frame,
                            INSTRUCTIONS,
                            "Template Instructions",
                            JOptionPane.WARNING_MESSAGE
                    )
            );

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
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(instructionsButton);
            buttonPanel.add(finishButton);
            panel.add(buttonPanel);

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