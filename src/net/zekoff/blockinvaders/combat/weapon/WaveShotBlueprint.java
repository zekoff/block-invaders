package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.WaveBullet;

public class WaveShotBlueprint extends WeaponBlueprint {

	public WaveShotBlueprint(int level) {
		super(level);
		name = "Wave Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = new WaveBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 5);
				b.power = power;
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.cooldown = .75f;
		setPower(w);
		w.bulletColor = 0xb000ff00;
		w.bulletSpeed = 120;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Wave Shot";
	}

}
