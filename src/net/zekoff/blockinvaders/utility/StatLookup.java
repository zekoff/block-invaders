package net.zekoff.blockinvaders.utility;

/**
 * Calculates how much HP a player or enemy has at level X, where X is the
 * argument provided at runtime.
 * 
 * @author Zekoff
 * 
 */
public class StatLookup {
	/**
	 * This is the value that HP values, weapon DPS, etc. grows between levels.
	 */
	public static final float percentage = 0.10f;

	/**
	 * At level 1, the amount of damage that a standard weapon should do in 1
	 * second.
	 */
	public static final int rawDps = 20;

	/**
	 * At level 1, the amount of health that the player should have.
	 */
	public static final int rawHp = 100;

	public static int lookup(int level, int value) {
		if (level > 1) {
			int newValue = (int) Math.ceil(value * (1 + percentage));
			return lookup(level - 1, newValue);
		} else
			return value;
	}

}
