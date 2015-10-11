package com.fifino.gptw.screens.games;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.MenuItemComposite;
import com.fifino.framework.entities.Sprite;
import com.fifino.framework.events.TouchAction;
import com.fifino.gptw.helpers.GPTWResources;
import com.fifino.gptw.helpers.GPTWTransition;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.MiddleScreen;
import com.fifino.gptw.screens.games.find_the_car.CarSprite;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by porfirioaprtida on 8/18/2015.
 */
public class WakeUp extends GPTWScreen {
    int hits, hitsCounter = 0;
    Paint hpBarBg, hpBarColor;
    public WakeUp(Game game, Integer level) {
        super(game, level);
        this.state = GameState.Running; // avoids hint rendering.
        int curLvl = getGameLevel(level);
        int timer = 10 - curLvl;
        if(timer < 3){
            timer = 3;
        }
        hits = curLvl * 3 + 1;
        setMaxSeconds(timer);
//        hintWaitSeconds = 1500;
    }

    HashMap<String, String> assets;
    @Override
    protected void initializeAssets() {
        assets = new HashMap<String, String>();
        assets.put(GPTWResources.WAKE_UP_BG, GPTWResources.WAKE_UP_IMG_BG);
        this.initializeAssets(assets);
    }
    private void setupBackground(){
        this.bgImage = Assets.getAndroidImage(GPTWResources.WAKE_UP_BG);
        MenuItem bgItem = new MenuItem(this.bgImage, 0, 0);
        bgItem.setCollidable(false);
        this.menuItems.add(bgItem); //bg should be first
    }
    private void setupHpBar(){
        hpBarBg = getPaint();
        hpBarBg.setColor(Color.BLACK);
        hpBarColor = getPaint();
        hpBarColor.setColor(Color.YELLOW);
    }
    @Override
    protected void setupEntities() {
        try {
            //Entities setup

            //We only have a bg here this counts hits for now.
            setupBackground();
            setupHpBar();

//            //Initial state
//            this.state = GameState.Running;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
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
    protected void updateRunning(List<Input.TouchEvent> touchEvents, float deltaTime) {
        this.updateTime();
        this.menuItems.update(deltaTime);

        int len = touchEvents.size();
        Input.TouchEvent event = null;
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    return;
                }
                event = touchEvents.get(i);
                Point point = new Point(event.x, event.y);
                if (event.type == Input.TouchEvent.TOUCH_DOWN) {
                    //do something on touch down...
                    hitsCounter++;
                    if(hitsCounter >= hits){
                        win();
                    }
                }
            }
        }
    }
    @Override
    public void drawRunningUI(List<Input.TouchEvent> touchEvents, float deltaTime){
        Graphics g = game.getGraphics();
        this.menuItems.draw(g);
        this.drawBar(g);
        this.drawHPBar(g);
    }
    protected void drawHPBar(Graphics g){
        float rate = (float)(hits-hitsCounter) / hits;
        int border = 2,
            x = border,
            y = 80,
            w = (int)((g.getWidth() - border * 2) * rate),
            h = 50;

//        System.out.println("rate: " + rate + " -- w: " + w);

        g.drawRect(x, y - border, g.getWidth(), h, hpBarBg);
        g.drawRect(x + border, y, w, h - border, hpBarColor);
    }

    public void win(){
        this.clean(assets);
        int score = getScore();
        String res = GPTWResources.WAKE_UP_WIN;
        GPTWTransition transition = new GPTWTransition(score, score + 100, true, 0);
        buildMiddleScreen(res, transition);
    }
    public void lose(){
        this.clean(assets);
        int score = getScore();
        GPTWTransition transition = new GPTWTransition(score, score - 20, false, 0);
        String res = GPTWResources.WAKE_UP_LOSE;
        buildMiddleScreen(res, transition);
    }
    public void buildMiddleScreen(String res, GPTWTransition transition){
        this.game.setScreen(new MiddleScreen(this.game, res, transition));
    }
    @Override
    protected void triggerTimeIsZero() {
        lose();
    }
}