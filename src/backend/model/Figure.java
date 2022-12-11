package backend.model;


public abstract class Figure {

    private final GraphicsController gc;

    public Figure(GraphicsController gc){
        this.gc=gc;
    } // se crea una figura con el controlador de graficos especificado

    public GraphicsController getGc(){
        return gc;
    } // se obtiene el controlador de graficos

    public abstract boolean containsPoint( Point eventPoint); // Chequea si el punto está dentro de la figura

    public abstract void moveFigure(double x, double y); // Mueve la figura
    public abstract String getFigureName(); // Obtiene el nombre de la figura


}
