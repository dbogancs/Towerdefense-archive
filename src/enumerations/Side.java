package enumerations;

// jó vagy rossz oldal
public enum Side {
	/* -------- ÉRTÉKEK -------- */
	LIGHT(0), DARK(1);

	/* -------- TAGVÁLTOZÓK -------- */
	private int intValue;

	/* -------- KONSTRUKTOR -------- */
	private Side(int a) {
		this.intValue = a;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// konvertálás stringgé
	public String toString() {
		switch (intValue) {
		case 0:
			return "LIGHT";
		case 1:
			return "DARK";
		default:
			return "UNDEFINED SIDE";
		}
	}
}