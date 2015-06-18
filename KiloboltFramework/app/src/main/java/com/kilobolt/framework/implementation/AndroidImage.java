package com.kilobolt.framework.implementation;

import android.graphics.Bitmap;

import com.fifino.framework.assets.BitmapTransform;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Graphics.ImageFormat;

public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;
    int x,y, parentX = 0, parentY = 0;

    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setParentX(int x) {
        this.parentX = x;
    }

    public void setParentY(int y) {
        this.parentY = y;
    }

    public int getParentX() {
        return this.parentX;
    }

    public int getParentY() {
        return this.parentY;
    }
    
    
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }
    @Override
    public void dispose() {
        bitmap.recycle();
    }
    @Override
    public Bitmap getBitmap(){
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public void rotate(int angle){
    	setBitmap( BitmapTransform.rotate(bitmap, angle));
    }
    public void scale(int width, int height){
    	setBitmap( BitmapTransform.scale(bitmap, width, height));
    }
}
