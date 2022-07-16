package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.actors.Actor;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.Interactable;

import java.util.List;
import java.util.stream.Collectors;

public record Interaction(InteractionType type, Actor actor, Interactable target, String... args) {
    public static Interaction fail (String msg) {
        return new Interaction(InteractionType.FAIL, null, null, msg);
    }

    public static Interaction clarify(String givenText, List<String> possibleTexts) {
        String msg =
                String.format("Which %s?\n", Text.red(givenText)) +
                possibleTexts
                        .stream()
                        .map(txt -> "  " + txt)
                        .collect(Collectors.joining("\n"));
        return new Interaction(InteractionType.CLARIFY, null, null, msg);
    }

    @Override
    public String toString() {
        String act = actor == null ? "NULL" : actor.getName();
        String tgt = target == null ? "NULL" : (target instanceof Entity ? ((Entity)target).getName() : target.getClass().getSimpleName());
        return String.format("%s(%s), %s -> %s", type.name(), String.join(", ", args), act, tgt);
    }
}
