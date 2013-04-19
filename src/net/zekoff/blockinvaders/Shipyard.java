package net.zekoff.blockinvaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * UI activity to present Shipyard interface; leads to weapon equipping and ship
 * customization.
 * 
 * @author Zekoff
 * 
 */
public class Shipyard extends Activity implements Button.OnClickListener {
	Button changeLoadoutButton;
	Button buyWeaponsButton;
	Button customizeShipButton;
	Button tradeShipButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shipyard);

		TextView shipname = (TextView) findViewById(R.id.shipyardShipName);
		shipname.setText("Ship name: NYI");

		TextView shipclass = (TextView) findViewById(R.id.shipyardShipClass);
		shipclass.setText("Ship class: Medium");

		setupListeners();
	}

	private void setupListeners() {
		changeLoadoutButton = (Button) findViewById(R.id.changeLoadoutButton);
		changeLoadoutButton.setOnClickListener(this);
		buyWeaponsButton = (Button) findViewById(R.id.buyWeaponsButton);
		buyWeaponsButton.setOnClickListener(this);
		customizeShipButton = (Button) findViewById(R.id.customizeShipButton);
		customizeShipButton.setOnClickListener(this);
		tradeShipButton = (Button) findViewById(R.id.tradeShipButton);
		tradeShipButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changeLoadoutButton:
			startActivity(new Intent(this, MountPoints.class));
			break;
		case R.id.buyWeaponsButton:
			startActivity(new Intent(this, ShopList.class));
			break;
		case R.id.customizeShipButton:
			startActivity(new Intent(this, CustomizeShip.class));
			break;
		case R.id.tradeShipButton:
			startActivity(new Intent(this, TradeShipList.class));
			break;
		}
	}
}
