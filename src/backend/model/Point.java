package backend.model;

public class Point {

    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void movePoint(double x, double y){ // Mueve el punto
        this.x=x;
        this.y=y;
    }
    public double getX() {
        return x;
    } // Obtiene la coordenada x del punto

    public double getY() {
        return y;
    } // Obtiene la coordenada y del punto

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    } // Devuelve el punto en formato String

}
