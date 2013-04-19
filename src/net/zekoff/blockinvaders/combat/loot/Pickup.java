package net.zekoff.blockinvaders.combat.loot;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.collision.BoundingShape;
import net.zekoff.blockinvaders.combat.collision.Circle;
import net.zekoff.blockinvaders.combat.entity.Entity;
import net.zekoff.blockinvaders.combat.ship.Ship;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Implements a spinning box containing some Loot that is dropped by an enemy
 * and that can be picked up and added to the player's inventory.
 * 
 * @author Zekoff
 * 
 */
public class Pickup implements Entity {
	public Loot contents;
	public float x;
	public float y;
	public static final float WIDTH = 23;
	public static final float HEIGHT = 23;
	public float MAX_SPEED = 100;
	public float yVel = -20;
	public float xVel = 0;
	public boolean isExpired = false;
	public BoundingShape hitbox;
	public int rotation = 0;

	public boolean pulledByTractorBeam = false;

	public int borderColor = 0;

	public Pickup() {
	}

	public Pickup(Loot loot) {
		hitbox = new Circle(15);

		contents = loot;

		borderColor = 0;
		if (contents.getQuality() == Loot.JUNK_QUALITY)
			borderColor = 0xFF9D9D9D; // junk (grey)
		else if (contents.getQuality() == Loot.STANDARD_QUALITY)
			borderColor = Color.WHITE; // common
		else if (contents.getQuality() == Loot.UNCOMMON_QUALITY)
			borderColor = 0xFF1EFF00; // uncommon (green)
		else if (contents.getQuality() == Loot.RARE_QUALITY)
			borderColor = 0xFF0070DD; // rare (blue)
		else if (contents.getQuality() == Loot.EPIC_QUALITY)
			borderColor = 0xFFA335EE; // epic color
		else
			borderColor = 0xFFFF8000; // legendary color
	}

	public void update(float deltaTime) {
		y += yVel * deltaTime;
		x += xVel * deltaTime;
		if (!pulledByTractorBeam && yVel < MAX_SPEED) {
			yVel += 30 * deltaTime;
		}
		if (pulledByTractorBeam) {
			double yDist = Assets.playerShip.y - y;
			double xDist = Assets.playerShip.x - x;
			double angle = Math.atan2(xDist, yDist);
			xVel = (float) (Math.sin(angle) * MAX_SPEED * 2);
			yVel = (float) (Math.cos(angle) * MAX_SPEED * 2);
		}
		if (y > 480)
			isExpired = true;
		rotation += 90 * deltaTime;
	}

	public void draw(Graphics g) {
		g.drawRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT, 0xffd3d3d3,
				rotation, x, y);
		Assets.paint.setColor(borderColor);
		Assets.paint.setStrokeWidth(4);
		Assets.paint.setStyle(Paint.Style.STROKE);
		g.drawRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT, Assets.paint,
				rotation, x, y);
		Assets.paint.setStyle(Paint.Style.FILL);
		Assets.paint.setTypeface(Assets.typeface);
		Assets.paint.setTextAlign(Paint.Align.CENTER);
		Assets.paint.setTextSize(16);
		Assets.paint.setColor(Color.BLACK);
		g.drawText("?", x, y + 6, Assets.paint);
	}

	public boolean testCollision(Ship s) {
		hitbox.x = x;
		hitbox.y = y;
		return BoundingShape.testCollision(s.fullHitbox, hitbox);
	}

	public void collidesWith(Ship s) {
		isExpired = true;
		SoundAssets.get_loot.play(1);
		addLoot(contents);
		Assets.hud.queueMessage("Acquired '" + contents.toString() + "'");
	}

	/**
	 * Add the contents of this Pickup to the appropriate place in the player's
	 * inventory, based on the LootType.
	 * 
	 * @param loot
	 */
	private void addLoot(Loot loot) {
		if (loot != null)
			Assets.pilot.inventory.addLoot(loot);
	}

	@Override
	public boolean isExpired() {
		return isExpired;
	}

	@Override
	public boolean testCollision(Bullet b) {
		return false;
	}

	@Override
	public void collidedWith(Bullet b) {
		// do nothing, and can never happen
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
