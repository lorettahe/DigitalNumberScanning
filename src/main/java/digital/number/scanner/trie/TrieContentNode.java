package digital.number.scanner.trie;

import java.util.HashMap;

public class TrieContentNode extends TrieNode {
    private Character c;
    
    public TrieContentNode(String content) {
        this.content = content;
        this.children = new HashMap<>();
        this.c = null;
    }
    
    public void setChar(char c) {
        this.c = c;
    }
    
    public Character getChar() {
        return c;
    }
}
