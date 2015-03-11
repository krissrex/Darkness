package com.polarbirds.darkness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.polarbirds.darkness.asset.Assets;
import com.polarbirds.darkness.graphics.Shaders;
import com.polarbirds.darkness.screen.GameScreen;
import com.polarbirds.darkness.screen.LoadingScreen;

public class DarknessGame extends Game {

    public SpriteBatch spriteBatch;
    public ModelBatch modelBatch;
    public final static AssetManager ASSET_MANAGER = new AssetManager();

    Texture img;

    public GameScreen gameScreen;
    public LoadingScreen loadingScreen;


    @Override
    public void create() {
        Shaders.init();

        if (!Shaders.normal_map.isCompiled()){
            System.err.println("Shader failed to compile.");
        }
        ShaderProgram.pedantic = false;

        spriteBatch = new SpriteBatch(1000, Shaders.normal_map);
        modelBatch = new ModelBatch();


        Bullet.init(false, true); // Init Bullet without refcount, and with logging

        queueAssets(); // Load assets

        img = new Texture(Assets.texture.badlogic);

        if (loadingScreen==null) loadingScreen = new LoadingScreen(this);
        if (gameScreen==null) gameScreen = new GameScreen(this);

        setScreen(loadingScreen);
    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

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
        if (loadingScreen != null) {loadingScreen.dispose(); loadingScreen=null;}
        if (gameScreen != null) {gameScreen.dispose(); gameScreen=null;}
    }


    private void queueAssets(){
        ASSET_MANAGER.load(Assets.model.weapon_teslaGun, Model.class);
        ASSET_MANAGER.load(Assets.model.debugEnemy, Model.class);
    }

}
