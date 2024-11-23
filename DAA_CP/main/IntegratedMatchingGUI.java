import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class IntegratedMatchingGUI {

    private JFrame frame;
    private JPanel mainMenuPanel, dnaMatchingPanel, stringMatchingPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IntegratedMatchingGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("DNA and String Matching Algorithms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new CardLayout());

        // Create Main Menu
        createMainMenuPanel();

        // Create DNA Matching Panel
        createDNAMatchingPanel();

        // Create String Matching Panel
        createStringMatchingPanel();

        // Add the panels to the frame
        frame.add(mainMenuPanel, "MainMenu");
        frame.add(dnaMatchingPanel, "DNAMatching");
        frame.add(stringMatchingPanel, "StringMatching");

        // Show the main menu
        switchToPanel("MainMenu");

        frame.setVisible(true);
    }

    private void createMainMenuPanel() {
        mainMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("Welcome to DNA and String Matching Algorithms", JLabel.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        mainMenuPanel.add(welcomeLabel, gbc);

        JButton dnaMatchingButton = new JButton("DNA Matching");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        mainMenuPanel.add(dnaMatchingButton, gbc);

        JButton stringMatchingButton = new JButton("String Matching Algorithms");
        gbc.gridx = 1; gbc.gridy = 1;
        mainMenuPanel.add(stringMatchingButton, gbc);

        // Action Listeners to navigate
        dnaMatchingButton.addActionListener(e -> switchToPanel("DNAMatching"));
        stringMatchingButton.addActionListener(e -> switchToPanel("StringMatching"));
    }

    private void createDNAMatchingPanel() {
        dnaMatchingPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextArea textInput = new JTextArea(3, 40);
        JScrollPane textScroll = new JScrollPane(textInput);
        JTextArea patternInput = new JTextArea(3, 40);
        JScrollPane patternScroll = new JScrollPane(patternInput);
        JTextArea resultOutput = new JTextArea(5, 40);
        resultOutput.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultOutput);

        JButton runButton = new JButton("Run DNA Matching");

        // Layout for DNA Matching input
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

        dnaMatchingPanel.add(inputPanel, BorderLayout.CENTER);
        dnaMatchingPanel.add(resultScroll, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back to Main Menu");
        dnaMatchingPanel.add(backButton, BorderLayout.NORTH);

        // Back button action
        backButton.addActionListener(e -> switchToPanel("MainMenu"));

        // Action for DNA Matching
        runButton.addActionListener(e -> {
            String sequence1 = textInput.getText().trim();
            String sequence2 = patternInput.getText().trim();

            if (sequence1.isEmpty() || sequence2.isEmpty()) {
                resultOutput.setText("Please enter both DNA sequences.");
            } else if (sequence1.length() != sequence2.length()) {
                resultOutput.setText("Both DNA sequences must be of the same length.");
            } else {
                String result = dnaMatch(sequence1, sequence2);
                resultOutput.setText(result);
            }
        });
    }

    private void createStringMatchingPanel() {
        stringMatchingPanel = new JPanel(new FlowLayout());

        JTextArea textInput = new JTextArea(5, 50);
        JScrollPane textScroll = new JScrollPane(textInput);

        JTextArea patternInput = new JTextArea(2, 50);
        JScrollPane patternScroll = new JScrollPane(patternInput);

        JTextArea outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);

        JLabel algorithmLabel = new JLabel("Choose Algorithm:");
        String[] algorithms = { "Aho-Corasick", "Boyer-Moore", "KMP", "Z-Algorithm" };
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);

        JButton runButton = new JButton("Run String Matching");

        // Add action listener to run button
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textInput.getText().trim();
                String pattern = patternInput.getText().trim();
                String algorithm = (String) algorithmDropdown.getSelectedItem();

                if (algorithm != null) {
                    switch (algorithm) {
                        case "Aho-Corasick":
                            List<String> patterns = Arrays.asList(pattern.split(","));
                            outputArea.setText(ahoCorasickSearch(text, patterns));
                            break;
                        case "Boyer-Moore":
                            outputArea.setText(boyerMooreSearch(text, pattern));
                            break;
                        case "KMP":
                            outputArea.setText(kmpSearch(text, pattern));
                            break;
                        case "Z-Algorithm":
                            outputArea.setText(zAlgorithm(text, pattern));
                            break;
                        default:
                            outputArea.setText("Please select an algorithm.");
                    }
                }
            }
        });

        JButton backButton = new JButton("Back to Main Menu");

        // Layout
        stringMatchingPanel.add(new JLabel("Text:"));
        stringMatchingPanel.add(textScroll);
        stringMatchingPanel.add(new JLabel("Pattern(s):"));
        stringMatchingPanel.add(patternScroll);
        stringMatchingPanel.add(algorithmLabel);
        stringMatchingPanel.add(algorithmDropdown);
        stringMatchingPanel.add(runButton);
        stringMatchingPanel.add(outputScroll);
        stringMatchingPanel.add(backButton);

        // Back button action
        backButton.addActionListener(e -> switchToPanel("MainMenu"));
    }

    private void switchToPanel(String panelName) {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), panelName);
    }

    // DNA Matching Logic
    private String dnaMatch(String seq1, String seq2) {
        int matches = 0;
        for (int i = 0; i < seq1.length(); i++) {
            if (seq1.charAt(i) == seq2.charAt(i)) {
                matches++;
            }
        }
        double matchPercentage = ((double) matches / seq1.length()) * 100;
        return String.format("Match Percentage: %.2f%%", matchPercentage);
    }

    // Aho-Corasick Algorithm Implementation
    private String ahoCorasickSearch(String text, List<String> patterns) {
        AhoCorasickTrie trie = new AhoCorasickTrie();
        for (String pattern : patterns) {
            trie.addPattern(pattern);
        }
        trie.buildFailureLinks();

        List<String> results = trie.search(text);
        return String.join("\n", results);
    }

    // Boyer-Moore Algorithm
    private String boyerMooreSearch(String text, String pattern) {
        List<Integer> resultIndices = boyerMoore(text, pattern);
        List<String> results = new ArrayList<>();
        for (int index : resultIndices) {
            results.add("Pattern found at index: " + index);
        }
        return String.join("\n", results);
    }

    // Knuth-Morris-Pratt (KMP) Algorithm
    private String kmpSearch(String text, String pattern) {
        List<Integer> resultIndices = kmp(text, pattern);
        List<String> results = new ArrayList<>();
        for (int index : resultIndices) {
            results.add("Pattern found at index: " + index);
        }
        return String.join("\n", results);
    }

    // Z Algorithm
    private String zAlgorithm(String text, String pattern) {
        List<Integer> resultIndices = zAlgorithmMatch(text, pattern);
        List<String> results = new ArrayList<>();
        for (int index : resultIndices) {
            results.add("Pattern found at index: " + index);
        }
        return String.join("\n", results);
    }

    // Helper Functions for Boyer-Moore
    private List<Integer> boyerMoore(String text, String pattern) {
        int[] badChar = preprocessBadChar(pattern);
        List<Integer> result = new ArrayList<>();
        int n = text.length();
        int m = pattern.length();
        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }
            if (j < 0) {
                result.add(shift);
                shift += (shift + m < n) ? m - badChar[text.charAt(shift + m)] : 1;
            } else {
                shift += Math.max(1, j - badChar[text.charAt(shift + j)]);
            }
        }
        return result;
    }

    private int[] preprocessBadChar(String pattern) {
        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);
        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }
        return badChar;
    }

    // Helper Functions for KMP
    private List<Integer> kmp(String text, String pattern) {
        int[] lps = computeLPS(pattern);
        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            if (j == pattern.length()) {
                result.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    private int[] computeLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;
        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // Helper Functions for Z Algorithm
    private List<Integer> zAlgorithmMatch(String text, String pattern) {
        String concat = pattern + "$" + text;
        int l = concat.length();
        int[] Z = new int[l];
        List<Integer> result = new ArrayList<>();
        int left = 0, right = 0, k;

        for (int i = 1; i < l; i++) {
            if (i > right) {
                left = right = i;
                while (right < l && concat.charAt(right) == concat.charAt(right - left)) {
                    right++;
                }
                Z[i] = right - left;
                right--;
            } else {
                k = i - left;
                if (Z[k] < right - i + 1) {
                    Z[i] = Z[k];
                } else {
                    left = i;
                    while (right < l && concat.charAt(right) == concat.charAt(right - left)) {
                        right++;
                    }
                    Z[i] = right - left;
                    right--;
                }
            }
            if (Z[i] == pattern.length()) {
                result.add(i - pattern.length() - 1);
            }
        }
        return result;
    }
}

// Simple Aho-Corasick Trie Implementation for Multiple Pattern Matching
class AhoCorasickTrie {
    private final TrieNode root = new TrieNode();

    public void addPattern(String pattern) {
        TrieNode node = root;
        for (char c : pattern.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.pattern = pattern;
    }

    public void buildFailureLinks() {
        Queue<TrieNode> queue = new LinkedList<>();
        for (TrieNode child : root.children.values()) {
            child.failureLink = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for (Map.Entry<Character, TrieNode> entry : current.children.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();
                TrieNode failureLink = current.failureLink;
                while (failureLink != null && !failureLink.children.containsKey(c)) {
                    failureLink = failureLink.failureLink;
                }
                if (failureLink == null) {
                    child.failureLink = root;
                } else {
                    child.failureLink = failureLink.children.get(c);
                }
                queue.add(child);
            }
        }
    }

    public List<String> search(String text) {
        TrieNode node = root;
        List<String> results = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            while (node != null && !node.children.containsKey(c)) {
                node = node.failureLink;
            }
            if (node == null) {
                node = root;
                continue;
            }
            node = node.children.get(c);
            TrieNode temp = node;
            while (temp != root) {
                if (temp.pattern != null) {
                    results.add("Pattern found at index " + (i - temp.pattern.length() + 1) + ": " + temp.pattern);
                }
                temp = temp.failureLink;
            }
        }
        return results;
    }

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        TrieNode failureLink = null;
        String pattern = null;
    }
}

