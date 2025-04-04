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
    private final JLabel[] reelLabels;
    private final JButton spinButton;
    private final JTextField betField;
    private final JLabel resultLabel;
    private final SlotMachine slotMachine;

    public SlotMachineView(int numReels) {
        setTitle("Tragamonedas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicializar la lógica de la máquina tragamonedas
        slotMachine = new SlotMachine(numReels);

        JPanel reelsPanel = new JPanel();
        reelsPanel.setLayout(new GridLayout(1, numReels));
        reelLabels = new JLabel[numReels];

        for (int i = 0; i < numReels; i++) {
            reelLabels[i] = new JLabel("?", SwingConstants.CENTER);
            reelLabels[i].setFont(new Font("Arial", Font.BOLD, 24));
            reelsPanel.add(reelLabels[i]);
        }

        add(reelsPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        betField = new JTextField(5);
        spinButton = new JButton("Girar");
        resultLabel = new JLabel("Ingrese su apuesta y gire", SwingConstants.CENTER);

        controlPanel.add(new JLabel("Apuesta: "));
        controlPanel.add(betField);
        controlPanel.add(spinButton);

        add(resultLabel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

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

            // Girar los carretes
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
