package frontend.FrontFigures;

import backend.model.Figure;
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

    public FrontFigure(Figure figure, GraphicsContext gc, Color fillColor, Color borderColor) {
        this.figure = figure;
        this.gc = gc;
        this.fillColor = fillColor;
    }
    public void setFillColor(Color fillColor){
        this.fillColor = fillColor;
    }
    public abstract void fill(GraphicsContext gc);
    public abstract void stroke(GraphicsContext gc);
    //retorna el graphicsContext;
    public GraphicsContext getGc() {
        return gc;
    }
    //retorna la instancia de figura del back que tenemos guardada
    public Figure getFigure() {
        return figure;
    }
    //retorna el color del borde de la figura
    public Color getBorderColor() {
        return borderColor;
    }
    public Color getFillColor() {
        return fillColor;
    }
}
