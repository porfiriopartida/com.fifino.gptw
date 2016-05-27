package com.fifino.gptw.helpers;

import android.graphics.Bitmap;

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

import static org.easymock.EasyMock.*;


/**
 * Created by porfiriopartida on 5/25/2016.
 */
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
        AndroidImage androidImageMock = createNiceMock(AndroidImage.class);
        Bitmap bitmapMock = createNiceMock(Bitmap.class);

        //expect from graphics
        expect(graphicsMock.newImage(isA(String.class), isA(Graphics.ImageFormat.class))).andReturn(androidImageMock).anyTimes();

        //expected from game
        expect(mockedGame.getLevel()).andReturn(1);
        expect(mockedGame.getGraphics()).andReturn(graphicsMock).anyTimes();

        //Expected from image
        expect(androidImageMock.getWidth()).andReturn(200).anyTimes();
        expect(androidImageMock.getHeight()).andReturn(200).anyTimes();
        expect(androidImageMock.getBitmap()).andReturn(bitmapMock).anyTimes();

        //prepare
        replay(mockedGame);
        replay(graphicsMock);
        replay(androidImageMock);
        replay(bitmapMock);

//        String fileName, ImageFormat format
        GPTWScreen s = f.getNextScreenReflection( GPTWGamesPool.FIND_THE_CAR ) ;
        System.out.println(s.getClass().toString());

        //Check
        verify(mockedGame);
        verify(graphicsMock);
        verify(androidImageMock);
        verify(bitmapMock);
    }
//
//    @Test
//    public void testGetNextScreen() throws Exception {
//        RandomGPTWGameFactory f = RandomGPTWGameFactory.getInstance(mockedGame);
//        f.getNextScreen() ;
//    }
}