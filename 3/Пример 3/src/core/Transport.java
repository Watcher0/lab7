package core;

import util.ThingInterface;

public class Transport implements ThingInterface {
    private String name;
    private boolean moving;
    private boolean flying;

    public Transport() {
        name = "Шар";
        moving = false;
        flying = true;
        joinStory();
    }

    public Transport(String name, boolean flying) {
        this.name = name;
        moving = false;
        this.flying = flying;
        joinStory();
    }

    private void joinStory() {
        System.out.println("Транспорт '" + name + "' возник.");
    }

    public void move() {
        if (!moving) {
            moving = true;
            System.out.println("Транспорт '" + name + "' начал быстро двигаться.");
        } else {
            System.out.println("Транспорт '" + name + "' уже и так двигается.");
        }
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isFlying() {
        return flying;
    }

    public void moveDown() {
        System.out.println("Все заметили, что '" + name + "' начал опускаться.");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Транспорт '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Transport) {
            return name.equals(((Transport) obj).name) && moving == ((Transport) obj).isMoving();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (moving) {
            return name.hashCode() + name.length();
        }
        return name.hashCode();
    }
}