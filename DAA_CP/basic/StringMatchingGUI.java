import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;  // Use this for java.util.List
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.swing.*;

public class StringMatchingGUI {

    public static void main(String[] args) {
        new StringMatchingGUI().createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("String Matching Algorithms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Text Input
        JTextArea textInput = new JTextArea(5, 50);
        JScrollPane textScroll = new JScrollPane(textInput);

        // Pattern Input
        JTextArea patternInput = new JTextArea(2, 50);
        JScrollPane patternScroll = new JScrollPane(patternInput);

        // Output Display
        JTextArea outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);

        // Algorithm Selection
        JLabel algorithmLabel = new JLabel("Choose Algorithm:");
        String[] algorithms = { "Aho-Corasick", "Boyer-Moore", "KMP", "Z-Algorithm" };
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);

        // Button to run the algorithm
        JButton runButton = new JButton("Run");

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

        // Add components to frame
        frame.setLayout(new FlowLayout());
        frame.add(new JLabel("Text:"));
        frame.add(textScroll);
        frame.add(new JLabel("Pattern(s):"));
        frame.add(patternScroll);
        frame.add(algorithmLabel);
        frame.add(algorithmDropdown);
        frame.add(runButton);
        frame.add(outputScroll);

        // Display the window
        frame.setVisible(true);
    }

    // Aho-Corasick Algorithm Implementation
    private String ahoCorasickSearch(String text, List<String> patterns) {
        AhoCorasickTrie1 trie = new AhoCorasickTrie1();
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
class AhoCorasickTrie1 {
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


