package com.fifino.gptw.screens.games;

import android.graphics.Point;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.Sprite;
import com.fifino.framework.events.TouchAction;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.MiddleScreen;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by ppartida on 6/23/15.
 */
public class TurnOffAirs extends GPTWScreen implements TouchAction {
    private AndroidImage bgImage;
    private Random rnd;
    protected ArrayList<Sprite> activeElements;
    public TurnOffAirs(Game game, Integer level) {
        super(game);
        int timer = 6 - getDifficulty(level);
        if(timer < 3){
            timer = 3;
        }
        setMaxSeconds(timer);
    }

    public int getDifficulty(int level){
        return level/3;
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
                    if(this.menuItems.collides(point)){
                        MenuItem item = this.menuItems.getLastCollision();
                        if (item instanceof Sprite) {
                            Sprite sprite = (Sprite)item;
                            sprite.triggerTouchEvent();
                        }
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
    }
    protected String getBackgroundKey(){
        return "game_1_bg";
    }
    HashMap<String, String> assets;
    @Override
    protected void initializeAssets() {
        assets = new HashMap<String, String>();
        Graphics g = game.getGraphics();
        String imagesPath = Assets.IMAGES_PATH;
        assets.put("game_1_bg", imagesPath + "/screens/games/airs.png");
        assets.put("game_2_bg", imagesPath + "/screens/games/alarms.png");
        assets.put("sprite_1_1", imagesPath + "/sprites/air.png");
        assets.put("sprite_1_2", imagesPath + "/sprites/alarm.png");
        Set<String> keys = assets.keySet();
        for(String key:keys){
            if(Assets.getImage(key) == null){
                Image bgImage =  g.newImage(assets.get(key), Graphics.ImageFormat.RGB565);
                Assets.addImage(key, bgImage);
            }
        }
    }
    protected void clean(){
        Set<String> keys = assets.keySet();
        for(String key:keys){
            if(Assets.getImage(key) != null){
                Assets.remove(key);
            }
        }
    }
    //AndroidImage airImage, alarmImage;
    @Override
    protected void setupEntities() {
        try {
            Graphics g = this.game.getGraphics();
            this.rnd = new Random();
            this.state = GameState.Running;
            this.bgImage = Assets.getAndroidImage(getBackgroundKey());
            MenuItem bgItem = new MenuItem(this.bgImage, 0, 0);
            bgItem.setCollidable(false);
            this.menuItems.add(bgItem);
            Sprite sprite = null;
            int x, y = 60, midX, thirdY;
            midX = g.getWidth()/2;
            thirdY = (g.getHeight() - 65 - 5 - 40)/3;
            this.activeElements = new ArrayList<Sprite>();
            for(int i=0; i<3;i++){
                y = 65+(thirdY+5)*i;
                for(int j=0; j<2;j++){
                    sprite = getRandomSprite();
                    x = midX*j + midX/2 - sprite.getWidth()/2;
                    sprite.setX(x);
                    sprite.setY(y);
                    activeElements.add(sprite);
                    this.menuItems.add(sprite);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    private Sprite getRandomSprite(){
        Sprite sprite = null;
        boolean isAir = rnd.nextBoolean();
        boolean isOn = rnd.nextBoolean();
        try {
            if(isAir){
                //public Sprite(String assetName, int x, int y, int width, int height) {
                sprite =  new Sprite("sprite_1_1", 0, 0, 230, 180);
                sprite.setName("air");
                sprite.registerAnimation("idle", 0, 2);
                sprite.registerAnimation("on", 1, 2);
                sprite.setAttribute("status", isOn);
                sprite.playAnimation(isOn ? "on" : "idle");
                sprite.addTouchListener(this);
            }else{
                sprite =  new Sprite("sprite_1_2", 0, 0, 142, 107);
                sprite.setName("alarm");
                sprite.registerAnimation("idle", 0, 2);
                sprite.registerAnimation("on", 1, 2);
                sprite.setAttribute("status", isOn);
                sprite.playAnimation(isOn ? "on" : "idle");
                sprite.addTouchListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprite;
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
    public void triggerTouch(String eventName, Object context) {
        triggerTouch(context);
    }

    @Override
    protected void checkTimeIsZero(){
        int errorState = 0;
        String stateName = "airs-";
        for(Sprite sprite:activeElements){
            boolean isAir = "air".equals(sprite.getName());
            boolean isOn = (boolean) sprite.getAttribute("status");
            if(isAir){
                if(isOn){
                    errorState = 1;
                    break;
                }
            } else {
                if(!isOn){
                    stateName = "alarms-";
                    errorState = 1;
                    break;
                }
            }
        }
        String bgName = stateName + errorState;
        this.game.setScreen(new MiddleScreen(this.game, bgName));
        this.clean();
    }
    @Override
    public void triggerTouch(Object context) {
        Sprite sprite = (Sprite)context;
        boolean isOn = (boolean) sprite.getAttribute("status");
        sprite.setAttribute("status", !isOn);
        try {
            sprite.playAnimation(!isOn ? "on" : "idle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
