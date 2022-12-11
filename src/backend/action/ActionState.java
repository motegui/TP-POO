package backend.action;

import backend.model.ColoredFigure;

import java.util.ArrayList;
import java.util.List;
//ActionState es "una foto" del estado actual del programa que contiene lo necesario para las funcionalidades a implementar.
public class ActionState {
    private final ActionType actionType;
    private final List<ColoredFigure> list = new ArrayList<>();

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
    } // se crea un estado de accion con el tipo de accion, la lista de figuras, la figura afectada y la copia de la figura afectada

    public ActionType getActionType(){
        return actionType;
    } // se obtiene el tipo de accion
    public ColoredFigure getFigureAffected(){
        return figureAffected;
    } // se obtiene la figura afectada
    public ColoredFigure getCopied(){
        return copied;
    } // se obtiene la copia de la figura afectada
    public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
    } // se obtiene la lista de figuras
    @Override
    public String toString(){
        return (String.format("%s %s\n",actionType.toString(),(figureAffected == null)? "" : figureAffected.getFigureName())); // se devuelve el tipo de accion y el nombre de la figura afectada
    }
}
