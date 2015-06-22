package com.fifino.gptw;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.framework.simple.SimpleScreen;
import com.fifino.gptw.screens.MainMenu;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidAudio;
import com.kilobolt.framework.implementation.AndroidFastRenderView;
import com.kilobolt.framework.implementation.AndroidFileIO;
import com.kilobolt.framework.implementation.AndroidGame;
import com.kilobolt.framework.implementation.AndroidGraphics;
import com.kilobolt.framework.implementation.AndroidInput;

public class GPTWGame extends AndroidGame {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        onCreateAndroidGame();
         AndroidEntity.MODE = AndroidEntity.Mode.PROD;
        //AndroidEntity.MODE = AndroidEntity.Mode.DEBUG;
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
    	return new MainMenu(this);
    }
    public boolean getIsPortrait(){
        return this.isPortrait;
    }
    boolean isPortrait = true;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	isPortrait = false;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        	isPortrait = true;
        }
        resetFrameBuffer(isPortrait);
        System.out.println(isPortrait ? "PORTRAIT":"LANDSCAPE");
    }
}