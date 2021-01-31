package digital.number.scanner.symbol;

import digital.number.scanner.trie.TrieBuilder;
import digital.number.scanner.trie.TrieRoot;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

public class SymbolReaderTest {
    private static TrieRoot trie;
    private static SymbolReader reader;
    
    static {
        try {
            String trieInput = "trie/trie_input";
            File trieInputFile = new File(SymbolReaderTest.class.getClassLoader().getResource(trieInput).getFile());
            List<String> trieInputLines = Files.readAllLines(trieInputFile.toPath());
            trie = TrieBuilder.buildTrie(trieInputLines);
            reader = new SymbolReader(trie);
        } catch (IOException e) {
            // Don't do anything
        }
    }
    
    @Test
    public void testZero() {
        char[][] input = {
                {' ', '_', ' '},
                {'|', ' ', '|'},
                {'|', '_', '|'}
        };
        assertEquals('0', reader.readSymbol(input));
    }
    
    @Test
    public void testOne() {
        char[][] input = {
                {' ', ' ', ' '},
                {' ', ' ', '|'},
                {' ', ' ', '|'}
        };
        assertEquals('1', reader.readSymbol(input));
    }
    
    @Test
    public void testTwo() {
        char[][] input = {
                {' ', '_', ' '},
                {' ', '_', '|'},
                {'|', '_', ' '}
        };
        assertEquals('2', reader.readSymbol(input));
    }
    
    @Test
    public void testThree() {
        char[][] input = {
                {' ', '_', ' '},
                {' ', '_', '|'},
                {' ', '_', '|'}
        };
        assertEquals('3', reader.readSymbol(input));
    }

    @Test
    public void testFour() {
        char[][] input = {
                {' ', ' ', ' '},
                {'|', '_', '|'},
                {' ', ' ', '|'}
        };
        assertEquals('4', reader.readSymbol(input));
    }
    
    @Test
    public void testFive() {
        char[][] input = {
                {' ', '_', ' '},
                {'|', '_', ' '},
                {' ', '_', '|'}
        };
        assertEquals('5', reader.readSymbol(input));
    }
    
    @Test
    public void testSix() {
        char[][] input = {
                {' ', '_', ' '},
                {'|', '_', ' '},
                {'|', '_', '|'}
        };
        assertEquals('6', reader.readSymbol(input));
    }
    
    @Test
    public void testSeven() {
        char[][] input = {
                {' ', '_', ' '},
                {' ', ' ', '|'},
                {' ', ' ', '|'}
        };
        assertEquals('7', reader.readSymbol(input));
    }
    
    @Test
    public void testEight() {
        char[][] input = {
                {' ', '_', ' '},
                {'|', '_', '|'},
                {'|', '_', '|'}
        };
        assertEquals('8', reader.readSymbol(input));
    }
    
    @Test
    public void testNine() {
        char[][] input = {
                {' ', '_', ' '},
                {'|', '_', '|'},
                {' ', '_', '|'}
        };
        assertEquals('9', reader.readSymbol(input));
    }
    
    @Test
    public void testIllegalChar() {
        char[][] input = {
                {' ', '_', ' '},
                {'|', '_', '|'},
                {'|', ' ', '|'}
        };
        assertEquals('?', reader.readSymbol(input));
    }
}
