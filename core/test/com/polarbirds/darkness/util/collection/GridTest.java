package com.polarbirds.darkness.util.collection;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 */
public class GridTest extends TestCase{
    @Test
    public void testGet() throws Exception {
        Grid<Integer> grid = new Grid<>(5);
        grid.set(1,0,0);
        grid.set(3,3,0);
        grid.set(4,0,4);
        assertEquals("Origin should be 1", 1, grid.get(0, 0).intValue());
        assertEquals("Pos (3,0) should be 3", 3, grid.get(3, 0).intValue());
        assertEquals("Pos (0,4) should be 4", 4, grid.get(0,4).intValue());
    }

    @Test
    public void testForIterator() throws Exception {
        Grid<Integer> grid = new Grid<>(3);
        int j = 1;

        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 3; x++)
            {
                grid.set(j, x, y);
                j++;
            }
        }
        int i = 0;
        for(Integer in : grid){
            assertEquals("Integer number "+i+" should be "+(i+1), i+1, in.intValue());
            i++;
        }
    }

    @Test
    public void testIterator() throws Exception {
        Grid<Integer> grid = new Grid<>(3);
        int j = 1;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++)
            {
                grid.set(j, x, y);
                j++;
            }
        }
        int i = 0;
        Grid<Integer>.GridIterator iterator = grid.new GridIterator();
        while (iterator.hasNext()){
            Integer cell = iterator.next();
            assertEquals("Integer number "+i+" should be "+(i+1), i+1, cell.intValue());
            i++;
        }
    }
}
