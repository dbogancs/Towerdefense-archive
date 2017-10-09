package game;

import graphics.GraphicsHandler;
import graphics.Image;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

class Menu extends JPanel
{
	// jatek attributomok

	private static JFrame frame = new JFrame("MyGame Menu");
	private static Image bg;
	
	private static Image[] diffImg = new Image[3];
	private static int diffImgIndex = 0;
	
	private static Image[] durationImg = new Image[3];
	private static int durImgIndex = 0;
	
	private static Menu menu;
	
	public static void main(String[] args) throws Exception 
	{
		
		try
		{
		
		//test mod: nincs menu, script van
		//menu class atadja a vezerlest a gamebol orokolt gametesternek
		if (args.length == 2 && args[0].equals("test"))
		{
			GameTester gameTester = new GameTester(game.Game.Difficulty.MEDIUM,50000);
		
			try{
				gameTester.runTest(args[1]);
			} catch (Exception ex){
				//ex.printStackTrace();
				System.out.println("An error occured. Terminating.");
			}
		} 
		else
		{
			// background image
			bg = new Image("menu");
			diffImg[0] = new Image("medium");
			diffImg[1] = new Image("hard");
			diffImg[2] = new Image("easy");
			
			durationImg[0] = new Image("3");
			durationImg[1] = new Image("5");
			durationImg[2] = new Image("1");
			
			// create menu
			menu = new Menu();
			menu.setLayout(null);
			menu.setBackground(Color.BLACK);
			frame.add(menu);
			
			
			// configure frame
			frame.setSize(800,600);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.addMouseListener(new MouseClickHandler());
		}
		} catch (Exception e)
		{
			
		}
		
	}
	
	// this is called after game over
	public static void showMenu(){
		frame.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) 
	{
		//super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		bg.draw(g2d, 0, 0);
		diffImg[diffImgIndex].draw(g2d, 0, 0);
		durationImg[durImgIndex].draw(g2d, 0, 0);	
	}

	private static long convertMinuteToDuration(int i) 
	{
		return i * 60 * 1000;
	}

	// start button
	private static void StartGomb() throws Exception
	{
		/*Game game = new Game(difficulty, duration, "map0");
		gamePanel.add(game.getGraphicsHandler(), "game");
		cardLayout.next(cardPanel);
		//frame.setSize(1000,1000);
		game.play();*/
	}

	private static class MouseClickHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			// PLAY
			if((e.getX() > 100) && (e.getX() < 190) &&
			   (e.getY() > 510) && (e.getY() < 550)){
				
				frame.setVisible(false);
				
				try {
					
					Game.Difficulty difficulty = game.Game.Difficulty.MEDIUM;;
					if (diffImgIndex == 0) difficulty = game.Game.Difficulty.MEDIUM;
					if (diffImgIndex == 1) difficulty = game.Game.Difficulty.HARD;
					if (diffImgIndex == 2) difficulty = game.Game.Difficulty.EASY;
					
					long duration = convertMinuteToDuration(3);;
					if (durImgIndex == 0) duration = convertMinuteToDuration(3);
					if (durImgIndex == 1) duration = convertMinuteToDuration(5);
					if (durImgIndex == 2) duration = convertMinuteToDuration(1);
					
					final Game game = new Game(difficulty, duration, "map0");
					
					new Thread(new Runnable() {
					    @Override
					    public void run() {
					    	game.play();
					    }
	
					   }).start();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			//difficulty
			if((e.getX() > 43) && (e.getX() < 238) &&
					   (e.getY() > 256) && (e.getY() < 328)){
				diffImgIndex = (diffImgIndex==2) ? 0 : diffImgIndex+1;
			}
			
			//duration
			if((e.getX() > 43) && (e.getX() < 238) &&
					   (e.getY() > 375) && (e.getY() < 439)){
				durImgIndex = (durImgIndex==2) ? 0 : durImgIndex+1;
			}
			
			menu.repaint();
	    }

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
