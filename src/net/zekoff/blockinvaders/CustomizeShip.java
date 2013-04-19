package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.ship.ShipBlueprint.ShipClass;
import net.zekoff.blockinvaders.combat.ship.SampleHeavyShipBlueprint;
import net.zekoff.blockinvaders.combat.ship.SimpleShipBlueprint;
import net.zekoff.blockinvaders.combat.ship.SimpleTriangularShipBlueprint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Handle ship customization -- show levels and details and provide input for
 * changing ship name.
 * 
 * @author Zekoff
 * 
 */
public class CustomizeShip extends Activity implements Button.OnClickListener {
	Button customizeShipDetailButton;
	Button changeShipNameButton;

	Button b1;
	Button b2;
	Button b3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.customize_ship);

		LinearLayout l = new LinearLayout(this);
		l.setOrientation(LinearLayout.VERTICAL);

		b1 = new Button(this);
		b1.setText("Use Light Chassis");
		b1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Assets.pilot.shipBlueprint = new SimpleTriangularShipBlueprint();
				setButtonState();
				Toast.makeText(CustomizeShip.this, "All weapons unequipped.", 0)
						.show();
			}
		});
		b1.setEnabled(false);

		b2 = new Button(this);
		b2.setText("Use Medium Chassis");
		b2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Assets.pilot.shipBlueprint = new SimpleShipBlueprint();
				setButtonState();
				Toast.makeText(CustomizeShip.this, "All weapons unequipped.", 0)
						.show();
			}
		});
		b2.setEnabled(false);

		b3 = new Button(this);
		b3.setText("Use Heavy Chassis");
		b3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Assets.pilot.shipBlueprint = new SampleHeavyShipBlueprint();
				setButtonState();
				Toast.makeText(CustomizeShip.this, "All weapons unequipped.", 0)
						.show();
			}
		});
		b3.setEnabled(false);

		Button b4 = new Button(this);
		b4.setText("Equip Armor");
		b4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Assets.pilot.shipBlueprint.armor = Assets.pilot.inventory.armorInventory
						.get(0);
			}
		});
		b4.setEnabled(true);

		Button b5 = new Button(this);
		b5.setText("Equip Shield");
		b5.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Assets.pilot.shipBlueprint.shield = Assets.pilot.inventory.shieldInventory
						.get(0);
			}
		});
		b5.setEnabled(true);

		Button spacer = new Button(this);
		spacer.setVisibility(Button.INVISIBLE);

		l.addView(b1);
		l.addView(b2);
		l.addView(b3);

		l.addView(spacer);
		l.addView(b4);
		l.addView(b5);

		setContentView(l);

		setButtonState();
		// setupListeners();
	}

	private void setButtonState() {
		if (Assets.pilot.shipBlueprint.getShipClass() == ShipClass.light) {
			b1.setEnabled(false);
			b2.setEnabled(true);
			b3.setEnabled(true);
		} else if (Assets.pilot.shipBlueprint.getShipClass() == ShipClass.medium) {
			b1.setEnabled(true);
			b2.setEnabled(false);
			b3.setEnabled(true);
		} else {
			b1.setEnabled(true);
			b2.setEnabled(true);
			b3.setEnabled(false);
		}
	}

	private void setupListeners() {
		customizeShipDetailButton = (Button) findViewById(R.id.customizeShipDetailButton);
		customizeShipDetailButton.setOnClickListener(this);
		changeShipNameButton = (Button) findViewById(R.id.changeShipNameButton);
		changeShipNameButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.customizeShipDetailButton:
			// confirm customization or present customization details
			break;
		case R.id.changeShipNameButton:
			// raise edittext to change ship name IF ship has been customized
			break;
		}
	}
}
