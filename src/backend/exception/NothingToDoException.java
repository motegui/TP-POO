package backend.exception;

public class NothingToDoException  extends Exception{
    private final static String MESSAGE = "NO HAY ACCIONES PARA ";
    public NothingToDoException(String message){
        super(MESSAGE + message);
    }
}
