package com.fifino.gptw.screens;

import android.graphics.Point;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LoadingScreen extends GPTWScreen {
    boolean doneLoading = false;
	public LoadingScreen(Game game) {
        super(game);
        this.state = GameState.Running;
	}
    /**
     * Initializes all other assets so game runs smoothly :O
     */
    private void initializeAllAssets(){
        HashMap<String, String> assets = new HashMap<String, String>();
        String imagesPath = Assets.IMAGES_PATH;
        Graphics g = game.getGraphics();
        assets.put("mainBg", imagesPath + "/screens/main/bg.png");
        assets.put("mainStart", imagesPath + "/screens/main/start.png");
        assets.put("mainExit", imagesPath + "/screens/main/exit.png");
        Set<String> keys = assets.keySet();
        int c = 0;
        for(String key:keys){
//            if(Assets.getImage(key) == null){
                Image bgImage =  g.newImage(assets.get(key), Graphics.ImageFormat.RGB565);
                Assets.addImage(key, bgImage);
//            }
        }
        doneLoading = true;
        this.game.setScreen(new MainMenu(game));
    }
    Point last = null;
    @Override
    protected void drawRunningUI(List<TouchEvent> touchEvents, float deltaTime){
        Graphics g = game.getGraphics();
        loadingScreen.draw(g);
        if(last != null){
            g.drawCircle(last.x, last.y, 10, paintB);
        }
    }
    @Override
    protected void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        TouchEvent event = null;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    // Handles out of bounds exception for the list getting empty
                    // after getting the size.
                    return;
                }
                event = touchEvents.get(i);
                last = new Point(event.x, event.y);
//                if (event.type == TouchEvent.TOUCH_DOWN) {
//                    state = GameState.GameOver;
//                }
            }
        }
    }

    /**
     * Initializes the main loading screen asset.
     */
    @Override
	protected void initializeAssets() {
        Graphics g = game.getGraphics();
        String key = "loading";
        if(Assets.getImage(key) == null){
            Image bgImage =  g.newImage(Assets.IMAGES_PATH + "/loading.png", Graphics.ImageFormat.RGB565);
            Assets.addImage(key, bgImage);
        }
	}
    MenuItem loadingScreen;
    @Override
    protected void setupEntities() {
        AndroidImage image = Assets.getAndroidImage("loading");
        loadingScreen = new MenuItem(image, 0, 0);
        new Thread(){
            @Override
            public void run(){
                initializeAllAssets();
            }
        }.start();
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

}
