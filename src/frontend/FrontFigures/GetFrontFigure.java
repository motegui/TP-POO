package frontend.FrontFigures;

import backend.model.*;
import javafx.scene.canvas.GraphicsContext;

public class GetFrontFigure {

    public static FrontFigure get(Figure figure, GraphicsContext gc){
        if (figure instanceof Rectangle)
            return new FrontRectangle(figure,gc);
        else if (figure instanceof Ellipse)
            return new FrontEllipse(figure, gc);
        else if (figure instanceof Square)
            return new FrontSquare(figure, gc);
        else if (figure instanceof Circle)
            return new FrontCircle(figure ,gc);
        return null;
    }
}
