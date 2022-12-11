package backend;

import backend.action.ActionType;
//import backend.action.PaintAction;
import backend.exception.NothingSelectedException;
import backend.exception.NothingToDoException;
import backend.model.ColoredFigure;
import backend.model.Figure;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CanvasState {
    private final List<ColoredFigure> list = new ArrayList<>();


    public void addFigure(ColoredFigure figure) {
        list.add(figure);
    } // se agrega una figura a la lista

    public void deleteFigure(ColoredFigure figure) {
        list.remove(figure);
    } // se elimina la figura de la lista

    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
   } // se obtiene una copia de la lista de figuras

    public void update(Iterable<ColoredFigure> figures){ // se actualiza la lista de figuras
        this.list.clear();
        for (ColoredFigure figure : figures)
            this.list.add(figure);
    }

}

