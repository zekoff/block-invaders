package net.zekoff.blockinvaders.combat.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.perk.Perk;
import net.zekoff.blockinvaders.combat.ship.ShipBlueprint;
import net.zekoff.blockinvaders.combat.ship.SimpleShipBlueprint;
import android.os.Environment;

/**
 * All persistent data related to the player's pilot. One Pilot object exists at
 * a time (unless additional save files are added) and this object is read and
 * written to persist data. After loading the data, classes may interact with
 * this object as necessary.
 * <p>
 * The player's ship is stored as a ShipBlueprint, which itself contains the
 * Weapon, Shield, and Armor Blueprints needed to instantiate the player's ship
 * at runtime. Abilities are stored as Strings containing the name of the class
 * of the ability, and instantiated at runtime as fresh copies through
 * reflection.
 * 
 * @author Zekoff
 * 
 */
public class Pilot implements Serializable {
	private static final long serialVersionUID = 280312608256261263L;
	public static final String abilityPackage = "net.zekoff.blockinvaders.combat.ability.";
	/**
	 * A boolean value indicating whether or not the most recent getPilot() call
	 * threw an error.
	 */
	public static boolean error = false;

	public static Pilot me = null;
	public String name = null;
	public int xp = 0;
	public int xpGained = 0;
	public int level = 1;
	public int money = 500;
	public int totalSkillPoints = 0;
	public int freeSkillPoints = 0;
	public Inventory inventory = new Inventory();

	public Shop shop = new Shop();

	public int levelCap = 30;
	public boolean endlessModeUnlocked = false;
	public boolean scenarioModeUnlocked = false;
	public ArrayList<String> scenariosUnlocked = new ArrayList<String>();

	public CampaignProgression campaignProgression = new CampaignProgression();

	public ShipBlueprint shipBlueprint = new SimpleShipBlueprint();

	/**
	 * Fully qualified class name of Ability equipped in slot 1.
	 */
	public String ability1 = null;
	/**
	 * Fully qualified class name of Ability equipped in slot 2.
	 */
	public String ability2 = null;

	public ArrayList<Perk> perks = new ArrayList<Perk>();
	/**
	 * A list of the class names of all Abilities the player can equip.
	 */
	public ArrayList<String> abilities = new ArrayList<String>();

	public ArrayList<String> titles = new ArrayList<String>();

	// Need to add a way to store ship and, via ship, equipped items

	// Does not follow best practice of saving to the correct location right
	// now. Need to go back and fix this.

	// private static final String pilotPath =
	// "Android/data/net/zekoff/blockinvaders/files/pilotdata";

	private Pilot() {
		xp = 0;
		level = 1;
		totalSkillPoints = 0;
		freeSkillPoints = totalSkillPoints;
	}

	/**
	 * Get access through this static method; not through constructing a Pilot.
	 * 
	 * @return the game's pilot from saved data
	 */
	public static Pilot getPilot() {
		if (me == null) {
			try {
				loadPilot();
			} catch (PilotDataNotFound e) {
				error = true;
			} catch (PilotDataCorrupt e) {
				error = true;
			}
		}
		return me;
	}

	/**
	 * A static method to load stored pilot data from the save file. Once
	 * loaded, pilot data will be present in the Pilot.me field. If an error was
	 * thrown as a result of loading the file, the Pilot.error flag will be set
	 * to true.
	 * 
	 * @throws PilotDataNotFound
	 * @throws PilotDataCorrupt
	 */
	private static void loadPilot() throws PilotDataNotFound, PilotDataCorrupt {
		error = false;
		Pilot loadedPilot = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
		try {
			fis = new FileInputStream(path + "pilotdata");
			in = new ObjectInputStream(fis);
			loadedPilot = (Pilot) in.readObject();
			in.close();
		} catch (Exception e) {
			loadedPilot = new Pilot();
			throw new PilotDataNotFound();
		} finally {
			me = loadedPilot;
		}
	}

	/**
	 * Writes current pilot data out to a file. Can be called as often as
	 * desired to make sure that the file contains current pilot data.
	 */
	public static void savePilot() {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
		try {
			fos = new FileOutputStream(path + "pilotdata");
			out = new ObjectOutputStream(fos);
			out.writeObject(me);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Adds XP to the total the pilot has gained since the start of the level.
	 * 
	 * @param xp
	 */
	public void addXp(int xp) {
		Perk p = hasPerkActive("Fast Learner");
		if (p != null) {
			xp *= p.getRankEffect(p.rank);
		}
		this.xpGained += xp;
	}

	/**
	 * To be called during post-mission processing, after setting the initial
	 * value of the XP bar on the level-up screen.
	 */
	public void commitXp() {
		xp += xpGained;
		xpGained = 0;
	}

	/**
	 * Return whether or not an error was thrown on the most recent call of
	 * getPilot().
	 * 
	 * @return
	 */
	public static boolean checkError() {
		boolean temp = error;
		error = false;
		return temp;
	}

	/**
	 * Convenience method to check for whether a player current has a given perk
	 * active. Returns that Perk object if its rank is greater than 0. If a
	 * player has access to a perk but it is not active, or if the player does
	 * not have access at all, will return null.
	 * 
	 * @param perkName
	 *            The String name of the perk according to its class-defined
	 *            field; not necessarily the name of the perk class itself.
	 * @return The Perk object representing the active Perk, or null.
	 */
	public Perk hasPerkActive(String perkName) {
		for (Perk p : perks) {
			if (p.name.compareTo(perkName) == 0) {
				if (p.rank > 0) {
					return p;
				}
			}
		}
		return null;
	}

	/**
	 * Convenience method to check if pilot has this perk at all (whether active
	 * or not).
	 * 
	 * @param perkName
	 *            The name of the perk to check for (perk name field, not
	 *            necessarily the class name).
	 * @return Boolean indicating whether pilot has access to the perk.
	 */
	public boolean hasPerk(String perkName) {
		for (Perk p : perks) {
			if (p.name.compareTo(perkName) == 0)
				return true;
		}
		return false;
	}

	/**
	 * Convenience method to check whether or not pilot has access to the listed
	 * ability.
	 * 
	 * @param abilityName
	 *            The name of the ability to check for.
	 * @return Boolean value indicating whether pilot has access to the ability.
	 */
	public boolean hasAbility(String abilityName) {
		for (String s : abilities) {
			if (s.compareTo(abilityName) == 0)
				return true;
		}
		return false;
	}

	public boolean hasTitle(String title) {
		for (String s : titles) {
			if (title.compareTo(s) == 0)
				return true;
		}
		return false;
	}

	/**
	 * Delete the file containing pilot data.
	 */
	public static void deletePilot() {
		File pilotFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "pilotdata");
		pilotFile.delete();
	}
}
