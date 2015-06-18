package com.fifino.framework.simple;

import android.os.Bundle;

import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;
//import android.provider.MediaStore.Files;

public class SimpleGame extends AndroidGame {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public Screen getInitScreen() {
        return new SimpleScreen(this);
    }
}