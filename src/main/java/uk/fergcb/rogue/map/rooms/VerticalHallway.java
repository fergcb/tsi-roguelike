package uk.fergcb.rogue.map.rooms;

public class VerticalHallway extends Room {

    public VerticalHallway(int x, int y) {
        super(0xffffff, x, y);
    }

    @Override
    public String getName() {
        return "a stone hallway";
    }

    @Override
    public void init() {}

    @Override
    public void tick() {}
}
