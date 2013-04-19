package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Can be attached to to a MovementOrder to let it check for the status of the
 * enemy position and react accordingly. The single checkCondition() method is
 * used by creating an anonymous class that returns true when the condition has
 * been met. Could be used to set a movement order to expired when the enemy has
 * reached some certain point, etc.
 * 
 * @author Zekoff
 * 
 */
public abstract class MovementCondition {
	public abstract boolean checkCondition(Enemy self, float deltaTime);
}
