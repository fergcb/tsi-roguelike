package uk.fergcb.rogue.parser.tokens;

import org.jetbrains.annotations.NotNull;
import uk.fergcb.rogue.parser.ParseResult;
import uk.fergcb.rogue.parser.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keyword extends Regex {

    private static final List<String> keywords = Arrays.asList(
            "from",
            "at",
            "to",
            "with"
    );

    private static final String regex = "^(" + String.join("|", keywords) + ")(\\s+|$)";

    public Keyword() {
        super(regex, true);
    }
}
