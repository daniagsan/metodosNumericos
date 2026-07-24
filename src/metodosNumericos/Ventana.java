package metodosNumericos;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controladores.MyMenuListener;
import controladores.procesoInterno;
import visual.VistaDefault;

public class Ventana extends JFrame{
	
	private JMenuBar menuBar = new JMenuBar();
	MyMenuListener listenerMenu = new MyMenuListener(this);

	public Ventana() {
		
		setVisible(true);
		
		int top = getInsets().top;
		int left = getInsets().left;
		int bottom = getInsets().bottom;
		int right = getInsets().right;
		
		
		setSize(1280 + left + right, 720 + top + bottom);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Metodos Numericos");
		setLocationRelativeTo(null);

		URL iconUrl = getClass().getResource("/icon.png");
		if (iconUrl != null) {
			ImageIcon icon = new ImageIcon(iconUrl);
			setIconImage(icon.getImage());
		} else {
			setIconImage(createIcon());
		}
		
        setJMenuBar(menuBar);
		menuOpciones(this);

		VistaDefault vista = new VistaDefault();
		add(vista);

		procesoInterno controlador = new procesoInterno(vista, listenerMenu);

		
		
		validate();
		repaint();
		miniTutorial();
	}

	private Image createIcon() {
		java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(16, 16, java.awt.image.BufferedImage.TYPE_INT_ARGB);
		java.awt.Graphics2D g = img.createGraphics();
		g.setColor(new java.awt.Color(44, 62, 80));
		g.fillRect(0, 0, 16, 16);
		g.setColor(java.awt.Color.WHITE);
		g.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 10));
		g.drawString("M", 3, 13);
		g.dispose();
		return img;
	}

	public void miniTutorial(){
		JOptionPane.showMessageDialog(this, 
		"1: Eliga el metodo en opciones\n" +
		"2: De click en 'aplicar metodo'\n" +
		"3: Presione  'Iniciar' hasta obtener el valor deseado\n" +
		"4: De click en 'Limpiar' si desea eliminar el contenido de toda la tabla", "Tutorial de uso", 1);
	}

	

	public void menuOpciones(Ventana ventana){

        JMenu opciones = new JMenu("Opciones");

        JMenu cambiarMetodo = new JMenu("Cambiar metodo");
		JMenu masOpciones = new JMenu("Mas opciones");
		
		opciones.add(cambiarMetodo);
		opciones.add(masOpciones);

		JMenuItem biseccion = new JMenuItem("Metodo de Biseccion");
		JMenuItem falsaPosicion = new JMenuItem("Falsa Posicion");
		JMenuItem newtonRaphson = new JMenuItem("Newton Raphson");

		JMenuItem salir = new JMenuItem("Salir");
		JMenuItem tutorial = new JMenuItem("Ver tutorial");

        cambiarMetodo.add(biseccion);
		cambiarMetodo.add(falsaPosicion);
		cambiarMetodo.add(newtonRaphson);

		
		masOpciones.add(tutorial);
		masOpciones.addSeparator();
		masOpciones.add(salir);
        
        menuBar.add(opciones);
		
		tutorial.addActionListener(listenerMenu);
		biseccion.addActionListener(listenerMenu);
		falsaPosicion.addActionListener(listenerMenu);
		newtonRaphson.addActionListener(listenerMenu);
		salir.addActionListener(listenerMenu);
	}
	

}