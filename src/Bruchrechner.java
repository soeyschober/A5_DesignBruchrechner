import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import java.awt.*;
import java.awt.geom.Path2D;

public class Bruchrechner {
    // Eingabefelder (kompatibel gehalten)
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JComboBox<Character> operationCB;
    private JPanel BruchrechnerView;
    private JButton einfacherBruchrechnerButton;
    private JButton gemischterBruchrechnerButton;
    private JButton dezimalzahlButton;
    private JButton ganzerBruchButton;
    private JButton gemischterBruchButton;
    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JTextField resultNumField;
    private JTextField resultDenField;

    public Bruchrechner() {
        if (operationCB != null) {
            operationCB.setModel(new DefaultComboBoxModel<>(
                    new Character[]{'+', '-', '*', '/'}
            ));
            operationCB.setSelectedItem('+');
        }
    }

    // Public API
    public JPanel getBruchrechnerView() {
        return BruchrechnerView;
    }
}
