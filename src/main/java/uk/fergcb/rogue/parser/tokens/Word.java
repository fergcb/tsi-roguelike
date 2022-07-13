package uk.fergcb.rogue.parser.tokens;

import java.util.regex.Pattern;

public class Word extends Regex {

    private final Pattern pattern = Pattern.compile("");

    public Word() {
        super("^\\S+(\\s+|$)", true);
    }
}
