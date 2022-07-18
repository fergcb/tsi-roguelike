package uk.fergcb.rogue.parser.token;

import java.util.regex.Pattern;

public class Word extends Regex {

    private final Pattern pattern = Pattern.compile("");

    public Word() {
        super("^\\S+(\\s+|$)", true);
    }
}
