package net.zekoff.blockinvaders.combat.enemy;

import net.zekoff.androidgames.framework.Audio;
import net.zekoff.androidgames.framework.Sound;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.SoundAssets;
import net.zekoff.blockinvaders.combat.bullet.Bullet;
import net.zekoff.blockinvaders.combat.entity.RectangularComponent;
import net.zekoff.blockinvaders.combat.movement.MovementOrders;
import android.graphics.Color;

/**
 * Sample boss-type enemy that moves slowly downward into the playing field,
 * then begins moving left and right when it has reached a low-enough point.
 * Fires a burst of dumb shots.
 * 
 * @author Zekoff
 * 
 */
public class SampleBoss extends Enemy {
	public float cooldown = 0;
	public static float COOLDOWN_TIME = 5;
	public Sound explode;

	public int rotationDirection = 1;

	public SampleBoss() {
		color = Color.GREEN;
		startColor = Color.GREEN;
		cooldown = COOLDOWN_TIME;
		hp = 500;
		speed = 60;
		rotateByXVel = false;
		components.add(new RectangularComponent(0, 0, 20, 40));
		components.add(new RectangularComponent(-20, -20, 50, 30));
		components.add(new RectangularComponent(20, -20, 50, 30));
		movementOrders.add(new MovementOrders() {
			public void move(Enemy enemy, float deltaTime) {
				enemy.y += 20 * deltaTime;
				if (enemy.y > 100)
					isFinished = true;
			}
		});

		Audio audio = Assets.game.getAudio();
		explode = audio.newSound("big_explosion.ogg");
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		rotation += 5 * rotationDirection * deltaTime;
		if (rotation < -5)
			rotationDirection = 1;
		else if (rotation > 5)
			rotationDirection = -1;
	}

	@Override
	public void destroy() {
		Assets.pm.addChunkEmitter(x, y, 12, startColor, 20);
		isExpired = true;
		SoundAssets.bigExplosion.play(1);
	}

	@Override
	public void defaultMovement(float deltaTime) {
		if (xVel == 0)
			xVel = speed;
		if (x < 40)
			xVel = speed;
		if (x > 280)
			xVel = -speed;
		x += xVel * deltaTime;
	}

	@Override
	public void collidedWith(Bullet b) {
		hp -= b.power;
	}

}
