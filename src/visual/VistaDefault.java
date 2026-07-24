package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

public class VistaDefault extends JPanel {

    private JTextField a = new JTextField("1");
    private JTextField b = new JTextField("2");
    private JTextField x0 = new JTextField("0");
    private JTextField fx = new JTextField("x^3 - 3*x + 1");
    private JTextField tolerancia = new JTextField("0.001");
    private JLabel metodoTag = new JLabel("Seleccionar metodo");
    private JButton[] botones = {new JButton("Iniciar"), new JButton("Aplicar metodo"), new JButton("Limpiar"), new JButton("Exportar CSV")};
    private JLabel fxDxTag = new JLabel();

    private DefaultTableModel defaultModel;
    private JTable table;
    private JScrollPane scrollPane;

    public VistaDefault() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        fxDxTag.setFont(new Font("Arial", Font.PLAIN, 17));

        add(userData(), BorderLayout.NORTH);
        add(dataDefaultTable());
    }

    public JPanel userData() {
        JPanel datos = new JPanel();
        datos.setLayout(new GridLayout(1, 0, 5, 0));
        Dimension textFieldSize = new Dimension(120, 20);

        metodoTag.setFont(new Font("Arial", Font.BOLD, 17));
        JTextField[] userData = {a, b, x0, fx, tolerancia};

        for (JTextField field : userData) {
            field.setFont(new Font("Helvetica", Font.PLAIN, 15));
            field.setPreferredSize(textFieldSize);
        }

        x0.setToolTipText("Valor inicial (solo Newton-Raphson)");

        datos.add(metodoTag);
        datos.add(new JLabel("a:"));
        datos.add(a);
        datos.add(new JLabel("b:"));
        datos.add(b);
        datos.add(new JLabel("x0:"));
        datos.add(x0);
        datos.add(new JLabel("f(x):"));
        datos.add(fx);
        datos.add(new JLabel("tol:"));
        datos.add(tolerancia);
        datos.add(botones[0]);
        datos.add(botones[1]);
        datos.add(botones[2]);
        datos.add(botones[3]);
        datos.add(fxDxTag);

        return datos;
    }

    public JScrollPane dataDefaultTable() {
        String[] defaultTable = {"Iteracion", "a", "b", "m", "f(a)", "f(b)", "f(m)", "f(a)*f(m)", "Error"};
        defaultModel = new DefaultTableModel(null, defaultTable);
        table = new JTable(defaultModel);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    public void addListener(ActionListener listener) {
        for (JButton boton : botones) {
            boton.addActionListener(listener);
        }
    }

    public void setupEnterKey(ActionListener listener) {
        registerKeyboardAction(listener, KeyStroke.getKeyStroke("ENTER"), -1);
    }

    public String getA() {
        return a.getText();
    }

    public String getB() {
        return b.getText();
    }

    public String getX0() {
        return x0.getText();
    }

    public String getFx() {
        return fx.getText();
    }

    public String getTolerancia() {
        return tolerancia.getText();
    }

    public void setMetodoTag(String nombreMetodo) {
        this.metodoTag.setText(nombreMetodo);
    }

    public String getMetodoTag() {
        return this.metodoTag.getText();
    }

    public void setA(String valorA) {
        a.setText(valorA);
    }

    public void setB(String valorB) {
        b.setText(valorB);
    }

    public void setX0(String valorX0) {
        x0.setText(valorX0);
    }

    public void setFx(String Fx) {
        fx.setText(Fx);
    }

    public void setTolerancia(String valorTolerancia) {
        tolerancia.setText(valorTolerancia);
    }

    public void setfxDxTag(String funcionTag) {
        this.fxDxTag.setText(funcionTag);
    }

    public DefaultTableModel getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(DefaultTableModel model) {
        this.defaultModel = model;
        table.setModel(model);
    }

    public JTable getTable() {
        return table;
    }
}