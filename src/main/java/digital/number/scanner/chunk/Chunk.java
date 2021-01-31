package digital.number.scanner.chunk;

public class Chunk {
    private final long id;
    private final String[] lines;

    public Chunk(long id, String chunkInput) {
        this.id = id;
        this.lines = chunkInput.split("\r?\n");
    }

    public long getId() {
        return id;
    }

    public String[] getLines() {
        return lines;
    }
}
