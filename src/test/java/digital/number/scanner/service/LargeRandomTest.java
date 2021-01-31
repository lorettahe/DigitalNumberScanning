package digital.number.scanner.service;

import digital.number.scanner.DigitalNumberScanner;
import digital.number.scanner.chunk.ChunkResult;
import digital.number.scanner.testutil.LargeRandomTestGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class LargeRandomTest {
    private final LargeRandomTestGenerator testGenerator = new LargeRandomTestGenerator();
    private final Random seedGenerator = new Random();
    private final DigitalNumberScanner scanner = new DigitalNumberScanner(3, 3, 27);
    
    @Test
    public void test() throws IOException {
        long seed = seedGenerator.nextLong();
        Stream<String> expectedOutput = testGenerator.generateExpectedOutput(seed, 10000, 9);
        Stream<String> input = testGenerator.generateInput(expectedOutput);
        // Have to redo the expected output stream
        expectedOutput = testGenerator.generateExpectedOutput(seed, 10000, 9);
        Path inputPath = Files.createTempFile(""+seed, ".input");
        File inputFile = inputPath.toFile();
        inputFile.deleteOnExit();
        try (PrintWriter pw = new PrintWriter(inputFile)) {
            input.forEach(pw::println);
        }
        Iterator<String> expectedOutputIterator = expectedOutput.iterator();
        Iterator<ChunkResult> actualOutputIterator = scanner.scan(inputPath.toString()).iterator();
        long chunkId = 0;
        while (expectedOutputIterator.hasNext() && actualOutputIterator.hasNext()) {
            String expectedChunkOutput = expectedOutputIterator.next();
            ChunkResult actualChunkOutput = actualOutputIterator.next();
            if (!expectedChunkOutput.equals(actualChunkOutput.getResult())) {
                System.out.println("Problematic chunk ID: " + chunkId);
            }
            assertEquals(expectedChunkOutput, actualChunkOutput.getResult());
        }
        assertTrue(!expectedOutputIterator.hasNext() && !actualOutputIterator.hasNext());
    }
    
}
