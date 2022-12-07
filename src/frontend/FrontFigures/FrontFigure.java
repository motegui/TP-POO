package frontend.FrontFigures;

import backend.model.Figure;
import javafx.scene.canvas.GraphicsContext;

// esta frontfigure recibe una figura del back para guardarla y todos los datos que pertenecen al front los hacemos aca
//(fillcolor, bordercolor etc)
public abstract class FrontFigure {
    private Figure figure;
    private GraphicsContext gc;


    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);


}
