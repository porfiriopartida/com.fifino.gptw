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

public class MiddleScreen extends GPTWScreen {
	//TODO: Change this for Sprite instead of image.
	String bgName = "";
	GPTWTransition transition;
	public MiddleScreen(Game game, String bgName, GPTWTransition transition) {
		super(game);
		this.bgName = bgName;
		this.transition = transition;
		initializeAssets();
		setupEntities();
	}

	protected void postConstruct(){
	}
	@Override
	protected void initializeAssets() {
		HashMap<String, String> assets;
		assets = new HashMap<String, String>();
		Graphics g = game.getGraphics();
		String imagesPath = Assets.IMAGES_PATH;

		assets.put("airs-1", imagesPath + "/screens/games/airs-1.png");
		assets.put("airs-2", imagesPath + "/screens/games/airs-2.png");
		assets.put("alarms-1", imagesPath + "/screens/games/alarms-1.png");
        assets.put("alarms-2", imagesPath + "/screens/games/alarms-2.png");
        assets.put("airs-0", imagesPath + "/screens/games/airs-0.png");
        assets.put("alarms-0", imagesPath + "/screens/games/alarms-0.png");
		assets.put(GPTWResources.FIND_THE_CAR_WIN, GPTWResources.FIND_THE_CAR_WIN_IMG);
		assets.put(GPTWResources.FIND_THE_CAR_LOSE_1, GPTWResources.FIND_THE_CAR_LOSE_1_IMG);
		assets.put(GPTWResources.FIND_THE_CAR_LOSE_2, GPTWResources.FIND_THE_CAR_LOSE_2_IMG);


		String key = this.bgName;
		//if(Assets.getImage(key) == null){
			Image bgImage =  g.newImage(assets.get(key), Graphics.ImageFormat.RGB565);
			Assets.addImage(key, bgImage);
		//}
	}
	@Override
	protected void setupEntities() {
//		Graphics g = game.getGraphics();
		bgImage = Assets.getAndroidImage(bgName);
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
			game.setScreen(getScoreScreen(transition));
			Assets.remove(bgImage);
			bgImage.getBitmap().recycle();
			this.state = GameState.Paused;
		}
	}
}
