package physics;

public class Position {

    private double x;
    private double y;
    private double prevX, prevY;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int intX(){
        return Math.round((float) x);
    }

    public int intY(){
        return Math.round((float) y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(Vector vector) {
        this.x += vector.getX();
        this.y += vector.getY();
    }
}
