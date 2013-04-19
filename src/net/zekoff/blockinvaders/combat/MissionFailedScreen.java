package net.zekoff.blockinvaders.combat;

import android.graphics.Color;
import net.zekoff.androidgames.framework.impl.BlockInvadersGame;

public class MissionFailedScreen extends LevelUpScreen {

	public MissionFailedScreen(BlockInvadersGame game) {
		super(game, "MISSION FAILED");
		headerColor = Color.RED;
	}

}
