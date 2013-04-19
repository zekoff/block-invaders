package net.zekoff.blockinvaders.combat.ship;

import net.zekoff.blockinvaders.combat.weapon.Weapon;

/**
 * Contains an x- and y-offset representing a weapon mounting point on some
 * ShipBlueprint, Ship, etc. Also contains an optional Weapon field upon which a
 * Weapon can be affixed if using this MountPoint as part of a Ship
 * instantiation.
 * 
 * @author Zekoff
 * 
 */
public class MountPoint {
	float x = 0;
	float y = 0;
	public Weapon weapon = null;

	public MountPoint() {

	}

	public MountPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public MountPoint(float x, float y, Weapon weapon) {
		this(x, y);
		this.weapon = weapon;
	}

	/**
	 * "Mounts" the weapon to this mount point, connecting it to the ship and
	 * specifying where its bullets should originate from.
	 * 
	 * @param weapon
	 */
	public void mountWeapon(Weapon weapon) {
		this.weapon = weapon;
		weapon.x = x;
		weapon.y = y;
	}
}
