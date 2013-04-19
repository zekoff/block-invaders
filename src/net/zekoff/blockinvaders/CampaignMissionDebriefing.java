package net.zekoff.blockinvaders;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.level.CampaignLevel;
import net.zekoff.blockinvaders.combat.loot.Loot;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CampaignMissionDebriefing extends Activity implements
		OnClickListener {
	@Override
	public void onCreate(Bundle sis) {
		super.onCreate(sis);

		// finalize leftover stuff from combat
		ArrayList<Loot> lootGained = new ArrayList<Loot>();
		for (Loot l : Assets.pilot.inventory.temporaryInventory) {
			lootGained.add(l);
		}
		Assets.pilot.inventory.commitLoot(); // move to mission debriefing
		// really, should move this to the "done" button so that debriefing
		// activities have access to it if needed

		Assets.pilot.campaignProgression.campaignLevel++;

		((CampaignLevel) Assets.level).getRewards();

		LinearLayout l = new LinearLayout(this);
		l.setOrientation(LinearLayout.VERTICAL);

		TextView t = new TextView(this);
		String text = "Congratulations! The mission was a success.\n";
		text += "Loot gained:\n";
		for (Loot lg : lootGained) {
			text += lg.toString() + "\n";
		}
		t.setText(text);

		Button b = new Button(this);
		b.setText("Done");
		b.setOnClickListener(this);

		l.addView(t);
		l.addView(b);
		setContentView(l);
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
