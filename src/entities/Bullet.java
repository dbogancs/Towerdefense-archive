package entities;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;

import map.Field;
import map.Position;
import movers.Mover;
import enumerations.*;
import graphics.Image;

//lövedék osztály
//ez közvetíti a sebzést a támadó és támadható egységek között
public class Bullet extends Entity {
	/* -------- TAGVÁLTOZÓK -------- */
	private Map<DamageType, Double> damages; // mit mennyire sebez? kilövéskor
												// kapja meg a forrásától
	private int areaOfExplosion; // hatótávolság
	private Mover mover; // mozgatást végző tagváltozó

	/* -------- KONSTRUKTOR -------- */
	public Bullet(Field f, Map<DamageType, Double> dmg, int AOE, long dt, Image img) {
		super(f,img);

		this.damages = dmg;
		this.areaOfExplosion = AOE;
		
		game.registerEntity(this);
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// mover tagváltozó beállítása
	public void setMover(Mover m) {
		this.mover = m;

		// rögtön mozogj egyet
		int dt = 0;
		mover.move(dt);
	}

	// mozgás
	public boolean animate(long dt) {
		// mozogjon => ha false, akkor ott vagyunk
		if (!mover.move(dt)) {
			// kik vannak itt?
			List<Attackable> attackablesPresent = field.getAttackables();

			// ha van itt valaki: sebezzük
			if (attackablesPresent != null){
				Attackable a = attackablesPresent.remove(0);
				a.receiveDamage(damages);
				attackablesPresent.add(a);
			}

			// ketté hasítás
			List<Unit> unitsPresent = field.getUnits();

			/*
			if (unitsPresent != null){
				Unit u = unitsPresent.remove(0);
				if (Math.random() < 0.05) { // 5%
					Unit newUnit = new Unit(u);
					field.registerUnit(newUnit);
					game.registerEntity(newUnit);
				}
				unitsPresent.add(u);
			}*/

			// megsemmisülés
			field.removeEntity(this);
			return false;
		}
		
		return true;
	}

	// konvertálás stringgé
	public String toString() {
		return "Bullet(damages = "
				+ (damages == null ? "null" : damages.toString()) + ", AOE = "
				+ areaOfExplosion + ", mover = "
				+ (mover == null ? "null" : mover.toString()) + ")";
	}

	public void testPrintInfo() 
	{
		Field dest = mover.getDestination(); 
		System.out.println("bullet(" + field.x + ", " + field.y + "): (" + dest.x + ", " + dest.y + ")");
	}
	
	public void draw(Graphics2D g2d)
	{
		Position pos = mover.getPosition();
		image.draw(g2d, field.fieldSize*field.x + pos.x, field.fieldSize*field.y + pos.y);
	}
}
