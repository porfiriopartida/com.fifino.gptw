package com.fifino.framework.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import android.graphics.Point;

import com.fifino.framework.Entity;
import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.implementation.AndroidImage;

public abstract class AndroidEntity extends Observable implements Entity {
    protected int x = 0, y = 0, width, height, offsetX = 0, offsetY = 0;
    private HashMap<String, Object> attributes = new HashMap<String, Object>();
    protected AndroidImage image;
    private boolean isVisible = true;
    private Bound bound;
    private String name;
    private AndroidEntity parent;
    private boolean isCollidable = true;
    
    @Override
    public boolean isCollidable() {
        return isCollidable;
    }

    public void setCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public AndroidEntity(){
        
    }
    
    public Object getAttribute(String key){
        return attributes.get(key);
    }
    public void setAttribute(String key, Object value){
        attributes.put(key, value);
    }
    public AndroidEntity(AndroidImage image, int x, int y){
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
//        this.setBound(b);
        this.generateBound();
    }
    protected void generateBound(){
        int width = image.getWidth();
        int height = image.getHeight();
        Bound b = new Bound();
        b.setEntity(this);
        Rectangle rectangle = new Rectangle(b);
//        rectangle.setHeight(height).setWidth(width);
        rectangle.setX(0).setY(0).setHeight(height).setWidth(width);
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(rectangle);
        b.setRectangles(rectangles);
        setBound(b);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }


    @Override
    public Bound getBound() {
        return bound;
    }

    @Override
    public void setBound(Bound bound) {
        this.bound = bound;
    }

    @Override
    public boolean collides(Entity remoteEntity) {
        if (!isCollidable() || !remoteEntity.isCollidable()) {
            return false;
        }
        Bound localBound = this.getBound();
        Bound remoteBound = remoteEntity.getBound();
        return localBound.collides(remoteBound);
    }

    public boolean collides(Point p) {
        if (!isCollidable()) {
            return false;
        }
        return this.getBound().collides(p);
    }

    @Override
    public Rectangle[] getCollisionRectangles(Entity remoteEntity) {
        if (!isCollidable() || !remoteEntity.isCollidable()) {
            return null;
        }
        Bound localBound = this.getBound();
        Bound remoteBound = remoteEntity.getBound();
        return localBound.getCollisionRectangles(remoteBound);
    }
    public AndroidImage getImage(){
        return this.image;
    }
    @Override
    public void draw(Graphics g) {
        if(isVisible){
            g.drawImage(getImage(), getX(), getY());
            this.drawBounds(g);
        }
    }

	public void drawBounds(Graphics g) {
		if (Mode.DEBUG == AndroidEntity.MODE) {
			 this.getBound().draw(g, getX(), getY());
		}
	}
    public AndroidEntity getParent() {
        return parent;
    }

    public void setParent(AndroidEntity parent) {
        this.parent = parent;
    }

	public static Mode MODE = Mode.DEBUG;
	public enum Mode { DEBUG, PROD };
    public abstract int getX();
    public abstract int getY();
    public abstract int setX(int x);
    public abstract int setY(int y);
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getAngle();
}
