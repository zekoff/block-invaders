package net.zekoff.blockinvaders.utility;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.enemy.Enemy;
import net.zekoff.blockinvaders.combat.loot.Loot;
import net.zekoff.blockinvaders.combat.weapon.BlasterBlueprint;
import net.zekoff.blockinvaders.combat.weapon.BurstShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.GrenadeLauncherBlueprint;
import net.zekoff.blockinvaders.combat.weapon.HeavyLaserBlueprint;
import net.zekoff.blockinvaders.combat.weapon.HelixShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.LaserBlueprint;
import net.zekoff.blockinvaders.combat.weapon.MainCannonBlueprint;
import net.zekoff.blockinvaders.combat.weapon.MissileBatteryBlueprint;
import net.zekoff.blockinvaders.combat.weapon.MultiShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.RocketLauncherBlueprint;
import net.zekoff.blockinvaders.combat.weapon.ScatterShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.RapidShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.FanShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.SpreadShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.TriShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.TwinShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.VeeShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.WaveShotBlueprint;
import net.zekoff.blockinvaders.combat.weapon.WeaponBlueprint;
import net.zekoff.blockinvaders.combat.weapon.WideShotBlueprint;

public class LootTable {
	public static String[] genericWeaponAdjectives = new String[] { "Super ",
			"Turbo ", "Mega ", "Terror ", "Hyper ", "Fast ", "Power ",
			"Master ", "Thunder ", "Xtreme", "Destiny ", "Lightning ", "Fire ",
			"Thunderous ", "Dangerous ", "Exploding ", "Sting ",
			"Destructive ", "Destruction ", "Angry ", "Killer ", "Pwning ",
			"Terminal ", "Angry ", "Phase ", "Raging ", "Bandit ", "Agony ",
			"Pain ", "Skull ", "Excecutioner's ", "Vorpal ", "Variable ",
			"Sniper ", "Magma ", "Meteor ", "Single-Barrelled ", "Flux ",
			"Quad-Capacitor ", "Stealth ", "Multi-", "Spinning ", "Blazing ",
			"Frostbite ", "Static ", "Archos ", "Dominus ", "Bandit's ",
			"Mercenary's ", "Rising ", "Burst-fire ", "Star ", "Adama's ",
			"Starbuck's ", "Mal's ", "Luke's ", "Alliance ", "Rebel ",
			"Imperial ", "Crazy ", "Madness ", "Long-range ", "Stingray ",
			"Mesmerizing ", "Pirate's ", "Alpha ", "Echo ", "Omega ",
			"Constellation ", "Cylon ", "Jedi's ", "Syndicate ", "Volatile ",
			"Plasma ", "Rocket ", "Scrap ", "Fortress ", "Antimatter ",
			"Photon ", "Neutron ", "Relativity ", "Splodin' ", "Durden's ",
			"Sonic ", "Solar ", "Battle-scarred ", "Deadly ", "Infinite ",
			"Chromatic " };
	public static String[] genericWeaponPrefixes = new String[] { "Exo",
			"Dyna", "Electro", "Hydro", "Auto", "Magna", "Meta", "Mega",
			"Turbo", "Hyper", "Quasi", "Spin", "Trans", "Ambi", "Anti",
			"Macro", "Retro", "Inter", "Infra", "Astro", "Ray", "Omni",
			"Necro", "Tele" };
	public static String[] genericWeaponBasewords = new String[] { "Gun",
			"Blaster", "Shooter", "Cannon", "Minigun", "Rifle", "Magnum",
			"Mortar", "Musket", "Shotgun", "Pistol", "Revolver", "Shotgun",
			"Artillery", "Needler", "Nailgun", "Railgun", "Chaingun", "Cutter",
			"Slicer", "Buster", "Crusher", "Carbine", "Carronade",
			"Muzzleloader", "Bombardier", "Bazooka", "Ripper", "Obliterator",
			"Flayer", "Disruptor", "Piercer", "Melter", "Sidearm", "Saber",
			"Launcher", "Lance", "Lasher", "Ballista", "Turret", "Javelin",
			"Shuriken", "Discus", "Slasher" };
	public static String[] genericWeaponSuffixes = new String[] { " of Terror",
			" of Power", " of the Master", " of Thunder", " of Destiny",
			" of Lightning", " of Fire", " of Danger", " of the Thousand Cuts",
			" of Justice", " of the Viper", " of Destruction",
			" of Excecution", " of Slaying", " of Annihilation", " of Smiting",
			" of the Blaze", " of First Frost", " of the Dynasty",
			" of Mauling", " of Discord", " of Fury", " of Ouchies",
			" of Sundering", " of Retaliation", " of Srsly", " of O'rly",
			" of Alpha Centauri", " of Midgar", " of Serenity", " of the Maw",
			" of Cleansing Fire", " of the Gerudo", " of the Bitter End",
			" of the Last Stand", " of Mortality", " of Epic Metal",
			" of Dragon's Force", " of the Fire and Flames",
			" of the Icy Grip", " of Death's Scythe", " of the Wing Zero",
			" of the Hellraiser", " of Churchill", " of the Daywalker",
			" of Zeus", " of Moonfire", " of Galactica", " of the Falcon",
			" of the Final Frontier", " of the Normandy", " of the Enterprise" };
	public static String[] legendaryWeaponNames = new String[] {
			"The Haymaker", "The Peacemaker", "The Equalizer", "The Diplomat",
			"The Persuader", "Dr. Jones", "Manifest Destiny",
			"Random Axe of Kindness", "Bluth Buster", "Saturday Night Special",
			"The Hammer", "Matrix Destabilizer" };

	public static String getWeaponName() {
		int i = Assets.rand.nextInt(genericWeaponBasewords.length);
		String base = genericWeaponBasewords[i];
		int rand = Assets.rand.nextInt(20);
		if (rand < 9) {
			i = Assets.rand.nextInt(genericWeaponAdjectives.length);
			String adjective = genericWeaponAdjectives[i];
			return adjective + base;
		} else if (rand < 12) {
			i = Assets.rand.nextInt(genericWeaponPrefixes.length);
			String prefix = genericWeaponPrefixes[i];
			return prefix + base.toLowerCase();
		} else if (rand < 17) {
			i = Assets.rand.nextInt(genericWeaponSuffixes.length);
			String suffix = genericWeaponSuffixes[i];
			return base + suffix;
		} else {
			i = Assets.rand.nextInt(genericWeaponPrefixes.length);
			String prefix = genericWeaponPrefixes[i];
			i = Assets.rand.nextInt(genericWeaponSuffixes.length);
			String suffix = genericWeaponSuffixes[i];
			return prefix + base.toLowerCase() + suffix;
		}
	}

	public static WeaponBlueprint getRandomWeapon(Enemy e) {
		// Determine what level of weapon to spawn
		int weaponLevel;
		int levelModifier = Assets.rand.nextInt(100);
		if (levelModifier > 96)
			weaponLevel = 4;
		else if (levelModifier > 92)
			weaponLevel = 3;
		else if (levelModifier > 85)
			weaponLevel = 2;
		else if (levelModifier > 74)
			weaponLevel = 1;
		else if (levelModifier > 40)
			weaponLevel = 0;
		else if (levelModifier > 27)
			weaponLevel = -1;
		else if (levelModifier > 10)
			weaponLevel = -2;
		else
			weaponLevel = -3;
		weaponLevel += e.level;
		if (weaponLevel < 1)
			weaponLevel = 1;
		if (weaponLevel > (Assets.pilot.levelCap + 10))
			weaponLevel = Assets.pilot.levelCap + 10;

		// Get a random weapon blueprint
		WeaponBlueprint w = getRandomWeaponBlueprint(weaponLevel);

		w.name = getWeaponName();
		// set weapon quality
		// more difficult an enemy is, better chance of improved weapon
		// for now (just for debugging, flat chance brackets)
		int weaponQuality = Assets.rand.nextInt(6);
		switch (weaponQuality) {
		case 0:
			w.quality = Loot.JUNK_QUALITY;
			break;
		case 1:
			w.quality = Loot.STANDARD_QUALITY;
			break;
		case 2:
			w.quality = Loot.UNCOMMON_QUALITY;
			break;
		case 3:
			w.quality = Loot.RARE_QUALITY;
			break;
		case 4:
			w.quality = Loot.EPIC_QUALITY;
			break;
		case 5:
			w.quality = Loot.LEGENDARY_QUALITY;
			break;
		}
		return w;
	}

	public static WeaponBlueprint getRandomWeaponBlueprint(int weaponLevel) {
		// Determine what type of weapon to spawn
		WeaponBlueprint w = null;
		int i = Assets.rand.nextInt(19);
		switch (i) {
		case 0:
			w = new MainCannonBlueprint(weaponLevel);
			break;
		case 1:
			w = new FanShotBlueprint(weaponLevel);
			break;
		case 2:
			w = new ScatterShotBlueprint(weaponLevel);
			break;
		case 3:
			w = new BurstShotBlueprint(weaponLevel);
			break;
		case 4:
			w = new RapidShotBlueprint(weaponLevel);
			break;
		case 5:
			w = new BlasterBlueprint(weaponLevel);
			break;
		case 6:
			w = new TriShotBlueprint(weaponLevel);
			break;
		case 7:
			w = new MissileBatteryBlueprint(weaponLevel);
			break;
		case 8:
			w = new LaserBlueprint(weaponLevel);
			break;
		case 9:
			w = new WaveShotBlueprint(weaponLevel);
			break;
		case 10:
			w = new WideShotBlueprint(weaponLevel);
			break;
		case 11:
			w = new GrenadeLauncherBlueprint(weaponLevel);
			break;
		case 12:
			w = new HeavyLaserBlueprint(weaponLevel);
			break;
		case 13:
			w = new HelixShotBlueprint(weaponLevel);
			break;
		case 14:
			w = new RocketLauncherBlueprint(weaponLevel);
			break;
		case 15:
			w = new SpreadShotBlueprint(weaponLevel);
			break;
		case 16:
			w = new TwinShotBlueprint(weaponLevel);
			break;
		case 17:
			w = new VeeShotBlueprint(weaponLevel);
			break;
		case 18:
			w = new MultiShotBlueprint(weaponLevel);
			break;
		}
		return w;
	}
}
