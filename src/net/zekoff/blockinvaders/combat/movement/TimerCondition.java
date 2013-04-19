package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Condition is met when a certain amount of time has expired.
 * 
 * @author Zekoff
 * 
 */
public class TimerCondition extends MovementCondition {
	private float counter = 0;
	private float timer = 0;

	public TimerCondition(float timer) {
		this.timer = timer;
	}

	@Override
	public boolean checkCondition(Enemy self, float deltaTime) {
		counter += deltaTime;
		if (counter >= timer) {
			return true;
		}
		return false;
	}
}
