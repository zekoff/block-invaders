package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.ability.Ability;
import net.zekoff.blockinvaders.combat.player.Pilot;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AbilityDetail extends Activity implements Button.OnClickListener {
	Button equipAbilityButton;
	TextView cooldownTextView;
	TextView descriptionTextView;
	String abilityName = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ability_detail);

		setupListeners();

		abilityName = this.getIntent().getExtras().getString("abilityName");
		Ability a = null;
		try {
			a = (Ability) Class.forName(Pilot.abilityPackage + abilityName)
					.newInstance();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TextView an = (TextView) findViewById(R.id.abilityDetailAbilityName);
		an.setText(a.name);

		cooldownTextView.setText("Cooldown: " + Float.toString(a.cooldownTime)
				+ " seconds");
		descriptionTextView.setText(a.description);
	}

	private void setupListeners() {
		equipAbilityButton = (Button) findViewById(R.id.equipAbilityButton);
		equipAbilityButton.setOnClickListener(this);
		cooldownTextView = (TextView) findViewById(R.id.abilityDetailAbilityCooldown);
		cooldownTextView.setOnClickListener(this);
		descriptionTextView = (TextView) findViewById(R.id.abilityDetailAbilityDescription);
		descriptionTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.equipAbilityButton:
			Toast.makeText(this, "Ability equipped", 0).show();
			int abilitySlot = getIntent().getExtras().getInt("abilitySlot");
			if (abilitySlot == 1)
				Assets.pilot.ability1 = abilityName;
			else
				Assets.pilot.ability2 = abilityName;
			finish();
		}
	}
}
