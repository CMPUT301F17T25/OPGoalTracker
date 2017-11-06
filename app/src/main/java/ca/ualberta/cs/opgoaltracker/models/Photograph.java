package ca.ualberta.cs.opgoaltracker.models;

/**
 * Created by donglin3 on 10/22/17.
 */

public class Photograph {

    private int height;
    private int width;

    public Photograph(int height, int width){
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
