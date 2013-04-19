package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;
import net.zekoff.blockinvaders.combat.bullet.RectBullet;

public class FanShotBlueprint extends WeaponBlueprint {

	public FanShotBlueprint(int level) {
		super(level);
		name = "Fan Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				for (int i = 0; i < numBullets; i++) {
					RectBullet b = new OvalBullet(0, 0, 4, 8);
					b.power = power / 5 * cooldown;
					b.yVel = -350;
					b.xVel = (float) spread / numBullets
							* (i - (numBullets - 1) / 2);
					b.x = Assets.playerShip.x + x;
					b.y = Assets.playerShip.y + y;
					b.color = bulletColor;
					b.damageType = DamageType.energy;
					Assets.bm.playerBullets.add(b);
				}
			}

		};

		w.bulletColor = 0xa000ff00;
		w.cooldown = .3f;
		setPower(w);
		w.spread = 150;
		w.numBullets = 5;

		setupPerks(w);

		return w;
	}

	@Override
	public String getTypeName() {
		return "Fan Shot";
	}

}
