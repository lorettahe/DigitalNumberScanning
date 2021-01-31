package digital.number.scanner;

import com.google.common.collect.Streams;
import digital.number.scanner.chunk.Chunk;
import digital.number.scanner.chunk.ChunkReader;
import digital.number.scanner.chunk.ChunkResult;
import digital.number.scanner.chunk.ChunkValidator;
import digital.number.scanner.io.FileChunker;
import digital.number.scanner.trie.TrieBuilder;
import digital.number.scanner.trie.TrieRoot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

public class DigitalNumberScanner {
    private static final String trieInputFileName = "digits.txt";
    private static final TrieRoot trie;
    
    private final ChunkValidator chunkValidator;
    private final ChunkReader chunkReader;
    
    static {
        try {
            trie = buildTrieFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected static TrieRoot buildTrieFromFile() throws IOException {
        URL trieInputFileURL = DigitalNumberScanner.class.getClassLoader().getResource(trieInputFileName);
        File trieInputFile;
        if (trieInputFileURL == null || !(trieInputFile=new File(trieInputFileURL.getFile())).exists()) {
            throw new IOException("Trie input file " + trieInputFileName + " does not exist on resource path!");
        }
        List<String> trieInputLines = Files.readAllLines(trieInputFile.toPath());
        return TrieBuilder.buildTrie(trieInputLines);
    }
    
    
    public DigitalNumberScanner(int charWidth, int noOfLinesInChunk, int lineLength) {
        this.chunkValidator = new ChunkValidator(lineLength, noOfLinesInChunk);
        this.chunkReader = new ChunkReader(trie, charWidth);
    }
    
    public Stream<ChunkResult> scan(String inputFilePath) throws IOException {
        Stream<String> stream = FileChunker.fileToChunk(inputFilePath);
        Stream<Chunk> chunks = Streams.mapWithIndex(stream, (s, id) -> new Chunk(id, s));
        return chunks.filter(chunkValidator::validateChunk).map(chunkReader::readChunk);
    }
}
