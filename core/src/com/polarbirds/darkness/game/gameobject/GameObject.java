package com.polarbirds.darkness.game.gameobject;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.darkness.game.GameWorld;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public abstract class GameObject implements ModelInstanceProvider, Disposable{

    public int health;
    public float speed;
    public btCollisionObject collisionObject;
    public GameWorld world;
    protected List<ModelInstance> mModels;

    public GameObject(GameWorld world){
        mModels = new ArrayList<>();
        health = 100;
        speed = 5;
        this.world = world;
    }


    @Override
    public void dispose() {
        if (collisionObject != null) collisionObject.dispose();
    }

    public boolean hurt(int amount){
        health -= amount;
        return health <= 0;
    }

    public abstract void update(float deltaTime);

    @Override
    public List<ModelInstance> getModelInstances() {
        return mModels;
    }
}
