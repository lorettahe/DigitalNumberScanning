package digital.number.scanner.trie;

import digital.number.scanner.trie.TrieNode;

import java.util.HashMap;
import java.util.Map;

public class TrieRoot extends TrieNode {

    public TrieRoot() {
        this.children = new HashMap<>();
        this.content = "";
    }
}
