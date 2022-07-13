package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

public class Not<T> extends Parser<T> {

    private final Parser<T> parser;

    public Not(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public @NotNull ParseResult<T> parse(String input) {
        // System.out.println("Trying NOT to match " + parser.getClass().getSimpleName());
        ParseResult<T> result = parser.parse(input);
        if (!result.isFail()) return ParseResult.fail(input);
        // System.out.println("Matched not");
        return ParseResult.empty(input);
    }
}
