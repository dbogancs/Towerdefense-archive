package runes;

import java.util.Map;

import enumerations.DamageType;

// rúna osztály
// tárolja, hogy milyen tulajdonságokat változtat meg
// a tulajdonságok frissítésére függvényeket biztosít
// pl. amikor egy PhysicalObstacle-re rakjuk, akkor az a receiveRune( ) függvényében meghívja a getNewHp( ) függvényt
public class Rune {
	// -------- ATTRIBÚTUMOK ---------
	private int HPChange;

	private int attackRangeChange;
	private int attackSpeedChange;

	private long timeLeftChange;

	private DamageType changedDamageType;
	private double damageChange;

	private int manaValue = 10;

	// -------- KONSTRUKTOR ---------
	public Rune(int hp, int range, int speed, int time, DamageType type,
			double damage) {

		this.HPChange = hp;
		this.attackRangeChange = range;
		this.attackSpeedChange = speed;
		this.timeLeftChange = time;
		this.changedDamageType = type;
		this.damageChange = damage;
	}

	// --------RUNA TÍPUSOK ---------
	public static Rune getHPBooster(int hp) {
		Rune returnValue = new Rune(hp, 0, 0, 0, null, 0);
		return returnValue;
	}

	public static Rune getRangeBooster(int range) {
		Rune returnValue = new Rune(0, range, 0, 0, null, 0);
		return returnValue;
	}

	public static Rune getSpeedBooster(int speed) {
		Rune returnValue = new Rune(0, 0, speed, 0, null, 0);
		return returnValue;
	}

	public static Rune getLifetimeBooster(int time) {
		Rune returnValue = new Rune(0, 0, 0, time, null, 0);
		return returnValue;
	}

	public static Rune getDamageBooster(DamageType type, double damage) {
		Rune returnValue = new Rune(0, 0, 0, 0, type, damage);
		return returnValue;
	}

	public int getManaValue() {
		return manaValue;
	}

	/* -------- FRISS�T� F�GGV�NYEK --------- */
	public long getNewTimeLeft(long timeLeft) {
		long newTimeLeft = timeLeft + timeLeftChange;
		return newTimeLeft;
	}
	
	public long getTimeLeftChange(){
		return timeLeftChange;
	}

	public int getNewHP(int oldHP) {
		int newHP = oldHP + HPChange;
		return newHP;
	}
	
	public int getHPChange(){
		return HPChange;
	}

	public int getNewAttackRange(int oldAttackRange) {
		int newAttackRange = oldAttackRange + attackRangeChange;
		return newAttackRange;
	}
	
	public int getAttackRangeChange(){
		return attackRangeChange;
	}

	// BraveBehavior k�rte
	public int getNewAttackSpeed(long cooldown) {
		int newAttackSpeed = (int) (cooldown + attackSpeedChange);
		return newAttackSpeed;
	}
	
	public int getAttackSpeedChange(){
		return attackSpeedChange;
	}

	/*public int getNewTimeLeft(int oldTimeLeft) {
		int newTimeLeft = (int) (oldTimeLeft + timeLeftChange);
		return newTimeLeft;
	}*/

	// visszat�r�si �rt�k a BraveBehavior miatt
	public Map<DamageType, Double> updateDamage(Map<DamageType, Double> damages) {
		damages.put(changedDamageType, damages.get(changedDamageType)
				+ damageChange);
		return damages;
	}
	
	public DamageType getChangedDamageType(){
		return changedDamageType;
	}
	
	public double getDamageChange(){
		return damageChange;
	}
	

	public String toString() {
		return "Rune(HPChange = "
				+ HPChange
				+ ", attackRangeChange = "
				+ attackRangeChange
				+ ", attackSpeedChange = "
				+ attackSpeedChange
				+ ", timeLeftChange"
				+ timeLeftChange
				+ ", changedDamageType = "
				+ (changedDamageType == null ? "null" : changedDamageType
						.toString()) + ", damageChange = " + damageChange + ")";
	}
}