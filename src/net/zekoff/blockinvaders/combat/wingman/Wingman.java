package net.zekoff.blockinvaders.combat.wingman;

import android.graphics.Color;
import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.collision.Aabb;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import net.zekoff.blockinvaders.combat.entity.Entity;

/**
 * Wingman that fights alongside the player. Essentially serves as weak extra
 * guns and a chance at absorbing shots that were otherwise bound to hit the
 * player. Can be drawn with a slight alpha component so that they don't take
 * focus entirely off the player.
 * 
 * @author Zekoff
 * 
 */
public class Wingman implements Entity {
	public float x = 0;
	public float y = 0;
	public float speed = 0;
	public float width = 0;
	public float height = 0;
	public WingmanState state;
	public boolean isExpired = false;
	public BoundingShape hitbox;

	public Wingman() {
		width = 10;
		height = 10;
		hitbox = new Aabb(width, height);
		speed = 50;
	}

	// JUST FOR TESTING PURPOSES
	public Wingman(float x, float y) {
		this();
		this.x = x;
		this.y = y;
	}

	public enum WingmanState {
		closeFormation, spreadFormation, movingTo, returning
	}

	@Override
	public boolean isExpired() {
		return isExpired;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		int color = Color.YELLOW;
		g.drawRect(x, y, width, height, color);
	}

	@Override
	public boolean testCollision(Bullet b) {
		return false;
	}

	@Override
	public void collidedWith(Bullet b) {
		// TODO Auto-generated method stub

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
