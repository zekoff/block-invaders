package net.zekoff.blockinvaders.combat.player;

import java.io.Serializable;
import java.util.ArrayList;

public class CampaignLevelRecord implements Serializable {

	/**
	 * Contains bonuses achieved during the level, like 100% kill rate, speedy
	 * finish, etc.
	 */
	public ArrayList<String> levelBonuses = new ArrayList<String>();

	/**
	 * Contains choices made during the level, either by choosing an option
	 * during a FlyupArea Choice, or by accomplishing/not accomplishing some
	 * optional objective.
	 */
	public ArrayList<String> levelChoices = new ArrayList<String>();
}
