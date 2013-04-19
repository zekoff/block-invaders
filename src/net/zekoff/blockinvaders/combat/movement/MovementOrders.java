package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * All Enemy ships have a list of MovementOrders that can be chained together.
 * When one order expires, the next one is begun. Once all orders have expired,
 * enemies default to some basic movement type.
 * <p>
 * To use a MovementOrder, enemy ships should override the move() method and
 * pass a reference to themselves so that the MovementOrder can actually access
 * and update them.
 * <p>
 * Subclasses of MovementOrder can implement whatever behavior they wish, as
 * long as they implement move() correctly.
 * 
 * @author Zekoff
 * 
 */
public abstract class MovementOrders {
	public boolean isFinished = false;

	/**
	 * @param enemy
	 *            Enemies using MovementOrders pass the move() method a
	 *            reference to themselves so that the method knows what to
	 *            update.
	 * @param deltaTime
	 */
	public abstract void move(Enemy enemy, float deltaTime);

}
