package digital.number.scanner.trie;

import digital.number.scanner.testutil.TrieTestUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.*;

public class TrieBuilderTest {
    
    @Test
    public void testBuildTrie() throws IOException {
        String trieInput = "trie/trie_input";
        File trieInputFile = new File(getClass().getClassLoader().getResource(trieInput).getFile());
        List<String> trieInputLines = Files.readAllLines(trieInputFile.toPath());
        TrieRoot trie = TrieBuilder.buildTrie(trieInputLines);
        assertEquals(TrieTestUtils.buildExpectedOutput(trieInputLines), TrieTestUtils.traverseTrie(trie));
    }
}