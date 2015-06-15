package com.fifino.gptw.screens;

import java.util.List;

import android.graphics.Color;

import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;

public class MainMenu extends GPTWScreen {

	public MainMenu(Game game) {
		super(game);
	}

	@Override
	protected void initializeAssets() {
	}

	@Override
	protected void setupEntities() {
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
	@Override
	protected void drawRunningUI(List<TouchEvent> touchEvents, float deltaTime) {
        Graphics g = game.getGraphics();
        g.fillRect(0, 0, g.getWidth(), 120, Color.WHITE);
        g.drawString("Running boys =D", g.getWidth() / 2 - 25, 40,
                paintB);
	}
	@Override
	protected void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    // Handles out of bounds exception for the list getting empty
                    // after getting the size.
                    return;
                }
                TouchEvent event = touchEvents.get(i);
                if (event.type == TouchEvent.TOUCH_DOWN) {
                    state = GameState.GameOver;
                }
            }
        }
	}
	@Override
	protected void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (touchEvents.size() < len) {
                    // Handles out of bounds exception for the list getting empty
                    // after getting the size.
                    return;
                }
                TouchEvent event = touchEvents.get(i);
                if (event.type == TouchEvent.TOUCH_DOWN) {
                    state = GameState.Ready;
                }
            }
        }
    }

}
