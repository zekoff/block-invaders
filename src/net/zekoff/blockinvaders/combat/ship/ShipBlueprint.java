package net.zekoff.blockinvaders.combat.ship;

import java.io.Serializable;

import net.zekoff.blockinvaders.combat.defense.ArmorBlueprint;
import net.zekoff.blockinvaders.combat.defense.ShieldBlueprint;
import net.zekoff.blockinvaders.combat.weapon.Weapon;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import android.graphics.Color;

/**
 * A serializable representation of the raw ship that a player pilots. A ship
 * instantiation is given by calling getShip() on the blueprint. At this time,
 * perks, weapons, etc. are taken into account for the new Ship and a real,
 * pilotable ship is returned for use by the combat engine.
 * 
 * @author Zekoff
 * 
 */
abstract public class ShipBlueprint implements Serializable {
	public int customizationLevel = 0;
	public int color = Color.BLUE;
	public WeaponBlueprint[] weapons = new WeaponBlueprint[5];
	public ShieldBlueprint shield = null;
	public ArmorBlueprint armor = null;

	public enum ShipClass {
		light, medium, heavy
	}

	public abstract Ship getShip();

	public abstract int getNumMountPoints();

	public abstract ShipClass getShipClass();

	public abstract String getShipClassName();

	public abstract void setupHitbox(Ship s);

	public abstract void setupSpeed(Ship s);

	protected void setupEquipment(Ship s) {
		for (int i = 0; i < getNumMountPoints(); i++) {
			if (weapons[i] != null) {
				Weapon w = weapons[i].getWeapon();
				if (s.mountPoints.size() > i)
					s.mountPoints.get(i).mountWeapon(w);
			}
		}
		if (shield != null) {
			s.shield = shield.getShield();
		}
		if (armor != null) {
			s.armor = armor.getArmor();
		}
	}

	public void setupAll(Ship s) {
		setupSpeed(s);
		setupHitbox(s);
		setupEquipment(s);
	}
}
