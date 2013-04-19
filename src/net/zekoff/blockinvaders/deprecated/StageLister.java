package net.zekoff.blockinvaders.deprecated;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.level.Level;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * An Activity to display any Levels that can be loaded for testing. The list is
 * static and the source must be manually updated any time levels are added or
 * removed for testing.
 * 
 * @author Zekoff
 * 
 */
public class StageLister extends ListActivity {
	String[] stages = { "SampleLevel", "SampleLevel2", "SampleLevel3",
			"SampleLevel4", "SampleLevel5", "AreaEffectTest" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, stages));
	}

	@Override
	protected void onListItemClick(ListView list, View view, int position,
			long id) {
		super.onListItemClick(list, view, position, id);
		String stageName = stages[position];
		try {
			Class<?> clazz = Class
					.forName("net.zekoff.blockinvaders.combat.level."
							+ stageName);
			// reinit Assets with this stage
			Assets.em.enemies = new ArrayList<Enemy>();
			Assets.bm.reinit();
			Assets.level = (Level) clazz.newInstance();
			finish();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
