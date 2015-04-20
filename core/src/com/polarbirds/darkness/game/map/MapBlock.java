package com.polarbirds.darkness.game.map;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 * Data oriented class for map blocks.
 */
public class MapBlock {

    /**
     * Map shape correspondence: <br/>
     * HALL - I <br/> CORNER - L <br/> JUNCTION - T <br/> CROSS - X
     */
    public enum BlockType {
        HALL, CORNER, JUNCTION, CROSS, ROOM, BIG_ROOM
    }

    /**
     * Rotation around the up axis in degrees.
     */
    public float rotation;

    public Vector2 position;

    /**
     *
     */
    public BlockType type;

    /**
     * Initializes with rotation 0. <br/>
     * Initialises the position {@link Vector2} to x, y <br/>
     * @see MapBlock#MapBlock(Vector2, BlockType, float)
     */
    public MapBlock(int x, int y, BlockType type){
        this(new Vector2(x, y), type, 0f);
    }

    /**
     * Initialises the position {@link Vector2} to x, y <br/>
     * @see MapBlock#MapBlock(Vector2, BlockType, float)
     */
    public MapBlock(int x, int y, BlockType type, float rotation){
        this(new Vector2(x,y), type, rotation);
    }

    /**
     * The type of the block determines its rendered shape. <br/>
     * @param position Position in 2d space (x,y)
     * @param type Room type, depending on 3D model of the room
     * @param rotation Rotation around the up axis
     */
    public MapBlock(Vector2 position, BlockType type, float rotation){
        this.position = position;
        this.type = type;
        this.rotation = rotation;
    }
}
