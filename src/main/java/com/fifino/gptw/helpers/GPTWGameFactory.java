package com.fifino.gptw.helpers;

import com.fifino.framework.helpers.GameFactory;
import com.fifino.gptw.screens.GPTWScreen;
import com.kilobolt.framework.Screen;

/**
 * Created by porfiriopartida on 4/10/2016.
 */
public interface GPTWGameFactory extends GameFactory {
    public GPTWScreen getNextScreen();
}
