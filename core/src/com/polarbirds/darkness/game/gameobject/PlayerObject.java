package com.polarbirds.darkness.game.gameobject;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.asset.Assets;
import com.polarbirds.darkness.game.GameWorld;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class PlayerObject extends GameObject {

    PerspectiveCamera camera;
    Matrix4 transform;
    ModelInstance playerModel;

    public PlayerObject(GameWorld world, PerspectiveCamera camera){
        super(world);
        this.camera = camera;
        //camera.position.set(0f, 1f, 0f);
        //camera.direction.set(0f, 0f, -1f);

        transform = new Matrix4(new Vector3(0f, 1f, 0f), new Quaternion(), new Vector3(1f, 1f, 1f));
        playerModel = new ModelInstance(DarknessGame.ASSET_MANAGER.get(Assets.model.map_room, Model.class));
        mModels.add(playerModel);

        collisionObject = new btCollisionObject();
        //collisionObject.setWorldTransform(transform);

        //btCollisionShape collisionShape = new btBoxShape(new Vector3(0.5f, 1f, 0.5f));

        btCollisionShape collisionShape = Bullet.obtainStaticNodeShape(playerModel.nodes);
        //btKinematicCharacterController ctrl = new btKinematicCharacterController(null, null, 0.5f);

        collisionObject.setCollisionShape(collisionShape);
        //Quaternion rot = new Quaternion(Vector3.Y, 90f);
        //collisionObject.setWorldTransform(new Matrix4(Vector3.Zero, rot, new Vector3(1f,1f,1f)));

    }

    @Override
    public void dispose() {
        super.dispose();
        camera = null;
        world = null;
    }

    Vector3 tempVec1 = new Vector3();
    //float dt;

    @Override
    public void update(float deltaTime) {
        //transform.rotate(Vector3.Y, 10*deltaTime);
        //camera.rotate(Vector3.Y, 10*deltaTime);
        transform.getTranslation(tempVec1);
        //camera.position.set(tempVec1);


        //camera.rotateAround(Vector3.Y, Vector3.Y, 15*deltaTime);
        //camera.lookAt(Vector3.Zero);
        //dt += deltaTime;
        //if (dt>=6.28){dt=0;}

        //collisionObject.setWorldTransform(transform);
    }

}
