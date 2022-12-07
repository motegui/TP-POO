package frontend.FrontFigures;

import backend.model.Ellipse;
import backend.model.Figure;

public class GetFrontFigure {

    public static FrontFigure getFigure(Figure Rect){
        return new FrontRectangle();
    }
    public static FrontFigure getFigure(Ellipse ellipse){
        return new FrontEllipse();
    }
}
