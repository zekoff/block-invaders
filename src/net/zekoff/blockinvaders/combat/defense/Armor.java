package net.zekoff.blockinvaders.combat.defense;

import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;

public class Armor {
	/**
	 * A number representing a flat damage reduction value from 0 to anything.
	 */
	public float flatReduction = 0;
	/**
	 * A number between 0 and 1 representing what percentage to reduce incoming
	 * damage by.
	 */
	public float percentageReduction = 0;

	/**
	 * Take the amount of damage being dealt, reduce it accordingly, and return
	 * the result.
	 * 
	 * @param power
	 *            The unmodified amount of damage that would be done.
	 * @param damageType
	 *            The DamageType of the bullet (to check if it's piercing).
	 * @return The amount of damage done after reduction from armor.
	 */
	public float takeDamage(float power, DamageType damageType) {
		if (damageType != DamageType.piercing) {
			// first, do flat reduction
			power -= flatReduction;
			// next, do percentage reduction
			power *= 1 - percentageReduction;
			// return final value (can never do less than 1 dmg)
			if (power < 1)
				power = 1;
			return power;
		} else
			return power;
	}

}
