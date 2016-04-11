package com.fifino.gptw.helpers;

import static junit.framework.Assert.*;

import org.junit.Test;

/**
 * Created by porfiriopartida on 4/10/2016.
 */
public class GPTWGamesPoolTest {
    @Test(expected = UnsupportedOperationException.class)
    public void constructException(){
        new GPTWGamesPool();
    }

    @Test
    public void hasGames(){
        assertTrue(GPTWGamesPool.LIST.length > 0);
    }

}