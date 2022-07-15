package uk.fergcb.rogue.map.rooms;

public class HorizontalHallway extends Room {

    public HorizontalHallway(int x, int y) {
        super(0xffffff, x, y);
    }

    @Override
    public String getName() {
        return "stone hallway";
    }

    @Override
    public void init() {}
}
