package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequence<T> extends Parser<List<T>> {

    @SafeVarargs
    public static <U> Sequence<U> of(Parser<U>... parsers) {
        return new Sequence<>(parsers);
    }

    private final List<Parser<T>> parsers;
    private final boolean consumeWhitespace = true;

    @SafeVarargs
    private Sequence(Parser<T>... parsers) {
        if (parsers.length < 1) throw new IllegalArgumentException("Sequence combinator must take at least one parser.");
        this.parsers = Arrays.asList(parsers);
    }

    @Override
    public @NotNull ParseResult<List<T>> parse(String input) {
        List<T> matches = new ArrayList<>();
        String rest = input;
        for (Parser<T> parser : parsers) {
            // System.out.println("Trying to match sequence element " + parser.getClass().getSimpleName());
            ParseResult<T> result = parser.parse(rest);
            if (result.isFail()) return ParseResult.fail(input);
            matches.add(result.value());
            rest = result.rest();
            if (consumeWhitespace) rest = rest.stripLeading();
            // System.out.println("Matched " + result.value());
        }

        return ParseResult.match(matches, rest);
    }
}
