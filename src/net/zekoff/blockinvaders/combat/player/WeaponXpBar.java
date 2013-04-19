package net.zekoff.blockinvaders.combat.player;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class WeaponXpBar {
	private static final double FILL_SPEED = .7;
	public String weaponName = "";
	public boolean isFinished = false;
	public float xpBar = 0;
	public int levelsGained = 0;
	public int initialLevel = 0;
	public float counter = 0;
	public boolean displayLevelUpInBar = false;
	public WeaponBlueprint wb = null;
	Paint paint = new Paint();
	RectF rectF = new RectF();
	float y = 0;
	private boolean dinged = false;

	public void update(float deltaTime) {
		if (levelsGained > 0) {
			// fill bar to 100%
			if (xpBar < 1)
				fillXpBar(deltaTime);
			else {
				// show level-up results
				// if (counter == 0) {
				// Assets.pm.addPlusOneEmitter(290, y);
				// }
				counter += deltaTime;
				if (counter < 0.5f) {
					if (!dinged) {
						dinged = true;
						SoundAssets.ding.play(1);
					}
					displayLevelUpInBar = true;
				} else {
					dinged = false;
					if (wb.getPercentageToLevel() != -1 || levelsGained > 0)
						xpBar = 0;
					else
						xpBar = -1;
					initialLevel++;
					levelsGained--;
					counter = 0;
					displayLevelUpInBar = false;
				}
			}
		} else {
			fillXpBarTo(deltaTime, wb.getPercentageToLevel());
		}
	}

	public void draw(Canvas canvas, int position) {
		// draw xp bar titles
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(16);
		paint.setTextAlign(Paint.Align.LEFT);
		y = 200 + 59 * position;
		canvas.drawText(weaponName, 15, y, paint);
		paint.setTextSize(12);

		// draw weapon XP bar
		y += paint.getFontSpacing() - 5;
		// draw background
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xFF505050);
		rectF.top = y;
		rectF.left = 50;
		rectF.right = 300;
		rectF.bottom = y + 10;
		canvas.drawRoundRect(rectF, 3, 3, paint);

		// draw filled-in bar
		rectF.top = y;
		rectF.left = 50;
		rectF.right = 50 + 230 * xpBar + 20;
		rectF.bottom = y + 10;
		paint.setColor(0xFF00FFFF);
		canvas.drawRoundRect(rectF, 3, 3, paint);

		// draw outline
		rectF.top = y;
		rectF.left = 50;
		rectF.right = 300;
		rectF.bottom = y + 10;
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);
		canvas.drawRoundRect(rectF, 3, 3, paint);

		// draw level
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
		if (wb.upgradeLevel >= WeaponBlueprint.MAX_LEVELS
				&& initialLevel >= WeaponBlueprint.MAX_LEVELS)
			canvas.drawText("MAX", 32f, y + 9, paint);
		else
			canvas.drawText(Integer.toString(initialLevel), 32f, y + 9, paint);

		if (displayLevelUpInBar) {
			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(14);
			paint.setTextAlign(Paint.Align.CENTER);
			paint.setColor(Color.YELLOW);
			canvas.drawText("LEVEL UP!", 160, y + 23, paint);
		}
	}

	private void fillXpBar(float deltaTime) {
		xpBar += FILL_SPEED * deltaTime;
		if (xpBar > 1) {
			xpBar = 1;
			return;
		}
		for (int i = 0; i < 2; i++) {
			float yOffset = Assets.rand.nextFloat() * 10;
			Assets.pm.addChunkEmitter(70 + 230 * xpBar, y + yOffset, 1,
					0xff00ffff, 2);
		}
	}

	private void fillXpBarTo(float deltaTime, float percentageToLevel) {
		xpBar += FILL_SPEED * deltaTime;
		if (xpBar > percentageToLevel) {
			xpBar = percentageToLevel;
			isFinished = true;
			return;
		}
		for (int i = 0; i < 2; i++) {
			float yOffset = Assets.rand.nextFloat() * 10;
			Assets.pm.addChunkEmitter(70 + 230 * xpBar, y + yOffset, 1,
					0xff00ffff, 2);
		}
	}
}
