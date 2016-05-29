package com.fifino.gptw.helpers;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.fifino.gptw.GPTWGame;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.games.FindTheCar;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidImage;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import org.junit.runner.RunWith;
//import org.robolectric.RuntimeEnvironment;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Implements;

import static org.easymock.EasyMock.*;


/**
 * Created by porfiriopartida on 5/25/2016.
 */
//@RunWith(RobolectricTestRunner.class)
//@Implements(Color.class)
public class RandomGPTWGameFactoryTest extends TestCase {

    GPTWGame mockedGame;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        mockedGame = createNiceMock(GPTWGame.class);
    }
    @After
    public void tearDown() throws Exception {
        mockedGame = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        RandomGPTWGameFactory f = RandomGPTWGameFactory.getInstance(mockedGame);
        //getInstance must always return a non null object.
        assertNotNull(f);

        RandomGPTWGameFactory f2 = RandomGPTWGameFactory.getInstance(mockedGame);
        //getInstance must always return the same object (singleton)
        assertSame(f, f2);
    }

//    @Test
//    public void testSetGPTWGame() throws Exception {
//
//    }

    @Test
    public void testGetNextScreenReflection() throws Exception {
        RandomGPTWGameFactory f = RandomGPTWGameFactory.getInstance(mockedGame);

        //Mocks
        Graphics graphicsMock = createNiceMock(Graphics.class);

        //expected from game
        expect(mockedGame.getLevel()).andReturn(1).atLeastOnce();

        //prepare
        replay(mockedGame);
        replay(graphicsMock);

//        String fileName, ImageFormat format
        GPTWScreen s = f.getNextScreenReflection( GPTWGamesPool.FIND_THE_CAR ) ;
        assertEquals(s.getClass().toString(), FindTheCar.class.toString());

        //Check
        verify(mockedGame);
        verify(graphicsMock);
    }
//
    @Test
    public void testGetNextScreenSwitch() throws Exception {
        RandomGPTWGameFactory f = RandomGPTWGameFactory.getInstance(mockedGame);

        //Mocks
        Graphics graphicsMock = createNiceMock(Graphics.class);

        //expected from game
        expect(mockedGame.getLevel()).andReturn(1).atLeastOnce();

        //prepare
        replay(mockedGame);
        replay(graphicsMock);

//        String fileName, ImageFormat format
        GPTWScreen s = f.getNextScreenSwitch( GPTWGamesPool.FIND_THE_CAR ) ;
        assertEquals(s.getClass().toString(), FindTheCar.class.toString());

        //Check
        verify(mockedGame);
        verify(graphicsMock);
    }
}