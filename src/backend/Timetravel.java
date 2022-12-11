

package backend;

import java.util.ArrayDeque;
import backend.action.ActionType;
import backend.action.ActionState;
import backend.exception.NothingToDoException;

import javax.swing.*;

public class Timetravel {
    private ArrayDeque<ActionState> undoStack = new ArrayDeque<>();
    private ArrayDeque<ActionState> redoStack = new ArrayDeque<>();

    public int getUndoSize(){
        return undoStack.size();
    }
    public int getRedoSize(){
        return redoStack.size();
    }
    public void add(ActionState state) {
        undoStack.push(state);
        redoStack.clear();
        System.out.println(undoStack.size());
    }

    public void addUndo(ActionState state) {
        undoStack.push(state);
    }
    public void addRedo(ActionState state) {
        redoStack.push(state);
    }

    public ActionState redo() throws NothingToDoException {
        if (!redoStack.isEmpty()) {
            ActionState state = redoStack.pop();
            return state;
        } else throw new NothingToDoException("REHACER");
    }

    public ActionState undo() throws NothingToDoException{
        if (!undoStack.isEmpty()) {
            ActionState state = undoStack.pop();
            return state;
        } else throw new NothingToDoException("DESHACER");
    }

    public ActionState getUndoLastAction(){
        return undoStack.peek();
    }
    public ActionState getRedoLastAction(){
        return redoStack.peek();
    }
}
//package backend;
//
//import java.util.ArrayDeque;
//
//public class Timetravel<T>{
//    private ArrayDeque<T> undoStack = new ArrayDeque<>();
//    private ArrayDeque<T> redoStack = new ArrayDeque<>();
//    public void add(T state){
//        undoStack.push(state);
//        redoStack.clear();
//    }
//
//    public T redo(){
//        if(!redoStack.isEmpty()){
//            T state = redoStack.pop();
//            undoStack.push(state);
//        }
//        return T;
//    }
//
//    public T undo(){
//        if(!undoStack.isEmpty()){
//            T state = undoStack.pop();
//            redoStack.push(state);
//        }
//        return T;
//    }
//
//
//
//
//
//}
