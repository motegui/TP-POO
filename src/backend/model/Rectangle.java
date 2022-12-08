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

}
