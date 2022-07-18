package uk.fergcb.rogue.map.room;

import uk.fergcb.rogue.enitity.Shrine;

public class ShrineRoom extends Room {

    public ShrineRoom(int x, int y) {
        super(0xd298d9, x, y);
    }

    @Override
    public String getName() {
        return "small chapel";
    }

    @Override
    public String observe() {
        return """
                The stone walls are engraved with scenes of
                angelic beings healing the sick, and fighting
                off gruesome demonic creatures.
                
                The pedestal in the middle of the room is
                illuminated in a soft yellow light.
                """;
    }

    @Override
    public void init() {
        addEntity(new Shrine());
    }
}
