package game;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import attackBehavior.*;
import map.Field;
import map.Position;
import movers.AggressiveMover;
import movers.Avoider;
import movers.RoutingMover;
import entities.Entity;
import entities.Unit;
import enumerations.DamageType;
import enumerations.Side;
import graphics.GraphicsHandler;
import graphics.Image;

//ezt az osztalyt a menu peldanyositja
//csak a tenyleges jatekot fedi le, a menu kulon komponens lett
public class Game {
	// jatek nehezsege, kihat a lenyek szamara/gyakorisagara/erossegere
	// esetleg mana cost-ra
	public enum Difficulty {
		/* -------- ÉRTÉKEK -------- */
		EASY(1), MEDIUM(2), HARD(3);

		/* -------- TAGVÁLTOZÓK -------- */
		private int intValue;

		/* -------- KONSTRUKTOR -------- */
		Difficulty(int a) {
			this.intValue = a;
		}

		/* -------- TAGFÜGGVÉNYEK -------- */
		// konvertálás stringgé
		public String toString() {
			switch (intValue) {
			case 1:
				return "EASY";
			case 2:
				return "MEDIUM";
			case 3:
				return "HARD";
			default:
				return "UNDEFINED DIFFICULTY!";
			}
		}
	}

	/* -------- TAGVÁLTOZÓK -------- */
	protected Difficulty difficulty;

	// jatek ideje masodpercben
	// duration jatekbeli szazadmasodperc mulva vege a jateknak
	protected long duration;

	// a jatek elkezdesenek ideje
	protected final long startTime;

	// ido jatek idokoordinatakkal zajlik, lehet gyorsitani/lassitani
	// ez a szorzot tarolja
	protected double speedMul;

	// a legutobbi mintavetelezeskor kiolvasott ido
	protected long lastCheckedTime;

	// ekkor jott be legutobb leny
	// hasznaljuk majd, hogy eldontsuk, most jon e
	protected long lastCreatureSpawnTime;

	protected Field topLeftCorner;
	protected Field mountDoom;
	protected byte mapSizeX;
	protected byte mapSizeY;
	
	private boolean death = false;
	
	Image speed;
	
	private ArrayList<Field> spawnPossible = new ArrayList<Field>();
	
	protected List<Entity> entities;

	// ebben nyilvantartjuk, hogy hany elo enemy unit van a jatekban
	protected int enemyCounter;

	// jatek kozben felhasznaloi interakcio regisztralasa
	// parancsra feldolgozasa, vegrehajtasa
	protected InputHandler inputHandler;

	// ebbe gyujtjuk a lenyek megolesekor a mana-t
	// input handler process fv hivaskor atadjuk neki
	private int bonusMana;
	
	// game over flag
	public boolean gameover = false;
	
	// graphics handler
	public GraphicsHandler graphicsHandler;

	/* -------- KONSTRUKTOR -------- */
	public Game(Difficulty gD, long gTime, String mapName) throws Exception {
		
		// initialize map
		if (mapName!=null) initMap(mapName);

		// initialize inputHandler
		int initialManaValue = 100;
		graphicsHandler = GraphicsHandler.init(this);
		inputHandler = new InputHandler(initialManaValue, topLeftCorner, graphicsHandler);
		
		speed = graphicsHandler.getImageByName("speed");

		// initialize everything else
		duration = gTime;
		speedMul = 1;
		difficulty = gD;
		startTime = System.currentTimeMillis();
		lastCheckedTime = startTime;
		lastCreatureSpawnTime = startTime;
		bonusMana = 0;
		enemyCounter = 0;
		entities = new ArrayList<Entity>();

		// set game
		Entity.setGame(this);
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	public JPanel getGraphicsHandler(){
		return graphicsHandler;
	}
	
	// unit keszito fv-k, amik megfeleloen parameterezik
	// a unit konstruktorat
	protected Unit createElf(Field f) {
		Map<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 45.0);
		dmg.put(DamageType.ELF, 45.0);
		dmg.put(DamageType.HUMAN, 45.0);
		dmg.put(DamageType.HOBBIT, 45.0);

		AttackBehaviour ab = new BraveBehaviour(Side.DARK, 150, 5, 80, 6, dmg);
		Unit newUnit = new Unit(f, 100, dmg, Side.DARK, ab, GraphicsHandler.getElfImgs());
		newUnit.setMover(new RoutingMover(newUnit, mountDoom, 2, new Position(
				(byte) 0, (byte) 0)));

		return newUnit;
	}

	protected Unit createDwarf(Field f) {
		Map<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 40.0);
		dmg.put(DamageType.ELF, 40.0);
		dmg.put(DamageType.HUMAN, 40.0);
		dmg.put(DamageType.HOBBIT, 40.0);

		AttackBehaviour ab = new BraveBehaviour(Side.DARK, 100, 5, 80, 6, dmg);
		Unit newUnit = new Unit(f, 100, dmg, Side.DARK, ab,GraphicsHandler.getDwarfImgs());
		newUnit.setMover(new RoutingMover(newUnit, mountDoom, 1, new Position(
				(byte) 0, (byte) 0)));

		return newUnit;
	}

	protected Unit createHuman(Field f) {
		Map<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 40.5);
		dmg.put(DamageType.ELF, 40.5);
		dmg.put(DamageType.HUMAN, 40.5);
		dmg.put(DamageType.HOBBIT, 40.5);

		AttackBehaviour ab = new BraveBehaviour(Side.DARK, 100, 5, 80, 6, dmg);
		Unit newUnit = new Unit(f, 100, dmg, Side.DARK, ab,GraphicsHandler.getHumanImgs());
		newUnit.setMover(new RoutingMover(newUnit, mountDoom, 1, new Position(
				(byte) 0, (byte) 0)));

		return newUnit;
	}

	protected Unit createHobbit(Field f) {
		Map<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 0.5);
		dmg.put(DamageType.ELF, 0.5);
		dmg.put(DamageType.HUMAN, 0.5);
		dmg.put(DamageType.HOBBIT, 0.5);

		AttackBehaviour ab = new CowardBehaviour(Side.DARK);
		Unit newUnit = new Unit(f, 100, dmg, Side.DARK, ab,GraphicsHandler.getHobbitImgs());
		newUnit.setMover(new Avoider(newUnit, mountDoom, 1, new Position(
				(byte) 0, (byte) 0)));

		return newUnit;
	}

	public Unit createOrc(Field f) {
		Map<DamageType, Double> dmg = new HashMap<DamageType, Double>();
		dmg.put(DamageType.DWARF, 0.5);
		dmg.put(DamageType.ELF, 0.5);
		dmg.put(DamageType.HUMAN, 0.5);
		dmg.put(DamageType.HOBBIT, 0.5);

		AttackBehaviour ab = new BraveBehaviour(Side.LIGHT, 50, 5, 10, 6, dmg);
		Unit newUnit = new Unit(f, 100, dmg, Side.DARK, ab, GraphicsHandler.getHobbitImgs());
		newUnit.setMover(new AggressiveMover(newUnit, 1, new Position((byte) 0,
				(byte) 0)));

		return newUnit;
	}

	// lenyek bejovetele
	private void spawn(long dt) {
		
		if (lastCheckedTime - lastCreatureSpawnTime < 1000) return;
		
		
		double difMod = 1.0;
		if (difficulty==Difficulty.HARD) difMod = 100;
		if (difficulty==Difficulty.EASY) difMod = 0.8;
		
		double tMod = (System.currentTimeMillis() + startTime) / (startTime + duration);
		
		if ( difMod * tMod * Math.random() < 0.5 ) return;
		
	
		Field splace = spawnPossible.get((int) (spawnPossible.size()*Math.random()));
		
		// calculate unit type
		double rand = Math.random();
		Unit newUnit = null;
		
		if(rand < 0.25)
			newUnit = createElf(splace);
		else if(rand < 0.5)
			newUnit = createHobbit(splace);
		else if(rand < 0.75)
			newUnit = createHuman(splace);
		else
			newUnit = createDwarf(splace);
	
		enemyCounter++;		
  	// register unit
		splace.registerUnit(newUnit);
		registerEntity(newUnit);
		lastCreatureSpawnTime = System.currentTimeMillis();
	}

	// egesz vilag dt ideju szimulalasa
	// szkeleton miatt public
	public void animate(long dt) {
		int listSize = entities.size();
		while (listSize-- != 0) {
			Entity entity = entities.remove(0);

			if (!entity.animate(dt)) {
				int manna = entity.getManaValue();

				if (manna > 0) {
					bonusMana += manna;
					enemyCounter--;
				}
				
				entity.getField().removeEntity(entity);
				entities.remove(entity);
			} else {
				entities.add(entity);
			}
		}
	}

	// input handler igy modositja a jatek gyorsasagat
	// neki nem kell torodnia a korlatokkal, azt itt rendezzuk
	public void timeSpeedMod(int tFlag) {
		if (tFlag == -1)
			speedMul *= 0.5;
		if (tFlag == 1)
			speedMul *= 2;

		if (speedMul < 0.5)
			speedMul = 0.5;
		if (speedMul > 2)
			speedMul = 2;
	}
	
	public void kill()
	{
		death = true;
	}

	// uj leny hozzadasa - lovedeket igy tudunk hozzaadni
	public void registerEntity(Entity e) {
		entities.add(e);
	}

	private void gameOverMsg() {
		gameover = true;

		long ct = System.currentTimeMillis();
		while(System.currentTimeMillis() < (ct + 2000)){
			graphicsHandler.repaint();
		}
	}

	// lekezeli a jatek veget
	// false -> meg nincs veg
	// true -> vege
	private boolean handleGameEnd() {
		// calculate game over
		boolean gameOver = (System.currentTimeMillis() > (startTime + duration)); //|| (enemyCounter == 0);
		
		if (mountDoom.getUnits()!=null || death) gameOver = true;
		
		if (gameOver)
			gameOverMsg();
		
		return gameOver;
	}

	// jatek ennek a hivasaval indithato, ez tartalmazza a main loop-ot
	public void play() {
		do {	
			// dt
			long dt = (long) ((System.currentTimeMillis() - lastCheckedTime) * speedMul / 10.0);
			lastCheckedTime = System.currentTimeMillis();
			
			spawn(dt);
			
			// felhasználó parancsainak feldolgozása
			inputHandler.process(this, bonusMana);

			// step
			bonusMana = 0;
			
			animate(dt); // sets bonusmana
			
			graphicsHandler.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (!handleGameEnd());
		
		graphicsHandler.close();
		Menu.showMenu();
		System.gc();
	}



	// konvertálás stringgé
	public String toString() {
		return "Game(difficulty = " + difficulty.toString() + ", duration = "
				+ duration + ")";
	}
	
	protected void initMap(String mapName) throws Exception {
		
		MapReader  mr = new MapReader(mapName);
		byte x = mr.getX();
		byte y = mr.getY();
		Field[][] fieldMap = new Field[x+2][y+2];

		for (byte i = 0; i<mr.getX()+2; i++)
			for (byte j=0; j<y+2; j++)
				fieldMap[i][j] = null;

		String s;
		for (byte i=1; i<x+1; i++)
			for (byte j=1; j<y+1; j++)
			{
				s = mr.getField(i-1,j-1);
				
				fieldMap[i][j] = new Field((byte)(i-1),(byte)(j-1),s.equals("r") ||s.equals("m")); ///!! mount doom is road now
				if (s.equals("m")) mountDoom = fieldMap[i][j];
			}

		for (int i=1; i<x+1; i++)
			for (int j=1; j<y+1; j++)
			{
				fieldMap[i][j].up 		= fieldMap[i][j-1];
				fieldMap[i][j].down 	= fieldMap[i][j+1];
				fieldMap[i][j].left 	= fieldMap[i-1][j];
				fieldMap[i][j].right 	= fieldMap[i+1][j];
			}
		topLeftCorner = fieldMap[1][1];
		//inputHandler.setCorner(topLeftCorner); csak tesz modban van igy ertelme
		mapSizeX = (byte) (x-2);
		mapSizeY = (byte) (y-2);
		
		
		///!! REMOVE THIS (this is for testing routing mover)
//		Unit u=new Unit(mountDoom, bonusMana, null, null, null);
//		u.setField(topLeftCorner.down);
//		RoutingMover rm=new RoutingMover(u, mountDoom, 3, null);
//		///!! UNTIL HERE
	
		//adding field on which spawning is possible to a list
		for (int i=1; i<x+1; i++)
			for (int j=1; j<y+1; j++)
			{
				if(fieldMap[i][j] != mountDoom && fieldMap[i][j].road==true && ((i==x) || (j==y) ||(i==1) ||(j==1) )) //road and on the side
					spawnPossible.add(fieldMap[i][j]);
				
			}
	
	}
	
	public void draw(Graphics2D g)
	{
		Field field = topLeftCorner;
		Field rowStart = topLeftCorner;
		for (int j=0; j<=mapSizeY+1; j++)
		{
			for (int i=0; i<=mapSizeX+1; i++)
			{
				field.drawBack(g);
				field = field.right;
			}
			rowStart = rowStart.down;
			field = rowStart;
		}
		
		graphicsHandler.drawField(g, mountDoom.x*Field.fieldSize, mountDoom.y*Field.fieldSize, "md");
		
		field = topLeftCorner;
		Field columnStart = topLeftCorner;
		for (int j=0; j<=mapSizeX+1; j++)
		{
			for (int i=0; i<=mapSizeY+1; i++)
			{
				field.drawFront(g);
				field = field.down;
			}
			columnStart = columnStart.right;
			field = columnStart;			
		}
		
		inputHandler.draw(g);
		
		speed.draw(g, 895,655);
		if (speedMul>0.5) speed.draw(g, 875,655);
		if (speedMul>1.1) speed.draw(g, 915,655);
		
	}
	
}

