package com.fifino.gptw.screens.games;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.Sprite;
import com.fifino.gptw.helpers.GPTWTransition;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.MiddleScreen;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input;
import com.kilobolt.framework.implementation.AndroidImage;

import java.util.List;

/**
 * Created by ppartida on 6/23/15.
 */
public class TurnOffAlarms extends TurnOffAirs {
    public TurnOffAlarms(Game game, Integer level) {
        super(game, level);
    }
    protected String getBackgroundKey(){
        return "game_2_bg";
    }

    @Override
    protected void triggerTimeIsZero(){
        int errorState = 0;
        String stateName = "alarms-";
        for(Sprite sprite:activeElements){
            boolean isAir = "air".equals(sprite.getName());
            boolean isOn = (Boolean) sprite.getAttribute("status");
            if(isAir){
                if(!isOn){
                    stateName = "airs-";
                    errorState = 2;
                    break;
                }
            } else {
                if(isOn){
                    errorState = 2;
                    break;
                }
            }
        }
        String bgName = stateName + errorState;
        this.clean(assets);
        int currentScore = this.game.getScore();
        GPTWTransition transition = new GPTWTransition(currentScore, errorState == 0 ? currentScore + (100 + 3 * lastChanged):currentScore-10, errorState == 0, 0);
        this.game.setScreen(new MiddleScreen(this.game, bgName, transition));
    }
}
