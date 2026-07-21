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
import javax.swing.table.DefaultTableModel;

public class VistaDefault extends JPanel {

    private JTextField a = new JTextField("1");
    private JTextField b = new JTextField("2");
    private JTextField fx = new JTextField("x^3 - 3*x + 1");
    private JTextField tolerancia = new JTextField("0.001");
    private JLabel metodoTag = new JLabel("Seleccionar metodo");
    private JButton[] botones = {new JButton("Iniciar"), new JButton("Aplicar metodo"), new JButton("Limpiar")};
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
        Dimension textFieldSize = new Dimension(150, 20);

        metodoTag.setFont(new Font("Arial", Font.BOLD, 17));
        JTextField[] userData = {a, b, fx, tolerancia};

        for (JTextField field : userData) {
            field.setFont(new Font("Helvetica", Font.PLAIN, 15));
            field.setPreferredSize(textFieldSize);
        }

        datos.add(metodoTag);
        datos.add(a);
        datos.add(b);
        datos.add(fx);
        datos.add(tolerancia);
        datos.add(botones[0]);
        datos.add(botones[1]);
        datos.add(botones[2]);
        datos.add(fxDxTag);

        return datos;
    }

    public JScrollPane dataDefaultTable() {
        String[] defaultTable = {"Iteracion", "a", "b", "m", "f(a)", "f(b)", "f(m)", "f(a)*f(m)", "Error"};
        defaultModel = new DefaultTableModel(null, defaultTable);
        table = new JTable(defaultModel);
        scrollPane = new JScrollPane(table);
        return scrollPane;
    }

    public void addListener(ActionListener listener) {
        for (JButton boton : botones) {
            boton.addActionListener(listener);
        }
    }

    public String getA() {
        return a.getText();
    }

    public String getB() {
        return b.getText();
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
}
