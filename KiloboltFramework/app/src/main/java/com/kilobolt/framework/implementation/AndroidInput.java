package com.kilobolt.framework.implementation;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.fifino.framework.implementation.RotateHandler;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.TouchHandler;

public class AndroidInput implements Input {
    TouchHandler touchHandler;
    RotateHandler rotateHandler;
    int orientation = 0;
    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        if (VERSION.SDK_INT < 5){
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        } else{
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
        }
        rotateHandler = new RotateHandler(this, context);
        rotateHandler.enable();
    }


    @Override
    public void setOrientation(int orientation){
        this.orientation = orientation;
    }
    @Override
    public int getOrientation(){
        return this.orientation;
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }

}