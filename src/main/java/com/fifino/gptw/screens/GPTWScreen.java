package com.fifino.gptw.screens;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItemComposite;
import com.fifino.gptw.GPTWGame;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.framework.implementation.AndroidInput;

public abstract class GPTWScreen extends Screen{
	protected GameState state = GameState.Running;

    AndroidInput input;
    protected int maxSeconds = 0, currentSeconds = 0;
    protected MenuItemComposite menuItems;
    protected AndroidImage bgImage;
    protected String name = "noname"; 
    protected boolean isPortrait = true;
    protected Paint paintB, paintW, paintBT;
    protected int level, gamesCounter;
    public enum GameState {
        Ready, Running, Paused, GameOver
    }
    public boolean isPortrait() {
        return isPortrait;
    }
    public void setGameState(GameState state){
        this.state = state;
    }
    public void setPortrait(boolean portrait) {
        this.isPortrait = portrait;
    }

    public GPTWScreen(Game game){
        this(game, -1);
    }
    public GPTWScreen(Game game, Integer gamesCounter) {
        super(game);
        this.gamesCounter = gamesCounter;
        this.level = getGameLevel(gamesCounter);
        menuItems = new MenuItemComposite(0, 0); 
        //bgImage =  (AndroidImage)Assets.getImage(Assets.IMAGES_PATH + "/main/gray-bg.png");
        paintB = getPaint();
        paintW = getPaint();
        paintBT = getPaint();
        paintBT.setColor(Color.BLACK);
        paintBT.setAlpha(200);
        paintW.setColor(Color.WHITE);
        input = (AndroidInput) game.getInput();
        postConstruct();
    }

    protected int getGameLevel(int gamesCount){
        int maxLevel = 5;
        if(gamesCount <= 3){
            return 1;
        } else if(gamesCount <= 5){
            return 2;
        } else if(gamesCount <= 10){
            return 3;
        } else {
            return maxLevel;
        }
    }
    protected Graphics getGraphics(){
        return this.game.getGraphics();
    }
    protected void postConstruct(){
        initializeAssets();
        setupEntities();
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
    protected void clean(HashMap<String, String> assets){
        Set<String> keys = assets.keySet();
        AndroidImage img = null;
        for(String key:keys){
            img = (AndroidImage) Assets.getImage(key);
            if(img != null){
                Assets.remove(key);
                img.getBitmap().recycle();
            }
        }
    }

    protected void checkTimeIsZero(){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.game.setScreen(getNextScreen());
    }
    protected void checkScreenState(){
        if(this.currentSeconds <= 0){
            this.drawBar(game.getGraphics());
            checkTimeIsZero();
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
    protected void drawBar(Graphics g){
        if(this.currentSeconds >= 0 && this.maxSeconds > 0) {
            int rate = 100 * this.currentSeconds / this.maxSeconds;
//            System.out.println("Rate: " + rate);
            g.drawRect(0, 0, g.getWidth() * rate / 100, 50, paintBT);
        }
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
    public int getMaxSeconds() {
        return maxSeconds;
    }
    public int getSeconds() {
        return currentSeconds;
    }

    public void setMaxSeconds(int maxSeconds) {
        maxSeconds *= 10;
        this.maxSeconds = maxSeconds;
        this.currentSeconds = maxSeconds;
        lastTime = getTimestamp();
    }
    protected long getTimestamp(){
        return Calendar.getInstance().getTimeInMillis();
    }
    long lastTime;
    protected void updateTime(){
        if(this.maxSeconds <= 0){
            return;
        }
        long diff = getTimestamp() - lastTime;
        if(diff > 100) {
            //Update every second.
            this.currentSeconds -= (diff/100);
            lastTime = getTimestamp();
        }
        checkScreenState();
    }
    protected Screen getNextScreen(){
        return ((GPTWGame)game).getNextScreen();
    }
//	@Override
//	public void backButton() {
//		System.exit(0);
//	}
}
