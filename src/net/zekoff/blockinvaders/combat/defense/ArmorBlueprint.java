package net.zekoff.blockinvaders.combat.defense;

import java.io.Serializable;

import net.zekoff.blockinvaders.combat.loot.Loot;
import net.zekoff.blockinvaders.utility.Blueprint;
import net.zekoff.blockinvaders.utility.StatLookup;

public class ArmorBlueprint implements Serializable, Loot, Blueprint {
	public float flatReduction = 0;
	public float percentageReduction = 0;
	public String name = "Armor";
	public float quality = Loot.STANDARD_QUALITY;
	public int level = 1;
	public static float BASE_REDUCTION = StatLookup.rawDps * .25f;
	public static int BASE_VALUE = 500;

	public Armor getArmor() {
		Armor a = new Armor();
		a.percentageReduction = percentageReduction;
		// do anything related to perks that needs to be done

		a.flatReduction = StatLookup.lookup(level, StatLookup.rawDps) * .25f;
		return a;
	}

	/**
	 * Gets an armor for an enemy to use. Armor gives the enemy 50% dmg
	 * reduction.
	 * 
	 * @param level
	 *            The level of the Enemy and thus the armor.
	 * @return An Armor for the enemy to use.
	 */
	public static Armor getEnemyArmor(int level) {
		Armor a = new Armor();
		a.percentageReduction = .7f;
		return a;
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
		return Loot.LootType.armor;
	}

	@Override
	public float getQuality() {
		return quality;
	}
}
