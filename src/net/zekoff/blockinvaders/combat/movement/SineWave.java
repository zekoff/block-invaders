package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Moves the enemy up and down at a variable (non-enemy-specific) rate along the
 * x-axis.
 * 
 * @author Zekoff
 * 
 */
public class SineWave extends MovementOrders {
	public float xAxis = 0;
	public float amplitude = 0;
	public float xSpeed = 0;
	public boolean init = false;
	public MovementCondition movementCondition = null;

	public SineWave(float xAxis, float amplitude, float xSpeed) {
		this.xAxis = xAxis;
		this.amplitude = amplitude;
		this.xSpeed = xSpeed;
	}

	public SineWave(float xAxis, float amplitude, float xSpeed,
			MovementCondition mc) {
		this(xAxis, amplitude, xSpeed);
		this.movementCondition = mc;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		if (!init) {
			init = true;
			self.rotateByXVel = false;
			self.xVel = xSpeed;
			self.yVel = -self.speed;
		}

		if (self.y < xAxis) {
			self.yVel += amplitude * deltaTime;
			if (self.yVel > amplitude)
				self.yVel = amplitude;
		}

		if (self.y > xAxis) {
			self.yVel -= amplitude * deltaTime;
			if (self.yVel < -amplitude)
				self.yVel = -amplitude;
		}

		self.x += self.xVel * deltaTime;
		self.y += self.yVel * deltaTime;

		if (movementCondition != null) {
			if (movementCondition.checkCondition(self, deltaTime))
				isFinished = true;
		}
	}
}
