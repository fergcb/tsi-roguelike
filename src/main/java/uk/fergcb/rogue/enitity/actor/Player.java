package uk.fergcb.rogue.enitity.actor;

import uk.fergcb.rogue.Text;

import java.util.LinkedList;
import java.util.Queue;

public class Player extends Actor {

    private final Queue<String> messageQueue = new LinkedList<>();

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
        messageQueue.add(message);
    }

    public void messageNow(String message) {
        System.out.println(message);
    }

    @Override
    public void doPostTick() {
        messageQueue.forEach(this::messageNow);
        messageQueue.clear();
    }
}
