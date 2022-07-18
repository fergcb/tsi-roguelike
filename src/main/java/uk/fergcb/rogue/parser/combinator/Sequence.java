package uk.fergcb.rogue.parser.combinator;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequence extends Parser {

    public static Sequence of(Parser... parsers) {
        return new Sequence(parsers);
    }

    private final List<Parser> parsers;
    private final boolean consumeWhitespace = true;

    private Sequence(Parser... parsers) {
        if (parsers.length < 1) throw new IllegalArgumentException("Sequence combinator must take at least one parser.");
        this.parsers = Arrays.asList(parsers);
    }

    @Override
    public @NotNull ParseResult parse(String input) {
        List<Object> matches = new ArrayList<>();
        String rest = input;
        for (Parser parser : parsers) {
            // System.out.println("Trying to match sequence element " + parser.getClass().getSimpleName());
            ParseResult result = parser.parse(rest);
            if (result.isFail()) return ParseResult.fail(input);
            matches.add(result.value());
            rest = result.rest();
            if (consumeWhitespace) rest = rest.stripLeading();
            // System.out.println("Matched " + result.value());
        }

        return ParseResult.match(matches, rest);
    }
}
