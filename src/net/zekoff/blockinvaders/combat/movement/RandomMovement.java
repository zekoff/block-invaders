package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Enemy hovers roughly in the same area but moves randomly within some set
 * bound.
 * 
 * @author Zekoff
 * 
 */
public class RandomMovement extends MovementOrders {
	float bound = 0;
	float startX = -999;
	float startY = -999;
	float xFactor = 0;
	float yFactor = 0;
	MovementCondition movementCondition = null;

	MovementOrders moveToOrder = null;

	public RandomMovement(float bound) {
		this.bound = bound;
	}

	public RandomMovement(float bound, MovementCondition movementCondition) {
		this(bound);
		this.movementCondition = movementCondition;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		// set start location on first call of this active order
		if (startX == -999)
			startX = self.x;
		if (startY == -999)
			startY = self.y;

		if (moveToOrder != null) {
			if (moveToOrder.isFinished)
				moveToOrder = null;
			moveToOrder.move(self, deltaTime);
			if (moveToOrder.isFinished)
				moveToOrder = null;
		} else {

			// check boundary conditions
			if (self.x > startX + bound || self.x < startX - bound) {
				moveToOrder = new MoveTo(startX, startY);
			}
			if (self.y > startY + bound || self.y < startY - bound) {
				moveToOrder = new MoveTo(startX, startY);
			}

			// update enemy movement and location based on agility and speed
			if (xFactor == 0 && yFactor == 0) {
				xFactor = Assets.rand.nextFloat() * self.speed;
				yFactor = self.speed - xFactor;
				int direction = Assets.rand.nextInt(2);
				if (direction == 0)
					xFactor = -xFactor;
				direction = Assets.rand.nextInt(2);
				if (direction == 0)
					yFactor = -yFactor;
			}

			self.xVel = xFactor;
			self.yVel = yFactor;
			self.x += self.xVel * deltaTime;
			self.y += self.yVel * deltaTime;
		}

		// finally, check movement condition
		if (movementCondition != null) {
			boolean condition = movementCondition.checkCondition(self,
					deltaTime);
			if (condition)
				isFinished = true;
		}
	}

	/**
	 * If the enemy has wandered outside of its bounds, return it to the
	 * starting location.
	 * <p>
	 * No longer used, but shows how to add a new MovementOrder to the Enemy's
	 * list from within a MovementOrder.
	 * 
	 * @param self
	 * 
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	private void returnToStartLocation(Enemy self) {
		MovementOrders mo = new MoveTo(startX, startY);
		int index = self.movementOrders.indexOf(this);
		self.movementOrders.add(index, mo);
		xFactor = 0;
		yFactor = 0;
	}
}
