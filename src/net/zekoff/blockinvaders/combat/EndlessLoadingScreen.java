package net.zekoff.blockinvaders.combat;

import net.zekoff.androidgames.framework.impl.BlockInvadersGame;

/**
 * Performs all the same functions as the CampaignLoadingScreen, but overrides
 * the update() method to transfer control to a new EndlessScreen instead of a
 * CampaignScreen.
 * 
 * @author Zekoff
 * 
 */
public class EndlessLoadingScreen extends CampaignLoadingScreen {

	public EndlessLoadingScreen(BlockInvadersGame game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		game.setScreen(new EndlessScreen(game));
	}

}
