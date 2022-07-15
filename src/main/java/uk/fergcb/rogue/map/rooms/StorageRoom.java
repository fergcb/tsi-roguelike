package uk.fergcb.rogue.map.rooms;

import uk.fergcb.rogue.entities.Crate;
import uk.fergcb.rogue.entities.items.*;

public class StorageRoom extends Room {

    public StorageRoom(int x, int y) {
        super(0xf59342, x, y);
    }

    @Override
    public String getName() {
        return "storage room";
    }

    @Override
    public void init() {
        Crate crate = new Crate();
        crate.inventory.add(new Gem(GemType.random(), GemQuality.random()));
        crate.inventory.add(new Potion(PotionColor.random()));
        addEntity(crate);
    }
}
