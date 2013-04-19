package net.zekoff.blockinvaders.combat.weapon;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Color;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.loot.Loot;
import net.zekoff.blockinvaders.combat.perk.Perk;
import net.zekoff.blockinvaders.utility.Blueprint;
import net.zekoff.blockinvaders.utility.StatLookup;
import net.zekoff.blockinvaders.utility.XpTable;

/**
 * Defines the behavior that a WeaponBlueprint must provide. All weapons have a
 * type of LootType.weapon, and must offer a getWeapon() method that returns a
 * concrete Weapon instantiation ready to mount on a player ship.
 * 
 * @author Zekoff
 * 
 */
public abstract class WeaponBlueprint implements Serializable, Loot, Blueprint {
	private static final long serialVersionUID = 2296651446524330211L;

	public String name = "Weapon";
	public int level = 1;
	public int xp = 0;
	public int xpGained = 0;
	public int upgradeLevel = 0;
	public ArrayList<Integer> xpPerLevel = new ArrayList<Integer>();
	public static final int MAX_LEVELS = 10;
	public static final int BASE_VALUE = 400;

	public float quality = STANDARD_QUALITY;

	public enum WeaponType {
		main_cannon, spread_shot, multi_shot, burst_shot, rapid_shot, blaster, laser, tri_shot, missile_battery, wave_shot
	}

	public WeaponBlueprint(int level) {
		// xpPerLevel.add(200);
		// xpPerLevel.add(300);
		// xpPerLevel.add(400);
		this.level = level;
		for (int i = 0; i < MAX_LEVELS; i++) {
			int toRetrieve = level;
			// to prevent array out of bounds from looking up player XP table
			if (toRetrieve > 29)
				toRetrieve = 29;
			xpPerLevel.add((int) (XpTable.xpPerLevel.get(toRetrieve) * 0.3));
		}
	}

	public int getRating() {
		Weapon w = getWeapon();
		setPower(w);
		return (int) w.power;
	}

	/**
	 * The Weapon returned by this method is specific and implemented based on
	 * the subclass. All weapons returned by this method should be ready-to-use;
	 * i.e., they must have already accounted for the appropriate perks, etc.
	 * that the player has equipped.
	 * 
	 * @return A instance of Weapon ready for use by the combat engine.
	 */
	public abstract Weapon getWeapon();

	@Override
	public LootType getType() {
		return LootType.weapon;
	}

	@Override
	public float getQuality() {
		return quality;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getName() {
		return toString();
	}

	/**
	 * Method for subclasses to easily set up perks as they apply to standard
	 * weapons. This method should be called after all other base weapon
	 * attributes have already been set up, since it modifies the existing
	 * attributes of a weapon by percentages.
	 * 
	 * @param w
	 *            The weapon to modify with the pilot's current perk selections.
	 */
	public void setupPerks(Weapon w) {
		Perk p = null;

		p = Assets.pilot.hasPerkActive("Itchy Trigger Finger");
		if (p != null) {
			w.cooldown *= 1 - p.getRankEffect(p.rank);
		}

		p = Assets.pilot.hasPerkActive("Well-Researched");
		if (p != null) {
			w.power *= 1 + p.getRankEffect(p.rank);
		}
	}

	public float getPercentageToLevel() {
		if (upgradeLevel > xpPerLevel.size() - 1)
			return -1; // error case
		int xpToCurrentLevel = 0;
		for (int i = 0; i < upgradeLevel; i++) {
			xpToCurrentLevel += xpPerLevel.get(i);
		}
		return (xp - xpToCurrentLevel) / (float) xpPerLevel.get(upgradeLevel);
	}

	public int checkLevelGain() {
		int level = this.upgradeLevel;
		int xp = this.xp;
		int levelTally = -1;
		for (int i = 0; xp >= 0; i++) {
			levelTally++;
			if (xpPerLevel.size() > i)
				xp -= xpPerLevel.get(i);
			else
				break;
		}
		int levelsGained = 0;
		if (levelTally > level) {
			for (; level < levelTally; level++) {
				// upgrade weapon here; call subroutine
				upgradeWeapon();
				levelsGained++;
			}
			return levelsGained;
		} else
			return 0;
	}

	private void upgradeWeapon() {
		upgradeLevel++;
	}

	public void addXp(int xp) {
		this.xpGained += xp;
	}

	public void commitXp() {
		xp += xpGained;
		xpGained = 0;
	}

	public void setPower(Weapon w) {
		float power = StatLookup.lookup(level, StatLookup.rawDps);
		// increase power by 10% per level of weapon quality
		power *= 1 + (quality * .1);
		// increase power by percentage per upgrade level
		power *= 1 + ((upgradeLevel) * StatLookup.percentage * 0.5);
		w.power = power;
	}

	public abstract String getTypeName();

	public int getQualityColor() {
		if (quality == JUNK_QUALITY)
			return 0xFF9D9D9D;
		if (quality == STANDARD_QUALITY)
			return Color.WHITE;
		if (quality == UNCOMMON_QUALITY)
			return 0xFF1EFF00;
		if (quality == RARE_QUALITY)
			return 0xFF0070DD;
		if (quality == EPIC_QUALITY)
			return 0xFFA335EE;
		if (quality == LEGENDARY_QUALITY)
			return 0xFFFF8000;
		return 0;
	}

	public String getQualityName() {
		if (quality == JUNK_QUALITY)
			return "Junk (quality 0)";
		if (quality == STANDARD_QUALITY)
			return "Standard-issue (quality 1)";
		if (quality == UNCOMMON_QUALITY)
			return "High Quality (quality 2)";
		if (quality == RARE_QUALITY)
			return "Custom Built (quality 3)";
		if (quality == EPIC_QUALITY)
			return "Masterwork (quality 4)";
		if (quality == LEGENDARY_QUALITY)
			return "Legendary (quality 5)";
		return null;
	}

	public int getValue() {
		int value = StatLookup.lookup(level, BASE_VALUE);
		if (upgradeLevel < MAX_LEVELS) {
			value += (upgradeLevel * .1 * value);
		} else {
			value += (upgradeLevel * .2 * value);
		}
		value *= 1 + (2 * quality * .1);
		return value;
	}
}
