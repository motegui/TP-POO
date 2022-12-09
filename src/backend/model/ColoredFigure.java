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
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor){
        this.fillColor=fillColor;
    }
    public void setLineColor(String lineColor){
        this.lineColor=lineColor;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }
    public String getLineColor() {
        return lineColor;
    }
    public void draw(){
        draw(lineColor);
    }
    public abstract void draw(String lineColor);

    public abstract boolean containsPoint(Point eventPoint);
}
