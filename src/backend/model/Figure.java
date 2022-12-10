package backend.model;

import java.awt.*;

public abstract class Figure {

    private final GraphicsController gc;

    public Figure(GraphicsController gc){
        this.gc=gc;
    }

    public GraphicsController getGc(){
        return gc;
    }
    public  boolean figureBelongs(Point eventPoint){
        return containsPoint(eventPoint);
    }

    public abstract boolean containsPoint(Point eventPoint);

    public abstract void moveFigure(double x, double y);

}
