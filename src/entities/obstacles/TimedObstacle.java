package entities.obstacles;

import map.Field;
import entities.Entity;
import graphics.Image;
import runes.IUpgradable;
import runes.Rune;

// id�t�l f�gg� akad�lyok
// olyan akad�lyok, amelyek csak bizonyos ideig vannak a p�ly�n
public abstract class TimedObstacle extends Entity implements IUpgradable {
	/* -------- TAGV�LTOZ�K -------- */
	private long timeLeft; // m�g mennyi id� maradt

	/* -------- KONSTRUKTOR -------- */
	public TimedObstacle(Field f, long time, Image img) {
		super(f,img);
		this.timeLeft = time;
	}

	/* -------- TAGF�GGV�NYEK -------- */
	// mozg�s
	public boolean animate(long dt) {
		// akad�lyozz
		obstractUnits(dt);

		// friss�tsd az id�t
		timeLeft -= dt;
		boolean returnValue;
		if (timeLeft <= 0) {
			disappear();
			returnValue = false;
		} else
			returnValue = true;
		
		return returnValue;
	}

	// r�na fogad�sa
	public void receiveRune(Rune r) {
		timeLeft = r.getNewTimeLeft(timeLeft);
	}

	// hat�s kifejt�se az akad�lyon tart�zkod� egys�gekre
	protected abstract void obstractUnits(long dt);

	// elt�n�skor megh�v�d� f�ggv�ny
	protected abstract void disappear();

	// konvert�l�s stringg�
	public String toString() {
		return "TimedObstacle(timeLeft = " + timeLeft + ")";
	}

	public void testPrintInfo(){
		System.out.println(timeLeft);
	}
	
	public abstract boolean isAttacking();
}