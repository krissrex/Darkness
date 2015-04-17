package com.polarbirds.darkness.util.collection;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 */
public class GridUtil<T> {

    public enum Position { ABOVE, BELOW, LEFT, RIGHT }

    private Grid<T> mGrid;
    public GridUtil(Grid<T> grid){
        mGrid = grid;
    }

    /**
     *
     * @param x element's x (col)
     * @param y element's y (row)
     * @param neighborPosition relative position of neighbor
     * @return neighbor, or null if position is invalid
     */
    public T getNeighbor(int x, int y, Position neighborPosition){
        int size = mGrid.getSize();
        if (!validatePosition(x, y)){
            throw new IndexOutOfBoundsException("Invalid point ("+x+", "+y+") in grid of size "+mGrid.getSize());
        }

        int dx = 0, dy = 0;

        switch (neighborPosition){
            case ABOVE:
                dy = -1;
                break;
            case BELOW:
                dy = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
        }

        if (validatePosition(x+dx, y+dy)){
            return mGrid.get(x+dx, y+dy);
        } else {
            return null;
        }
    }


    private boolean validatePosition(int x, int y){
        int size = mGrid.getSize();
        return !(x<0 || x>= size || y<0 || y>= size); // not ( invalid conditions )
    }

    public void setGrid(Grid<T> grid){
        mGrid = grid;
    }
    public Grid<T> getGrid(){
        return mGrid;
    }
}
