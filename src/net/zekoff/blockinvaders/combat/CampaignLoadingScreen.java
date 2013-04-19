package net.zekoff.blockinvaders.combat;

import net.zekoff.androidgames.framework.Screen;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;
import net.zekoff.blockinvaders.combat.ability.Ability;
import net.zekoff.blockinvaders.combat.background.DynamicStarfield;
import net.zekoff.blockinvaders.combat.background.StaticStarfield;
import net.zekoff.blockinvaders.combat.control.MultiTouchControl;
import net.zekoff.blockinvaders.combat.control.SingleTouchControl;
import net.zekoff.blockinvaders.combat.hud.CampaignHud;
import net.zekoff.blockinvaders.combat.player.Pilot;
import net.zekoff.blockinvaders.combat.ship.Ship;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Intermediate screen to load campaign assets before their use in the combat
 * screens. Loads general game assets, not campaign- or endless-specific ones.
 * Can be used by game modes needing standard setup behavior (load selected
 * perks, outfit ship with selected weapons, etc.).
 * <p>
 * This class loads a CampaignScreen when it has finished; subclass can override
 * the update() method to load the appropriate screen.
 * 
 * @author Zekoff
 * 
 */
public class CampaignLoadingScreen extends Screen {

	public CampaignLoadingScreen(BlockInvadersGame game) {
		super(game);

		// reinitialize game
		Assets.reinit();
		Assets.game = game;

		// load all sounds/music for the game
		loadSounds();

		// ***** SETUP PLAYER-RELATED OBJECTS *****

		// setup ship
		Ship playerShip = Assets.pilot.shipBlueprint.getShip();
		playerShip.x = 160;
		playerShip.y = 375;
		Assets.playerShip = playerShip;

		// load abilities
		Assets.am.reinit();
		try {
			if (Assets.pilot.ability1 != null) {
				Ability loadedAbility1 = null;
				loadedAbility1 = (Ability) Class.forName(
						Pilot.abilityPackage + Assets.pilot.ability1)
						.newInstance();
				Assets.am.ability1 = loadedAbility1;
			}
			if (Assets.pilot.ability2 != null) {
				Ability loadedAbility2 = null;
				loadedAbility2 = (Ability) Class.forName(
						Pilot.abilityPackage + Assets.pilot.ability2)
						.newInstance();
				Assets.am.ability2 = loadedAbility2;
			}
		} catch (Exception e) {
			Log.d("BI_log", "Error loading abilities.");
		}

		// ***** SETUP PREFERENCES/OPTIONS *****

		// Retrieve shared preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(game);

		// Set up control
		boolean singleTouch = prefs.getBoolean("single_touch", false);
		if (singleTouch)
			Assets.control = new SingleTouchControl();
		else
			Assets.control = new MultiTouchControl();

		// Set up HUD
		Assets.hud = new CampaignHud();

		// Get pilot name
		Assets.pilot.name = prefs.getString("pilot_name", "Captain Blue");

		// Set up particle preferences
		String particlePreference;
		particlePreference = prefs.getString("particle_level", "1");
		Assets.pm.preferencesModifier = Float.parseFloat(particlePreference);

		// Set up background
		boolean dynamicStarfield;
		dynamicStarfield = prefs.getBoolean("dynamic_starfield", true);
		if (dynamicStarfield) {
			String numStars;
			numStars = prefs.getString("num_of_stars", "40");
			Assets.background = new DynamicStarfield(Integer.parseInt(numStars));
		} else
			Assets.background = new StaticStarfield();

		// antialiasing
		boolean aa;
		aa = prefs.getBoolean("noAntialias", true);
		game.getGraphics().setAntialiasing(!aa);
	}

	@Override
	public void update(float deltaTime) {
		game.setScreen(new CampaignScreen(game));
	}

	@Override
	public void present(float deltaTime) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	/**
	 * Fills the fields of SoundAssets with the appropriate sounds. Technically,
	 * this method should not start playing the music (separation of concerns);
	 * that should be done elsewhere.
	 */
	public void loadSounds() {
		// Load sounds
		SoundAssets.fastTick = game.getAudio().newSound("fast_tick.ogg");
		SoundAssets.smallerExplosion = game.getAudio().newSound(
				"smaller_explosion.ogg");
		SoundAssets.bigExplosion = game.getAudio()
				.newSound("big_explosion.ogg");
		SoundAssets.veryFastTick = game.getAudio().newSound(
				"very_fast_tick.ogg");
		SoundAssets.ding = game.getAudio().newSound("ding.ogg");
		SoundAssets.barrier = game.getAudio().newSound("barrier.ogg");
		SoundAssets.chain_laser = game.getAudio().newSound("chain_laser.ogg");
		SoundAssets.phase_shift = game.getAudio().newSound("phase_shift.ogg");
		SoundAssets.reflective_pulse = game.getAudio().newSound(
				"reflective_pulse.ogg");
		SoundAssets.get_loot = game.getAudio().newSound("get_loot.ogg");
		SoundAssets.supercharge = game.getAudio().newSound("supercharge.ogg");

		// Load small explosion sounds
		SoundAssets.smallExplosion1 = game.getAudio().newSound(
				"smallExplosion1.ogg");
		SoundAssets.smallExplosion2 = game.getAudio().newSound(
				"smallExplosion2.ogg");
		SoundAssets.smallExplosion3 = game.getAudio().newSound(
				"smallExplosion3.ogg");
		SoundAssets.smallExplosion4 = game.getAudio().newSound(
				"smallExplosion4.ogg");
		SoundAssets.smallExplosion5 = game.getAudio().newSound(
				"smallExplosion5.ogg");

		// Load music
		SoundAssets.track1 = game.getAudio().newMusic("track1.ogg");
		SoundAssets.track2 = game.getAudio().newMusic("track2.ogg");
		SoundAssets.track3 = game.getAudio().newMusic("track3.ogg");
		SoundAssets.track4 = game.getAudio().newMusic("track4.ogg");
		SoundAssets.track5 = game.getAudio().newMusic("track5.ogg");
		SoundAssets.track6 = game.getAudio().newMusic("track6.ogg");

		int musicChoice = Assets.rand.nextInt(6);
		switch (musicChoice) {
		case 0:
			SoundAssets.music = SoundAssets.track1;
			break;
		case 1:
			SoundAssets.music = SoundAssets.track2;
			break;
		case 2:
			SoundAssets.music = SoundAssets.track3;
			break;
		case 3:
			SoundAssets.music = SoundAssets.track4;
			break;
		case 4:
			SoundAssets.music = SoundAssets.track5;
			break;
		case 5:
			SoundAssets.music = SoundAssets.track6;
			break;
		}
		SoundAssets.music.setLooping(true);
		SoundAssets.music.setVolume(0.4f);
		// TODO start playing the music elsewhere
		SoundAssets.music.play();
	}
}
