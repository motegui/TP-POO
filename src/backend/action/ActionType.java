package backend.action;

public enum ActionType {
    DRAW("DIBUJAR"),
    DELETE("BORRAR"),
    LINE_COLOR("CAMBIAR EL COLOR DEL BORDE"),
    FILL_COLOR("CAMBIAR EL COLOR DE RELLENO"),
    COPY_FORMAT("COPIAR EL FORMATO"),
    CUT("CORTAR"),
    COPY("COPIAR"),
    PASTE("PEGAR");

    private final String message;
    ActionType(String message){
        this.message = message;
    }
    @Override
    public String toString(){
        return message;
    }
}
