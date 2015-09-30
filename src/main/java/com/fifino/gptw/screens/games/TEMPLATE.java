package com.fifino.gptw.screens.games;

import android.graphics.Point;

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

import java.util.HashMap;
import java.util.List;

/**
 * Created by porfirioaprtida on 8/18/2015.
 */
public class TEMPLATE extends GPTWScreen implements TouchAction {
    MenuItemComposite carSprites;
    public TEMPLATE(Game game, Integer level) {
        super(game, level);
        this.state = GameState.Running;
//        setMaxSeconds(timer);
//        hintWaitSeconds = 1500;
    }

    HashMap<String, String> assets;
    @Override
    protected void initializeAssets() {
        assets = new HashMap<String, String>();
//        assets.put(GPTWResources.FIND_THE_CAR_BG, GPTWResources.FIND_THE_CAR_IMG_BG);
        this.initializeAssets(assets);
    }
    private void setupBackground(){
//        this.bgImage = Assets.getAndroidImage(GPTWResources.FIND_THE_CAR_BG);
//        MenuItem bgItem = new MenuItem(this.bgImage, 0, 0);
//        bgItem.setCollidable(false);
//        this.menuItems.add(bgItem); //bg should be first
    }
    @Override
    protected void setupEntities() {
        try {
            //Entities setup
            setupBackground();
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
        CarSprite sprite = (CarSprite)context;
        if("winner".equalsIgnoreCase(sprite.getName())){
            win();
        } else {
            lose(false);
        }
    }
    public void win(){
        this.clean(assets);
        int score = getScore();
        String res = GPTWResources.FIND_THE_CAR_WIN;
        GPTWTransition transition = new GPTWTransition(score, score + 100, true, 0);
        buildMiddleScreen(res, transition);
    }
    public void lose(boolean timeout){
        this.clean(assets);
        int score = getScore();
        GPTWTransition transition = new GPTWTransition(score, score + ( timeout ? -10:-25), false, 0);
        String res = timeout ? GPTWResources.FIND_THE_CAR_LOSE_2: GPTWResources.FIND_THE_CAR_LOSE_1;
        buildMiddleScreen(res, transition);
    }
    public void buildMiddleScreen(String res, GPTWTransition transition){
        this.game.setScreen(new MiddleScreen(this.game, res, transition));
    }
}