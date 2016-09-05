/*
 * Programacion Orientada a Objetos 
 * Erik Raul Mendoza Ruiz A01226009
 * Alberto Villagomez Vargas A01063154
 * Grupo 3
 * 09/04/14
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EventObject;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PanelControl extends JPanel implements ChangeListener, ActionListener, ItemListener{

	private TiroParabolico tp;
	private PanelPrincipal pp;
	private JCheckBox trayectoria;
	private JSlider slider;
	private JLabel text,
				   etiqueta;
	private File leerFile,
	             escribirFile;
	private int altura,
	            esc,
	            a;
	private double valor,
	               velocidadX,
	               velocidadY,
	               h,
	               aceleracion;
	private JTextField vx,
	                   vy,
	                   altura_max,
	                   ac;
	private JButton abrir,
					guardar,
					lanzar,
					reset;
	private boolean movimiento,
					direccion;
	public PanelControl(TiroParabolico tiro, PanelPrincipal panel){
		super();
		this.setPreferredSize(new Dimension(180,50));
		this.tp=tiro;
		this.pp=panel;
		esc=100;
		a=0;

		//Crear los JLabel y los agrega al PanelControl
		text=new JLabel("Vx");
		this.add(this.text);
		this.text.setBounds(60, 30, 20, 30);

		text=new JLabel("Vy");
		this.add(this.text);
		this.text.setBounds(60, 80, 20, 30);

		text=new JLabel("Altura Max");
		this.add(this.text);
		this.text.setBounds(15, 130, 65,30);

		text=new JLabel("Aceleracion");
		this.add(this.text);
		this.text.setBounds(10, 180, 70, 30);

		String tex=Integer.toString(altura);
		etiqueta=new JLabel(0 + " altura");
		this.add(this.etiqueta);
		this.etiqueta.setBounds(90, 280, 70, 30);

		//Crea los JTextField
		this.vx=new JTextField();
		this.vx.setText("10");
		this.vy=new JTextField("10");
		this.altura_max=new JTextField("100");
		this.ac=new JTextField("-9.8");

		//Crea los componentes
		this.slider=new JSlider(JSlider.VERTICAL,0,100, 0);
		this.trayectoria=new JCheckBox("Dibujar trayectoria");
		this.abrir= new JButton("Abrir");
		this.guardar= new JButton("Guardar");
		this.lanzar= new JButton("Lanzar");
		this.reset= new JButton("Reset");

		//Agrega los componentes al PanelControl
		this.add(this.vx);
		this.add(this.vy);
		this.add(this.altura_max);
		this.add(this.ac);
		this.add(this.slider);
		this.add(this.trayectoria);
		this.add(this.abrir);
		this.add(this.guardar);
		this.add(this.lanzar);
		this.add(this.reset);

		//Acomoda los componentes en PanelControl
		this.vx.setBounds(100, 35, 50, 20);
		this.vy.setBounds(100, 85, 50, 20);
		this.altura_max.setBounds(100, 135, 50, 20);
		this.ac.setBounds(100, 185, 50, 20);
		this.slider.setBounds(50, 220, 30,200);
		this.trayectoria.setBounds(10, 440, 150, 20);
		this.lanzar.setBounds(5, 470, 80, 30);
		this.reset.setBounds(95, 470, 80, 30);
		this.abrir.setBounds(5, 510, 80, 30);
		this.guardar.setBounds(95, 510, 80, 30);
		setLayout(null);

		//Escuchar los Eventos
		this.slider.addChangeListener(this);
		this.trayectoria.addChangeListener(this);
		this.altura_max.addActionListener(this);
		this.vx.addActionListener(this);
		this.vy.addActionListener(this);
		this.ac.addActionListener(this);
		this.abrir.addActionListener(this);
		this.guardar.addActionListener(this);
		this.lanzar.addActionListener(this);
		this.reset.addActionListener(this);	
		this.trayectoria.addItemListener(this);


		this.tp.setVx(Double.parseDouble(vx.getText()));
		this.tp.setVy(Double.parseDouble(vy.getText()));
		this.tp.setAceleracion(Double.parseDouble(ac.getText()));
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawLine(179, 0, 179,this.getHeight());
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==this.lanzar){
			try {
				this.tp.setH(valor);
				this.tp.setVx(Double.parseDouble(vx.getText()));
				this.tp.setVy(Double.parseDouble(vy.getText()));
				this.tp.setAceleracion(Double.parseDouble(ac.getText()));
				this.tp.setMovimiento(true);
				this.tp.repaint();
				this.setVisible(true);
			} catch (Exception h) {
				JOptionPane.showMessageDialog(null,"Dejaste uno de los campos vacios","Opcion Invalido",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == this.reset){
			this.tp.setMovimiento(false);
		}
		else if(e.getSource() == this.abrir){
			try{
				JFileChooser leer = new JFileChooser();
				leer.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int opcionLeer = leer.showOpenDialog(this);
				if(opcionLeer == JFileChooser.APPROVE_OPTION){
					this.leerFile = leer.getSelectedFile();
					try {
						BufferedReader br=new BufferedReader(new FileReader(leerFile));
						String linea, altura, posInc;
						StringTokenizer st;
						double x1,y1,acel;
						int altInc;
						while((linea=br.readLine()) != null){
							st=new StringTokenizer(linea,",");
							x1=Double.parseDouble(st.nextToken());
							this.setTextX(x1);
							this.tp.setVx(x1);
							y1=Double.parseDouble(st.nextToken());
							this.setTextY(y1);
							this.tp.setVy(y1);
							altura=st.nextToken();
							altura_max.selectAll();
							esc=Integer.parseInt(altura);
							tp.setEscala(esc);
							slider.setMaximum(esc);
							this.setAlturaMax(esc);
							this.tp.setTextAlt(esc);
							posInc=st.nextToken();
							altInc=Integer.parseInt(posInc);
							tp.setAltura(altInc);
							double pos=(460-4*(altInc*(100.0/this.esc)));
							int y=(int) pos;
							tp.set_Y(y);
							this.etiqueta.setText(altInc + " altura");
							this.slider.setValue(altInc);
							acel=Double.parseDouble(st.nextToken());
							this.setTextAceleracion(acel);
							this.tp.setAceleracion(acel);
						}
						br.close();
						tp.repaint();

					} catch (FileNotFoundException f) {
						// TODO Auto-generated catch block
					} catch (IOException f) {
						// TODO Auto-generated catch block
					}
				}
			}
			catch (HeadlessException e1) {
				// TODO Auto-generated catch block
			}
		}
		else if(e.getSource() == this.guardar){
			try{
				JFileChooser escribir = new JFileChooser();
				escribir.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int opcionEscribir = escribir.showSaveDialog(this);
				if(opcionEscribir == JFileChooser.APPROVE_OPTION){
					this.escribirFile = escribir.getSelectedFile();
				}
				try {
					PrintWriter pw= new PrintWriter(new FileWriter(escribirFile +".txt"));
					pw.print(this.tp.getVx() + ",");
					pw.print(this.tp.getVy() + ",");
					pw.print(this.getAlturaMax() + ",");
					pw.print(this.tp.getAltura() + ",");
					pw.print(this.tp.getAceleracion() + ",");
					pw.close();
				} catch (IOException f) {
					// TODO Auto-generated catch block
				}
			} 
			catch (HeadlessException e1) {
				// TODO Auto-generated catch block
			}	
		}
		else if(e.getSource() == this.vx){
			try {
				this.tp.setVx(Double.parseDouble(vx.getText()));
			} catch (Exception h) {
				JOptionPane.showMessageDialog(null,"El valor que ingreso es invalido","Opcion Invalido",JOptionPane.ERROR_MESSAGE);
			}	
		}
		else if(e.getSource() == this.vy){
			try {
				this.tp.setVy(Double.parseDouble(vy.getText()));
			} catch (Exception h) {
				JOptionPane.showMessageDialog(null,"El valor que ingreso es invalido","Opcion Invalido",JOptionPane.ERROR_MESSAGE);
			}	
		}
		else if(e.getSource() == this.ac){
			try {
				this.tp.setAceleracion(Double.parseDouble(ac.getText()));
			} catch (Exception h) {
				JOptionPane.showMessageDialog(null,"El valor que ingreso es invalido","Opcion Invalido",JOptionPane.ERROR_MESSAGE);
			}	
		}
		else if(e.getSource() == this.altura_max){
			try {
				if((Integer.parseInt(altura_max.getText())) >= 1){
					int alto=Integer.parseInt(altura_max.getText());
					String text = altura_max.getText();
					altura_max.selectAll();
					esc=Integer.parseInt(text);
					tp.setEscala(esc);
					slider.setMaximum(esc);
					tp.repaint();
					this.tp.setTextAlt(Integer.parseInt(altura_max.getText()));
				}
				else{
					JOptionPane.showMessageDialog(null,"La altura maxima no puede ser menor que 1","Opcion Invalido",JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception h) {
				JOptionPane.showMessageDialog(null,"Ingrese un valor entero","Opcion Invalido",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public void stateChanged(ChangeEvent e) {
		if((e.getSource())==this.slider){
			altura= slider.getValue();
			tp.setAltura(altura);
			double pos=((this.getHeight()-101)-4*(altura*(100.0/this.esc)));
			int y=(int) pos;
			tp.set_Y(y);
			tp.repaint();
			valor= slider.getValue();
			int alt=(int) valor;
			this.etiqueta.setText(alt + " altura");
		}
	}
	public void itemStateChanged(ItemEvent e) {
		if(this.trayectoria.isSelected() == true){
			this.tp.setTrayectoria(true);
			this.tp.repaint();
		}
		else{
			this.tp.setTrayectoria(false);
			this.tp.repaint();
		}
	}
	public void setTextX(double x){
		this.vx.setText(String.valueOf(x));
	}
	public void setTextY(double y){
		this.vy.setText(String.valueOf(y));
	}
	public void setAlturaMax(int a){
		this.altura_max.setText(String.valueOf(a));
	}
	public void setTextAceleracion(double aceleration){
		this.ac.setText(String.valueOf(aceleration));
	}
	public int getAlturaMax(){
		int am=Integer.parseInt(this.altura_max.getText());
		return am; 
	}
}