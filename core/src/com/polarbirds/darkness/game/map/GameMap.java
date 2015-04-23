package com.polarbirds.darkness.game.map;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.Debug;
import com.polarbirds.darkness.asset.Assets;
import com.polarbirds.darkness.game.map.impl.LineDrawingMapGeneratorStrat;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;
import com.polarbirds.darkness.util.geom.IntPoint2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Rekstad on 22.04.2015.
 */
public class GameMap implements ModelInstanceProvider, Disposable {

    private final MapGenerator mGenerator;
    private LineDrawingMapGeneratorStrat mStrat;
    private int mSize;

    private List<ModelInstance> mModelInstances;
    private IntPoint2 mStart;
    private IntPoint2 mEnd;

    private btCollisionShape mCollisionShape;
    private btRigidBody mRigidBody;

    /**
     * @see LineDrawingMapGeneratorStrat#generate(int, int)
     * @see MapGenerator#MapGenerator(MapGeneratorStrategy, int)
     * @param size grid size
     * @param iterations number of iterations to generate lines
     */
    public GameMap(int size, int iterations){
        mSize = size;
        mStrat = new LineDrawingMapGeneratorStrat(iterations);
        mGenerator = new MapGenerator(mStrat, mSize);
        mModelInstances = new ArrayList<>((int) (size*iterations*0.25));
        mStart = new IntPoint2();
        mEnd = new IntPoint2();
    }

    public void setSize(int size){
        mSize = size;
    }

    public int getSize(){
        return mSize;
    }

    public void generate(){
        mGenerator.generate();
        createInstances();
        mStart.set(mGenerator.getMapBlocks().startPoint);
        mStart.scale(10);
        mEnd.set(mGenerator.getMapBlocks().endPoint);
        mEnd.scale(10);
    }

    private void createInstances(){
        MapGenerator.GenerationResult result = mGenerator.getMapBlocks();

        if (Debug.DEBUG){
            char map[][] = new char[mSize][mSize];
            for (int y = 0; y < mSize; y++) {
                for (int x = 0; x < mSize; x++) {
                    map[y][x] = ' ';
                }
            }
            for (MapBlock block : result.blocks){
                map[(int)block.position.y][(int)block.position.x] = block.type.name().charAt(0);
            }

            for (int y = 0; y < mSize; y++) {
                for (int x = 0; x < mSize; x++) {
                    System.out.print(map[y][x]);
                }
                System.out.print("\n");
            }
        }


        mModelInstances.clear();
        for (MapBlock block : result.blocks){
            ModelInstance instance = new ModelInstance(getModelFromType(block.type));
            instance.transform.idt();
            instance.transform.setToTranslation(block.position.x*10, 0f, block.position.y*10);
            instance.transform.rotate(Vector3.Y, block.rotation);
            if (Debug.DEBUG){
                System.out.println("Instance for: "+ block.type.name()+ "\t"+block.position+"\t"+block.rotation);
            }
            instance.calculateTransforms();
            mModelInstances.add(instance);
        }
    }

    private Model getModelFromType(MapBlock.BlockType type){
        String key=null;

        switch (type){
            case HALL:
                key = Assets.model.map_I; break;
            case CORNER:
                key = Assets.model.map_L; break;
            case JUNCTION:
                key = Assets.model.map_T; break;
            case CROSS:
                key = Assets.model.map_X; break;
            case ROOM:
                key = Assets.model.map_room; break;
            case BIG_ROOM:
                key = Assets.model.map_room; break; //FIXME set to boss room
        }

        return DarknessGame.ASSET_MANAGER.get(key);
    }


    public void setSeed(long seed){
        mStrat.setSeed(seed);
    }

    /**
     * @return a copy of the start point
     */
    public IntPoint2 getStart(){
        return new IntPoint2(mStart.x, mStart.y);
    }

    /**
     * @return a copy of the end point
     */
    public IntPoint2 getEnd(){
        return new IntPoint2(mEnd.x, mEnd.y);
    }

    @Override
    public List<ModelInstance> getModelInstances() {
        return mModelInstances;
    }


    // physics stuff

    public void createPhysicsStuff(){

        if (mCollisionShape != null){
            mCollisionShape.dispose();
            mCollisionShape = null;
        }

        btCompoundShape shape = new btCompoundShape();
        for(ModelInstance instance : mModelInstances){
            shape.addChildShape(instance.transform, Bullet.obtainStaticNodeShape(instance.nodes));
        }

        mCollisionShape = shape;

        if (mRigidBody != null){
            mRigidBody.dispose();
            mRigidBody = null;
        }

        mRigidBody = new btRigidBody(0f, null, mCollisionShape);
    }

    public btRigidBody getRigidBody(){
        return mRigidBody;
    }

    @Override
    public void dispose() {
        if (mCollisionShape != null) mCollisionShape.dispose();
        if (mRigidBody != null) mRigidBody.dispose();
    }
}
