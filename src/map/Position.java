package map;

// relat�v pozici�k
// egy entit�snak k�t dolog hat�rozza meg a helyzet�t:
//		- a mez�, amin elhelyezkedik (Field)
//		- a mez�n bel�li relat�v helyzete (Position)
public class Position {
	// relat�v koordin�t�k
	public byte x, y;

	// maximum koordin�t�k abszol�t �rt�ke
	// pl. ha x > posMax, akkor m�r �t kell l�pni a jobb oldali mez�be
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