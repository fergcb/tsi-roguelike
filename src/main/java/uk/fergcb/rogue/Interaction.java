package uk.fergcb.rogue;

import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.enitity.Entity;

import java.util.List;
import java.util.stream.Collectors;

public record Interaction(InteractionType type, Actor actor, Entity target, String... args) {
    public static Interaction fail (Actor actor, String msg) {
        return new Interaction(InteractionType.FAIL, actor, actor, msg);
    }

    public static Interaction clarify(Actor actor, String givenText, List<String> possibleTexts) {
        String msg =
                String.format("Which %s?\n", Text.red(givenText)) +
                possibleTexts
                        .stream()
                        .map(txt -> "  " + txt)
                        .collect(Collectors.joining("\n"));
        return new Interaction(InteractionType.CLARIFY, actor, actor, msg);
    }

    @Override
    public String toString() {
        String act = actor == null ? "NULL" : actor.getName();
        String tgt = target == null ? "NULL" : target.getName();
        return String.format("%s(%s), %s -> %s", type.name(), String.join(", ", args), act, tgt);
    }
}
