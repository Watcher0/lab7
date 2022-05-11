package common.data;

import java.io.Serializable;

public class Location implements Comparable<Location>, Serializable {
    private Integer x;
    private Float y;
    private String name;

    public Location(Integer x, Float y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Location(Integer x, Float y){
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + name;
    }

    @Override
    public int compareTo(Location o) {
        if (Math.sqrt(Math.pow(x,2) + Math.pow(y,2)) > Math.sqrt(Math.pow(o.x,2) + Math.pow(o.y,2))){
            return 1;
        }
        else
            if (Math.sqrt(Math.pow(x,2) + Math.pow(y,2)) < Math.sqrt(Math.pow(o.x,2) + Math.pow(o.y,2))){
                return -1;
            }
            else return 0;
    }

    public static Location valueOf(String s){
        String[] values = s.split(" ");
        if (values.length == 3) {
            return new Location(Integer.parseInt(values[0]), Float.parseFloat(values[1]), values[2]);
        }
        else{
            return new Location(Integer.parseInt(values[0]), Float.parseFloat(values[1]));
        }
    }
}
