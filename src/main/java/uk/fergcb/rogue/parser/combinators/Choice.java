package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.Arrays;
import java.util.List;

public class Choice<T> extends Parser<T> {

    public final List<Parser<T>> parsers;

    @SafeVarargs
    public Choice (Parser<T>... parsers) {
        if (parsers.length < 2) throw new IllegalArgumentException("Choice combinator must take at least two parsers.");
        this.parsers = Arrays.asList(parsers);
    }

    @Override
    public @NotNull ParseResult<T> parse(String input) {
        ParseResult<T> result;
        int i = 0;
        do {
            Parser<T> parser = parsers.get(i++);
            result = parser.parse(input);
        } while (result.isFail() && i < parsers.size());

        return result;
    }
}
