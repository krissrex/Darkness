package com.polarbirds.darkness.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.darkness.DarknessGame;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class LoadingScreen implements Screen{

    DarknessGame game;
    SpriteBatch batch;
    boolean loaded;

    public LoadingScreen(DarknessGame game){
        this.game = game;
        loaded = false;
    }

    @Override
    public void show() {
        batch = game.spriteBatch;

    }

    @Override
    public void render(float delta) {
        if (DarknessGame.ASSET_MANAGER.update()){
            loaded = true;
        }

        batch.begin();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch = null;
    }

    @Override
    public void dispose() {
        game = null;
    }
}
