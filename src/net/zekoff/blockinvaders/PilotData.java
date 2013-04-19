package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Presents a summary of pilot data and UI elements to begin changing perks and
 * abilities.
 * 
 * @author Zekoff
 * 
 */
public class PilotData extends Activity implements Button.OnClickListener {
	Button abilitiesButton;
	Button perksButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pilot_data);

		setupListeners();

		TextView name = (TextView) findViewById(R.id.pilotDataName);
		name.setText("Pilot name: " + Assets.pilot.name);

		TextView level = (TextView) findViewById(R.id.pilotDataLevel);
		level.setText("Level: " + Assets.pilot.level);

		TextView xp = (TextView) findViewById(R.id.pilotDataXp);
		xp.setText("XP: " + Assets.pilot.xp);

		TextView sp = (TextView) findViewById(R.id.pilotDataPerksUnlocked);
		sp.setText("Total skill points: " + Assets.pilot.totalSkillPoints);

		TextView money = (TextView) findViewById(R.id.pilotDataMoney);
		money.setText("Money: " + Assets.pilot.money);

		if (Assets.pilot.perks.size() == 0)
			perksButton.setEnabled(false);
	}

	private void setupListeners() {
		abilitiesButton = (Button) findViewById(R.id.abilitiesButton);
		abilitiesButton.setOnClickListener(this);
		perksButton = (Button) findViewById(R.id.perksButton);
		perksButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.abilitiesButton:
			startActivity(new Intent(this, AbilityMain.class));
			break;
		case R.id.perksButton:
			startActivity(new Intent(this, PerksList.class));
			break;
		default:
			// error
		}
	}
}
