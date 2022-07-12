package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.items.Item;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {
    public Item searchFor(String itemName) {
        return this
                .stream()
                .filter(item -> item
                        .getName()
                        .replaceAll("\u001B\\[[;\\d]*m", "")
                        .equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }
}
