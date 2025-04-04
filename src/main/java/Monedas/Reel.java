//Integrantes: 
//Baizabal acosta Ismael
//Cruz Mendoza Lucero
package Monedas;
import Monedas.Symbol;
import java.util.List;
import java.util.Random;

public class Reel {
    private final List<Symbol> symbols;
    private Symbol currentSymbol;
    private final Random random = new Random();

    public Reel(List<Symbol> symbols) {
        if (symbols == null || symbols.isEmpty()) {
            throw new IllegalArgumentException("La lista de símbolos no puede estar vacía.");
        }
        this.symbols = List.copyOf(symbols);
    }

    public Symbol spin() {
        try {
            Thread.sleep(500 + random.nextInt(1000)); // Simula el tiempo de giro
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El giro fue interrumpido.");
        }
        currentSymbol = symbols.get(random.nextInt(symbols.size()));
        return currentSymbol;
    }
}