package uk.fergcb.rogue.parser.command;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinator.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.*;

public class SearchCommand extends Command {

    private static final Parser parser = Sequence.of(strs("search", "look in"), opt(phrase));

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        if (args.size() > 1) return Interaction.fail("I don't know how to " + Text.red(command));

        if (args.size() == 0) {
            return new Interaction(InteractionType.LOOK, actor, actor);
        }

        String targetName = Entity.stripArticle(args.get(0));
        List<Entity> targets = actor.currentRoom.findEntity(targetName);

        if (targets.size() == 0)
            return Interaction.fail("I can't see a " + Text.red(targetName) + " to search.");

        if (targets.size() == 1) {
            Entity target = targets.get(0);
            return new Interaction(InteractionType.SEARCH, actor, target);
        }

        List<String> options = targets
                .stream()
                .map(Entity::getName)
                .toList();

        return Interaction.clarify(targetName, options);
    }

    @Override
    protected ParseResult parse(String input) {
        return parser.parse(input);
    }
}
