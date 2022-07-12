package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.Interaction;

public interface Interactable {
    boolean canInteract(Interaction action);
    boolean onInteract(Interaction action);
}
