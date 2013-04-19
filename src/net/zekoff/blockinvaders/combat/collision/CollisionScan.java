package net.zekoff.blockinvaders.combat.collision;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.entity.Entity;

/**
 * An abstracted collision-testing layer that can be replaced if better
 * collision detection alogorithms are devised.
 * <p>
 * This class is responsible for implementing static methods that take all
 * bullets, check them against any entity they can collide with, return whether
 * or not a collision has occurred, then call the proper update method in each
 * entity.
 * <p>
 * The real collision-testing work is done in the entities themselves, which
 * primarily use BoundingShape.testCollision().
 * 
 * @author Zekoff
 * 
 */
public class CollisionScan {

	public static void scanPlayerBullets() {
		// check against all enemy entities
		ArrayList<Bullet> b = Assets.bm.playerBullets;
		ArrayList<Enemy> e = Assets.em.enemies;
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < e.size(); j++) {
				boolean collided = e.get(j).testCollision(b.get(i));
				if (collided) {
					b.get(i).collidedWith(e.get(j));
					e.get(j).collidedWith(b.get(i));
				}
			}
		}
	}

	public static void scanEnemyBullets() {
		// check against all player entities
		ArrayList<Bullet> b = Assets.bm.enemyBullets;
		ArrayList<Entity> e = Assets.em.playerEntities;
		for (int i = 0; i < b.size(); i++) {
			for (int j = 0; j < e.size(); j++) {
				boolean collided = e.get(j).testCollision(b.get(i));
				if (collided) {
					b.get(i).collidedWith(e.get(j));
					e.get(j).collidedWith(b.get(i));
				}
			}
		}
		// check against player ship
		for (int i = 0; i < b.size(); i++) {
			boolean collided = Assets.playerShip.testCollision(b.get(i));
			if (collided) {
				b.get(i).collidedWith(Assets.playerShip);
				Assets.playerShip.collidedWith(b.get(i));
			}
		}

	}

	public static void scanPickups() {
		for (int i = 0; i < Assets.em.pickups.size(); i++) {
			if (Assets.em.pickups.get(i).testCollision(Assets.playerShip)) {
				Assets.em.pickups.get(i).collidesWith(Assets.playerShip);
			}
		}
	}

	public static void fullScan() {
		scanPlayerBullets();
		scanEnemyBullets();
		scanPickups();
	}

}
