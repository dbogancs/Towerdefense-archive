package entities;

import java.util.Map;

import map.Field;
import enumerations.*;
import graphics.Image;

public abstract class Attackable extends Entity {
	/* -------- TAGV�LTOZ�K -------- */
	protected int HP; // �leter�
	private Side side; // melyik oldalon �ll

	// damage reduction (resistance) values
	// a n�gy fajta t�mad�s t�pushoz p�ros�t egy-egy 0 �s 1 k�z�tti �rt�ket:
	// 0: nincs semmi ellen�ll�s (a teljes sebz�s megt�rt�nik)
	// 1: teljes ellen�ll�s (0 sebz�s az adott sebz�st�pushoz)
	// a t�mad�knak van egy Map< DamageType, double > "list�ja", ami azt
	// mutatja, hogy
	// adott t�mad�si t�pusb�l mekkora sebz�sre k�pesek. Ebb�l, �s az Attackable
	// dmgRV list�j�b�l sz�molja a receiveDamage a sebz�st.
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

	/* -------- TAGF�GGV�NYEK -------- */
	public Side getSide() {
		return side;
	}

	// sebz�d�s
	public void receiveDamage(Map<DamageType, Double> damages) {
		// sebz�s kisz�m�t�sa (kerek�t�ssel)
		double damage = calculateDamage(damages, dmgRV);

		// HP friss�t�se
		HP -= damage;

		if (HP <= 0) // sz�l a mez�nek, hogy t�r�lje ki onnan
			field.removeEntity(this);
		// ett�l az entit�s list�ban m�g benne van, onnan a k�v. animate
		// h�v�sakor fog t�rl�dni
		// => mostant�l nem foglalkozik vele a t�bbi
	}

	// sebz�s kisz�m�t�sa
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

	// konvert�l�s stringg�
	public String toString() {
		return "Attackable(side = " + side.toString() + ", HP = " + HP
				+ ", dmgRV = " + dmgRV.toString() + ")";
	}
}