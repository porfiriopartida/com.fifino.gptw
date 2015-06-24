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
public class TurnOffAlarms extends GPTWScreen {
    private AndroidImage bgImage;
    public TurnOffAlarms(Game game, Integer level) {
        super(game);
        setMaxSeconds(6);
    }

    @Override
    protected void updateRunning(List<Input.TouchEvent> touchEvents, float deltaTime) {
        this.updateTime();
    }
    @Override
    public void drawRunningUI(List<Input.TouchEvent> touchEvents, float deltaTime){
        Graphics g = game.getGraphics();
        this.menuItems.draw(g);
        this.drawBar(g);
    }

    @Override
    protected void initializeAssets() {

    }

    @Override
    protected void setupEntities() {
        this.state = GameState.Running;
        bgImage = Assets.getAndroidImage("game_2_bg");
        this.menuItems.add(new MenuItem(bgImage, 0, 0));
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        System.exit(0);
    }
}
