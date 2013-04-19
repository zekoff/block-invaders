package net.zekoff.blockinvaders.combat;

import java.util.Random;

import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.combat.ability.AbilityManager;
import net.zekoff.blockinvaders.combat.background.Background;
import net.zekoff.blockinvaders.combat.bullet.BulletManager;
import net.zekoff.blockinvaders.combat.control.PlayerControl;
import net.zekoff.blockinvaders.combat.entity.EntityManager;
import net.zekoff.blockinvaders.combat.hud.Hud;
import net.zekoff.blockinvaders.combat.level.Level;
import net.zekoff.blockinvaders.combat.particle.ParticleManager;
import net.zekoff.blockinvaders.combat.player.Pilot;
import net.zekoff.blockinvaders.combat.ship.Ship;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Provides easy (static) access for all classes to the necessary objects of the
 * game, like Managers, plus some utility objects like a Paint and an RNG, etc.
 * Because all member fields are static, the stuff in this class persists even
 * through loading new games, returning to the main menu screens, etc. It is
 * used by any class that needs to modify in-game stuff.
 * <p>
 * Typically, this class gets a workout from the combat engine. A particular
 * Screen running the game should hold little to no information, other than
 * perhaps a control scheme, etc. Everything else should be held here.
 * <p>
 * As the one-off objects can be used by any class at any time, there is no
 * guarantee of state persistence in any of them. Any class that uses one should
 * set all its attributes from scratch each time.
 * 
 * @author Zekoff
 * 
 */
public class Assets {

	// The pilot (player) object, available regardless of game state
	public static Pilot pilot = null;

	// One-off objects for any class to use
	public static Random rand = new Random(System.nanoTime());
	public static Paint paint = new Paint();
	public static Typeface typeface = Typeface.create("monospace",
			Typeface.NORMAL);

	// Combat manager objects
	public static Ship playerShip = null;
	public static ParticleManager pm = new ParticleManager();
	public static BulletManager bm = new BulletManager();
	public static EntityManager em = new EntityManager();
	public static AbilityManager am = new AbilityManager();

	// Other control objects
	public static Background background = null;
	public static Level level = null;
	public static PlayerControl control = null;
	public static BlockInvadersGame game = null;
	public static Hud hud = null;

	public static float gameSpeed = 1.0f;

	/**
	 * Reinitialize all assets (in case of starting new game mode, etc.). Note
	 * that the pilot object can only be reinitialized in case of a direct call
	 * to set it to a new object. This is a guard against inadvertent data loss
	 * from ending a stage, starting a new one, etc.
	 */
	public static void reinit() {
		rand = new Random(System.nanoTime());
		paint = new Paint();
		pm.reinit();
		bm.reinit();
		em.reinit();
		am.reinit();
		background = null;
		level = null;
		control = null;
		game = null;
		hud = null;
		gameSpeed = 1.0f;
		playerShip = null;
	}

}
