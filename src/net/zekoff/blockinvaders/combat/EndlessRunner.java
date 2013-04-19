package net.zekoff.blockinvaders.combat;

import net.zekoff.androidgames.framework.Screen;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Activity that holds and runs the game for Endless mode.
 * 
 * @author Zekoff
 * 
 */
public class EndlessRunner extends BlockInvadersGame {

	public int responseCode = DEFEAT;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(3, 333, 0, "Eject and Return to Base");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 333:
			setScreen(new LevelUpScreen(this, "EJECTED INTO DEEP SPACE"));
			return true;
		}
		return false;
	}

	@Override
	public Screen getStartScreen() {
		return new EndlessLoadingScreen(this);
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
	public void gameOver(int condition) {
		responseCode = condition;
		if (condition == VICTORY) {
			setScreen(new LevelUpScreen(this, "RETURNED SAFELY"));
		} else if (condition == DEFEAT) {
			LevelUpScreen lus = new LevelUpScreen(this, "KILLED IN ACTION");
			lus.headerColor = Color.RED;
			setScreen(lus);
		} else
			throw new RuntimeException("Improper game over condition sent");
	}

	@Override
	public void endGame() {
		if (responseCode == VICTORY) {
			// start the loot-review activity; won't be campaign debriefing for
			// endless mode. chose to return to base
			// Intent intent = new Intent(this,
			// CampaignMissionDebriefing.class);
			// startActivity(intent);
			Assets.pilot.inventory.commitLoot(); // move to mission debriefing
			getHandler().post(new Runnable() {
				public void run() {
					Toast.makeText(EndlessRunner.this,
							"Loot from mission deposited at base.", 0).show();
				}
			});
			finish();
		} else if (responseCode == DEFEAT) {
			// was destroyed mid-sector
			getHandler().post(new Runnable() {
				public void run() {
					Toast.makeText(EndlessRunner.this,
							"Loot from mission lost.", 0).show();
				}
			});
			finish();
		} else {
			throw new RuntimeException("invalid response code");
		}
	}

}
