package frontend.FrontFigures;

import backend.model.Figure;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class FrontRectangle extends FrontFigure {
    Rectangle rect;
    public FrontRectangle(Figure figure, GraphicsContext gc) {
        super(figure, gc);
        rect = (Rectangle) super.getFigure();
    }

    @Override
    public void fill() {
        super.getGc().fillRect(rect.getTopLeft().getX(), rect.getTopLeft().getY(),
                Math.abs(rect.getTopLeft().getX() - rect.getBottomRight().getX()), Math.abs(rect.getTopLeft().getY() - rect.getBottomRight().getY()));
    }

    @Override
    public void stroke() {
        if (rect != null)
            super.getGc().strokeRect(rect.getTopLeft().getX(), rect.getTopLeft().getY(),
                Math.abs(rect.getTopLeft().getX() - rect.getBottomRight().getX()), Math.abs(rect.getTopLeft().getY() - rect.getBottomRight().getY()));
    }
}
