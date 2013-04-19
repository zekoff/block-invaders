package net.zekoff.blockinvaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.weapon.Weapon;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Extends ListActivity to display a list of all weapons that the player
 * currently has in inventory that can be equipped.
 * 
 * @author Zekoff
 * 
 */
public class WeaponEquipList extends ListActivity {

	WeaponAdapter adapter = null;

	class WeaponAdapter extends ArrayAdapter<WeaponBlueprint> {

		public WeaponAdapter(Context context, int textViewResourceId,
				List<WeaponBlueprint> objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();

			View row = convertView;
			if (row == null)
				row = inflater.inflate(R.layout.weapon_row, parent, false);

			TextView name = (TextView) row.findViewById(R.id.weaponRowName);
			name.setText(getItem(position).toString());
			name.setTextColor(getItem(position).getQualityColor());

			String content = "";
			TextView levelType = (TextView) row
					.findViewById(R.id.weaponRowLevelType);
			content += "Level ";
			content += getItem(position).level;
			content += " ";
			content += getItem(position).getTypeName();
			levelType.setText(content);

			content = "";
			TextView prof = (TextView) row
					.findViewById(R.id.weaponRowProficiency);
			content += "Proficiency level: ";
			if (getItem(position).upgradeLevel == WeaponBlueprint.MAX_LEVELS) {
				content += "MAX";
			} else {
				content += getItem(position).upgradeLevel;
				content += "/" + WeaponBlueprint.MAX_LEVELS;
			}
			prof.setText(content);

			TextView rating = (TextView) row.findViewById(R.id.weaponRowRating);
			if (getItem(position).toString() != "None") {
				content = "";
				content += "Power rating: ";
				// Weapon w = getItem(position).getWeapon();
				// getItem(position).setPower(w);
				content += getItem(position).getRating();
				rating.setText(content);
			}

			TextView value = (TextView) row.findViewById(R.id.weaponRowValue);
			value.setVisibility(View.GONE);

			if (getItem(position).toString() == "None") {
				name.setText("None");
				levelType.setText("");
				prof.setText("");
				rating.setText("");
			}

			return row;
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getListView().setDividerHeight(4);

		ArrayList<WeaponBlueprint> inv = new ArrayList<WeaponBlueprint>();

		// add all weapons in inventory to list
		for (WeaponBlueprint w : Assets.pilot.inventory.weaponsInventory) {
			inv.add(w);
		}

		// remove weapons already equipped in another slot
		int thisMountPoint = getIntent().getExtras().getInt("mountPoint");
		Iterator<WeaponBlueprint> iterator = inv.iterator();
		while (iterator.hasNext()) {
			WeaponBlueprint w = iterator.next();
			for (int i = 0; i < 5; i++) {
				if (i == thisMountPoint)
					continue;
				if (Assets.pilot.shipBlueprint.weapons[i] != null) {
					if (Assets.pilot.shipBlueprint.weapons[i].equals(w))
						iterator.remove();
				}
			}
		}

		adapter = new WeaponAdapter(this, android.R.layout.simple_list_item_1,
				inv);
		adapter.add(new WeaponBlueprint(0) {
			private static final long serialVersionUID = -1383101497486966538L;

			@Override
			public String toString() {
				return "None";
			}

			@Override
			public Weapon getWeapon() {
				return null;
			}

			@Override
			public String getTypeName() {
				return null;
			}
		});
		setListAdapter(adapter);
	}

	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);

		if (adapter.getItem(position).toString().compareTo("None") == 0) {
			int mountPoint = getIntent().getExtras().getInt("mountPoint");
			Assets.pilot.shipBlueprint.weapons[mountPoint] = null;
			Toast.makeText(this, "Mount point " + (mountPoint + 1) + " empty.",
					0).show();
			finish();
		} else {
			int hashCode = adapter.getItem(position).hashCode();
			Intent intent = new Intent(this, WeaponDetail.class);
			Bundle extras = getIntent().getExtras();
			extras.putInt("position", position);
			extras.putInt("hashCode", hashCode);
			intent.putExtras(extras);

			startActivity(intent);
			finish();
		}
	}
}
