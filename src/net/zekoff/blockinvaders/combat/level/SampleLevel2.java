package net.zekoff.blockinvaders.combat.level;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.enemy.Greenie;
import net.zekoff.blockinvaders.combat.enemy.SampleBoss;
import net.zekoff.blockinvaders.combat.movement.MoveTo;
import net.zekoff.blockinvaders.combat.movement.MovementOrders;
import net.zekoff.blockinvaders.combat.movement.Parabola;

/**
 * Test of different movement patterns for each wave of enemies, ending with a
 * single boss.
 * 
 * @author Zekoff
 * 
 */
public class SampleLevel2 extends Level {
	int waveNumber = 1;
	float counter = 0;
	float timer = 0;
	boolean newWaveFlag = true;

	@Override
	public void update(float deltaTime) {
		switch (waveNumber) {
		case 1:
			if (newWaveFlag) {
				Enemy enemy;
				for (int i = 0; i < 4; i++) {
					enemy = new Greenie();
					enemy.x = -30;
					enemy.y = 160 + ((i + 1) * 30);
					enemy.movementOrders.add(new MoveTo(80, (i + 1) * 30));
					enemy.movementOrders.add(new MovementOrders() {
						public void move(Enemy self, float deltaTime) {
							self.xVel = 160;
							self.yVel = 10;
							isFinished = true;
						}
					});
					// enemy.movementOrders.add(new MovementOrders() {
					// public void move(Enemy self, float deltaTime) {
					// self.x += self.xVel * deltaTime;
					// self.y += self.yVel * deltaTime;
					// self.xVel -= 50 * deltaTime;
					// }
					// });
					enemy.movementOrders.add(new Parabola(160, 10, -50, 0));
					Assets.em.enemies.add(enemy);
					newWaveFlag = false;
				}
			}
			if (!newWaveFlag && Assets.em.enemies.size() == 0) {
				waveNumber++;
				newWaveFlag = true;
			}
			break;
		case 2:
			if (newWaveFlag) {
				newWaveFlag = false;
				Enemy enemy;
				for (int i = 0; i < 3; i++) {
					enemy = new Greenie(2);
					enemy.x = 160 + (i * 80);
					enemy.y = -30;
					enemy.xVel = -20;
					enemy.yVel = 160;
					enemy.movementOrders.add(new MovementOrders() {
						public void move(Enemy self, float deltaTime) {
							self.x += self.xVel * deltaTime;
							self.y += self.yVel * deltaTime;
							self.yVel -= 40 * deltaTime;
						}
					});
					Assets.em.enemies.add(enemy);
				}
			}
			if (!newWaveFlag && Assets.em.enemies.size() == 0) {
				newWaveFlag = true;
				waveNumber++;
			}
			break;
		case 3:
			timer += deltaTime;
			if (newWaveFlag) {
				newWaveFlag = false;
				counter = 0;
				timer = 0;
				Enemy e = new Greenie();
				e.x = -30;
				e.y = 80;
				e.movementOrders.add(new MoveTo(160, -80));
				Assets.em.enemies.add(e);
			} else if (counter == 0 && timer > 1.5) { // offender
				counter++;
				Enemy e = new Greenie();
				e.x = 350;
				e.y = 230;
				e.movementOrders.add(new MoveTo(-160, 80));
				Assets.em.enemies.add(e);
			} else if (counter == 1 && timer > 3) { // offender
				counter++;
				Enemy e = new Greenie();
				e.x = -30;
				e.y = 100;
				e.movementOrders.add(new MoveTo(400, -10));
				Assets.em.enemies.add(e);
			} else if (counter == 2 && timer > 4.5) {
				counter++;
				Enemy e = new Greenie();
				e.x = 350;
				e.y = 40;
				e.movementOrders.add(new MoveTo(-60, 300));
				Assets.em.enemies.add(e);
			} else if (Assets.em.enemies.size() == 0) {
				newWaveFlag = true;
				waveNumber++;
			}
			break;
		case 4:
			if (newWaveFlag) {
				newWaveFlag = false;
				Enemy enemy;
				for (int i = 0; i < 3; i++) {
					enemy = new Greenie() {
						public void defaultMovement(float deltaTime) {
							xVel = 0;
						}
					};
					enemy.x = 160 + 80 * i;
					enemy.y = 480;
					enemy.movementOrders.add(new MoveTo(40 + 80 * i, 80));
					Assets.em.enemies.add(enemy);
				}
				for (int i = 0; i < 3; i++) {
					enemy = new Greenie() {
						public void defaultMovement(float deltaTime) {
							xVel = 0;
						}
					};
					enemy.x = 0 + 80 * i;
					enemy.y = 520;
					enemy.movementOrders.add(new MoveTo(100 + 80 * i, 160));
					Assets.em.enemies.add(enemy);
				}
			} else if (Assets.em.enemies.size() == 0) {
				waveNumber++;
				newWaveFlag = true;
			}
			break;
		case 5:
			timer += deltaTime;
			if (newWaveFlag) {
				newWaveFlag = false;
				timer = 0;
				counter = 0;
				Enemy enemy;
				for (int i = 0; i < 3; i++) {
					enemy = new Greenie();
					enemy.y = -40;
					enemy.x = 0 + (100 * i);
					enemy.xVel = 20;
					enemy.yVel = 90;
					enemy.movementOrders.add(new MovementOrders() {
						public void move(Enemy self, float deltaTime) {
							self.x += self.xVel * deltaTime;
							self.y += self.yVel * deltaTime;
							;
							self.yVel -= 30 * deltaTime;
						}
					});
					Assets.em.enemies.add(enemy);
				}
				enemy = new Greenie();
				enemy.y = 100;
				enemy.x = -40;
				enemy.xVel = 90;
				enemy.yVel = 15;
				enemy.movementOrders.add(new MovementOrders() {
					public void move(Enemy self, float deltaTime) {
						self.x += self.xVel * deltaTime;
						self.y += self.yVel * deltaTime;
						;
						self.xVel -= 30 * deltaTime;
					}
				});
				Assets.em.enemies.add(enemy);
				enemy = new Greenie();
				enemy.y = 100;
				enemy.x = 360;
				enemy.xVel = -90;
				enemy.yVel = 15;
				enemy.movementOrders.add(new MovementOrders() {
					public void move(Enemy self, float deltaTime) {
						self.x += self.xVel * deltaTime;
						self.y += self.yVel * deltaTime;
						self.xVel -= -30 * deltaTime;
					}
				});
				Assets.em.enemies.add(enemy);
				enemy = new Greenie();
				enemy.y = 480;
				enemy.x = 160;
				enemy.yVel = -180;
				enemy.xVel = 0;
				enemy.movementOrders.add(new MovementOrders() {
					public void move(Enemy self, float deltaTime) {
						self.x += self.xVel * deltaTime;
						self.y += self.yVel * deltaTime;
						self.yVel += 60 * deltaTime;
					}
				});
				Assets.em.enemies.add(enemy);
				// enemy = new Greenie();
				// enemy.y = 210;
				// enemy.x = -20;
				// enemy.yVel = 0;
				// enemy.xVel = 60;
				// enemy.movementOrders.add(new MovementOrders() {
				// public void move(Enemy self, float deltaTime) {
				// self.x += self.xVel * deltaTime;
				// self.y += self.yVel * deltaTime;
				// }
				// });
				// Assets.em.enemies.add(enemy);
				// } else if (counter == 0 && timer > 2) {

			} else if (Assets.em.enemies.size() == 0) {
				newWaveFlag = true;
				waveNumber++;
			}
			break;
		case 6:
			// boss wave
			if (newWaveFlag) {
				newWaveFlag = false;
				Enemy boss = new SampleBoss();
				boss.x = 160;
				boss.y = -40;
				Assets.em.enemies.add(boss);
			}
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}
