package uk.fergcb.rogue.parser.command;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinator.Sequence;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static uk.fergcb.rogue.parser.Parsers.phrase;
import static uk.fergcb.rogue.parser.Parsers.word;

public class GoCommand extends Command {
    private static final List<String> validDirs = Arrays.asList("NORTH", "EAST", "SOUTH", "WEST");

    private static final Parser parser = Sequence.of(word("go").or(word("move")), phrase);

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        if (args.size() != 1) return Interaction.fail(actor, "I don't know how to " + Text.red(command));

        String dir = args.get(0).toUpperCase(Locale.ROOT);
        if (!validDirs.contains(dir)) return Interaction.fail(actor, dir + " isn't a valid direction...");

        return new Interaction(InteractionType.GO, actor, actor, dir);
    }

    @Override
    protected ParseResult parse(String input) {
        return parser.parse(input);
    }
}
