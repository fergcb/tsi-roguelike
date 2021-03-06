package uk.fergcb.rogue.parser.command;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.enitity.Container;
import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.enitity.item.Item;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinator.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.*;

public class TakeCommand extends Command {

    private static final Parser parser = Sequence.of(strs("take", "get"), phrase, str("from"), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String targetName = Entity.stripArticle(args.get(2));
        List<Entity> targets = actor.currentRoom.findEntity(targetName);

        if (targets.size() == 0)
            return Interaction.fail(actor, "I can't see a " + Text.red(targetName) + " to take from.");

        if (targets.size() == 1) {
            Entity target = targets.get(0);
            if (!(target instanceof Container container))
                return Interaction.fail(actor, target.getDefiniteName() + " has nothing for me to take.");

            String itemName = Entity.stripArticle(args.get(0));
            List<Item> items = container.inventory.searchFor(itemName);

            if (items.size() == 0)
                return Interaction.fail(actor, "I can't find a " + Text.red(itemName) + " in " + container.getDefiniteName());

            return new Interaction(InteractionType.TAKE, actor, container, itemName);
        }

        List<String> options = targets
                .stream()
                .map(Entity::getName)
                .toList();

        return Interaction.clarify(actor, targetName, options);
    }

    @Override
    protected ParseResult parse(String input) {
        return parser.parse(input);
    }
}
