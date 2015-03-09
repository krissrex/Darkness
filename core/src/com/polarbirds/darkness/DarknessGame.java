package com.polarbirds.darkness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.polarbirds.darkness.asset.Assets;
import com.polarbirds.darkness.screen.GameScreen;
import com.polarbirds.darkness.screen.LoadingScreen;

public class DarknessGame extends Game {

    public SpriteBatch spriteBatch;
    public ModelBatch modelBatch;
    public final static AssetManager ASSET_MANAGER = new AssetManager();
    public final static InputMultiplexer INPUT_MULTIPLEXER = new InputMultiplexer();

    Texture img;

    public GameScreen gameScreen;
    public LoadingScreen loadingScreen;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        modelBatch = new ModelBatch();

        Bullet.init(false, true); // Init Bullet without refcount, and with logging

        Gdx.input.setInputProcessor(INPUT_MULTIPLEXER); // Set input handlers.

        queueAssets(); // Load assets

        if (loadingScreen==null) loadingScreen = new LoadingScreen(this);
        if (gameScreen==null) gameScreen = new GameScreen(this);

        setScreen(loadingScreen);


        // Set gfx settings
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);
        Gdx.gl.glCullFace(GL30.GL_BACK);
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        //Gdx.gl.glDisable(GL30.GL_CULL_FACE); // dont cull sprites



        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
        modelBatch.dispose();
        if (loadingScreen != null) {loadingScreen.dispose(); loadingScreen=null;}
        if (gameScreen != null) {gameScreen.dispose(); gameScreen=null;}
    }


    private void queueAssets(){
        ASSET_MANAGER.load(Assets.model.weapon_teslaGun, Model.class);
        ASSET_MANAGER.load(Assets.model.debugEnemy, Model.class);
        ASSET_MANAGER.load(Assets.model.map_T, Model.class);
    }

}
