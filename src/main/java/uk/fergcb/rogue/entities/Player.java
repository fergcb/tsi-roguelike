package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Text;

public class Player extends Actor {

    @Override
    public String getName() {
        return "you";
    }

    @Override
    public String getDefiniteName() {
        return getName();
    }

    @Override
    public String getIndefiniteName() {
        return getName();
    }

    @Override
    public void tick() { }

    @Override
    public String draw() {
        return Text.white("@");
    }
}
