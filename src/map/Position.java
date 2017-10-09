package map;

// relatív poziciók
// egy entitásnak két dolog határozza meg a helyzetét:
//		- a mezõ, amin elhelyezkedik (Field)
//		- a mezõn belüli relatív helyzete (Position)
public class Position {
	// relatív koordináták
	public byte x, y;

	// maximum koordináták abszolút értéke
	// pl. ha x > posMax, akkor már át kell lépni a jobb oldali mezõbe
	// (ezt a Mover kezeli)
	public static byte posMax = 50;

	// sima konstruktor
	public Position(byte x, byte y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "Position(x = " + x + ", y = " + y + ")";
	}
}