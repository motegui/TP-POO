package backend.model;

public class Circle extends Ellipse{

    public Circle(GraphicsController gc, Point centerPoint, double radius, String lineColor, String fillColor, double lineWidth)  {
        super(gc, centerPoint,2*radius,2*radius, lineColor,fillColor,lineWidth);
    }

    @Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, getRadius());
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getRadius() {
        return getsMayorAxis()/2;
    }

    @Override
    public void draw(String lineColor) {
        getGc().drawCircle(centerPoint, sMayorAxis/2, lineColor,getFillColor(),getLineWidth());
    }
    @Override
    public boolean containsPoint(Point eventPoint){
        return  Math.sqrt(Math.pow(getCenterPoint().getX() - eventPoint.getX(), 2) +
                Math.pow(getCenterPoint().getY() - eventPoint.getY(), 2)) < getRadius();
    }

    @Override
    public String getFigureName(){
        return "Circle";
    }


}
