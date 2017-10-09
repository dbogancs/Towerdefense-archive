package map;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entities.*;
import entities.obstacles.*;
import graphics.GraphicsHandler;
import runes.Rune;

public class Field {
	
	public static int fieldSize = 100;
	
	// mezõ koordináták (bal felsõ mezõ: 0, 0 )
	public final byte x, y;
	
	// szomszédok
	public Field right, left, up, down; // static mivel a convertMouseToField is static

	// út vagy nem?
	public boolean road;

	// lists
	private List<Attackable> attackables = new ArrayList<Attackable>();
	private List<Unit> units = new ArrayList<Unit>();
	private List<Bullet> bullets = new ArrayList<Bullet>();
	private Tower tower = null;
	private TimedObstacle tObstacle = null;
	private PhysicalObstacle pObstacle = null;
	
	// BFS segedvaltozo
	public boolean visited = false;

	// konstruktor
	public Field(byte x, byte y) {
		this.x = x;
		this.y = y;
		this.right = this.left = this.up = this.down = null;
		this.road = false;
	}

	public Field(byte x, byte y, boolean road) {
		this.x = x;
		this.y = y;
		this.right = this.left = this.up = this.down = null;
		this.road = road;
	}
	
	public void drawBack(Graphics2D g)
	{
		if (road)
			GraphicsHandler.drawField(g,x*fieldSize,y*fieldSize, "road");
		else
			GraphicsHandler.drawField(g,x*fieldSize,y*fieldSize, "nroad");
	}
	
	public void drawFront(Graphics2D g)
	{
		if (road)
		{
			if (tObstacle!=null) tObstacle.draw(g);
			if (pObstacle!=null) pObstacle.draw(g);
			for (Unit u :units) u.draw(g);
			
			for (Bullet b :bullets){
				b.draw(g);
			}
		}
		else
		{
			for (Unit u :units) u.draw(g);
			if (tower!=null) tower.draw(g);
		}
	}

	// töröld a mezõrõl az entitást
	public void removeEntity(Entity e) {	
		if(bullets.contains(e)){
			bullets.remove(e);
		} else if(units.contains(e)){
			units.remove(e);
			attackables.remove(e);
		} else if(tObstacle == e){
			tObstacle = null;
		} else if(pObstacle == e){
			pObstacle = null;
			attackables.remove(e);
		}
		
		attackables.remove(e);
		
	}

	public boolean isTowerBuildingPossible() {
		boolean returnValue = !road && (tower == null);
		return returnValue;
	}

	public boolean isPObstacleBuildingPossible() {
		boolean returnValue = road && (pObstacle == null)
				&& (attackables.size() == 0);
		return returnValue;
	}

	public boolean isTObstacleBuildingPossible() {
		boolean returnValue = road && (tObstacle == null)
				&& (attackables.size() == 0);
		return returnValue;
	}

	public void registerTower(Tower t) {
		tower = t;
	}

	public void registerUnit(Unit unit) {
		units.add(unit);
		attackables.add(unit);
	}

	public void registerTimedObstacle(TimedObstacle tO) {
		tObstacle = tO;
	}

	public void registerPObstacle(PhysicalObstacle pO) {
		if (!road) return;
		pObstacle = pO;
		attackables.add(pO);
	}

	public void registerBullet(Bullet b) {
		bullets.add(b);
	}

	public List<Unit> getUnits() {
		return units.isEmpty() ? null : units;
	}

	public List<Attackable> getAttackables() {
		return attackables.isEmpty() ? null : attackables;
	}

	// egér koordináták -> Field
	public static Field convertMouseToField(double x, double y) {
		return new Field((byte)(x / Field.fieldSize), (byte)(y / Field.fieldSize));
	}

	public void addRune(Rune r) {
		if (tower != null)
			tower.receiveRune(r);
		if (pObstacle != null)
			pObstacle.receiveRune(r);
		if (tObstacle != null)
			tObstacle.receiveRune(r);
	}


	public String toString() {
		return "Field(x = " + x + ", y = " + y + ", road = " + road + ")";
	}

	public Tower getTower()					{return tower;}
	public TimedObstacle getTObstacle() 	{return tObstacle;}
	public PhysicalObstacle getPObstacle()	{return pObstacle;}
	public List<Bullet>	getBullets()		{return bullets.isEmpty() ? null : bullets;}
}