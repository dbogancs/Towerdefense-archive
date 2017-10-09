package attackBehavior;

import map.Field;
import enumerations.Side;

// támadási magatartás
// fõ feladatok: - célpont kiválasztása
//				 - célpont megtámadása
//				 - támadás véghezvitele (animálás)
public abstract class AttackBehaviour {
	
	public enum AttackPhase
	{
		COOLDOWN,
		ATTACKING,
		NONE
	}
	
	
	/* -------- TAGVÁLTOZÓK -------- */
	// jó vagy rossz oldal
	// a célpont kiválasztásánál játszik szerepet
	// (pl. orkok a többi fajra támadnak)
	protected Side side;

	/* -------- KONSTRUKTOR -------- */
	public AttackBehaviour(Side side) {
		this.side = side;
	}

	public AttackBehaviour(AttackBehaviour other) {
		this.side = other.side;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */

	// célpont kiválasztása, támadás (meghívva az animate( ) függvényben)
	// true: talált célt/támad; false: nincs támadható ellenség
	// false visszatérési érték esetén az animate( ) támadás helyett mozgat
	// paraméterek: - mezo: az aktuális mezõ, innen keresi a környezõ mezõkön a
	// támadható ellenfeleket
	// - dt: az éppen eltelt idõ (ez azért kell, mert a támadás animálását is ez
	// az osztály végzi)
	public abstract AttackPhase attack(Field mezo, long dt);

	// konvertálás stringgé
	public String toString() {
		return "AttackBehaviour(side = " + side.toString() + ")";
	}
	
	public abstract boolean isAttacking();

	public abstract AttackBehaviour copy();
}