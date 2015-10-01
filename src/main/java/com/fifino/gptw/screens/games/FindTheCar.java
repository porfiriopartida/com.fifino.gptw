package com.fifino.gptw.screens.games;

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
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by porfirioaprtida on 8/18/2015.
 */
public class FindTheCar extends GPTWScreen implements TouchAction {
    private Random rnd;
    private int totalCars, speed;
    MenuItemComposite carSprites;
    public FindTheCar(Game game, Integer level) {
        super(game, level);
        this.state = GameState.Running;
//        if(timer < 3){
//            timer = 3;
//        }
//        setMaxSeconds(timer);
    }

    HashMap<String, String> assets;
    @Override
    protected void initializeAssets() {
        assets = new HashMap<String, String>();
        Graphics g = game.getGraphics();
        //BG
        assets.put(GPTWResources.FIND_THE_CAR_BG, GPTWResources.FIND_THE_CAR_IMG_BG);
        //Loading all totalCars and signs (may need to use spritesheet instead)
        //SIGNS ~ 7+ May need to use text instead of images.
        for(int i=0;i<GPTWResources.FIND_THE_CAR_IMG_SIGNS.length;i++){
            assets.put(GPTWResources.FIND_THE_CAR_SIGN + i, GPTWResources.FIND_THE_CAR_IMG_SIGNS[i]);
        }
        //CARS ~ 3-4
        for(int i=0;i<GPTWResources.FIND_THE_CAR_IMG_CARS.length;i++){
            assets.put(GPTWResources.FIND_THE_CAR_CAR + i, GPTWResources.FIND_THE_CAR_IMG_CARS[i]);
        }
        this.initializeAssets(assets);
    }

    private String getSignImg(int idx){
        return GPTWResources.FIND_THE_CAR_SIGN + idx;
    }
    private int getRandomSignImgIdx(int level, int winner, boolean exclude){
        //First level shows up either LU15 or OXX0
        int poolSize = (level == 1 && !exclude) ? 2:GPTWResources.FIND_THE_CAR_IMG_SIGNS.length;
//        int poolSize = GPTWResources.FIND_THE_CAR_IMG_SIGNS.length;
        int rndIdx;
        do{
            rndIdx = rnd.nextInt(poolSize);
        }while(rndIdx == winner && exclude);
        return rndIdx; //GPTWResources.FIND_THE_CAR_SIGN + rndIdx;
    }

    private String getRandomCarImg(){
        //First level shows up either LU15 or OXX0
        int poolSize = GPTWResources.FIND_THE_CAR_IMG_CARS.length;
        int rndIdx = rnd.nextInt(poolSize);
        return GPTWResources.FIND_THE_CAR_CAR + rndIdx;
    }
    private void setupBackground(){
        this.bgImage = Assets.getAndroidImage(GPTWResources.FIND_THE_CAR_BG);
        MenuItem bgItem = new MenuItem(this.bgImage, 0, 0);
        bgItem.setCollidable(false);
        this.menuItems.add(bgItem); //bg should be first
    }
    int winnerIdx = -1;
    private void setupSign(){
        Graphics g = getGraphics();
        winnerIdx = getRandomSignImgIdx(this.level, winnerIdx, false);
        AndroidImage signImage = Assets.getAndroidImage(getSignImg(winnerIdx));
        //Center item and put near to the top inside white box
        MenuItem signItem = new MenuItem(signImage, g.getWidth()/2 - signImage.getWidth()/2, 140);
        signItem.setCollidable(false);
        this.menuItems.add(signItem); //bg should be first
    }
    private void setupCars(){
        if(totalCars <= 0){
           return;
        }
        Graphics g = getGraphics();
        AndroidImage carImage;
        CarSprite carSprite = null;
        String carName;
        carSprites = new MenuItemComposite(0, 0);
        int i;
        int winner = rnd.nextInt(totalCars);
        boolean success;
        MenuItem sign = null;
        AndroidImage signImage = null;
        String signImageName = null;
        int signIdx;
        try{
            for(i=0;i<totalCars;i++){
                success = i == winner;
                carName = getRandomCarImg();
                carImage = Assets.getAndroidImage(carName);
                //setting up cars to be around bottom centered
                //Sprite(String assetName, int x, int y) {
                carSprite = new CarSprite(carImage,
                        carName + i,
                        g.getWidth()/2 - carImage.getWidth()/2 + (i+1)*(carImage.getWidth() + (int)(carImage.getWidth()*.55f)), //1/2 of margin
                        g.getHeight() - carImage.getHeight() - 200,
                        this.speed);
                carSprite.setName(success ? "winner":carName);
                carSprite.addTouchListener(this); //register on touch
                carSprite.setAttribute("success", success);

                // MenuItem(AndroidImage image, int x, int y) {
                if(success){
                    signImageName = getSignImg(this.winnerIdx);
                } else {
                    signIdx = getRandomSignImgIdx(this.level, this.winnerIdx, true); // get any idx except the winner.
                    signImageName = getSignImg(signIdx);
                }
                signImage = (AndroidImage) Assets.getImage(signImageName, true);
                signImage.scale(245, 90); //white sign placeholder
                // Adding sign to car
                sign = new MenuItem(signImage, carSprite.getX() + carSprite.getWidth()/2 - signImage.getWidth()/2, carSprite.getY() + 205);
                carSprite.setSign(sign);
                // Adding all cars to pool of cars
                carSprites.add(carSprite);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        carSprite.setGame(this);
        carSprite.setIsLast(true);
        
    }
    @Override
    protected void setupEntities() {
        try {
            //Helpers
            Graphics g = this.game.getGraphics();
            this.rnd = new Random();
            int maxCars = 5,
                maxSpeed = 15;
            totalCars = 2 + this.level;
            speed = this.level * 2 + 7 ;
            if(speed > maxSpeed){
                speed = maxSpeed;
            }
            if(totalCars > maxCars){
                totalCars = maxCars;
            }
            //BG Setup

            //Entities setup
            setupBackground();
            setupSign();
            setupCars();

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
        //this.updateTime();
        this.menuItems.update(deltaTime);
        this.carSprites.update(deltaTime);

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
                    if(this.carSprites.collides(point)){
                        MenuItem item = this.carSprites.getLastCollision();
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
        this.carSprites.draw(g);
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