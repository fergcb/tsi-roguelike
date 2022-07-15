package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Inventory;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.map.Direction;

import java.util.Arrays;
import java.util.List;

public abstract class Actor extends Entity implements Interactable {
    public final Inventory inventory = new Inventory();

    @Override
    public boolean canInteract(Interaction action) {
        List<InteractionType> allowed = Arrays.asList(
                InteractionType.DROP, InteractionType.PICK_UP, InteractionType.GO, InteractionType.LOOK
        );
        return allowed.contains(action.type()) && action.target() == this;
    }

    @Override
    public boolean onInteract(Interaction action) {
        switch (action.type()) {
            case GO -> {
                String dirString = action.args()[0];
                Direction dir = Direction.valueOf(dirString);

                if (!currentRoom.hasExit(dir)) {
                    System.out.printf("There's no way %s from here.\n", Text.blue(dirString));
                    return false;
                }

                currentRoom = currentRoom.getExit(dir);

                return true;
            }
            case LOOK -> {
                System.out.print(currentRoom.draw(this));
                System.out.print(currentRoom.describe());
            }
            case DROP -> {
                String itemName = action.args()[0];
                Item item = inventory.searchFor(itemName);
                inventory.remove(item);
                currentRoom.entities.add(item);
                if (action.actor() == action.target()) {
                    System.out.println(
                            Text.capitalize(action.actor().getDefiniteName())
                                    + (action.actor() instanceof Player ? " drop " : " drops ")
                                    + item.getDefiniteName()
                    );
                } else {
                    System.out.println(
                            Text.capitalize(action.actor().getDefiniteName())
                                    + (action.actor() instanceof Player ? " remove " : " removes ")
                                    + item.getDefiniteName()
                                    + " from "
                                    + ((Entity)action.target()).getDefiniteName()
                    );
                }
            }
            case PICK_UP -> {
                String itemName = action.args()[0];
                Item item = (Item)currentRoom.findEntity(itemName);
                currentRoom.entities.remove(item);
                inventory.add(item);
                System.out.println(
                        Text.capitalize(action.actor().getDefiniteName())
                                + (action.actor() instanceof Player ? " pick up " : " picks up ")
                                + item.getDefiniteName()
                );
            }
        }

        return false;
    }
}
