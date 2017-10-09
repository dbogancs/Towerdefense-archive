package attackBehavior;

import map.Field;
import enumerations.Side;

// gyáva: sose támad (hobbit)
public class CowardBehaviour extends AttackBehaviour {
	/* -------- KONSTRUKTOR -------- */
	public CowardBehaviour(Side side) {
		super(side);
	}

	public CowardBehaviour(CowardBehaviour other) {
		super(other);
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	@Override
	public AttackPhase attack(Field mezo, long dt) {
		return AttackPhase.NONE;
	}

	// konvertálás stringgé
	public String toString() {
		return "CowardBehaviour";
	}

	@Override
	public AttackBehaviour copy() {
		return new CowardBehaviour(this);
	}

	@Override
	public boolean isAttacking() {
		return false;
	}
}