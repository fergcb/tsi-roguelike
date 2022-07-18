package uk.fergcb.rogue.map.room;

public class EmptyRoom extends Room {
    public EmptyRoom(int x, int y) {
        super(0x9dedda, x, y);
    }

    @Override
    public String getName() {
        return "small, dark room";
    }

    @Override
    public void init() {}
}
