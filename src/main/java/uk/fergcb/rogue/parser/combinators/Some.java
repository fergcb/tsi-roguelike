package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Some<T> extends Parser<List<T>> {
    private final Parser<T> parser;

    public Some(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public @NotNull ParseResult<List<T>> parse(String input) {
        List<T> matches = new ArrayList<>();

        String rest = input;
        ParseResult<T> result;
        while (true) {
            result = parser.parse(rest);
            if (result.isFail()) break;
            matches.add(result.value());
            rest = result.rest();
        }

        if (matches.size() > 0)
            return ParseResult.match(matches, rest);
        return ParseResult.fail(input);
    }
}
