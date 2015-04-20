package com.polarbirds.darkness.game.map.impl;

import com.polarbirds.darkness.util.collection.Grid;
import junit.framework.TestCase;

/**
 * Created by Kristian Rekstad on 20.04.2015.
 */
public class LineDrawingMapGeneratorStratTest extends TestCase{


    private int mSize = 31;
    private int mIter = 28;
    private LineDrawingMapGeneratorStrat mStrat;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mStrat = new LineDrawingMapGeneratorStrat(mIter);
    }

    public void testSeed() throws Exception {
        long seed = 123153321l;
        mStrat.setSeed(seed);
        Grid<Boolean> firstGrid = mStrat.generateGrid(mSize);
        mStrat.setSeed(seed);
        Grid<Boolean> secondGrid = mStrat.generateGrid(mSize);

        Grid<Boolean>.GridIterator firstIter = firstGrid.new GridIterator();
        Grid<Boolean>.GridIterator secondIter = secondGrid.new GridIterator();


        while (firstIter.hasNext()){
            assertEquals("Grid value "+firstIter.getCurrentPosition(), firstIter.next(), secondIter.next());
        }

        assertFalse("Second iter should be empty", secondIter.hasNext());
    }

    public void testGenerate() throws Exception {
        Grid<Boolean> grid = mStrat.generateGrid(mSize);
        assertNotNull("Strat start point not null", mStrat.getStartPoint());
        assertNotNull("Strat end point not null", mStrat.getEndPoint());
        assertEquals("Strat start grid value should be true", true, grid.get(mStrat.getStartPoint()).booleanValue());
        assertEquals("Strat stop grid value should be true", true, grid.get(mStrat.getEndPoint()).booleanValue());
        System.out.println("Printing generated grid:\n");

        for (int y = 0; y < mSize; y++) {
            for (int x = 0; x < mSize; x++) {
                char val = grid.get(x, y)==null?' ' : (grid.get(x, y)? '1' : ' ');
                System.out.print(val);
            }
            System.out.print("\n");
        }
    }

    public void testDynamicIterations() throws Exception {
        mStrat.setIterations(0);


    }
}
