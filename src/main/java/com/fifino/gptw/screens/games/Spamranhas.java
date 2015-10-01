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
import com.fifino.gptw.screens.games.spamranha.SpamSprite;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by porfirioaprtida on 8/18/2015.
 */
public class Spamranhas extends GPTWScreen implements TouchAction {
    private Random rnd;
    private int spamranhasCounter, asyncCounter;
    Point center;
    public Spamranhas(Game game, Integer level) {
        super(game, level);
        this.state = GameState.Running;
//        int curLvl = getGameLevel(level);
//        int timer = 6 - curLvl;
//        if(timer < 3){
//            timer = 3;
//        }
        //lv 1 = 2, lv 2 = 4, lv 3 = 6
//        setMaxSeconds(timer);
//        hintWaitSeconds = 1500;
    }

    HashMap<String, String> assets;
    ArrayList<SpamSprite> spams;
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
//        inboxItem.setCollidable(false);
        this.menuItems.add(inboxItem);
    }

    AndroidImage spamImg;
    private void setupSpamEmails(){
        spams = new ArrayList<SpamSprite>();
        //TODO: Add piranhas here, use the counter generated in the constructor, random position
        //TODO: Create the piranha sprite to update giving the center and move there
        spamImg =  Assets.getAndroidImage(GPTWResources.SPAMRANHAS_SPAM);
        int lv = level;
        spamranhasCounter = (int)(1 + lv * 1.8f);
        asyncCounter = 0;
        if(lv >= 7){
            asyncCounter = 3;
        } else if(lv >= 5){
            asyncCounter = 2;
        } else if(lv >= 3){
            asyncCounter = 1;
        }
//        spamranhasCounter = 10;
        String name = "spamranha";
        SpamSprite spamranha;
        Graphics g = getGraphics();
        int x,y;
        int cx = g.getWidth()/2, cy = g.getHeight()/2;
        for(int i = 0; i<= this.asyncCounter; i++){
            spamranha = new SpamSprite(spamImg, name + i, lv);
            spamranha.setCenter(cx, cy); //For direction
            spamranha.setMaxHeight(g.getHeight());
            spamranha.setMaxWidth(g.getWidth());
            spamranha.setScreen(this);
            spamranha.reset();
            this.menuItems.add(spamranha);
            spams.add(spamranha);
        }

    }
    private int getSpamWidth(){
        return this.spamImg.getWidth();
    }
    private int getSpamHeight(){
        return this.spamImg.getHeight();
    }

    @Override
    protected void setupEntities() {
        try {
            Graphics g = getGraphics();
            rnd = new Random();
            //Entities setup
            setupBackground();
            setupInbox();
            setupSpamEmails();
            center = new Point(g.getWidth()/2, g.getHeight()/2);
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
            //Check touch events
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    break;
                }
                event = touchEvents.get(i);
                Point point = new Point(event.x, event.y);
                if (event.type == Input.TouchEvent.TOUCH_DOWN) {
                    if(menuItems.collides(point)){
                        MenuItem collidedItem = menuItems.getLastCollision();
                        if(collidedItem instanceof SpamSprite){
                            SpamSprite clickedItem = (SpamSprite)collidedItem;
                            //do something on touch down...
                            spamranhasCounter--;
                            if(spamranhasCounter <= 0){
                                win();
                            } else {
                                clickedItem.reset();
                            }
                        }
                    }
                }
            }
        }
        for(SpamSprite spr : spams){
            if(spr.collides(inboxItem)){
                lose();
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