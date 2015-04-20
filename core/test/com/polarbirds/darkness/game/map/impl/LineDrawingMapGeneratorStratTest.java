package com.polarbirds.darkness.game.map.impl;

import com.polarbirds.darkness.util.collection.Grid;
import com.polarbirds.darkness.util.geom.IntPoint2;
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
        printGrid(grid, mStrat.getStartPoint(), mStrat.getEndPoint());
    }

    public void testDynamicIterations() throws Exception {
        mStrat.setIterations(0);
        System.out.println("Generating grid of size 10, iter 0");
        printGrid(mStrat.generateGrid(10), mStrat.getStartPoint(), mStrat.getEndPoint());
        mStrat.setIterations(-1);
        System.out.println("Generating grid of size 40, iter -1");
        printGrid(mStrat.generateGrid(40), mStrat.getStartPoint(), mStrat.getEndPoint());
        System.out.println("Generating grid of size 60, iter -1");
        printGrid(mStrat.generateGrid(60), mStrat.getStartPoint(), mStrat.getEndPoint());

    }

    private void printGrid(Grid<Boolean> grid, IntPoint2 start, IntPoint2 end){
        for (int y = 0; y < grid.getSize(); y++) {
            for (int x = 0; x < grid.getSize(); x++) {
                char val = grid.get(x, y)==null?' ' : (grid.get(x, y)? '1' : ' ');
                if (x == start.x && y == start.y) val = 'S';
                if (x == end.x && y == end.y) val = 'E';
                System.out.print(val);
            }
            System.out.print("\n");
        }
    }
}
