package com.polarbirds.darkness.game.map;

import com.polarbirds.darkness.util.collection.Grid;


/**
 * Created by Kristian Rekstad on 17.04.2015.
 * Generates a list of room positions
 */
public interface MapGeneratorStrategy {
    public Grid<Boolean> generateGrid(int size);
}
