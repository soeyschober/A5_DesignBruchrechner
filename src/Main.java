import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // always start Swing on the EDT, unless you like random bugs
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bruchrechner");
            frame.setContentPane(new BruchrechnerUI().getBruchrechnerView());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();                      // sizes to preferred sizes
            frame.setLocationRelativeTo(null); // center on screen
            frame.setVisible(true);
        });
    }
}