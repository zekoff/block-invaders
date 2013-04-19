package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;

public class SpreadShotBlueprint extends WeaponBlueprint {

	public SpreadShotBlueprint(int level) {
		super(level);
		name = "Spread Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = null;

				b = new OvalBullet(Assets.playerShip.x + x - 8,
						Assets.playerShip.y + y - 6, 4, 8);
				b.yVel = -bulletSpeed;
				b.power = power / 4 * cooldown;
				b.color = bulletColor;
				Assets.bm.playerBullets.add(b);

				b = new OvalBullet(Assets.playerShip.x + x + 8,
						Assets.playerShip.y + y - 6, 4, 8);
				b.yVel = -bulletSpeed;
				b.power = power / 4 * cooldown;
				b.color = bulletColor;
				Assets.bm.playerBullets.add(b);

				b = new OvalBullet(Assets.playerShip.x + x - 24,
						Assets.playerShip.y + y + 6, 4, 8);
				b.yVel = -bulletSpeed;
				b.power = power / 4 * cooldown;
				b.color = bulletColor;
				Assets.bm.playerBullets.add(b);

				b = new OvalBullet(Assets.playerShip.x + x + 24,
						Assets.playerShip.y + y + 6, 4, 8);
				b.yVel = -bulletSpeed;
				b.power = power / 4 * cooldown;
				b.color = bulletColor;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.cooldown = .4f;
		setPower(w);
		w.bulletColor = 0xa000ff00;
		w.bulletSpeed = 340;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Spread Shot";
	}

}
