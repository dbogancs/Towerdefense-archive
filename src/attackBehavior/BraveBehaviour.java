package attackBehavior;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import map.Field;
import map.Position;
import movers.Mover;
import entities.Attackable;
import entities.Bullet;
import enumerations.DamageType;
import enumerations.Side;
import graphics.GraphicsHandler;
import runes.IUpgradable;
import runes.Rune;

//bátor
public class BraveBehaviour extends AttackBehaviour implements IUpgradable {
	
	/* -------- TAGVÁLTOZÓK -------- */
	private int attackRange; // hatótávolság (a legnagyobb távolság, amire még
								// tud támadni)
	private int areaOfExplosion; // lövedék sebzési környezete

	private boolean isAttacking; // támad épp vagy nem?
	private long timeLeft; // ha támad: a támadásból hátralévő idő (animáció )
							// ha befejezett egy tamadast: a kovetkezo tamadasig
							// hatralevo ido
	private long attackTime; // mennyi ideig tart egy teljes támadás (fajfüggő),
								// konstruálástól fix

	private Map<DamageType, Double> damages; // tárolja fajonként a sebzést

	private Field currentTarget; // az aktuális célpont (támadás végén erre
									// lőjük ki a lövedéket)

	private long cooldown; // tamadas befejezese es kovetkezo elkezdese kozotti
							// ido

	/* -------- KONSTRUKTOR -------- */
	public BraveBehaviour(Side side, int range, long aTime, long cTime,
			int AOE, Map<DamageType, Double> dmg) {
		super(side);

		this.attackRange = range;
		this.areaOfExplosion = AOE;
		this.attackTime = aTime;
		this.cooldown = cTime;

		this.isAttacking = false;
		this.currentTarget = null;

		timeLeft = 0;
		damages = dmg;
	}
	
	public long getCoolDown() {return cooldown;}
	public long getTimeLeft() {return timeLeft;}

	public BraveBehaviour(BraveBehaviour other) {
		super(other);

		this.attackRange = other.attackRange;
		this.areaOfExplosion = other.areaOfExplosion;
		this.attackTime = other.attackTime;
		this.cooldown = other.cooldown;

		this.isAttacking = other.isAttacking;
		this.currentTarget = other.currentTarget;

		timeLeft = other.timeLeft;
		damages = other.damages;
	}

	

	/* -------- TAGFÜGGVÉNYEK -------- */
	// támadás
	public AttackPhase attack(Field mezo, long dt) {	
		// ha tamadtunk, es most varni kell a cooldownra
		if (!isAttacking && timeLeft > 0) {
			timeLeft -= dt;
			
			return AttackPhase.COOLDOWN;
		}
		
		// ha épp szabad (nem támad senkit) es lejart a cooldown
		if (!isAttacking && timeLeft <= 0) {
			// keress új célpontot

			currentTarget = findNewTarget(mezo, attackRange);
			// findNewTarget: privát függvény, ami egy adott mezőből kiindulva
			// visszaadja
			// a max. adott távolságra lévő egyik ellenfelet (vagy null-t)

			// ha nem találtál: térj vissza false-szal
			if (currentTarget == null) {
				return AttackPhase.NONE;
			}

			// egyébként meg jegyezd meg, hogy támadsz és állíts be egy
			// számlálót (idő)
			isAttacking = true;
			timeLeft = attackTime;
		}
		
		// ha épp támadunk
		if (isAttacking) {
			// állítsuk be a megfelelő képet
			// animateAttacking( dt );

			// frissítsük a hátralévő időt
			timeLeft -= dt;

			// ha végeztünk: lövedék, majd takarítsunk ki
			if (timeLeft <= 0) {
				Bullet newBullet = new Bullet(mezo, damages, 10, dt, GraphicsHandler.getImageByName("bullet_2")); // Bullet(Field, Map<DamageType, Double>, int AOE, long dt)
				mezo.registerBullet(newBullet);
				Mover mov = new Mover(newBullet, currentTarget, 1,
						new Position((byte) 0, (byte) 0));
				newBullet.setMover(mov); // Mover(Entity e, Field dest, int s,
											// Map.Position p)

				isAttacking = false;
				currentTarget = null;
				timeLeft = cooldown;
			}

			return AttackPhase.ATTACKING;
		}
		
		return AttackPhase.ATTACKING;
	}

	// új célpont keresése
	private Field findNewTarget(Field mezo, int attackRange) {
		// new target field
		Field returnValue = null;

		// BFS
		double distance = 0; // distance variable
		ArrayList<Field> FIFO = new ArrayList<Field>(); // FIFO for BFS
		ArrayList<Field> visitedFields = new ArrayList<Field>(); // list for storing visited fields (=> clearing 'visited' variable after BFS)
		
		// init
		FIFO.add(mezo); visitedFields.add(mezo); mezo.visited = true;
		
		// start BFS
		while(!FIFO.isEmpty() && (returnValue == null)){			
			// get current field
			Field currentField = FIFO.remove(0);
			currentField.visited = true;
			distance = Field.fieldSize * Math.sqrt((currentField.x - mezo.x) * (currentField.x - mezo.x) + (currentField.y - mezo.y) * (currentField.y - mezo.y));

			// do not go too far
			if(distance > attackRange)
				break;

			// check current field
			List<Attackable> attackables = currentField.getAttackables();
			if(attackables != null)
				for(Attackable a : attackables)
					if(a.getSide() != side){
						returnValue = currentField;
						break;
					}
			
			// store neighbours
			if((currentField.left != null) && (currentField.left.visited != true)){
				FIFO.add(currentField.left);
				visitedFields.add(currentField.left);
			}
			
			if((currentField.right != null) && (currentField.right.visited != true)){
				FIFO.add(currentField.right);
				visitedFields.add(currentField.right);
			}
			
			if((currentField.up != null) && (currentField.up.visited != true)){
				FIFO.add(currentField.up);
				visitedFields.add(currentField.up);
			}
			
			if((currentField.down != null) && (currentField.down.visited != true)){
				FIFO.add(currentField.down);
				visitedFields.add(currentField.down);
			}
		}
		
		// cleanup
		for(Field f : visitedFields)
			f.visited = false;
		
		return returnValue;
	}

	// rúna fogadása
	public void receiveRune(Rune r) {
		attackRange = r.getNewAttackRange(attackRange);
		cooldown = r.getNewAttackSpeed(cooldown);
		damages = r.updateDamage(damages);
	}

	// konvertálás stringgé
	public String toString() {
		return "BraveBehaviour(attackRange = " + attackRange + ", AOE = "
				+ areaOfExplosion + ", attackTime = " + attackTime + ")";
	}

	@Override
	public AttackBehaviour copy() {
		return new BraveBehaviour(this);
	}

	@Override
	public boolean isAttacking() {
		return isAttacking;
	}
}
