package uk.fergcb.rogue.parser.commands;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.entities.actors.Actor;
import uk.fergcb.rogue.parser.ParseResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Command {

    public Optional<Interaction> match(String input, Actor actor) {
        ParseResult result = parse(input);
        if (result.isFail()) return Optional.empty();
        List<String> parts = result.asListOfString();
        String command = String.join("", parts);
        List<String> args = parts
                .subList(1, parts.size())
                .stream()
                .filter(part -> part.trim().length() > 0) // Filter out whitespace
                .collect(Collectors.toList());
        Interaction action = resolve(command, args, actor);
        return Optional.of(action);
    }

    protected abstract Interaction resolve(String command, List<String> args, Actor actor);

    protected abstract ParseResult parse(String input);
}
