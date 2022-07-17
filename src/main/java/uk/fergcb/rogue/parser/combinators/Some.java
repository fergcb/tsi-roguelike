package uk.fergcb.rogue.parser.combinators;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.ArrayList;
import java.util.List;

public class Some extends Parser {
    private final Parser parser;

    public Some(Parser parser) {
        this.parser = parser;
    }

    @Override
    public @NotNull ParseResult parse(String input) {
        List<Object> matches = new ArrayList<>();

        String rest = input;
        ParseResult result;
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
