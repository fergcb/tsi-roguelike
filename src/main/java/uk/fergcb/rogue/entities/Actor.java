package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Inventory;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.map.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                List<Item> possibleItems = inventory.searchFor(itemName);
                if (possibleItems.size() == 0) {
                    System.out.printf("I don't have a %s.\n", Text.red(itemName));
                    return false;
                }

                if (possibleItems.size() == 1) {
                    Item item = possibleItems.get(0);
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
                    return false;
                }

                String msg = String.format("Which %s would you like to drop?\n", Text.red(itemName))
                        + possibleItems
                        .stream()
                        .map(item -> "  " + Text.capitalize(item.getDefiniteName()))
                        .collect(Collectors.joining("\n"));
                System.out.println(msg);
            }
            case PICK_UP -> {
                String itemName = action.args()[0];
                List<Entity> entities = currentRoom.findEntity(itemName);
                if (entities.size() == 0) {
                    System.out.printf("I can't see a %s.\n", Text.red(itemName));
                    return false;
                }

                if (entities.size() == 1) {
                    Entity entity = entities.get(0);
                    if (!(entity instanceof Item item)) {
                        System.out.printf("I can't pick %s up.\n", entity.getDefiniteName());
                        return false;
                    }
                    currentRoom.entities.remove(item);
                    inventory.add(item);
                    System.out.println(
                            Text.capitalize(action.actor().getDefiniteName())
                                    + (action.actor() instanceof Player ? " pick up " : " picks up ")
                                    + item.getDefiniteName()
                    );
                    return false;
                }

                String msg = String.format("Which %s would you like to pick up?\n", Text.red(itemName))
                        + entities
                        .stream()
                        .map(item -> "  " + Text.capitalize(item.getDefiniteName()))
                        .collect(Collectors.joining("\n"));
                System.out.println(msg);
            }
        }

        return false;
    }
}
