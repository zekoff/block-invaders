package net.zekoff.blockinvaders.combat.entity;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import android.graphics.Color;
import android.graphics.Paint;

public class Barrier implements Entity {
	public boolean isExpired = false;
	public float timeLeft = 10f;
	public float x;
	public float y;
	public Component component = new RectangularComponent(0, 0, 60, 10);
	Paint paint = new Paint();

	public Barrier() {
		paint.setColor(Color.LTGRAY);
	}

	public Barrier(float duration) {
		this();
		timeLeft = duration;
	}

	@Override
	public boolean isExpired() {
		return isExpired;
	}

	@Override
	public void update(float deltaTime) {
		timeLeft -= deltaTime;
		if (timeLeft <= 0)
			isExpired = true;
		component.height = (float) Math.ceil(timeLeft);
	}

	@Override
	public void draw(Graphics g) {
		component.draw(g, x, y, paint);
	}

	@Override
	public boolean testCollision(Bullet b) {
		BoundingShape tempHitbox = component.getBoundingShape();
		tempHitbox.x = x;
		tempHitbox.y = y;
		return BoundingShape.testCollision(tempHitbox, b.hitbox);
	}

	@Override
	public void collidedWith(Bullet b) {

	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

}
