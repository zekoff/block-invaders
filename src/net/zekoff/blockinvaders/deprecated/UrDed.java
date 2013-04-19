package net.zekoff.blockinvaders.deprecated;

import net.zekoff.blockinvaders.combat.Assets;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Game over screen.
 * 
 * @author Zekoff
 * 
 */
public class UrDed extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView t = new TextView(this);
		int numWeaps = Assets.pilot.inventory.junkInventory.size();
		t.setText("Pilot name: " + Assets.pilot.name + "\nLevel: "
				+ Assets.pilot.level + "\nXP: " + Assets.pilot.xp + "\n"
				+ numWeaps + " pieces of junk loot in inventory.\n"
				+ "Press back to return to menu.");
		setContentView(t);
		Assets.reinit();
	}
}
