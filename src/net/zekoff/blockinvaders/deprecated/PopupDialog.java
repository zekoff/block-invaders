package net.zekoff.blockinvaders.deprecated;

import net.zekoff.blockinvaders.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Simple pop-up to display small amounts of information and block execution
 * until manually advanced.
 * 
 * @author Zekoff
 * 
 */
public class PopupDialog extends Activity implements Button.OnClickListener {
	Button b;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_dialog);
		TextView t = (TextView) findViewById(R.id.popupDialogTextView1);
		t.setText("You killed 25 dudes!");
		b = (Button) findViewById(R.id.popupDialogButton1);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.popupDialogButton1) {
			finish();
		}
	}

}
