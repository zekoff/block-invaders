package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Enemy slows to a stop based on acceleration.
 * 
 * @author Zekoff
 * 
 */
public class SmoothNoMovement extends NoMovement {

	public SmoothNoMovement() {
	}

	public SmoothNoMovement(MovementCondition movementCondition) {
		super(movementCondition);
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		if (self.xVel > 0) {
			self.xVel -= deltaTime * self.acceleration;
			if (self.xVel < 0)
				self.xVel = 0;
		}
		if (self.xVel < 0) {
			self.xVel += deltaTime * self.acceleration;
			if (self.xVel > 0)
				self.xVel = 0;
		}
		if (self.yVel > 0) {
			self.yVel -= self.acceleration * deltaTime;
			if (self.yVel < 0)
				self.yVel = 0;
		}
		if (self.yVel < 0) {
			self.yVel += self.acceleration * deltaTime;
			if (self.yVel > 0)
				self.yVel = 0;
		}

		self.x += self.xVel * deltaTime;
		self.y += self.yVel * deltaTime;
		super.move(self, deltaTime);
	}

}
