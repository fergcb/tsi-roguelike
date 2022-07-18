package uk.fergcb.rogue.parser.combinator;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.Arrays;
import java.util.List;

public class Choice extends Parser {

    public final List<Parser> parsers;

    public Choice (Parser... parsers) {
        if (parsers.length < 2) throw new IllegalArgumentException("Choice combinator must take at least two parsers.");
        this.parsers = Arrays.asList(parsers);
    }

    @Override
    public @NotNull ParseResult parse(String input) {
        ParseResult result;
        int i = 0;
        do {
            Parser parser = parsers.get(i++);
            result = parser.parse(input);
        } while (result.isFail() && i < parsers.size());

        return result;
    }
}
