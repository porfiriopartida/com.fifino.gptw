package com.fifino.gptw;

import android.content.res.Configuration;
import android.os.Bundle;

import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.gptw.helpers.GPTWGamesPool;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.LoadingScreen;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidGame;

import java.lang.reflect.Constructor;
import java.util.Random;

public class GPTWGame extends AndroidGame {
    Random rnd;
    int lastGame = -1;
    int level = 0;
    int score;
    int lives = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        onCreateAndroidGame();
        AndroidEntity.MODE = AndroidEntity.Mode.PROD;
        // AndroidEntity.MODE = AndroidEntity.Mode.DEBUG;
        rnd = new Random();
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
    	return new LoadingScreen(this);
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
    public Screen getNextScreen(){
        try {
            Class[] gamesPool = GPTWGamesPool.LIST;
            //Ensures not to get the same game again.
            int nextGame = -1;
            int maxTries = 100, tries = 0;
            do{
                nextGame = rnd.nextInt(gamesPool.length);
                tries++;
            }while(lastGame == nextGame && tries < maxTries);
            lastGame = nextGame;

            //Creates a new instance of the game
//            String className = gamesPool[nextGame];
//                cl = Class.forName(className);
            Class cl = gamesPool[nextGame];
//            System.out.println(cl.getName());
//            System.out.println("nextGame: " + nextGame);
            Constructor con = cl.getConstructor(Game.class, Integer.class);
            Object xyz = con.newInstance(this, ++level);
            return (GPTWScreen) xyz;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int loseLive(){
        this.lives--;
        return this.lives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void reset() {
        setScore(0);
        setLives(3);
        setLevel(0);
    }
}