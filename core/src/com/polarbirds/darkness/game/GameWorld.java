package com.polarbirds.darkness.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.darkness.Debug;
import com.polarbirds.darkness.game.gameobject.PlayerObject;
import com.polarbirds.darkness.game.map.GameMap;
import com.polarbirds.darkness.game.map.Minimap;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;
import com.polarbirds.darkness.screen.GameScreen;
import com.polarbirds.darkness.util.geom.IntPoint2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class GameWorld implements Disposable{

    // Rendering
    GameScreen game;
    ModelBatch modelBatch;

    public Environment environment;
    public PerspectiveCamera playerCamera;
    private Minimap minimap;
    private PerspectiveCamera weaponCamera;

    // Collision
    btCollisionWorld collisionWorld;
    DebugDrawer debugDrawer;
    btCollisionDispatcher dispatcher;
    btCollisionConfiguration collisionConfiguration;
    btDbvtBroadphase broadphase;

    // Entities
    PlayerObject playerObject;
    private List<ModelInstanceProvider> minimapRenderables;
    GameMap gameMap;



    public GameWorld(GameScreen game){
        this.game = game;
        modelBatch = game.game.modelBatch;
        this.playerCamera = game.playerCamera;

        weaponCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        weaponCamera.near = 0.001f;
        weaponCamera.far = 10f;
        weaponCamera.lookAt(Vector3.X);
        weaponCamera.update();

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

        minimap = new Minimap();
        minimapRenderables = new ArrayList<>();
        minimapRenderables.add(playerObject.getMinimapModelProvider());

        gameMap = new GameMap(31, 28);
        gameMap.generate();
        minimapRenderables.add(gameMap);


        IntPoint2 start = gameMap.getStart();
        playerCamera.position.x = start.x;
        playerCamera.position.z = start.y;
        if (Debug.DEBUG){
            System.out.println("Player pos: " +start);
        }
        playerCamera.update();
    }




    public void resize(int width, int height){
        minimap.resize(width, height);
        weaponCamera.viewportWidth = width;
        weaponCamera.viewportHeight = height;
        weaponCamera.update(true);
    }

    public void update(float deltaTime) {
        playerObject.update(deltaTime);

        collisionWorld.performDiscreteCollisionDetection();

        minimap.update(playerCamera.position, playerCamera.direction);
    }

    public void render(){

        modelBatch.begin(playerCamera);
        for (ModelInstance model : gameMap.getModelInstances()){
            modelBatch.render(model); //Fixme: add environment
        }
        modelBatch.end();

        debugDrawer.begin(playerCamera);
        collisionWorld.debugDrawWorld();
        debugDrawer.end();

        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(weaponCamera);
        for (ModelInstance model : playerObject.getModelInstances()){
            modelBatch.render(model, environment);
        }
        modelBatch.end();

        minimap.render(modelBatch, minimapRenderables);
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
