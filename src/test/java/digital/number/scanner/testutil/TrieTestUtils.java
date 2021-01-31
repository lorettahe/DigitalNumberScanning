package digital.number.scanner.testutil;

import digital.number.scanner.trie.TrieContentNode;
import digital.number.scanner.trie.TrieNode;
import digital.number.scanner.trie.TrieRoot;

import java.util.*;

public class TrieTestUtils {

    public static Map<Character, List<String>> buildExpectedOutput(List<String> trieInputLines) {
        Map<Character, List<String>> result = new HashMap<>();
        Character currentChar = ' ';
        for (String line: trieInputLines) {
            if (line.length() == 1) {
                currentChar = line.charAt(0);
                result.put(currentChar, new ArrayList<>());
            } else {
                result.get(currentChar).add(line);
            }
        }
        return result;
    }

    public static Map<Character, List<String>> traverseTrie(TrieRoot trie) {
        Map<Character, List<String>> result = new HashMap<>();
        Deque<TrieNodeTraverse> stack = new ArrayDeque<>();
        for (Map.Entry<String, TrieNode> childEntry : trie.getChildren().entrySet()) {
            stack.push(new TrieNodeTraverse(childEntry.getValue(), new ArrayList<>()));
        }
        while (!stack.isEmpty()) {
            TrieNodeTraverse current = stack.pop();
            if (current.node instanceof TrieContentNode) {
                TrieContentNode contentNode = (TrieContentNode) current.node;
                if (contentNode.getChar() != null) {
                    List<String> finalLines = new ArrayList<>(current.currentLines);
                    finalLines.add(contentNode.getContent());
                    result.put(contentNode.getChar(), finalLines);
                } else {
                    for (Map.Entry<String, TrieNode> childEntry : current.node.getChildren().entrySet()) {
                        List<String> newLines = new ArrayList<>(current.currentLines);
                        newLines.add(((TrieContentNode) current.node).getContent());
                        stack.push(new TrieNodeTraverse(childEntry.getValue(), newLines));
                    }
                }
            }
        }
        return result;
    }
}
