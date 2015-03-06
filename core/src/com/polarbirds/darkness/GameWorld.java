package com.polarbirds.darkness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
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

    // Rendering
    GameScreen game;
    ModelBatch modelBatch;

    PerspectiveCamera playerCamera;
    public Environment environment;

    // Collision
    btCollisionWorld collisionWorld;
    DebugDrawer debugDrawer;
    btCollisionDispatcher dispatcher;
    btCollisionConfiguration collisionConfiguration;
    btDbvtBroadphase broadphase;

    // Entities
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

        playerCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        playerCamera.translate(0, 2f, 10f); //Fixme
        playerCamera.near = 0.1f;
        playerCamera.far = 20f;
        playerCamera.lookAt(0,0,0);
        playerCamera.update();
        playerObject = new PlayerObject(this, playerCamera);

        collisionWorld.addCollisionObject(playerObject.collisionObject);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1f));
        environment.add(new PointLight().set(Color.WHITE, new Vector3(0f, 1f, 1f), 2.0f));
    }


    public void resize(int width, int height){
        playerCamera.viewportWidth = width;
        playerCamera.viewportHeight = height;
        playerCamera.update(true);
    }

    public void update(float deltaTime){
        playerObject.update(deltaTime);
        collisionWorld.performDiscreteCollisionDetection();

        playerCamera.update();
    }

    public void render(){


        modelBatch.begin(playerCamera);
        playerObject.render(modelBatch);
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


    /** Allows the environment to change. */
    public Environment getPlayerEnvironment(){
        return environment;
    }
}
