//Programa de un traga monedas
//Integrantes: 
//Baizabal acosta Ismael
//Cruz Mendoza Lucero
package Monedas;
import Monedas.Symbol;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SlotMachineView extends JFrame {
    private final JLabel[] reelLabels;  // Etiquetas para mostrar los carretes
    private final JButton spinButton;   // Botón para girar los ruedas
    private final JTextField betField;  // Campo de entrada para las apuestas
    private final JLabel resultLabel;   // Etiqueta para mostrar el resultado del giro
    private final SlotMachine slotMachine; // Instancia de la lógica del juego

    public SlotMachineView(int numReels) {
        setTitle("Tragamonedas"); // Título de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar aplicación al cerrar ventana
        setLayout(new BorderLayout()); // Usar BorderLayout para organizar los componentes

        // Inicializar la lógica de la máquina tragamonedas
        slotMachine = new SlotMachine(numReels);
        
        // Crear panel para mostrar los carretes
        JPanel reelsPanel = new JPanel();
        reelsPanel.setLayout(new GridLayout(1, numReels));
        reelLabels = new JLabel[numReels];
        
        // Inicializar las etiquetas de las ruedas con el simbolo "?"
        for (int i = 0; i < numReels; i++) {
            reelLabels[i] = new JLabel("?", SwingConstants.CENTER);
            reelLabels[i].setFont(new Font("Arial", Font.BOLD, 24));
            reelsPanel.add(reelLabels[i]);
        }

        add(reelsPanel, BorderLayout.CENTER);  // Agregar panel de carretes al centro

        JPanel controlPanel = new JPanel();
        betField = new JTextField(5); //Campo para meter la cantidad de la apuesta
        spinButton = new JButton("Girar"); //Boton para girar las ruedas
        resultLabel = new JLabel("Ingrese su apuesta y gire", SwingConstants.CENTER);
        
        // Agregar elementos al panel de control
        controlPanel.add(new JLabel("Apuesta: "));
        controlPanel.add(betField);
        controlPanel.add(spinButton);
        
        // Agregar etiquetas y paneles a la ventana
        add(resultLabel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Acción del botón "Girar"
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinReels();
            }
        });
    }

    public void spinReels() {
        try {
            int bet = Integer.parseInt(betField.getText().trim());

            if (bet <= 0) {
                JOptionPane.showMessageDialog(this, "Ingrese una apuesta válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Girar las ruedas
            List<Symbol> results = slotMachine.spinReels();

            // Mostrar los resultados en la UI
            updateReels(results);

            // Calcular pago
            int payout = slotMachine.calculatePayout(bet, results);

            // Mostrar el resultado
            displayPayout(payout);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en la apuesta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateReels(List<Symbol> results) {
        for (int i = 0; i < results.size(); i++) {
            reelLabels[i].setText(results.get(i).getName());
        }
    }

    public void displayPayout(int payout) {
        resultLabel.setText(payout > 0 ? "¡Ganaste " + payout + " monedas!" : "No ganaste. ¡Inténtalo de nuevo!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SlotMachineView(3).setVisible(true));
    }
}
