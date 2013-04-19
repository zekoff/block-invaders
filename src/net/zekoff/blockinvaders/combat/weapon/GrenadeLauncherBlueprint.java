package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.bullet.Payload;

public class GrenadeLauncherBlueprint extends WeaponBlueprint {

	public GrenadeLauncherBlueprint(int level) {
		super(level);
		name = "Grenade Launcher";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = new CircleBullet(Assets.playerShip.x + x,
						Assets.playerShip.y + y, 7);
				b.power = power * cooldown / 2;
				b.color = bulletColor;
				b.yVel = -bulletSpeed;
				b.xVel = Assets.rand.nextFloat() * accuracy - accuracy / 2;
				b.payload = payload;
				Assets.bm.playerBullets.add(b);
			}
		};

		setPower(w);
		w.bulletSpeed = 270;
		// w.bulletColor = 0xFFFFA000;
		w.accuracy = 100;
		Payload p = new Payload();
		p.type = Payload.Type.ae;
		p.power = w.power; // need to make sure this is balanced
		p.duration = 2f;
		p.size = 25f;
		w.payload = p;
		w.cooldown = .75f;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Grenade Launcher";
	}

}
