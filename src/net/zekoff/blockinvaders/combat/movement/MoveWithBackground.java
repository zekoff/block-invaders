package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Enemy moves in exact sync with the background, making the enemy appear
 * motionless with respect to the movement of the level.
 * 
 * @author Zekoff
 * 
 */
public class MoveWithBackground extends MovementOrders {

	MovementCondition mc = null;

	MoveWithBackground(MovementCondition mc) {
		this();
		this.mc = mc;
	}

	public MoveWithBackground() {
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		self.yVel = Assets.background.speed;
		self.y += self.yVel * deltaTime;

		// check mc
		if (mc != null) {
			boolean condition = mc.checkCondition(self, deltaTime);
			if (condition)
				isFinished = true;
		}
	}

}
