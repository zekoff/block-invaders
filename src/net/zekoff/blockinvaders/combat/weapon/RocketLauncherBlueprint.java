package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.Payload;
import net.zekoff.blockinvaders.combat.bullet.TriangleBullet;

public class RocketLauncherBlueprint extends WeaponBlueprint {

	public RocketLauncherBlueprint(int level) {
		super(level);
		name = "Rocket Launcher";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {

			@Override
			public void fire() {
				Bullet b = new TriangleBullet(Assets.playerShip.x + x,
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
		w.bulletSpeed = 210;
		// w.bulletColor = 0xFFFF4040;
		w.accuracy = 10;
		w.cooldown = 1.7f;
		w.power *= .7;

		Payload p = new Payload();
		p.type = Payload.Type.ae;
		p.power = w.power; // need to make sure this is balanced
		p.duration = 4f;
		p.size = 40f;
		w.payload = p;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Rocket Launcher";
	}
}
