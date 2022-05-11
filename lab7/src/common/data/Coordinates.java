package common.data;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
    private int x;
    private float y;

    public Coordinates(int x, float y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

    @Override
    public int compareTo(Coordinates o) {
        return Float.compare(x + y, o.x + o.y);
    }

    public static Coordinates valueOf(String s){
        String[] values = s.split(" ");
        return new Coordinates(Integer.parseInt(values[0]), Float.parseFloat(values[1]));
    }
}
