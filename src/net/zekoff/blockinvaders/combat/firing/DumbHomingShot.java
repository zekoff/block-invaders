package net.zekoff.blockinvaders.combat.firing;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.bullet.CircleBullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class DumbHomingShot extends FiringOrders {

	public DumbHomingShot() {
		cooldown = 2;
	}

	public DumbHomingShot(Enemy e) {
		super(e);
	}

	@Override
	void fire(Enemy self) {
		Bullet b = new CircleBullet(self.x, self.y, 5);
		b.power = self.power * cooldown / 2; // note this is weaker b/c it homes
		b.speed = 200;
		b.color = 0xFFFF0000;
		double yDist = Assets.playerShip.y - self.y;
		double xDist = Assets.playerShip.x - self.x;
		double angle = Math.atan2(xDist, yDist);
		b.xVel = (float) (Math.sin(angle) * b.speed);
		b.yVel = (float) (Math.cos(angle) * b.speed);
		Assets.bm.enemyBullets.add(b);
	}

}
