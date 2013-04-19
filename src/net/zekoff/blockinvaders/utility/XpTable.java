package net.zekoff.blockinvaders.utility;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.player.Rewards;

/**
 * Provides static methods related to XP curves.
 * 
 * @author Zekoff
 * 
 */
public class XpTable {

	public static ArrayList<Integer> xpPerLevel = new ArrayList<Integer>();
	public static final int NUMBER_OF_XP_LEVELS;

	static {
		xpPerLevel.add(0); // to get from level 0 to 1 takes 0 xp
		xpPerLevel.add(2000); // from level 1 to 2...
		xpPerLevel.add(3000);
		xpPerLevel.add(4000);
		xpPerLevel.add(5000);
		xpPerLevel.add(6000);
		xpPerLevel.add(7000); // from level 6 to 7...
		xpPerLevel.add(8000);
		xpPerLevel.add(9000);
		xpPerLevel.add(10000);
		// this brings the player to level 10
		xpPerLevel.add(11000); // from 10 to 11
		xpPerLevel.add(12000);
		xpPerLevel.add(13000);
		xpPerLevel.add(14000);
		xpPerLevel.add(15000);
		xpPerLevel.add(15000); // from 15 to 16
		xpPerLevel.add(15000);
		xpPerLevel.add(15000);
		xpPerLevel.add(15000);
		xpPerLevel.add(15000);
		xpPerLevel.add(15000); // 20 to 21
		xpPerLevel.add(16000);
		xpPerLevel.add(17000);
		xpPerLevel.add(18000);
		xpPerLevel.add(19000);
		xpPerLevel.add(19500); // 25 to 26
		xpPerLevel.add(20000);
		xpPerLevel.add(20000);
		xpPerLevel.add(20000);
		xpPerLevel.add(20000); // from 29 to 30
		// maximum level is 30

		NUMBER_OF_XP_LEVELS = xpPerLevel.size();
	}

	/**
	 * A static formula that returns the amount of XP gained for killing an
	 * enemy.
	 * 
	 * @param playerLevel
	 *            The player's level.
	 * @param enemyLevel
	 *            The enemy's level.
	 * @param enemyModifier
	 *            A float value indicating what percentage modifier should be
	 *            applied to the standard XP gain given from killing an enemy.
	 *            This value is a field of the enemy. 1 is standard (no
	 *            modification).
	 * @return The final amount of XP gained for killing the enemy.
	 */
	public static int xpGain(int playerLevel, int enemyLevel,
			float enemyModifier) {
		if (playerLevel == enemyLevel) {
			return (int) (100 * enemyModifier);
		} else if (playerLevel < enemyLevel) {
			float xp = enemyLevel - playerLevel;
			xp *= xp;
			xp *= 3;
			xp += 100;
			xp *= enemyModifier;
			return (int) xp;
		} else if (playerLevel > enemyLevel) {
			float xp = playerLevel - enemyLevel;
			xp *= xp;
			xp *= 4;
			xp = 100 - xp;
			xp *= enemyModifier;
			if (xp < 1)
				xp = 1;
			return (int) xp;
		} else
			return 1; // error case
	}

	public static ArrayList<Rewards> checkLevelGain() {
		int level = Assets.pilot.level;
		int xp = Assets.pilot.xp;
		int levelTally = 0;
		for (int i = 1; xp >= 0; i++) {
			levelTally++;
			if (xpPerLevel.size() > i)
				xp -= xpPerLevel.get(i);
			else
				break;
		}
		if (levelTally > level) {
			ArrayList<Rewards> rewardList = new ArrayList<Rewards>();
			for (; level < levelTally; level++) {
				Assets.pilot.level++;
				rewardList.add(getRewards(Assets.pilot.level));
			}
			return rewardList;
		} else
			return null;
	}

	/**
	 * @param level
	 *            The level at which the player receives the awards. A player
	 *            that has just reached level 2 receives level 2 rewards, etc.
	 * @return A Rewards object containing integers and Strings representing
	 *         what was awarded to the player based on this level gain. The
	 *         receiving object parses the list for display.
	 */
	private static Rewards getRewards(int level) {
		Rewards rewards = new Rewards();
		rewards.level = level;
		rewards.hpGained = StatLookup.lookup(level, 100)
				- StatLookup.lookup(level - 1, 100);
		switch (level) {
		case 1:
			// this is actually an error
			break;
		case 2:
			rewards.abilitiesGained.add("Time Warp");
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		}
		return rewards;
	}

	public static float getPercentageToLevel() {
		int level = Assets.pilot.level;
		if (level > xpPerLevel.size() - 1)
			return -1; // error case
		int xp = Assets.pilot.xp;
		int xpToCurrentLevel = 0;
		for (int i = 1; i < level; i++) {
			xpToCurrentLevel += xpPerLevel.get(i);
		}
		return (xp - xpToCurrentLevel) / (float) xpPerLevel.get(level);
	}

	public static int getXpToLevel(int level) {
		int xpToCurrentLevel = 0;
		for (int i = 0; i < level; i++) {
			xpToCurrentLevel += xpPerLevel.get(i);
		}
		return xpToCurrentLevel;
	}
}
