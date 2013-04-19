package net.zekoff.blockinvaders;

import java.util.List;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.player.Pilot;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Extends ListActivity to display all weapons in the shop inventory.
 * 
 * @author Zekoff
 * 
 */
public class ShopList extends Activity {

	class ShopWeaponAdapter extends ArrayAdapter<WeaponBlueprint> {

		public ShopWeaponAdapter(Context context, int textViewResourceId,
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

			TextView prof = (TextView) row
					.findViewById(R.id.weaponRowProficiency);
			prof.setVisibility(View.GONE);

			TextView rating = (TextView) row.findViewById(R.id.weaponRowRating);
			if (getItem(position).toString() != "None") {
				content = "";
				content += "Power rating: ";
				// Weapon w = getItem(position).getWeapon();
				// getItem(position).setPower(w);
				content += getItem(position).getRating();
				rating.setText(content);
			}

			// TODO add an actual value line to weapon row
			TextView value = (TextView) row.findViewById(R.id.weaponRowValue);
			value.setText("Price: $" + getItem(position).getValue());

			if (getItem(position).toString() == "None") {
				name.setText("None");
				levelType.setText("");
				prof.setText("");
				rating.setText("");
			}

			return row;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_inventory);
		ListView list = (ListView) findViewById(R.id.shopInventoryList);
		list.setAdapter(new ShopWeaponAdapter(this,
				android.R.layout.simple_list_item_1,
				Assets.pilot.shop.weaponInventory));

		if (Assets.pilot.shop.timeToRestock()) {
			Toast.makeText(this, "Shop restocked.", 0).show();
			Assets.pilot.shop.restockInventory();
		}

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int cost = Assets.pilot.shop.weaponInventory.get(position)
						.getValue();
				if (Assets.pilot.money >= cost) {
					// purchase
					Assets.pilot.money -= cost;
					WeaponBlueprint w = Assets.pilot.shop.weaponInventory
							.get(position);
					Assets.pilot.inventory.weaponsInventory.add(w);
					Assets.pilot.shop.weaponInventory.remove(w);
					Toast.makeText(ShopList.this, "Weapon purchased.", 0)
							.show();
					ListView list = (ListView) findViewById(R.id.shopInventoryList);
					list.setAdapter(new ShopWeaponAdapter(ShopList.this,
							android.R.layout.simple_list_item_1,
							Assets.pilot.shop.weaponInventory));

				} else {
					Toast.makeText(ShopList.this, "Not enough money.", 0)
							.show();
				}
				Pilot.savePilot();
			}
		});
	}
}
