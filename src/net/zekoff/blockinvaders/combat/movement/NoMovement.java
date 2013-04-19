package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Enemy instantly stops moving.
 * 
 * @author Zekoff
 * 
 */
public class NoMovement extends MovementOrders {

	MovementCondition mc = null;

	public NoMovement() {

	}

	public NoMovement(MovementCondition movementCondition) {
		this.mc = movementCondition;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		// do nothing

		if (mc != null) {
			boolean condition = mc.checkCondition(self, deltaTime);
			if (condition)
				isFinished = true;
		}
	}
}
