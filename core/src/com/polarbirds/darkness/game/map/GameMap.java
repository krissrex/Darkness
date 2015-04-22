package com.polarbirds.darkness.game.map;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.asset.Assets;
import com.polarbirds.darkness.game.map.impl.LineDrawingMapGeneratorStrat;
import com.polarbirds.darkness.graphics.ModelInstanceProvider;
import com.polarbirds.darkness.util.geom.IntPoint2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Rekstad on 22.04.2015.
 */
public class GameMap implements ModelInstanceProvider {

    private final MapGenerator mGenerator;
    private LineDrawingMapGeneratorStrat mStrat;
    private int mSize;

    private List<ModelInstance> mModelInstances;
    private IntPoint2 mStart;
    private IntPoint2 mEnd;

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
        mEnd.set(mGenerator.getMapBlocks().endPoint);
    }

    private void createInstances(){
        MapGenerator.GenerationResult result = mGenerator.getMapBlocks();
        mModelInstances.clear();
        for (MapBlock block : result.blocks){
            ModelInstance instance = new ModelInstance(getModelFromType(block.type));
            instance.transform.rotate(Vector3.Y, block.rotation);
            instance.transform.translate(block.position.x, 0, block.position.y);
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
}
