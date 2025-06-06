package utils;

import network.ShowProposalDTO;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;

public class ProposalAnalysisDialog extends JDialog {

    private final ShowProposalDTO proposal;
    private String action = "exit";

    public ProposalAnalysisDialog(Frame parent, ShowProposalDTO proposal) {
        super(parent, "Proposal Analysis - ID: " + proposal.getId(), true);
        this.proposal = proposal;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(String.join("\n", proposal.getText()));
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton acceptButton = new JButton("Accept");
        JButton rejectButton = new JButton("Reject");
        JButton downloadButton = new JButton("Download");
        JButton exitButton = new JButton("Exit");

        acceptButton.addActionListener(e -> {
            action = "accept";
            dispose();
        });

        rejectButton.addActionListener(e -> {
            action = "reject";
            dispose();
        });

        downloadButton.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("proposal_" + proposal.getId() + ".txt"));
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                    writer.write(String.join("\n", proposal.getText()));
                    JOptionPane.showMessageDialog(this, "Proposal downloaded successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
                }
            }
        });

        exitButton.addActionListener(e -> {
            action = "exit";
            dispose();
        });

        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(downloadButton);
        buttonPanel.add(exitButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(700, 600);
        setLocationRelativeTo(null);
    }

    public String showDialog() {
        setVisible(true);
        return action;
    }
}
