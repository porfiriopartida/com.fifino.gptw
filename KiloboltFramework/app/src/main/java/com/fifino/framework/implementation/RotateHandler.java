package com.fifino.framework.implementation;

import android.content.Context;
import android.view.OrientationEventListener;

import com.kilobolt.framework.implementation.AndroidInput;

public class RotateHandler extends OrientationEventListener {
    AndroidInput input;
    public RotateHandler(AndroidInput input, Context context) {
        super(context);
        this.input = input;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        this.input.setOrientation(orientation);
    }
}