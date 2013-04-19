package net.zekoff.blockinvaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Screen after clicking "Campaign" on the main menu. Gateway to Mission
 * Briefing screen; also includes buttons for Pilot Data and Shipyard.
 * 
 * @author Zekoff
 * 
 */
public class CampaignMainActivity extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle sis) {
		super.onCreate(sis);
		LinearLayout l = new LinearLayout(this);
		l.setOrientation(LinearLayout.VERTICAL);
		TextView t = new TextView(this);
		t.setText("Campaign");
		Button b = new Button(this);
		b.setOnClickListener(this);
		b.setText("Next mission");
		b.setId(1);

		LinearLayout l2 = new LinearLayout(this);
		l2.setOrientation(LinearLayout.HORIZONTAL);
		Button b2 = new Button(this);
		Button b3 = new Button(this);
		b2.setOnClickListener(this);
		b2.setId(2);
		b3.setOnClickListener(this);
		b3.setId(3);
		b2.setText("Pilot Data");
		b3.setText("Shipyard");
		l2.addView(b2);
		l2.addView(b3);

		l.addView(t);
		l.addView(b);
		l.addView(l2);
		setContentView(l);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case 1:
			intent = new Intent(this, CampaignMissionBriefing.class);
			break;
		case 2:
			intent = new Intent(this, PilotData.class);
			break;
		case 3:
			intent = new Intent(this, Shipyard.class);
			break;
		}
		startActivity(intent);
	}
}
