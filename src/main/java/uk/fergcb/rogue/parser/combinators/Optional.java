package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

public class Optional extends Parser {

    private final Parser parser;

    public Optional(Parser parser) {
        this.parser = parser;
    }

    @Override
    public @NotNull ParseResult parse(String input) {
        ParseResult result = parser.parse(input);
        if (result.isFail()) return ParseResult.empty(input);
        return result;
    }
}
