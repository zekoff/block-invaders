package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;

public class TriShotBlueprint extends WeaponBlueprint {

	public TriShotBlueprint(int level) {
		super(level);
		name = "TriShot";

	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = null;
				b = new CircleBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y - 7, 4);
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = 0;
				b.power = power / 3;
				Assets.bm.playerBullets.add(b);
				b = new CircleBullet(Assets.playerShip.x + x - 9,
						Assets.playerShip.y + y + 3, 4);
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = 0;
				b.power = power / 3;
				Assets.bm.playerBullets.add(b);
				b = new CircleBullet(Assets.playerShip.x + x + 9,
						Assets.playerShip.y + y + 3, 4);
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = 0;
				b.power = power / 3;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.bulletColor = 0xb000ff00;
		w.bulletSpeed = 330;
		setPower(w);
		w.cooldown = .7f;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Tri-Shot";
	}

}
