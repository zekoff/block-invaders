package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * MovementOrder type to allow enemies to move in a parabola fashion, although
 * it can also be used for other types of exponential movement.
 * 
 * @author Zekoff
 * 
 */
public class Parabola extends MovementOrders {

	private float xVel = 0;
	private float yVel = 0;
	private float xDecel = 0;
	private float yDecel = 0;
	private MovementCondition movementCondition = null;

	public Parabola(float xVel, float yVel, float xDecel, float yDecel) {
		this.xVel = xVel;
		this.yVel = yVel;
		this.xDecel = xDecel;
		this.yDecel = yDecel;
	}

	public Parabola(float xVel, float yVel, float xDecel, float yDecel,
			MovementCondition movementCondition) {
		this(xVel, yVel, xDecel, yDecel);
		this.movementCondition = movementCondition;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		// TODO make movement smooth
		xVel += xDecel * deltaTime;
		yVel += yDecel * deltaTime;
		self.xVel = xVel;
		self.yVel = yVel;
		self.x += self.xVel * deltaTime;
		self.y += self.yVel * deltaTime;

		// finally, check movementcondition
		if (movementCondition != null) {
			boolean condition = movementCondition.checkCondition(self,
					deltaTime);
			if (condition)
				isFinished = true;
		}
	}
}
