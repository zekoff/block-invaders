package net.zekoff.blockinvaders.combat.firing;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

/**
 * Class to encapsulate firing logic away from individual enemies and make it
 * reusable by all. This abstract class implements boilerplate update logic but
 * can be overridden by enemies who need to use burst-fire or some other type of
 * firing logic.
 * <p>
 * If the FiringOrders object gets instantiated with an Enemy in its
 * constructor, it automatically uses that Enemy to decide where to put bullets
 * into play, etc. If not, an enemy can be passed at runtime.
 * 
 * @author Zekoff
 * 
 */
public abstract class FiringOrders {
	public boolean isExpired = false;
	public float cooldown = 1;
	public float currentCooldown = 0;
	Enemy sourceEnemy = null;

	public FiringOrders() {

	}

	public FiringOrders(Enemy e) {
		this();
		sourceEnemy = e;
	}

	/**
	 * Sets the current cooldown of the weapon to 0.
	 */
	public void resetCooldown() {
		currentCooldown = 0;
	}

	/**
	 * Randomizes the current cooldown value of this FiringOrders. Useful after
	 * instantiating an enemy and associated FiringOrders so enemies do not all
	 * fire at the same time after being spawned.
	 */
	public void randomizeCooldown() {
		currentCooldown = Assets.rand.nextFloat() * cooldown;
	}

	/**
	 * A convenience method to have the FiringOrder use whatever source enemy it
	 * was instantiated with. Does not need to be overridden by subclasses.
	 * 
	 * @param deltaTime
	 *            The amount of time passed since the last frame.
	 */
	public final void update(float deltaTime) {
		if (sourceEnemy != null)
			update(sourceEnemy, deltaTime);
		else
			throw new RuntimeException("No source enemy defined");
	}

	/**
	 * The main method that gets called to update cooldown timers, etc. This
	 * boilerplate update() method can be used for enemies who fire at regular
	 * intervals. Burst-fire and other firing types can be achieved by
	 * overriding the update() method in the subclass.
	 * 
	 * @param self
	 *            The enemy firing the shots.
	 * @param deltaTime
	 *            The amount of time since the last frame.
	 */
	public void update(Enemy self, float deltaTime) {
		currentCooldown += deltaTime;
		if (currentCooldown >= cooldown) {
			currentCooldown = 0;
			fire(self);
		}
	}

	/**
	 * Called when bullets need to be put into play. All subclasses must
	 * implement this.
	 * 
	 * @param self
	 *            The enemy that is firing the shot.
	 */
	abstract void fire(Enemy self);
}
