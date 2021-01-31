package digital.number.scanner.trie;

import java.util.Map;

public class TrieNode {
    protected Map<String, TrieNode> children;
    protected String content;
    
    public TrieNode insertChild(String content) {
        if (children.containsKey(content)) {
            return children.get(content);
        }
        TrieContentNode newNode = new TrieContentNode(content);
        children.put(content, newNode);
        return newNode;
    }
    
    public Map<String, TrieNode> getChildren() {
        return children;
    }
    
    public String getContent() {
        return content;
    }
}

