package frontend.FrontFigures;

import backend.model.Figure;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

public class GetFrontFigure {
    
    
    public FrontRectangle getFrontRectangle(Figure fig, GraphicsContext gc){ return new FrontRectangle(fig,gc);}
    public FrontEllipse getFrontEllipse(Figure fig, GraphicsContext gc){ return new FrontEllipse(fig,gc);}
    public FrontSquare getFrontSquare(Figure fig, GraphicsContext gc){ return new FrontSquare(fig,gc);}
    public FrontCircle getFrontCircle(Figure fig, GraphicsContext gc){ return new FrontCircle(fig,gc);}
}
