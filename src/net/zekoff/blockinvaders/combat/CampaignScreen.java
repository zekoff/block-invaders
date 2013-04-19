package net.zekoff.blockinvaders.combat;

import java.util.List;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Input.TouchEvent;
import net.zekoff.androidgames.framework.Screen;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.combat.collision.CollisionScan;
import net.zekoff.blockinvaders.combat.level.CampaignLevel2Old;
import net.zekoff.blockinvaders.combat.player.Pilot;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;

/**
 * This class is tailored to run campaign levels. This is primarily due to its
 * inclusion of a mission complete routine which campaign levels can access.
 * 
 * @author Zekoff
 * 
 */
public class CampaignScreen extends Screen {

	float playerDeadCounter = 0;
	int collisionCounter = 0;
	boolean reduceCollisionDetection = false;

	public boolean missionComplete = false;
	float missionCompleteCounter = 0;

	public boolean paused = false;
	Paint paint = new Paint();
	private boolean playerDead;

	public CampaignScreen(BlockInvadersGame game) {
		super(game);

		// Retrieve shared preferences, set collision precision
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(game);
		reduceCollisionDetection = prefs.getBoolean("reduce_collision", false);

		// Load correct level (which also loads background if not starfield)
		Assets.level = Assets.pilot.campaignProgression.getCurrentLevel(this);

		//Assets.level = new CampaignLevel2Old(this);

	}

	@Override
	public void update(float deltaTime) {
		// if (deltaTime > .034)
		// deltaTime = .034f;

		float fullDeltaTime = deltaTime;
		deltaTime *= Assets.gameSpeed;

		if (paused) {
			List<TouchEvent> tes = game.getInput().getTouchEvents();
			for (TouchEvent te : tes) {
				if (te.type == TouchEvent.TOUCH_UP) {
					paused = false;
					SoundAssets.music.play();
				}
			}
		}

		// Give Flyup update chance first, otherwise Control will wipe out touch
		// events
		Assets.hud.flyup.update(deltaTime);

		if (!paused) {

			if (!reduceCollisionDetection)
				CollisionScan.fullScan();
			else {
				collisionCounter++;
				if (collisionCounter == 1) {
					collisionCounter = 0;
					CollisionScan.fullScan();
				} else {
					CollisionScan.scanEnemyBullets();
				}
			}

			Assets.background.update(deltaTime);
			Assets.pm.update(deltaTime);
			Assets.bm.update(deltaTime);
			Assets.em.update(deltaTime);
			Assets.playerShip.update(fullDeltaTime);
			Assets.am.update(deltaTime);

			Assets.control.update(fullDeltaTime);
			Assets.level.update(deltaTime);
			Assets.hud.update(fullDeltaTime);

			// Last thing every frame: check for win/loss

			if (Assets.playerShip.hp <= 0) {
				playerDead = true;
			}

			if (playerDead) {
				playerDeadCounter += deltaTime;
				Assets.playerShip.weaponsDisabled = true;
				// should also cause player ship to lose control here and
				// spin
				// randomly and move in a random direction
				if (Assets.rand.nextInt(10) == 0) {
					float rx = Assets.rand.nextFloat() * 20 - 10;
					float ry = Assets.rand.nextFloat() * 20 - 10;
					float rs = Assets.rand.nextFloat() * 10 + 4;
					Assets.pm.addExplosionEmitter(Assets.playerShip.x + rx,
							Assets.playerShip.y + ry, rs);
				}
				// TODO make ship explode into particles for a few seconds
				// before game-over screen
				if (playerDeadCounter > 4)
					game.gameOver(BlockInvadersGame.DEFEAT);
			}

			if (missionComplete) {
				missionCompleteCounter += deltaTime;
				Assets.playerShip.isHittable = false;
				Assets.playerShip.weaponsDisabled = true;
				if (missionCompleteCounter > 1) {
					Assets.playerShip.yVel -= 80 * deltaTime;
					Assets.background.speed += 120 * deltaTime;
					if (Assets.background.speed > 500)
						Assets.background.speed = 500;
				}
				if (missionCompleteCounter > 5)
					game.gameOver(BlockInvadersGame.VICTORY);
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		Assets.background.drawBackground(g);
		Assets.em.draw(g);
		Assets.playerShip.draw(g);
		Assets.bm.draw(g);
		Assets.pm.draw(g);
		Assets.am.draw(g);
		Assets.background.drawForeground(g);
		Assets.level.draw(g);

		// draw HUD
		Assets.hud.draw(g);
		Assets.control.draw(g);
		Assets.hud.flyup.draw(g);

		if (paused) {
			paint.setColor(0xFF202020);
			paint.setStyle(Paint.Style.FILL);
			g.drawRoundRect(40, 180, 240, 160, paint);
			paint.setColor(Color.WHITE);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(5);
			g.drawRoundRect(40, 180, 240, 160, paint);

			paint.setColor(Color.WHITE);
			paint.setStyle(Paint.Style.FILL);
			paint.setTextSize(24);
			paint.setTextAlign(Paint.Align.CENTER);
			g.drawText("PAUSED", 160, 240, paint);
			paint.setTextSize(16);
			g.drawText("Touch screen to continue...", 160, 300, paint);
		}
	}

	@Override
	public void pause() {
		paused = true;
		SoundAssets.music.pause();
	}

	@Override
	public void resume() {
		SoundAssets.music.play();
	}

	@Override
	public void dispose() {
		Pilot.savePilot();
	}

}
