package com.fifino.gptw.screens;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Point;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.TouchAction;
import com.fifino.framework.simple.SimpleScreen;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;

public class MainMenu extends GPTWScreen {

	public MainMenu(Game game) {
		super(game);
	}

	@Override
	protected void initializeAssets() {
		HashMap<String, String> assets = new HashMap<String, String>();
		String imagesPath = Assets.IMAGES_PATH + "/screens/main/";
		Graphics g = game.getGraphics();
		assets.put("mainBg", imagesPath + "bg.png");
		assets.put("mainStart", imagesPath + "start.png");
		assets.put("mainExit", imagesPath + "exit.png");
		Set<String> keys = assets.keySet();
		for(String key:keys){
			if(Assets.getImage(key) == null){
				System.out.println("Loading: " + assets.get(key));
				Image bgImage =  g.newImage(assets.get(key), Graphics.ImageFormat.RGB565);
				Assets.addImage(key, bgImage);
			}
		}
	}
	MenuItem startButton, exitButton;
	@Override
	protected void setupEntities() {
		Graphics g = game.getGraphics();
		bgImage = Assets.getAndroidImage("mainBg");
		startButton = new MenuItem(Assets.getAndroidImage("mainStart"), 10, 10 );
		exitButton = new MenuItem(Assets.getAndroidImage("mainExit"), 10, 50);
		exitButton.setY(g.getHeight() - exitButton.getHeight() - 15);
		startButton.setY(exitButton.getY() - exitButton.getHeight() - 15);
		startButton.setX(g.getWidth() / 2 - startButton.getWidth() / 2);
		startButton.setName("Start Button");
		exitButton.setName("Exit Button");
		startButton.addTouchListener(new TouchAction() {
			@Override
			public void triggerTouch() {
				game.setScreen(new SimpleScreen(game));
			}
		});
		exitButton.addTouchListener(new TouchAction() {
			@Override
			public void triggerTouch() {
				System.exit(0);
			}
		});
		exitButton.setX(g.getWidth() / 2 - startButton.getWidth() / 2);
		MenuItem bg = new MenuItem(bgImage, 0, 0);
		bg.setCollidable(false);
		this.menuItems.add(bg);
		this.menuItems.add(startButton);
		this.menuItems.add(exitButton);

		this.state = GameState.Running;
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
//        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
//        g.drawString("Running boys =D: " + this.input.getOrientation(), g.getWidth() / 2 - 25, 40,
//				paintB);
		this.menuItems.draw(g);
	}
	@Override
	protected void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		this.menuItems.update(deltaTime);
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
	}
	@Override
	protected void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    // Handles out of bounds exception for the list getting empty
                    // after getting the size.
                    return;
                }
                TouchEvent event = touchEvents.get(i);
                if (event.type == TouchEvent.TOUCH_DOWN) {
                    state = GameState.Ready;
                }
            }
        }
    }

}
