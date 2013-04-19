package net.zekoff.blockinvaders.combat.control;

import java.util.List;

import net.zekoff.androidgames.framework.Graphics;
import net.zekoff.androidgames.framework.Input.TouchEvent;
import android.graphics.Paint;

/**
 * Provides a constructor and several necessary variables for child classes to
 * use in their full implementation of control schemes.
 * <p>
 * A control object cannot be set until after the player's ship object has been
 * loaded into the Assets class, else this class will throw a
 * NullPointerException when it tries to access the player ship object.
 * 
 * @author Zekoff
 * 
 */
public abstract class PlayerControl {
	public boolean inputDisabled = false;
	public boolean left;
	public boolean right;
	public List<TouchEvent> touches;
	TouchEvent te;
	Paint paint = new Paint();

	public abstract void update(float deltaTime);

	public abstract void draw(Graphics g);
}
