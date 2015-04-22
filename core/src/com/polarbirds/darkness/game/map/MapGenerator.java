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
    private GenerationResult mGenerated;


    private static class Neighbors{
        public int count;
        /** [above, below, left, right] */
        boolean neighbors[] = {false, false, false, false};
    }

    public static class GenerationResult{
        public final List<MapBlock> blocks;
        public final IntPoint2 startPoint;
        public final IntPoint2 endPoint;
        public final int startIndex, endIndex;

        public GenerationResult(List<MapBlock> blocks, IntPoint2 startPoint, IntPoint2 endPoint, int startIndex, int endIndex) {
            this.blocks = blocks;
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
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
        mSize = size;
    }

    /**
     * Must not be run before generating the blocks.
     * @see MapGenerator#generate()
     */
    public GenerationResult getMapBlocks(){
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

        IntPoint2 startPosition = new IntPoint2();
        IntPoint2 endPosition = new IntPoint2();
        int startIndex = 0, endIndex = 0;

        Grid<Boolean>.GridIterator iterator = boolGrid.new GridIterator();
        while (iterator.hasNext()){

            IntPoint2 position = iterator.getCurrentPosition();
            Boolean cell = iterator.next();
            if (cell == null){
                continue;
            }

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

                float rotation = determineRotation(neighbors, blockType);

                // Set the start and end rooms

                if (position.x == mStrategy.getStartPoint().x && position.y == mStrategy.getStartPoint().y){
                    blockType = MapBlock.BlockType.ROOM;
                    startPosition = new IntPoint2(position.x, position.y);
                    startIndex = generatedMapBlocks.size();
                } else if (position.x == mStrategy.getEndPoint().x && position.y == mStrategy.getEndPoint().y){
                    blockType = MapBlock.BlockType.BIG_ROOM;
                    endPosition = new IntPoint2(position.x, position.y);
                    endIndex = generatedMapBlocks.size();
                }

                // Create the block and add it to the result
                MapBlock block = new MapBlock(position.x, position.y, blockType, rotation);
                generatedMapBlocks.add(block);
            }
        }

        mGenerated = new GenerationResult(generatedMapBlocks, startPosition, endPosition, startIndex, endIndex);
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


    private float determineRotation(Neighbors neighbors, MapBlock.BlockType type){
        // Junction is?  on 0
        // Corner is ? on o
        // Cross is ? on 0
        // Room is ?? on 0
        // Big room should be equal to room
        // Hall is | on 0
        switch (type){
            case HALL:
                if (neighbors.neighbors[2]) return 90f;
                break;
            case BIG_ROOM:
            case ROOM:
                if (neighbors.neighbors[0]) return 90f;
                if (neighbors.neighbors[1]) return 270f;
                if (neighbors.neighbors[2]) return 180f;
                break;
            case CORNER:
                if (neighbors.neighbors[0] && neighbors.neighbors[3]) return 90f;
                if (neighbors.neighbors[0] && neighbors.neighbors[2]) return 180f;
                if (neighbors.neighbors[2] && neighbors.neighbors[1]) return 270f;
                break;
            case JUNCTION:
                if (neighbors.neighbors[0] && neighbors.neighbors[1] && neighbors.neighbors[2]) return 180f;
                if (neighbors.neighbors[2] && neighbors.neighbors[3]){
                    if (neighbors.neighbors[0]) return 90f;
                    if (neighbors.neighbors[1]) return 270f;
                }
                break;
        }

        return 0f;
    }

    private final GridUtil.Position positions[] = {GridUtil.Position.ABOVE, GridUtil.Position.BELOW,
            GridUtil.Position.LEFT, GridUtil.Position.RIGHT};

    private Neighbors countTrueNeighbors(GridUtil<Boolean> gridUtil, int x, int y){
        int neighborCount = 0;
        Neighbors neighbors = new Neighbors();

        for (int i = 0; i < positions.length; i++) {
            Boolean cell = gridUtil.getNeighbor(x, y, positions[i]);
            //Debug: System.out.println("Testing for x: "+x +"\ty: "+y+"\t"+positions[i]);
            if (cell != null && cell){
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
