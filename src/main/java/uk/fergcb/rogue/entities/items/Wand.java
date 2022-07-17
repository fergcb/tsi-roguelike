package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

public class Wand extends Item {
    @Override
    public String describe() {
        return """
                A carved wooden stick, about 9 inches long.
                It looks like a magic wand from some fantasy film.
                """;
    }
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
