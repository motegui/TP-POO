package frontend;

import backend.model.ColoredFigure;
import backend.model.GraphicsController;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FrontGraphicsController implements GraphicsController {

    private final GraphicsContext gc;

    public FrontGraphicsController(GraphicsContext gc){
        this.gc = gc;
    }
    private void setColors(String lineColor, String fillColor, double lineWidth){
        gc.setStroke(Color.web(lineColor));
        gc.setLineWidth(lineWidth);
        gc.setFill(Color.web(fillColor));
    }
    @Override
    public void drawRectangle(Point topLeft, Point bottomRight,String lineColor, String fillColor, double lineWidth) {
        setColors(lineColor,fillColor,lineWidth);
        gc.fillRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
        gc.strokeRect(topLeft.getX(), topLeft.getY(),
                Math.abs(topLeft.getX() - bottomRight.getX()), Math.abs(topLeft.getY() - bottomRight.getY()));
    }

    @Override
    public void drawEllipse(Point centerPoint, double sMajorAxis, double sMinorAxis, String lineColor, String fillColor, double lineWidth) {
        setColors(lineColor,fillColor,lineWidth);
        gc.strokeOval(centerPoint.getX() - (sMajorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMajorAxis, sMinorAxis);
        gc.fillOval(centerPoint.getX() - (sMajorAxis / 2), centerPoint.getY() - (sMinorAxis / 2), sMajorAxis, sMinorAxis);
    }

    @Override
    public void drawCircle(Point centerPoint, double radius, String lineColor, String fillColor, double lineWidth) {
        drawEllipse(centerPoint, 2*radius, 2*radius, lineColor,fillColor,lineWidth);
    }

    @Override
    public void drawSquare(Point topLeft, double size, String lineColor, String fillColor, double lineWidth) {
        drawRectangle(topLeft, new Point(topLeft.getX()+size, topLeft.getY()-size), lineColor,fillColor,lineWidth);
    }
}
