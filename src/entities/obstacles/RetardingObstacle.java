package entities.obstacles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import map.Field;
import entities.Unit;
import graphics.Image;

// lassító akadály
public class RetardingObstacle extends TimedObstacle {
	/* -------- TAGVÁLTOZÓK -------- */
	private List<Unit> slowedUnits = new LinkedList<Unit>(); // lista az éppen
																// az akadályon
																// tartózkodó
																// egységekrõl

	/* -------- KONSTRUKTOR -------- */
	public RetardingObstacle(Field f, long dt, Image img) {
		super(f, dt, img);
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// az akadályon tartózkodó egységek lassítása
	protected void obstractUnits(long dt) {
		// a mezõn tartózkodó egységek
		ArrayList<Unit> unitsPresent = (ArrayList<Unit>) field.getUnits();

		// ha olyan jött, aki eddig nem volt benne: lassítsd és jegyezd meg
		if(unitsPresent != null){
			for (Unit a : unitsPresent)
				if (slowedUnits.indexOf(a) == -1) {
					a.mulSpeed(0.5);
					slowedUnits.add(a);
				}
		}
			// ha valamelyik eltûnt: gyorsítsd és töröld
		for (Unit b : slowedUnits)
			if (unitsPresent == null || unitsPresent.indexOf(b) == -1) {
				b.mulSpeed(2);
				slowedUnits.remove(b);
			}
	}

	// eltûnéskor meghívódó függvény
	protected void disappear() {
		// állítsd vissza az értékeket
		for (Unit a : slowedUnits)
			a.mulSpeed(2);
	}

	// konvertálás stringgé
	public String toString() {
		return "RetardingObstacle(slowedUnits = " + slowedUnits.toString()
				+ ")";
	}
	
	@Override
	public void testPrintInfo() 
	{
		System.out.print("slowing obstacle(" + field.x + ", " + field.y + "): ");
		System.out.print((!slowedUnits.isEmpty() ? "attacking" : "not attacking") + ", ");
		System.out.print("no upgrades, "); /// TODO
		super.testPrintInfo();
	}

	@Override
	public boolean isAttacking(){
		return !slowedUnits.isEmpty();
	}
}