package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.Actor;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.*;

public class SearchCommand extends Command {

    private static final Parser<List<String>> parser = Sequence.of(strs("search", "look in"), opt(phrase));

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        if (args.size() > 1) return Interaction.fail("I don't know how to " + Text.red(command));

        if (args.size() == 0) {
            return new Interaction(InteractionType.LOOK, actor, actor);
        }

        String targetName = Entity.stripArticle(args.get(0));
        Interactable target = actor.currentRoom.findInteractableEntity(targetName);

        if (target == null) return Interaction.fail("I can't see a " + Text.red(targetName) + " to search.");

        return new Interaction(InteractionType.SEARCH, actor, target);
    }

    @Override
    protected ParseResult<List<String>> parse(String input) {
        return parser.parse(input);
    }
}
