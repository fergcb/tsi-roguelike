package uk.fergcb.rogue.parser.command;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.enitity.item.Item;
import uk.fergcb.rogue.enitity.item.Weapon;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinator.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.*;

public class AttackCommand extends Command {

    Parser parser = Sequence.of(strs("hit", "attack"), phrase, opt(Sequence.of(str("with"), phrase)));

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        String targetName = Entity.stripArticle(args.get(0));
        List<Entity> targets = actor.currentRoom.findEntity(targetName);

        if (targets.size() == 0) {
            return Interaction.fail(actor, String.format("I can't see a %s.", Text.red(targetName)));
        }

        if (targets.size() > 1) {
            return Interaction.clarify(actor, targetName, targets.stream().map(Entity::getDefiniteName).toList());
        }

        Item item = null;
        if (args.size() > 1) {
            String itemName = Entity.stripArticle(args.get(2));
            List<Item> items = actor.inventory.searchFor(itemName);

            if (items.size() == 0) {
                return Interaction.fail(actor, String.format("I don't have a %s.", Text.red(Entity.stripArticle(itemName))));
            }

            if (items.size() > 1) {
                return Interaction.clarify(actor, itemName, items.stream().map(Entity::getDefiniteName).toList());
            }

            item = items.get(0);
        }

        int damage;
        String source;

        if (item != null) {
            damage = item instanceof Weapon weapon
                    ? weapon.getDamage()
                    : (int) Math.round(Math.random());
            source = item.getDefiniteName();
        } else {
            damage = 0;
            source = "your fists";
        }

        Entity target = targets.get(0);

        return new Interaction(InteractionType.ATTACK, actor, target, source, damage);
    }

    @Override
    protected ParseResult parse(String input) {
        return parser.parse(input);
    }
}
