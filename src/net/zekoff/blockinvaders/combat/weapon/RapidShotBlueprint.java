package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import net.zekoff.blockinvaders.combat.bullet.RectBullet;

public class RapidShotBlueprint extends WeaponBlueprint {

	public RapidShotBlueprint(int level) {
		super(level);
		name = "Rapid Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = new RectBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 2, 4);
				b.power = power * cooldown;
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = Assets.rand.nextFloat() * accuracy - (accuracy / 2);
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.bulletSpeed = 400;
//		w.bulletColor = Color.CYAN;
		setPower(w);
		w.accuracy = 10;
		w.cooldown = .14f;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Rapid Shot";
	}

}
