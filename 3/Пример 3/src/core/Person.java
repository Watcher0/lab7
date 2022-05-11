package core;

import util.PersonInterface;
import util.WeatherEnum;

public class Person implements PersonInterface {
    private String name;
    private boolean believes;
    private boolean sitting;
    private boolean upsidedown;

    public Person() {
        name = "Незнайка";
        believes = false;
        sitting = true;
        upsidedown = true;
        joinStory();
    }

    public Person(String name) {
        this.name = name;
        believes = false;
        sitting = true;
        upsidedown = true;
        joinStory();
    }

    public Person(String name, boolean believes, boolean sitting, boolean upsidedown) {
        this.name = name;
        this.believes = believes;
        this.sitting = sitting;
        this.upsidedown = upsidedown;
        joinStory();
    }

    private void joinStory() {
        System.out.println("Человек по имени '" + name + "' присоединился к истории.");
    }

    public void lookDown(Weather weather, Transport transport) {
        if (transport.isFlying()) {
            if (weather.getWeather().equals(WeatherEnum.CLOUDY)) {
                System.out.println("Человек по имени '" + name + "' смотрит вниз из " + transport
                        + " и видит облака, закрывающие землю.");
                believe();
            } else {
                System.out.println("Человек по имени '" + name + "' смотрит вниз из " + transport + " и видит землю.");
            }
        } else {
            System.out.println("Человек по имени '" + name + "' смотрит вниз из " + transport
                    + " и не видит ничего, так как транспорт не летит.");
        }
    }

    public void hold(Hat hat) {
        if (sitting) {
            System.out.println(
                    name + " сидит на своем месте и крепко держит руками головной убор " + hat.getName() + ".");
        } else {
            System.out.println(name + " стоит и крепко держит руками головной убор " + hat.getName() + ".");
        }
    }

    public void thinkAbout(Hat hat) {
        if (upsidedown) {
            System.out.println(name + " думает, что головной убор " + hat.getName()
                    + " может свалиться с него, ведь он сидит вверх ногами.");
        } else {
            System.out.println(
                    name + " ни о чем не думает и не понимает, зачем держит головной убор " + hat.getName() + ".");
        }
    }

    private void believe() {
        if (believes) {
            System.out.println(name + " этому верит.");
        } else {
            System.out.println(name + " этому не верит.");
        }
    }

    @Override
    public boolean isSitting() {
        return sitting;
    }

    @Override
    public boolean isBelieving() {
        return believes;
    }

    @Override
    public boolean isUpsideDown() {
        return upsidedown;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Человек '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Person) {
            return (name.equals(((Person) obj).getName()) && isBelieving() == ((Person) obj).isBelieving()
                    && isSitting() == ((Person) obj).isSitting() && isUpsideDown() == ((Person) obj).isUpsideDown());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (isBelieving()) {
            return name.hashCode() + name.length();
        } else {
            return name.hashCode();
        }
    }
}
