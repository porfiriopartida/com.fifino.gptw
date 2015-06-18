package com.fifino.framework.entities;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;

import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Graphics;

public class Bound {
    private int x;
    private int y;
    private List<Rectangle> rectangles;
    private int debugColor = 0;
    private AndroidEntity entity;

    public int getX() {
        if(null != this.getEntity()){
            AndroidEntity parent = getEntity();
            int absX = parent.getX() + this.x; 
            return absX;
        }
        return x;
    }

    public Bound setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        if(null != this.getEntity()){
            AndroidEntity parent = getEntity();
            int absY = parent.getY() + this.y; 
            return absY;
        }
        return y;
    }

    public Bound setY(int y) {
        this.y = y;
//        for (Rectangle rectangle : rectangles) {
//            rectangle.setParentY(y);
//        }
        return this;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public Bound setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
//        for (Rectangle rectangle: rectangles) {
//            rectangle.setBound(this);
//        }
        return this;
    }

    public Bound addRectangle(Rectangle rectangle) {
        this.rectangles.add(rectangle);
        return this;
    }

    public boolean collides(Bound b) {
        List<Rectangle> rectanglesLocal = this.getRectangles();
        List<Rectangle> rectanglesRemote = b.getRectangles();

        for (Rectangle rectangleLocal : rectanglesLocal) {
            for (Rectangle rectangleRemote : rectanglesRemote) {
                if (rectangleLocal.intersects(rectangleRemote)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean collides(Point p) {
        List<Rectangle> rectanglesLocal = this.getRectangles();
        for (Rectangle rectangleLocal : rectanglesLocal) {
            if (rectangleLocal.intersects(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the 2 rectangles that collided from local and remote entities.
     * @param b
     * @return
     */
    public Rectangle[] getCollisionRectangles(Bound b) {
        List<Rectangle> rectanglesLocal = this.getRectangles();
        List<Rectangle> rectanglesRemote = b.getRectangles();

        for (Rectangle rectangleLocal : rectanglesLocal) {
            for (Rectangle rectangleRemote : rectanglesRemote) {
                if (rectangleLocal.intersects(rectangleRemote)) {
                    return new Rectangle[]{rectangleLocal, rectangleRemote};
                }
            }
        }
        return null;
    }
    public int getColor(){
        if(this.debugColor == 0){
            Random rnd = new Random();
            this.debugColor = Color.argb(150, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
        }
        return this.debugColor;
    }
    public void draw(Graphics g, int parentX, int parentY) {
        for (Rectangle r : rectangles) {
//            g.drawRect(r.getAbsoluteX(), r.getAbsoluteY(), r.getWidth(),
//                    r.getHeight(), getColor());
//            g.fillRect(r.getX() + parentX, r.getY() + parentY, r.getWidth(),
//                    r.getHeight(), getColor());
            g.fillRect(r.getX(), r.getY(), r.getWidth(),
                    r.getHeight(), getColor());
        }
    }

    public AndroidEntity getEntity() {
        return entity;
    }

    public void setEntity(AndroidEntity entity) {
        this.entity = entity;
    }
}
