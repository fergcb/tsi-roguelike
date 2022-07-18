package uk.fergcb.rogue.parser.token;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

public class WS extends Parser {
    @Override
    public @NotNull ParseResult parse(String input) {
        int i = 0;
        while(i < input.length() && Character.isWhitespace(input.charAt(i))) {
            i += 1;
        }
        if (i == 0) return ParseResult.fail(input);
        String match = input.substring(0, i);
        String rest = input.substring(i);
        return ParseResult.match(match, rest);
    }
}
