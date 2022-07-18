package uk.fergcb.rogue.map.room;

public class VerticalHallway extends Room {

    public VerticalHallway(int x, int y) {
        super(0xffffff, x, y);
    }

    @Override
    public String getName() {
        return "stone hallway";
    }

    @Override
    public void init() {}
}
