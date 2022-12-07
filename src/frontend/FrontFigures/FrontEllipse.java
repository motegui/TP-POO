package frontend.FrontFigures;

import backend.model.Ellipse;
import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class FrontEllipse extends FrontFigure{
    Ellipse ellipse;
    public FrontEllipse(Figure figure, GraphicsContext gc) {
        super(figure, gc);
        this.ellipse = (Ellipse) super.getFigure();
    }

    //super

    @Override
    public void fill() {
        getGc().fillOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

    @Override
    public void stroke() {
        getGc().strokeOval(ellipse.getCenterPoint().getX() - (ellipse.getsMayorAxis() / 2), ellipse.getCenterPoint().getY() - (ellipse.getsMinorAxis() / 2), ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
}
