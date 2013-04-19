package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Payload;
import net.zekoff.blockinvaders.combat.ship.ShipBlueprint.ShipClass;

/**
 * A Weapon is mounted on one of a Ship's MountPoints, and fires its ordinance
 * when conditions are met. It knows how to construct its own Bullet objects as
 * appropriate. Its fire() method is only called when a shot is to be put into
 * the Bullet list.
 * <p>
 * Specific Weapon implementations primarily work by overriding the fire()
 * method, and of course can add any number of additional variables necessary to
 * do their job correctly. The cooldown-based method of firing is built-in. If
 * other methods of determining when to fire are desired, update() can be
 * overridden. For example, to create a laser that only fires when some enemy is
 * above it, implement that behavior in update().
 * <p>
 * Weapon is not subclassed, but rather implemented by means of anonymous
 * subclassing within the getWeapon() method of any WeaponBlueprint.
 * 
 * @author Zekoff
 * 
 */
public abstract class Weapon {
	public float cooldown = 1.5f;
	public float currentCooldown = 0;
	public float power = 1;
	public float x;
	public float y;
	public float bulletSpeed;
	public float spread = 0;
	public float accuracy = 1;
	public float numBullets = 1;
	public int bulletColor = 0xFF00FF00;
	public float bulletSize = 0;
	public Payload payload = null;

	/**
	 * Guaranteed to be called by all subclasses. Even if it is overridden, must
	 * call super.
	 * <p>
	 * To make weapons fire faster, increase the speed at which currentCooldown
	 * is increased by passing in an inflated deltaTime.
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		float comboSpeedModifier = 1;
		if (Assets.pilot.shipBlueprint.getShipClass() == ShipClass.light) {
			comboSpeedModifier += Assets.playerShip.comboCounter.getCombo()
					/ Assets.playerShip.comboCounter.comboCap;
			comboSpeedModifier *= 1f; // cap at 100% speed increase
		}
		currentCooldown += deltaTime * comboSpeedModifier;
		if (currentCooldown > cooldown) {
			currentCooldown = 0;
			fire();
		}
	}

	public void draw(Graphics g, float x, float y) {
		// do nothing for now
	}

	public abstract void fire();
}
