package movers;

import map.*;
import entities.Bullet;
import entities.Entity;
import entities.Unit;

//pozicionalo osztaly
//entityk helyenek es mozgasanak leirasahoz: a leszármaztatott osztályok
// lövedék mozgásához: ez
public class Mover {
	/* --------- TAGVÁLTOZÓK -------- */
	protected double speed; // a mozgas sebessege
	protected Position position; // az entity pillanatnyi helyzete a
										// kirajzolashoz, pixel pontosan (mezőn
										// belül)
	protected Field destination; // az aktuális célmező
	protected double directionX, directionY; // merre megy
	protected Entity e; // entitás, akihez tartozik

	// konstruktor
	public Mover(Entity e, Field dest, double s, Position p) {
		this.e = e;
		this.destination = dest;
		this.speed = s;
		this.position = p;
		
		Field f = e.getField();
		directionX = destination.x - f.x;
		directionY = destination.y - f.y;

		// normalize direction vector
		double len = Math.sqrt(directionX * directionX + directionY
				* directionY);
		directionX /= len;
		directionY /= len;
	}

	public Mover(Mover other) {
		this.e = other.e;
		this.destination = other.destination;
		this.speed = other.speed;
		this.position = other.position;

		Field f = e.getField();
		directionX = destination.x - f.x;
		directionY = destination.y - f.y;

		// normalize direction vector
		double len = Math.sqrt(directionX * directionX + directionY * directionY);
		directionX /= len;
		directionY /= len;
	}

	public Position getPosition() {
		return position;
	}

	// dt ido alatti mozgatast vegrehajttatja
	// ha hamis a visszateres, akkor elerte a dest mezot
	// ez a bullete, mindenki mas felulirja
	public boolean move(long dt) {
		// új pozíció kiszámítása
		double posX = position.x + dt * speed * directionX;
		double posY = position.y + dt * speed * directionY;

		// mezőváltás
		Field currentField = e.getField();
		if ((Math.abs(posX) > Position.posMax)
				|| (Math.abs(posY) > Position.posMax)) {
			Field newField = null;
			if (posX < -Position.posMax){
				newField = currentField.left;
				position = new Position((byte)(2*Position.posMax + posX), (byte) posY);
			}
			else if (posX > Position.posMax){
				newField = currentField.right;
				position = new Position((byte)(-2*Position.posMax + posX), (byte) posY);
			}
			if (posY > Position.posMax){
				newField = currentField.down;
				position = new Position((byte) posX, (byte)(-2*Position.posMax + posY));
			}
			if (posY < -Position.posMax){
				newField = currentField.up;
				position = new Position((byte) posX, (byte)(2*Position.posMax + posY));
			}


			currentField.removeEntity(e);
			newField.registerBullet((Bullet)e);
			e.setField(newField);
		} else {
			position.x = (byte) posX;
			position.y = (byte) posY;
		}
		
		boolean returnValue = e.getField() != destination;
		return returnValue;
	}

	// sebesseg modositasa egyszeru szorzassal
	public void mulSpeed(double mul) {
		speed *= mul;
	}

	public String toString() {
		return "Mover(speed = " + speed + ", destination = "
				+ destination.toString() + ", ...)";
	}
	
	public Field getDestination(){
		return destination;
	}
	
	public int getDirectionIndex(){
		// 0 down, 1 up, 2 left, 3 right
		
		if(Math.abs(directionX) > Math.abs(directionY)){
			if(directionX < 0)
				return 2;
			else
				return 3;
		} else {
			if(directionY < 0)
				return 1;
			else
				return 0;
		}
	}
}