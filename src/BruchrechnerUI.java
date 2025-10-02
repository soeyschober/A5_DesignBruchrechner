import javax.swing.*;

public class BruchrechnerUI {
    private JComboBox<Character> operationCB;
    private JProgressBar progressBar2;
    private JTextField textField1;
    private JPanel BruchrechnerView;
    private JButton resetBtn;
    private JButton berechnenBtn;
    private JPanel ergebnisPL;
    private JPanel eingabePL;
    private JPanel fussPL;

    public JPanel getBruchrechnerView() {
        return BruchrechnerView;
    }

    public BruchrechnerUI() {
        operationCB.addItem('+');
        operationCB.addItem('-');
        operationCB.addItem('*');
        operationCB.addItem('/');
    }
}
