package net.zekoff.blockinvaders.combat;

import net.zekoff.androidgames.framework.Screen;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.CampaignMissionDebriefing;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * The Activity that holds and runs the Screen for campaign missions.
 * 
 * @author Zekoff
 * 
 */
public class CampaignRunner extends BlockInvadersGame {

	int responseCode = DEFEAT;

	public CampaignRunner() {
		super();
		commitChanges = true;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(3, 333, 0, "Abort Mission");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 333:
			setScreen(new LevelUpScreen(this, "MISSION ABORTED"));
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			getCurrentScreen().pause();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public Screen getStartScreen() {
		return new CampaignLoadingScreen(this);
	}

	@Override
	public void gameOver(int condition) {
		responseCode = condition;
		if (condition == VICTORY) {
			setScreen(new MissionCompleteScreen(this));
		} else if (condition == DEFEAT) {
			setScreen(new MissionFailedScreen(this));
		} else
			throw new RuntimeException("Improper game over condition sent");
	}

	@Override
	public void endGame() {
		if (responseCode == VICTORY) {
			Intent intent = new Intent(this, CampaignMissionDebriefing.class);
			startActivity(intent);
			finish();
		} else if (responseCode == DEFEAT) {
			// actually, this happens on defeat or aborting mission
			finish();
		} else {
			throw new RuntimeException("invalid response code");
		}
	}
}
