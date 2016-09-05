/*
 * Programacion Orientada a Objetos 
 * Erik Raul Mendoza Ruiz A01226009
 * Alberto Villagomez Vargas A01063154
 * Grupo 3
 * 09/04/14
 * 
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TiroParabolico extends JPanel implements Runnable,MouseListener,MouseMotionListener, ComponentListener{
	
	private int altura,
				esc,
				y,
				xi,
				yi,
				xf,
				yf,
				textAlt,
				x2;	
	private double valor,
	   			   velocidadx,
	   			   velocidady,
	   			   h,
	   			   tiempo,
	   			   aceleracion;
	private boolean movimiento,
					trayecto;
	
	private PanelControl pc;
	private PanelPrincipal pp;
	
	private Font fuente;
	
	private Image fondo, bird;
	
	public TiroParabolico(int valor, PanelControl controles, PanelPrincipal panel){
		this.setPreferredSize(new Dimension(800,600));
		altura=valor;
		esc=100;
		pc=controles;
		pp=panel;
		Thread hilo=new Thread(this);
		hilo.start();
		y=460;
		x2=50;
		textAlt=100;
		movimiento=false;
		trayecto=false;
		this.fuente= new Font("Comic sans", Font.BOLD, 14);
		this.fondo= new ImageIcon("fondo.jpg").getImage();
		this.bird= new ImageIcon("bird.png").getImage();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addComponentListener(this);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(this.fondo,0,0,this.getWidth(),this.getHeight(),null);
		g.drawLine(70,getHeight()-461,70,getHeight()-61);
		g.drawLine(70,getHeight()-61,getWidth()-50,getHeight()-61);
		//g.fillOval(get_X(),get_Y(), 40, 40);
		g.drawImage(this.bird, get_X(),get_Y(), 60, 60, null);
		this.repaint();
		if(getTrayectoria()==true){
			double temp=0.4;
			double trayectoriaY;
			double trayectoriaX;
			int tY,tX;
			for(int i=1;i<100;i++){
				g.setColor(Color.BLACK);
				trayectoriaY=((this.getHeight()-101)-4*(getAltura()*(100.0/esc)+(((this.getVy())*temp)+(0.5*getAceleracion()*temp*temp))))+20;
				tY=(int) trayectoriaY;
				trayectoriaX=(4*(this.getVx()*temp))+65;
				tX=(int) trayectoriaX;
				g.fillOval(tX,tY, 5, 5);
				temp++;
				}	
		}
		g.setColor(Color.WHITE);
		g.setFont(fuente);
		g.drawString("altura maxima: " + this.getTextAlt(), 10, 50);
		this.repaint();
	}	
	public void run() {
		try {
			while(true){
				tiempo=0.0;
				while(getMovimiento()){
					if(get_Y()<= this.getHeight()-101 && this.get_X() <= this.getWidth()-60 && this.get_X()>= 0){
						Thread.sleep(40);
						this.tiempo+=0.1;
						double pos=((this.getHeight()-101)-4*(getAltura()*(100.0/esc)+(((this.getVy())*tiempo)+(0.5*getAceleracion()*tiempo*tiempo))));
						this.y=(int) pos;
						this.set_Y(y);
						double x=(4*(this.getVx()*tiempo))+50;
						x2=(int) x;
						this.set_X(x2);
					}
					this.repaint();
				}
				if(getMovimiento() == false){
					double rese=((this.getHeight()-101)-4*(getAltura()*(100.0/this.esc)));
					int w=(int) rese;
					this.set_Y(w);
					this.set_X(50);
					this.repaint();
				}
			}
		} catch (InterruptedException e) {
			
		}	
	}
	public int getAltura(){
		return this.altura;
	}
	public void setAltura(int altura){
		this.altura=altura;
	}
	public int getEscala(){
		return this.esc;
	}
	public void setEscala(int esc){
		this.esc=esc;
	}
	public void setVx(double vx){
		this.velocidadx=vx;
	}
	public double getVx(){
		return this.velocidadx;
	}
	public void setVy(double vy){
		this.velocidady=vy;
	}
	public double getVy(){
		return this.velocidady;
	}
	public void setH(double altura){
		this.h=altura;
	}
	public double getH(){
		return this.h;
	}
	public void setAceleracion(double aceleracion){
		this.aceleracion=aceleracion;
	}
	public double getAceleracion(){
		return this.aceleracion;
	}
	public void setMovimiento(boolean movimiento){
		this.movimiento=movimiento;
	}
	public boolean getMovimiento(){
		return this.movimiento;
	}
	public void setTextAlt(int alt){
		this.textAlt=alt;
	}
	public int getTextAlt(){
		return this.textAlt;
	}
	public void set_Y(int w){
		this.y=w;
	}
	public int get_Y(){
		return this.y;
	}
	public void set_X(int w){
		this.x2=w;
	}
	public int get_X(){
		return this.x2;
	}
	public boolean getTrayectoria(){
		return this.trayecto;
	}
	public void setTrayectoria(boolean trayecto){
		this.trayecto=trayecto;
	}
	public double getTiempo(){
		return this.tiempo;
	}
	public void setTiempo(int t){
		this.tiempo=t;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		double velX=(double)((e.getX()-xi)*-1);
		double velY=(double)((e.getY()-yi));
		this.setVx(velX);
		this.setVy(velY);
		this.pc.setTextX(velX);
		this.pc.setTextY(velY);
		this.repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		xi=e.getX();
		yi=e.getY();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		xf=e.getX()-xi;
		yf=e.getY()-yi;
		double xF=(double)(xf*-1);
		double yF=(double)(yf);
		this.setVx(xF);
		this.setVy(yF);
		this.setMovimiento(true);
		this.repaint();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	public void setPC(PanelControl pc){
		this.pc = pc;
	}
	@Override
	public void componentResized(ComponentEvent e) {
		double pos=((this.getHeight()-101)-4*(getAltura()*(100.0/this.esc)));
		int y=(int) pos;
		this.set_Y(y);
		this.repaint();
		
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
