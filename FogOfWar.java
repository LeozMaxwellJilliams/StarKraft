/**
 * @(#)Fog of war.java
 *
 *
 * @author 
 * @version 1.00 2015/6/4
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 

public class FogOfWar extends JFrame implements ActionListener{
	Timer myTimer;   
	FogPanel image;
		
    public FogOfWar() {
		super("Fog");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);

		myTimer = new Timer(10, this);	 // trigger every 100 ms

		image = new FogPanel(this);
		add(image);

		setResizable(false);
		setVisible(true);
    }
    
    public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt){
		image.move();
		image.repaint();			
	}

    public static void main(String[] arguments) {
		FogOfWar frame = new FogOfWar();		
    }
}

class FogPanel extends JPanel implements KeyListener{
	private Image map, unit;
	private boolean []keys;
	private int boxx,boxy;
	private FogOfWar mainframe;
	private int sightR;
	
	
	public FogPanel(FogOfWar i){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mainframe = i;
		boxx = 100;
		boxy = 50;
		sightR = 200;
		addKeyListener(this);
        try {
    		map = new ImageIcon("maps/map1.jpg").getImage();
    		unit = new ImageIcon("character1.png").getImage();
		} 
		catch (Exception e) {
		}
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainframe.start();
    }
    
    public void setAlpha(){
    	
    }
    
    public void move(){
		if(keys[KeyEvent.VK_RIGHT] ){
			boxx += 1;
		}
		if(keys[KeyEvent.VK_LEFT] ){
			boxx -= 1;
		}
		if(keys[KeyEvent.VK_UP] ){
			boxy -= 1;
		}
		if(keys[KeyEvent.VK_DOWN] ){
			boxy += 1;
		}
		
	//	System.out.println(boxx + "|" + boxy);
	}
	
	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    
    public void paintComponent(Graphics g){	
        g.drawImage(map, -700, -500, null); 
        g.drawImage(unit, boxx - 13, boxy - 20, null);
        g.setColor(Color.red);
        g.fillOval(boxx, boxy, 5, 5);
        g.setColor(new Color(0,0,0,100));
        g.fillOval(boxx - 50, boxy - 50, 100, 100);
    }
}