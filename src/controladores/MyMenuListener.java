package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import metodosNumericos.Ventana;
import visual.VistaDefault;

public class MyMenuListener implements ActionListener {
	
	private Ventana ventana;
	private String metodo = "";
	VistaDefault vista = new VistaDefault();

	public MyMenuListener(){

	}
	public MyMenuListener(Ventana ventana) {
		this.ventana = ventana;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()) {
			case "Metodo de Biseccion":
				metodo = "Metodo de Biseccion";
				break;
			case "Falsa Posicion":
				metodo = "Falsa Posicion";
				break;

			case "Newton Raphson":
				metodo = "Newton Raphson";
				
				break;
			
			case "Ver tutorial":
				ventana.miniTutorial();
			break;
			
			case "Salir":
				System.exit(0);
		}
		
		if(metodo != ""){
			JOptionPane.showMessageDialog(ventana, "Metodo seleccionado: " + metodo, "Aviso", JOptionPane.INFORMATION_MESSAGE);
		}

		
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	
}
