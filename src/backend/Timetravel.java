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
