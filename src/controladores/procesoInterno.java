package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import visual.VistaDefault;

public class procesoInterno implements ActionListener {

    private VistaDefault vista;
    private MyMenuListener menuListener;

    private int iteraciones;
    private double a;
    private double b;
    private String ecuacion = "x^3 - 3*x + 1";
    private double valorTolerancia = 0.001;

    private double error;
    private double error2;

    private double fa;
    private double fb;
    private double m;
    private double mAnt;
    private double fm;
    private double fafm;

    private double fx;
    private double fxDx;
    private double fx2;
    private double fxDx2;
    private double m2;
    private double mAnt2;

    String metodoControl;

    public procesoInterno(VistaDefault vista, MyMenuListener menuListener) {
        this.vista = vista;
        this.vista.addListener(this);
        this.menuListener = menuListener;
        valoresUsuario();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Iniciar":
                metodoElegido();
                break;
            case "Aplicar metodo":
                aplicarMetodo();
                break;
            case "Limpiar":
                limpiar();
                break;
        }
    }

    private void aplicarMetodo() {
        if (!leerValoresUsuario()) return;
        reiniciarTabla();
        iteraciones = 0;
        String metodo = menuListener.getMetodo();
        if (metodo == null || metodo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un metodo en el menu Opciones",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (metodo.equals("Newton Raphson")) {
            vista.setfxDxTag("f'(x): derivada numerica de " + ecuacion);
        } else {
            vista.setfxDxTag(null);
        }
    }

    private boolean leerValoresUsuario() {
        try {
            a = Double.parseDouble(vista.getA());
            b = Double.parseDouble(vista.getB());
            ecuacion = vista.getFx();
            valorTolerancia = Double.parseDouble(vista.getTolerancia());
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                "Ingrese valores numericos validos para a, b y tolerancia",
                "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void metodoElegido() {
        String metodo = menuListener.getMetodo();
        if (metodo == null || metodo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un metodo en el menu Opciones",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (iteraciones == 0) {
            if (!leerValoresUsuario()) return;
            reiniciarTabla();
        }

        switch (metodo) {
            case "Metodo de Biseccion":
                metodoBiseccion();
                break;
            case "Falsa Posicion":
                falsaPosicion();
                break;
            case "Newton Raphson":
                newtonRapshon();
                break;
        }
    }

    public void valoresUsuario() {
        vista.setA(String.valueOf(a));
        vista.setB(String.valueOf(b));
        vista.setFx(ecuacion);
        vista.setTolerancia(String.valueOf(valorTolerancia));
    }

    public void limpiar() {
        vista.getDefaultModel().setRowCount(0);
        iteraciones = 0;
        String metodo = menuListener.getMetodo();
        if (metodo != null && metodo.equals("Newton Raphson")) {
            vista.setfxDxTag("f'(x): derivada numerica de " + ecuacion);
        } else {
            vista.setfxDxTag(null);
        }
    }

    public void metodoBiseccion() {
        vista.setMetodoTag("Metodo de Biseccion");
        m = (a + b) / 2;
        fa = Evaluador.evaluar(ecuacion, a);
        fb = Evaluador.evaluar(ecuacion, b);
        fm = Evaluador.evaluar(ecuacion, m);
        fafm = fa * fm;

        if (iteraciones == 0) {
            metodoControl = menuListener.getMetodo();
            error = 0;
            mAnt = m;
        } else {
            error = Math.abs((m - mAnt) / m);
            mAnt = m;
        }

        cargarValores();
        iteraciones++;

        if (fafm > 0) {
            a = m;
        } else {
            b = m;
        }
    }

    public void falsaPosicion() {
        vista.setMetodoTag("Falsa Posicion");
        fa = Evaluador.evaluar(ecuacion, a);
        fb = Evaluador.evaluar(ecuacion, b);
        m = a - ((fa * (b - a)) / (fb - fa));
        fm = Evaluador.evaluar(ecuacion, m);
        fafm = fa * fm;

        if (iteraciones == 0) {
            metodoControl = menuListener.getMetodo();
            error = 0;
            mAnt = m;
        } else {
            error = Math.abs((m - mAnt) / m);
            mAnt = m;
        }

        cargarValores();
        iteraciones++;

        if (fafm < 0) {
            b = m;
        } else {
            a = m;
        }
    }

    public void newtonRapshon() {
        vista.setMetodoTag("Newton Raphson");

        if (iteraciones == 0) {
            metodoControl = menuListener.getMetodo();
            error = 0;
            error2 = 0;
        }

        fx = Evaluador.evaluar(ecuacion, m);
        fxDx = Evaluador.derivar(ecuacion, m);
        fx2 = Evaluador.evaluar(ecuacion, m2);
        fxDx2 = Evaluador.derivar(ecuacion, m2);

        double mTemp = m;
        double m2Temp = m2;

        if (fxDx != 0) m = m - (fx / fxDx);
        if (fxDx2 != 0) m2 = m2 - (fx2 / fxDx2);

        if (iteraciones > 0) {
            error = Math.abs(m - mTemp);
            error2 = Math.abs(m2 - m2Temp);
        }

        cargarValores();
        iteraciones++;
    }

    public void reiniciarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.getDefaultModel();
        model.setRowCount(0);
        int columnCount = model.getColumnCount();
        for (int i = columnCount - 1; i >= 0; i--) {
            model.setColumnIdentifiers(new java.util.Vector<>());
        }
        String[] newtonColumnNames = {"Iteracion", "Xn+1", "f(x)", "f'(x)", "Error", "Xn+1", "f(x)", "f'(x)", "Error"};
        String[] defaultColumNames = {"Iteracion", "a", "b", "m", "f(a)", "f(b)", "f(m)", "f(a)*f(m)", "Error"};
        String metodo = menuListener.getMetodo();
        if ("Newton Raphson".equals(metodo)) {
            for (String name : newtonColumnNames) {
                model.addColumn(name);
            }
        } else {
            for (String name : defaultColumNames) {
                model.addColumn(name);
            }
        }
    }

    public void cargarValores() {
        Object[] controlRow;
        Object[] defaultRow = {iteraciones, a, b, m, fa, fb, fm, fafm, error};
        Object[] newtonRow = {iteraciones, m, fx, fxDx, error, m2, fx2, fxDx2, error2};

        if ("Newton Raphson".equals(menuListener.getMetodo())) {
            controlRow = newtonRow;
        } else {
            controlRow = defaultRow;
        }

        vista.getDefaultModel().addRow(controlRow);
    }

    public int getIteraciones() {
        return iteraciones;
    }

    public void setIteraciones(int iteraciones) {
        this.iteraciones = iteraciones;
    }
}
