package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Text;

public class Crate extends Container {

    @Override
    public String describe() {
        return """
                The crate is made of rough wooden slats.
                It looks easy enough to prize open the lid.
                """;
    }

    @Override
    public String getName() {
        return Text.yellow("CRATE");
    }

    @Override
    public void doTick() {}

    @Override
    protected boolean isLocked() {
        return false;
    }

    @Override
    public String draw() {
        return Text.yellow("■");
    }
}
