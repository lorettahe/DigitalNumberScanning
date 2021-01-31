package digital.number.scanner.symbol;

import digital.number.scanner.trie.TrieContentNode;
import digital.number.scanner.trie.TrieNode;
import digital.number.scanner.trie.TrieRoot;

import java.util.Map;
import java.util.Objects;

/**
 * Reads a 2-D char array and returns a symbol this 2-D char array represents according to the trie built out of all possible chars
 */
public class SymbolReader {
    private final TrieRoot trie;
    
    public SymbolReader(TrieRoot trie) {
        this.trie = trie;
    }
    
    public char readSymbol(char[][] input) {
        TrieNode currentNode = trie;
        for (char[] chars : input) {
            String currentRow = String.valueOf(chars);
            Map<String, TrieNode> children = currentNode.getChildren();
            if (children.containsKey(currentRow)) {
                currentNode = children.get(currentRow);
            } else {
                return '?';
            }
        }
        if (currentNode instanceof TrieContentNode) {
            TrieContentNode contentNode = (TrieContentNode) currentNode;
            Character c = contentNode.getChar();
            return Objects.requireNonNullElse(c, '?');
        } else {
            return '?';
        }
    }
}
