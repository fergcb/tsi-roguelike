package uk.fergcb.rogue.parser.token;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex extends Parser {
    private final Pattern pattern;
    private final boolean ignoreTrailingWhitespace;

    public Regex(String regex, boolean ignoreTrailingWhitespace) {
         this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
         this.ignoreTrailingWhitespace = ignoreTrailingWhitespace;
    }

    @Override
    public @NotNull ParseResult parse(String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String match = matcher.group();
            String rest = input.substring(match.length());
            if (ignoreTrailingWhitespace) match = match.stripTrailing();
            return ParseResult.match(match, rest);
        }

        return ParseResult.fail(input);
    }
}
