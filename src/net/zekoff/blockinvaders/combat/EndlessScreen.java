package net.zekoff.blockinvaders.combat;

import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.combat.background.DynamicStarfield;
import net.zekoff.blockinvaders.combat.level.EndlessLevel;
import net.zekoff.blockinvaders.combat.level.Level;

/**
 * The gameplay Screen, tailored for Endless mode. Currently inherits all
 * behavior of the CampaignScreen, except for the loading of the Level asset
 * from the campaign progression. Endless mode uses a different Level tailored
 * to this mode.
 * 
 * @author Zekoff
 * 
 */
public class EndlessScreen extends CampaignScreen {

	public EndlessScreen(BlockInvadersGame game) {
		super(game);

		// TODO currently, the call to super() loads the background associated
		// with the current campaign level.
		Assets.background = new DynamicStarfield(40);

		// Load endless level
		Assets.level = new EndlessLevel(this);
	}

}
