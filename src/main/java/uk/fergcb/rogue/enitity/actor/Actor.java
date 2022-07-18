package uk.fergcb.rogue.enitity.actor;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.enitity.Inventory;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.enitity.item.Item;
import uk.fergcb.rogue.map.Direction;
import uk.fergcb.rogue.map.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Actor extends Entity {
    public final Inventory inventory = new Inventory();

    public void move(Direction dir) {
        Room destination = currentRoom.getExit(dir);
        currentRoom.leavingEntities.put(this, dir);
        destination.arrivingEntities.put(this, Direction.inverse(dir));
        destination.addEntity(this);
    }

    @Override
    public boolean canReceive(Interaction action) {
        List<InteractionType> allowed = Arrays.asList(
                InteractionType.DROP, InteractionType.PICK_UP, InteractionType.GO, InteractionType.LOOK
        );
        return super.canReceive(action)
                || (allowed.contains(action.type()) && action.target() == this);
    }

    @Override
    public boolean handleInteraction(Interaction action) {
        Actor actor = action.actor();

        switch (action.type()) {
            case GO -> {
                String dirString = (String)action.args()[0];
                Direction dir = Direction.valueOf(dirString);

                if (!currentRoom.hasExit(dir)) {
                    actor.messagef("There's no way %s from here.", Text.blue(dirString));
                    return false;
                }

                move(dir);

                return true;
            }
            case DROP -> {
                String itemName = (String)action.args()[0];
                List<Item> possibleItems = inventory.searchFor(itemName);
                if (possibleItems.size() == 0) {
                    actor.messagef("I don't have a %s.", Text.red(itemName));
                    return false;
                }

                if (possibleItems.size() == 1) {
                    Item item = possibleItems.get(0);
                    inventory.remove(item);
                    currentRoom.entities.add(item);
                    if (actor == action.target()) {
                        actor.message(
                                Text.capitalize(actor.getDefiniteName())
                                        + (actor instanceof Player ? " drop " : " drops ")
                                        + item.getDefiniteName()
                        );
                    } else {
                        actor.message(
                                Text.capitalize(actor.getDefiniteName())
                                        + (actor instanceof Player ? " remove " : " removes ")
                                        + item.getDefiniteName()
                                        + " from "
                                        + action.target().getDefiniteName()
                        );
                    }
                    return false;
                }

                String msg = String.format("Which %s would you like to drop?\n", Text.red(itemName))
                        + possibleItems
                        .stream()
                        .map(item -> "  " + Text.capitalize(item.getDefiniteName()))
                        .collect(Collectors.joining("\n"));
                actor.message(msg);
            }
            case PICK_UP -> {
                String itemName = (String)action.args()[0];
                List<Entity> entities = currentRoom.findEntity(itemName);
                if (entities.size() == 0) {
                    actor.messagef("I can't see a %s.", Text.red(itemName));
                    return false;
                }

                if (entities.size() == 1) {
                    Entity entity = entities.get(0);
                    if (!(entity instanceof Item item)) {
                        actor.messagef("I can't pick %s up.", entity.getDefiniteName());
                        return false;
                    }
                    currentRoom.entities.remove(item);
                    inventory.add(item);
                    actor.message(
                            Text.capitalize(actor.getDefiniteName())
                                    + (actor instanceof Player ? " pick up " : " picks up ")
                                    + item.getDefiniteName()
                    );
                    return false;
                }

                String msg = String.format("Which %s would you like to pick up?\n", Text.red(itemName))
                        + entities
                        .stream()
                        .map(item -> "  " + Text.capitalize(item.getDefiniteName()))
                        .collect(Collectors.joining("\n"));
                actor.message(msg);
            }
        }

        return super.handleInteraction(action);
    }
}
