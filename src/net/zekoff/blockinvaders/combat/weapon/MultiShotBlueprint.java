package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;

public class MultiShotBlueprint extends WeaponBlueprint {

	public MultiShotBlueprint(int level) {
		super(level);
		name = "Multi-Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = new OvalBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 2, 4);
				b.power = power * cooldown;
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = Assets.rand.nextFloat() * accuracy - (accuracy / 2);
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.bulletSpeed = 380;
		w.bulletColor = 0xC000FF00;
		setPower(w);
		w.power *= 1.2;
		w.accuracy = 180;
		w.cooldown = .05f;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Multi-Shot";
	}

}
