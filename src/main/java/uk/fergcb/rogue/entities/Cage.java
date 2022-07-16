package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Text;

public class Cage extends Entity {
    @Override
    public String getName() {
        return Text.grey("CAGE");
    }

    @Override
    public void tick() {

    }

    @Override
    public String draw() {
        return Text.grey("#");
    }
}
