// import javax.swing.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class DNAMatchingGUI {

//     // Create GUI
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new DNAMatchingGUI().createAndShowGUI());
//     }

//     private void createAndShowGUI() {
//         JFrame frame = new JFrame("DNA Matching Using Z Algorithm");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(600, 400);

//         // Input for DNA sequence and pattern
//         JTextArea textInput = new JTextArea(3, 40);
//         JScrollPane textScroll = new JScrollPane(textInput);
//         JTextArea patternInput = new JTextArea(3, 40);
//         JScrollPane patternScroll = new JScrollPane(patternInput);

//         // Result output
//         JTextArea resultOutput = new JTextArea(5, 40);
//         resultOutput.setEditable(false);
//         JScrollPane resultScroll = new JScrollPane(resultOutput);

//         // Button to run Z algorithm
//         JButton runButton = new JButton("Run");
        
//         // Layout setup
//         JPanel panel = new JPanel();
//         panel.add(new JLabel("DNA Sequence 1:"));
//         panel.add(textScroll);
//         panel.add(new JLabel("DNA Sequence 2:"));
//         panel.add(patternScroll);
//         panel.add(runButton);
//         panel.add(resultScroll);

//         frame.add(panel);
//         frame.setVisible(true);

//         // Action on clicking "Run"
//         runButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String sequence1 = textInput.getText().trim();
//                 String sequence2 = patternInput.getText().trim();

//                 if (sequence1.isEmpty() || sequence2.isEmpty()) {
//                     resultOutput.setText("Please enter both DNA sequences.");
//                 } else if (sequence1.length() != sequence2.length()) {
//                     resultOutput.setText("Both DNA sequences must be of the same length.");
//                 } else {
//                     // Run sequence matching
//                     String result = dnaMatch(sequence1, sequence2);
//                     resultOutput.setText(result);
//                 }
//             }
//         });
//     }

//     // Basic character-by-character matching for DNA sequences
//     private String dnaMatch(String seq1, String seq2) {
//         int matches = 0;

//         // Compare character by character
//         for (int i = 0; i < seq1.length(); i++) {
//             if (seq1.charAt(i) == seq2.charAt(i)) {
//                 matches++;
//             }
//         }

//         // Calculate matching percentage
//         double matchPercentage = ((double) matches / seq1.length()) * 100;
//         return String.format("Match Percentage: %.2f%%", matchPercentage);
//     }
// }


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DNAMatchingGUI {

    // Create GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DNAMatchingGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("DNA Matching Using Z Algorithm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500); // Set a fixed window size for better appearance
        frame.setLayout(new BorderLayout());

        // Top panel for DNA images
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Load and add DNA images
        ImageIcon dnaIcon1 = new ImageIcon("dna1.png"); // Ensure the image paths are correct
        ImageIcon dnaIcon2 = new ImageIcon("dna2.png");
        JLabel dnaLabel1 = new JLabel(dnaIcon1);
        JLabel dnaLabel2 = new JLabel(dnaIcon2);

        imagePanel.add(dnaLabel1);
        imagePanel.add(dnaLabel2);

        // Center panel for input fields and button
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input for DNA sequences
        JTextArea textInput = new JTextArea(3, 40);
        JScrollPane textScroll = new JScrollPane(textInput);
        JTextArea patternInput = new JTextArea(3, 40);
        JScrollPane patternScroll = new JScrollPane(patternInput);

        // Result output
        JTextArea resultOutput = new JTextArea(5, 40);
        resultOutput.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultOutput);

        // Button to run Z algorithm
        JButton runButton = new JButton("Run");

        // Layout input fields and button
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("DNA Sequence 1:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(textScroll, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("DNA Sequence 2:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(patternScroll, gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(runButton, gbc);

        // Bottom panel for result output
        JPanel resultPanel = new JPanel();
        resultPanel.add(resultScroll);

        frame.add(imagePanel, BorderLayout.NORTH);
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Action on clicking "Run"
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sequence1 = textInput.getText().trim();
                String sequence2 = patternInput.getText().trim();

                if (sequence1.isEmpty() || sequence2.isEmpty()) {
                    resultOutput.setText("Please enter both DNA sequences.");
                } else if (sequence1.length() != sequence2.length()) {
                    resultOutput.setText("Both DNA sequences must be of the same length.");
                } else {
                    // Run sequence matching
                    String result = dnaMatch(sequence1, sequence2);
                    resultOutput.setText(result);
                }
            }
        });
    }

    // Basic character-by-character matching for DNA sequences
    private String dnaMatch(String seq1, String seq2) {
        int matches = 0;

        // Compare character by character
        for (int i = 0; i < seq1.length(); i++) {
            if (seq1.charAt(i) == seq2.charAt(i)) {
                matches++;
            }
        }

        // Calculate matching percentage
        double matchPercentage = ((double) matches / seq1.length()) * 100;
        return String.format("Match Percentage: %.2f%%", matchPercentage);
    }
}
