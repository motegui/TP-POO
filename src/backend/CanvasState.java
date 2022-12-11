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
    }

    public void deleteFigure(ColoredFigure figure) {
        list.remove(figure);
    }

    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
   }

    public void update(Iterable<ColoredFigure> figures){
        this.list.clear();
        for (ColoredFigure figure : figures)
            this.list.add(figure);
    }

}

