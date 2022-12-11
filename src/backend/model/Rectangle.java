package backend.model;

import backend.CanvasState;

public class Rectangle extends ColoredFigure {

    private final Point topLeft, bottomRight;

    private final double width;
    private final double height;

    public Rectangle(GraphicsController gc, Point topLeft, Point bottomRight, String lineColor, String fillColor, double lineWidth) {
        super(gc, lineColor, fillColor, lineWidth);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.width = bottomRight.getX()- topLeft.getX();
        this.height = topLeft.getY()- bottomRight.getY();

    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }


    @Override
    public String toString() {
        return String.format("Rectángulo [ %s , %s ]", topLeft, bottomRight);
    }

    @Override
    public void moveFigure(double x, double y){
        topLeft.movePoint(x,y);
        bottomRight.movePoint(x+width,y-height);
    }

    @Override
    public void draw(String lineColor) {
        getGc().drawRectangle(topLeft, bottomRight, lineColor,getFillColor(),getLineWidth());
    }

    @Override
    public boolean containsPoint(Point eventPoint){
        System.out.println(String.format("%f > %f && %f < %f && %f > %f && %f < %f", eventPoint.getX(), topLeft.getX(), eventPoint.getX(), bottomRight.getX(), eventPoint.getY(), topLeft.getY(), eventPoint.getY(), bottomRight.getY()));
        return eventPoint.getX() > topLeft.getX() && eventPoint.getX() < bottomRight.getX() &&
                eventPoint.getY() > topLeft.getY() && eventPoint.getY() < bottomRight.getY();
    }

    @Override
    public ColoredFigure copyFigure(Point centerPoint) {
        return new Rectangle(getGc(), new Point(centerPoint.getX()-width/2, centerPoint.getY()+height/2),
                new Point(centerPoint.getX()+width/2, centerPoint.getY()-height/2), getLineColor(), getFillColor(), getLineWidth());
    }
    public ColoredFigure copyFigure() {
        return new Rectangle(getGc(), topLeft, bottomRight, getLineColor(), getFillColor(), getLineWidth());
    }
    @Override
    public String getFigureName(){
        return "rectangle";
    }
}
