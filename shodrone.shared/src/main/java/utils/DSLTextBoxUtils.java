package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class DSLTextBoxUtils {

    private static final String INSTRUCTIONS =
            """
            DSL Syntax Guide for Figure:

            • Start with the version: DSL version X.Y.Z;
            • Declare the drone type: DroneType YourDroneName;

            • You can define:
              - Position name = (x, y, z);
              - Velocity name = expression;
              - Distance name = expression;

            • Define elements with parameters:
              - ElementType elementName(position, value1, value2, DroneType);

            • Use timed blocks for actions:
              before
                  element.method(...);
              endbefore

              after
                  ...
              endafter

            • Supported methods: move, rotate, lightsOn(R,G,B), lightsOff, pause(time);

            • Use 'group ... endgroup' to group statements for parallel execution.

            Press 'Submit' to validate the DSL or 'Cancel' to abort.
            """;

    public static List<String> getDslLinesFromTextBox(String title) {
        List<String> lines = new ArrayList<>();

        JTextArea textArea = new JTextArea(30, 80);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton helpButton = new JButton("Instructions");
        helpButton.addActionListener((ActionEvent e) -> {
            JTextArea helpArea = new JTextArea(INSTRUCTIONS);
            helpArea.setEditable(false);
            helpArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            helpArea.setLineWrap(true);
            helpArea.setWrapStyleWord(true);

            JScrollPane helpScroll = new JScrollPane(helpArea);
            helpScroll.setPreferredSize(new Dimension(600, 300));

            JOptionPane.showMessageDialog(null, helpScroll, "DSL Help", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(helpButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
                null,
                mainPanel,
                title,
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