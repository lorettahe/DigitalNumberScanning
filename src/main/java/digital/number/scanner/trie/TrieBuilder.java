package digital.number.scanner.trie;

import java.util.List;

/**
 * Builds a trie given a sequence of lines representing all possible characters
 */
public final class TrieBuilder {
    
    public static TrieRoot buildTrie(List<String> lines) {
        TrieRoot root = new TrieRoot();
        char currentChar = ' ';
        TrieNode currentNode = root;
        for (String line: lines) {
            int lineLength = line.length();
            if (lineLength == 1) {
                if (currentNode instanceof TrieContentNode) {
                    TrieContentNode contentNode = (TrieContentNode) currentNode;
                    contentNode.setChar(currentChar);
                    currentNode = root;
                }
                currentChar = line.charAt(0);
            } else {
                currentNode = currentNode.insertChild(line);
            }
        }
        if (currentNode instanceof TrieContentNode) {
            TrieContentNode contentNode = (TrieContentNode) currentNode;
            contentNode.setChar(currentChar);
        }
        return root;
    }
}
