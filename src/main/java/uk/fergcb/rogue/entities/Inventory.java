package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends ArrayList<Item> {
    public List<Item> searchFor(String itemName) {
        List<Item> matches = new ArrayList<>();
        for (Item item : this) {
            int distance = item.matchName(itemName);
            if (distance == -1) continue;
            if (distance == 0) return List.of(item);
            matches.add(item);
        }

        return matches;
    }
}
