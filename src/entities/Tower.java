package entities;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import enumerations.DamageType;
import graphics.Image;
import attackBehavior.*;
import map.Field;
import runes.IUpgradable;
import runes.Rune;

// torony
public class Tower extends Entity implements IUpgradable {
	/* -------- TAGVÁLTOZÓK -------- */
	private BraveBehaviour b; // támadást kezelõ obj

	private boolean fog;
	private int fogTime;
	
	private static final int towerFogTime = 5000;
	
	// upgrades
	int attackRangeChange = 0;
	int attackTimeChange = 0;
	Map<DamageType, Double> damageChanges = new HashMap<DamageType, Double>();

	/* -------- KOSNTRUKTOR -------- */
	public Tower(Field f, BraveBehaviour b, Image img) 
	{
		super(f,img);

		fog = false;
		fogTime = 0;

		this.b = b;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	@Override
	public void draw(Graphics2D g2d)
	{
		image.draw(g2d, Field.fieldSize*field.x, Field.fieldSize*(field.y - 1));
	}
	
	// támadás
	public boolean animate(long dt) {
		/*if (fog) {
			fogTime -= dt;
			if (fogTime <= 0)
				fog = false;
		} else if (Math.random() < 0.05){ // 5%
			fogNow();
		}*/

		if ((!fog) || (Math.random() < 0.4))
			b.attack(field, dt);

		return true;
	}

	// rúnát kap a torony
	public void receiveRune(Rune r) {
		b.receiveRune(r);
		
		attackRangeChange += r.getAttackRangeChange();
		attackTimeChange += r.getAttackSpeedChange();
		
		DamageType changedDamageType = r.getChangedDamageType();
		double damageChange = r.getDamageChange();
		
		if (changedDamageType != null)
		{
			if(damageChanges.containsKey(changedDamageType))
				damageChanges.put(changedDamageType, damageChanges.get(changedDamageType) + damageChange);
			else
				damageChanges.put(changedDamageType, damageChange);
		}
		else 
		{
			damageChanges.put(DamageType.DWARF, damageChanges.get(DamageType.DWARF) + damageChange);
			damageChanges.put(DamageType.HUMAN, damageChanges.get(DamageType.HUMAN) + damageChange);
			damageChanges.put(DamageType.ELF, damageChanges.get(DamageType.ELF) + damageChange);
			damageChanges.put(DamageType.HOBBIT, damageChanges.get(DamageType.HOBBIT) + damageChange);
		}
	}

	// konvertálás stringgé
	public String toString() {
		return "Tower(b = " + b.toString() + ")";
	}

	public void testPrintInfo() 
	{
		System.out.print("tower(" + field.x + ", " + field.y + "): ");
		System.out.print("range: " + attackRangeChange + ", speed: " + attackTimeChange);
		if(!damageChanges.isEmpty()){
			System.out.print(", damage: ");
			for(Map.Entry<DamageType, Double> entry : damageChanges.entrySet())
		    	System.out.print(entry.getValue() + "(" + entry.getKey().toString() + ") ");
		}
		System.out.println(", " + (fog ? "fogged" : "not fogged"));
	}
	
	public boolean isAttacking(){
		return b.isAttacking();
	}
	
	public void fogNow(){
		fog = true;
		fogTime = towerFogTime;
	}
}