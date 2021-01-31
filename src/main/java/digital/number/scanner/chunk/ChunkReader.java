package digital.number.scanner.chunk;

import digital.number.scanner.symbol.SymbolReader;
import digital.number.scanner.trie.TrieRoot;

/**
 * A reader that returns parsed chunk output when fed individual chunks
 */
public class ChunkReader {
    private final SymbolReader symbolReader;
    private final int charWidth;
    
    public ChunkReader(TrieRoot trie, int charWidth) {
        this(new SymbolReader(trie), charWidth);
    }
    
    protected ChunkReader(SymbolReader symbolReader, int charWidth) {
        this.symbolReader = symbolReader;
        this.charWidth = charWidth;
    }
    
    public ChunkResult readChunk(Chunk chunk) {
        String[] lines = chunk.getLines();
        int lineLength = lines[0].length();
        int noOfLines = lines.length;
        int noOfChars = lineLength / charWidth;
        StringBuilder builder = new StringBuilder();
        boolean hasErrors = false;
        for (int i=0;i<noOfChars;++i) {
            int startIndex = i * charWidth;
            int endIndex = startIndex + charWidth;
            char[][] symbol = new char[noOfLines][charWidth];
            for (int j=0;j<noOfLines;++j) {
                for (int k=startIndex;k<endIndex;++k) {
                    symbol[j][k%charWidth]=lines[j].charAt(k);
                }
            }
            char outputChar = symbolReader.readSymbol(symbol);
            builder.append(outputChar);
            if (outputChar == '?') hasErrors = true;
        }
        if (hasErrors) {
            builder.append("ILL");
        }
        return new ChunkResult(chunk.getId(), builder.toString());
    }
}
