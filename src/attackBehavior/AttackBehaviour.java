package attackBehavior;

import map.Field;
import enumerations.Side;

// t�mad�si magatart�s
// f� feladatok: - c�lpont kiv�laszt�sa
//				 - c�lpont megt�mad�sa
//				 - t�mad�s v�ghezvitele (anim�l�s)
public abstract class AttackBehaviour {
	
	public enum AttackPhase
	{
		COOLDOWN,
		ATTACKING,
		NONE
	}
	
	
	/* -------- TAGV�LTOZ�K -------- */
	// j� vagy rossz oldal
	// a c�lpont kiv�laszt�s�n�l j�tszik szerepet
	// (pl. orkok a t�bbi fajra t�madnak)
	protected Side side;

	/* -------- KONSTRUKTOR -------- */
	public AttackBehaviour(Side side) {
		this.side = side;
	}

	public AttackBehaviour(AttackBehaviour other) {
		this.side = other.side;
	}

	/* -------- TAGF�GGV�NYEK -------- */

	// c�lpont kiv�laszt�sa, t�mad�s (megh�vva az animate( ) f�ggv�nyben)
	// true: tal�lt c�lt/t�mad; false: nincs t�madhat� ellens�g
	// false visszat�r�si �rt�k eset�n az animate( ) t�mad�s helyett mozgat
	// param�terek: - mezo: az aktu�lis mez�, innen keresi a k�rnyez� mez�k�n a
	// t�madhat� ellenfeleket
	// - dt: az �ppen eltelt id� (ez az�rt kell, mert a t�mad�s anim�l�s�t is ez
	// az oszt�ly v�gzi)
	public abstract AttackPhase attack(Field mezo, long dt);

	// konvert�l�s stringg�
	public String toString() {
		return "AttackBehaviour(side = " + side.toString() + ")";
	}
	
	public abstract boolean isAttacking();

	public abstract AttackBehaviour copy();
}