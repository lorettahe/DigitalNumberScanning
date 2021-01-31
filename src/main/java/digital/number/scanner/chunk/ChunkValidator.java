package digital.number.scanner.chunk;

/**
 * Validates a given chunk for incorrect length of line and incorrect number of lines in a chunk
 */
public class ChunkValidator {
    private final int legitLineLength;
    private final int legitNoOfLines;

    public ChunkValidator(int legitLineLength, int legitNoOfLines) {
        this.legitLineLength = legitLineLength;
        this.legitNoOfLines = legitNoOfLines;
    }

    public boolean validateChunk(Chunk chunk) {
        long id = chunk.getId();
        String[] lines = chunk.getLines();
        if (lines.length != legitNoOfLines) {
            String error = String.format("Error on chunk %d: this illegal chunk has %d lines instead of %d lines!",
                    id, lines.length, legitNoOfLines);
            System.out.println(error);
            return false;
        }
        boolean hasError = false;
        for (int i=0;i<legitNoOfLines;++i) {
            if (lines[i].length() != legitLineLength) {
                hasError = true;
                String error = String.format("Error on chunk %d line %d: this illegal line has %d characters instead of %d characters!",
                        id, i, lines[i].length(), legitLineLength);
                System.out.println(error);
            }
        }
        return !hasError;
    }
}
