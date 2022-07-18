package uk.fergcb.rogue.map.room;

import uk.fergcb.rogue.enitity.Cage;
import uk.fergcb.rogue.enitity.actor.Minotaur;

public class CageRoom extends Room {
    public CageRoom(int x, int y) {
        super(0x962037, x, y);
    }

    @Override
    public String getName() {
        return "messy stone room";
    }

    @Override
    public String observe() {
        return """
                The putrid smell of rotting meat fills the air here.
                A black ichor is oozing down the walls and coating the floor.
                A rusted iron cage is built into the corner of the room.
                The iron bars are bowed and fractured...
                ...did something force it's way out of here?
                """;
    }

    @Override
    public void init() {
        addEntity(new Cage());
        addEntity(new Minotaur());
    }
}
