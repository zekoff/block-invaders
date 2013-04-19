package net.zekoff.blockinvaders.combat.defense;

import java.io.Serializable;

import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.loot.Loot;
import net.zekoff.blockinvaders.utility.Blueprint;
import net.zekoff.blockinvaders.utility.StatLookup;

public class ShieldBlueprint implements Serializable, Loot, Blueprint {
	/**
	 * Maximum number of units the shield charges to.
	 */
	public float maxCharge = 1;
	/**
	 * Units the shield regenerates per second.
	 */
	public float regenRate = 1;
	/**
	 * Number of seconds the shield remains disabled after being completely
	 * depleted.
	 */
	public float recoveryTime = 5f;
	public float quality = Loot.STANDARD_QUALITY;
	public int level = 1;
	public static float BASE_CHARGE = StatLookup.rawDps * 2;
	public String name = "Shield";
	public static int BASE_VALUE = 500;

	public Shield getShield() {
		Shield s = new Shield();
		s.maxCharge = StatLookup.lookup(level, StatLookup.rawDps) * 2;
		s.currentCharge = s.maxCharge;
		s.regenRate = s.maxCharge / 10;
		s.recoveryTime = recoveryTime;

		// do anything related to perks

		return s;
	}

	/**
	 * For use by Enemy objects; gets a generic Shield of appropriate power for
	 * an enemy to use.
	 * <p>
	 * Basic enemy shields have 1.5x the enemy's max HP in charge.
	 * 
	 * @param level
	 *            The level of the enemy and thus the shield.
	 * @return A Shield prepared for the enemy to use.
	 */
	public static Shield getEnemyShield(int level) {
		Shield s = new Shield();
		s.maxCharge = StatLookup.lookup(level, Enemy.BASE_HP) * 1.5f;
		s.currentCharge = s.maxCharge;
		s.regenRate = s.maxCharge / 10;
		s.recoveryTime = 15;
		s.enemyShield = true;
		return s;
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getRating() {
		// TODO implement
		return 0;
	}

	@Override
	public int getValue() {
		// TODO implement
		return 0;
	}

	@Override
	public LootType getType() {
		return Loot.LootType.shield;
	}

	@Override
	public float getQuality() {
		return quality;
	}
}
