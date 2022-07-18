package uk.fergcb.rogue.entities.actors;

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
    public String describe() {
        return "You are a hapless adventurer.";
    }

    @Override
    public String draw() {
        return Text.white("@");
    }

    public void message(String message) {
        System.out.println(message);
    }
}
