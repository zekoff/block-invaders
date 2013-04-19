package net.zekoff.blockinvaders.combat.level;

import java.util.Random;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Greenie;
import net.zekoff.blockinvaders.combat.enemy.SampleBoss;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * A few waves of enemies followed by an endless loop of bosses.
 * 
 * @author Zekoff
 * 
 */
public class SampleLevel extends Level {
	int waveNumber = 1;
	float timeCounter = 0;
	Paint paint = new Paint();
	Typeface typeface = Typeface.create("monospace", Typeface.NORMAL);
	int rotation = 0;

	int textColor = Color.WHITE;
	boolean displayText = false;
	private boolean bossFlag;

	public SampleLevel() {
		paint.setTypeface(typeface);
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(18);

		Enemy enemy;
		Random r = Assets.rand;
		for (int i = 0; i < 4; i++) {
			enemy = new Greenie();
			enemy.x = r.nextFloat() * 320;
			enemy.y = r.nextFloat() * 260;
			Assets.em.enemies.add(enemy);
		}

	}

	@Override
	public void update(float deltaTime) {
		rotation++;
		timeCounter -= deltaTime;
		if (timeCounter < 0)
			timeCounter = 0;
		if (timeCounter > 0) {
			paint.setColor(Color.WHITE);
		} else if (displayText) {
			float tick = 0xFF / 2;
			tick *= deltaTime;
			int alpha = paint.getColor() >>> 24;
			alpha -= tick;
			int newColor = ((int) alpha << 24) + 0xFFFFFF;
			paint.setColor(newColor);
			if (alpha <= 0)
				displayText = false;
		}
		switch (waveNumber) {
		case 1:
		case 2:
		case 3:
		case 4:
			if (Assets.em.enemies.size() == 0) {
				Enemy enemy;
				Random r = Assets.rand;
				for (int i = 0; i < 4; i++) {
					enemy = new Greenie();
					enemy.x = r.nextFloat() * 320;
					enemy.y = r.nextFloat() * 260;
					Assets.em.enemies.add(enemy);
				}
				displayText = true;
				textColor = Color.WHITE;
				timeCounter = 2;
				waveNumber++;
			}
			break;
		case 5:
			// boss wave
			if (Assets.em.enemies.size() == 0) {
				bossFlag = true;
				Assets.playerShip.weaponsDisabled = true;
				SampleBoss boss = new SampleBoss();
				boss.x = 160;
				boss.y = -40;
				Assets.em.enemies.add(boss);
				displayText = true;
				textColor = Color.WHITE;
				timeCounter = 2;
			}
			if (Assets.em.enemies.get(0).y > 90)
				Assets.playerShip.weaponsDisabled = false;
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		switch (waveNumber) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			g.drawTriangle(50, 150, 20, Color.BLUE, rotation, 50, 150);

			if (displayText && !bossFlag)
				g.drawText("Wave " + waveNumber, 160, 30, paint);
			if (displayText && bossFlag)
				g.drawText("Incoming boss", 160, 30, paint);
			break;
		}
	}

}
