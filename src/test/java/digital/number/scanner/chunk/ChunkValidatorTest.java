package digital.number.scanner.chunk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ChunkValidatorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    
    @Test
    public void testNormalChunk() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_| _|"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 3);
        assertTrue(validator.validateChunk(new Chunk(0, chunkInput)));
    }
    
    @Test
    public void testChunkWithIllegalCharStillValidates() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_|| |"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 3);
        assertTrue(validator.validateChunk(new Chunk(0, chunkInput)));
    }
    
    @Test
    public void testChunkValidatorWithDifferentNoOfLines() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 2);
        assertTrue(validator.validateChunk(new Chunk(0, chunkInput)));
    }
    
    @Test
    public void testChunkValidatorWithDifferentLineLength() {
        String[] chunkLines = {"    _  _     _  _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_||_|", "  ||_  _|  | _||_|  ||_|| || |"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(30, 3);
        assertTrue(validator.validateChunk(new Chunk(0, chunkInput)));
    }
    
    @Test
    public void testIncompleteChunk() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 3);
        assertFalse(validator.validateChunk(new Chunk(6, chunkInput)));
        assertEquals("Error on chunk 6: this illegal chunk has 2 lines instead of 3 lines!", outContent.toString().trim());
    }
    
    @Test
    public void testOverlyLongChunk() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_|| |", "more lines"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 3);
        assertFalse(validator.validateChunk(new Chunk(7, chunkInput)));
        assertEquals("Error on chunk 7: this illegal chunk has 4 lines instead of 3 lines!", outContent.toString().trim());
    }
    
    @Test
    public void testOverlyShortLines() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_", "  ||_  _|  | _||_|  ||_|| |"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 3);
        assertFalse(validator.validateChunk(new Chunk(8, chunkInput)));
        assertEquals("Error on chunk 8 line 1: this illegal line has 26 characters instead of 27 characters!", outContent.toString().trim());
    }
    
    @Test
    public void testOverlyLongLines() {
        String[] chunkLines = {"    _  _     _  _  _  _  _  ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_|| |"};
        String chunkInput = String.join("\n", chunkLines);
        ChunkValidator validator = new ChunkValidator(27, 3);
        assertFalse(validator.validateChunk(new Chunk(9, chunkInput)));
        assertEquals("Error on chunk 9 line 0: this illegal line has 28 characters instead of 27 characters!", outContent.toString().trim());
    }
}
