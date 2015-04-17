package com.polarbirds.darkness.game.map;

import com.polarbirds.darkness.util.collection.Grid;
import com.polarbirds.darkness.util.collection.GridUtil;
import com.polarbirds.darkness.util.geom.IntPoint2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Rekstad on 17.04.2015. <br>
 * Generates a collection of {@link MapBlock}.
 */
public class MapGenerator {

    private MapGeneratorStrategy mStrategy;
    private int mSize;
    private List<MapBlock> mGenerated;


    private static class Neighbors{
        public int count;
        /** [above, below, left, right] */
        boolean neighbors[] = {false, false, false, false};
    }



    /**
     * Creates a MapGenerator with size 38.
     * @see MapGenerator#MapGenerator(MapGeneratorStrategy, int)
     */
    public MapGenerator(MapGeneratorStrategy strategy){
        this(strategy, 38);
    }

    /**
     *
     * @param strategy The strategy to generate a grid for rooms
     * @param size the size of the grid
     */
    public MapGenerator(MapGeneratorStrategy strategy, int size){
        mStrategy = strategy;
    }

    /**
     * Must not be run before generating the blocks.
     * @see MapGenerator#generate()
     */
    public List<MapBlock> getMapBlocks(){
        if (mGenerated == null){
            throw new IllegalStateException("Function MapGenerator#generate() must be run first!");
        }
        return mGenerated;
    }


    /**
     * Generates the {@link MapBlock} collection using the provided {@link MapGeneratorStrategy}.
     */
    public void generate(){
        List<MapBlock> generatedMapBlocks = new ArrayList<>();

        Grid<Boolean> boolGrid = mStrategy.generateGrid(mSize);
        GridUtil<Boolean> gridUtil = new GridUtil<>(boolGrid);

        Grid<Boolean>.GridIterator iterator = boolGrid.new GridIterator();
        while (iterator.hasNext()){
            Boolean cell = iterator.next();
            if (cell == null){
                continue;
            }

            IntPoint2 position = iterator.getCurrentPosition();
            if (cell.booleanValue()){
                // Cell contains room
                Neighbors neighbors = countTrueNeighbors(gridUtil, position.x, position.y);

                MapBlock.BlockType blockType = MapBlock.BlockType.HALL; //default initialization
                switch (neighbors.count){
                    case 1:
                        blockType = MapBlock.BlockType.ROOM;
                        break;
                    case 2:
                        blockType = determineHallOrCorner(neighbors);
                        break;
                    case 3:
                        blockType = MapBlock.BlockType.JUNCTION;
                        break;
                    case 4:
                        blockType = MapBlock.BlockType.CROSS;
                        break;
                }
                if (position.x == mStrategy.getStartPoint().x
                        && position.y == mStrategy.getStartPoint().y){
                    blockType = MapBlock.BlockType.ROOM;
                } else if (position.x == mStrategy.getEndPoint().x
                        && position.y == mStrategy.getEndPoint().y){
                    blockType = MapBlock.BlockType.BIG_ROOM;
                }
                MapBlock block = new MapBlock(position.x, position.y, blockType);
                generatedMapBlocks.add(block);
            }
        }

        mGenerated = generatedMapBlocks;
    }


    private MapBlock.BlockType determineHallOrCorner(Neighbors neighbors){
        if (neighbors.count != 2) throw new IllegalArgumentException("Neighbor count must be exactly 2.");

        if (neighbors.neighbors[0] && neighbors.neighbors[1]){
            return MapBlock.BlockType.HALL;
        }

        if (neighbors.neighbors[2] && neighbors.neighbors[3]) {
            return MapBlock.BlockType.HALL;
        }

        return MapBlock.BlockType.CORNER;
    }


    private final GridUtil.Position positions[] = {GridUtil.Position.ABOVE, GridUtil.Position.BELOW,
            GridUtil.Position.LEFT, GridUtil.Position.RIGHT};
    private Neighbors countTrueNeighbors(GridUtil<Boolean> gridUtil, int x, int y){
        int neighborCount = 0;
        Neighbors neighbors = new Neighbors();

        for (int i = 0; i < positions.length; i++) {
            Boolean cell = gridUtil.getNeighbor(x, y, GridUtil.Position.ABOVE);
            if (cell != null && cell.booleanValue()){
                neighborCount++;
                neighbors.neighbors[i] = true;
                /*Index depends on that MapGenerator.positions matches declaration
                  in MapGenerator$Neighbors.neighbors */
            }
        }
        neighbors.count = neighborCount;

        return neighbors;
    }

}
