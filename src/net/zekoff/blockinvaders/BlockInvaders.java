package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.EndlessRunner;
import net.zekoff.blockinvaders.combat.defense.ArmorBlueprint;
import net.zekoff.blockinvaders.combat.defense.ShieldBlueprint;
import net.zekoff.blockinvaders.combat.perk.DmgIncrease;
import net.zekoff.blockinvaders.combat.perk.Evasive;
import net.zekoff.blockinvaders.combat.perk.IncreasedXp;
import net.zekoff.blockinvaders.combat.perk.Insightful;
import net.zekoff.blockinvaders.combat.perk.RapidFire;
import net.zekoff.blockinvaders.combat.perk.TractorBeam;
import net.zekoff.blockinvaders.combat.perk.Wingmen;
import net.zekoff.blockinvaders.combat.player.Pilot;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import net.zekoff.blockinvaders.utility.LootTable;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Entry point for the game; loads necessary data and displays the main menu.
 * 
 * @author Zekoff
 * 
 */
public class BlockInvaders extends Activity implements Button.OnClickListener {
	Button campaignButton;
	Button optionsButton;
	Button endlessButton;
	Button scenarioButton;
	Button pilotButton;
	Button shipyardButton;
	Button deletePilotButton;
	Button seedAbilitiesPerksButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupListeners();

		// endlessButton.setEnabled(false);
		scenarioButton.setEnabled(false);

		// This sets the Assets and the Pilot object to the same object.
		setupPilot();

		// experimental UncaughtExceptionHandler for base activity;
		// should prevent all force-closes
		// ...and it works so well that when the game fails you never know why
		// its frozen forever
		// Thread.currentThread().setUncaughtExceptionHandler(
		// new UncaughtExceptionHandler() {
		//
		// @Override
		// public void uncaughtException(Thread thread, Throwable ex) {
		// Toast.makeText(getBaseContext(),
		// "Unknown error caught by base Activity", 0)
		// .show();
		// startActivity(new Intent(getBaseContext(),
		// BlockInvaders.class));
		// finish();
		// }
		// });
	}

	private void seedAbilitiesPerks() {
		// temporary debugging addition of all perks and abilities
		Assets.pilot.perks.add(new Evasive());
		Assets.pilot.perks.add(new RapidFire());
		Assets.pilot.perks.add(new DmgIncrease());
		Assets.pilot.perks.add(new IncreasedXp());
		Assets.pilot.perks.add(new Insightful());
		Assets.pilot.perks.add(new TractorBeam());
		Assets.pilot.perks.add(new Wingmen());

		Assets.pilot.abilities.add("Supercharge");
		Assets.pilot.abilities.add("MissileBarrage");
		Assets.pilot.abilities.add("DeployBarrier");
		Assets.pilot.abilities.add("PhaseShift");
		Assets.pilot.abilities.add("ReflectivePulse");
		Assets.pilot.abilities.add("SystemScrambler");
		Assets.pilot.abilities.add("ChainLaser");
		Assets.pilot.abilities.add("TimeWarp");

		// Assets.pilot.inventory.weaponsInventory.add(new
		// MainCannonBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new FanShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory
		// .add(new ScatterShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// BurstShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// RapidShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new BlasterBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new TriShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory
		// .add(new MissileBatteryBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new LaserBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// WaveShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// WideShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory
		// .add(new GrenadeLauncherBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// HelixShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// TwinShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// SpreadShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new VeeShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// MultiShotBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory.add(new
		// HeavyLaserBlueprint(1));
		// Assets.pilot.inventory.weaponsInventory
		// .add(new RocketLauncherBlueprint(1));

		Assets.pilot.ability1 = "ChainLaser";
		Assets.pilot.ability2 = "ReflectivePulse";

		WeaponBlueprint newWeapon = null;
		for (int i = 0; i < 5; i++) {
			newWeapon = LootTable.getRandomWeaponBlueprint(1);
			Assets.pilot.inventory.weaponsInventory.add(newWeapon);
		}
		for (int i = 0; i < Assets.pilot.shipBlueprint.getNumMountPoints(); i++) {
			Assets.pilot.shipBlueprint.weapons[i] = Assets.pilot.inventory.weaponsInventory
					.get(i);
		}

		Assets.pilot.titles.add("Cadet");

		ArmorBlueprint a = new ArmorBlueprint();
		Assets.pilot.shipBlueprint.armor = a;
		Assets.pilot.inventory.armorInventory.add(a);

		ShieldBlueprint s = new ShieldBlueprint();
		Assets.pilot.shipBlueprint.shield = s;
		Assets.pilot.inventory.shieldInventory.add(s);
	}

	private void setupPilot() {
		Assets.pilot = null;
		Pilot.me = null;
		Assets.pilot = Pilot.getPilot();
		if (Pilot.checkError())
			Toast.makeText(this,
					"Error loading pilot data. New pilot created.", 0).show();
	}

	private void setupListeners() {
		campaignButton = (Button) findViewById(R.id.campaignButton);
		campaignButton.setOnClickListener(this);
		optionsButton = (Button) findViewById(R.id.optionsButton);
		optionsButton.setOnClickListener(this);
		endlessButton = (Button) findViewById(R.id.endlessButton);
		endlessButton.setOnClickListener(this);
		scenarioButton = (Button) findViewById(R.id.scenarioButton);
		scenarioButton.setOnClickListener(this);
		pilotButton = (Button) findViewById(R.id.pilotButton);
		pilotButton.setOnClickListener(this);
		shipyardButton = (Button) findViewById(R.id.shipyardButton);
		shipyardButton.setOnClickListener(this);
		deletePilotButton = (Button) findViewById(R.id.deletePilotButton);
		deletePilotButton.setOnClickListener(this);
		seedAbilitiesPerksButton = (Button) findViewById(R.id.seedAbilitiesPerksButton);
		seedAbilitiesPerksButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.campaignButton:
		case R.id.scenarioButton:
			startActivity(new Intent(this, CampaignMainActivity.class));
			break;
		case R.id.endlessButton:
			startActivity(new Intent(this, EndlessRunner.class));
			break;
		case R.id.optionsButton:
			startActivity(new Intent(this, OptionsScreen.class));
			break;
		case R.id.pilotButton:
			startActivity(new Intent(this, PilotData.class));
			break;
		case R.id.shipyardButton:
			startActivity(new Intent(this, Shipyard.class));
			break;
		case R.id.deletePilotButton:
			Pilot.deletePilot();
			setupPilot();
			Toast.makeText(this, "Pilot data reset.", 0).show();
			break;
		case R.id.seedAbilitiesPerksButton:
			seedAbilitiesPerks();
		default:
			// error
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Pilot.savePilot();
	}
}