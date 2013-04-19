package net.zekoff.blockinvaders.deprecated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class to inteface with an SQLite database for BlockInvaders. Currently
 * unused.
 * 
 * @author Zekoff
 * 
 */
public class WeaponDatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_TABLE_NAME = "weapons";

	// Descriptive attributes
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_WORTH = "worth";
	public static final String KEY_UPGRADE_LEVEL = "upgrade_level";
	public static final String KEY_WEAPON_QUALITY = "weapon_quality";
	public static final String KEY_XP = "xp";

	// Common weapon attributes
	public static final String KEY_WEAPON_CLASS = "weapon_class";
	public static final String KEY_SHOTS_PER_VOLLEY = "shots_per_volley";
	public static final String KEY_ACCURACY = "accuracy";
	public static final String KEY_TRAVEL_SPEED = "travel_speed";
	public static final String KEY_BULLET_SIZE = "bullet_size";
	public static final String KEY_FIRING_RATE = "firing_rate";
	public static final String KEY_BULLET_POWER = "bullet_power";
	public static final String KEY_WEAPON_ELEMENT = "weapon_element";

	// Homing/seeking attributes
	public static final String KEY_REACQUIRE_TARGET = "reaquire_target";
	public static final String KEY_INERRANT_TRACKING = "inerrant_tracking";
	public static final String KEY_AGILITY = "agility";

	// AoE attributes
	public static final String KEY_AE_POWER = "ae_power";
	public static final String KEY_AE_SIZE = "ae_size";
	public static final String KEY_AE_DURATION = "ae_duration";

	// DoT attributes
	public static final String KEY_DOT_POWER = "dot_power";
	public static final String KEY_DOT_LENGTH = "dot_length";

	private static final String DATABASE_CREATE = "CREATE TABLE "
			+ DATABASE_TABLE_NAME + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ID + " TEXT, "
			+ KEY_DESCRIPTION + " TEXT, " + KEY_WORTH + " TEXT, "
			+ KEY_UPGRADE_LEVEL + " TEXT, " + KEY_WEAPON_QUALITY + " TEXT, "
			+ KEY_XP + " TEXT, " + KEY_WEAPON_CLASS + " TEXT, "
			+ KEY_SHOTS_PER_VOLLEY + " TEXT, " + KEY_ACCURACY + " TEXT, "
			+ KEY_TRAVEL_SPEED + " TEXT, " + KEY_TRAVEL_SPEED + " TEXT, "
			+ KEY_BULLET_POWER + " TEXT, " + KEY_WEAPON_ELEMENT + " TEXT, "
			+ KEY_REACQUIRE_TARGET + " TEXT, " + KEY_INERRANT_TRACKING
			+ " TEXT, " + KEY_AGILITY + " TEXT, " + KEY_AE_POWER + " TEXT, "
			+ KEY_AE_SIZE + " TEXT, " + KEY_AE_DURATION + " TEXT, "
			+ KEY_DOT_POWER + " TEXT, " + KEY_DOT_LENGTH + " TEXT)";

	public WeaponDatabaseHelper(Context context) {
		super(context, DATABASE_TABLE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// upgrades not supported at this time
	}

}
