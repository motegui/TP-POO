package backend.model;

public class Square extends Rectangle {

    private final double size;

    public Square(GraphicsController gc, Point topLeft, double size, String lineColor, String fillColor, double lineWidth) {
        super(gc, topLeft,new Point(topLeft.getX() +size, topLeft.getY()+size ), lineColor, fillColor,lineWidth);
        this.size=size;
    }
    @Override
    public String getFigureName(){
        return "CUADRADO";
    }


    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", getTopLeft(), getBottomRight());
    }

    @Override
    public void draw(String lineColor) {
        getGc().drawSquare(super.getTopLeft(), size, lineColor,getFillColor(),getLineWidth());
    }
    @Override
    public boolean containsPoint(Point eventPoint){ // se fija si el punto esta dentro del cuadrado
        return  eventPoint.getX() > getTopLeft().getX() && eventPoint.getX() < getBottomRight().getX() &&
                eventPoint.getY() > getTopLeft().getY() && eventPoint.getY() < getBottomRight().getY();
    }
    @Override
    public ColoredFigure copyFigure() { // se copia la figura
        return new Square(getGc(), getTopLeft(), size,
                getLineColor(), getFillColor(), getLineWidth());
    }

}
