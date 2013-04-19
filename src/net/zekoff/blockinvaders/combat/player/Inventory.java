package net.zekoff.blockinvaders.combat.player;

import java.io.Serializable;
import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.defense.ArmorBlueprint;
import net.zekoff.blockinvaders.combat.defense.ShieldBlueprint;
import net.zekoff.blockinvaders.combat.loot.JunkLoot;
import net.zekoff.blockinvaders.combat.loot.Loot;
import net.zekoff.blockinvaders.combat.loot.Loot.LootType;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;

/**
 * A serializable collection of ArrayLists that contain the player's inventory.
 * 
 * @author Zekoff
 * 
 */
public class Inventory implements Serializable {
	private static final long serialVersionUID = -3590090157831429340L;

	public ArrayList<WeaponBlueprint> weaponsInventory = new ArrayList<WeaponBlueprint>();
	public ArrayList<ArmorBlueprint> armorInventory = new ArrayList<ArmorBlueprint>();
	public ArrayList<ShieldBlueprint> shieldInventory = new ArrayList<ShieldBlueprint>();
	public ArrayList<JunkLoot> junkInventory = new ArrayList<JunkLoot>();

	public ArrayList<Loot> temporaryInventory = new ArrayList<Loot>();

	/**
	 * Splits up the loot in temporaryInventory into the correct lists, making
	 * them permanent.
	 */
	public void commitLoot() {
		for (Loot loot : temporaryInventory) {
			if (loot.getType() == LootType.junk)
				junkInventory.add((JunkLoot) loot);
			else if (loot.getType() == LootType.weapon)
				weaponsInventory.add((WeaponBlueprint) loot);
		}
		temporaryInventory.clear();
	}

	public void clearTemporaryLoot() {
		temporaryInventory.clear();
	}

	public void addLoot(Loot loot) {
		if (temporaryInventory == null)
			temporaryInventory = new ArrayList<Loot>();
		temporaryInventory.add(loot);
	}
}
