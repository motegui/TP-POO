package backend.model;


public abstract class ColoredFigure extends Figure {
    private String lineColor, fillColor;
    private double lineWidth;

    public ColoredFigure(GraphicsController gc, String lineColor, String fillColor, double lineWidth){
        super(gc);
        this.lineColor = lineColor;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;

    }

    @Override
    public GraphicsController getGc() {
        return super.getGc();
    } // se obtiene el controlador de graficos

    public double getLineWidth() {
        return lineWidth;
    } // se obtiene el ancho de linea

    public String getFillColor() {
        return fillColor;
    } // se obtiene el color de relleno

    public void setFillColor(String fillColor){
        this.fillColor=fillColor;
    } // se establece el color de relleno
    public void setLineColor(String lineColor){
        this.lineColor=lineColor;
    } // se establece el color de linea

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    } // se establece el ancho de linea
    public String getLineColor() {
        return lineColor;
    } // se obtiene el color de linea
    public void draw(){
        draw(lineColor);
    } // se dibuja la figura

    public abstract ColoredFigure copyFigure(Point centerPoint); // se copia la figura en el centro del canvas
    public abstract ColoredFigure copyFigure(); // se copia la figura

    public abstract void draw(String lineColor); // se dibuja la figura con el color de linea especificado

    public abstract boolean containsPoint(Point eventPoint); // se fija si el punto esta dentro de la figura

}
