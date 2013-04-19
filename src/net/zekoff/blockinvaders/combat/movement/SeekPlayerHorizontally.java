package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.ship.Ship;

/**
 * Enemy stays centered above the player horizontally to some degree.
 * 
 * @author Zekoff
 * 
 */
public class SeekPlayerHorizontally extends MovementOrders {

	/**
	 * Aggression with which the enemy should seek to remain above the player.
	 * 
	 * @author Zekoff
	 * 
	 */
	public enum SeekAggression {
		lazy, normal, aggressive
	}

	MovementCondition mc = null;
	SeekAggression aggression = SeekAggression.normal;

	public SeekPlayerHorizontally(SeekAggression aggressionLevel) {
		this.aggression = aggressionLevel;
	}

	public SeekPlayerHorizontally(SeekAggression aggressionLevel,
			MovementCondition mc) {
		this.mc = mc;
		this.aggression = aggressionLevel;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		// Determine whether enemy needs to move itself or not
		int needsToMove = 0;
		int acceptableRange = 10;
		Ship player = Assets.playerShip;
		switch (aggression) {
		case lazy:
			acceptableRange = 70;
			break;
		case normal:
			acceptableRange = 40;
			break;
		case aggressive:
			acceptableRange = 10;
			break;
		}
		if (self.x > player.x + acceptableRange)
			needsToMove = -1;
		else if (self.x < player.x - acceptableRange)
			needsToMove = 1;

		// If enemy needs to move nearer to the player...
		switch (aggression) {
		case lazy:
			self.xVel = self.speed / 2 * needsToMove;
			break;
		case normal:
			self.xVel = (float) (self.speed / 1.5 * needsToMove);
			break;
		case aggressive:
			self.xVel = self.speed * needsToMove;
			break;
		}

		// Finally, move
		self.x += self.xVel * deltaTime;

		// check movement conditions
		if (mc != null) {
			boolean condition = mc.checkCondition(self, deltaTime);
			if (condition)
				isFinished = true;
		}

	}
}
