package uk.fergcb.rogue.parser.tokens;

import java.util.Arrays;
import java.util.List;

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
