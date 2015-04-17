package com.polarbirds.darkness.game.map;

import com.polarbirds.darkness.util.collection.Grid;
import com.polarbirds.darkness.util.geom.IntPoint2;
import junit.framework.TestCase;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 */
public class MapGeneratorTest extends TestCase{

    public void testGenerate() throws Exception {
        MapGenerator generator = new MapGenerator(new TestGeneratorStrat(), 31);
        generator.generate();
        MapGenerator.GenerationResult result = generator.getMapBlocks();
        assertEquals("Start room index should be 0", 0, result.startIndex);
        assertEquals("End room index should be 114", 114, result.endIndex);
        assertEquals("Start should be room", MapBlock.BlockType.ROOM.name(), result.blocks.get(result.startIndex).type.name());
        assertEquals("Start should be room", MapBlock.BlockType.BIG_ROOM.name(), result.blocks.get(result.endIndex).type.name());

    }

    private static class TestGeneratorStrat implements MapGeneratorStrategy {
        @Override
        public Grid<Boolean> generateGrid(int size) {
            if (size != 31) throw new IllegalArgumentException("Only 31 is valid");
            return generatedGrid;
        }

        @Override
        public IntPoint2 getStartPoint() {
            return new IntPoint2(11, 0);
        }

        @Override
        public IntPoint2 getEndPoint() {
            return new IntPoint2(21, 21);
        }

        /** Generated with /docs/sketch/static_gen.py size 31, iter 24, unknown seed*/
        private static final Grid<Boolean> generatedGrid = new Grid<Boolean>(31);
        static{
            generatedGrid.set(true, 11, 0);
            generatedGrid.set(true, 11, 1);
            generatedGrid.set(true, 11, 2);
            generatedGrid.set(true, 18, 2);
            generatedGrid.set(true, 4, 3);
            generatedGrid.set(true, 5, 3);
            generatedGrid.set(true, 6, 3);
            generatedGrid.set(true, 7, 3);
            generatedGrid.set(true, 8, 3);
            generatedGrid.set(true, 9, 3);
            generatedGrid.set(true, 10, 3);
            generatedGrid.set(true, 11, 3);
            generatedGrid.set(true, 12, 3);
            generatedGrid.set(true, 13, 3);
            generatedGrid.set(true, 14, 3);
            generatedGrid.set(true, 15, 3);
            generatedGrid.set(true, 16, 3);
            generatedGrid.set(true, 17, 3);
            generatedGrid.set(true, 18, 3);
            generatedGrid.set(true, 11, 4);
            generatedGrid.set(true, 18, 4);
            generatedGrid.set(true, 11, 5);
            generatedGrid.set(true, 18, 5);
            generatedGrid.set(true, 11, 6);
            generatedGrid.set(true, 18, 6);
            generatedGrid.set(true, 11, 7);
            generatedGrid.set(true, 18, 7);
            generatedGrid.set(true, 11, 8);
            generatedGrid.set(true, 18, 8);
            generatedGrid.set(true, 0, 9);
            generatedGrid.set(true, 1, 9);
            generatedGrid.set(true, 2, 9);
            generatedGrid.set(true, 3, 9);
            generatedGrid.set(true, 4, 9);
            generatedGrid.set(true, 5, 9);
            generatedGrid.set(true, 6, 9);
            generatedGrid.set(true, 7, 9);
            generatedGrid.set(true, 8, 9);
            generatedGrid.set(true, 9, 9);
            generatedGrid.set(true, 10, 9);
            generatedGrid.set(true, 11, 9);
            generatedGrid.set(true, 18, 9);
            generatedGrid.set(true, 0, 10);
            generatedGrid.set(true, 11, 10);
            generatedGrid.set(true, 18, 10);
            generatedGrid.set(true, 0, 11);
            generatedGrid.set(true, 11, 11);
            generatedGrid.set(true, 18, 11);
            generatedGrid.set(true, 0, 12);
            generatedGrid.set(true, 11, 12);
            generatedGrid.set(true, 18, 12);
            generatedGrid.set(true, 0, 13);
            generatedGrid.set(true, 11, 13);
            generatedGrid.set(true, 18, 13);
            generatedGrid.set(true, 0, 14);
            generatedGrid.set(true, 11, 14);
            generatedGrid.set(true, 18, 14);
            generatedGrid.set(true, 0, 15);
            generatedGrid.set(true, 11, 15);
            generatedGrid.set(true, 18, 15);
            generatedGrid.set(true, 0, 16);
            generatedGrid.set(true, 6, 16);
            generatedGrid.set(true, 7, 16);
            generatedGrid.set(true, 8, 16);
            generatedGrid.set(true, 9, 16);
            generatedGrid.set(true, 10, 16);
            generatedGrid.set(true, 11, 16);
            generatedGrid.set(true, 18, 16);
            generatedGrid.set(true, 0, 17);
            generatedGrid.set(true, 6, 17);
            generatedGrid.set(true, 10, 17);
            generatedGrid.set(true, 11, 17);
            generatedGrid.set(true, 18, 17);
            generatedGrid.set(true, 0, 18);
            generatedGrid.set(true, 6, 18);
            generatedGrid.set(true, 10, 18);
            generatedGrid.set(true, 11, 18);
            generatedGrid.set(true, 12, 18);
            generatedGrid.set(true, 13, 18);
            generatedGrid.set(true, 14, 18);
            generatedGrid.set(true, 15, 18);
            generatedGrid.set(true, 16, 18);
            generatedGrid.set(true, 18, 18);
            generatedGrid.set(true, 0, 19);
            generatedGrid.set(true, 6, 19);
            generatedGrid.set(true, 11, 19);
            generatedGrid.set(true, 16, 19);
            generatedGrid.set(true, 18, 19);
            generatedGrid.set(true, 0, 20);
            generatedGrid.set(true, 6, 20);
            generatedGrid.set(true, 11, 20);
            generatedGrid.set(true, 16, 20);
            generatedGrid.set(true, 17, 20);
            generatedGrid.set(true, 18, 20);
            generatedGrid.set(true, 0, 21);
            generatedGrid.set(true, 1, 21);
            generatedGrid.set(true, 2, 21);
            generatedGrid.set(true, 3, 21);
            generatedGrid.set(true, 4, 21);
            generatedGrid.set(true, 5, 21);
            generatedGrid.set(true, 6, 21);
            generatedGrid.set(true, 7, 21);
            generatedGrid.set(true, 8, 21);
            generatedGrid.set(true, 9, 21);
            generatedGrid.set(true, 10, 21);
            generatedGrid.set(true, 11, 21);
            generatedGrid.set(true, 12, 21);
            generatedGrid.set(true, 13, 21);
            generatedGrid.set(true, 14, 21);
            generatedGrid.set(true, 15, 21);
            generatedGrid.set(true, 16, 21);
            generatedGrid.set(true, 17, 21);
            generatedGrid.set(true, 18, 21);
            generatedGrid.set(true, 19, 21);
            generatedGrid.set(true, 20, 21);
            generatedGrid.set(true, 21, 21);
            generatedGrid.set(true, 0, 22);
            generatedGrid.set(true, 6, 22);
            generatedGrid.set(true, 11, 22);
            generatedGrid.set(true, 17, 22);
            generatedGrid.set(true, 20, 22);
            generatedGrid.set(true, 0, 23);
            generatedGrid.set(true, 1, 23);
            generatedGrid.set(true, 2, 23);
            generatedGrid.set(true, 3, 23);
            generatedGrid.set(true, 4, 23);
            generatedGrid.set(true, 5, 23);
            generatedGrid.set(true, 6, 23);
            generatedGrid.set(true, 7, 23);
            generatedGrid.set(true, 8, 23);
            generatedGrid.set(true, 9, 23);
            generatedGrid.set(true, 10, 23);
            generatedGrid.set(true, 11, 23);
            generatedGrid.set(true, 12, 23);
            generatedGrid.set(true, 13, 23);
            generatedGrid.set(true, 14, 23);
            generatedGrid.set(true, 15, 23);
            generatedGrid.set(true, 16, 23);
            generatedGrid.set(true, 17, 23);
            generatedGrid.set(true, 18, 23);
            generatedGrid.set(true, 19, 23);
            generatedGrid.set(true, 20, 23);
            generatedGrid.set(true, 6, 24);
            generatedGrid.set(true, 11, 24);
            generatedGrid.set(true, 17, 24);
            generatedGrid.set(true, 6, 25);
            generatedGrid.set(true, 11, 25);
            generatedGrid.set(true, 17, 25);
            generatedGrid.set(true, 6, 26);
            generatedGrid.set(true, 17, 26);
            generatedGrid.set(true, 6, 27);
            generatedGrid.set(true, 17, 27);
            generatedGrid.set(true, 6, 28);
            generatedGrid.set(true, 7, 28);
            generatedGrid.set(true, 8, 28);
            generatedGrid.set(true, 9, 28);
            generatedGrid.set(true, 10, 28);
            generatedGrid.set(true, 11, 28);
            generatedGrid.set(true, 12, 28);
            generatedGrid.set(true, 13, 28);
            generatedGrid.set(true, 14, 28);
            generatedGrid.set(true, 15, 28);
            generatedGrid.set(true, 16, 28);
            generatedGrid.set(true, 17, 28);
        }

        /*
                      1
                      1
                      1             1
        1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
                      1             1
                      1             1
                      1             1
                      1             1
                      1             1
1 1 1 1 1 1 1 1 1 1 1 1             1
1                     1             1
1                     1             1
1                     1             1
1                     1             1
1                     1             1
1                     1             1
1           1 1 1 1 1 1             1
1           1       1 1             1
1           1       1 1 1 1 1 1 1   1
1           1         1         1   1
1           1         1         1 1 1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
1           1         1           1     1
1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
            1         1           1
            1         1           1
            1                     1
            1                     1
            1 1 1 1 1 1 1 1 1 1 1 1
         */
    }
}
