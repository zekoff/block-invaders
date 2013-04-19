package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.ability.Ability;
import net.zekoff.blockinvaders.combat.player.Pilot;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AbilityMain extends Activity implements Button.OnClickListener {
	Button ability1;
	Button ability2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ability_main);

		setupListeners();
	}

	@Override
	public void onResume() {
		super.onResume();
		Ability a = null;
		try {
			if (Assets.pilot.ability1 != null) {
				a = (Ability) Class.forName(
						Pilot.abilityPackage + Assets.pilot.ability1)
						.newInstance();
				ability1.setText(a.toString());
			} else
				ability1.setText("None");

			if (Assets.pilot.ability2 != null) {
				a = (Ability) Class.forName(
						Pilot.abilityPackage + Assets.pilot.ability2)
						.newInstance();
				ability2.setText(a.toString());
			} else
				ability2.setText("None");
		} catch (Exception e) {
			Log.d("BI_log",
					"Problem loading equipped abilities from pilot data.");
		}
	}

	private void setupListeners() {
		ability1 = (Button) findViewById(R.id.ability1);
		ability1.setOnClickListener(this);
		ability2 = (Button) findViewById(R.id.ability2);
		ability2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, AbilityList.class);
		Bundle extras = new Bundle();
		switch (v.getId()) {
		case R.id.ability1:
			extras.putInt("abilitySlot", 1);
			break;
		case R.id.ability2:
			extras.putInt("abilitySlot", 2);
			break;
		}
		intent.putExtras(extras);
		startActivity(intent);
	}
}
