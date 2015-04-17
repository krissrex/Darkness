package com.polarbirds.darkness.util.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 */
public class Grid<T> implements Iterable<T>{


    /**
     * Iterates by row first
     * <pre>{@code
     * [[1,2,3],
     * [4,5,6],
     * [7,8,9],
     * [10,11,12]]
     * }
     * </pre>
     */
    private class GridIterator implements Iterator<T>{
        private int row = 0, col = 0;

        @Override
        public boolean hasNext() {
            return (row < mSize && col < mSize);
        }

        @Override
        public T next() {
            T next = get(col, row);
            col++;
            if (col == mSize){
                col = 0;
                row++;
            }
            return next;
        }
    }



    /**
     * A list of rows. <br>
     * mGrid[y][x]
     * <pre>{@code
     * [ [x1, x2, ..., x3],
     *   [x1, x2, ..., x3],
     *   ...,
     *   [x1, x2, ..., x3]
     * ]
     * }</pre>
     */
    private List<List<T>> mGrid;
    private int mSize;


    public Grid(int size){
        mSize = size;

        mGrid = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            mGrid.add(new ArrayList<T>(size));
        }
    }


    public void set(T element, int x, int y){
        mGrid.get(y).set(x, element);
    }

    public T get(int x, int y){
        return mGrid.get(y).get(x);
    }

    @Override
    public Iterator<T> iterator() {
        return new GridIterator();
    }

    public int getSize(){
        return mSize;
    }
}
