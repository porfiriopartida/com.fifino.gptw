package com.fifino.gptw.screens;

import android.graphics.Color;
import android.graphics.Paint;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.gptw.GPTWGame;
import com.fifino.gptw.helpers.GPTWResources;
import com.fifino.gptw.helpers.GPTWTransition;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Input.TouchEvent;

import java.util.HashMap;
import java.util.List;

public class ScoreScreen extends GPTWScreen {
	HashMap<String, String> assets;
	GPTWTransition transition;
	int min, max, step, current;
	boolean done = false;
	boolean isWin = false;
	Paint paintW;
	public ScoreScreen(Game game, GPTWTransition transition) {
		super(game);
		this.transition = transition;
		initializeAssets();
		setupEntities();
		min = transition.getFromScore();
		max = transition.getToScore();
		isWin = transition.isWin();
		step = (max-min)/10;
		current = min;
		paintW = getPaint();
		paintW.setColor(Color.WHITE);
		game.setScore(max);
	}

	protected void postConstruct(){
	}
	@Override
	protected void initializeAssets() {
	 	assets = new HashMap<String, String>();
		Graphics g = game.getGraphics();
		String imagesPath = Assets.IMAGES_PATH;
		assets.put(GPTWResources.GPTW_SCORE_BG, GPTWResources.GPTW_SCORE_BG_IMG);
		assets.put(GPTWResources.GPTW_SCORE_LIVE, GPTWResources.GPTW_SCORE_LIVE_IMG);
		initializeAssets(assets);
	}
	@Override
	protected void setupEntities() {
//		Graphics g = game.getGraphics();
		bgImage = Assets.getAndroidImage(GPTWResources.GPTW_SCORE_BG);
		MenuItem bg = new MenuItem(bgImage, 0, 0);
		this.menuItems.add(bg);
		this.state = GameState.Running;
		lastTime = getTimestamp();
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
          g.fillRect(0, 0, g.getWidth(), g.getHeight(), Color.WHITE);
		this.menuItems.draw(g);
		String scoreText = getScoreText();
		int x = g.getWidth()/2 - scoreText.length()*40/2,
			y = g.getHeight()/2 - 40/2;
		//drawString(String text, int x, int y, Paint paint)
		g.drawString(scoreText, x, y, paintW);
	}
	private String getScoreText(){
		return ""+current;
	}
	@Override
	protected void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		if(!done){
			current += step;
			if((current > max || touchEvents.size() > 0)){
				current = max;
				done = true;
				lastTime = getTimestamp();
			}
		}
		if(done && this.getTimestamp() > lastTime + 2000 || this.getTimestamp() > lastTime + 5000){
			this.clean(this.assets);
			game.setScreen(getNextScreen());
			this.state = GameState.Paused;
		}
	}
}
