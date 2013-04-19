package net.zekoff.blockinvaders.combat.ability;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.ship.MountPoint;

/**
 * Sample ability that sets player's current shield value to maximum when
 * activated. Also reactivate shield if it is currently disabled.
 * 
 * @author Zekoff
 * 
 */
public class Supercharge extends Ability {

	// TODO currently does not increase weapon power
	// TODO perhaps make player temporarily invulnerable instead?
	float[] weaponPower = new float[5];
	int[] weaponColor = new int[5];
	boolean supercharged = false;
	float timer = 0;

	public Supercharge() {
		name = "Supercharge";
		cooldownTime = 10f;
		cooldown = 0;
		description = "This ability will instantly refill your shield, and causes all of your weapons to briefly become more powerful."
				+ "\n\n"
				+ "This versatile ability is useful when you need a little help to survive a close encounter, or when you need to burn down an enemy quickly.";
	}

	@Override
	public void update(float deltaTime) {
		cooldown -= deltaTime;
		if (cooldown < 0)
			cooldown = 0;
		if (supercharged) {
			timer -= deltaTime;
			if (timer < 0) {
				supercharged = false;
				for (int i = 0; i < Assets.playerShip.mountPoints.size(); i++) {
					MountPoint mp = Assets.playerShip.mountPoints.get(i);
					if (mp != null && mp.weapon != null) {
						mp.weapon.power = weaponPower[i];
						mp.weapon.bulletColor = weaponColor[i];
					}
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (icon == null) {
			icon = g.newPixmap("shield_boost.png", null);
		}
	}

	@Override
	public boolean trigger() {
		if (cooldown == 0) {
			if (Assets.playerShip.shield != null) {
				Assets.playerShip.shield.isDisabled = false;
				Assets.playerShip.shield.currentCharge = Assets.playerShip.shield.maxCharge;
				cooldown = cooldownTime;
			}

			for (int i = 0; i < Assets.playerShip.mountPoints.size(); i++) {
				MountPoint mp = Assets.playerShip.mountPoints.get(i);
				if (mp != null && mp.weapon != null) {
					weaponPower[i] = mp.weapon.power;
					mp.weapon.power *= 2;
					weaponColor[i] = mp.weapon.bulletColor;
					int newBulletAlpha = mp.weapon.bulletColor & 0xFF000000;
					mp.weapon.bulletColor = newBulletAlpha + 0xFFFF00;
				}
			}

			timer = 2;
			supercharged = true;

			SoundAssets.supercharge.play(1);

			return true;
		}
		return false;
	}
}
