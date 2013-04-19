package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;

public class VeeShotBlueprint extends WeaponBlueprint {

	public VeeShotBlueprint(int level) {
		super(level);
		name = "V-Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				for (int i = 0; i < numBullets; i++) {
					Bullet b = new OvalBullet(0, 0, 4, 8);
					b.power = power / 2 * cooldown;
					b.yVel = -370;
					b.xVel = (float) spread / numBullets
							* (i - (numBullets - 1) / 2);
					b.x = Assets.playerShip.x + x;
					b.y = Assets.playerShip.y + y;
					b.color = bulletColor;
					Assets.bm.playerBullets.add(b);
				}
			}

		};

		w.cooldown = .17f;
		setPower(w);
		w.spread = 360;
		// w.bulletColor = Color.WHITE;
		w.numBullets = 2;

		setupPerks(w);

		return w;
	}

	@Override
	public String getTypeName() {
		return "V-Shot";
	}

}
