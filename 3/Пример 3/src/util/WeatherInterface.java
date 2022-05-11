package util;

public interface WeatherInterface extends ThingInterface {
    WeatherEnum getWeather();

    boolean isWindy();

    void setWind(boolean windy);
}
