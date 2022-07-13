package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

public class Optional<T> extends Parser<T> {

    private final Parser<T> parser;

    public Optional(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public @NotNull ParseResult<T> parse(String input) {
        ParseResult<T> result = parser.parse(input);
        if (result.isFail()) return ParseResult.empty(input);
        return result;
    }
}
