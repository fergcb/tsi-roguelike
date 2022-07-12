package uk.fergcb.rogue.map.rooms;

public class EmptyRoom extends Room {
    @Override
    public String getName() {
        return "small, dark room";
    }

    @Override
    public void init() {}

    @Override
    public void tick() {}
}
