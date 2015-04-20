package com.polarbirds.darkness.game.map.impl;

import com.polarbirds.darkness.game.map.MapGeneratorStrategy;
import com.polarbirds.darkness.util.collection.Grid;
import com.polarbirds.darkness.util.geom.IntPoint2;

import java.util.Random;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 */
public class LineDrawingMapGeneratorStrat implements MapGeneratorStrategy{

    private int mIterations;
    private boolean mDynamicIterations = false;
    private Random mRandom;

    private IntPoint2 mStart = new IntPoint2();
    private IntPoint2 mStop = new IntPoint2();

    /**
     * Sets dynamic iterations enabled, by setting iterations to {@code -1}
     */
    public LineDrawingMapGeneratorStrat(){
        this(-1);
    }

    /**
     * Dynamic iterations are calculated by
     * <pre>{@code iterations = random.randint(15+int(math.sqrt(size)), 15+3*int(math.sqrt(size)))}</pre>
     * @param iterations the number of line drawing iterations. 0 or below sets it to dynamic
     */
    public LineDrawingMapGeneratorStrat(int iterations){
        mIterations = iterations;
        mDynamicIterations = !(iterations<=0); // true if negative
        mRandom = new Random();
    }

    public void setIterations(int iterations) {
        this.mIterations = iterations;
        mDynamicIterations = !(iterations<=0);
    }

    public void setSeed(long seed){
        mRandom.setSeed(seed);
    }

    public void randomizeSeed(){
        mRandom.setSeed(System.currentTimeMillis());
        mRandom.setSeed(mRandom.nextLong());
    }


    private int getDynamicIterationCount(int size){
        int range = 2*(int)Math.sqrt(size);
        int rand = mRandom.nextInt(range);
        return 15+rand;
    }

    @Override
    public Grid<Boolean> generateGrid(int size) {
        if (size<=0) throw new IllegalArgumentException("Invalid size "+size+". Size must be greater than 0.");
        int iter = mDynamicIterations? getDynamicIterationCount(size) : mIterations;
        return generate(size, iter);
    }

    @Override
    public IntPoint2 getStartPoint() {
        return mStart;
    }

    @Override
    public IntPoint2 getEndPoint() {
        return mStop;
    }


    private Grid<Boolean> generate(int size, int iterations){
        Grid<Boolean> grid = new Grid<>(size);
        IntPoint2 start = new IntPoint2();
        IntPoint2 stop = new IntPoint2();

        start.x = mRandom.nextInt(size);
        stop.x = start.x;
        stop.y = excludingBoundedRandInt(size, start.y);
        //python mapgen.py line 62
        makePath(grid, start, stop);

        for (int i = 0; i < iterations; i++) {
            start.x = stop.x;
            start.y = stop.y;
            if (mRandom.nextBoolean()){
                stop.x = excludingBoundedRandInt(size, start.x);
            } else {
                stop.y = excludingBoundedRandInt(size, start.y);
            }
        }

        setStartAndEnd(grid, size);

        return grid;
    }

    private void setStartAndEnd(Grid<Boolean> grid, int size){
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Boolean cell = grid.get(x, y);
                if (cell != null && cell){
                    mStart.x = x;
                    mStart.y = y;
                    break;
                }
            }
        }

        for (int x = size-1; x >= 0; x--) {
            for (int y = size-1; y >= 0; y--) {
                Boolean cell = grid.get(x, y);
                if (cell != null && cell){
                    mStop.x = x;
                    mStop.y = y;
                }
            }
        }
    }


    public void makePath(Grid<Boolean> grid, IntPoint2 start, IntPoint2 stop){
        int dx = stop.x - start.x;
        int dy = stop.y - start.y;


        for (int x = 0; x < Math.abs(dx)+1; x++) {
            grid.set(true, start.x + (int)Math.copySign(x, dx), yFromX(x, start.y, dx, dy));
        }

        for (int y = 0; y < Math.abs(dy) + 1; y++) {
            grid.set(true, xFromY(y, start.x, start.y, dx, dy), start.y +  (int)Math.copySign(y, dy));
        }
    }

    private int yFromX(int x, int startY, int dx, int dy){
        int m = dx==0? 0 : dy/dx;
        return startY + (int)Math.floor(x*m);
    }

    private int xFromY(int y, int startX, int startY, int dx, int dy){
        int m = dy==0? 0 : dx/dy;
        return startX + (int)Math.floor((y-startY)*m);
    }


    /** @returns int in [0, range) except excluded. May return excluded value if range is 1*/
    private int excludingBoundedRandInt(int range, int excluded){
        if (range < 1) throw new IllegalArgumentException("Range must be bigger than 1. Got "+range);
        int val = mRandom.nextInt(range);
        if (val == excluded){
            if (val>0){
                val--;
            } else if (range > 1){
                val++;
            }
        }
        return val;
    }
}
