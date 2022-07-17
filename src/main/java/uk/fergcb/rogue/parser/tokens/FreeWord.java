package uk.fergcb.rogue.parser.tokens;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Not;
import uk.fergcb.rogue.parser.combinators.Sequence;

public class FreeWord extends Parser {

    Parser parser = Sequence.of(new Not(new Keyword()), new Word());

    @Override
    public @NotNull ParseResult parse(String input) {
        ParseResult result = parser.parse(input);
        if (result.isFail()) return ParseResult.fail(input);
        String match = (String)result.asList().get(1);
        String rest = result.rest();
        return ParseResult.match(match, rest);
    }
}
