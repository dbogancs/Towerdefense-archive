package movers;

import map.*;
import entities.Entity;

//nem veszi figyelembe az obstacle-eket
public class Avoider extends RoutingMover {

	// konstruktor
	public Avoider(Entity e, Field dest, double s, Position p) { // public
																// RoutingMover(Entity
																// e, Field
																// dest, int s,
																// Map.Position
																// p)
		super(e, dest, s, p);
	}

	public Avoider(Avoider other) {
		super(other);
	}

	public boolean move(long dt) {
		return super.move(dt);
	}

	public String toString() {
		return "Avoider()";
	}
}