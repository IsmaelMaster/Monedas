//Integrantes: 
//Baizabal acosta Ismael
//Cruz Mendoza Lucero
package Monedas;
import Monedas.Symbol;
import java.util.List;
import java.util.Random;

public class Reel {
    private final List<Symbol> symbols; // Lista de símbolos disponibles en el carrete
    private Symbol currentSymbol; // Símbolo actual mostrado en el carrete
    private final Random random = new Random(); // Generador de números aleatorios
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
            Thread.currentThread().interrupt(); // Restablece el estado de interrupción si ocurre un error
            System.err.println("El giro fue interrumpido.");
        }
        currentSymbol = symbols.get(random.nextInt(symbols.size())); //Aleatoriamente elige el simbolo que salga
        return currentSymbol;
    }
}