package com.polarbirds.darkness;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.bullet.Bullet;

/**
 * Created by Jørgen on 04.03.2015.
 */
public class GameScreen implements Screen{

    private DarknessGame game;

    public GameScreen(DarknessGame game){
        this.game = game;
        Bullet.init(false, true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }

    @Override
    public void dispose() {

    }
}
