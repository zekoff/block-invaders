package net.zekoff.blockinvaders.combat.defense;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.bullet.Bullet.DamageType;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * A shield that provides protection to some entity. The shield recovers some
 * charge every frame based on its regenRate field. It has a maximum charge, and
 * if fully depleted will be inactive for some specified recovery time. It draws
 * itself as an arc around the point specified in its draw() method.
 * 
 * @author Zekoff
 * 
 */
public class Shield {
	public float maxCharge = 1;
	public float currentCharge = 1;
	public float regenRate = 1;
	public float recoveryTime = 5;
	public float currentRecoveryTime = 0;
	public boolean isDisabled = false;
	public int color = 0x8000FF00;
	public float size = 40;
	public RectF oval = new RectF();
	public Paint paint = new Paint();
	public boolean enemyShield = false;

	// ** EXPERIMENTAL ** //
	public float timeSinceHit = 999;
	public float briefDelay = .5f;

	public void update(float deltaTime) {
		if (currentCharge < maxCharge && !isDisabled) {
			if (timeSinceHit > briefDelay) { // **
				currentCharge += regenRate * deltaTime;
			} else {// **
				timeSinceHit += deltaTime; // **
			} // **
		}
		if (currentCharge > maxCharge) {
			currentCharge = maxCharge;
		}
		if (isDisabled) {
			currentRecoveryTime += deltaTime;
			if (currentRecoveryTime >= recoveryTime) {
				isDisabled = false;
			}
		}
	}

	public float takeDamage(float damage, DamageType damageType) {
		if (damageType == DamageType.energy)
			damage *= 3;
		float difference = currentCharge - damage;
		currentCharge -= damage;
		if (difference <= 0) {
			isDisabled = true;
			currentRecoveryTime = 0;
			currentCharge = 0;
		}

		timeSinceHit = 0; // **

		return difference;
	}

	public void draw(Graphics g, float x, float y) {
		// TODO redo this code to make the oval surround the hitbox plus a
		// percentage
		if (!isDisabled) {
			oval.left = x - size;// / 2;
			oval.right = x + size;// / 2;
			oval.top = y - size / 2;
			oval.bottom = y + size / 2;
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(7);
			paint.setColor(color);
			float sweep = currentCharge / maxCharge * 360;
			int alpha = (int) (0x50 * currentCharge / maxCharge);
			alpha += 0x40;

			color = (alpha << 24) + 0x00FF00;
			if (!enemyShield)
				g.drawArc(oval, -90 + sweep / 2, -sweep, false, paint);
			else
				g.drawArc(oval, 90 + sweep / 2, -sweep, false, paint);
		}
	}
}
