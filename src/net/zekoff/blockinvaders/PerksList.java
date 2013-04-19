package net.zekoff.blockinvaders;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.perk.Perk;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Extends ListActivity to show a list of all available perks.
 * 
 * @author Zekoff
 * 
 */
public class PerksList extends ListActivity {
	String perks[] = { "Sharpshooter", "Evasive", "Focused", "Handsome",
			"Itchy Trigger-Finger", "Another perk", "More perks...",
			"Tinkerer", "Well-trained", "Jack of All Trades",
			"Brilliant Scientist", "Nine Lives" };

	ArrayList<Perk> perksList = Assets.pilot.perks;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<Perk>(this,
				android.R.layout.simple_list_item_1, perksList));
	}

	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		Perk perkSelected = perksList.get(position);

		Intent toDisplay = new Intent(this, PerkDetail.class);
		Bundle extras = new Bundle();
		extras.putString("perkName", perkSelected.name);
		toDisplay.putExtras(extras);
		startActivity(toDisplay);
	}
}
