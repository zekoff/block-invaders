package net.zekoff.blockinvaders.combat.enemy;

import java.util.ArrayList;
import java.util.Iterator;

import net.zekoff.blockinvaders.combat.Assets;

/**
 * Secondary collection of Enemy objects related by a common formation. Can be
 * used to cause enemies to all move in the same way or at the same time. Access
 * is granted by simply iterating over the members list of this object.
 * 
 * @author Zekoff
 * 
 */
public class EnemyFormation {
	public ArrayList<Enemy> members = new ArrayList<Enemy>();
	Iterator<Enemy> iterator;

	/**
	 * Check to make sure that each member of this formation is still present in
	 * the master enemy list from Assets. If it is not, then the enemy has
	 * expired, and should be removed from this list as well.
	 */
	public void cleanup() {
		iterator = members.iterator();
		while (iterator.hasNext()) {
			if (!Assets.em.enemies.contains(iterator.next()))
				iterator.remove();
		}
	}

	public void add(Enemy e) {
		members.add(e);
	}

	public boolean isEmpty() {
		return members.isEmpty();
	}

}
