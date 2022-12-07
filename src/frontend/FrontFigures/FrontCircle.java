package frontend.FrontFigures;

import backend.model.Circle;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import javafx.scene.canvas.GraphicsContext;

public class FrontCircle extends FrontEllipse{
    Circle circle;
    double diameter;
    public FrontCircle(Figure figure, GraphicsContext gc) {
        super(figure, gc);
        this.circle = (Circle) figure;
        this.diameter = circle.getRadius() * 2;
    }
    public void fill() {
        getGc().fillOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
    }

    @Override
    public void stroke() {
        getGc().strokeOval(circle.getCenterPoint().getX() - circle.getRadius(), circle.getCenterPoint().getY() - circle.getRadius(), diameter, diameter);
    }
}
