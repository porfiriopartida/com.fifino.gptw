package com.fifino.gptw.screens.games.spamranha;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.fifino.framework.entities.Sprite;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.games.Spamranhas;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by porfiriopartida on 9/30/2015.
 */
public class SpamSprite extends Sprite {
    int stepSpeed, auxSpeed;
    int centerX, centerY, startX, startY;
    int maxW, maxH, auxAxis;
    Spamranhas game;
    Random rnd;
    SpamState state = SpamState.Ready;
    AndroidImage spriteImage;
    From from;
    public SpamSprite(AndroidImage asset, String spriteName, int stepSpeed) {
        super(asset, spriteName, 0, 0);
        this.stepSpeed = stepSpeed;
        auxSpeed = 20;
        spriteImage = asset;
        rnd = new Random();
    }
    protected void generateBound(){
        int width = image.getWidth();
        int height = image.getHeight();
        Bound b = new Bound();
        b.setEntity(this);
        Rectangle rectangle = new Rectangle(b);
        int margin = 10;
//        rectangle.setHeight(height).setWidth(width);
        rectangle.setX(-margin).setY(-margin).setHeight(height+margin*2).setWidth(width+margin*2);
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);
        b.setRectangles(rectangles);
        setBound(b);
    }
    public void setGame(Spamranhas game){
        this.game = game;
    }
    public void setCenter(int x, int y){
        this.centerX = x;
        this.centerY = y;
    }
    //TODO: Add moving to center animation - manage state?
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(state == SpamState.Ready){
            setVisible(true);
            setCollidable(true);
            state = SpamState.Align;
        } else if(state == SpamState.Align){
            align();
        } else if(state == SpamState.Moving){
            moving();
         }else if(state == SpamState.Paused){
            checkPaused();
        } else if(state == SpamState.Dead){
            setVisible(false);
            setCollidable(false);
        }
    }
    Spamranhas scr;
    public void setScreen(Spamranhas scr){
        this.scr = scr;
    }
    long lastTime;
    private void checkPaused(){
        if(scr.getTimestamp() - lastTime > 800){
            state = SpamState.Moving;
        }
    }

    /**
     * Moves from the aux alignment to the center
     */
    private void moving(){
        int div;
//        switch (stepSpeed){
//            case 1:
//                div = 100;
//                break;
//            case 2:
//                div = 90;
//                break;
//            case 3:
//                div = 80;
//                break;
//            case 4:
//                div = 70;
//                break;
//            default:
//                div = 50;
//                break;
//        }
        div = 100;
        int dx = startX - centerX,
            dy = startY - centerY,
            sx = Math.abs(dx/div),
            sy = Math.abs(dy/div);
        if(x > centerX){
            x -= sx;
        } else {
            x += sx;
        }
        if(y > centerY){
            y -= sy;
        } else {
            y += sy;
        }
    }
    /**
     * Moves from outside the box into the aux inner rect.
     */
    private void align(){
        if(from == From.RIGHT){
            this.x -= auxSpeed;
            if(x <= auxAxis){
                state = SpamState.Paused;
            }
        } else if(from == From.LEFT){
            this.x += auxSpeed;
            if(x >= auxAxis){
                state = SpamState.Paused;
            }
        } else if(from == From.TOP){
            this.y += auxSpeed;
            if(y <= auxAxis){
                state = SpamState.Paused;
            }
        } else if(from == From.BOTTOM){
            this.y -= auxSpeed;
            if(y >= auxAxis){
                state = SpamState.Paused;
            }
        }
        if(state == SpamState.Paused){
            startX = x;
            startY = y;
            lastTime = getTimestamp();
        }
    }
    private long getTimestamp(){
        return scr.getTimestamp();
    }
    public void setMaxHeight(int h){
        this.maxH = h;
    }
    public void setMaxWidth(int w){
        this.maxW = w;
    }
    public void reset() {
        int[] xy = getRandomXY(maxW, maxH);
        state = SpamState.Ready;
        x = xy[0];
        y = xy[1];
    }

    private int[] getRandomXY(int maxW, int maxH){
        int x, y;

        //Checks if Horizontal or Vertical out of screen
        boolean isHor = rnd.nextBoolean();

        //Check if left/top OR right/bottom
        boolean before = rnd.nextBoolean();

        //Helpers using the dimensions of the spam email sprite
        int w = spriteImage.getWidth(),
            h = spriteImage.getHeight();

        if(isHor){
            //Can be out of the screen from left or right, then random Y
            if(before){
                //Out from the left
                 x = -w;
                from = From.LEFT;
                auxAxis = (int) (maxW * 0.15);
            } else {
                //Out from the right
                 x = maxW;
                from = From.RIGHT;
                auxAxis = (int) (maxW * 0.85);
            }
            y = rnd.nextInt(maxH);
        } else {
            //Can be out of the screen from top or bottom, then random X
            x = rnd.nextInt(maxW);
            if(before){
                //Out from the top
                y = -h;
                from = From.TOP;
                auxAxis = (int) (maxH * 0.30);
            } else {
                //Out from the bottom
                y = maxH;
                from = From.BOTTOM;
                auxAxis = (int) (maxH * 0.70);
            }
        }
        return new int[]{x, y};
    }
    enum SpamState {
        Ready, //Stand by?
        Align, //Moving to the Aux
        Moving, //Moving to the center
        Paused, // Should stay there
        Dead
    };
    enum From{
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
}