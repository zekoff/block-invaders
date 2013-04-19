package net.zekoff.blockinvaders;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.perk.Perk;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PerkDetail extends Activity implements OnClickListener {
	TextView pr = null;
	TextView prd = null;
	Perk perk = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perk_detail);

		String perkNameFromBundle = this.getIntent().getExtras()
				.getString("perkName");
		for (Perk p : Assets.pilot.perks) {
			if (p.name.compareTo(perkNameFromBundle) == 0) {
				perk = p;
			}
		}

		TextView pn = (TextView) findViewById(R.id.perkDetailPerkName);
		pn.setText(perk.name);

		TextView pd = (TextView) findViewById(R.id.perkDetailPerkDescription);
		pd.setText(perk.getDescription());

		pr = (TextView) findViewById(R.id.perkDetailRankDisplay);
		pr.setText("Perk rank: " + perk.rank);

		prd = (TextView) findViewById(R.id.perkDetailPerkLevelDescription);
		if (perk.rank > 0)
			prd.setText(perk.getRankDescription(perk.rank));
		else
			prd.setText("Perk inactive.");

		Button lessButton = (Button) findViewById(R.id.perkDetailLessButton);
		lessButton.setOnClickListener(this);

		Button moreButton = (Button) findViewById(R.id.perkDetailMoreButton);
		moreButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.perkDetailLessButton:
			perk.rank--;
			Log.d("BI_log", "Perk rank set to " + perk.rank);
			if (perk.rank < 0)
				perk.rank = 0;
			if (perk.rank > 0)
				prd.setText(perk.getRankDescription(perk.rank));
			else
				prd.setText("Perk inactive.");
			pr.setText("Perk rank: " + perk.rank);
			break;
		case R.id.perkDetailMoreButton:
			perk.rank++;
			Log.d("BI_log", "Perk rank set to " + perk.rank);
			if (perk.rank > perk.numRanks)
				perk.rank = perk.numRanks;
			prd.setText(perk.getRankDescription(perk.rank));
			pr.setText("Perk rank: " + perk.rank);
			break;
		}
	}
}
