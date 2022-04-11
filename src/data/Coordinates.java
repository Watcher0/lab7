package data;

public class Coordinates implements Comparable<Coordinates>{
    private int x;
    private float y;

    public Coordinates(int x, float y){
        this.x = x;
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
