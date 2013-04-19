package net.zekoff.blockinvaders.combat.movement;

import net.zekoff.blockinvaders.combat.enemy.Enemy;

public class LeftRight extends MovementOrders {
	private int movementDirection = 1;
	public static final int LEFT_BOUND = 40;
	public static final int RIGHT_BOUND = 280;
	private float lb = 0;
	private float rb = 320;
	MovementCondition mc = null;

	public LeftRight() {
		lb = LEFT_BOUND;
		rb = RIGHT_BOUND;
	}

	public LeftRight(MovementCondition mc) {
		this();
		this.mc = mc;
	}

	public LeftRight(float leftBound, float rightBound) {
		lb = leftBound;
		rb = rightBound;
	}

	public LeftRight(float leftBound, float rightBound, MovementCondition mc) {
		this(leftBound, rightBound);
		this.mc = mc;
	}

	@Override
	public void move(Enemy self, float deltaTime) {
		if (self.x < lb)
			movementDirection = 1;
		if (self.x > rb)
			movementDirection = -1;
		if (movementDirection == 1 && self.xVel < self.speed)
			self.xVel += self.acceleration * deltaTime;
		if (self.xVel > -self.speed && movementDirection == -1)
			self.xVel -= self.acceleration * deltaTime;
		self.x += self.xVel * deltaTime;
		self.yVel = 0;
	}
}
