package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class SmoothMoveTo extends MoveTo {

	public SmoothMoveTo(float xDest, float yDest) {
		super(xDest, yDest);
	}

	public SmoothMoveTo(float xDest, float yDest,
			MovementCondition movementCondition) {
		super(xDest, yDest, movementCondition);
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
		float targetXVel = (float) (Math.sin(angle) * self.speed);
		float targetYVel = (float) (Math.cos(angle) * self.speed);

		if (self.xVel < targetXVel) {
			self.xVel += deltaTime * self.acceleration;
			if (self.xVel > targetXVel)
				self.xVel = targetXVel;
		} else if (self.xVel > targetXVel) {
			self.xVel -= deltaTime * self.acceleration;
			if (self.xVel < targetXVel)
				self.xVel = targetXVel;
		}
		if (self.yVel < targetYVel) {
			self.yVel += deltaTime * self.acceleration;
			if (self.yVel > targetYVel)
				self.yVel = targetYVel;
		} else if (self.yVel > targetYVel) {
			self.yVel -= deltaTime * self.acceleration;
			if (self.yVel < targetYVel)
				self.yVel = targetYVel;
		}

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
