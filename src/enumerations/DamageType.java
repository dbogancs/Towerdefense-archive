package enumerations;

//fajspecifikus sebz�s�rt�kek azonos�t�i
//Tower egy Map< DamageType, double > t�rol�ban t�rolja, hogy melyik faj ellen mennyi a sebz�se
//attackable dmgRV valtozoja hasonlo map, ott az egyes sebzesek hatasa az adott lenyre jellegu cucc tarolodik
public enum DamageType {
	/* -------- �RT�KEK -------- */
	DWARF(0), // törp ellen
	HOBBIT(1), // hobbit ellen
	ELF(2), // elf ellen
	HUMAN(3); // ember ellen

	/* -------- TAGV�LTOZ�K -------- */
	private int intValue;

	/* -------- KONSTRUKTOR -------- */
	private DamageType(int a) {
		this.intValue = a;
	}

	/* -------- TAGF�GGV�NYEK -------- */
	// konvert�l�s stringg�
	public String toString() {
		switch (intValue) {
		case 0:
			return "DWARF";
		case 1:
			return "HOBBIT";
		case 2:
			return "ELF";
		case 3:
			return "HUMAN";
		default:
			return "UNDEFINED DAMAGE TYPE";
		}
	}
}

/*
 * public enum DamageType{ DWARF, // törp ellen HOBBIT, // hobbit ellen ELF, //
 * elf ellen HUMAN // ember ellen }
 */