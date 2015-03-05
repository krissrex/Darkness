package com.polarbirds.darkness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.polarbirds.darkness.screen.GameScreen;

public class DarknessGame extends Game {
    public SpriteBatch spriteBatch;
    public ModelBatch modelBatch;
    public final static AssetManager ASSET_MANAGER = new AssetManager();

    Texture img;

    GameScreen gameScreen;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        modelBatch = new ModelBatch();

        img = new Texture("badlogic.jpg");

        gameScreen = new GameScreen(this);

        setScreen(gameScreen);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(img, 0, 0, 50, 50);
        spriteBatch.end();

        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
        modelBatch.dispose();

    }


}
