package com.fifino.gptw;

import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.gptw.screens.LoadingScreen;
import com.kilobolt.framework.Screen;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by porfiriopartida on 6/8/2016.
 */
public class GPTWGameTest extends TestCase {
    GPTWGame game;

    @Before
    public void setUp() throws Exception {
        game = new GPTWGame();
        GPTWGame.TEST = true;
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void testGetInitScreen() throws Exception {
        Screen s = game.getInitScreen();
        assertEquals(s.getClass().toString(), LoadingScreen.class.toString());
    }

    @Test
    public void testGetIsPortrait() throws Exception {
        assertTrue(game.getIsPortrait());
    }


    @Test
    public void testGetScore() throws Exception {
        game.setScore(100);
        assertEquals(game.getScore(), 100);
    }

    @Test
    public void testSetScore() throws Exception {
        game.setScore(100);
        assertEquals(game.getScore(), 100);
    }

    @Test
    public void testLoseLive() throws Exception {
        game.setLives(100);
        assertEquals(game.getLives(), 100);
        game.loseLive();
        assertEquals(game.getLives(), 99);
    }

    @Test
    public void testGetLives() throws Exception {
        game.setLives(100);
        assertEquals(game.getLives(), 100);
    }

    @Test
    public void testSetLives() throws Exception {
        game.setLives(100);
        assertEquals(game.getLives(), 100);
    }

    @Test
    public void testGetLevel() throws Exception {
        game.setLevel(100);
        assertEquals(game.getLevel(), 100);
    }

    @Test
    public void testLevelUp() throws Exception {

        game.setLevel(100);
        game.levelUp();
        assertEquals(game.getLevel(), 101);
    }

    @Test
    public void testSetLevel() throws Exception {
        game.setLevel(100);
        assertEquals(game.getLevel(), 100);
    }

    @Test
    public void testReset() throws Exception {
        game.setLevel(100);
        game.setScore(101);
        game.setLives(102);
        assertEquals(game.getLevel(), 100);
        assertEquals(game.getScore(), 101);
        assertEquals(game.getLives(), 102);
        game.reset();
        assertEquals(game.getLevel(), 0);
        assertEquals(game.getScore(), 0);
        assertEquals(game.getLives(), 3);
    }
}