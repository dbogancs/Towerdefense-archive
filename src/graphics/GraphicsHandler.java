package graphics;


import game.Game;
import game.InputHandler;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphicsHandler extends JPanel
{
	
	private static HashMap<String,Image> imgLib = new HashMap<String,Image>();
	private static Game game;
	private static JFrame frame;
	
	public static GraphicsHandler init(Game g)
	{
		game = g;
		
		try 
		{
			// background image
			imgLib.put("background", new Image("background"));
			
			// game over image
			imgLib.put("gameover", new Image("gameover"));
			
			// no image
			imgLib.put("noimg",new Image("noimg"));
			
			// mount doom and fields
			imgLib.put("md",new Image("md"));
			imgLib.put("road",new Image("field3"));
			imgLib.put("nroad",new Image("field1"));
			
			// bullets
			imgLib.put("bullet_1", new Image("bullet_1"));
			imgLib.put("bullet_2", new Image("bullet_2"));
			
			// obstacles
			imgLib.put("obst_p", new Image("obst_p"));
			imgLib.put("obst_r", new Image("obst_r"));
			imgLib.put("obst_w", new Image("obst_w"));
			
			// towers
			imgLib.put("tower_1", new Image("tower_1"));
			imgLib.put("tower_2", new Image("tower_2"));
			imgLib.put("tower_3", new Image("tower_3"));
			
			// hobbit
			imgLib.put("h_up",new Image("h_up"));
			imgLib.put("h_down",new Image("h_down"));
			imgLib.put("h_left",new Image("h_left"));
			imgLib.put("h_right",new Image("h_right"));		
			
			// elf
			imgLib.put("e_up",new Image("e_up"));
			imgLib.put("e_down",new Image("e_down"));
			imgLib.put("e_left",new Image("e_left"));
			imgLib.put("e_right",new Image("e_right"));
			
			imgLib.put("e_aup",new Image("e_aup"));
			imgLib.put("e_adown",new Image("e_adown"));
			imgLib.put("e_aleft",new Image("e_aleft"));
			imgLib.put("e_aright",new Image("e_aright"));
			
			// dwarf
			imgLib.put("d_up",new Image("d_up"));
			imgLib.put("d_down",new Image("d_down"));
			imgLib.put("d_left",new Image("d_left"));
			imgLib.put("d_right",new Image("d_right"));
			
			imgLib.put("d_aup",new Image("d_aup"));
			imgLib.put("d_adown",new Image("d_adown"));
			imgLib.put("d_aleft",new Image("d_aleft"));
			imgLib.put("d_aright",new Image("d_aright"));
			
			// human
			imgLib.put("hu_up",new Image("hu_up"));
			imgLib.put("hu_down",new Image("hu_down"));
			imgLib.put("hu_left",new Image("hu_left"));
			imgLib.put("hu_right",new Image("hu_right"));
			
			imgLib.put("hu_aup",new Image("hu_aup"));
			imgLib.put("hu_adown",new Image("hu_adown"));
			imgLib.put("hu_aleft",new Image("hu_aleft"));
			imgLib.put("hu_aright",new Image("hu_aright"));
			
			imgLib.put("speed", new Image("speed"));
		}
		catch (IOException e) 
		{
			System.out.println("An image is missing...");
			e.printStackTrace();
			System.exit(-1);
		}
		
		GraphicsHandler graphicsHandler = new GraphicsHandler();
		
		frame = new JFrame("MyGame");
		graphicsHandler.setBackground(Color.BLACK);
		frame.add(graphicsHandler);
		frame.setSize(1024,768);
		frame.setLocation(0, 0);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addMouseListener(new InputHandler.MouseClickHandler());
		frame.addMouseMotionListener(new InputHandler.MouseMotionHandler());
		frame.addKeyListener(new InputHandler.KeyHandler());

		return graphicsHandler;
	}
	
	public static Image[] getHobbitImgs()
	{
		Image[] imgs = new Image[4];
		imgs[0] = getImageByName("h_down");
		imgs[1] = getImageByName("h_up");
		imgs[2] = getImageByName("h_left");
		imgs[3] = getImageByName("h_right");
		return imgs;
	}
	
	public static Image[] getElfImgs()
	{
		Image[] imgs = new Image[8];
		imgs[0] = getImageByName("e_down");
		imgs[1] = getImageByName("e_up");
		imgs[2] = getImageByName("e_left");
		imgs[3] = getImageByName("e_right");
		imgs[4] = getImageByName("e_adown");
		imgs[5] = getImageByName("e_aup");
		imgs[6] = getImageByName("e_aleft");
		imgs[7] = getImageByName("e_aright");
		return imgs;
	}
	
	public static Image[] getHumanImgs()
	{
		Image[] imgs = new Image[8];
		imgs[0] = getImageByName("hu_down");
		imgs[1] = getImageByName("hu_up");
		imgs[2] = getImageByName("hu_left");
		imgs[3] = getImageByName("hu_right");
		imgs[4] = getImageByName("hu_adown");
		imgs[5] = getImageByName("hu_aup");
		imgs[6] = getImageByName("hu_aleft");
		imgs[7] = getImageByName("hu_aright");
		return imgs;
	}
	
	public static Image[] getDwarfImgs()
	{
		Image[] imgs = new Image[8];
		imgs[0] = getImageByName("d_down");
		imgs[1] = getImageByName("d_up");
		imgs[2] = getImageByName("d_left");
		imgs[3] = getImageByName("d_right");
		imgs[4] = getImageByName("d_adown");
		imgs[5] = getImageByName("d_aup");
		imgs[6] = getImageByName("d_aleft");
		imgs[7] = getImageByName("d_aright");
		return imgs;
	}
	
	public static Image getImageByName(String s)
	{
		Image img = null;
		try
		{
			img = imgLib.get(s);
		}
		catch (Exception e)
		{
			System.out.println(s +"doesn't contained in imgLib");
			img = imgLib.get("noimg");
		}
		return img;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		getImageByName("background").draw(g2d, 0, 0);
		
		game.draw(g2d);
		
		if(game.gameover)
			getImageByName("gameover").draw(g2d, 100, 100);
	}
	
	public static void drawField(Graphics2D g, int x, int y, String type)
	{
		getImageByName(type).draw(g, x, y);
	}
	
	public static void close(){
		frame.setVisible(false);
	}
}