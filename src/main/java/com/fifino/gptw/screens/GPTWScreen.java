package com.fifino.gptw.screens;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.graphics.Color;
import android.graphics.Paint;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItemComposite;
import com.fifino.framework.helpers.GameFactory;
import com.fifino.gptw.GPTWGame;
import com.fifino.gptw.flags.AutoRun;
import com.fifino.gptw.helpers.GPTWHint;
import com.fifino.gptw.helpers.GPTWTransition;
import com.fifino.gptw.helpers.RandomGPTWGameFactory;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidImage;
import com.kilobolt.framework.implementation.AndroidInput;

public abstract class GPTWScreen extends Screen{
	protected GameState state = GameState.Ready;

    AndroidInput input;
    protected int maxSeconds = 0, currentSeconds = 0;
    protected MenuItemComposite menuItems;
    protected AndroidImage bgImage;
    protected String name = "noname"; 
    protected boolean isPortrait = true;
    protected Paint paintB, paintW, paintBT, paintBG;
    protected int difficulty, level, readyBg;

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
        if(this instanceof AutoRun && !GPTWGame.TEST){
            initAndroidComponents();
        }
    }
    public GPTWScreen(Game game, Integer level) {
        super(game);
//        if(level < 0){
//            throw new RuntimeException("Invalid level: " + level);
//        }
        this.level = level;
        this.difficulty = getGameDifficulty(level);
        menuItems = new MenuItemComposite(0, 0);
        //bgImage =  (AndroidImage)Assets.getImage(Assets.IMAGES_PATH + "/main/gray-bg.png");
        input = (AndroidInput) game.getInput();
        readyTime = getTimestamp();
    }

    /**
     * ANything that uses this will require instrumenstation as it loads android components.
     */
    public void initAndroidComponents(){
        postConstruct();

        readyBg = Color.argb(255, 255, 255, 255);

        paintB = getPaint();
        paintW = getPaint();
        paintBT = getPaint();
        paintBT.setColor(Color.BLACK);
        paintBT.setAlpha(200);
        paintBG = getPaint();
        paintBG.setColor(Color.BLACK);
        paintBG.setTextSize(100);
        paintBG.setTextAlign(Paint.Align.CENTER);
        paintW.setColor(Color.WHITE);
    }
    boolean debug = true;
    public int getGameDifficulty(int level){
        if(debug){
            return 1;
        }
        int maxLevel = 8;
        if(level <= 2){
            return 1;
        } else if(level <= 4){
            return 2;
        } else if(level <= 6){
            return 3;
        } else if(level <= 8){
            return 4;
        } else if(level <= 10){
            return 5;
        } else if(level <= 12){
            return 6;
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
    protected void initializeAssets(HashMap<String, String> assets) {
        Graphics g = getGraphics();
        Set<String> keys = assets.keySet();
        if(g == null){
            throw new RuntimeException("Graphics cannot be null");
        }
        if(assets == null){
            throw new RuntimeException("Assets cannot be null");
        }
        for(String key:keys){
            //if(Assets.getImage(key) == null){
                Image bgImage =  g.newImage(assets.get(key), Graphics.ImageFormat.RGB565);
                Assets.addImage(key, bgImage);
            //}
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

    protected void triggerTimeIsZero(){
        throw new UnsupportedOperationException("Not implemented");
    }
    protected void checkScreenState(){
        if(this.currentSeconds <= 0){
            this.drawBar(game.getGraphics());
            triggerTimeIsZero();
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
    protected long readyTime = 0;
    protected int bgFontAlpha = 255;
    protected int hintWaitSeconds = 1500, hintWaitSecondsLow = 1000;
    protected void updateReady(List<TouchEvent> touchEvents) {
        long diff = getTimestamp() - readyTime;
        boolean force = false;
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
                    force = true;
                }
            }
        }
        if(diff > hintWaitSeconds || force) {
            setGameState(GameState.Running);
            lastTime = getTimestamp();
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
    protected void drawReadyUI(List<TouchEvent> touchEvents, float deltaTime) {
        drawReady();
    }
    protected void drawReady(){
        Graphics g = game.getGraphics();
        GPTWHint[] hints = getHints();
        if(hints == null){
            return;
        }
        int hintHeight = 80;
        int hintMargin = 80;
        int moved = moved = hints.length % 2 == 0 ? hints.length/2:(hints.length+1)/2;

        int y = g.getHeight()/2 - hintHeight*moved - hintMargin*moved;
        Paint bgFont = getBackgroundFont();
        int x =  g.getWidth() / 2;
        int readyBgAlpha = this.bgFontAlpha - 150;
        if(readyBgAlpha <= 0){
            readyBgAlpha = 0;
        }
        g.fillRect(0, y - 10, g.getWidth(), (hintHeight + hintMargin) * hints.length, Color.argb(readyBgAlpha, 255, 255, 255));

        for(GPTWHint hint : hints ){
            bgFont.setColor(hint.getColor());
            bgFont.setAlpha(this.bgFontAlpha);
            g.drawString(hint.getHint(), x, y, bgFont);
            y += hintHeight + hintMargin;
        }
    }
    protected void drawRunningUI(List<TouchEvent> touchEvents, float deltaTime) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
        g.drawString("Nothing implemented yet.", g.getWidth() / 2 - 25, 40,
                paintB);
    }
    protected void drawGameOverUI(List<TouchEvent> touchEvents) {
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
    public GPTWHint[] getHints(){
        return null;
    }
    public int getBackgroundColor(){
        return Color.WHITE;
    }
    public Paint getBackgroundFont(){
        return paintBG;
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
    public long getTimestamp(){
        return Calendar.getInstance().getTimeInMillis();
    }
    protected long lastTime;
    protected long getDiff(){
        return getTimestamp() - lastTime;
    }
    protected float getPercentTimer(){
        if(this.currentSeconds <= 0 || this.maxSeconds <= 0){
            return 0;
        }
        return (float)this.currentSeconds/this.maxSeconds;
    }
    protected void updateTime(){
        if(this.maxSeconds <= 0){
            return;
        }
        long diff = getDiff();
        if(diff > 100) {
            if(diff > 200){
                //Adding a lock of 100 ms in case of pause
                diff = 200;
            }
            //Update every second.
            this.currentSeconds -= (diff/100);
            lastTime = getTimestamp();
        }
        checkScreenState();
    }
    protected Screen getNextScreen(){
        GPTWGame gptwGame = (GPTWGame) game;
        gptwGame.levelUp();
        //int level = gptwGame.getLevel();
        GameFactory factory = RandomGPTWGameFactory.getInstance(gptwGame);
        return factory.getNextScreen();
    }
    protected Screen getScoreScreen(GPTWTransition transition){
        return new ScoreScreen(this.game, transition);
    }
    public int getScore(){
        return this.game.getScore();
    }
	@Override
	public void backButton() {
        System.exit(0);
//        if(this.state == GameState.Running){
//            setGameState(GameState.Paused);
//        } else if(this.state == GameState.Paused){
//            setGameState(GameState.Running);
//        } else {
//            System.exit(0);
//        }
	}
}
