package entities;

import java.util.Map;

import map.Field;
import enumerations.*;
import graphics.Image;

public abstract class Attackable extends Entity {
	/* -------- TAGVÁLTOZÓK -------- */
	protected int HP; // életerõ
	private Side side; // melyik oldalon áll

	// damage reduction (resistance) values
	// a négy fajta támadás típushoz párosít egy-egy 0 és 1 közötti értéket:
	// 0: nincs semmi ellenállás (a teljes sebzés megtörténik)
	// 1: teljes ellenállás (0 sebzés az adott sebzéstípushoz)
	// a támadóknak van egy Map< DamageType, double > "listája", ami azt
	// mutatja, hogy
	// adott támadási típusból mekkora sebzésre képesek. Ebbõl, és az Attackable
	// dmgRV listájából számolja a receiveDamage a sebzést.
	private Map<DamageType, Double> dmgRV;

	/* -------- KONSTRUKTOR -------- */
	public Attackable(Field f, int hP, Map<DamageType, Double> dS, Side s, Image img) {
		super(f,img);

		this.HP = hP;
		this.side = s;
		this.dmgRV = dS;
	}
	
	
	public Attackable(Attackable other) {
		super(other.getField(),other.getImage());

		this.HP = other.HP;
		this.side = other.side;
		this.dmgRV = other.dmgRV;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	public Side getSide() {
		return side;
	}

	// sebzõdés
	public void receiveDamage(Map<DamageType, Double> damages) {
		// sebzés kiszámítása (kerekítéssel)
		double damage = calculateDamage(damages, dmgRV);

		// HP frissítése
		HP -= damage;

		if (HP <= 0) // szól a mezõnek, hogy törölje ki onnan
			field.removeEntity(this);
		// ettõl az entitás listában még benne van, onnan a köv. animate
		// hívásakor fog törlõdni
		// => mostantól nem foglalkozik vele a többi
	}

	// sebzés kiszámítása
	private double calculateDamage(Map<DamageType, Double> damages,
			Map<DamageType, Double> damageSens) {
		
		if (damages == null)
			return 0;

		double d = 0;

		for (Map.Entry<DamageType, Double> entry : damages.entrySet()) {
			DamageType type = entry.getKey();
			d += (1 - damageSens.get(type)) * damages.get(type);
		}

		return d;
	}

	// konvertálás stringgé
	public String toString() {
		return "Attackable(side = " + side.toString() + ", HP = " + HP
				+ ", dmgRV = " + dmgRV.toString() + ")";
	}
}