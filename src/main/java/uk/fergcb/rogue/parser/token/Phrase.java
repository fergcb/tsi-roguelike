package uk.fergcb.rogue.parser.token;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinator.Some;

public class Phrase extends Parser {

    private static final Parser parser = new Some(new FreeWord());

    @Override
    public @NotNull ParseResult parse(String input) {
        ParseResult result = parser.parse(input);
        if (result.isFail()) return ParseResult.fail(input);
        String match = String.join(" ", result.asListOfString());
        String rest = result.rest();
        return ParseResult.match(match, rest);
    }
}
