package com.fifino.gptw.screens;

import java.util.HashMap;
import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.events.TouchAction;
import com.fifino.gptw.GPTWGame;
import com.fifino.gptw.flags.AutoRun;
import com.fifino.gptw.helpers.GPTWResources;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.implementation.AndroidAudio;

public class MainMenu extends GPTWScreen implements TouchAction, AutoRun {

	public MainMenu(Game game) {
		super(game);
	}

	@Override
	protected void initializeAssets() {
		HashMap<String, String> assets = new HashMap<String, String>();
        assets.put(GPTWResources.GPTW_MAIN_BG, GPTWResources.GPTW_MAIN_BG_IMG);
        assets.put(GPTWResources.GPTW_MAIN_START, GPTWResources.GPTW_MAIN_START_IMG);
        assets.put(GPTWResources.GPTW_MAIN_RECORDS, GPTWResources.GPTW_MAIN_RECORDS_IMG);
		this.initializeAssets(assets);
	}
	MenuItem recordsButton, startButton;
	@Override
	protected void setupEntities() {
		Graphics g = game.getGraphics();
		bgImage = Assets.getAndroidImage(GPTWResources.GPTW_MAIN_BG);
        startButton = new MenuItem(Assets.getAndroidImage(GPTWResources.GPTW_MAIN_START), 10, 10 );
        recordsButton = new MenuItem(Assets.getAndroidImage(GPTWResources.GPTW_MAIN_RECORDS), 10, 10 );
        int h = g.getHeight() - startButton.getHeight();
		startButton.setY(h);
//        startButton.setX(g.getWidth() / 2 - startButton.getWidth() / 2);
        startButton.setX(0);
		startButton.setName("start");
		startButton.addTouchListener(this);

        recordsButton.setY(h);
        recordsButton.setX(startButton.getWidth() + 5);
        recordsButton.setName("records");
        recordsButton.addTouchListener(this);

		MenuItem bg = new MenuItem(bgImage, 0, 0);
		bg.setCollidable(false);
		this.menuItems.add(bg);
        this.menuItems.add(startButton);
        this.menuItems.add(recordsButton);
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
//					System.out.println(menuItems.getLastCollision().getName());
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

	@Override
	public void triggerTouch(String eventName, Object context) {
		this.triggerTouch(context);
	}

	@Override
	public void triggerTouch(Object context) {
		MenuItem item = (MenuItem)context;
        GPTWGame game = (GPTWGame) this.game;
        String name = item.getName();
		if("start".equals(name)){
			game.reset();
			game.setScreen(getNextScreen());
		} else if("records".equals(name)) {
            System.exit(0);
            //TODO: switch to records screen.
        }
	}
}
