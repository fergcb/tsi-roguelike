package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Text;

public class Chest extends Container {

    @Override
    public String getName() {
        return Text.yellow("CHEST");
    }

    @Override
    public String describe() {
        return "The wooden chest is reinforced with wrought iron bands.\n";
    }

    @Override
    public String draw() {
        return Text.yellow("m");
    }

    @Override
    protected boolean isLocked() {
        return false;
    }
}
