package net.zekoff.blockinvaders.combat.ability;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.utility.StatLookup;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

public class ChainLaser extends Ability {
	public Paint paint = new Paint();
	public Path path = new Path();
	public float durationMax = 1.6f;
	public float duration = 0;
	public boolean active = false;
	public int numTargets = 4;
	public float dps = 0;
	public ArrayList<Point> chain = new ArrayList<Point>();
	public ArrayList<Enemy> alreadyChained = new ArrayList<Enemy>();
	int color = 0x6060C060;

	float debugPower = 0;

	private class Point {
		float x = -999;
		float y = -999;

		Point(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	public ChainLaser() {
		cooldown = 0;
		cooldownTime = 6f;
		name = "Chain Laser";
		description = "Fire a laser that jumps from one target to another, chaining to a maximum of "
				+ numTargets
				+ " targets. The laser does constant damage that scales with pilot level and bypasses all enemy armor."
				+ "\n\n"
				+ "This ability is exceptionally powerful against "
				+ numTargets
				+ " or more targets. It suffers no loss in damage by chaining to additional targets, effectively multiplying its power by the number of targets in the chain.";

		dps = StatLookup.lookup(Assets.pilot.level, 20) * 1.5f;
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);
		paint.setColor(color);
	}

	@Override
	public void update(float deltaTime) {
		alreadyChained.clear();
		cooldown -= deltaTime;
		if (cooldown < 0)
			cooldown = 0;
		if (duration > durationMax) {
			duration = 0;
			active = false;
			Log.d("BI_log_chainlaser", "Total dmg of laser: " + debugPower);
		}
		if (active) {
			duration += deltaTime;
			// build chain of enemies
			chain.add(new Point(Assets.playerShip.x, Assets.playerShip.y));
			for (int i = 0; i < numTargets; i++) {
				Point oldPoint = chain.get(chain.size() - 1);
				Enemy candidateEnemy = null;
				float dist = 999;
				for (Enemy e : Assets.em.enemies) {
					if (!alreadyChained.contains(e)) {
						float newDist = (float) Math.sqrt(Math.pow(e.x
								- oldPoint.x, 2)
								+ Math.pow(e.y - oldPoint.y, 2));
						if (newDist < dist) {
							candidateEnemy = e;
							dist = newDist;
						}
					}
				}
				if (candidateEnemy != null) {
					chain.add(new Point(candidateEnemy.x, candidateEnemy.y));
					alreadyChained.add(candidateEnemy);
					float power = 0;
					power = dps * deltaTime;
					debugPower += power;
					candidateEnemy.hp -= power;
					candidateEnemy.updateColor(power);
					candidateEnemy.update(0);
					int color = paint.getColor();
					color = (0xFF << 24) + (color & 0x00FFFFFF);
					Assets.pm.addBurstEmitter(candidateEnemy.x,
							candidateEnemy.y, 1, color);
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (active && chain.size() > 0) {
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(color);
			path.reset();
			path.moveTo(chain.get(0).x, chain.get(0).y);
			for (int i = 1; i < chain.size(); i++) {
				path.lineTo(chain.get(i).x, chain.get(i).y);
			}
			if (chain.size() == 1)
				path.lineTo(chain.get(0).x, -50);
			g.drawPath(path, paint);
			paint.setStyle(Paint.Style.FILL);
			int tempColor = paint.getColor();
			tempColor = (0xFF << 24) + (tempColor & 0x00FFFFFF);
			paint.setColor(tempColor);
			// for (Point p : chain) {
			// g.drawCircle(p.x, p.y, 4, paint);
			// }
			Point p = chain.get(chain.size() - 1);
			g.drawCircle(p.x, p.y, 5, paint);
			p = chain.get(0);
			g.drawCircle(p.x, p.y, 5, paint);
		}

		chain.clear();
	}

	@Override
	public boolean trigger() {
		if (cooldown == 0) {
			SoundAssets.chain_laser.play(0.5f);
			duration = 0;
			active = true;
			cooldown = cooldownTime;
			debugPower = 0;
			return true;
		}
		return false;
	}

}
