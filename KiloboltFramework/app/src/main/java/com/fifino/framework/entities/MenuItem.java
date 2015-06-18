package com.fifino.framework.entities;

import com.fifino.framework.implementation.AndroidEntity;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.implementation.AndroidImage;

public class MenuItem extends AndroidEntity {
    public MenuItem(){
        super();
    }
    public MenuItem(AndroidImage image, int x, int y) {
        super(image, x, y);
    }

    @Override
    public void update(float deltaTime) {
    }

    public int getRight(){
        return this.getX() + this.getWidth();
    }
    public int getLeft(){
        return this.getX() + this.getRelativeX();
    }
    
    public int getRelativeX(){
        return this.x;
    }
    public int getRelativeY(){
        return this.y;
    }

	@Override
	public int getX() {
	    if(null != this.getParent()){
	        MenuItemComposite parent = (MenuItemComposite)getParent();
	        int absX = parent.getParentX() + this.x; 
	        return absX;
	    }
		return x;
	}

	@Override
	public int getY() {
        if(null != this.getParent()){
            MenuItemComposite parent = (MenuItemComposite)getParent(); 
            return parent.getParentY() + this.y;
        }
		return y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getAngle() {
		return 0;
	}

	public void setImage(Image debugButton) {
	}
    
    @Override
    public int setX(int x) {
        int old = this.x ; 
        this.x = x;
        return old;
    }

    @Override
    public int setY(int y) {
        int old = this.y; 
        this.y = y;
        return old;
    }
}
