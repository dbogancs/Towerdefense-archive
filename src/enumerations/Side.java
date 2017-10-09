package enumerations;

// j� vagy rossz oldal
public enum Side {
	/* -------- �RT�KEK -------- */
	LIGHT(0), DARK(1);

	/* -------- TAGV�LTOZ�K -------- */
	private int intValue;

	/* -------- KONSTRUKTOR -------- */
	private Side(int a) {
		this.intValue = a;
	}

	/* -------- TAGF�GGV�NYEK -------- */
	// konvert�l�s stringg�
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