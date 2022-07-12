package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

public class Sword extends Item {
    @Override
    public String getName() {
        return Text.cyan("SWORD");
    }

    @Override
    public void tick() {}

    @Override
    public String draw() {
        return Text.cyan("!");
    }
}
