package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.Actor;
import uk.fergcb.rogue.entities.Container;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.*;

public class PickUpCommand extends Command {

    private static final Parser<List<String>> parser = Sequence.of(strs("pick up", "take", "get"), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String itemName = Entity.stripArticle(args.get(0));
        Entity entity = actor.currentRoom.findEntity(itemName);

        if (entity == null)
            return Interaction.fail("I can't see a " + Text.red(itemName) + ".");

        if (!(entity instanceof Item))
            return Interaction.fail("I can't carry " + entity.getDefiniteName() + ".");

        return new Interaction(InteractionType.PICK_UP, actor, actor, itemName);
    }

    @Override
    protected ParseResult<List<String>> parse(String input) {
        return parser.parse(input);
    }
}
