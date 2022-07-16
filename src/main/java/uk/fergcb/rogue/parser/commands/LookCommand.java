package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.InteractionType;
import uk.fergcb.rogue.entities.actors.Actor;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.List;

import static uk.fergcb.rogue.parser.Parsers.str;

public class LookCommand extends Command {

    Parser<List<String>> parser = Sequence.of(str("look"));

    @Override
    protected Interaction resolve(String command, List<String> args, Actor actor) {
        return new Interaction(InteractionType.LOOK, actor, actor);
    }

    @Override
    protected ParseResult<List<String>> parse(String input) {
        return parser.parse(input);
    }
}
