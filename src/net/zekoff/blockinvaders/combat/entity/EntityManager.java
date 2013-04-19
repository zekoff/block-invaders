package net.zekoff.blockinvaders.combat.entity;

import java.util.ArrayList;
import java.util.Iterator;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.loot.Pickup;

/**
 * Class providing maintenance and access to all entities (note the lowercase).
 * Does not necessarily contain all Entities in the game; rather, contains all
 * Entity objects of a given "faction" that should be tested for collision with
 * bullets of the opposing faction.
 * 
 * @author Zekoff
 * 
 */
public class EntityManager {
	public ArrayList<Entity> playerEntities = new ArrayList<Entity>();
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<Entity> neutralEntities = new ArrayList<Entity>();
	public ArrayList<Pickup> pickups = new ArrayList<Pickup>();
	private Iterator<Entity> iterator;
	private Iterator<Enemy> enemyIterator;
	private Iterator<Pickup> pickupIterator;

	public void update(float deltaTime) {
		// chain update() calls to member entities
		for (int i = 0; i < playerEntities.size(); i++) {
			playerEntities.get(i).update(deltaTime);
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update(deltaTime);
		}
		for (int i = 0; i < neutralEntities.size(); i++) {
			neutralEntities.get(i).update(deltaTime);
		}
		for (int i = 0; i < pickups.size(); i++) {
			pickups.get(i).update(deltaTime);
		}

		// do "garbage collection"
		iterator = playerEntities.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().isExpired())
				iterator.remove();
		}
		enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) {
			if (enemyIterator.next().isExpired())
				enemyIterator.remove();

		}
		iterator = neutralEntities.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().isExpired())
				iterator.remove();
		}
		pickupIterator = pickups.iterator();
		while (pickupIterator.hasNext()) {
			if (pickupIterator.next().isExpired)
				pickupIterator.remove();
		}
	}

	public void draw(Graphics g) {
		// chain draw() calls to member entities
		for (int i = 0; i < playerEntities.size(); i++) {
			playerEntities.get(i).draw(g);
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		for (int i = 0; i < neutralEntities.size(); i++) {
			neutralEntities.get(i).draw(g);
		}
		for (int i = 0; i < pickups.size(); i++) {
			pickups.get(i).draw(g);
		}
	}

	public void reinit() {
		playerEntities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		neutralEntities = new ArrayList<Entity>();
		pickups = new ArrayList<Pickup>();
	}
}
