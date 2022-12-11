package backend.model;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class Ellipse extends ColoredFigure {

    protected final Point centerPoint;
    protected final double sMayorAxis, sMinorAxis;

    public Ellipse(GraphicsController gc, Point centerPoint, double sMayorAxis, double sMinorAxis, String lineColor, String fillColor, double lineWidth) {
        super(gc, lineColor, fillColor, lineWidth);
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }

    @Override
    public String toString() {
        return String.format("Elipse [Centro: %s, DMayor: %.2f, DMenor: %.2f]", centerPoint, sMayorAxis, sMinorAxis);
    }

    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }

    @Override
    public void moveFigure(double x, double y){
        centerPoint.movePoint(x, y);
    }

    @Override
    public void draw(String lineColor) {
        getGc().drawEllipse(centerPoint, sMayorAxis, sMinorAxis, lineColor,getFillColor(),getLineWidth());
    }
    @Override
    public boolean containsPoint(Point eventPoint){
        return ((Math.pow(eventPoint.getX() - centerPoint.getX(), 2) / Math.pow(sMayorAxis, 2)) +
                (Math.pow(eventPoint.getY() - centerPoint.getY(), 2) / Math.pow(sMinorAxis, 2))) <= 0.30;
    }

    @Override
    public ColoredFigure copyFigure(Point centerPoint) {
        return new Ellipse(getGc(), centerPoint , sMayorAxis, sMinorAxis, getLineColor(), getFillColor(), getLineWidth());
    }

    public ColoredFigure copyFigure() {
        return new Ellipse(getGc(), centerPoint , sMayorAxis, sMinorAxis, getLineColor(), getFillColor(), getLineWidth());
    }
    @Override
    public String getFigureName(){
        return "ELIPSE";
    }
}
