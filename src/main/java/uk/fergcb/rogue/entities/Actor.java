package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Inventory;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.map.rooms.Room;

public abstract class Actor extends Entity implements Interactable {
    public final Inventory inventory = new Inventory();

    public Room currentRoom;

    public Actor(Room room) {
        this.currentRoom = room;
    }

    @Override
    public boolean canInteract(Interaction action) {
        return action.type() == InteractionType.DROP
                || action.type() == InteractionType.PICK_UP;
    }

    @Override
    public boolean onInteract(Interaction action) {
        switch (action.type()) {
            case GO -> {
                String dir = action.args()[0];
                Room next = switch(dir) {
                    case "NORTH" -> currentRoom.north;
                    case "EAST" -> currentRoom.east;
                    case "SOUTH" -> currentRoom.south;
                    case "WEST" -> currentRoom.west;
                    default -> throw new IllegalArgumentException("No such direction '" + dir + "'.");
                };

                if (next == null) {
                    System.out.printf("There's no way %s from here.\n", Text.blue(dir));
                    return false;
                }

                currentRoom = next;
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
