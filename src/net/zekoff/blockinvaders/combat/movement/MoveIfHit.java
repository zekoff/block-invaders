package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * If enemy takes a hit, move it to a random location some short-ish distance
 * away from its current location.
 * 
 * @author Zekoff
 * 
 */
public class MoveIfHit extends MovementOrders {

	private MovementCondition mc;
	SmoothMoveTo destination = null;
	SmoothNoMovement stop = null;
	float oldHp = -999;

	public MoveIfHit() {

	}

	public MoveIfHit(MovementCondition mc) {
		this.mc = mc;
	}

	@Override
	public void move(Enemy self, float deltaTime) {

		// set up HP variable if this is the first pass through the method
		if (oldHp == -999) {
			oldHp = self.hp;
		}

		// if enemy has taken a hit AND enemy is not currently moving in
		// response to being hit
		if (self.hp < oldHp && destination == null && stop == null) {
			float desiredX = 0;
			float desiredY = 0;
			float distance = 0;
			int counter = 0;
			while ((distance < 40 || distance > 80) && counter < 2) {
				// This method was dominating framerate. Don't let it run more
				// than twice
				counter++;
				desiredX = Assets.rand.nextFloat() * 200 + 60;
				desiredY = Assets.rand.nextFloat() * 100 + 50;
				distance = (float) Math.sqrt((double) Math.pow(
						(desiredX - self.x), 2)
						+ Math.pow((desiredY - self.y), 2));
			}
			destination = new SmoothMoveTo(desiredX, desiredY);
			stop = new SmoothNoMovement(new StoppedCondition());
		} else if (destination != null) {
			destination.move(self, deltaTime);
			if (destination.isFinished)
				destination = null;
		} else if (destination == null && stop != null) {
			stop.move(self, deltaTime);
			if (stop.isFinished) {
				stop = null;
				oldHp = self.hp;
			}
		} else {
			// do nothing
			self.xVel = 0;
			self.yVel = 0;
		}

		// check mc
		if (mc != null) {
			boolean condition = mc.checkCondition(self, deltaTime);
			if (condition)
				isFinished = true;
		}
	}
}
