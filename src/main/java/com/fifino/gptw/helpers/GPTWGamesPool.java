package com.fifino.gptw.helpers;

/**
 * Created by porfiriopartida on 9/11/2015.
 */
public final class GPTWGamesPool {
    public GPTWGamesPool(){
        throw new UnsupportedOperationException();
    }
    public static final Class[] LIST = new Class[]{
            com.fifino.gptw.screens.games.TurnOffAirs.class,
            com.fifino.gptw.screens.games.TurnOffAlarms.class,
            com.fifino.gptw.screens.games.FindTheCar.class,

            com.fifino.gptw.screens.games.WakeUp.class,
            com.fifino.gptw.screens.games.Spamranhas.class,
    };
    public static final int
            TURN_OFF_AIRS = 0,
            TURN_OFF_ALARMS = 1,
            FIND_THE_CAR = 2,
            WAKE_UP = 3,
            SPAMRANHAS = 4;
}
