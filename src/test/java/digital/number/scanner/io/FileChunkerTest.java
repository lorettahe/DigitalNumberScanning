package digital.number.scanner.io;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FileChunkerTest {
    
    @Test
    public void testFileToChunk() {
        String filePath = Paths.get("src", "test", "resources", "testInput", "all_digits").toAbsolutePath().toString();
        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add(" _  _  _  _  _  _  _  _  _ \n| || || || || || || || || |\n|_||_||_||_||_||_||_||_||_|");
        expectedStrings.add("    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|");
        runTest(filePath, expectedStrings);
    }
    
    @Test
    public void testDoubleEmptyLines() {
        String filePath = Paths.get("src", "test", "resources", "illegalChunkInput", "double_empty_line").toAbsolutePath().toString();
        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add(" _  _  _  _  _  _  _  _  _ \n| || || || || || || || || |\n|_||_||_||_||_||_||_||_||_|");
        expectedStrings.add("\n    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|");
        runTest(filePath, expectedStrings);
    }
    
    @Test
    public void testSpaceInDelimiterThenSpaceAsFirstLine() {
        String filePath = Paths.get("src", "test", "resources", "testInput", "space_in_delimiter_then_space_as_first_line").toAbsolutePath().toString();
        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add(" _  _  _  _  _  _  _  _  _ \n| || || || || || || || || |\n|_||_||_||_||_||_||_||_||_|");
        expectedStrings.add("                           \n  |  ||_||_|  |  |  ||_||_|\n  |  |  |  |  |  |  |  |  |");
        runTest(filePath, expectedStrings);
    }
    
    @Test(expected = IOException.class)
    public void testNonExistingFileShouldThrow() throws IOException {
        String filePath = Paths.get("src", "test", "resources", "testInput", "non_existent_file").toAbsolutePath().toString();
        FileChunker.fileToChunk(filePath);
    }
    
    private void runTest(String filePath, List<String> expectedStrings) {
        try {
            Stream<String> chunks = FileChunker.fileToChunk(filePath);
            List<String> chunkList = chunks.collect(Collectors.toList());
            assertEquals(expectedStrings.size(), chunkList.size());
            for (int i=0;i<expectedStrings.size();++i) {
                List<String> outputChunkLines = Arrays.asList(chunkList.get(i).split("\r?\n").clone());
                List<String> expectedOutputLines = Arrays.asList(expectedStrings.get(i).split("\r?\n").clone());
                assertEquals(expectedOutputLines, outputChunkLines);
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}
