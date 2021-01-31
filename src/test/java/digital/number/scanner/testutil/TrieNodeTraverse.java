package digital.number.scanner.testutil;

import digital.number.scanner.trie.TrieNode;

import java.util.List;

public class TrieNodeTraverse {
    public final TrieNode node;
    public final List<String> currentLines;
    
    public TrieNodeTraverse(TrieNode node, List<String> currentLines) {
        this.node = node;
        this.currentLines = currentLines;
    }
}