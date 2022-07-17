package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

public class Sword extends Item {

    @Override
    public String describe() {
        return """
                The blade is a little rusted in places,
                but it looks like it could do some damage.
                """;
    }
    @Override
    public String getName() {
        return Text.cyan("SWORD");
    }

    @Override
    public void doTick() {}

    @Override
    public String draw() {
        return Text.cyan("!");
    }
}
