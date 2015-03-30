package com.polarbirds.darkness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    private int overviewX, overviewY, overviewSize;

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
        overviewCamera.direction.set(0, -1f, 0);
        overviewCamera.up.set(0, 0, 1f);
        overviewCamera.position.set(0, 3f, 0);
        overviewCamera.viewportWidth = 100;
        overviewCamera.viewportHeight = 100;
        overviewCamera.zoom = 0.3f;
        overviewCamera.update();


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

    private void setOverviewPosition(){
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if (width > 500 && height > 500){
            overviewSize = 100;
        } else {
            overviewSize = (int)(Math.min(width, height) * 0.2f);
        }
        overviewX = width-overviewSize-10;
        overviewY = (int)(height*0.95f)-overviewSize;
    }


    public void resize(int width, int height){
        setOverviewPosition();
    }

    public void update(float deltaTime) {
        playerObject.update(deltaTime);
        collisionWorld.performDiscreteCollisionDetection();

        playerCamera.update(true);

        updateMapCamera();
    }


    float oldOrthoX;
    float oldOrthoZ;
    private void updateMapCamera(){
        boolean dirty = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)){
            dirty |= zoomMap(-0.1f);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.K)){
            dirty |= zoomMap(0.1f);
        }

        if (playerCamera.position.x != overviewCamera.position.x
                || playerCamera.position.z != overviewCamera.position.z){
            overviewCamera.position.x = playerCamera.position.x;
            overviewCamera.position.z = playerCamera.position.z;
            dirty = true;
        }

        if (oldOrthoX != playerCamera.direction.x || oldOrthoZ != playerCamera.direction.z){
            oldOrthoX = playerCamera.direction.x;
            oldOrthoZ = playerCamera.direction.z;
            overviewCamera.up.set(oldOrthoX, 0f, oldOrthoZ);
            overviewCamera.up.nor();
            dirty = true;
        }

        if (dirty){
            overviewCamera.update();
        }

    }

    private boolean zoomMap(float delta){
        float prev = overviewCamera.zoom;
        overviewCamera.zoom += delta;
        overviewCamera.zoom = MathUtils.clamp(overviewCamera.zoom, 0.1f, 1f);
        System.out.println("Zoom " + overviewCamera.zoom);
        return prev != overviewCamera.zoom;
    }

    public void render(){

        modelBatch.begin(playerCamera);
        playerObject.render(modelBatch);
        modelBatch.end();

        debugDrawer.begin(playerCamera);
        collisionWorld.debugDrawWorld();
        debugDrawer.end();


        Gdx.gl.glViewport(overviewX, overviewY, overviewSize, overviewSize);

        //Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
        modelBatch.begin(overviewCamera);
        playerObject.render(modelBatch);

        modelBatch.end();
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
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
