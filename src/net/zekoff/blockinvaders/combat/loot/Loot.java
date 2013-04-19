package net.zekoff.blockinvaders.combat.loot;

/**
 * Anything that can be dropped by an enemy, placed in a Pickup, and acquired by
 * the player, is marked with the Loot interface.
 * 
 * @author Zekoff
 * 
 */
public interface Loot {

	public static final float JUNK_QUALITY = -5;
	public static final float STANDARD_QUALITY = -2;
	public static final float UNCOMMON_QUALITY = 0;
	public static final float RARE_QUALITY = 1.5f;
	public static final float EPIC_QUALITY = 3;
	public static final float LEGENDARY_QUALITY = 5;

	public enum LootType {
		junk, weapon, shield, armor, money
	}

	public LootType getType();

	public float getQuality();

}
