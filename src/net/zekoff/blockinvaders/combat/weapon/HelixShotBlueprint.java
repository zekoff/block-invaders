package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.WaveBullet;

public class HelixShotBlueprint extends WeaponBlueprint {

	public HelixShotBlueprint(int level) {
		super(level);
		name = "Helix Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				WaveBullet b = null;

				b = new WaveBullet(Assets.playerShip.x + x, Assets.playerShip.y
						+ y, 3);
				b.power = power / 2 * cooldown;
				b.xVel /= 2;
				b.maxVelocity /= 2;
				b.acceleration /= 2;
				b.color = bulletColor;
				b.yVel = -bulletSpeed * 1.5f;
				Assets.bm.playerBullets.add(b);

				b = new WaveBullet(Assets.playerShip.x + x, Assets.playerShip.y
						+ y, 3);
				b.power = power / 2 * cooldown;
				b.xVel /= 2;
				b.maxVelocity /= 2;
				b.acceleration /= 2;
				b.xVel = -b.xVel;
				b.color = bulletColor;
				b.yVel = -bulletSpeed * 1.5f;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.cooldown = .15f;
		setPower(w);
		w.bulletColor = 0x9000FF00;
		w.bulletSpeed = 120;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Helix Shot";
	}

}
