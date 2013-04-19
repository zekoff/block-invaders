package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.MissileBatteryMissile;
import net.zekoff.blockinvaders.combat.bullet.TriangleMissile;

public class MissileBatteryBlueprint extends WeaponBlueprint {

	public MissileBatteryBlueprint(int level) {
		super(level);
		name = "Missile Battery";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				TriangleMissile b = null;

				b = new MissileBatteryMissile();
				b.radius = 4;
				b.speed = bulletSpeed;
				b.power = power / 2;
				b.color = bulletColor;
				b.x = Assets.playerShip.x + x;
				b.y = Assets.playerShip.y + y;
				b.xVel = -80;
				b.yVel = -80;
				Assets.bm.playerBullets.add(b);

				b = new MissileBatteryMissile();
				b.radius = 4;
				b.speed = bulletSpeed;
				b.power = power / 2;
				b.color = bulletColor;
				b.x = Assets.playerShip.x + x;
				b.y = Assets.playerShip.y + y;
				b.xVel = 80;
				b.yVel = -80;
				Assets.bm.playerBullets.add(b);
			}
		};
		w.bulletSpeed = 330f;
		// w.bulletColor = Color.GREEN;
		setPower(w);
		w.power *= .75f;
		w.cooldown = 1f;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Missile Battery";
	}

}
