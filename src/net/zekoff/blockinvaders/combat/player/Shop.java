package net.zekoff.blockinvaders.combat.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.defense.ArmorBlueprint;
import net.zekoff.blockinvaders.combat.defense.ShieldBlueprint;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import net.zekoff.blockinvaders.utility.LootTable;

public class Shop implements Serializable {

	public long refreshTime = 0;
	public List<WeaponBlueprint> weaponInventory = new ArrayList<WeaponBlueprint>();
	public ArrayList<ShieldBlueprint> shieldInventory = new ArrayList<ShieldBlueprint>();
	public ArrayList<ArmorBlueprint> armorInventory = new ArrayList<ArmorBlueprint>();
	public static final long TIME_TO_RESTOCK = 10; // time to restock in minutes

	public Shop() {
		refreshTime = System.nanoTime();
		refreshTime -= 1000000000L * 60L * 60L; // subtract 60 minutes
	}

	public void restockInventory() {
		refreshTime = System.nanoTime();
		Random r = new Random();

		// clear weapon inventory and restock
		int rand = r.nextInt(4) + 4;
		weaponInventory.clear();
		for (int i = 0; i < rand; i++) {
			weaponInventory.add(LootTable
					.getRandomWeaponBlueprint(Assets.pilot.level));
		}
		Assets.pilot.savePilot();
	}

	public boolean timeToRestock() {
		long currentTime = System.nanoTime();
		Log.d("BI_shop", "Refresh:   " + Long.toString(refreshTime));
		Log.d("BI_shop", "Current:   " + Long.toString(currentTime));
		long timeToRefresh = (refreshTime + (long) (1000000000L * 60L * TIME_TO_RESTOCK));
		Log.d("BI_shop", "Restock @: " + Long.toString(timeToRefresh));
		Log.d("BI_shop", "cur - res: " + (currentTime - timeToRefresh));
		if (currentTime > timeToRefresh)
			return true;
		return false;
	}
}
