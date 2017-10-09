package entities;

import java.awt.Graphics2D;
import java.util.Map;

import attackBehavior.*;
import attackBehavior.AttackBehaviour.AttackPhase;
import map.*;
import movers.*;
import enumerations.*;
import graphics.Image;

// unit
public class Unit extends Attackable implements Cloneable {
	/* -------- TAGVÁLTOZÓK -------- */
	private AttackBehaviour ab; 
								
	private RoutingMover mover;
	
	private Image[] images;
	

	/* -------- KONSTRUKTOR -------- */
	public Unit(Field f, int hP, Map<DamageType, Double> dS, Side s,
			AttackBehaviour aB, Image[] img) 
	{
		super(f, hP, dS, s, img[0]);
		this.ab = aB;
		images = img;
	}

	
	public Unit(Unit other) {
		super(other);

		ab = other.ab.copy();
		mover = new RoutingMover(other.mover);
		images = other.images;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// mover beállítása
	public void setMover(RoutingMover m) {
		this.mover = m;
	}

	// mozgás
	public boolean animate(long dt) {
		// ha meghalt, jelezd
		if (HP <= 0) {
			return false;
		}

		// egyébként támadj, ha nem megy, mozogj
		AttackPhase at = ab.attack(field, dt);
		if (at == AttackPhase.ATTACKING)
		{
			if(images.length > 4){
				image = images[mover.getDirectionIndex() + 4];
			}
		} 
		else 
		{
			if (at == AttackPhase.NONE) {
				mover.move(dt);
				image = images[mover.getDirectionIndex()];
			}
			else //akkor biztos cooldown
			{
				if (((BraveBehaviour)ab).getCoolDown() > ((BraveBehaviour)ab).getTimeLeft()*2) 
					image = images[mover.getDirectionIndex()];
			}
		}
		
		return true;
	}

	// sebesség megváltoztatása
	public void mulSpeed(double x) {
		mover.mulSpeed(x);
	}

	// konvertálás stringgé
	public String toString() {
		return "Unit(ab = " + (ab == null ? "null" : ab.toString())
				+ ", mover = " + (mover == null ? "null" : mover.toString())
				+ ")";
	}

	public void testPrintInfo() 
	{
		System.out.println("unit(" + field.x + ", " + field.y + "): -, " + HP + ", " + (ab.isAttacking() ? "attacking" : "not attacking"));	
	}
	
	public boolean isAttacking(){
		return ab.isAttacking();
	}
	
	public void draw(Graphics2D g2d)
	{
		Position pos = mover.getPosition();
		image.draw(g2d, field.fieldSize*field.x + pos.x, field.fieldSize*field.y + pos.y);
	}
	
}