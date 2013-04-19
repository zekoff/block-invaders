package net.zekoff.blockinvaders;

import java.util.ArrayList;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.ability.Ability;
import net.zekoff.blockinvaders.combat.player.Pilot;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Extends ListActivity to show all abilities that the player can equip.
 * 
 * @author Zekoff
 * 
 */
public class AbilityList extends ListActivity {
	String[] abilities = { "Barrel-roll", "Targeted Laser", "Shot absorb",
			"Shield Charger", "Chain Laser", "Missile Battery",
			"Shield Inverter", "Heal Wingmen", "Order Wingmen",
			"Deploy Barrier", "Grenade" };

	ArrayList<Ability> abilitiesList = new ArrayList<Ability>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		for (String an : Assets.pilot.abilities) {
			try {
				Ability a = (Ability) Class.forName(Pilot.abilityPackage + an)
						.newInstance();
				abilitiesList.add(a);
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
		}

		ArrayAdapter<Ability> adapter = new ArrayAdapter<Ability>(this,
				android.R.layout.simple_list_item_1, abilitiesList);
		adapter.add(new Ability() {
			public String toString() {
				return "None";
			}

			@Override
			public void update(float deltaTime) {
				// TODO Auto-generated method stub

			}

			@Override
			public void draw(Graphics g) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean trigger() {
				// TODO Auto-generated method stub
				return false;
			}
		});
		setListAdapter(adapter);
	}

	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		if (abilitiesList.get(position).toString().compareTo("None") == 0) {
			int slotNumber = getIntent().getExtras().getInt("abilitySlot");
			Toast.makeText(this, "Ability slot " + slotNumber + " unequipped.",
					0).show();
			if (slotNumber == 1)
				Assets.pilot.ability1 = null;
			else
				Assets.pilot.ability2 = null;
			finish();
		} else {
			String abilitySelected = Assets.pilot.abilities.get(position);

			Intent toDisplay = new Intent(this, AbilityDetail.class);
			Bundle extras = getIntent().getExtras();
			extras.putString("abilityName", abilitySelected);
			toDisplay.putExtras(extras);
			startActivity(toDisplay);
			finish();
		}
	}
}
