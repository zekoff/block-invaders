package net.zekoff.blockinvaders;

import java.util.ArrayList;
import java.util.List;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.ship.ShipBlueprint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MountPoints extends Activity implements Button.OnClickListener {
	Button b1;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	List<Button> buttonList = new ArrayList<Button>();
	List<Button> unequipButtonList = new ArrayList<Button>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mount_points);

		ArrayList<LinearLayout> layouts = new ArrayList<LinearLayout>();
		layouts.add((LinearLayout) findViewById(R.id.mountPointLayout1));
		layouts.add((LinearLayout) findViewById(R.id.mountPointLayout2));
		layouts.add((LinearLayout) findViewById(R.id.mountPointLayout3));
		layouts.add((LinearLayout) findViewById(R.id.mountPointLayout4));
		layouts.add((LinearLayout) findViewById(R.id.mountPointLayout5));

		b1 = (Button) findViewById(R.id.mountPointsButton1);
		b2 = (Button) findViewById(R.id.mountPointsButton2);
		b3 = (Button) findViewById(R.id.mountPointsButton3);
		b4 = (Button) findViewById(R.id.mountPointsButton4);
		b5 = (Button) findViewById(R.id.mountPointsButton5);

		buttonList.add(b1);
		buttonList.add(b2);
		buttonList.add(b3);
		buttonList.add(b4);
		buttonList.add(b5);

		Button up1 = (Button) findViewById(R.id.mountPointsUnequip1);
		Button up2 = (Button) findViewById(R.id.mountPointsUnequip2);
		Button up3 = (Button) findViewById(R.id.mountPointsUnequip3);
		Button up4 = (Button) findViewById(R.id.mountPointsUnequip4);
		Button up5 = (Button) findViewById(R.id.mountPointsUnequip5);

		unequipButtonList.add(up1);
		unequipButtonList.add(up2);
		unequipButtonList.add(up3);
		unequipButtonList.add(up4);
		unequipButtonList.add(up5);

		for (LinearLayout l : layouts) {
			l.setVisibility(View.GONE);
		}
		for (Button b : buttonList) {
			b.setEnabled(false);
		}
		for (Button b : unequipButtonList) {
			b.setTextColor(Color.RED);
		}

		ShipBlueprint sb = Assets.pilot.shipBlueprint;
		for (int i = 0; i < sb.getNumMountPoints(); i++) {
			layouts.get(i).setVisibility(View.VISIBLE);
			buttonList.get(i).setEnabled(true);
		}

		setupListeners();
		setButtonText();
	}

	@Override
	public void onResume() {
		super.onResume();
		setButtonText();
	}

	private void setButtonText() {
		for (int i = 0; i < 5; i++) {
			if (Assets.pilot.shipBlueprint.weapons[i] != null) {
				// if the weapon is equipped on this mount point...
				String weaponName = Assets.pilot.shipBlueprint.weapons[i]
						.toString();
				buttonList.get(i).setText(weaponName);
				unequipButtonList.get(i).setVisibility(Button.VISIBLE);
			} else {
				buttonList.get(i).setText("None");
				unequipButtonList.get(i).setVisibility(Button.GONE);
			}
		}
	}

	private void setupListeners() {
		for (Button b : buttonList) {
			b.setOnClickListener(this);
		}
		for (Button b : unequipButtonList) {
			b.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mountPointsButton1:
			startWeaponDetail(0);
			break;
		case R.id.mountPointsButton2:
			startWeaponDetail(1);
			break;
		case R.id.mountPointsButton3:
			startWeaponDetail(2);
			break;
		case R.id.mountPointsButton4:
			startWeaponDetail(3);
			break;
		case R.id.mountPointsButton5:
			startWeaponDetail(4);
			break;
		case R.id.mountPointsUnequip1:
			Assets.pilot.shipBlueprint.weapons[0] = null;
			setButtonText();
			break;
		case R.id.mountPointsUnequip2:
			Assets.pilot.shipBlueprint.weapons[1] = null;
			setButtonText();
			break;
		case R.id.mountPointsUnequip3:
			Assets.pilot.shipBlueprint.weapons[2] = null;
			setButtonText();
			break;
		case R.id.mountPointsUnequip4:
			Assets.pilot.shipBlueprint.weapons[3] = null;
			setButtonText();
			break;
		case R.id.mountPointsUnequip5:
			Assets.pilot.shipBlueprint.weapons[4] = null;
			setButtonText();
			break;
		}
	}

	private void startWeaponDetail(int mountpoint) {
		Bundle extras = new Bundle();
		extras.putInt("mountPoint", mountpoint);
		if (Assets.pilot.shipBlueprint.weapons[mountpoint] != null) {
			extras.putInt("hashCode",
					Assets.pilot.shipBlueprint.weapons[mountpoint].hashCode());
			Intent intent = new Intent(this, WeaponDetail.class);
			intent.putExtras(extras);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, WeaponEquipList.class);
			intent.putExtras(extras);
			startActivity(intent);
		}
	}

}
