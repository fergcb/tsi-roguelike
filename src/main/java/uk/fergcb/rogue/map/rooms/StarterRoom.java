package uk.fergcb.rogue.map.rooms;

import uk.fergcb.rogue.entities.Chest;
import uk.fergcb.rogue.entities.items.Sword;
import uk.fergcb.rogue.entities.items.Wand;

public class StarterRoom extends Room {

    @Override
    public String getName() {
        return "dank cellar";
    }

    @Override
    public void init() {
        Chest chest = new Chest();
        chest.inventory.add(new Sword());
        chest.inventory.add(new Wand());
        this.entities.add(chest);
    }

    @Override
    public void tick() {

    }
}
