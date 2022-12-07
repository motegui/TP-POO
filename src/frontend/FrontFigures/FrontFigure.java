package frontend.FrontFigures;

import backend.model.Figure;
import backend.model.Rectangle;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

// esta frontfigure recibe una figura del back para guardarla y todos los datos que pertenecen al front los hacemos aca
//(fillcolor, bordercolor etc)
public abstract class FrontFigure {
    private Figure figure;
    private GraphicsContext gc;
    private Color fillColor;
    private Color borderColor;
    private double borderSize;

    public FrontFigure(Figure figure, GraphicsContext gc) {
        this.figure = figure;
        this.gc = gc;
    }
    public abstract void fill();

    public abstract void stroke();
    //retorna el graphicsContext;
    public GraphicsContext getGc() {
        return gc;
    }
    //retorna la instancia de figura del back que tenemos guardada
    public Figure getFigure() {
        return figure;
    }
}
