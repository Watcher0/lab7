package util;

public abstract class WeatherAbstract implements WeatherInterface {
    private boolean windy;

    public WeatherAbstract(boolean windy) {
        this.windy = windy;
    }

    @Override
    public boolean isWindy() {
        return windy;
    }

    @Override
    public void setWind(boolean windy) {
        this.windy = windy;
    }
}