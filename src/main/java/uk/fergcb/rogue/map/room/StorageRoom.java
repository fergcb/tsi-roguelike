package uk.fergcb.rogue.map.room;

import uk.fergcb.rogue.enitity.Crate;
import uk.fergcb.rogue.enitity.item.*;

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
