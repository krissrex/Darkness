package com.polarbirds.darkness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.darkness.gameobject.PlayerObject;
import com.polarbirds.darkness.screen.GameScreen;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class GameWorld implements Disposable{

    GameScreen game;
    ModelBatch modelBatch;

    btCollisionWorld collisionWorld;
    DebugDrawer debugDrawer;
    btCollisionDispatcher dispatcher;
    btCollisionConfiguration collisionConfiguration;
    btDbvtBroadphase broadphase;

    PerspectiveCamera playerCamera;

    PlayerObject playerObject;


    public GameWorld(GameScreen game){
        this.game = game;
        modelBatch = game.game.modelBatch;

        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfiguration);

        debugDrawer = new DebugDrawer();
        debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
        collisionWorld.setDebugDrawer(debugDrawer);

        playerCamera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerCamera.translate(5, 1, 0);
        playerObject = new PlayerObject(playerCamera);

        collisionWorld.addCollisionObject(playerObject.collisionObject);
    }


    public void resize(int width, int height){
        playerCamera.viewportWidth = width;
        playerCamera.viewportHeight = height;
        playerCamera.update();
    }

    public void update(float deltaTime){
        playerObject.update(deltaTime);
        collisionWorld.performDiscreteCollisionDetection();

        playerCamera.update();
    }

    public void render(){


        modelBatch.begin(playerCamera);
        modelBatch.end();

        debugDrawer.begin(playerCamera);
        collisionWorld.debugDrawWorld();
        debugDrawer.end();
    }

    @Override
    public void dispose() {
        game = null;
        modelBatch = null;

        collisionWorld.dispose();
        debugDrawer.dispose();
        dispatcher.dispose();
        collisionConfiguration.dispose();
        collisionConfiguration.dispose();

        playerObject.dispose();
    }
}
