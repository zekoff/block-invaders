package net.zekoff.blockinvaders.combat;

import java.util.ArrayList;
import java.util.List;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Input.TouchEvent;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.androidgames.framework.Screen;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.androidgames.framework.impl.AndroidPixmap;
import net.zekoff.blockinvaders.combat.background.DynamicStarfield;
import net.zekoff.blockinvaders.combat.player.Rewards;
import net.zekoff.blockinvaders.combat.player.WeaponXpBar;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import net.zekoff.blockinvaders.utility.XpTable;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class LevelUpScreen extends Screen {
	private static final double FILL_SPEED = .3;
	Paint paint = new Paint();
	ArrayList<Rewards> rewards = null;
	private float xpBar;
	float initialDelay = 0;
	float counter = 0;
	int initialLevel = 0;
	String headerMessage = "";
	int timesToFillXpBar = 0;
	private boolean displayLevelUpInBar;
	private int xpGained = 0;
	private int overlayAlpha = 0xFF;
	float finishedCounter = 0;
	int headerColor = Color.WHITE;
	boolean dinged = false;

	boolean xpBarDone = false;
	ArrayList<WeaponXpBar> weaponBars = new ArrayList<WeaponXpBar>();

	public Bitmap overlay = null;
	public Canvas canvas = null;
	public Pixmap pixmap = null;
	public RectF rectF = new RectF();
	public Paint overlayPaint = new Paint();

	public DisplayMode mode = DisplayMode.xp;

	enum DisplayMode {
		xp, rewards, finished
	}

	public LevelUpScreen(BlockInvadersGame game, String headerMessage) {
		super(game);
		this.headerMessage = headerMessage;
		Assets.pm.reinit();
		Assets.background = new DynamicStarfield(40);
		xpBar = XpTable.getPercentageToLevel();
		xpGained = Assets.pilot.xpGained;
		int cappedXp = XpTable.getXpToLevel(Assets.pilot.levelCap);
		boolean xpCapped = Assets.pilot.xp + xpGained > cappedXp;
		initialLevel = Assets.pilot.level;
		Assets.pilot.commitXp();
		if (xpCapped)
			Assets.pilot.xp = cappedXp;
		rewards = XpTable.checkLevelGain();
		if (rewards != null)
			timesToFillXpBar = rewards.size();

		overlay = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
		canvas = new Canvas(overlay);
		pixmap = new AndroidPixmap(overlay, Graphics.PixmapFormat.ARGB8888);

		for (WeaponBlueprint w : Assets.pilot.shipBlueprint.weapons) {
			if (w != null) {
				WeaponXpBar wb = new WeaponXpBar();
				wb.weaponName = w.toString();
				wb.xpBar = w.getPercentageToLevel();
				wb.initialLevel = w.upgradeLevel;
				w.commitXp();
				wb.levelsGained = w.checkLevelGain();
				wb.wb = w;
				weaponBars.add(wb);

			}
		}
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchList = game.getInput().getTouchEvents();
		if (touchList.size() > 0 && initialDelay > 2) {
			done();
		}

		if (mode == DisplayMode.xp) {

			initialDelay += deltaTime;
			if (initialDelay > 1) {
				for (WeaponXpBar wb : weaponBars) {
					wb.update(deltaTime);
				}
				if (XpTable.getPercentageToLevel() != -1 && xpBar < 0)
					xpBar = 0;
				if (timesToFillXpBar > 0) {
					// fill bar to 100%
					if (xpBar < 1)
						fillXpBar(deltaTime);
					else {
						// show level-up results
						// if (counter == 0) {
						// Assets.pm.addPlusOneEmitter(290, 117);
						// }
						counter += deltaTime;
						if (counter < 1.5f) {
							if (!dinged) {
								dinged = true;
								SoundAssets.ding.play(1);
							}
							displayLevelUpInBar = true;
						} else {
							dinged = false;
							xpBar = -1;
							initialLevel++;
							timesToFillXpBar--;
							counter = 0;
							displayLevelUpInBar = false;
						}
					}
				} else {
					fillXpBarTo(deltaTime, XpTable.getPercentageToLevel());
				}
			}

			if (checkFinished()) {
				finishedCounter += deltaTime;
				if (finishedCounter > 3f)
					overlayAlpha -= 0xFF * .5 * deltaTime;
				if (overlayAlpha < 0) {
					overlayAlpha = 0;
					if (rewards != null)
						mode = DisplayMode.rewards;
					else
						mode = DisplayMode.finished;
				}
			}
		}

		if (mode == DisplayMode.rewards) {
			if (overlayAlpha < 0xFF) {
				overlayAlpha += 0xFF * .5 * deltaTime;
			}
			if (overlayAlpha > 0xFF)
				overlayAlpha = 0xFF;
			if (rewards != null && rewards.size() > 0) {
				rewards.get(0).update(deltaTime);
				if (rewards.size() > 1 && rewards.get(0).isExpired)
					rewards.remove(0);
			}
		}

		Assets.background.update(deltaTime);
		Assets.pm.update(deltaTime);
	}

	void done() {
		game.endGame();
	}

	private boolean checkFinished() {
		boolean weaponsFinished = true;
		for (WeaponXpBar w : weaponBars) {
			if (!w.isFinished)
				weaponsFinished = false;
		}
		if (weaponsFinished && xpBarDone)
			return true;
		else
			return false;
	}

	private void fillXpBarTo(float deltaTime, float percentageToLevel) {
		xpBar += FILL_SPEED * deltaTime;
		if (xpBar > percentageToLevel) {
			xpBar = percentageToLevel;
			xpBarDone = true;
			return;
		}
		for (int i = 0; i < 2; i++) {
			float y = Assets.rand.nextFloat() * 25 + 130;
			Assets.pm.addChunkEmitter(40 + 260 * xpBar, y, 1, Color.YELLOW, 3);
		}
	}

	private void fillXpBar(float deltaTime) {
		xpBar += FILL_SPEED * deltaTime;
		if (xpBar > 1) {
			xpBar = 1;
			return;
		}
		for (int i = 0; i < 2; i++) {
			float y = Assets.rand.nextFloat() * 25 + 130;
			Assets.pm.addChunkEmitter(40 + 260 * xpBar, y, 1, Color.YELLOW, 3);
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.background.drawBackground(g);

		// draw header
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(headerColor);
		paint.setTextSize(25);
		paint.setTextAlign(Paint.Align.CENTER);
		g.drawText(headerMessage, 160, 30, paint);
		paint.setStrokeWidth(3);
		paint.setStyle(Paint.Style.STROKE);
		g.drawLine(20, 40, 300, 40, Color.WHITE);

		overlay.eraseColor(0x00000000);

		if (mode == DisplayMode.xp) {
			// draw screen main contents
			// if xp bars still filling, draw them
			drawXpBars();
		}

		if (mode == DisplayMode.rewards) {
			// if xp bars done and rewards to display, draw them
			if (rewards != null && rewards.size() > 0) {
				rewards.get(0).draw(canvas);
			}
		}

		overlayPaint.setColor((overlayAlpha << 24) + 0xFFFFFF);
		g.drawPixmap(pixmap, 0, 0, overlayPaint);
		Assets.pm.draw(g);
	}

	private void drawXpBars() {
		// draw xp bar titles
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(Assets.pilot.name, 160, 100, paint);
		paint.setTextSize(14);
		canvas.drawText("Level " + initialLevel, 160,
				100 + paint.getFontSpacing() + 3, paint);
		paint.setTextSize(16);
		canvas.drawText("XP Gained: " + xpGained, 160,
				40 + paint.getFontSpacing() + 4, paint);

		// draw pilot XP bar
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(0xFF505050);
		rectF.top = 130;
		rectF.left = 20;
		rectF.right = 20 + 280;
		rectF.bottom = 130 + 25;
		canvas.drawRoundRect(rectF, 15, 15, paint);

		rectF.top = 130;
		rectF.left = 20;
		rectF.right = 20 + 260 * xpBar + 20;
		rectF.bottom = 130 + 25;
		paint.setColor(0xFF00FF00);
		canvas.drawRoundRect(rectF, 15, 15, paint);

		rectF.top = 130;
		rectF.left = 20;
		rectF.right = 20 + 280;
		rectF.bottom = 130 + 25;
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(3);
		canvas.drawRoundRect(rectF, 15, 15, paint);

		if (displayLevelUpInBar) {
			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(14);
			paint.setTextAlign(Paint.Align.CENTER);
			paint.setColor(Color.BLACK);
			canvas.drawText("LEVEL UP!", 160, 147, paint);
		}

		// draw weapon xp bars
		for (int i = 0; i < weaponBars.size(); i++) {
			weaponBars.get(i).draw(canvas, i);
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
