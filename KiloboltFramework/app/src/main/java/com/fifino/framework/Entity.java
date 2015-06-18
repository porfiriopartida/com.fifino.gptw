package com.fifino.framework;

import com.fifino.framework.entities.Bound;
import com.fifino.framework.entities.Rectangle;
import com.kilobolt.framework.Graphics;

public interface Entity {
    
    public void setBound(Bound bound);

    public Bound getBound();

//    public void setImages(List<Image> images);

//    public List<Image> getImages();

//    public void addImage(Image image);

    public boolean collides(Entity e);

    public boolean isCollidable();

    public void draw(Graphics g);
    
    public void update(float delta);

    public Rectangle[] getCollisionRectangles(Entity remoteEntity);
}