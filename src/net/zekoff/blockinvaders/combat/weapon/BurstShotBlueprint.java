package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;

public class BurstShotBlueprint extends WeaponBlueprint {

	public BurstShotBlueprint(int level) {
		super(level);
		name = "Burst Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {
			public float cooldown2 = 0;
			public float cooldown3 = 0;
			boolean trigger2 = false;
			boolean trigger3 = false;
			float oldCurrentCooldown = 0;

			@Override
			public void update(float deltaTime) {
				// Burst 1
				super.update(deltaTime);
				oldCurrentCooldown += deltaTime;

				if (currentCooldown < oldCurrentCooldown) {
					trigger2 = true;
					oldCurrentCooldown = 0;
				}

				if (trigger2) {
					// Burst 2
					cooldown2 += deltaTime;
					if (cooldown2 > cooldown / 16) {
						cooldown2 = 0;
						fire2();
						trigger2 = false;
						trigger3 = true;
					}
				}

				// Burst 3
				if (trigger3) {
					cooldown3 += deltaTime;
					if (cooldown3 > cooldown / 16) {
						cooldown3 = 0;
						fire3();
						trigger3 = false;
					}
				}
			}

			@Override
			public void fire() {
				Bullet b = new CircleBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, bulletSize);
				b.power = power / 3 * cooldown;
				b.yVel = -bulletSpeed;
				b.color = bulletColor;
				b.xVel = (Assets.rand.nextFloat() * accuracy) - accuracy / 2;
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);
			}

			public void fire2() {
				Bullet b = new CircleBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, bulletSize - 1);
				b.power = power / 3 * cooldown;
				b.yVel = -bulletSpeed;
				b.color = bulletColor;
				b.xVel = (Assets.rand.nextFloat() * accuracy) - accuracy / 2;
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);
			}

			public void fire3() {
				Bullet b = new CircleBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, bulletSize - 2);
				b.power = power / 3 * cooldown;
				b.yVel = -bulletSpeed;
				b.color = bulletColor;
				b.xVel = (Assets.rand.nextFloat() * accuracy) - accuracy / 2;
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);
			}

		};
		w.cooldown = 1.2f;
		setPower(w);
		w.bulletSize = 7;
		w.accuracy = 50;
		w.bulletSpeed = 350;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Burst Shot";
	}

}
