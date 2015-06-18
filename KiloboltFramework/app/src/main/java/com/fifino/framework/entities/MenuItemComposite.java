package com.fifino.framework.entities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;

public class MenuItemComposite extends MenuItem {
    protected List<MenuItem> menuItems;
    private MenuItem firstMenuItem, lastMenuItem;
    private int parentX, parentY;
    public MenuItemComposite() {
        this(0, 0);
    }
    public MenuItemComposite(int x, int y) {
        menuItems = new ArrayList<MenuItem>();
        this.parentX = x;
        this.parentY = y;
    }
    public MenuItem getItemByName(String name){
        MenuItem item;
        int i = 0, length = menuItems.size();
        for(i=0;i<length;i++){
            item = menuItems.get(i);
            if(name.equals(item.getName())){
                return item;
            }
        }
        return null;
    }
    MenuItem lastCollitionItem = null;
    public MenuItem getLastCollision(){
        return lastCollitionItem;
    }
    @Override
    public boolean collides(Point p) {
        int len = menuItems.size();
        MenuItem item;
        for(int i=0;i<len;i++){
            item = menuItems.get(i);
            if(item.collides(p)){
                if(item instanceof MenuItemComposite){
                    lastCollitionItem = ((MenuItemComposite) item).getLastCollision();
                }else{
                    lastCollitionItem = item;
                }
                return true;
            }
        }
        return false;
    }
    public List<MenuItem> getItems(){
    	return this.menuItems;
    }
    private MenuItem getFirstItem(){
        return this.menuItems.get(0);
    }
    private MenuItem getLastItem(){
        return this.menuItems.get(menuItems.size() - 1);
    }
    public MenuItemComposite add(MenuItem item){
        item.setParent(this);
        menuItems.add(item);
        return this;
    }
    public MenuItemComposite remove(MenuItem item){
        item.setParent(null);
        menuItems.remove(item);
        return this;
    }
    public MenuItemComposite clear(){
        menuItems.clear();
        return this;
    }
    public MenuItemComposite addMenuItem(MenuItem item){
        return this.add(item);
    }
    @Override
    public void draw(Graphics g) {
        int len = menuItems.size();
        MenuItem item;
        for(int i=0;i<len;i++){
            item = menuItems.get(i);
            if(null != item){
                item.draw(g);   
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        int len = menuItems.size();
        MenuItem item;
        for(int i=0;i<len;i++){
            item = menuItems.get(i);
            item.update(deltaTime);
        }
    }

	public void setImage(Image image) {
        int len = menuItems.size();
        MenuItem item;
        for(int i=0;i<len;i++){
            item = menuItems.get(i);
            item.setImage(image);
        }
	}

    @Override
    public int setX(int x) {
        int len = menuItems.size();
        MenuItem item;
        for(int i=0;i<len;i++){
            item = menuItems.get(i);
            item.setX(x);
        }
        return -1;
    }

    @Override
    public int setY(int y) {
        int len = menuItems.size();
        MenuItem item;
        for(int i=0;i<len;i++){
            item = menuItems.get(i);
            item.setY(y);
        }
        return -1;
    }
    public int getParentX() {
        return parentX;
    }
    public void setParentX(int parentX) {
        this.parentX = parentX;
    }
    public int getParentY() {
        return parentY;
    }
    public void setParentY(int parentY) {
        this.parentY = parentY;
    }
    public MenuItem getFirstMenuItem() {
        return firstMenuItem != null ? firstMenuItem : this.getFirstItem();
    }
    public void setFirstMenuItem(MenuItem firstMenuItem) {
        this.firstMenuItem = firstMenuItem;
    }
    public MenuItem getLastMenuItem() {
        return lastMenuItem != null ? lastMenuItem : this.getLastItem();
    }
    public void setLastMenuItem(MenuItem lastMenuItem) {
        this.lastMenuItem = lastMenuItem;
    }
}
