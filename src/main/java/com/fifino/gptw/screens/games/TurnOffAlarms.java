package com.fifino.gptw.screens.games;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.gptw.screens.GPTWScreen;
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
}
