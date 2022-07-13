package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.Actor;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.Interactable;

public record Interaction(InteractionType type, Actor actor, Interactable target, String... args) {
    public static Interaction fail (String msg) {
        return new Interaction(InteractionType.FAIL, null, null, msg);
    }

    @Override
    public String toString() {
        String act = actor == null ? "NULL" : actor.getName();
        String tgt = target == null ? "NULL" : (target instanceof Entity ? ((Entity)target).getName() : target.getClass().getSimpleName());
        return String.format("%s(%s), %s -> %s", type.name(), String.join(", ", args), act, tgt);
    }
}
