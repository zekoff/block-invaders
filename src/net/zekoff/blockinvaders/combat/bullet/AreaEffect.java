package net.zekoff.blockinvaders.combat.bullet;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.collision.Circle;
import net.zekoff.blockinvaders.combat.entity.Entity;
import android.graphics.Paint;

public class AreaEffect extends Bullet {
	public float durationMax = 1.5f;
	public float durationCurrent = 0;
	public int alpha = 0xA0;
	public float targetRadius = 10;
	public float currentRadius = 0;
	public float expansionRate = 110;
	private Paint paint = new Paint();
	float fullPower = -999;

	public AreaEffect(float x, float y, float targetRadius) {
		type = BulletType.circle;
		this.x = x;
		this.y = y;
		this.targetRadius = targetRadius;
		this.hitbox = new Circle(0);
		color = 0xA0FF5000;
		paint.setStyle(Paint.Style.FILL);
		expansionRate = targetRadius * 2;
		power = 20;
	}

	@Override
	public void update(float deltaTime) {
		if (fullPower == -999)
			fullPower = power;
		if (currentRadius < targetRadius) {
			currentRadius += expansionRate * deltaTime;
			hitbox.radius = currentRadius;
		} else {
			expansionRate -= expansionRate * 3 * deltaTime;
			if (expansionRate < .5f)
				expansionRate = .5f;
			currentRadius += expansionRate * deltaTime;
		}
		durationCurrent += deltaTime;
		alpha = (int) (0x80 * (1 - durationCurrent / durationMax));
		color = (alpha << 24) + (color & 0x00FFFFFF);
		if (durationCurrent > durationMax) {
			isExpired = true;
		}
		power = fullPower * deltaTime;
		super.update(deltaTime);
	}

	@Override
	public void draw(Graphics g) {
		paint.setColor(color);
		g.drawCircle(x, y, currentRadius, paint);
	}

	@Override
	public void collidedWith(Entity e) {
		Assets.pm.addBurstEmitter(e.getX(), e.getY(), 1, color);
	}

}
