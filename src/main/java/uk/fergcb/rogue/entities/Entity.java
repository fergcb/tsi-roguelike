package uk.fergcb.rogue.entities;

public abstract class Entity {

    public String getDefiniteName() {
        return "the " + getName();
    }

    public String getIndefiniteName() {
        return "a " + getName();
    }

    public abstract String getName();
    public abstract void tick();
    public abstract String draw();
}
