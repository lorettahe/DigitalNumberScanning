package digital.number.scanner.service;

import digital.number.scanner.chunk.ChunkResult;
import digital.number.scanner.DigitalNumberScanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class IllegalChunkIntegrationTest {

    private static final String INPUT_DIR = "illegalChunkInput";
    private static final String OUTPUT_DIR = "illegalChunkOutput";
    private static final String RESULT_POSTFIX = ".result";
    private static final String STDOUT_POSTFIX = ".stdout";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    private final DigitalNumberScanner scanner = new DigitalNumberScanner(3, 3, 27);

    @Parameterized.Parameters(name = "{0}")
    public static List<String> inputFileNames() throws IOException {
        Path inputDirPath = Paths.get("src", "test", "resources", INPUT_DIR);
        return Files.list(inputDirPath)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Parameterized.Parameter
    public String inputFileName;

    protected List<String> performScanning(String inputFilePath) {
        List<String> resultStrings = new ArrayList<>();
        try {
            Stream<ChunkResult> scanResultStream = scanner.scan(inputFilePath);
            resultStrings = scanResultStream.map(ChunkResult::getResult).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Failed to scan " + inputFilePath + ":");
            e.printStackTrace();
        }
        return resultStrings;
    }

    @Test
    public final void testScannerWithStdout() throws IOException {
        String inputFilePath = Paths.get("src", "test", "resources", INPUT_DIR, inputFileName).toAbsolutePath().toString();
        List<String> expectedResult = Files.readAllLines(Paths.get("src", "test", "resources", OUTPUT_DIR, inputFileName+RESULT_POSTFIX));
        List<String> expectedStdout = Files.readAllLines(Paths.get("src", "test", "resources", OUTPUT_DIR, inputFileName+STDOUT_POSTFIX));
        assertThat(performScanning(inputFilePath)).containsExactlyElementsOf(expectedResult);
        assertThat(outContent.toString().split("\r?\n")).containsExactlyElementsOf(expectedStdout);
    }
}
