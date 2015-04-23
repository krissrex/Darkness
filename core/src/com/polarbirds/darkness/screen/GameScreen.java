package com.polarbirds.darkness.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.Debug;
import com.polarbirds.darkness.game.GameWorld;

/**
 * Created by Kristian Rekstad on 04.03.2015.
 */
public class GameScreen implements Screen {

    public DarknessGame game;
    GameWorld world;
    public PerspectiveCamera playerCamera;
    private ShapeRenderer shapeRenderer;


    public GameScreen(DarknessGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        playerCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerCamera.translate(0, 1.8f, 0f); //Fixme
        playerCamera.lookAt(1f, 1.8f, 0f);
        playerCamera.near = 0.1f;
        playerCamera.far = 20f;
        playerCamera.update(true);

        if (world == null) world = new GameWorld(this);

        shapeRenderer = new ShapeRenderer();
    }



    @Override
    public void render(float delta) {
        world.update(delta);
        world.render();


        if (Debug.DEBUG){
            shapeRenderer.setProjectionMatrix(playerCamera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(0f, 0, 0, 100, 0, 0, Color.RED, Color.RED);
                shapeRenderer.line(0f, 0f, 0, 0f, 100f, 0, Color.GREEN, Color.GREEN);
                shapeRenderer.line(0f, 0f, 0f, 0f, 0f, 100f, Color.BLUE, Color.BLUE);
            shapeRenderer.end();
        }

    }

    @Override
    public void resize(int width, int height) {
        playerCamera.viewportWidth = width;
        playerCamera.viewportHeight = height;
        playerCamera.update(true);

        world.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (world != null) {world.dispose(); world=null;}
    }
}
