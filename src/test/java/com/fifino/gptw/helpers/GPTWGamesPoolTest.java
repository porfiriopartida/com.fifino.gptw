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

    @Test
    public void testGameTypes(){
        assertEquals(GPTWGamesPool.LIST[GPTWGamesPool.SPAMRANHAS].toString(), com.fifino.gptw.screens.games.Spamranhas.class.toString() );
        assertEquals(GPTWGamesPool.LIST[GPTWGamesPool.TURN_OFF_AIRS].toString(), com.fifino.gptw.screens.games.TurnOffAirs.class.toString() );
        assertEquals(GPTWGamesPool.LIST[GPTWGamesPool.TURN_OFF_ALARMS].toString(), com.fifino.gptw.screens.games.TurnOffAlarms.class.toString() );
        assertEquals(GPTWGamesPool.LIST[GPTWGamesPool.FIND_THE_CAR].toString(), com.fifino.gptw.screens.games.FindTheCar.class.toString() );
    }
}