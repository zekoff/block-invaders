package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Displays list of mount points on the ship and buttons for reassigning the
 * weapons in those slots.
 * 
 * @author Zekoff
 * 
 */
public class LoadoutMain extends Activity implements
		ListView.OnItemClickListener {
	ListView weaponMountPointsList;
	String[] mountPoints = { "Mount point 1", "Mount point 2", "Mount point 3" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadout_main);

		setupListeners();
	}

	private void setupListeners() {
		weaponMountPointsList = (ListView) findViewById(R.id.weaponMountPointsList);
		weaponMountPointsList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mountPoints));
		weaponMountPointsList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		startActivity(new Intent(this, WeaponEquipList.class));
	}
}
