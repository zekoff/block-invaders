package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Displays Shared Preferences to user.
 * 
 * @author Zekoff
 * 
 */
public class OptionsScreen extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
