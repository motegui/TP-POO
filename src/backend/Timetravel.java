

package backend;

import java.util.ArrayDeque;
import backend.action.ActionType;
import backend.action.ActionState;
import backend.exception.NothingToDoException;

import javax.swing.*;

public class Timetravel {
    private final ArrayDeque<ActionState> undoStack = new ArrayDeque<>(); //stack for undo
    private final ArrayDeque<ActionState> redoStack = new ArrayDeque<>(); //stack for redo

    public int getUndoSize(){
        return undoStack.size();
    }
    public int getRedoSize(){
        return redoStack.size();
    }
    public void add(ActionState state) {
        undoStack.push(state);
        redoStack.clear();
    }

    public void addUndo(ActionState state) {
        undoStack.push(state);
    }
    public void addRedo(ActionState state) {
        redoStack.push(state);
    }

    public ActionState redo() throws NothingToDoException { // se hace la logica de redo
        if (!redoStack.isEmpty()) {
            return redoStack.pop();
        }
        throw new NothingToDoException("REHACER");
    }

    public ActionState undo() throws NothingToDoException{ // se hace la logica de undo
        if (!undoStack.isEmpty()) {
            ActionState state = undoStack.pop();
            return state;
        } else throw new NothingToDoException("DESHACER");
    }

    public ActionState getUndoLastAction(){
        return undoStack.peek();
    } // se obtiene el ultimo elemento de la pila de undo
    public ActionState getRedoLastAction(){
        return redoStack.peek();
    } // se obtiene el ultimo elemento de la pila de redo
}

