/*
 * Programacion Orientada a Objetos 
 * Erik Raul Mendoza Ruiz A01226009
 * Alberto Villagomez Vargas A01063154
 * Grupo 3
 * 09/04/14
 * 
 */
import java.awt.*;
import javax.swing.*;
public class PanelPrincipal extends JFrame {
	
	private TiroParabolico tp;
	
	private PanelControl controles;
	private int valor;
	
	public PanelPrincipal(){
		super("Tiro parabolico");
		this.setPreferredSize(new Dimension(1000,600));
		tp=new TiroParabolico(valor,controles,this);
		controles=new PanelControl(tp,this);
		this.tp.setPC(this.controles);
		this.add(this.tp, BorderLayout.CENTER);
		this.add(controles, BorderLayout.WEST);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		PanelPrincipal panel=new PanelPrincipal();
		panel.repaint();
	}
}

