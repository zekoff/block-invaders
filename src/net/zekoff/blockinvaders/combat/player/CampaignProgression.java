package net.zekoff.blockinvaders.combat.player;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.level.CampaignLevel;

/**
 * Manages data associated with the player's progression through the Campaign.
 * This object keeps track of campaign-related variables. It is itself stored as
 * a variable in the Pilot object. Starting a new campaign replaces it with a
 * fresh copy of the object. One of its primary methods is to return the
 * appropriate CampaignLevel for the player's current position in the campaign.
 * 
 * @author Zekoff
 * 
 */
public class CampaignProgression implements Serializable {
	public int campaignLevel = 1;
	public boolean newGamePlus = false;

	/**
	 * HashMap for keeping up with completed levels and their records. Keyed by
	 * integers representing level number.
	 */
	public HashMap<Integer, CampaignLevelRecord> levels = new HashMap<Integer, CampaignLevelRecord>();

	/**
	 * Returns the level associated with whatever integer level number passed as
	 * a parameter. Uses reflection in combination with an integer value to
	 * instantiate the proper CampaignLevel object.
	 * 
	 * @param screen
	 *            The CampaignScreen that will run the level, so that it can be
	 *            passed to the CampaignLevel.
	 * @param level
	 *            The level number to instantiate.
	 * @return A CampaignLevel object appropriate to the level number requested.
	 */
	public CampaignLevel getLevel(CampaignScreen screen, int level) {
		String levelString = "net.zekoff.blockinvaders.combat.level.CampaignLevel";
		int levelNumber = campaignLevel;

		// next statement is only for debugging while limited levels exist
		if (levelNumber > 4)
			levelNumber = 4;

		levelString = levelString + levelNumber;
		CampaignLevel theLevel = null;
		Class<CampaignLevel> clazz = null;
		try {
			clazz = (Class<CampaignLevel>) Class.forName(levelString);
			Constructor<CampaignLevel> c = clazz
					.getConstructor(CampaignScreen.class);
			theLevel = c.newInstance(screen);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theLevel;
	}

	/**
	 * A convenience method that returns the current CampaignLevel.
	 * 
	 * @param screen
	 *            The CampaignScreen that will run the level, so that it can be
	 *            passed to the CampaignLevel.
	 * @return A CampaignLevel object appropriate to the player's current
	 *         position in the campaign.
	 */
	public CampaignLevel getCurrentLevel(CampaignScreen screen) {
		return getLevel(screen, campaignLevel);
	}
}
