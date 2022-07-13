package uk.fergcb.rogue.parser.tokens;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;
import uk.fergcb.rogue.parser.combinators.Not;
import uk.fergcb.rogue.parser.combinators.Sequence;

import java.util.List;

public class FreeWord extends Parser<String> {

    Parser<List<String>> parser = Sequence.of(new Not<>(new Keyword()), new Word());

    @Override
    public @NotNull ParseResult<String> parse(String input) {
        ParseResult<List<String>> result = parser.parse(input);
        if (result.isFail()) return ParseResult.fail(input);
        String match = result.value().get(1);
        String rest = result.rest();
        return ParseResult.match(match, rest);
    }
}
