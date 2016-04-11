package com.fifino.gptw.helpers;

import com.fifino.gptw.GPTWGame;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.games.FindTheCar;
import com.fifino.gptw.screens.games.Spamranhas;
import com.fifino.gptw.screens.games.TurnOffAirs;
import com.fifino.gptw.screens.games.TurnOffAlarms;
import com.fifino.gptw.screens.games.WakeUp;
import com.kilobolt.framework.Game;

import java.lang.reflect.Constructor;
import java.util.Random;

/**
 * Created by porfiriopartida on 4/10/2016.
 */
public class RandomGPTWGameFactory implements GPTWGameFactory{
    private Random rnd;
    private int lastGame = -1;
    private GPTWGame game;
    private static RandomGPTWGameFactory self;
    private RandomGPTWGameFactory(){
        rnd = new Random();
    }
    public static RandomGPTWGameFactory getInstance(GPTWGame game){
        if(self == null){
            self = new RandomGPTWGameFactory();
        }
        self.setGPTWGame(game);
        return self;
    }
    public void setGPTWGame(GPTWGame game){
        this.game = game;
    }
    public GPTWScreen getNextScreenReflection(int nextGame){
        try {
            Class[] gamesPool = GPTWGamesPool.LIST;
            Class cl = gamesPool[nextGame];
            Constructor con = cl.getConstructor(Game.class, Integer.class);
            Object xyz = con.newInstance(game, game.getLevel());
            return (GPTWScreen) xyz;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
    boolean useReflection = false;
    @Override
    public GPTWScreen getNextScreen() {
        try {
            int nextGame = -1;
            int maxTries = 100, tries = 0;
            int length = GPTWGamesPool.LIST.length;
            do{
                nextGame = rnd.nextInt(length);
                tries++;
            }while(lastGame == nextGame && tries < maxTries);
            lastGame = nextGame;
            if(useReflection){
                return getNextScreenReflection(nextGame);
            } else {
                return getNextScreenSwitch(nextGame);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private GPTWScreen getNextScreenSwitch(int nextGame) {
//        TURN_OFF_AIRS = 0,
//                TURN_OFF_ALARMS = 1,
//                FIND_THE_CAR = 2,
//                WAKE_UP = 3,
//                SPAMRANHAS = 4;
        switch(nextGame){
            case GPTWGamesPool.TURN_OFF_AIRS:
                return new TurnOffAirs(game, game.getLevel());
            case GPTWGamesPool.TURN_OFF_ALARMS:
                return new TurnOffAlarms(game, game.getLevel());
            case GPTWGamesPool.FIND_THE_CAR:
                return new FindTheCar(game, game.getLevel());
            case GPTWGamesPool.WAKE_UP:
                return new WakeUp(game, game.getLevel());
            case GPTWGamesPool.SPAMRANHAS:
                return new Spamranhas(game, game.getLevel());
            default:
                throw new UnsupportedOperationException("Random Factory can only take valid integers from the GPTWGamesPool class.");
        }
    }
}
