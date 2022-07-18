package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Text;

public class Cage extends Entity {

    @Override
    public String getName() {
        return Text.grey("CAGE");
    }

    @Override
    public String describe() {
        return """
                The rusted iron cage has been wrought open from the inside.
                It's about 5 feet wide and 5 feet deep - big enough for
                a couple of humans, or maybe one larger form...
                """;
    }

    @Override
    public String draw() {
        return Text.grey("#");
    }
}
