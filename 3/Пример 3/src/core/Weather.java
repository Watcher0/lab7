package core;

import util.WeatherAbstract;
import util.WeatherEnum;

public class Weather extends WeatherAbstract {
    private String name;
    private final WeatherEnum TYPE = WeatherEnum.CLOUDY;

    public Weather() {
        super(true);
        name = "Ветер";
        joinStory();
    }

    public Weather(String name) {
        super(true);
        this.name = name;
        joinStory();
    }

    public Weather(String name, boolean windy) {
        super(windy);
        this.name = name;
        joinStory();
    }

    private void joinStory() {
        if (isWindy()) {
            System.out.println("Ветренная погода '" + name + "' началась.");
        } else {
            System.out.println("Погода '" + name + "' началась.");
        }
    }

    public void moveTransport(Transport obj) {
        if (isWindy()) {
            System.out.println("Погода " + name + " несет транспорт '" + obj.getName() + "'.");
            obj.move();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public WeatherEnum getWeather() {
        return TYPE;
    }

    @Override
    public String toString() {
        if (isWindy()) {
            return "Ветренная погода '" + name + "'";
        } else {
            return "Погода '" + name + "'";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Weather) {
            return (name.equals(((Weather) obj).getName()) && isWindy() == ((Weather) obj).isWindy());

        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (isWindy()) {
            return name.hashCode() + name.length();
        } else {
            return name.hashCode();
        }
    }
}