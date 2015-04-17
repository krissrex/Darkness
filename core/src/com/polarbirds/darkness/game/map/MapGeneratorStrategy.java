package com.polarbirds.darkness.game.map;

import com.polarbirds.darkness.util.collection.Grid;
import com.polarbirds.darkness.util.geom.IntPoint2;


/**
 * Created by Kristian Rekstad on 17.04.2015.<br/>
 * Generates a list of room positions
 */
public interface MapGeneratorStrategy {
    public Grid<Boolean> generateGrid(int size);

    /**
     * The start point is the topmost left point in the grid. Nothing can be above the point<br/>
     * The {@code x} shows the valid point for start position in a 4*4 grid.
     * <pre>{@code
     * |x1oo|
     * |o111|
     * |oooo|
     * |oooo|
     * }</pre>
     */
    public IntPoint2 getStartPoint();

    /**
     * The end point is the bottom rightmost point in the grid. Nothing can be to the right of the point<br/>
     * The {@code x} shows the valid point for end position in a 4*4 grid.
     * <pre>{@code
     * |11oo|
     * |o11x|
     * |oooo|
     * |oooo|
     * }</pre>
     */
    public IntPoint2 getEndPoint();
}
