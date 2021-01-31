package digital.number.scanner.chunk;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChunkReaderTest {
    private final ChunkReader chunkReader = new ChunkReader(new MockSymbolReader(), 3);
    
    @Test
    public void testReadNormalChunk() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_| _|"};
        String chunkInput = String.join("\n", chunkLines);
        runTest(0, chunkInput, "123456789");
    }
    
    @Test
    public void testReadNormalChunkWithWindowsLineEndings() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_| _|"};
        String chunkInput = String.join("\r\n", chunkLines);
        runTest(12, chunkInput, "123456789");
    }
    
    @Test
    public void testChunkWithUnknownChar() {
        String[] chunkLines = {"    _  _     _  _  _  _  _ ", "  | _| _||_||_ |_   ||_||_|", "  ||_  _|  | _||_|  ||_|| |"};
        String chunkInput = String.join("\n", chunkLines);
        runTest(0, chunkInput, "12345678?ILL");
    }
    
    private void runTest(long chunkId, String chunkInput, String expectedChunkResult) {
        Chunk chunk = new Chunk(chunkId, chunkInput);
        ChunkResult result = chunkReader.readChunk(chunk);
        assertEquals(expectedChunkResult, result.getResult());
        assertEquals(chunkId, result.getId());
    }
}
