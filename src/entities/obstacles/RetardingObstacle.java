package entities.obstacles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import map.Field;
import entities.Unit;
import graphics.Image;

// lass�t� akad�ly
public class RetardingObstacle extends TimedObstacle {
	/* -------- TAGV�LTOZ�K -------- */
	private List<Unit> slowedUnits = new LinkedList<Unit>(); // lista az �ppen
																// az akad�lyon
																// tart�zkod�
																// egys�gekr�l

	/* -------- KONSTRUKTOR -------- */
	public RetardingObstacle(Field f, long dt, Image img) {
		super(f, dt, img);
	}

	/* -------- TAGF�GGV�NYEK -------- */
	// az akad�lyon tart�zkod� egys�gek lass�t�sa
	protected void obstractUnits(long dt) {
		// a mez�n tart�zkod� egys�gek
		ArrayList<Unit> unitsPresent = (ArrayList<Unit>) field.getUnits();

		// ha olyan j�tt, aki eddig nem volt benne: lass�tsd �s jegyezd meg
		if(unitsPresent != null){
			for (Unit a : unitsPresent)
				if (slowedUnits.indexOf(a) == -1) {
					a.mulSpeed(0.5);
					slowedUnits.add(a);
				}
		}
			// ha valamelyik elt�nt: gyors�tsd �s t�r�ld
		for (Unit b : slowedUnits)
			if (unitsPresent == null || unitsPresent.indexOf(b) == -1) {
				b.mulSpeed(2);
				slowedUnits.remove(b);
			}
	}

	// elt�n�skor megh�v�d� f�ggv�ny
	protected void disappear() {
		// �ll�tsd vissza az �rt�keket
		for (Unit a : slowedUnits)
			a.mulSpeed(2);
	}

	// konvert�l�s stringg�
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