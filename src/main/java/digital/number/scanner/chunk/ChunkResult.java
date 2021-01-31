package digital.number.scanner.chunk;

public class ChunkResult {
    private final long id;
    private final String result;
    
    public ChunkResult(long id, String result) {
        this.id = id;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    } 
}
