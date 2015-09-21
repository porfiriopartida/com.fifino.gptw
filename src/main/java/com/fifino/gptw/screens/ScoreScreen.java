package com.fifino.gptw.screens;

import android.graphics.Color;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.gptw.helpers.GPTWResources;
import com.fifino.gptw.helpers.GPTWTransition;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;

import java.util.HashMap;
import java.util.List;

public class ScoreScreen extends GPTWScreen {
	HashMap<String, String> assets;
	public ScoreScreen(Game game, GPTWTransition transition) {
		super(game);
		initializeAssets();
		setupEntities();
	}

	protected void postConstruct(){
	}
	@Override
	protected void initializeAssets() {
	 	assets = new HashMap<String, String>();
		Graphics g = game.getGraphics();
		String imagesPath = Assets.IMAGES_PATH;
		assets.put(GPTWResources.GPTW_SCORE_BG, GPTWResources.GPTW_SCORE_BG_IMG);
		assets.put(GPTWResources.GPTW_SCORE_LIVE, GPTWResources.GPTW_SCORE_LIVE_IMG);
		initializeAssets(assets);
	}
	@Override
	protected void setupEntities() {
//		Graphics g = game.getGraphics();
		bgImage = Assets.getAndroidImage(GPTWResources.GPTW_SCORE_BG);
		MenuItem bg = new MenuItem(bgImage, 0, 0);
		this.menuItems.add(bg);
		this.state = GameState.Running;
		lastTime = getTimestamp();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void backButton() {
		System.exit(0);
	}
	@Override
	protected void drawRunningUI(List<TouchEvent> touchEvents, float deltaTime) {
        Graphics g = game.getGraphics();
          g.fillRect(0, 0, g.getWidth(), g.getHeight(), Color.WHITE);
		this.menuItems.draw(g);
	}
	@Override
	protected void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		if(this.getTimestamp() > lastTime + 3000){
			this.clean(this.assets);
			game.setScreen(getNextScreen());
			this.state = GameState.Paused;
		}
	}
}
