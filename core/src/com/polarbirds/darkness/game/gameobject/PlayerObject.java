package com.polarbirds.darkness.game.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.asset.Assets;
import com.polarbirds.darkness.game.GameWorld;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class PlayerObject extends GameObject {

    PerspectiveCamera camera;
    Matrix4 transform;
    ModelInstance playerModel;
    ModelInstance minimapModel;

    PerspectiveCamera weaponCamera;
    Model capsule;
    ModelInstance capsuleInstance;

    public PlayerObject(GameWorld world, PerspectiveCamera camera){
        super(world);
        this.camera = camera;

        transform = new Matrix4(new Vector3(0f, 1f, 0f), new Quaternion(), new Vector3(1f, 1f, 1f));
        playerModel = new ModelInstance(DarknessGame.ASSET_MANAGER.get(Assets.model.weapon_teslaGun, Model.class));
        mModels.add(playerModel);

        collisionObject = new btCollisionObject();
        //collisionObject.setWorldTransform(transform);

        //btCollisionShape collisionShape = new btBoxShape(new Vector3(0.5f, 1f, 0.5f));

        btCollisionShape collisionShape = Bullet.obtainStaticNodeShape(playerModel.nodes);
        //btKinematicCharacterController ctrl = new btKinematicCharacterController(null, null, 0.5f);

        collisionObject.setCollisionShape(collisionShape);
        //collisionObject.setWorldTransform(new Matrix4(Vector3.Zero, rot, new Vector3(1f,1f,1f)));

        ModelBuilder builder = new ModelBuilder();
        capsule = builder.createCapsule(0.5f, 1.8f, 6, new Material(ColorAttribute.createDiffuse(Color.RED)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        capsuleInstance = new ModelInstance(capsule);

    }

    @Override
    public void dispose() {
        super.dispose();
        camera = null;
        world = null;
        capsule.dispose();
    }

    Vector3 tempVec1 = new Vector3();
    //float dt;

    @Override
    public void update(float deltaTime) {
        //transform.rotate(Vector3.Y, 10*deltaTime);
        //camera.rotate(Vector3.Y, 10*deltaTime);
        //transform.getTranslation(tempVec1);

        //tempVec1.set(camera.position).add(camera.direction);

        //playerModel.transform.idt();
        //playerModel.transform.setToTranslation(camera.position);

        capsuleInstance.transform.setToTranslation(camera.position);

        //playerModel.transform.translate(camera.position);
        //camera.position.set(tempVec1);


        //camera.rotateAround(Vector3.Y, Vector3.Y, 15*deltaTime);
        //camera.lookAt(Vector3.Zero);
        //dt += deltaTime;
        //if (dt>=6.28){dt=0;}

        //collisionObject.setWorldTransform(transform);
    }

    private class MinimapInstanceProvider implements ModelInstanceProvider {
        public List<ModelInstance> models = new ArrayList<>();

        @Override
        public List<ModelInstance> getModelInstances() {
            return models;
        }
    }

    /** what a hack */
    public ModelInstanceProvider getMinimapModelProvider(){
        MinimapInstanceProvider provider = new MinimapInstanceProvider();
        provider.models.add(capsuleInstance);
        return provider;
    }
}
