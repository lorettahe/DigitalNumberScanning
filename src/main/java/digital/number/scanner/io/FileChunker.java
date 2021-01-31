package digital.number.scanner.io;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Reads a file into a stream of String, where each String is a chunk
 */
public final class FileChunker {
    private static final Pattern EMPTY_LINE = Pattern.compile("\r?\n[ \\t]*\r?\n");

    /**
     * This takes path to the input file, and returns a stream of Strings, where each string is a chunk.
     * Chunks are separated by an empty line
     * @param filePath String representing the path to a file
     * @return Stream of String, where each String represents a chunk, and adjacent chunks are separated by an empty line
     * @throws IOException
     */
    public static Stream<String> fileToChunk(String filePath) throws IOException {
        Scanner scanner = new Scanner(Paths.get(filePath));
        scanner.useDelimiter(EMPTY_LINE);
        return StreamSupport
                .stream(Spliterators.spliteratorUnknownSize(scanner, Spliterator.ORDERED | Spliterator.NONNULL),
                        false)
                .onClose(scanner::close);
    }
}
