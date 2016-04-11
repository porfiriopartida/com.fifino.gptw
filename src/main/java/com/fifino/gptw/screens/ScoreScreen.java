package com.fifino.gptw.screens;

import android.graphics.Color;
import android.graphics.Paint;

import com.fifino.framework.assets.Assets;
import com.fifino.framework.entities.MenuItem;
import com.fifino.framework.entities.Sprite;
import com.fifino.gptw.GPTWGame;
import com.fifino.gptw.helpers.GPTWResources;
import com.fifino.gptw.helpers.GPTWTransition;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Graphics;
import com.kilobolt.framework.Input.TouchEvent;
import com.kilobolt.framework.Screen;

import java.util.HashMap;
import java.util.List;

public class ScoreScreen extends GPTWScreen {
	HashMap<String, String> assets;
	GPTWTransition transition;
	int min, max, step, current;
	boolean done = false;
	boolean isWin = false;
	int fontSize = 165;
	Paint paintScore;
	int maxLives = 3;
	GPTWGame gptwGame;
	public ScoreScreen(Game game, GPTWTransition transition) {
		super(game);
		this.transition = transition;
		min = transition.getFromScore();
		max = transition.getToScore();
		isWin = transition.isWin();
		step = (max-min)/20;
		current = min;
		paintScore = getPaint();
		//rgb(241,89,34)
//		paintScore.setTextAlign(Paint.Align.CENTER);
		paintScore.setTextSize(fontSize);
		paintScore.setARGB(255, 241, 89, 34);
		gptwGame = (GPTWGame) this.game;
		gptwGame.setScore(max);
		if(!isWin){
			gptwGame.loseLive();
		}

		initializeAssets();
		setupEntities();
	}

	protected void postConstruct(){
	}
	@Override
	protected void initializeAssets() {
	 	assets = new HashMap<String, String>();
		Graphics g = game.getGraphics();
		String imagesPath = Assets.IMAGES_PATH;
		assets.put(GPTWResources.GPTW_SCORE_BG, GPTWResources.GPTW_SCORE_BG_IMG);
		assets.put(GPTWResources.GPTW_SCORE_LIVES, GPTWResources.GPTW_SCORE_LIVES_IMG);
		initializeAssets(assets);
	}
	@Override
	protected void setupEntities() {
		GPTWGame game = (GPTWGame) this.game;
		Graphics g = game.getGraphics();
		Sprite sprite;
		int spriteSize = 150,
				livesX = (int)(g.getWidth()/2 - (1.5 * spriteSize)),
				livesY = g.getHeight() / 2 - spriteSize,
				currentX = livesX;

		bgImage = Assets.getAndroidImage(GPTWResources.GPTW_SCORE_BG);
		MenuItem bg = new MenuItem(bgImage, 0, 0);
		this.menuItems.add(bg);
		this.state = GameState.Running;
		lastTime = getTimestamp();
		try {
			for(int i=maxLives;i>=1;i--){
				//String assetName, int x, int y, int width, int height
				sprite = new Sprite(GPTWResources.GPTW_SCORE_LIVES, currentX, livesY, spriteSize, spriteSize);
				sprite.registerAnimation("alive", 0, 2);
				sprite.registerAnimation("dead", 1, 2);
				sprite.playAnimation(i <= game.getLives() ? "alive" : "dead");

				//sprites[i - 1] = sprite;

				currentX += spriteSize;
				this.menuItems.add(sprite);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		int x = g.getWidth()/2,
			y = g.getHeight() - 50;
		//drawString(String text, int x, int y, Paint paint)
		g.fillRect(0, y - fontSize, g.getWidth(), fontSize + 100, Color.WHITE);
		g.drawString(scoreText, x, y, paintScore);
		//TODO: Add share score DRAW here
	}
	private String getScoreText(){
		return ""+current;
	}
	@Override
	protected void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		this.menuItems.update(deltaTime);
		long timestamp = getTimestamp();
		if(!done){
			current += step;
			if((current > max || touchEvents.size() > 0)){
				current = max;
				done = true;
				lastTime = timestamp;
			}
		}
		if(done && timestamp > lastTime + 2000 || timestamp > lastTime + 5000){
			if(touchEvents.size() > 0 || gptwGame.getLives() > 0){
				//TODO: Add share score logic here
				this.clean(this.assets);
				game.setScreen(getCheckLivesScreen());
			}
//			this.state = GameState.Paused;
		}
	}
	private Screen getCheckLivesScreen(){
		GPTWGame game = (GPTWGame) this.game;
		if(game.getLives() > 0){
			return getNextScreen();
		} else {
			return new MainMenu(game);
		}
	}
}
