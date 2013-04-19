package net.zekoff.blockinvaders.deprecated;

import net.zekoff.androidgames.framework.Screen;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.LevelUpScreen;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * The actual Activity that runs the ScrollScreen for pre-alpha testing.
 * 
 * @author Zekoff
 * 
 */
public class ScrollTester extends BlockInvadersGame {

	public boolean backAlreadyPressed = false;

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(2, 111, 0, "Restart Game");
		menu.add(1, 222, 0, "Stage List");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 111:
			renderView.pause();
			Assets.reinit();
			setScreen(new CampaignScreen(this));
			renderView.resume();
			return true;
		case 222:
			startActivity(new Intent(this, StageLister.class));
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (backAlreadyPressed) {
				setScreen(new LevelUpScreen(this, "MISSION ABORTED"));
				return true;
			}
			if (!backAlreadyPressed) {
				Toast.makeText(this, "Press back again to abort the mission.",
						0).show();
				backAlreadyPressed = true;
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public Screen getStartScreen() {
		return new CampaignScreen(this);
	}
}
