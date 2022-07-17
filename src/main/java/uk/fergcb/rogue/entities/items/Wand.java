package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

public class Wand extends Item {
    @Override
    public String getName() {
        return Text.magenta("WAND");
    }

    @Override
    public void doTick() {}

    @Override
    public String draw() {
        return Text.magenta("/");
    }
}
