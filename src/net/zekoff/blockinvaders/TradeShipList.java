package net.zekoff.blockinvaders;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TradeShipList extends ListActivity {
	String[] ships = { "Tranquility", "Epoch Hawk", "Sun Destroyer",
			"Manta Ray II" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ships));
	}
	
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
//		String shipSelected = ships[position];

		// normally, show weapon detail instead of toasting...
		startActivity(new Intent(this, TradeShipDetail.class));
	}

}
