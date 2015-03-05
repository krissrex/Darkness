package com.polarbirds.darkness.gameobject;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class PlayerObject extends GameObject {

    PerspectiveCamera camera;
    Matrix4 translation;
    ModelInstance playerModel;

    public PlayerObject(PerspectiveCamera camera){
        super();
        this.camera = camera;
        translation = new Matrix4(Vector3.Y.cpy(), new Quaternion(), new Vector3(1f, 1f, 1f));

        collisionObject = new btCollisionObject();
        collisionObject.setWorldTransform(translation);

        btCollisionShape collisionShape = new btBoxShape(new Vector3(0.3f, 1.f, 0.3f));
        collisionObject.setCollisionShape(collisionShape);
    }

    @Override
    public void dispose() {
        super.dispose();
        camera = null;

    }

    @Override
    public void update(float deltaTime) {
        //camera.transform(translation);
        camera.lookAt(Vector3.Zero); //Fixme

        collisionObject.setWorldTransform(translation);
    }

    @Override
    public void render(ModelBatch batch) {

    }
}
