package uk.fergcb.rogue.parser.tokens;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

public class Str extends Parser {
    private final String text;

    public Str(String text) {
        this.text = text;
    }

    @Override
    public @NotNull ParseResult parse(String input) {
        if (input.startsWith(text)) {
            String rest = input.substring(text.length());
            return ParseResult.match(text, rest);
        }
        return ParseResult.fail(input);
    }
}
