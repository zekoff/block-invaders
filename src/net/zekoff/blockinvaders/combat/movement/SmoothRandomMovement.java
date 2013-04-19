package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Extension of RandomMovement for smooth starting and stopping motions.
 * 
 * @author Zekoff
 * 
 */
public class SmoothRandomMovement extends RandomMovement {

	public SmoothRandomMovement(float bound) {
		super(bound);
	}

	public SmoothRandomMovement(float bound, MovementCondition movementCondition) {
		super(bound, movementCondition);
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		// set start location on first call of this active order
		if (startX == -999)
			startX = self.x;
		if (startY == -999)
			startY = self.y;

		// check boundary conditions; if it ever somehow gets outside of bounds,
		// move it back in
		if (self.x > startX + bound || self.x < startX - bound) {
			moveToOrder = new SmoothMoveTo(startX, startY);
		}
		if (self.y > startY + bound || self.y < startY - bound) {
			moveToOrder = new SmoothMoveTo(startX, startY);
		}

		if (moveToOrder != null) {
			if (moveToOrder.isFinished)
				moveToOrder = null;
			moveToOrder.move(self, deltaTime);
			if (moveToOrder.isFinished)
				moveToOrder = null;
		} else {
			// make another moveTo order
			float newX = Assets.rand.nextFloat() * (bound * 2) - bound;
			float newY = Assets.rand.nextFloat() * (bound * 2) - bound;
			moveToOrder = new SmoothMoveTo(startX + newX, startY + newY);
		}

		// finally, check movement condition
		if (movementCondition != null) {
			boolean condition = movementCondition.checkCondition(self,
					deltaTime);
			if (condition)
				isFinished = true;
		}
	}
}
