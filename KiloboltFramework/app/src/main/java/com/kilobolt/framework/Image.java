package com.kilobolt.framework;

import android.graphics.Bitmap;

import com.kilobolt.framework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();

    public int getHeight();

    public ImageFormat getFormat();
    public Bitmap getBitmap();

    public void dispose();

//    public void setParentX(int x);
//
//    public void setParentY(int y);
//
//    public int getParentX();
//
//    public int getParentY();
}