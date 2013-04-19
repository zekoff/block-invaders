package net.zekoff.blockinvaders.combat.particle;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Puts up white text on the screen representing how much XP was just gained
 * from the destruction of an enemy.
 * 
 * @author Zekoff
 * 
 */
public class XpGainEmitter extends ParticleEmitter {
	float decayTime = 5f;
	float decayTick = 0xFF / decayTime;
	int xpAmount;
	int color = Color.WHITE;
	Paint paint = new Paint();

	public XpGainEmitter(float x, float y, int xp) {
		this.x = x;
		this.y = y;
		xpAmount = xp;
		paint.setTypeface(Assets.typeface);
		paint.setTextAlign(Paint.Align.CENTER);

		// add a token particle to prevent automatic cleanup
		Particle toAdd = pool.newObject();
		toAdd.reset();
		particles.add(toAdd);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		float alpha = (color & 0xFF000000) >>> 24;
		alpha -= decayTick * deltaTime;
		if (alpha < 0) {
			alpha = 0;
			// free token particle
			pool.free(particles.get(0));
			isExpired = true;
		}
		color = (color & 0x00FFFFFF) + ((int) alpha << 24);
	}

	@Override
	public void draw(Graphics g) {
		paint.setColor(color);
		g.drawText("+" + xpAmount + " XP", x, y, paint);
	}
}
