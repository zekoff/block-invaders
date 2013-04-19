package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Condition is met when the enemy's x- and y-velocity is 0.
 * 
 * @author Zekoff
 * 
 */
public class StoppedCondition extends MovementCondition {
	@Override
	public boolean checkCondition(Enemy self, float deltaTime) {
		if (self.xVel == 0 && self.yVel == 0)
			return true;
		return false;
	}
}
