package digital.number.scanner.trie;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrieNodeTest {
    
    @Test
    public void testInsertChild() {
        TrieRoot root = new TrieRoot();
        TrieNode child = root.insertChild("   ");
        TrieNode grandChild = child.insertChild("  |");
        TrieNode grandChild2 = child.insertChild("|_|");
        assertEquals(1, root.children.size());
        assertEquals(2, child.children.size());
        assertEquals(child, root.children.get("   "));
        assertEquals(grandChild, child.children.get("  |"));
        assertEquals(grandChild2, child.children.get("|_|"));
    }
    
    @Test
    public void testInsertChildWithExistingContentShouldNotReplaceNode() {
        TrieRoot root = new TrieRoot();
        TrieNode child = root.insertChild("   ");
        TrieNode child2 = root.insertChild("   ");
        assertEquals(1, root.children.size());
        assertEquals(child, root.children.get("   "));
        assertSame(child, child2);
    }
}
