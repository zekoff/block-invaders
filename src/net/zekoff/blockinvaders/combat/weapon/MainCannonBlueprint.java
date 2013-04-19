package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;

/**
 * The blueprint for use with Cannon-type weapons.
 * 
 * @author Zekoff
 * 
 */
public class MainCannonBlueprint extends WeaponBlueprint {
	private static final long serialVersionUID = -8583127832540646450L;

	public MainCannonBlueprint(int level) {
		super(level);
		name = "Main Cannon";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {
			@Override
			public void fire() {
				CircleBullet b = new CircleBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, bulletSize);
				b.yVel = -300;
				b.xVel = (float) ((Assets.rand.nextFloat() - 0.5) * accuracy);
				b.color = bulletColor;
				b.power = power * cooldown;
				Assets.bm.playerBullets.add(b);
			}
		};
		w.cooldown = 1.2f;
		setPower(w);
		// w.bulletColor = Color.RED;
		w.bulletSize = 10;
		w.accuracy = 10;

		setupPerks(w);

		return w;
	}

	@Override
	public String getTypeName() {
		return "Main Cannon";
	}

}
