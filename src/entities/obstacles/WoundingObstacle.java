package entities.obstacles;

import java.util.Map;

import attackBehavior.AttackBehaviour;
import attackBehavior.BraveBehaviour;
import map.Field;
import enumerations.DamageType;
import enumerations.Side;
import graphics.Image;

// sebzõ akadály
public class WoundingObstacle extends TimedObstacle {
	/* -------- TAGVÁLTOZÓK -------- */
	private AttackBehaviour ab; // támadási viselkedés

	/* -------- KOSNTRUKTOR -------- */
	public WoundingObstacle(Field f, long dt, Map<DamageType, Double> dmg, Image img) {
		super(f, dt, img);
		ab = new BraveBehaviour(Side.LIGHT, 1, 20, 1, 5, dmg);
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// akadályon tartózkodó egységek sebzése
	protected void obstractUnits(long dt) {
		// támadj meg valakit
		ab.attack(field, dt);
	}

	// eltûnéskor meghívódó függvény
	protected void disappear() {
		/// EMPTY
	}

	// konvertálás stringgé
	public String toString() {
		return "WoundingObstacle(ab = " + ab.toString() + ")";
	}
	
	@Override
	public void testPrintInfo() 
	{
		System.out.print("wounding obstacle(" + field.x + ", " + field.y + "): ");
		System.out.print((ab.isAttacking() ? "attacking" : "not attacking") + ", ");
		System.out.print("no upgrades, "); /// TODO
		super.testPrintInfo();
	}

	@Override
	public boolean isAttacking(){
		return ab.isAttacking();
	}
}