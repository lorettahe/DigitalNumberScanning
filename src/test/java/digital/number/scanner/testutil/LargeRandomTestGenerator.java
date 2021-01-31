package digital.number.scanner.testutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class LargeRandomTestGenerator {
    private static Map<Character, List<String>> charToInput = new HashMap<>();
    private static int noOfLinesInChar = 0;
    
    static {
        try {
            String trieInput = "trie/trie_input";
            File trieInputFile = new File(LargeRandomTestGenerator.class.getClassLoader().getResource(trieInput).getFile());
            List<String> trieInputLines = Files.readAllLines(trieInputFile.toPath());
            // We know the second line of the file will be the first line of the first character
            charToInput = TrieTestUtils.buildExpectedOutput(trieInputLines);
            noOfLinesInChar = charToInput.entrySet().stream().findAny().get().getValue().size();
        } catch (IOException e) {
            // Don't do anything
        }
    }
    
    private String generateChunkExpectedOutput(Random random, int noOfCharsInChunk) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<noOfCharsInChunk;++i) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    public Stream<String> generateExpectedOutput(long seed, long noOfChunks, int noOfCharsInChunk) {
        Random random = new Random(seed);
        Stream<String> expectedOutputStream = LongStream.range(0, noOfChunks)
                .mapToObj(id -> generateChunkExpectedOutput(random, noOfCharsInChunk));
        return expectedOutputStream;
    }
    
    private String[] generateChunkInput(String chunkOutput) {
        int noOfChars = chunkOutput.length();
        String[] lines = new String[noOfLinesInChar+1];
        Arrays.fill(lines, "");
        for (int i=0;i<noOfChars;++i) {
            List<String> charLines = charToInput.get(chunkOutput.charAt(i));
            for (int j=0;j<noOfLinesInChar;++j) {
                lines[j] = lines[j]+charLines.get(j);
            }
        }
        lines[noOfLinesInChar] = "";
        return lines;
    }
    
    public Stream<String> generateInput(Stream<String> expectedOutput) {
        return expectedOutput.flatMap(output -> Stream.of(generateChunkInput(output)));
    }
}