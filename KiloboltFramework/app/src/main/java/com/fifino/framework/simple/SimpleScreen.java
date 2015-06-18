package com.fifino.framework.simple;

import java.util.List;

import android.graphics.Color;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Graphics.ImageFormat;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidImage;

public class SimpleScreen extends Screen {
    private MenuItem simpleItem;
    private Game game;
    private String simple = "_simpleStart";
    public SimpleScreen(Game game) {
        super(game);
        this.game = game;
        initializeAssets();
        setupEntities();
    }

    @Override
    protected void setupEntities() {
    	AndroidImage image = (AndroidImage)Assets.getImage(simple);
        int x = 800/2 - image.getWidth()/2;
        int y = 1200/2 - image.getHeight()/2;
        simpleItem = new MenuItem(image, x, y);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
        	if(touchEvents.size() < len){
        		//Handles out of bounds exception for the list getting empty after getting the size.
        		return;
        	}
            TouchEvent event = touchEvents.get(i);
//            if (event.type == TouchEvent.TOUCH_UP) {
//                if (startMenuItem.collides(new Point(event.x, event.y))) {
//                  Do something nice!                
//                }
//            }
            simpleItem.setX(event.x);
            simpleItem.setY(event.y);
        }
    }
    
    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), g.getHeight(), Color.WHITE);
        simpleItem.draw(g);
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
        // Display "Exit Game?" Box
    }

    @Override
    protected void initializeAssets() {
        Graphics g = game.getGraphics();
        if(Assets.getImage(simple) == null){
            Image startImage =  g.newImage(Assets.IMAGES_PATH + "/simple.png", ImageFormat.RGB565);
        	Assets.addImage(simple, startImage);
        }
    }
}