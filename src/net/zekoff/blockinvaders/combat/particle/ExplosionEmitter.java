package net.zekoff.blockinvaders.combat.particle;

import net.zekoff.androidgames.framework.Graphics;
import android.graphics.Paint;

public class ExplosionEmitter extends ParticleEmitter {
	float x;
	float y;
	float currentRadius = 0;
	float targetRadius;
	int color;
	Paint paint = new Paint();
	float expansionRate;
	int alpha;
	float durationMax = 2;
	float durationCurrent = 0;

	public ExplosionEmitter(float x, float y, float targetRadius) {
		this.x = x;
		this.y = y;
		this.targetRadius = targetRadius;
		color = 0xFFFF5000;
		paint.setStyle(Paint.Style.FILL);
		expansionRate = targetRadius * 2;
	}

	@Override
	public void update(float deltaTime) {
		if (currentRadius < targetRadius) {
			currentRadius += expansionRate * deltaTime;
		} else {
			expansionRate -= expansionRate * 3 * deltaTime;
			if (expansionRate < .5f)
				expansionRate = .5f;
			currentRadius += expansionRate * deltaTime;
		}
		durationCurrent += deltaTime;
		alpha = (int) (0xFF * (1 - durationCurrent / durationMax));
		color = (alpha << 24) + (color & 0x00FFFFFF);
		if (durationCurrent > durationMax) {
			isExpired = true;
		}
	}

	@Override
	public void draw(Graphics g) {
		paint.setColor(color);
		g.drawCircle(x, y, currentRadius, paint); // correct one
	}
}
