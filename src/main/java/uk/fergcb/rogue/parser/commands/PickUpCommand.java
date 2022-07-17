package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.actors.Actor;
import uk.fergcb.rogue.entities.Container;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.items.Item;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static uk.fergcb.rogue.parser.Parsers.phrase;
import static uk.fergcb.rogue.parser.Parsers.strs;

public class PickUpCommand extends Command {

    private static final Parser parser = Sequence.of(strs("pick up", "take", "get"), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String itemName = Entity.stripArticle(args.get(0));
        List<Entity> entities = actor.currentRoom.findEntity(itemName);

        if (entities.size() == 0) {
            Stream<Container> containers = actor.currentRoom.entities
                    .stream()
                    .filter(c -> c instanceof Container)
                    .map(c -> (Container)c)
                    .filter(c -> c.contentsIsKnown);

            Map<Container, List<Item>> possibleItems = new HashMap<>();

            containers
                    .map(c -> new AbstractMap.SimpleEntry<>(c, c.inventory.searchFor(itemName)))
                    .filter(entry -> entry.getValue() != null)
                    .forEach(entry -> possibleItems.put(entry.getKey(), entry.getValue()));

            if (possibleItems.size() == 0) return Interaction.fail("I can't see a " + Text.red(itemName) + ".");

            if (possibleItems.size() == 1) {
                Map.Entry<Container, List<Item>> entry = possibleItems.entrySet()
                        .stream()
                        .findFirst()
                        .orElseThrow();
                Container container = entry.getKey();
                return new Interaction(InteractionType.TAKE, actor, container, itemName);
            }

            Map<Item, Container> itemOptions = new HashMap<>();
            possibleItems.forEach((container, items) -> {
                for (Item item : items) {
                    itemOptions.put(item, container);
                }
            });

            List<String> options = itemOptions.entrySet()
                    .stream()
                    .map(entry -> String.format("%s from %s", entry.getValue().getDefiniteName(), entry.getKey().getDefiniteName()))
                    .toList();

            return Interaction.clarify(itemName, options);
        }

        return new Interaction(InteractionType.PICK_UP, actor, actor, itemName);
    }

    @Override
    protected ParseResult parse(String input) {
        return parser.parse(input);
    }
}
