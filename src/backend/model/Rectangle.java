package backend.model;

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
        return eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }

}
