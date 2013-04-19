package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.weapon.Weapon;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Displays detailed information about a weapon along with buttons to interact
 * with it.
 * 
 * @author Zekoff
 * 
 */
public class WeaponDetail extends Activity implements Button.OnClickListener {
	Button equipButton;
	Button customizeButton;
	Button sellButton;
	WeaponBlueprint w = null;
	public static final int SELL_DIALOG = 0x12345;
	int mountpoint;

	@Override
	public Dialog onCreateDialog(int dialog) {
		AlertDialog.Builder builder = new AlertDialog.Builder(WeaponDetail.this);
		builder.setMessage(
				"Sell " + w.getName() + " for $" + w.getValue() / 4 + "?")
				.setCancelable(true)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// sell weapon
								Assets.pilot.shipBlueprint.weapons[mountpoint] = null;
								Assets.pilot.inventory.weaponsInventory
										.remove(w);
								Assets.pilot.money += w.getValue() / 4;
								Toast.makeText(WeaponDetail.this, "Sold.", 0)
										.show();
								WeaponDetail.this.finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		return alert;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weapon_detail);

		setupListeners();

		int position = getIntent().getExtras().getInt("position");
		int hashCode = getIntent().getExtras().getInt("hashCode");
		mountpoint = getIntent().getExtras().getInt("mountPoint");

		for (WeaponBlueprint wb : Assets.pilot.inventory.weaponsInventory) {
			if (wb.hashCode() == hashCode)
				w = wb;
		}

		Button cwb = (Button) findViewById(R.id.customizeWeaponButton);
		cwb.setEnabled(false);

		Button swb = (Button) findViewById(R.id.sellWeaponButton);
		swb.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(SELL_DIALOG);
			}
		});

		TextView name = (TextView) findViewById(R.id.weaponDetailName);
		TextView levelType = (TextView) findViewById(R.id.weaponDetailType);
		// name.setText(Assets.pilot.inventory.weaponsInventory.get(position).name);
		name.setText(w.toString());
		name.setTextColor(w.getQualityColor());

		TextView qual = (TextView) findViewById(R.id.weaponDetailQuality);
		qual.setText(w.getQualityName());
		qual.setTextColor(w.getQualityColor());

		String content = "";
		content += "Level ";
		content += w.level;
		content += " ";
		content += w.getTypeName();
		levelType.setText(content);

		content = "";
		TextView prof = (TextView) findViewById(R.id.weaponDetailProficiency);
		content += "Proficiency level: ";
		if (w.upgradeLevel == WeaponBlueprint.MAX_LEVELS) {
			content += "MAX";
		} else {
			content += w.upgradeLevel;
			content += "/" + WeaponBlueprint.MAX_LEVELS;
		}
		prof.setText(content);

		TextView rating = (TextView) findViewById(R.id.weaponDetailRating);
		content = "";
		content += "Power rating: ";
		Weapon weap = w.getWeapon();
		w.setPower(weap);
		content += Integer.toString((int) weap.power);
		rating.setText(content);

		TextView value = (TextView) findViewById(R.id.weaponDetailValue);
		content = "";
		content += "Value: $";
		content += w.getValue();
		value.setText(content);

		equipButton = (Button) findViewById(R.id.equipWeaponButton);
		if (Assets.pilot.shipBlueprint.weapons[mountpoint] == w) {
			equipButton.setEnabled(false);
			equipButton.setText("Weapon Equipped in Slot " + (mountpoint + 1));
		} else {
			equipButton.setText("Equip in Slot " + (mountpoint + 1));
		}
	}

	private void setupListeners() {
		equipButton = (Button) findViewById(R.id.equipWeaponButton);
		equipButton.setOnClickListener(this);
		customizeButton = (Button) findViewById(R.id.customizeWeaponButton);
		customizeButton.setOnClickListener(this);
		sellButton = (Button) findViewById(R.id.sellWeaponButton);
		sellButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		int mountpoint = getIntent().getExtras().getInt("mountPoint");
		int position = getIntent().getExtras().getInt("position");
		switch (arg0.getId()) {
		case R.id.equipWeaponButton:
			// WeaponBlueprint wb = Assets.pilot.inventory.weaponsInventory
			// .get(position);
			// Assets.pilot.shipBlueprint.weapons[mountpoint] = wb;
			Assets.pilot.shipBlueprint.weapons[mountpoint] = w;
			Toast.makeText(this, "Weapon equipped", 0).show();
			finish();
			break;
		case R.id.customizeWeaponButton:
			break;
		case R.id.sellWeaponButton:
			break;
		}
	}
}
