package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.OvalBullet;
import net.zekoff.blockinvaders.combat.bullet.RectBullet;

public class ScatterShotBlueprint extends WeaponBlueprint {

	public ScatterShotBlueprint(int level) {
		super(level);
		name = "Scatter Shot";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {
			@Override
			public void fire() {
				for (int i = 0; i < numBullets; i++) {
					RectBullet b = new OvalBullet(160, 380, 4, 8);
					b.yVel = -400;
					b.power = power / 4 * cooldown;
					b.xVel = (float) ((Assets.rand.nextFloat() - 0.5) * spread);
					b.x = Assets.playerShip.x + x;
					b.y = Assets.playerShip.y + y;
					b.color = bulletColor;
					Assets.bm.playerBullets.add(b);
				}
			}
		};

		w.bulletColor = 0x9000ff00;
		setPower(w);
		w.numBullets = 4;
		w.spread = 300;
		w.cooldown = .3f;

		setupPerks(w);

		return w;
	}

	@Override
	public String getTypeName() {
		return "Scatter Shot";
	}

}
