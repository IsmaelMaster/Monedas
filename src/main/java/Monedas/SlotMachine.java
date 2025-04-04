//Integrantes: 
//Baizabal acosta Ismael
//Cruz Mendoza Lucero
package Monedas;
import Monedas.Symbol;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SlotMachine {
    private final List<Reel> reels;
    private final ExecutorService executor;

    public SlotMachine(int numReels) {
        if (numReels <= 0) {
            throw new IllegalArgumentException("El número de carretes debe ser mayor que cero.");
        }

        List<Symbol> symbols = List.of(
            new Symbol("Cherry", 10),
            new Symbol("Lemon", 5),
            new Symbol("Bar", 15),
            new Symbol("Seven", 50)
        );

        this.reels = new ArrayList<>();
        for (int i = 0; i < numReels; i++) {
            reels.add(new Reel(symbols));
        }

        this.executor = Executors.newFixedThreadPool(numReels);
    }

    public List<Symbol> spinReels() {
        List<Future<Symbol>> futures = new ArrayList<>();
        for (Reel reel : reels) {
            futures.add(executor.submit(reel::spin));
        }

        List<Symbol> results = new ArrayList<>();
        for (Future<Symbol> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                System.err.println("Error al obtener el resultado de un carrete: " + e.getMessage());
                Thread.currentThread().interrupt(); // Mantiene el estado de interrupción
            }
        }

        return results;
    }

    public int calculatePayout(int bet, List<Symbol> results) {
        if (bet <= 0) {
            throw new IllegalArgumentException("La apuesta debe ser mayor que cero.");
        }
        if (results == null || results.isEmpty()) {
            throw new IllegalArgumentException("No hay símbolos en el resultado.");
        }

        String firstSymbol = results.get(0).getName();
        if (results.stream().allMatch(symbol -> symbol.getName().equals(firstSymbol))) {
            return bet * results.get(0).getValue();
        }
        return 0;
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
