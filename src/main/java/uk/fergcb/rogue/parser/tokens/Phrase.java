package uk.fergcb.rogue.parser.tokens;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Some;

import java.util.List;

public class Phrase extends Parser<String> {

    private static final Parser<List<String>> parser = new Some<>(new FreeWord());

    @Override
    public @NotNull ParseResult<String> parse(String input) {
        ParseResult<List<String>> result = parser.parse(input);
        if (result.isFail()) return ParseResult.fail(input);
        String match = String.join(" ", result.value());
        String rest = result.rest();
        return ParseResult.match(match, rest);
    }
}
