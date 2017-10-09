package game;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import attackBehavior.AttackBehaviour;
import attackBehavior.BraveBehaviour;
import entities.Tower;
import entities.Unit;
import entities.obstacles.PhysicalObstacle;
import entities.obstacles.RetardingObstacle;
import entities.obstacles.TimedObstacle;
import entities.obstacles.WoundingObstacle;
import enumerations.DamageType;
import enumerations.Side;
import graphics.GraphicsHandler;
import map.Field;
import map.Position;
import movers.AggressiveMover;
import runes.Rune;



// input kezelõ osztály
public class InputHandler {
	/* -------- TAGVÁLTOZÓK -------- */
	// jatekos szamara elerheto mana, ebbol vasarolgathat
	private int mana;

	// igaz ertek eseten a process-ben vmit letre kell hozni
	static boolean shouldCreateSomething = false;

	// -1 -> lassitunk, 1 -> gyorsitunk
	private static int timeChange = 0;

	// a térkép bal felsõ sarka
	private static Field topLeftCorner = null;

	// a mezo, ami ki van jelolve
	// barmelyik menuikonra kattintva nullazodik
	private static Field selectedField = null;
	
	private static boolean gameShouldDie = false;

	// ideglenes taroloknak hivott vmik kezdodnek************
	// ezekben taroljuk a mar kijelolt de meg le nem rakott
	// objektumokat, ha az egyik ikonjara rakattintunk,
	// az osszes tobbi null-ra alitodik
	// igy process-ben a shouldCreateSomething true eseten
	// csak letrehozzuk azt, ami nem null
	private static Unit newUnit = null;
	private static TimedObstacle newTObstacle = null;
	private static PhysicalObstacle newPObstacle = null;
	private static Rune newRune = null;
	private static Tower newTower = null;
	
	private static GraphicsHandler gh = null;
	
	private static int currentMouseX = 0;
	private static int currentMouseY = 0;

	/* -------- KONSTRUKTOR -------- */
	public InputHandler(int mana, Field corner, GraphicsHandler gh) {
		this.mana = mana;
		this.topLeftCorner = corner;
		this.gh = gh;
	}
	
	public void setCorner(Field corner)
	{
		topLeftCorner = corner;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// egér kattintás
	public static void mouseClick(double x, double y) {
		
		if (x<800){
		
			int xc = (int) (x / Field.fieldSize);
			int yc = (int) (y / Field.fieldSize);
			
			Field field = topLeftCorner;
			for (int i=0; i<xc;i++) field = field.right;
			for (int i=0; i<yc;i++) field = field.down;
			
			selectedField = field;
			shouldCreateSomething = true;
		} //x<800
		
		
		if((x > 805) && (x < 855) && (y > 200) && (y < 255)) keyPressed('b');
		if((x > 875) && (x < 925) && (y > 200) && (y < 255)) keyPressed('m');
		if((x > 945) && (x < 995) && (y > 200) && (y < 255)) keyPressed('n');
		
		if((x > 805) && (x < 855) && (y > 300) && (y < 355)) keyPressed('h');
		if((x > 875) && (x < 925) && (y > 300) && (y < 355)) keyPressed('k');
		if((x > 945) && (x < 995) && (y > 300) && (y < 355)) keyPressed('j');
		
		if((x > 805) && (x < 855) && (y > 400) && (y < 455)) keyPressed('c');
		if((x > 875) && (x < 925) && (y > 400) && (y < 455)) keyPressed('g');
		if((x > 945) && (x < 995) && (y > 400) && (y < 455)) keyPressed('v');
		
		if((x > 805) && (x < 855) && (y > 500) && (y < 555)) keyPressed('x');
		if((x > 875) && (x < 925) && (y > 500) && (y < 555)) keyPressed('i');
		if((x > 945) && (x < 995) && (y > 500) && (y < 555)) keyPressed('y');
		
		if((x > 805) && (x < 855) && (y > 600) && (y < 655)) keyPressed('o');
		if((x > 875) && (x < 925) && (y > 600) && (y < 655)) keyPressed('u');
		if((x > 945) && (x < 995) && (y > 600) && (y < 655)) keyPressed('p');
		
		if((x > 805) && (x < 855) && (y > 700) && (y < 755)) timeChange = -1;
		if((x > 945) && (x < 995) && (y > 700) && (y < 755)) timeChange = 1;
		
		if ((x > 700) && (y < 300)) gameShouldDie = true;
	}

	// egér mozgatás
	public void mouseMotion(double x, double y) {
		// majd kirajzolasnal tarolni kell az x,y-t
	}

	
	private static boolean isBuyCmd(char key)
	{
		if (key=='u' || key=='i' || key=='o' || key=='p' || 
			key=='z' || key=='h' || key=='j' || key=='k' || 
			key=='g' || key=='b' || key=='n' || key=='m' || 
			key=='y' || key=='x' || key=='c' || key=='v') return true;
		else return false;
	}
	
	private static boolean setNewTower(char key)
	{
		HashMap<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 20.0);
		dmg.put(DamageType.ELF, 20.0);
		dmg.put(DamageType.HUMAN, 20.0);
		dmg.put(DamageType.HOBBIT, 20.0);
		
		
		if (key=='b')
		{
			dmg.put(DamageType.ELF, 0.5);
			newTower = new Tower(null, new BraveBehaviour(Side.LIGHT, 250, 60, 60, 6, dmg),GraphicsHandler.getImageByName("tower_1"));
			return true;
		}
		if (key=='m')
		{
			dmg.put(DamageType.DWARF, 0.5);
			newTower = new Tower(null, new BraveBehaviour(Side.LIGHT, 300, 60, 60, 6, dmg),GraphicsHandler.getImageByName("tower_2"));
			return true;
		}
		if (key=='n')
		{
			dmg.put(DamageType.HUMAN, 0.5);
			newTower = new Tower(null, new BraveBehaviour(Side.LIGHT, 400, 60, 60, 6, dmg),GraphicsHandler.getImageByName("tower_3"));
			return true;
		}
		return false;
	}
	
	private static boolean setNewRune(char key)
	{
		switch (key)
		{
			case 'u':
				newRune = Rune.getHPBooster(10);
				break;
			case 'i':
				newRune = Rune.getRangeBooster(10);
				break;
			case 'o':
				newRune = Rune.getSpeedBooster(10);
				break;
			case 'p':
				newRune = Rune.getLifetimeBooster(10);
				break;
			case 'z':
				newRune = Rune.getDamageBooster(null, 1);
				break;
			case 'y':
				newRune = Rune.getDamageBooster(DamageType.ELF, 1);
				break;
			case 'x':
				newRune = Rune.getDamageBooster(DamageType.HOBBIT, 4);
				break;
			case 'c':
				newRune = Rune.getDamageBooster(DamageType.DWARF, 4);
				break;
			case 'v':
				newRune = Rune.getDamageBooster(DamageType.HUMAN, 4);
				break;
			default:
				return false;
		}
		return true;
	}
	
	private static boolean setObstacle(char key)
	{
		HashMap<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 30.0);
		dmg.put(DamageType.ELF,  30.0);
		dmg.put(DamageType.HUMAN,  30.0);
		dmg.put(DamageType.HOBBIT,  30.0);
		
		HashMap<DamageType, Double> dmgrv = new HashMap<DamageType, Double>();
		dmgrv.put(DamageType.DWARF, 0.5);
		dmgrv.put(DamageType.ELF,  0.5);
		dmgrv.put(DamageType.HUMAN,  0.5);
		dmgrv.put(DamageType.HOBBIT,  0.5);
		
		if (key=='h')
		{
			newPObstacle = new PhysicalObstacle(null, 150, dmgrv, Side.LIGHT, GraphicsHandler.getImageByName("obst_p"));
			return true;
		}
		if (key=='j')
		{
			newTObstacle = new RetardingObstacle(null, 300,GraphicsHandler.getImageByName("obst_r"));
			return true;
		}
		if (key=='k')
		{
			newTObstacle = new WoundingObstacle(null,300, dmg,GraphicsHandler.getImageByName("obst_w"));
			return true;
		}
		return false;
	}
	
	private static boolean setUnit(char key)
	{
		if (key=='g')
		{
			Map<DamageType, Double> dmg = new HashMap<DamageType, Double>();
			dmg.put(DamageType.DWARF, 0.5);
			dmg.put(DamageType.ELF, 0.5);
			dmg.put(DamageType.HUMAN, 0.5);
			dmg.put(DamageType.HOBBIT, 0.5);
	
			AttackBehaviour ab = new BraveBehaviour(Side.LIGHT, 50, 30, 10, 6, dmg);
			newUnit = new Unit(null, 100, dmg, Side.DARK, ab, GraphicsHandler.getHobbitImgs());
			newUnit.setMover(new AggressiveMover(newUnit, 1, new Position((byte) 0, (byte) 0)));
			return true;
		}
		return false;
	}
	
	public static void keyPressed(char key) 
	{
		if (isBuyCmd(key))
		{
			newUnit 	 = null;
			newTObstacle = null;
			newPObstacle = null;
			newRune 	 = null;
			newTower 	 = null;
			if (!setUnit(key) && !setNewRune(key) && !setObstacle(key)) setNewTower(key);
		}
	}

	public void draw(Graphics2D g){
		if(currentMouseX > 800 || currentMouseY > 1000)
			return;
		
		if(newTower != null){
			newTower.setField(Field.convertMouseToField(currentMouseX, currentMouseY));
			newTower.draw(g);
		} else if(newTObstacle != null){
			newTObstacle.setField(Field.convertMouseToField(currentMouseX, currentMouseY));
			newTObstacle.draw(g);
		} else if(newPObstacle != null){
			newPObstacle.setField(Field.convertMouseToField(currentMouseX, currentMouseY));
			newPObstacle.draw(g);
		} else if(newUnit != null){
			newUnit.setField(Field.convertMouseToField(currentMouseX, currentMouseY));
			newUnit.draw(g);
			System.out.println(newUnit.getField());
		} /*else if(newRune != null){
			newRune.setField(Field.convertMouseToField(currentMouseX, currentMouseY));
			newRune.draw(g);
		}*/
		
	}
	
	// minden regisztralt valtoztatast itt kezelunk le
	public void process(Game g, int bMana) {
		
		if (timeChange!=0){
			g.timeSpeedMod(timeChange);
			timeChange = 0;
		}
		
		if (gameShouldDie) g.kill();
		
		mana += bMana;

		if (selectedField != null && shouldCreateSomething) {
			if ((newUnit != null) && (newUnit.getManaValue() <= mana)) {
				mana -= newUnit.getManaValue();
				selectedField.registerUnit(newUnit);
				newUnit.setField(selectedField);
				g.registerEntity(newUnit);
				selectedField = null;
				newUnit = null;
			} else if ((newTObstacle != null)
					&& (newTObstacle.getManaValue() <= mana)) {
				mana -= newTObstacle.getManaValue();
				selectedField.registerTimedObstacle(newTObstacle);
				newTObstacle.setField(selectedField);
				g.registerEntity(newTObstacle);
				selectedField = null;
				newTObstacle = null;
			} else if ((newPObstacle != null)
					&& (newPObstacle.getManaValue() <= mana)) {
				mana -= newPObstacle.getManaValue();
				selectedField.registerPObstacle(newPObstacle);
				newPObstacle.setField(selectedField);
				g.registerEntity(newPObstacle);
				selectedField = null;
				newPObstacle = null;
			} else if ((newTower != null)
					&& (newTower.getManaValue() <= mana)) {
				mana -= newTower.getManaValue();
				selectedField.registerTower(newTower);
				newTower.setField(selectedField);
				g.registerEntity(newTower);
				selectedField = null;
				newTower = null;
			} else if ((newRune != null)
					&& (newRune.getManaValue() <= mana)) {
				mana -= newRune.getManaValue();
				selectedField.addRune(newRune);
				selectedField = null;
				newRune = null;
			}
			shouldCreateSomething = false;
		}

	}

	// konvertálás stringgé
	public String toString() {
		return "InputHandler(mana = " + mana + ")";
	}
	
	
	/* --------- MOUSE CLICK --------- */
	public static class MouseClickHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			InputHandler.mouseClick(arg0.getX(), arg0.getY());
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	/* --------- MOUSE MOTION --------- */
	public static class MouseMotionHandler implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			currentMouseX = arg0.getX();
			currentMouseY = arg0.getY();
		}	
	}
	
	
	/* --------- KEY --------- */
	public static class KeyHandler implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			InputHandler.keyPressed(arg0.getKeyChar());
		}
		
	}
}