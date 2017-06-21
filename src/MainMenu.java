/**
 * @(#)MainMenu.java
 *
 *
 * @author 
 * @version 1.00 2015/5/6
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

public class MainMenu extends JFrame implements ActionListener{
	javax.swing.Timer myTimer;  
	MenuPanel menuP;
	MAP map;
	AudioClip menuM;

    public MainMenu(MAP map) {
    	super("MainMenu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);

		myTimer = new javax.swing.Timer(10, this);	 // trigger every 100 ms
		
		this.map = map;
		menuP = new MenuPanel(this, map);
		add(menuP);
		
		try {
    			menuM = Applet.newAudioClip(getClass().getResource("music/mainmenu.wav"));
    	}
    	catch(Exception ex) {
       	 	System.out.println("Error with playing sound.");
    	}
    	
		StartM();

		setResizable(false);
		setVisible(true);
    }
    
    public MenuPanel getMenuPanel(){
    	return menuP;
    }
    
    public String getC(){
    	return menuP.getC();
    }
 
    public void StartM(){
    	menuM.play();
    }
    
    public void StopM(){
    	menuM.stop();
    }
    
    public void start(){
		myTimer.start();
	}
		
	public void actionPerformed(ActionEvent evt){
    	Object source = evt.getSource();
		if(source == myTimer){
			menuP.repaint();
		}			
	}
	
	public static void main(String[] arguments) {
		MainMenu frame = new MainMenu(null);		
    }
}

class MenuPanel extends JPanel implements MouseMotionListener, MouseListener{
	
	private double transitionShade;
	private boolean transition;
	private int transD, introP, mx, my, HoverNum, MenuNum;
	private MainMenu m;
	private MAP map;
	private ArrayList<Image> intro = new ArrayList<Image>();
	private Image mainM, Chover, CmenuP, backH;
	private Image startH, comH;
	private String Cmenu, commander;
	private Rectangle CampR, QuickR, InstR, CredR, QuitR, BackR, StartR, KenR, ShaR, FraR, LeoR, AndR;
	
	public MenuPanel(MainMenu m, MAP map){
		addMouseMotionListener(this);
		addMouseListener(this);
		this.m = m;
		this.map = map;
		
		transitionShade = 0.0;//clear screen for fade effect
		transition = true; // the transition is set to false when the game starts
		transD = -1;
		introP = 0;
		
		Cmenu = "mainmenu";
		commander = "";
		
		CampR = new Rectangle(185, 315, 318, 62);			
    	QuickR = new Rectangle(523, 313, 318, 62);
    	InstR = new Rectangle(183, 402, 318, 62);
    	CredR = new Rectangle(522, 400, 318, 62);				//Setting pop-up image container
    	QuitR = new Rectangle(355, 492, 318, 62);
    	BackR = new Rectangle(19, 58, 82, 40);
    	StartR = new Rectangle(336, 533, 318, 62);
    	
    	KenR = new Rectangle(56, 243, 171, 247);
    	ShaR = new Rectangle(234, 244, 171, 247);
    	FraR = new Rectangle(412, 246, 171, 247);
    	LeoR = new Rectangle(591, 245, 171, 247);
    	AndR = new Rectangle(772, 245, 171, 247);
	
		try{
			for(int i = 0; i < 2; i ++){
				intro.add(new ImageIcon("menu pics/intro" + i + ".jpg").getImage());
			}
			backH = new ImageIcon("menu pics/backH.png").getImage();
			mainM = new ImageIcon("menu pics/mainmenu.jpg").getImage();
			comH = new ImageIcon("menu pics/box2H.png").getImage();
			startH = new ImageIcon("menu pics/startH.png").getImage();
			intro.add(mainM);
				
		}
		catch(Exception ex){
			System.out.println("Error with loading file.");	
		}
	}
	
	public String getC(){
		return commander;
	}
	
	public void menuC(String menuN){
		Cmenu = menuN;
	}
	
	public void changeHover(){
		try{
			Chover = new ImageIcon("menu pics/hover" + HoverNum + ".png").getImage();
		}
		catch(Exception ex){
			System.out.println("Error with loading file.");	
		}
	}
	
	
	public void changeMenu(){
		try{
			CmenuP = new ImageIcon("menu pics/menu" + MenuNum + ".jpg").getImage();
		}
		catch(Exception ex){
			System.out.println("Error with loading file.");	
		}
	}
	
	public void clearCommander(){
		System.out.println(commander);
		commander = "";
	}
	
	public void addNotify() {
        super.addNotify();
        m.start();
    }
	
	public void intro(Graphics t){
    	if(transitionShade > 255.0){
				transD *= -1;
				introP ++;
			}
			if(transitionShade < 0){
				transD *= -1;
				if(introP >= 2){
					transition = false;
				}
			}
			transitionShade += transD;
			t.setColor(new Color(0,0,0,(int)(Math.max(0,Math.min(255,transitionShade)))));
			t.fillRect(0,0,1000,700);
    }
    
    public void menu(Graphics m){
    	// painting hover pics on the screen on the menu only if the screen is on main menu
    	if (Cmenu.equals("mainmenu")){
    		m.drawImage(mainM, 0, 0, null);
    		if(CampR.contains(mx,my)){
    			HoverNum = 0;
    			changeHover();
    			m.drawImage(Chover, 185, 315,null);
    		}
    		else if(QuickR.contains(mx,my)){
    			HoverNum = 1;
    			changeHover();
    			m.drawImage(Chover, 523, 312, null);
    		}
    		else if(InstR.contains(mx,my)){
    			HoverNum = 2;
    			changeHover();
    			m.drawImage(Chover, 183, 402, null);
    		}
    		else if(CredR.contains(mx,my)){
    			HoverNum = 3;
    			changeHover();
    			m.drawImage(Chover, 522, 400, null);
    		}
    		else if(QuitR.contains(mx,my)){
    			HoverNum = 4;
    			changeHover();
    			m.drawImage(Chover, 355, 492, null);
    		} 
    		else{
    			m.drawImage(mainM, 0, 0, null);
    		}
    	}
    	// drawing the menus on to the screen whenever its been called
    	else if(Cmenu.equals("campagin")){
    		MenuNum = 0;
    		changeMenu();
    		m.drawImage(CmenuP, 0, 0, null);
    		// all the hover box action
    		if(BackR.contains(mx, my)){
    			m.drawImage(backH, 19, 58, null);;
    		}
    		else if(StartR.contains(mx, my)){
    			m.drawImage(startH, 336, 533, null);
    		}
    		else if(KenR.contains(mx, my)){
    			m.drawImage(comH, 56, 243, null);
    		}
    		else if(ShaR.contains(mx, my)){
    			m.drawImage(comH, 234, 244, null);
    		}
    		else if(FraR.contains(mx, my)){
    			m.drawImage(comH, 412, 246, null);
    		}
    		else if(LeoR.contains(mx, my)){
    			m.drawImage(comH, 591, 244, null);
    		}
    		else if(AndR.contains(mx, my)){
    			m.drawImage(comH, 772, 244, null);
    		}
    		// all the cammander selection action
    		if (commander.equals("ken")){
    			m.drawImage(comH, 56, 243, null);
    		}
    		else if (commander.equals("sha")){
    			m.drawImage(comH, 234, 244, null);
    		}
    		else if (commander.equals("fra")){
    			m.drawImage(comH, 412, 246, null);
    		}
    		else if (commander.equals("leo")){
    			m.drawImage(comH, 591, 245, null);
    		}
    		else if (commander.equals("and")){
    			m.drawImage(comH, 772, 245, null);
    		}
    	}
    	else if(Cmenu.equals("quickplay")){
    		MenuNum = 1;
    		changeMenu();
    		m.drawImage(CmenuP, 0, 0, null);
    		// all the hover box action
    		if(BackR.contains(mx, my)){
    			m.drawImage(backH, 19, 58, null);;
    		}
    		else if(StartR.contains(mx, my)){
    			m.drawImage(startH, 336, 533, null);
    		}
    		else if(KenR.contains(mx, my)){
    			m.drawImage(comH, 56, 243, null);
    		}
    		else if(ShaR.contains(mx, my)){
    			m.drawImage(comH, 234, 244, null);
    		}
    		else if(FraR.contains(mx, my)){
    			m.drawImage(comH, 412, 246, null);
    		}
    		else if(LeoR.contains(mx, my)){
    			m.drawImage(comH, 591, 244, null);
    		}
    		else if(AndR.contains(mx, my)){
    			m.drawImage(comH, 772, 244, null);
    		}
    		// all the cammander selection action
    		if (commander.equals("ken")){
    			m.drawImage(comH, 56, 243, null);
    		}
    		else if (commander.equals("sha")){
    			m.drawImage(comH, 234, 244, null);
    		}
    		else if (commander.equals("fra")){
    			m.drawImage(comH, 412, 246, null);
    		}
    		else if (commander.equals("leo")){
    			m.drawImage(comH, 591, 245, null);
    		}
    		else if (commander.equals("and")){
    			m.drawImage(comH, 772, 245, null);
    		}
    	}
    	else if(Cmenu.equals("instruction")){
    		MenuNum = 2;
    		changeMenu();
    		m.drawImage(CmenuP, 0, 0, null);
    		if(BackR.contains(mx, my)){
    			m.drawImage(backH, 19, 58, null);;
    		}
    	}
    	else if(Cmenu.equals("credits")){
    		MenuNum = 3;
    		changeMenu();
    		m.drawImage(CmenuP, 0, 0, null);
    		if(BackR.contains(mx, my)){
    			m.drawImage(backH, 19, 58, null);;
    		}
    	}	
    
    }
    public void paintComponent(Graphics g){
	/*	if(transition){
			g.drawImage(intro.get(introP), 0, 0, null);
			intro(g);
		}
		else{
			menu(g);
		}*/
		menu(g);
	
    }
    
     // ------------ MouseListener ------------------------------------------
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}   
    public void mouseClicked(MouseEvent e){}  	 
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){
    	int mx = e.getX();
    	int my = e.getY();
    	if (Cmenu.equals("mainmenu")){
    		if(CampR.contains(mx,my)){
    			Cmenu = "campagin";
    		}
    		else if(QuickR.contains(mx,my)){
    			Cmenu = "quickplay";
    		}
    		else if(InstR.contains(mx,my)){
    			Cmenu = "instruction";
    		}
    		else if(CredR.contains(mx,my)){
    			Cmenu = "credits";
    		}
    		else if(QuitR.contains(mx,my)){
    			System.exit(0);
    		} 
    	}
    	else if(Cmenu.equals("campagin")){
    		if(BackR.contains(mx, my)){
    			Cmenu = "mainmenu";
    			commander = "";
    		}
    		else if(KenR.contains(mx, my)){
    			commander = "ken";
    		}
    		else if(ShaR.contains(mx, my)){
    			commander = "sha";
    		}
    		else if(FraR.contains(mx, my)){
    			commander = "fra";
    		}
    		else if(LeoR.contains(mx, my)){
    			commander = "leo";
    		}
    		else if(AndR.contains(mx, my)){
    			commander = "and";
    		}
    		// you can't start the game without selecting a commander
    		if (!commander.equals("")){
    			if(StartR.contains(mx, my)){
    				m.setVisible(false);
    				m.StopM();
    				map.setVisible(true);
    				map.StartM();
    			}
    		}
    	}
    	else if(Cmenu.equals("quickplay")){
    		if(BackR.contains(mx, my)){
    			Cmenu = "mainmenu";
    			commander = "";
    		}
    		else if(KenR.contains(mx, my)){
    			commander = "ken";
    		}
    		else if(ShaR.contains(mx, my)){
    			commander = "sha";
    		}
    		else if(FraR.contains(mx, my)){
    			commander = "fra";
    		}
    		else if(LeoR.contains(mx, my)){
    			commander = "leo";
    		}
    		else if(AndR.contains(mx, my)){
    			commander = "and";
    		}
    		// you can't start the game without selecting a commander
    		if (!commander.equals("")){
    			if(StartR.contains(mx, my)){
    				m.setVisible(false);
    				m.StopM();
    				map.setVisible(true);
    				map.StartM();
    			}
    		}
    	}
    	else{
    		if(BackR.contains(mx, my)){
    			Cmenu = "mainmenu";
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