package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.management.timer.Timer;
import javax.swing.table.DefaultTableModel;

import visual.VistaDefault;

public class procesoInterno implements ActionListener{

    private VistaDefault vista =  new VistaDefault();
    private MyMenuListener menuListener = new MyMenuListener();

    //general and user values
    private int iteraciones;
    private double a;
    private double b;
    private String ecuacion = "x³ - 3x + 1";
    private double valorTolerancia = 0.001;

    private double error;
    private double error2;

    //defaultValues
    private double fa;
    private double fb;
    private double m;
    private double mAnt;
    private double fm;
    private double fafm;

    //newtonRaphson
    private double fx;
    private double fxDx;
    private double fx2;
    private double fxDx2;
    private double m2;
    private double mAnt2;

    //controlMetodo
    String metodoControl;
    Timer timer;

    public procesoInterno(VistaDefault vista, MyMenuListener menuListener){
        this.vista = vista;
        this.vista.addListener(this);
        this.menuListener = menuListener;
        //vista.avisoTutorial();
        valoresUsuario();
        
        //vista.dataTable();
    }

    @Override
	public void actionPerformed(ActionEvent e){
        switch(e.getActionCommand()) {
	        case "Iniciar":
            metodoElegido();
	            break;
	        case "Aplicar metodo":
            //reiniciarTabla();
	            break;
            case "Limpiar":
            limpiar();
	            break;
	    }
    }

    public void metodoElegido(){

        switch(menuListener.getMetodo()){

            case "Metodo de Biseccion":
            if(iteraciones == 0){
                reiniciarTabla();
                a = 1;
                b = 2;
            }
            metodoBiseccion();
            break;
        
            case "Falsa Posicion":
            if(iteraciones == 0){
                a = 1;
                b = 2;
                reiniciarTabla();
            }
            falsaPosicion();
            break;
            
            case "Newton Raphson":
            if(iteraciones == 0){
                a = 2;
                b = 1;
                reiniciarTabla();
            }
            newtonRapshon();
            break;

        }

    }

    public void valoresUsuario(){
        //limpiar();
        //vista.setA(String.valueOf(a));
        //vista.setB(String.valueOf(b));
        vista.setFx(ecuacion);
        vista.setTolerancia(String.valueOf(valorTolerancia));
        //vista.setIteraciones("Iteracion: " + String.valueOf(iteraciones));
    }

    public void limpiar(){

        
        vista.getDefaultModel().setRowCount(0);

        switch(menuListener.getMetodo()){

            case "Newton Raphson":
                a = 2;
                b = 1;
                vista.setfxDxTag("f'(x)= 3x² - 3");
            break;

            default:
                a = 1;
                b = 2;
                vista.setfxDxTag(null);
            break;
        }

        iteraciones = 0;

        vista.setA(String.valueOf(a));
        vista.setB(String.valueOf(b));
        vista.setfxDxTag(null);
        
    }

    public void metodoBiseccion(){
          //do{
            vista.setMetodoTag("Método de Bisección");
                m = (a + b) / 2;
                fa = (Math.pow(a, 3)) - 3*(a) + 1;
                fb = (Math.pow(b, 3)) - 3*(b) + 1;
                fm = (Math.pow(m, 3)) - 3*(m) + 1;
                fafm = fa*fm;

                if(iteraciones == 0){
                    metodoControl = menuListener.getMetodo();
                    limpiar();
                    reiniciarTabla();
                    error = 0;
                    mAnt = m;
                }else if(iteraciones > 0){
                    error = Math.abs((m - mAnt)/ m);
                    mAnt = m;
                }
                
                cargarValores();
                iteraciones++;

                if(fafm > 0){
                    a = m;
                }else{
                    b = m;
                }

                
           //}while(true);
    }

    public void falsaPosicion(){
        // do{}
            vista.setMetodoTag("Falsa Posicion");
            fa = (Math.pow(a, 3)) - 3*(a) + 1;
            fb = (Math.pow(b, 3)) - 3*(b) + 1;
            m = a - ((fa*(b-a)) / (fb - fa));
            fm = (Math.pow(m, 3)) - 3*(m) + 1;
            fafm = fa*fm;

            if(iteraciones == 0){
                metodoControl = menuListener.getMetodo();
                limpiar();
                reiniciarTabla();
                error = 0;
                mAnt = m;
            }else if(iteraciones > 0){
                error = Math.abs((m - mAnt)/ m);
                mAnt = m;
            }
            
            cargarValores();
            iteraciones++;

            if(fafm < 0){
                b = m;
            }else{
                a = m;
            }
            
       //}while((valorTolerancia < Math.abs(error)) && iteraciones < 8);

    }

    public void newtonRapshon(){
        
        vista.setMetodoTag("Newton Rapshon");
        
        fx = (Math.pow(a, 3)) - 3*(a) + 1;
        fxDx = (Math.pow(a, 2)) - 3;

        fx2 = (Math.pow(b, 3)) - 3*(b) + 1;
        fxDx2 = (Math.pow(b, 2)) - 3;
        
        if(iteraciones == 0){
            metodoControl = menuListener.getMetodo();
            limpiar();
            reiniciarTabla();
            vista.setA(String.valueOf(a));
            vista.setA(String.valueOf(b));
            m = a;
            m2 = b;
            error = 0;
            error2 = 0;
            mAnt = m;
            mAnt2 = m2;
        }else if(iteraciones > 0 && (fxDx != 0 || fxDx2 != 0)){
            mAnt = m - (fx/fxDx);
            mAnt2 = m2 - (fx2/fxDx2);
            error = Math.abs(m-mAnt);
            error2 = Math.abs(m2-mAnt2);
        }
        
       cargarValores();
       iteraciones++;
        
    }

    public void reiniciarTabla(){

        vista.setMetodoTag(menuListener.getMetodo());
        DefaultTableModel model = (DefaultTableModel) vista.getDefaultModel();
        int columnCount = model.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
           model.setColumnIdentifiers(new Vector<>());
        }

        String[] newtonColumnNames = {"Iteracion","Xn+1","f(x)","f'(x)","Error","Xn+1","f(x)","f'(x)","Error"};
        String[] defaultColumNames = {"Iteracion","a","b","m","f(a)","f(b)","f(m)","f(a)*f(m)","Error"};

        switch(menuListener.getMetodo()){

            case "Newton Raphson":
                for (String columnName : newtonColumnNames) {
                    model.addColumn(columnName);
                }

            break;

            default:
                for (String columnName : defaultColumNames) {
                    model.addColumn(columnName);
                }
            
            break;
        }

        limpiar();
    }


    public void cargarValores(){

        Object[] controlRow; 
        Object[] defaultRow =  {iteraciones,a,b,m,fa,fb,fm,fafm,error};
        Object[] newtonRow = {iteraciones,m,fx,fxDx,error,m2,fx2,fxDx2,error2};

        switch(menuListener.getMetodo()){
            case "Newton Raphson":
                controlRow = newtonRow;
            break;
            
            default:
                controlRow =  defaultRow;
                
            break;

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