package com.fifino.gptw.screens;

import android.graphics.Color;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.MenuItemComposite;
import com.fifino.gptw.GPTWResources;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MiddleScreen extends GPTWScreen {
	//TODO: Change this for Sprite instead of image.
	String bgName = "";
	public MiddleScreen(Game game, String bgName) {
		super(game);
		this.bgName = bgName;
		initializeAssets();
		setupEntities();
	}

	protected void postConstruct(){
	}
	@Override
	protected void initializeAssets() {
		HashMap<String, String> assets = new HashMap<String, String>();
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
			System.out.println("Loading: " + assets.get(key));
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
		game.setScreen(getNextScreen());
		Assets.remove(bgImage);
		bgImage.getBitmap().recycle();
		this.state = GameState.Paused;
	}
		//this.menuItems.update(deltaTime);
		/*
        int len = touchEvents.size();
		TouchEvent event = null;
		Point point;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    // Handles out of bounds exception for the list getting empty
                    // after getting the size.
                    return;
                }
				event = touchEvents.get(i);
				point = new Point(event.x, event.y);
				if(menuItems.collides(point)){
					System.out.println(menuItems.getLastCollision().getName());
					menuItems.getLastCollision().triggerTouchEvent();
				}
//                if (event.type == TouchEvent.TOUCH_DOWN) {
//                    state = GameState.GameOver;
//                }
            }
        }
        */
	}
}
