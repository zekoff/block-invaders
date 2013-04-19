package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Displays detailed information on weapon via buy_weapon_detail layout before
 * user purchases it.
 * 
 * @author Zekoff
 * 
 */
public class BuyWeaponDetail extends Activity implements Button.OnClickListener {
	Button buyWeaponButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buy_weapon_detail);

		setupListeners();
	}

	private void setupListeners() {
		buyWeaponButton = (Button) findViewById(R.id.buyButton);
		buyWeaponButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// buy weapon
		Toast.makeText(this, "Weapon purchased", 0).show();
		finish();
	}

}
