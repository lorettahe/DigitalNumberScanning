package digital.number.scanner.service;

import digital.number.scanner.chunk.ChunkResult;
import digital.number.scanner.DigitalNumberScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScannerServiceIntegrationTest extends BaseScannerServiceIntegrationTest {
    private final DigitalNumberScanner scanner = new DigitalNumberScanner(3, 3, 27);

    @Override
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
}
