package backend.model;

public interface GraphicsController {

    void drawRectangle(Point topLeft, Point bottomRight, String lineColor, String fillColor, double lineWidth);

    void drawEllipse(Point centerPoint, double sMajorAxis, double sMinorAxis, String lineColor, String fillColor, double lineWidth);

    void drawCircle(Point centerPoint, double radius, String lineColor, String fillColor, double lineWidth);

    void drawSquare(Point topLeft, double size, String lineColor, String fillColor, double lineWidth);

}
