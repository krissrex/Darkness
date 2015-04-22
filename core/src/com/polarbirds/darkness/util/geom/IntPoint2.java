package com.polarbirds.darkness.util.geom;

/**
 * Created by Kristian Rekstad on 17.04.2015.
 */
public class IntPoint2 {
    public int x;
    public int y;

    /**
     * Initializes to (0,0)
     * @see IntPoint2#IntPoint2(int, int)
     */
    public IntPoint2(){
        this(0,0);
    }

    public IntPoint2(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "IntPoint2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Sets this point to be equal to the other
     * @param other the point to copy values from
     */
    public void set(IntPoint2 other){
        this.x = other.x;
        this.y = other.y;
    }
}
