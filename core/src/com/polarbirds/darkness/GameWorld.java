package com.polarbirds.darkness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    public Environment environment;
    public PerspectiveCamera playerCamera;

    private OrthographicCamera overviewCamera;

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
        this.playerCamera = game.playerCamera;

        overviewCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, broadphase, collisionConfiguration);

        debugDrawer = new DebugDrawer();
        debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
        collisionWorld.setDebugDrawer(debugDrawer);


        playerObject = new PlayerObject(this, playerCamera);

        collisionWorld.addCollisionObject(playerObject.collisionObject);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0f, 0f, 0f, 1f));
        environment.add(new PointLight().set(Color.WHITE, new Vector3(0f, 1f, 1f), 2.0f));
    }


    public void resize(int width, int height){
    }

    public void update(float deltaTime){
        playerObject.update(deltaTime);
        collisionWorld.performDiscreteCollisionDetection();

        playerCamera.update(true);
        overviewCamera.update();
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
        playerCamera = null;

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
