package com.polarbirds.darkness.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Kristian Rekstad on 23.04.2015.
 */
public class WeaponRenderer {

    private ModelBatch mBatch;
    private Environment mWeaponEnvironment;
    private PointLight mLight;

    private PerspectiveCamera mWeaponCamera;

    public WeaponRenderer(ModelBatch batch) {
        mBatch = batch;
        mWeaponEnvironment = new Environment();
        mWeaponEnvironment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));

        mLight = new PointLight();
        mLight.set(Color.WHITE, new Vector3(2f, 0f, 0.4f), 0.5f);
        mWeaponEnvironment.add(mLight);

        mWeaponCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mWeaponCamera.near = 0.001f;
        mWeaponCamera.far = 10f;
        mWeaponCamera.lookAt(Vector3.X);
        mWeaponCamera.update();
    }

    public void setLight(boolean on){
        if (on){
            mLight.intensity = 2f;
        } else {
            mLight.intensity = 0.7f;
        }
    }

    /**
     * Do not begin the batch before calling render.
     * @param provider
     */
    public void render(ModelInstanceProvider provider){
        mBatch.begin(mWeaponCamera);
        for (ModelInstance model : provider.getModelInstances()){
            mBatch.render(model, mWeaponEnvironment);
        }
        mBatch.end();
    }

    public void resize(int width, int height){
        mWeaponCamera.viewportWidth = width;
        mWeaponCamera.viewportHeight = height;
        mWeaponCamera.update(true);
    }
}
