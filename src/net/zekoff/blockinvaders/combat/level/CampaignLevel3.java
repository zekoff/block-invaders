package net.zekoff.blockinvaders.combat.level;

import java.util.ArrayList;

import net.zekoff.blockinvaders.combat.Assets;
import net.zekoff.blockinvaders.combat.CampaignScreen;
import net.zekoff.blockinvaders.combat.background.WeaponsDepot;
import net.zekoff.blockinvaders.combat.background.WeaponsDepot.Area;

public class CampaignLevel3 extends CampaignLevel {

	WeaponsDepot background;

	public CampaignLevel3(CampaignScreen screen) {
		super(screen);
		background = new WeaponsDepot();
		Assets.background = background;
	}

	@Override
	public void update(float deltaTime) {
		if (background.totalDistance > 500 && background.area == Area.preDepot)
			background.area = Area.depotEntrance;
		if (background.area == Area.depotEscape && background.speed >= 180)
			missionComplete();
	}

	@Override
	public String getBriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDebriefing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getRewards() {
		// TODO Auto-generated method stub
		return null;
	}

}
