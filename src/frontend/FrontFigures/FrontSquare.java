package frontend.FrontFigures;

import backend.model.Circle;
import backend.model.Figure;
import backend.model.Square;
import javafx.scene.canvas.GraphicsContext;

public class FrontSquare extends FrontRectangle{
    Square square;
    double diameter;
    public FrontSquare(Figure figure, GraphicsContext gc) {
        super(figure, gc);
        this.square = (Square) super.getFigure();
    }
    @Override
    public void fill() {
        getGc().fillRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
    }

    @Override
    public void stroke() {
        getGc().strokeRect(square.getTopLeft().getX(), square.getTopLeft().getY(),
                Math.abs(square.getTopLeft().getX() - square.getBottomRight().getX()), Math.abs(square.getTopLeft().getY() - square.getBottomRight().getY()));
    }
}
