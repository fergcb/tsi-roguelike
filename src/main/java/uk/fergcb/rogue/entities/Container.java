package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Inventory;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.items.Item;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Container extends Entity implements Interactable {
    public final Inventory inventory;

    public boolean contentsIsKnown;

    public Container () {
        super();
        this.inventory = new Inventory();
        this.contentsIsKnown = false;
    }

    protected abstract boolean isLocked();

    @Override
    public boolean canInteract(Interaction action) {
        return action.type() == InteractionType.SEARCH
                || action.type() == InteractionType.TAKE;
    }

    @Override
    public boolean onInteract(Interaction action) {
        switch (action.type()) {
            case SEARCH -> {
                if (isLocked()) System.out.printf("The %s is locked.\n", getName());
                else {
                    printContents();
                    contentsIsKnown = true;
                }
            }
            case TAKE -> {
                if (isLocked()) System.out.printf("The %s is locked.\n", getName());
                else {
                    String itemName = action.args()[0];
                    List<Item> possibleItems = inventory.searchFor(itemName);
                    if (possibleItems.size() == 0) {
                        System.out.printf("%s doesn't contain %s.\n", Text.capitalize(getDefiniteName()), Text.red(itemName));
                    } else if (possibleItems.size() == 1) {
                        Item item = possibleItems.get(0);
                        this.inventory.remove(item);
                        action.actor().inventory.add(item);
                        System.out.println(
                                Text.capitalize(action.actor().getDefiniteName())
                                        + (action.actor() instanceof Player ? " take " : " takes ")
                                        + item.getDefiniteName()
                                        + " from "
                                        + getDefiniteName()
                        );
                        return false;
                    } else {
                        String msg = String.format("Which %s would you like to take from %s?\n", Text.red(itemName), getDefiniteName())
                        + possibleItems
                                .stream()
                                .map(item -> "  " + Text.capitalize(item.getDefiniteName()))
                                .collect(Collectors.joining("\n"));
                        System.out.println(msg);
                    }
                }
            }
        }
        return false;
    }

    protected void printContents() {
        StringBuilder sb = new StringBuilder();
        sb.append(Text.capitalize(this.getDefiniteName()));
        int itemCount = inventory.size();
        if (itemCount > 0) {
            sb.append(" contains ");
            for (int i = 0; i < itemCount; i++) {
                Item item = inventory.get(i);
                sb.append(item.getIndefiniteName());
                if (itemCount > 1 && i < itemCount - 1) {
                    sb.append(i == itemCount - 2 ? " and " : ", ");
                }
            }
        } else {
            sb.append(" is empty");
        }
        sb.append(".");
        System.out.println(sb);
    }
}
