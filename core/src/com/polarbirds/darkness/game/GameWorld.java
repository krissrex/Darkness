package com.polarbirds.darkness.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.Debug;
import com.polarbirds.darkness.game.gameobject.PlayerObject;
import com.polarbirds.darkness.game.map.GameMap;
import com.polarbirds.darkness.game.map.Minimap;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;
import com.polarbirds.darkness.graphics.WeaponRenderer;
import com.polarbirds.darkness.input.FPSCameraController;
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
    public FPSCameraController cameraController;


    public Environment environment;
    public PerspectiveCamera playerCamera;
    private Minimap mMinimap;
    private WeaponRenderer mWeaponRenderer;
    PointLight gunLight;

    // Collision
    btDiscreteDynamicsWorld collisionWorld;
    DebugDrawer debugDrawer;
    btCollisionDispatcher dispatcher;
    btCollisionConfiguration collisionConfiguration;
    btDbvtBroadphase broadphase;
    btConstraintSolver solver;

    // Entities
    PlayerObject playerObject;
    private List<ModelInstanceProvider> minimapRenderables;
    GameMap gameMap;

    //Stuff
    private Vector3 tmpVec1 = new Vector3();
    private Vector3 tmpVec2 = new Vector3();


    public GameWorld(GameScreen game){
        // Bad programming
        this.game = game;
        modelBatch = game.game.modelBatch;
        this.playerCamera = game.playerCamera;


        // Camera controller
        cameraController = new FPSCameraController(playerCamera);
        DarknessGame.INPUT_MULTIPLEXER.addProcessor(cameraController);

        // Physics world
        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btDbvtBroadphase();
        solver = new btSequentialImpulseConstraintSolver();
        collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);

        // Physics debug render
        debugDrawer = new DebugDrawer();
        debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
        collisionWorld.setDebugDrawer(debugDrawer);

        // Player entity
        playerObject = new PlayerObject(this, playerCamera);

        collisionWorld.addCollisionObject(playerObject.collisionObject);

        // Minimap renderer
        mMinimap = new Minimap();
        minimapRenderables = new ArrayList<>();
        minimapRenderables.add(playerObject.getMinimapModelProvider());

        // Game map
        gameMap = new GameMap(31, 28);
        gameMap.generate();
        minimapRenderables.add(gameMap);
        gameMap.createPhysicsStuff();
        collisionWorld.addRigidBody(gameMap.getRigidBody());

        // Set player position
        IntPoint2 start = gameMap.getStart();
        playerCamera.position.x = start.x;
        playerCamera.position.z = start.y;
        if (Debug.DEBUG){
            System.out.println("Player pos: " +start);
        }
        playerCamera.update();

        // Environment light
        tmpVec1.set(playerCamera.position).add(playerCamera.direction);
        gunLight = new PointLight().set(Color.YELLOW, tmpVec1, 0.f);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.set(new ColorAttribute(ColorAttribute.Fog, 0f, 0f, 0f, 1f));
        environment.add(gunLight);

        // Weapon renderer
        mWeaponRenderer = new WeaponRenderer(modelBatch);
    }




    public void resize(int width, int height){
        cameraController.resized(width, height);
        mMinimap.resize(width, height);
        mWeaponRenderer.resize(width, height);
    }

    public void update(float deltaTime) {
        cameraController.update(deltaTime);
        playerObject.update(deltaTime);

        collisionWorld.performDiscreteCollisionDetection();

        updateLightPosition();
        if (Gdx.input.justTouched()){
            System.out.println("touched");
            gunLight.intensity = 2f;
            mWeaponRenderer.setLight(true);
        }

        mMinimap.update(playerCamera.position, playerCamera.direction);
    }

    private void updateLightPosition(){
        tmpVec1.set(playerCamera.position);
        tmpVec2.set(playerCamera.direction).nor().scl(0.3f);
        tmpVec1.add(tmpVec2);
        gunLight.position.set(tmpVec1);
    }

    public void render(){
        modelBatch.begin(playerCamera);
        for (ModelInstance model : gameMap.getModelInstances()){
            model.transform.getTranslation(tmpVec1);
            if (isMapPieceVisible(tmpVec1, playerCamera)) {
                modelBatch.render(model, environment);
            }
        }
        modelBatch.end();


        if (Debug.DEBUG && false){ //fixme disabled
            debugDrawer.begin(playerCamera);
            collisionWorld.debugDrawWorld();
            debugDrawer.end();
        }

        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT); //Fixme: could cause trouble
        mWeaponRenderer.render(playerObject);

        mMinimap.render(modelBatch, minimapRenderables);
    }

    @Override
    public void dispose() {
        DarknessGame.INPUT_MULTIPLEXER.removeProcessor(cameraController);
        game = null;
        modelBatch = null;
        playerCamera = null;

        collisionWorld.dispose();
        debugDrawer.dispose();
        dispatcher.dispose();
        collisionConfiguration.dispose();
        collisionConfiguration.dispose();

        playerObject.dispose();
        gameMap.dispose();
    }

    private boolean isMapPieceVisible(Vector3 position, Camera camera){
        return camera.frustum.sphereInFrustum(position, 10f); //boundsInFrustum(position, mapBlockDimension);
    }

    /** Allows the environment to change. */
    public Environment getPlayerEnvironment(){
        return environment;
    }
}
