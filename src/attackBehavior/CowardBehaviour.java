package attackBehavior;

import map.Field;
import enumerations.Side;

// gy�va: sose t�mad (hobbit)
public class CowardBehaviour extends AttackBehaviour {
	/* -------- KONSTRUKTOR -------- */
	public CowardBehaviour(Side side) {
		super(side);
	}

	public CowardBehaviour(CowardBehaviour other) {
		super(other);
	}

	/* -------- TAGF�GGV�NYEK -------- */
	@Override
	public AttackPhase attack(Field mezo, long dt) {
		return AttackPhase.NONE;
	}

	// konvert�l�s stringg�
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