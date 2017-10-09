package enumerations;

//fajspecifikus sebzésértékek azonosítói
//Tower egy Map< DamageType, double > tárolóban tárolja, hogy melyik faj ellen mennyi a sebzése
//attackable dmgRV valtozoja hasonlo map, ott az egyes sebzesek hatasa az adott lenyre jellegu cucc tarolodik
public enum DamageType {
	/* -------- ÉRTÉKEK -------- */
	DWARF(0), // tÃ¶rp ellen
	HOBBIT(1), // hobbit ellen
	ELF(2), // elf ellen
	HUMAN(3); // ember ellen

	/* -------- TAGVÁLTOZÓK -------- */
	private int intValue;

	/* -------- KONSTRUKTOR -------- */
	private DamageType(int a) {
		this.intValue = a;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// konvertálás stringgé
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
 * public enum DamageType{ DWARF, // tÃ¶rp ellen HOBBIT, // hobbit ellen ELF, //
 * elf ellen HUMAN // ember ellen }
 */