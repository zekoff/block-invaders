package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignRunner;
import net.zekoff.blockinvaders.combat.level.CampaignLevel;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Screen between Campaign Main Screen and The actual mission. Includes red
 * "LAUNCH" button and briefing text.
 * 
 * @author Zekoff
 * 
 */
public class CampaignMissionBriefing extends Activity implements
		OnClickListener {
	@Override
	public void onCreate(Bundle sis) {
		super.onCreate(sis);

		LinearLayout l = new LinearLayout(this);
		l.setOrientation(LinearLayout.VERTICAL);

		TextView t = new TextView(this);
		CampaignLevel level = Assets.pilot.campaignProgression
				.getCurrentLevel(null);
		String briefing = level.getBriefing();
		if (briefing == null)
			briefing = "No briefing implemented.";
		t.setText(briefing);

		Button b = new Button(this);
		b.setText("LAUNCH");
		b.setOnClickListener(this);
		b.setBackgroundColor(0xFFFF0000);
		b.setTypeface(b.getTypeface(), Typeface.BOLD);
		b.setTextColor(0xFFFFFFFF);

		l.addView(t);
		l.addView(b);
		setContentView(l);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, CampaignRunner.class);
		startActivity(intent);
		finish();
	}
}
