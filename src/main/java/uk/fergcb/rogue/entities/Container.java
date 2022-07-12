package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Inventory;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.items.Item;

public abstract class Container extends Entity implements Interactable {
    public final Inventory inventory;

    public Container () {
       this.inventory = new Inventory();
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
                    else printContents();
            }
            case TAKE -> {
                if (isLocked()) System.out.printf("The %s is locked.\n", getName());
                else {
                    Item item = inventory.searchFor(action.args()[0]);
                    this.inventory.remove(item);
                    action.actor().inventory.add(item);
                    System.out.println(
                            Text.capitalize(action.actor().getDefiniteName())
                                    + (action.actor() instanceof Player ? " take " : " takes ")
                                    + item.getDefiniteName()
                    );
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
