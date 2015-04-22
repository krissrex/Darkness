package com.polarbirds.darkness.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;

import java.util.List;

/**
 * Created by Kristian Rekstad on 22.04.2015.
 */
public class Minimap {

    private final OrthographicCamera overviewCamera;
    private int overviewX, overviewY, overviewSize;

    public Minimap(){
        overviewCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        overviewCamera.direction.set(0, -1f, 0);
        overviewCamera.up.set(0, 0, 1f);
        overviewCamera.position.set(0, 3f, 0);
        overviewCamera.viewportWidth = 100;
        overviewCamera.viewportHeight = 100;
        overviewCamera.zoom = 0.3f;
        overviewCamera.update();
    }


    private void setOverviewPosition(int width, int height){
        if (width > 500 && height > 500){
            overviewSize = 100;
        } else {
            overviewSize = (int)(Math.min(width, height) * 0.2f);
        }
        overviewX = width-overviewSize-10;
        overviewY = (int)(height*0.95f)-overviewSize;
    }

    public void resize(int width, int height){
        setOverviewPosition(width, height);
    }

    public void update(Vector3 playerPosition, Vector3 playerDirection){
        updateMapCamera(playerPosition, playerDirection);
    }


    private float oldOrthoX;
    private float oldOrthoZ;

    private void updateMapCamera(Vector3 playerPosition, Vector3 playerDirection){
        boolean dirty = false;
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)){
            dirty |= zoomMap(-0.1f);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.K)){
            dirty |= zoomMap(0.1f);
        }

        if (playerPosition.x != overviewCamera.position.x
                || playerPosition.z != overviewCamera.position.z){
            overviewCamera.position.x = playerPosition.x;
            overviewCamera.position.z = playerPosition.z;
            dirty = true;
        }

        if (oldOrthoX != playerDirection.x || oldOrthoZ != playerDirection.z){
            oldOrthoX = playerDirection.x;
            oldOrthoZ = playerDirection.z;
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


    /**
     * The batch should not have called {@link ModelBatch#begin(Camera)} already.
     * @param batch
     * @param mapRenderables the renderables that compose the map
     */
    public void render(ModelBatch batch, List<ModelInstanceProvider> mapRenderables){
        Gdx.gl.glViewport(overviewX, overviewY, overviewSize, overviewSize);
        batch.begin(overviewCamera);
        for (ModelInstanceProvider provider : mapRenderables){
            for (ModelInstance model : provider.getModelInstances()){
                batch.render(model);
            }
        }
        batch.end();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

}
