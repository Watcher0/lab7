package run;

import core.*;

public class Story {
    public static void main(String[] args) {
        Person Dunno = new Person();
        Transport aTransport = new Transport();
        Weather aWeather = new Weather();
        Dunno.lookDown(aWeather, aTransport);
        Hat aHat = new Hat();
        Dunno.hold(aHat);
        Dunno.thinkAbout(aHat);
        aWeather.moveTransport(aTransport);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        aTransport.moveDown();
    }
}
