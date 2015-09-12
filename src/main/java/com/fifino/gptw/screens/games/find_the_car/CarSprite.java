package com.fifino.gptw.screens.games.find_the_car;

import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.Sprite;
import com.fifino.gptw.screens.games.FindTheCar;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.implementation.AndroidImage;

/**
 * Created by porfiriopartida on 9/12/2015.
 */
public class CarSprite extends Sprite {
    int speedX;
    boolean isLast = false;
    FindTheCar game;
    public CarSprite(AndroidImage asset, String assetName, int x, int y, int speed) {
        super(asset, assetName, x, y);
        this.speedX = speed;
    }
    public void setGame(FindTheCar game){
        this.game = game;
    }
    public void setIsLast(boolean isLast){
        this.isLast = isLast;
    }
    public void update(float deltaTime) {
        this.x -= this.speedX;
        sign.setX(sign.getX() - this.speedX);
        if(this.x < 0 - this.getWidth()){
            this.setVisible(false);
            if(isLast){
                game.lose();
            }
            return;
        }
        super.update(deltaTime);
        sign.update(deltaTime);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        sign.draw(g);
    }

    MenuItem sign;
    public void setSign(MenuItem sign) {
        this.sign = sign;
        sign.setCollidable(false);
        //sign.setParent(this);
    }
}
