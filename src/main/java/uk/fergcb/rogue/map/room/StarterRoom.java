package uk.fergcb.rogue.map.room;

import uk.fergcb.rogue.enitity.Chest;
import uk.fergcb.rogue.enitity.item.Sword;
import uk.fergcb.rogue.enitity.item.Wand;

public class StarterRoom extends Room {

    public StarterRoom(int x, int y) {
        super(0xba9d1c, x, y);
    }

    @Override
    public String getName() {
        return "dank cellar";
    }

    @Override
    public void init() {
        Chest chest = new Chest();
        chest.inventory.add(new Sword());
        chest.inventory.add(new Wand());
        addEntity(chest);
    }
}
