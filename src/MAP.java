/**
 * @(#)MAP.java
 *
 *
 * Andy Li 
 * this class is for all the map movements and the mini map postions, this class also contains the funtion of transition between levels. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*;
import java.util.*;
import java.applet.*;
import javax.sound.sampled.AudioSystem;

public class MAP extends JFrame implements ActionListener{
	javax.swing.Timer myTimer;  
	GamePanel game;
	MainMenu menu;
	MenuPanel MP;
	AudioClip back;
	
    public MAP() {
    	super("Star Kraft");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);

		myTimer = new javax.swing.Timer(10, this);	 // trigger every 100 ms

		menu = new MainMenu(this);
		MP = menu.getMenuPanel();
		game = new GamePanel(this, menu, MP);
		add(game);
		
		try {
    			back = Applet.newAudioClip(getClass().getResource("music/lvl1.wav"));
    	}
    
    	catch(Exception ex) {
       	 	System.out.println("Error with playing sound.");
    	}
		
		setResizable(false);
	//	setVisible(false);
    }
    
    public void StartM(){
    	back.play();
    }
    
	public void start(){
		myTimer.start();
	}
	
    public void actionPerformed(ActionEvent evt){
    	Object source = evt.getSource();
		if(source == myTimer){
			game.move();
			game.repaint();
		}			
	}
	public static void main(String[] arguments) {
		MAP frame = new MAP();		
    } 
}

class GamePanel extends JPanel implements MouseMotionListener, MouseListener{
	private int mx,my, mapx,mapy, rx,ry, Lvl, storyN, transD , tempX, tempY;
	private Image toolBar, rect, Cmap, Cmini, Bmap, commandI, pauseH, pauseBox, menuH, resumeH, storyPic;
	public double transitionShade;
	private boolean transition, gameP, commanderChange;
	private Rectangle pauseB, menuB, resumeB;
	private int[]x;
	private int[]y;
	private MAP m;
	private MainMenu MM;
	private MenuPanel MP;
	
	public GamePanel(MAP m, MainMenu Mmenu, MenuPanel MP){
		addMouseMotionListener(this);
		addMouseListener(this);
		this.m = m;
		MM = Mmenu;
		this.MP = MP;
		transitionShade = 0.0;//clear screen for fade effect
		
		x = new int[4];
		y = new int[4];
		
		Lvl = 1; // increases as the level increase
		storyN = 1; // number of the story that matches the level
		
		try{
			Cmap = new ImageIcon("maps/map" + Lvl + ".jpg").getImage();
			Cmini = new ImageIcon("maps/map" + Lvl + "mini.jpg").getImage();
			toolBar = new ImageIcon("toolbar/toolbar1.png").getImage();
			rect = new ImageIcon("toolbar/rect.png").getImage();
			pauseH = new ImageIcon("toolbar/pauseH.png").getImage();
			pauseBox = new ImageIcon("toolbar/pauseB.png").getImage();
			menuH = new ImageIcon("toolbar/menuH.png").getImage();
			resumeH = new ImageIcon("toolbar/resumeH.png").getImage();
		}
		catch(Exception ex){
			System.out.println("Error with loading file.");	
		}
		
		pauseB = new Rectangle(952, 515, 50, 25);
		menuB = new Rectangle(456, 349, 82, 40);
		resumeB = new Rectangle(456, 284, 82, 40);	
		
		commanderChange = true;
		transition = false; // the transition is set to false when the game starts
		gameP = false; // the pause is set to false in order to play the game
		transD = 1; // the interval of the transition shade when it is doing its job
		
		loadMapData();
		mapx = x[Lvl - 1];
		mapy = y[Lvl - 1];

	}
	
    public void addNotify() {
        super.addNotify();
        m.start();
    }
    
    public void CommanderI(){
    	if(commanderChange){
    		try{
				if(MM.getC().equals("ken")){
					commandI = new ImageIcon("toolbar/kennethC.png").getImage();
				}
				else if(MM.getC().equals("sha")){
					commandI = new ImageIcon("toolbar/shahirC.png").getImage();
				}
				else if(MM.getC().equals("fra")){
					commandI = new ImageIcon("toolbar/frankC.png").getImage();
				}
				else if(MM.getC().equals("leo")){
					commandI = new ImageIcon("toolbar/leonC.png").getImage();
				}
				else if(MM.getC().equals("and")){
					commandI = new ImageIcon("toolbar/andyC.png").getImage();
				}
			}
			catch(Exception ex){
				System.out.println("Error with loading file.");	
			}
			commanderChange = false;
    	}
    }
    
    public void loadMapData(){
    	Scanner infile = null;
    	try{
    		infile = new Scanner(new File("maps/mapinfo.txt"));
    	}
    	catch(IOException ex){
    		System.out.println(ex);
    	}
    	int n;
    	String line;
    	n = Integer.parseInt(infile.nextLine());
    	for(int i = 0; i < n; i ++){
    		line = infile.nextLine();
    		String[]info = line.split(",");
    		x[i] = Integer.parseInt(info[1]);
    		y[i] = Integer.parseInt(info[2]);
    	}
    }
    
    public void MapC(){
    	if (Lvl >= 4){
    		Lvl = 1;
    	}
    	else{
    		Lvl ++;
    	}
    	mapx = x[Lvl - 1];
    	mapy = y[Lvl - 1];
    	rx = (int)((120.4 * mapx) / Cmap.getWidth(null)) * -1 + 14;
		ry = (int)((147.4 * mapy) / Cmap.getHeight(null)) * -1 + 530;
    	try{
			Cmap = new ImageIcon("maps/map" + Lvl + ".jpg").getImage();
			Cmini = new ImageIcon("maps/map" + Lvl + "mini.jpg").getImage();
		}
		catch(Exception ex){
			System.out.println("Error with loading file.");	
		}
    }
    
    public void storyC(){
    	try{
			storyPic = new ImageIcon("story/story" + storyN + ".jpg").getImage();
			Cmini = new ImageIcon("maps/map" + Lvl + "mini.jpg").getImage();
		}
		catch(Exception ex){
			System.out.println("Error with loading file.");	
		}
    }
	
    public void move() {
    	if(transition == false){
			if(mx > 950 && mapx > -1 * (Cmap.getWidth(null) - 1000)){
				mapx -= 5;
			}
			if(mx < 50 && mapx < 0){
				mapx += 5;
			}
			if(my > 650 && mapy > -1 * (Cmap.getHeight(null) - 700)){
				mapy -= 5;
			}
			if(my < 50 && mapy < -5){
				mapy += 5;
			}
			rx = (int)((120.4 * mapx) / Cmap.getWidth(null)) * -1 + 14;
			ry = (int)((147.4 * mapy) / Cmap.getHeight(null)) * -1 + 530;
    	}
    }
    
    public void transitionPaint(Graphics t){
    	if(transitionShade > 255.0){
				transD *= -1;
				MapC();
			}
			if(transitionShade < 0){
				transD *= -1;
				transition = false;
			}
			transitionShade += transD;
			t.setColor(new Color(0,0,0,(int)(Math.max(0,Math.min(255,transitionShade)))));
			t.fillRect(0,0,1000,700);
    }
    
   /* public void storyPanel(Graphics s){
    	if(transitionShade > 255.0){
				transD *= -1;
				MapC();
			}
			if(transitionShade < 0){
				transD *= -1;
				transition = false;
			}
			transitionShade += transD;
			t.setColor(new Color(0,0,0,(int)(Math.max(0,Math.min(255,transitionShade)))));
			t.fillRect(0,0,1000,700);
    }*/
    
    public void paused(Graphics p){
    	p.drawImage(pauseBox, 300, 200, null);
    	if(resumeB.contains(mx, my)){
    		p.drawImage(resumeH, 456, 284, null);
    	}
    	else if(menuB.contains(mx, my)){
    		p.drawImage(menuH, 456, 349, null);
    	}
    }
    
    public void play(Graphics y){
    	y.drawImage(Cmap, mapx, mapy, null); 
		y.drawImage(toolBar, 0, 485, null);
		y.drawImage(Cmini, 15, 535, null);
		y.drawImage(rect, rx, ry, null);
		y.drawImage(commandI, 722, 565, null);
		if(pauseB.contains(mx,my)){
			y.drawImage(pauseH, 962, 515, null); 
		}
		if(transition){// fade out and fade in effect
			transitionPaint(y);
		}
    }
    
    public void paintComponent(Graphics g){
    	if(gameP){
    		paused(g);
    	}
    /*	else if(storyTime){
    		story(g);
    	}*/
    	else{
    		CommanderI();
    		play(g);
    	}
    }
    
    
    // ------------ MouseListener ------------------------------------------
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}   
    public void mouseClicked(MouseEvent e){}  	 
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){
    	// trigger the gamepause box
    	if(pauseB.contains(mx,my) && transition == false){
    		tempX = mx;
    		tempY = my;
    		gameP = true;
    	}
    	else{
    		transition = true;
    	}
    	// gamepause funtions
    	if(gameP){
    		if(resumeB.contains(mx, my)){
    			mx = tempX;
    			my = tempY;
    			gameP = false;
    			transition = false;
    		}
    		else if(menuB.contains(mx, my)){
    			gameP = false;
    			transition = false;
    			commanderChange = true;
    			MP.menuC("mainmenu");
    			m.setVisible(false);
    			MM.setVisible(true);
    		}
    	}
    	
    } 
    	
    // ---------- MouseMotionListener ------------------------------------------
    public void mouseDragged(MouseEvent e){}
    public void mouseMoved(MouseEvent e){
    	mx = e.getX();
		my = e.getY();
    }   
}