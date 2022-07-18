package uk.fergcb.rogue.parser.command;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.enitity.item.Item;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinator.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.phrase;
import static uk.fergcb.rogue.parser.Parsers.strs;

public class DropCommand extends Command {

    private static final Parser parser = Sequence.of(strs("drop", "discard"), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String itemName = Entity.stripArticle(args.get(0));
        List<Item> items = actor.inventory.searchFor(itemName);

        if (items.size() == 0)
            return Interaction.fail(actor, "I don't have a " + Text.red(itemName) + ".");

        return new Interaction(InteractionType.DROP, actor, actor, itemName);
    }

    @Override
    protected ParseResult parse(String input) {
        return parser.parse(input);
    }
}
