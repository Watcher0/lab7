package core;

import util.ThingInterface;

public class Hat implements ThingInterface {
    private String name;

    public Hat() {
        name = "Шляпа";
        joinStory();
    }

    public Hat(String name) {
        this.name = name;
        joinStory();
    }

    private void joinStory() {
        System.out.println("Головной убор '" + name + "' возник.");
    }

    @Override
    public String getName() {
        return name;
    }
}
