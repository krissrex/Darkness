package com.polarbirds.darkness.util.collection;

import com.polarbirds.darkness.util.geom.IntPoint2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kristian Rekstad on 17.04.2015.<br/>
 * The top left point is (0,0).<br/>
 * The bottom right point is (size-1, size-1)
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
    public class GridIterator implements Iterator<T>{
        private int row = 0, col = 0;

        @Override
        public boolean hasNext() {
            return (row < mSize && col < mSize);
        }

        /**
         * May return {@code null} <br/>
         * {@inheritDoc}
         */
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

        /** Note that calling {@link GridIterator#next()}
         * changes the current position.
         * @see GridIterator#next()
         */
        public IntPoint2 getCurrentPosition(){
            return new IntPoint2(col, row);
        }
    }



    /**
     * A list of rows. <br/>
     * The top left point is (0,0)<br/>
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


    /**
     * Initializes a square grid of size {@code size} with {@code null}.
     * @param size grid size
     */
    public Grid(int size){
        mSize = size;

        mGrid = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<T> row = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                row.add(null);
            }
            mGrid.add(row);
        }
    }


    public void set(T element, int x, int y){
        mGrid.get(y).set(x, element);
    }

    public T get(IntPoint2 point){
        return get(point.x, point.y);
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


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (List<T> row : mGrid){
            for (T cell : row){
                if (cell == null){
                    b.append(' ');
                } else {
                    b.append(cell);
                }
            }
            b.append('\n');
        }
        return b.toString();
    }
}
