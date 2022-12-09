package backend.model;

public class Rectangle extends ColoredFigure {

    private final Point topLeft, bottomRight;

    public Rectangle(GraphicsController gc, Point topLeft, Point bottomRight, String lineColor, String fillColor, double lineWidth) {
        super(gc, lineColor, fillColor, lineWidth);
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
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
        System.out.println(new Point(x,y));
        bottomRight.movePoint(x+ bottomRight.getX()- topLeft.getX(),y+ topLeft.getY()- bottomRight.getY());
        topLeft.movePoint(x,y);
//        bottomRight.movePoint(x+ bottomRight.getX()- topLeft.getX(),y+ topLeft.getY()- bottomRight.getY());
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
