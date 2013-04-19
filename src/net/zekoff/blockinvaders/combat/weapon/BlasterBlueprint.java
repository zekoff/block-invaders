package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.Bullet.BulletType;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;

public class BlasterBlueprint extends WeaponBlueprint {

	public BlasterBlueprint(int level) {
		super(level);
		name = "Blaster";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = new OvalBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 8, 14);
				b.yVel = -bulletSpeed;
				b.xVel = Assets.rand.nextFloat() * accuracy - accuracy / 2;
				b.power = power / 2;
				b.color = bulletColor;
				b.damageType = DamageType.piercing;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.cooldown = .35f;
		setPower(w);
		// w.bulletColor = Color.WHITE;
		w.accuracy = 30;
		w.bulletSpeed = 400;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Blaster";
	}

}
