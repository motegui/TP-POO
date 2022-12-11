package backend.action;

import backend.model.ColoredFigure;
import sun.text.resources.CollationData;

public class PaintAction {
    private final ActionType actionType;
    private ColoredFigure oldFigure; // figura que quiero reemplzar al apretar deshacer
    private int idx; //indice como se encuentra la figura a modificar en list

    public PaintAction(ActionType actionType,ColoredFigure oldFigure,int idx) {
        this.oldFigure = oldFigure;
        this.actionType = actionType;
        this.idx = idx;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public ColoredFigure getOldFigure() {
        return oldFigure;
    }

    public int getIdx() {
        return idx;
    }

    public String toString(){
        return String.format("%s %s", actionType,oldFigure.getFigureName());
   }
}
