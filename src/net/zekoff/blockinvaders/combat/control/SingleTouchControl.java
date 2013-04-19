package net.zekoff.blockinvaders.combat.control;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Input.TouchEvent;
import net.zekoff.androidgames.framework.Pixmap;
import net.zekoff.blockinvaders.combat.Assets;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

/**
 * This class handles single-touch controls. Notably, sliding a finger off of
 * one of the movement keys will cause it to "lock" into the on position.
 * 
 * @author Zekoff
 * 
 */
public class SingleTouchControl extends PlayerControl {
	Typeface typeface = Typeface.create("sans", Typeface.NORMAL);
	Pixmap greenArrowLeft;
	Pixmap greenArrowRight;
	Pixmap greyArrowLeft;
	Pixmap greyArrowRight;
	RectF oval = new RectF(280, 0, 320, 40);

	public void update(float deltaTime) {
		touches = Assets.game.getInput().getTouchEvents();
		if (inputDisabled) {
			left = false;
			right = false;
			return;
		}
		for (int i = 0; i < touches.size(); i++) {
			te = touches.get(i);
			if (te.type == TouchEvent.TOUCH_DOWN) {
				if (te.y >= 360) {
					if (te.x <= 80)
						left = true;
					else if (te.x >= 240)
						right = true;
					else if (te.x > 80 && te.x <= 160) {
						abilityTrigger(1);
					} else {
						abilityTrigger(2);
					}
				}
			}
			if (te.type == TouchEvent.TOUCH_UP) {
				if (te.y >= 360) {
					if (te.x <= 80)
						left = false;
					else if (te.x >= 240)
						right = false;
				}
			}
		}
		if (left) {
			Assets.playerShip.moveLeft(deltaTime);
		} else if (right) {
			Assets.playerShip.moveRight(deltaTime);
		} else
			Assets.playerShip.noMovement(deltaTime);
	}

	public void draw(Graphics g) {
		paint.setColor(Color.WHITE);
		paint.setTypeface(typeface);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(20);

		if (left)
			drawGreenLeftArrow(g);
		else
			drawGreyLeftArrow(g);
		if (right)
			drawGreenRightArrow(g);
		else
			drawGreyRightArrow(g);
	}

	void drawGreyRightArrow(Graphics g) {
		if (greyArrowRight == null)
			greyArrowRight = g.newPixmap("grey_arrow_right.png", null);
		g.drawPixmap(greyArrowRight, 248, 408);
	}

	void drawGreenRightArrow(Graphics g) {
		if (greenArrowRight == null)
			greenArrowRight = g.newPixmap("green_arrow_right.png", null);
		g.drawPixmap(greenArrowRight, 248, 408);
	}

	void drawGreyLeftArrow(Graphics g) {
		if (greyArrowLeft == null)
			greyArrowLeft = g.newPixmap("grey_arrow_left.png", null);
		g.drawPixmap(greyArrowLeft, 8, 408);
	}

	void drawGreenLeftArrow(Graphics g) {
		if (greenArrowLeft == null)
			greenArrowLeft = g.newPixmap("green_arrow_left.png", null);
		g.drawPixmap(greenArrowLeft, 8, 408);
	}

	void abilityTrigger(int abilityBox) {
		if (Assets.am.ability1 != null && Assets.am.ability2 != null) {
			if (abilityBox == 1)
				Assets.am.ability1.trigger();
			else
				Assets.am.ability2.trigger();
		} else if (Assets.am.ability1 == null && Assets.am.ability2 == null) {
			// do nothing
		} else {
			if (Assets.am.ability1 != null)
				Assets.am.ability1.trigger();
			else
				Assets.am.ability2.trigger();
		}
	}
}
