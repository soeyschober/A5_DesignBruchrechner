import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;


public class BruchrechnerUI extends JPanel {

    private JTextField aZ;
    private JTextField aN;
    private JTextField bZ;
    private JTextField bN;
    private JComboBox<String> op;
    private JButton berechnen;
    private JButton reset;
    private JButton tauschen;
    private JLabel ergBruch;
    private JLabel ergGemischt;
    private JLabel fehler;
    private JLabel headerLbl;
    private JProgressBar aTrennstrichPB;
    private JProgressBar bTrennstrichPB;
    private JPanel BruchrechnerView;
    private JPanel eingabePL;
    private JPanel headerPL;
    private JPanel fussPL;
    private JPanel ergebnisPL;

    public BruchrechnerUI() {
        setLayout(new BorderLayout(12, 12));
        setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel titel = new JLabel("Bruchrechner");
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 20f));
        titel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titel, BorderLayout.NORTH);

        // Center panel with inputs (3 Spalten: A | Operator | B)
        JPanel center = new JPanel(new GridBagLayout());
        add(center, BorderLayout.CENTER);
        GridBagConstraints base = new GridBagConstraints();
        base.insets = new Insets(6, 6, 6, 6);

        aZ = new JTextField(6);
        aN = new JTextField(6);
        bZ = new JTextField(6);
        bN = new JTextField(6);
        unifyFieldSizes(aZ, aN, bZ, bN);
        onlyInteger(aZ); onlyInteger(aN); onlyInteger(bZ); onlyInteger(bN);

        op = new JComboBox<>(new String[]{"+", "-", "×", "÷"});
        op.setFocusable(false);

        // A-Block (mittig in Spalte 0)
        addFrac(center, base, "A", aZ, aN);

        // Operator mittig in Spalte 1
        JLabel opLbl = new JLabel("Operator");
        opLbl.setLabelFor(op);
        JPanel opBox = new JPanel(new BorderLayout());
        opBox.add(opLbl, BorderLayout.NORTH);
        opBox.add(op, BorderLayout.CENTER);

        GridBagConstraints cop = new GridBagConstraints();
        cop.gridx = 1;
        cop.gridy = 0;
        cop.insets = base.insets;
        cop.anchor = GridBagConstraints.CENTER;
        cop.fill = GridBagConstraints.NONE;
        cop.weightx = 0;
        center.add(opBox, cop);

        // B-Block (mittig in Spalte 2)
        addFrac(center, base, "B", bZ, bN);

        // Buttons (zentriert, über alle 3 Spalten)
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        berechnen = new JButton("Berechnen");
        berechnen.setMnemonic('B');
        reset = new JButton("Reset");
        reset.setMnemonic('R');
        tauschen = new JButton("A ↔ B");
        tauschen.setMnemonic('T');

        // Optional: gleiche Button-Größe
        Dimension btnMax = new Dimension(
                Math.max(berechnen.getPreferredSize().width,
                        Math.max(reset.getPreferredSize().width, tauschen.getPreferredSize().width)),
                Math.max(berechnen.getPreferredSize().height,
                        Math.max(reset.getPreferredSize().height, tauschen.getPreferredSize().height))
        );
        berechnen.setPreferredSize(btnMax);
        reset.setPreferredSize(btnMax);
        tauschen.setPreferredSize(btnMax);

        buttons.add(berechnen);
        buttons.add(tauschen);
        buttons.add(reset);

        GridBagConstraints cb = new GridBagConstraints();
        cb.gridx = 0; cb.gridy = 2; cb.gridwidth = 3;
        cb.insets = new Insets(10, 6, 6, 6);
        cb.anchor = GridBagConstraints.CENTER;
        cb.fill = GridBagConstraints.NONE;
        center.add(buttons, cb);

        // Ergebnis unten
        JPanel result = new JPanel(new GridLayout(3, 1, 4, 4));
        result.setBorder(BorderFactory.createTitledBorder("Ergebnis"));
        ergBruch = new JLabel("—");
        ergBruch.setFont(ergBruch.getFont().deriveFont(Font.BOLD, 18f));
        ergGemischt = new JLabel("");
        fehler = new JLabel("");
        fehler.setForeground(new Color(170, 0, 0));
        result.add(ergBruch);
        result.add(ergGemischt);
        result.add(fehler);
        add(result, BorderLayout.SOUTH);

        // Actions
        berechnen.addActionListener(this::onBerechnen);
        reset.addActionListener(e -> {
            aZ.setText("");
            aN.setText("");
            bZ.setText("");
            bN.setText("");
            ergBruch.setText("—");
            ergGemischt.setText("");
            fehler.setText("");
            aZ.requestFocusInWindow();
        });
        tauschen.addActionListener(e -> {
            String z = aZ.getText(), n = aN.getText();
            aZ.setText(bZ.getText());
            aN.setText(bN.getText());
            bZ.setText(z);
            bN.setText(n);
        });

        // Enter triggert Berechnen
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "calc");
        getActionMap().put("calc", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { onBerechnen(e); }
        });

        setTooltipsAndA11y();
    }

    private void addFrac(JPanel parent, GridBagConstraints c, String label, JTextField z, JTextField n) {
        JLabel head = new JLabel("Bruch " + label);
        head.setHorizontalAlignment(SwingConstants.CENTER); // Überschrift auch mittig

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));

        // Textfelder nicht größer als preferred
        z.setMaximumSize(z.getPreferredSize());
        n.setMaximumSize(n.getPreferredSize());

        // numerisch hübscher: zentrierte Felder
        z.setAlignmentX(Component.CENTER_ALIGNMENT);
        n.setAlignmentX(Component.CENTER_ALIGNMENT);

        fields.add(z);
        fields.add(Box.createVerticalStrut(2));
        fields.add(new JSeparator());
        fields.add(Box.createVerticalStrut(2));
        fields.add(n);

        JPanel container = new JPanel(new BorderLayout());
        container.add(head, BorderLayout.NORTH);
        container.add(fields, BorderLayout.CENTER);

        GridBagConstraints cc = new GridBagConstraints();
        cc.gridx = label.equals("A") ? 0 : 2;      // <- A links, B rechts; Mitte gehört dem Operator
        cc.gridy = 0;
        cc.insets = c.insets;
        cc.anchor = GridBagConstraints.CENTER;     // <- wirklich mittig
        cc.fill = GridBagConstraints.NONE;         // <- nicht dehnen
        cc.weightx = 1;                            // <- Spalte bekommt Raum, Komponente bleibt mittig

        parent.add(container, cc);
    }


    private void onBerechnen(ActionEvent e) {
        fehler.setText("");
        try {
            Bruch A = read(aZ, aN);
            Bruch B = read(bZ, bN);
            String operator = (String) op.getSelectedItem();
            Bruch res;
            switch (operator) {
                case "+": res = A.add(B); break;
                case "-": res = A.sub(B); break;
                case "×": res = A.mul(B); break;
                case "÷": res = A.div(B); break;
                default: throw new IllegalStateException("Unbekannter Operator: " + operator);
            }
            ergBruch.setText("Als Bruch: " + res.toString());
            ergGemischt.setText("Gemischt: " + res.toMixedString());
        } catch (NumberFormatException ex) {
            fehler.setText("Bitte ganze Zahlen eingeben. " + ex.getMessage());
            ergBruch.setText("—");
            ergGemischt.setText("");
        } catch (IllegalArgumentException | ArithmeticException ex) {
            fehler.setText(ex.getMessage());
            ergBruch.setText("—");
            ergGemischt.setText("");
        }
    }

    private Bruch read(JTextField z, JTextField n) {
        String zs = z.getText().trim();
        String ns = n.getText().trim();
        if (zs.isEmpty() || ns.isEmpty()) {
            throw new IllegalArgumentException("Zähler und Nenner bitte ausfüllen.");
        }
        int zl = Integer.parseInt(zs);
        int nl = Integer.parseInt(ns);
        return new Bruch(zl, nl);
    }

    private void onlyInteger(JTextField tf) {
        ((AbstractDocument) tf.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (accepts(string)) super.insertString(fb, offset, string, attr);
            }
            @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (accepts(text)) super.replace(fb, offset, length, text, attrs);
            }
            private boolean accepts(String s) {
                if (s == null || s.isEmpty()) return true;
                for (int i = 0; i < s.length(); i++) {
                    char ch = s.charAt(i);
                    if (!(Character.isDigit(ch) || ch == '-')) return false;
                }
                return true;
            }
        });
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        tf.setToolTipText("Ganze Zahl, z. B. -3 oder 42");
    }

    private void setTooltipsAndA11y() {
        aZ.getAccessibleContext().setAccessibleName("Zähler A");
        aN.getAccessibleContext().setAccessibleName("Nenner A");
        bZ.getAccessibleContext().setAccessibleName("Zähler B");
        bN.getAccessibleContext().setAccessibleName("Nenner B");
        op.setToolTipText("Rechenoperation auswählen");
        berechnen.setToolTipText("Berechnet A [Operator] B");
        reset.setToolTipText("Alle Felder löschen");
        tauschen.setToolTipText("Tauscht A und B");
    }

    private void unifyFieldSizes(JTextField... fields) {
        Dimension max = new Dimension(0, 0);
        for (JTextField f : fields) {
            Dimension d = f.getPreferredSize();
            if (d.width > max.width) max.width = d.width;
            if (d.height > max.height) max.height = d.height;
        }
        for (JTextField f : fields) {
            f.setPreferredSize(max);
            f.setMinimumSize(max);
            f.setMaximumSize(max);
        }
    }
}
