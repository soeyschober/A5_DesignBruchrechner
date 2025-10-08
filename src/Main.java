import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Bruchrechner");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setContentPane(new BruchrechnerUI());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}