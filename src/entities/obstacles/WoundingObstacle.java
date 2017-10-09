package entities.obstacles;

import java.util.Map;

import attackBehavior.AttackBehaviour;
import attackBehavior.BraveBehaviour;
import map.Field;
import enumerations.DamageType;
import enumerations.Side;
import graphics.Image;

// sebz� akad�ly
public class WoundingObstacle extends TimedObstacle {
	/* -------- TAGV�LTOZ�K -------- */
	private AttackBehaviour ab; // t�mad�si viselked�s

	/* -------- KOSNTRUKTOR -------- */
	public WoundingObstacle(Field f, long dt, Map<DamageType, Double> dmg, Image img) {
		super(f, dt, img);
		ab = new BraveBehaviour(Side.LIGHT, 1, 20, 1, 5, dmg);
	}

	/* -------- TAGF�GGV�NYEK -------- */
	// akad�lyon tart�zkod� egys�gek sebz�se
	protected void obstractUnits(long dt) {
		// t�madj meg valakit
		ab.attack(field, dt);
	}

	// elt�n�skor megh�v�d� f�ggv�ny
	protected void disappear() {
		/// EMPTY
	}

	// konvert�l�s stringg�
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