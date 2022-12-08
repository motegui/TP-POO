package backend.model;

public class Square extends Rectangle {

    private double size;

    public Square(GraphicsController gc, Point topLeft, double size, String lineColor, String fillColor, double lineWidth) {
        super(gc, topLeft,new Point(topLeft.getX() +size, topLeft.getY()+size ), lineColor, fillColor,lineWidth);
        this.size=size;
    }


    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

}
