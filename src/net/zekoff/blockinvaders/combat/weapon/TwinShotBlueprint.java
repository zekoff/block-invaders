package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import net.zekoff.blockinvaders.combat.bullet.RectBullet;

public class TwinShotBlueprint extends WeaponBlueprint {

	public TwinShotBlueprint(int level) {
		super(level);
		name = "Twin Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = null;

				b = new RectBullet(Assets.playerShip.x + x - 6,
						Assets.playerShip.y + y, 3, 7);
				b.yVel = -bulletSpeed;
				b.power = power / 2 * cooldown;
				b.color = bulletColor;
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);

				b = new RectBullet(Assets.playerShip.x + x + 6,
						Assets.playerShip.y + y, 3, 7);
				b.yVel = -bulletSpeed;
				b.power = power / 2 * cooldown;
				b.color = bulletColor;
				b.damageType = DamageType.energy;
				Assets.bm.playerBullets.add(b);
			}
		};

		w.cooldown = .35f;
		setPower(w);
		// w.bulletColor = Color.CYAN;
		w.bulletSpeed = 380;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Twin Shot";
	}

}
