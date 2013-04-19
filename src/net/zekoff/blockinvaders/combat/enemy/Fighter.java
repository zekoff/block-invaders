package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.entity.TriangularComponent;
import net.zekoff.blockinvaders.combat.firing.DoubleSteadyTriangles;
import android.graphics.Color;

public class Fighter extends Enemy {
	int movementDirection = 1;
	public static final float WEAPON_SPEED = 1.0f;
	public float cooldown = WEAPON_SPEED;

	public Fighter() {
		this(Assets.pilot.level);

	}

	public Fighter(int level) {
		super(level, EnemyType.armored);
		speed = 100;
		startColor = Color.CYAN;
		color = startColor;
		components.add(new TriangularComponent(0, 0, 14, 180));
		components.add(new RectangularComponent(-16, -10, 12, 20, -45));
		components.add(new RectangularComponent(16, -10, 12, 20, 45));
		acceleration = 150;
		xpModifier = 1.3f;

		firingOrders = new DoubleSteadyTriangles(this);
		firingOrders.randomizeCooldown();
	}

	// @Override
	// public void updateWeapons(float deltaTime) {
	// // cooldown -= deltaTime;
	// // if (cooldown <= 0) {
	// // cooldown = WEAPON_SPEED;
	// // fire();
	// // }
	// firingOrders.update(deltaTime);
	// }

	// @Override
	// public void fire() {
		// Bullet b = new TriangleBullet(x + 22, y, 4, 180);
		// b.yVel = 200;
		// b.power = power / 2 * WEAPON_SPEED;
		// b.color = Color.RED;
		// Assets.bm.enemyBullets.add(b);
		// b = new TriangleBullet(x - 22, y, 4, 180);
		// b.yVel = 200;
		// b.power = power / 2 * WEAPON_SPEED;
		// b.color = Color.RED;
		// Assets.bm.enemyBullets.add(b);
//	}

	// @Override
	// public void draw(Graphics g) {
	// paint.setColor(0xFFA0A0A0);
	// paint.setStyle(Paint.Style.STROKE);
	// paint.setStrokeWidth(10);
	// for (int i = 0; i < components.size(); i++) {
	// if ((int) rotation == 0)
	// components.get(i).draw(g, x, y, paint);
	// else
	// components.get(i).draw(g, x, y, paint, (int) rotation);
	// }
	// super.draw(g);
	// }

	// @Override
	// public void collidedWith(Bullet b) {
	// b.power -= 1; // armor
	// if (b.power < 1) // player can never do less than 1 damage
	// b.power = 1;
	// super.collidedWith(b);
	// }

}
