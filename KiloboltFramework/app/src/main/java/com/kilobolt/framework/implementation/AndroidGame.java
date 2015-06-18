package com.kilobolt.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.kilobolt.framework.Audio;
import com.kilobolt.framework.FileIO;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {
    protected AndroidFastRenderView renderView;
    protected AndroidGraphics graphics;
    protected Audio audio;
    protected Input input;
    protected FileIO fileIO;
    protected Screen screen;
    protected WakeLock wakeLock;

    protected void onCreateActivity(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	
    }
    
    @SuppressWarnings("deprecation")
    protected void onCreateAndroidGame(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        int frameBufferWidth = isPortrait ? 800:1280;
        int frameBufferHeight = isPortrait ? 1280:800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);

        screen = getInitScreen();
        setContentView(renderView);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
                "MyGame");
    }
    public void resetFrameBuffer(boolean isPortrait){
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        int frameBufferWidth = isPortrait ? 800:1280;
        int frameBufferHeight = isPortrait ? 1280:800;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);

        renderView.setFrameBuffer(frameBuffer);
        graphics.setFrameBuffer(frameBuffer);
    }
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateAndroidGame();
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing()){
            screen.dispose();
        }
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }
    @Override
    public void onBackPressed() {
        System.out.println("Back button pressed");
        screen.backButton();
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {

        return screen;
    }
}