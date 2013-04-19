package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.EndlessScreen;
import net.zekoff.blockinvaders.combat.endless.CompletionCriteria;
import net.zekoff.blockinvaders.combat.endless.EnemyWave;
import net.zekoff.blockinvaders.combat.endless.NoEnemies;
import net.zekoff.blockinvaders.combat.endless.SectorQuest;
import net.zekoff.blockinvaders.combat.endless.SpawnFighters;
import net.zekoff.blockinvaders.combat.endless.SpawnRandomBunch;
import net.zekoff.blockinvaders.combat.endless.SpawnSwoopers;
import net.zekoff.blockinvaders.combat.endless.SpawnWeak;
import net.zekoff.blockinvaders.combat.endless.SpawnYWings;
import net.zekoff.blockinvaders.combat.hud.FlyupListener;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * The "Level" used by Endless Mode. Logic for spawning new waves, transitioning
 * between zones, etc. can appear in this class. If it needs to use subclasses
 * to help do its job, that's fine as well.
 * 
 * @author Zekoff
 * 
 */
public class EndlessLevel extends Level implements FlyupListener {

	EndlessScreen screen;
	private Paint paint;

	public int sector = 0;
	boolean sectorComplete = false;
	boolean advanceSector = true;
	boolean betweenSectors = false;

	EnemyWave wave = null;
	CompletionCriteria criteria = null;
	SectorQuest quest = null;

	public EndlessLevel(EndlessScreen screen) {
		this.screen = screen;

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Paint.Align.CENTER);

		Assets.hud.flyup.registerForNotification(this);
		setEnemyWave();
		criteria = new NoEnemies(this);
	}

	@Override
	public void update(float deltaTime) {
		if (advanceSector) {
			sector++;
			advanceSector = false;
			Assets.hud.queueMessage("ENTERING SECTOR " + sector);
		}

		// check conditions of level and set appropriately
		if (!betweenSectors) {
			wave.update(deltaTime);
			criteria.update(deltaTime);
			if (quest != null)
				quest.update(deltaTime);
		}

		if (sectorComplete) {
			// queue a choice to continue or quit
			sectorComplete = false;
			betweenSectors = true;
			String nextSectorText = "";

			setEnemyWave();

			criteria = new NoEnemies(this);

			nextSectorText = wave.getDescription();
			nextSectorText += " Continue on to the next sector?";
			Assets.hud.flyup.queueChoice(nextSectorText, "AFFIRMATIVE",
					"NEGATIVE");
		}
	}

	public void setEnemyWave() {
		// choose next enemy wave type
		if (true) {
			wave = new SpawnRandomBunch(this);
			return;
		}
		// TODO perhaps make a list of waves to spawn?
		int waveType = Assets.rand.nextInt(5);
		switch (waveType) {
		case 0:
			wave = new SpawnFighters(this);
			break;
		case 1:
			wave = new SpawnSwoopers(this);
			break;
		case 2:
			wave = new SpawnYWings(this);
			break;
		case 3:
			wave = new SpawnRandomBunch(this);
			break;
		case 4:
			wave = new SpawnWeak(this);
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		// Assets.game.getGraphics().drawText("THIS IS ENDLESS MODE", 160, 50,
		// paint);
	}

	@Override
	public void onFlyupOpen() {
		Assets.playerShip.isHittable = false;
	}

	@Override
	public void onFlyupClosed() {
		Assets.playerShip.isHittable = true;
	}

	@Override
	public void onChoice(String choice) {
		if (choice.compareTo("AFFIRMATIVE") == 0) {
			advanceSector = true;
			betweenSectors = false;
		} else {
			screen.missionComplete = true;
			Assets.hud.queueMessage("RETURNING TO BASE...");
		}
	}

	public void sectorComplete() {
		sectorComplete = true;
		Assets.bm.enemyBullets.clear();
		Assets.am.ability1.cooldown = 0;
		Assets.am.ability2.cooldown = 0;
	}

}
