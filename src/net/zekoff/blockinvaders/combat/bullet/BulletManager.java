package net.zekoff.blockinvaders.combat.bullet;

import java.util.ArrayList;
import java.util.Iterator;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.impl.Pool;
import net.zekoff.androidgames.framework.impl.Pool.PoolObjectFactory;

/**
 * Class to manage all bullets in the game. Provides access to list of both
 * enemy and player bullets, and chains calls to update() and draw() to all
 * bullets in both lists. Provides "garbage collection" to all bullets in its
 * list by scanning their isExpired fields for true during the update phase.
 * <p>
 * Pools for separate bullet types were becoming exceptionally difficult to
 * manage, so for now, bullet pools are set up but not used. The code is there
 * to expand them if necessary, perhaps during refactoring.
 * 
 * @author Zekoff
 * 
 */
public class BulletManager {
	public ArrayList<Bullet> playerBullets = new ArrayList<Bullet>();
	public ArrayList<Bullet> enemyBullets = new ArrayList<Bullet>();
	Iterator<Bullet> iterator;
	Bullet toUpdate;
	public Pool<CircleBullet> circlePool;
	public Pool<RectBullet> rectPool;

	public BulletManager() {
		// set up Bullet pools
		PoolObjectFactory<CircleBullet> circleBulletFactory = new PoolObjectFactory<CircleBullet>() {
			public CircleBullet createObject() {
				return new CircleBullet();
			}
		};
		circlePool = new Pool<CircleBullet>(circleBulletFactory, 100);

		PoolObjectFactory<RectBullet> rectBulletFactory = new PoolObjectFactory<RectBullet>() {
			public RectBullet createObject() {
				return new RectBullet();
			}
		};
		rectPool = new Pool<RectBullet>(rectBulletFactory, 100);
	}

	public void draw(Graphics g) {
		// Draw all player bullets
		for (int i = 0; i < playerBullets.size(); i++) {
			playerBullets.get(i).draw(g);
		}
		// Draw all enemy bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).draw(g);
		}
	}

	public void update(float deltaTime) {
		// chain updates to player bullets
		for (int i = 0; i < playerBullets.size(); i++) {
			playerBullets.get(i).update(deltaTime);
		}
		// chain updates to enemy bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).update(deltaTime);
		}

		// "garbage collect"
		iterator = playerBullets.iterator();
		while (iterator.hasNext()) {
			toUpdate = iterator.next();
			if (toUpdate.isExpired) {
				iterator.remove();
			}
		}
		iterator = enemyBullets.iterator();
		while (iterator.hasNext()) {
			while (iterator.hasNext()) {
				toUpdate = iterator.next();
				if (toUpdate.isExpired) {
					iterator.remove();
				}
			}
		}
	}

	public void reinit() {
		playerBullets = new ArrayList<Bullet>();
		enemyBullets = new ArrayList<Bullet>();
	}
}
