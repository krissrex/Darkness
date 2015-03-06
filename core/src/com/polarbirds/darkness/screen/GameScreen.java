package com.polarbirds.darkness.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.GameWorld;

/**
 * Created by Kristian Rekstad on 04.03.2015.
 */
public class GameScreen implements Screen {

    public DarknessGame game;
    GameWorld world;

    public GameScreen(DarknessGame game) {
        this.game = game;
        //world = new GameWorld(this);
    }

    @Override
    public void show() {
        if (world == null) world = new GameWorld(this);
    }

    @Override
    public void render(float delta) {
        world.update(delta);

        world.render();
    }

    @Override
    public void resize(int width, int height) {
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
