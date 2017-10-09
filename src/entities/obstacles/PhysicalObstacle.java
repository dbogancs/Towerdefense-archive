package entities.obstacles;

import java.util.Map;

import map.Field;
import entities.Attackable;
import enumerations.DamageType;
import enumerations.Side;
import graphics.Image;
import runes.IUpgradable;
import runes.Rune;

// fizikai akad�ly: csak �tban van
public class PhysicalObstacle extends Attackable implements IUpgradable {
	/* -------- KONSTRUKTOR -------- */
	public PhysicalObstacle(Field f, int hP, Map<DamageType, Double> dS, Side s, Image img) {
		super(f, hP, dS, s, img);
	}

	/* -------- TAGF�GGV�NYEK -------- */
	// animate: ha meghalt, jelezd
	public boolean animate(long dt) {
		return (HP > 0);
	}

	// r�na fogad�sa
	public void receiveRune(Rune r) {
		HP = r.getNewHP(HP);
	}

	// konvert�l�s stringg�
	public String toString() {
		return "PhysicalObstacle()";
	}

	public void testPrintInfo() 
	{
		System.out.println("physical obstacle(" + field.x + ", " + field.y + "): " + HP);
	}
}