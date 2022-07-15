package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Text;

public class Chest extends Container {

    @Override
    public String getName() {
        return Text.yellow("CHEST");
    }

    @Override
    public void tick() {}

    @Override
    protected boolean isLocked() {
        return false;
    }

    @Override
    public String draw() {
        return Text.yellow("m");
    }
}
