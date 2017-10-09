package entities.obstacles;

import map.Field;
import entities.Entity;
import graphics.Image;
import runes.IUpgradable;
import runes.Rune;

// idõtõl függõ akadályok
// olyan akadályok, amelyek csak bizonyos ideig vannak a pályán
public abstract class TimedObstacle extends Entity implements IUpgradable {
	/* -------- TAGVÁLTOZÓK -------- */
	private long timeLeft; // még mennyi idõ maradt

	/* -------- KONSTRUKTOR -------- */
	public TimedObstacle(Field f, long time, Image img) {
		super(f,img);
		this.timeLeft = time;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// mozgás
	public boolean animate(long dt) {
		// akadályozz
		obstractUnits(dt);

		// frissítsd az idõt
		timeLeft -= dt;
		boolean returnValue;
		if (timeLeft <= 0) {
			disappear();
			returnValue = false;
		} else
			returnValue = true;
		
		return returnValue;
	}

	// rúna fogadása
	public void receiveRune(Rune r) {
		timeLeft = r.getNewTimeLeft(timeLeft);
	}

	// hatás kifejtése az akadályon tartózkodó egységekre
	protected abstract void obstractUnits(long dt);

	// eltûnéskor meghívódó függvény
	protected abstract void disappear();

	// konvertálás stringgé
	public String toString() {
		return "TimedObstacle(timeLeft = " + timeLeft + ")";
	}

	public void testPrintInfo(){
		System.out.println(timeLeft);
	}
	
	public abstract boolean isAttacking();
}