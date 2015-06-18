package com.kilobolt.framework;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    protected abstract void initializeAssets();

    protected abstract void setupEntities();

    public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void backButton();
}
