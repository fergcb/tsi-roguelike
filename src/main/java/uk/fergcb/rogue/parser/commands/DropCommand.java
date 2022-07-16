package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.actors.Actor;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.phrase;
import static uk.fergcb.rogue.parser.Parsers.strs;

public class DropCommand extends Command {

    private static final Parser<List<String>> parser = Sequence.of(strs("drop", "discard"), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String itemName = Entity.stripArticle(args.get(0));
        List<Item> items = actor.inventory.searchFor(itemName);

        if (items.size() == 0)
            return Interaction.fail("I don't have a " + Text.red(itemName) + ".");

        return new Interaction(InteractionType.DROP, actor, actor, itemName);
    }

    @Override
    protected ParseResult<List<String>> parse(String input) {
        return parser.parse(input);
    }
}
