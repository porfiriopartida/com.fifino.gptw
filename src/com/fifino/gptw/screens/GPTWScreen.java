package com.fifino.gptw.screens;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Paint;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItemComposite;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidImage;

public abstract class GPTWScreen extends Screen{
	protected GameState state = GameState.Ready;
	protected Random rnd = new Random();
    public enum GameState {
        Ready, Running, Paused, GameOver
    }
    protected MenuItemComposite menuItems;
    protected AndroidImage bgImage;
    protected String name = "noname"; 
    protected boolean isPortrait = true;
    protected Paint paintB, paintW;
    public boolean isPortrait() {
        return isPortrait;
    }
    public void setGameState(GameState state){
        this.state = state;
    }
    public void setPortrait(boolean portrait) {
        this.isPortrait = portrait;
    }
    public GPTWScreen(Game game) {
        super(game);
        menuItems = new MenuItemComposite(0, 0); 
        bgImage =  (AndroidImage)Assets.getImage(Assets.IMAGES_PATH + "/main/gray-bg.png");
        paintB = getPaint();
        paintW = getPaint();
        paintW.setColor(Color.WHITE);
    }
    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        if (state == GameState.Ready) {
            updateReady(touchEvents);
        } else if (state == GameState.Running) {
            updateRunning(touchEvents, deltaTime);
        } else if (state == GameState.Paused) {
            updatePaused(touchEvents, deltaTime);
        } else if (state == GameState.GameOver) {
            updateGameOver(touchEvents);
        }
    }
    protected abstract void updateRunning(List<TouchEvent> touchEvents, float deltaTime);
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
    protected void updateReady(List<TouchEvent> touchEvents) {
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
                    state = GameState.Running;
                }
            }
        }
    }
    protected void updatePaused(List<TouchEvent> touchEvents, float deltaTime) {
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
                    state = GameState.Running;
                }
            }
        }
    }
    public static Paint getPaint() {
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        return paint;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public void start(){ }

//    @Override
//    public void paint(float deltaTime) {
//        Graphics g = game.getGraphics();
//        g.fillRect(0, 0, g.getWidth(), g.getHeight(), Color.WHITE);
//        menuItems.draw(g);
//    }
    public void paint(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        if (state == GameState.Ready) {
            drawReadyUI(touchEvents, deltaTime);
        } else if (state == GameState.Running) {
            drawRunningUI(touchEvents, deltaTime);
        } else if (state == GameState.Paused) {
            drawPausedUI(touchEvents);
        } else if (state == GameState.GameOver) {
            drawGameOverUI(touchEvents);
        }
    }
    protected void drawGameOverUI(List<TouchEvent> touchEvents) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
        g.drawString("Nothing implemented yet.", g.getWidth() / 2 - 25, 40,
                paintB);
	}
    protected void drawRunningUI(List<TouchEvent> touchEvents, float deltaTime) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
        g.drawString("Nothing implemented yet.", g.getWidth() / 2 - 25, 40,
                paintB);
		
	}
    protected void drawPausedUI(List<TouchEvent> touchEvents) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
        g.drawString("GAME PAUSED - Tap to Resume", g.getWidth() / 2 - 25, 40,
                paintB);
    }
    protected void drawReadyUI(List<TouchEvent> touchEvents, float deltaTime) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
        g.drawString("Nothing implemented yet.", g.getWidth() / 2 - 25, 40,
                paintB);		
	}
//	@Override
//	public void backButton() {
//		System.exit(0);
//	}

    
//    public void setOrientation(){
//        EpuEpoGame act = EpuEpoGame.get();
//        boolean isPortrait =  act.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
//        EpuEpoGame.LAST_SCREEN = this;
//        if(!isPortrait && this.isPortrait){
//            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else if(isPortrait && !this.isPortrait){
//            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
//    }
}
