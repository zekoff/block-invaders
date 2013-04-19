package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.StabilizingBullet;

public class WideShotBlueprint extends WeaponBlueprint {

	public WideShotBlueprint(int level) {
		super(level);
		name = "Wide Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = null;

				b = new StabilizingBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 6);
				b.power = power / 2;
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = -450;
				Assets.bm.playerBullets.add(b);

				b = new StabilizingBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 6);
				b.power = power / 2;
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = 450;
				Assets.bm.playerBullets.add(b);
			}
		};

		setPower(w);
		w.cooldown = .60f;
		// w.bulletColor = Color.GREEN;
		w.bulletSpeed = 350;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Wide Shot";
	}

}
