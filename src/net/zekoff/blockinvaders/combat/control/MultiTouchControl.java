package net.zekoff.blockinvaders.combat.control;

import net.zekoff.androidgames.framework.Input.TouchEvent;
import net.zekoff.blockinvaders.combat.Assets;

/**
 * Expands single-touch controls by keeping track of which finger is "on" which
 * component of the display. Specifically, keeps track of which fingers are
 * pressing the left and right keys, and allows those fingers to be moved around
 * if necessary to trigger abilities, accidental sliding, etc.
 * 
 * @author Zekoff
 * 
 */
public class MultiTouchControl extends SingleTouchControl {
	int leftButtonPointer;
	int rightButtonPointer;

	@Override
	public void update(float deltaTime) {
		touches = Assets.game.getInput().getTouchEvents();
		if (inputDisabled) {
			left = false;
			right = false;
			return;
		}
		for (int i = 0; i < touches.size(); i++) {
			te = touches.get(i);
			if (te.type == TouchEvent.TOUCH_DOWN
					|| te.type == TouchEvent.TOUCH_DRAGGED) {
				if (te.y >= 360) {
					if (te.x <= 80) {
						leftButtonPointer = te.pointer;
						left = true;
					} else if (te.x >= 240) {
						rightButtonPointer = te.pointer;
						right = true;
					} else if (te.x > 80 && te.x <= 160) {
						abilityTrigger(1);
					} else {
						abilityTrigger(2);
					}
				}
			}
			if (te.type == TouchEvent.TOUCH_UP) {
				if (te.pointer == leftButtonPointer)
					left = false;
				if (te.pointer == rightButtonPointer)
					right = false;
			}
		}
		if (left && right) {
			Assets.playerShip.noMovement(deltaTime);
		} else if (left) {
			Assets.playerShip.moveLeft(deltaTime);
		} else if (right) {
			Assets.playerShip.moveRight(deltaTime);
		} else
			Assets.playerShip.noMovement(deltaTime);
	}

}
