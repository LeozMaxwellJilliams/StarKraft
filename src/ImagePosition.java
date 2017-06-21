import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 

public class ImagePosition extends JFrame implements ActionListener{
	Timer myTimer;   
	ImagePanel image;
		
    public ImagePosition() {
		super("Move the Box");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);

		myTimer = new Timer(10, this);	 // trigger every 100 ms

		image = new ImagePanel(this);
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
		ImagePosition frame = new ImagePosition();		
    }
}

class ImagePanel extends JPanel implements KeyListener{
	private Image back, hover;
	private boolean []keys;
	private int boxx,boxy;
	private ImagePosition mainframe;
	
	
	public ImagePanel(ImagePosition i){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mainframe = i;
		boxx = 500;
		boxy = 100;
		addKeyListener(this);
        try {
    		back = new ImageIcon("story/story2.jpg").getImage();
    		hover = new ImageIcon("story/nextH.png").getImage();
		} 
		catch (Exception e) {
		}
	}
	
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainframe.start();
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
		
		System.out.println(boxx + "|" + boxy);
	}
	
	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    
    public void paintComponent(Graphics g){	
        g.drawImage(back,0, 0,null); 
        g.drawImage(hover, boxx - 6, boxy - 31,null);
        g.setColor(Color.red);
        g.drawRect(boxx, boxy, 1, 1); 
    }
}