package net.zekoff.blockinvaders.combat.ability;

import java.util.Iterator;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.ship.Ship;
import android.graphics.Paint;

public class ReflectivePulse extends Ability {
	public float fullRadius = 90;
	private Paint paint = new Paint();
	float radius = 0;
	float expansionSpeed = 200f;
	Iterator<Bullet> iterator = null;
	boolean activated = false;
	int alpha = 0xFF;
	int color = 0x4080A0;

	public ReflectivePulse() {
		name = "Reflective Pulse";
		cooldownTime = 4f;
		cooldown = 0;
		description = "This ability will cause a pulse to radiate outward from your ship, reflecting any enemy shots caught in the pulse. Enemy bullets reflected in this way will fly back in the direction from which they came as if they were fired from your own ship."
				+ "\n\n"
				+ "This versatile ability can be used for both offensive and defensive purposes.";

		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(15);
		paint.setColor((alpha << 24) + color);
	}

	@Override
	public void update(float deltaTime) {
		cooldown -= deltaTime;
		if (cooldown < 0)
			cooldown = 0;
		if (activated) {
			if (radius > fullRadius) {
				radius = 0;
				activated = false;
			}
			radius += expansionSpeed * deltaTime;
			Ship s = Assets.playerShip;
			iterator = Assets.bm.enemyBullets.iterator();
			while (iterator.hasNext()) {
				Bullet b = iterator.next();
				float dist = 999;
				dist = (float) Math.sqrt(Math.pow(s.x - b.x, 2)
						+ Math.pow(s.y - b.y, 2));
				if (dist < radius) {
					b.xVel = -b.xVel;
					b.yVel = -b.yVel;
					b.rotation += 180;
					Assets.bm.playerBullets.add(b);
					iterator.remove();
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (activated) {
			int tempAlpha = (int) (alpha * (1 - radius / fullRadius));
			if (tempAlpha < 0x30)
				tempAlpha = 0x30;
			int tempColor = (tempAlpha << 24) + color;
			paint.setColor(tempColor);
			g.drawCircle(Assets.playerShip.x, Assets.playerShip.y, radius,
					paint);
		}
	}

	@Override
	public boolean trigger() {
		if (cooldown == 0) {
			SoundAssets.reflective_pulse.play(1);
			cooldown = cooldownTime;
			radius = 20;
			activated = true;
			return true;
		}
		return false;
	}

}
