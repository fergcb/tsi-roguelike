package uk.fergcb.rogue;

import uk.fergcb.rogue.entities.Actor;
import uk.fergcb.rogue.entities.Interactable;

public record Interaction(InteractionType type, Actor actor, Interactable target, String... args) {}
