package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.hud.FlyupListener;

/**
 * @author Zekoff
 * 
 */
public abstract class CampaignLevel extends Level implements FlyupListener {

	CampaignScreen screen = null;

	public CampaignLevel(CampaignScreen screen) {
		this.screen = screen;
		// setup background if necessary
	}

	@Override
	public abstract void update(float deltaTime);

	@Override
	public void draw(Graphics g) {

	}

	@Override
	public void missionComplete() {
		screen.missionComplete = true;
	}

	public void onFlyupOpen() {

	}

	public void onFlyupClosed() {

	}

	public void onChoice(String choice) {

	}

	/**
	 * All briefings are given here, because briefings are not allowed to change
	 * based on past performance. If that sort of branching is desired, make an
	 * entirely different level.
	 * 
	 * @return The mission briefing as a String
	 */
	public abstract String getBriefing();

	/**
	 * Each campaign level implements its own debriefing. The reason for this is
	 * so that the level can provide customized debriefings based on level
	 * performance if necessary, including choices made during the level.
	 * 
	 * @return A String containing the debriefing for the level.
	 */
	public abstract String getDebriefing();

	/**
	 * Updates pilot profile to reflect rewards gained for completing this
	 * level, and returns a list of those rewards.
	 * 
	 * @return A list of the rewards gained as strings, after activating those
	 *         rewards in this method.
	 */
	public abstract ArrayList<String> getRewards();

	/**
	 * Convenience method to add an Enemy to the enemy list in the
	 * EntityManager.
	 * 
	 * @param e
	 *            The Enemy to add to the EntityManager.
	 */
	public void addEnemy(Enemy e) {
		Assets.em.enemies.add(e);
	}

	/**
	 * Convenience method to test whether or not any enemies remain in the
	 * EntityManager.
	 * 
	 * @return Boolean indicating whether the entity manager enemy list is
	 *         empty.
	 */
	public boolean noEnemies() {
		return Assets.em.enemies.size() == 0;
	}

}
