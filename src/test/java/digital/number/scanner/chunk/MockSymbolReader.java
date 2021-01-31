package digital.number.scanner.chunk;

import digital.number.scanner.symbol.SymbolReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MockSymbolReader extends SymbolReader {
    
    MockSymbolReader() {
        super(null);
    }

    @Override
    public char readSymbol(char[][] input) {
        List<String> inputStrings = Arrays.stream(input).map(String::valueOf).collect(Collectors.toList());
        String inputString = String.join("\n", inputStrings);
        return switch (inputString) {
            case " _ \n| |\n|_|" -> '0';
            case "   \n  |\n  |" -> '1';
            case " _ \n _|\n|_ " -> '2';
            case " _ \n _|\n _|" -> '3';
            case "   \n|_|\n  |" -> '4';
            case " _ \n|_ \n _|" -> '5';
            case " _ \n|_ \n|_|" -> '6';
            case " _ \n  |\n  |" -> '7';
            case " _ \n|_|\n|_|" -> '8';
            case " _ \n|_|\n _|" -> '9';
            default -> '?';
        };
    } 
}
