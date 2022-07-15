package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.Actor;
import uk.fergcb.rogue.entities.Container;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.*;

public class TakeCommand extends Command {

    private static final Parser<List<String>> parser = Sequence.of(strs("take", "get"), phrase, str("from"), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String targetName = Entity.stripArticle(args.get(2));
        Entity target = actor.currentRoom.findEntity(targetName);

        if (target == null)
            return Interaction.fail("I can't see a " + Text.red(targetName) + " to take from.");

        if (!(target instanceof Container container))
            return Interaction.fail(target.getDefiniteName() + " has nothing for me to take.");

        String itemName = Entity.stripArticle(args.get(0));
        Item item = container.inventory.searchFor(itemName);

        if (item == null)
            return Interaction.fail("I can't find a " + Text.red(itemName) + " in " + container.getDefiniteName());

        return new Interaction(InteractionType.TAKE, actor, container, itemName);
    }

    @Override
    protected ParseResult<List<String>> parse(String input) {
        return parser.parse(input);
    }
}
