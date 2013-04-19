package net.zekoff.blockinvaders.combat.weapon;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import android.graphics.Paint;

public class HeavyLaserBlueprint extends WeaponBlueprint {

	// TODO check power-setting code for lasers

	public HeavyLaserBlueprint(int level) {
		super(level);
		name = "Heavy Laser";
	}

	@Override
	public Weapon getWeapon() {
		Weapon w = new Weapon() {
			Enemy currentTarget = null;
			boolean active = false;
			float timeActive = 0;
			Point drawPoint = null;
			private Paint paint = new Paint();

			class Point {
				public float x = -999;
				public float y = -999;

				Point(float x, float y) {
					this.x = x;
					this.y = y;
				}
			}

			@Override
			public void update(float deltaTime) {
				super.update(deltaTime);
				
				if (active) {
					if (timeActive > .8f)
						active = false;
					timeActive += deltaTime;
					if (!Assets.em.enemies.contains(currentTarget))
						currentTarget = null;
					if (currentTarget != null) {
						currentTarget.hp -= power * deltaTime;
						currentTarget.updateColor(power * 2.5f * deltaTime);
						currentTarget.update(0);
						drawPoint = new Point(currentTarget.x, currentTarget.y);
						int color = (0xFF << 24) + (bulletColor & 0x00FFFFFF);
						Assets.pm.addChunkEmitter(currentTarget.x,
								currentTarget.y, 1, color, 3);
					}
				}
			}

			private void pickTarget() {
				if (Assets.em.enemies.size() > 0) {
					int index = Assets.rand.nextInt(Assets.em.enemies.size());
					currentTarget = Assets.em.enemies.get(index);
				}
			}

			@Override
			public void fire() {
				active = true;
				timeActive = 0;
				pickTarget();
			}

			@Override
			public void draw(Graphics g, float x, float y) {
				paint.setColor(bulletColor);
				paint.setStrokeWidth(6);
				paint.setStyle(Paint.Style.STROKE);
				if (drawPoint != null)
					g.drawLine(x, y, drawPoint.x, drawPoint.y, paint);
				drawPoint = null;
			}
		};

		w.cooldown = 2.4f;
		w.bulletColor = 0xA000FF00;
		setPower(w);
		w.power *= 1.0f;

		setupPerks(w);
		return w;
	}

	@Override
	public String getTypeName() {
		return "Heavy Laser";
	}

}
