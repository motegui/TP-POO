package backend;

import backend.action.ActionType;
import backend.action.PaintAction;
import backend.exception.NothingSelectedException;
import backend.exception.NothingToDoException;
import backend.model.ColoredFigure;
import backend.model.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CanvasState {
    private final List<ColoredFigure> list = new ArrayList<>();
    //necesito hacer 2 colas para que siempre se saque la ultima accion agregada
    private final Deque<PaintAction> unDo = new LinkedList<>();
    private final Deque<PaintAction> reDo = new LinkedList<>();

    public void addFigure(ColoredFigure figure) {
        list.add(figure);
    }

    public void deleteFigure(ColoredFigure figure) {
        list.remove(figure);
    }

   public Iterable<ColoredFigure> figures() {
        return new ArrayList<>(list);
   }
   //SE LLAMA CADA VEZ QUE SE REALIZA UNA ACCION DE TIPO DRAW,DELETE,LINECOLOR,FILLCOLOR,COPYFORMAT,CUT,COPY,PASTE
    public void toUndo(ActionType actionType,ColoredFigure figure) throws NothingSelectedException {
        if(figure== null)
            throw new NothingSelectedException(actionType.toString());
        unDo.add(new PaintAction(actionType, figure.copyFigure(),list.indexOf(figure)));
    //cada vez que se agrega una accion a UNDO vacio la cola del REDO
    reDo.clear();
    }
    //se llama al apretar el boton deshacer
    public void undoAction() throws NothingToDoException{
        if(unDo.size() ==0)
            throw new NothingToDoException("deshacer");
        PaintAction action = unDo.getLast();
        unDo.removeLast();
        //si es DELETE solo vuelvo a agregarla a la lista en la misma posicion en la que estaba
        if(action.getActionType() == ActionType.DELETE){
            replaceFigureInList(action);
            reDo.add(new PaintAction(action.getActionType(),list.get(action.getIdx()).copyFigure(),action.getIdx()));
            return;
        }
        //SI ES DRAW ELIMINO LA FIGURA
        if(action.getActionType() == ActionType.DRAW) {
            reDo.add(new PaintAction(action.getActionType(), list.get(action.getIdx()).copyFigure(), action.getIdx()));
            list.remove(action.getIdx());
            return;
        }
        reDo.add(new PaintAction(action.getActionType(), list.get(action.getIdx()).copyFigure(), action.getIdx()));
        //Si no es DELETE ni DRAW seteo en la lista la vieja figura
        list.set(action.getIdx(), action.getOldFigure());
    }
    public PaintAction getUndoLastAction(){
        return unDo.getLast();
    }
    //Se llama al apretar el boton Rehacer
    public void redoAction() throws NothingToDoException {
        if(reDo.size() == 0)
            throw new NothingToDoException("Rehacer");
        PaintAction action = reDo.getLast();
        reDo.removeLast();
        if(action.getActionType() == ActionType.DRAW) {
            replaceFigureInList(action);
            unDo.add(new PaintAction(action.getActionType(), list.get(action.getIdx()).copyFigure(), action.getIdx()));
            return;
        }
        ColoredFigure undoOldFigure = list.get(action.getIdx()).copyFigure();
        if(action.getActionType() == ActionType.DELETE) {
            unDo.add(new PaintAction(action.getActionType(), undoOldFigure, action.getIdx()));
            list.remove(action.getIdx());
            return;
        }
        list.set(action.getIdx(), action.getOldFigure());
        unDo.add(new PaintAction(action.getActionType(), undoOldFigure, action.getIdx()));
    }
    public void replaceFigureInList(PaintAction action){
        ColoredFigure last = action.getOldFigure();
        for(int i= action.getIdx(); i<=list.size();i++){
            if(i==list.size()){
                list.add(last);
                return;
            }
            ColoredFigure curr = list.get(i);
            list.set(i,last);
            last = curr;
        }
    }


   public int getUndoSize(){
        return unDo.size();
}

    public Deque<PaintAction> getUnDo() {
        return unDo;
    }

    public Deque<PaintAction> getReDo() {
        return reDo;
    }
    public PaintAction getRedoLastAction(){
        return reDo.getLast();
    }
    public int getRedoSize(){
        return reDo.size();
    }
}

