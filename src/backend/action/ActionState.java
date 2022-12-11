package backend.action;

import backend.CanvasState;
import backend.model.ColoredFigure;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

public class ActionState {
    private final ActionType actionType;
    private List<ColoredFigure> list = new ArrayList<>();

    private final ColoredFigure figureAffected;
    private final ColoredFigure copied;
    public ActionState(ActionType actionType, Iterable<ColoredFigure> list, ColoredFigure figureAffected, ColoredFigure copied){
        this.actionType=actionType;
        for (ColoredFigure figure : list)
        {
            this.list.add(figure.copyFigure());
        }
        this.figureAffected=figureAffected;
        this.copied=copied;
    }

    public ActionType getActionType(){
        return actionType;
    }
    public ColoredFigure getFigureAffected(){
        return figureAffected;
    }
    public ColoredFigure getCopied(){
        return copied;
    }
    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
    }
    @Override
    public String toString(){
        return (String.format("%s %s\n",actionType.toString(),(figureAffected == null)? "" : figureAffected.getFigureName()));
    }
}
