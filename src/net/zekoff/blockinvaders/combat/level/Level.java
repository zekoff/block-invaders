package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Abstract class from which all levels of the game must derive. Guarantees an
 * update() and draw() method. Otherwise, has full access to all relevant
 * components of the combat engine via the Assets class, and can implement any
 * methods or fields necessary to accomplish whatever it needs for constructing
 * a level.
 * 
 * @author Zekoff
 * 
 */
public abstract class Level {

	@Override
	public String toString() {
		return "Sample Level";
	}

	/**
	 * Once per frame, the current Level object gets a chance to survey
	 * conditions of the game and update itself and the game as necessary. It
	 * can check for player movement, enemy numbers, boss health, etc.
	 * 
	 * @param deltaTime
	 */
	public abstract void update(float deltaTime);

	/**
	 * The level also gets a chance to draw anything to the screen that it needs
	 * (example: incoming boss message, story exposition, etc.).
	 * 
	 * @param g
	 */
	public abstract void draw(Graphics g);

	public void registerKilled(Enemy e) {
		// do nothing
	}

	public void missionComplete() {
		Assets.game.gameOver(BlockInvadersGame.VICTORY);
	}

	public void missionFailed() {
		Assets.game.gameOver(BlockInvadersGame.DEFEAT);
	}
}
