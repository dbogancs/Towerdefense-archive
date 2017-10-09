package entities;

import java.awt.Graphics2D;

import game.Game;
import graphics.Image;
import map.Field;

//minden palyan szereplo entitas kozos ose
public abstract class Entity {
	/* -------- TAGVÁLTOZÓK -------- */
	protected Field field; // a mezo, amin az entitás tartózkodik
	public static Game game; // referencia az aktuális játék példányra (osztály
								// szintű!) //publica tettem,mibel a
								// BraveBehaviornak hozzá kell férnie
	protected int manaValue; // az érték, amit az entitás elpusztításáért kap a
								// Player
	
	protected Image image;
	

	/* -------- KOSNTRUKTOR -------- */
	public Entity(Field f, Image img) {
		field = f;
		image = img;
	}

	/* -------- TAGFÜGGVÉNYEK -------- */
	// mozgást és támadást végző függvény
	// az alosztályokban lesz megvalósítva
	public abstract boolean animate(long dt);

	// aktuális játék mentése osztály szinten
	// Game konstruktorában hívódik meg
	public static void setGame(Game g) {
		game = g;
	}

	// field access függvények
	// az entitás mozgatásához és kereséséhez kellenek
	public Field getField() {
		return field;
	}

	public void setField(Field f) {
		field = f;
	}

	// manaValue access függvények
	public int getManaValue() {
		return manaValue;
	}

	public void setManaValue(int m) {
		manaValue = m;
	}

	// konvertálás stringgé
	public String toString() {
		return "Entity(field = " + field.toString() + ", manaValue = "
				+ manaValue + ")";
	}
	
	public void draw(Graphics2D g2d)
	{
		image.draw(g2d, field.fieldSize*field.x, field.fieldSize*field.y);
	}
	
	Image getImage() { return image; }
	
	public abstract void testPrintInfo();
}