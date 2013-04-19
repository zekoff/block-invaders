package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * An implementation of a MovementOrder which accepts an X- and Y-coordinate in
 * the constructor, moves the Enemy toward that point at its normal speed, and
 * marks itself for removal when it has reached its destination.
 * 
 * @author Zekoff
 * 
 */
public class MoveTo extends MovementOrders {
	float xDest;
	float yDest;
	MovementCondition movementCondition = null;

	public MoveTo(float xDest, float yDest) {
		this.xDest = xDest;
		this.yDest = yDest;
	}

	public MoveTo(float xDest, float yDest, MovementCondition movementCondition) {
		this(xDest, yDest);
		this.movementCondition = movementCondition;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		// TODO make movement smooth
		double yDist;
		double xDist;

		// set yDist
		yDist = yDest - self.y;

		// set xDist
		xDist = xDest - self.x;

		double angle = Math.atan2(xDist, yDist);
		self.xVel = (float) (Math.sin(angle) * self.speed);
		self.yVel = (float) (Math.cos(angle) * self.speed);

		// If the enemy will reach its destination between this frame and the
		// next, this MovementOrder is finished.
		if (Math.abs(yDist) < Math.abs(self.yVel * deltaTime)
				&& Math.abs(xDist) < Math.abs(self.xVel * deltaTime))
			isFinished = true;

		self.x += self.xVel * deltaTime;
		self.y += self.yVel * deltaTime;

		// finally, if a movementcondition exists, check it
		if (movementCondition != null) {
			boolean conditionMet = movementCondition.checkCondition(self,
					deltaTime);
			if (conditionMet)
				isFinished = true;
		}
	}
}
