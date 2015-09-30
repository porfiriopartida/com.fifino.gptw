package com.fifino.gptw.screens.games;

import android.graphics.Point;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.MenuItemComposite;
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

/**
 * Created by porfirioaprtida on 8/18/2015.
 */
public class Spamranhas extends GPTWScreen implements TouchAction {
    int spamranhasCounter = 5;
    public Spamranhas(Game game, Integer level) {
        super(game, level);
        this.state = GameState.Running;
        int curLvl = getGameLevel(level);
        int timer = 6 - curLvl;
        if(timer < 3){
            timer = 3;
        }
        //lv 1 = 2, lv 2 = 4, lv 3 = 6
        spamranhasCounter = (int)(1 + getGameLevel(level) * 1.7f);
        spamranhasCounter = 1;
        setMaxSeconds(timer);
//        hintWaitSeconds = 1500;
    }

    HashMap<String, String> assets;
    @Override
    protected void initializeAssets() {
        assets = new HashMap<String, String>();
        assets.put(GPTWResources.SPAMRANHAS_BG, GPTWResources.SPAMRANHAS_IMG_BG);
        assets.put(GPTWResources.SPAMRANHAS_INBOX, GPTWResources.SPAMRANHAS_INBOX_IMG);
        assets.put(GPTWResources.SPAMRANHAS_SPAM, GPTWResources.SPAMRANHAS_SPAM_IMG);
        this.initializeAssets(assets);
    }
    private void setupBackground(){
        this.bgImage = Assets.getAndroidImage(GPTWResources.SPAMRANHAS_BG);
        MenuItem bgItem = new MenuItem(this.bgImage, 0, 0);
        bgItem.setCollidable(false);
        this.menuItems.add(bgItem); //bg should be first
    }
    MenuItem inboxItem;
    private void setupInbox(){
        Graphics g = getGraphics();
        AndroidImage inboxImage = Assets.getAndroidImage(GPTWResources.SPAMRANHAS_INBOX);
        int x = g.getWidth()/2 - inboxImage.getWidth()/2 , y = g.getHeight()/2 - inboxImage.getHeight()/2;
        inboxItem = new MenuItem(inboxImage, x, y);

        this.menuItems.add(inboxItem);
    }
    private void setupSpamEmails(){
        //TODO: Add piranhas here, use the counter generated in the constructor, random position
        //TODO: Create the piranha sprite to update giving the center and move there
//        this.menuItems.add(inboxItem);
    }
    @Override
    protected void setupEntities() {
        try {
            //Entities setup
            setupBackground();
            setupInbox();
            setupSpamEmails();
            //Initial state
            this.state = GameState.Running;
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
                    spamranhasCounter--;
                    if(spamranhasCounter <= 0){
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
        //this.drawBar(g);
    }

    public void triggerTouch(String eventName, Object context) {
        this.triggerTouch(context);
    }

    @Override
    public void triggerTouch(Object context) {
        //TODO: Handle click to piranhas
    }
    public void win(){
        this.clean(assets);
        int score = getScore();
        String res = GPTWResources.SPAMRANHAS_WIN;
        GPTWTransition transition = new GPTWTransition(score, score + 110, true, 0);
        buildMiddleScreen(res, transition);
    }

    //TODO: Remove this since you will lose on contact
    @Override
    protected void triggerTimeIsZero() {
        lose();
    }

    public void lose(){
        this.clean(assets);
        int score = getScore();
        GPTWTransition transition = new GPTWTransition(score, score - 15, false, 0);
        String res = GPTWResources.SPAMRANHAS_LOSE;
        buildMiddleScreen(res, transition);
    }
    public void buildMiddleScreen(String res, GPTWTransition transition){
        this.game.setScreen(new MiddleScreen(this.game, res, transition));
    }
}