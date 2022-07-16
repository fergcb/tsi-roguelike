package uk.fergcb.rogue;

import java.util.Locale;

public class Text {
    private static final String ANSI_BLACK = "\u001b[30m";
    private static final String ANSI_RED = "\u001b[31m";
    private static final String ANSI_GREEN = "\u001b[32m";
    private static final String ANSI_YELLOW = "\u001b[33m";
    private static final String ANSI_BLUE = "\u001b[34m";
    private static final String ANSI_MAGENTA = "\u001b[35m";
    private static final String ANSI_CYAN = "\u001b[36m";
    private static final String ANSI_WHITE = "\u001b[37m";
    private static final String ANSI_BRIGHT_GREEN = "\u001b[32;1m";
    private static final String ANSI_BRIGHT_WHITE = "\u001b[37;1m";

    private static final String ANSI_BG_BLACK = "\u001b[40m";
    private static final String ANSI_BG_RED = "\u001b[41m";
    private static final String ANSI_BG_GREEN = "\u001b[42m";
    private static final String ANSI_BG_YELLOW = "\u001b[43m";
    private static final String ANSI_BG_BLUE = "\u001b[44m";
    private static final String ANSI_BG_MAGENTA = "\u001b[45m";
    private static final String ANSI_BG_CYAN = "\u001b[46m";
    private static final String ANSI_BG_WHITE = "\u001b[47m";
    private static final String ANSI_BG_BRIGHT_WHITE = "\u001b[47;1m";
    private static final String ANSI_BG_246 = "\u001b[48;5;246m";

    private static final String ANSI_ITALIC = "\u001b[3m";

    private static final String ANSI_RESET = "\u001b[0m";

    public static String black(String text) {
        return ANSI_BLACK + text + ANSI_RESET;
    }

    public static String red(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String green(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String yellow(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static String blue(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String magenta(String text) {
        return ANSI_MAGENTA + text + ANSI_RESET;
    }

    public static String cyan(String text) {
        return ANSI_CYAN + text + ANSI_RESET;
    }

    public static String grey(String text) {
        return ANSI_WHITE + text + ANSI_RESET;
    }

    public static String lime(String text) {
        return ANSI_BRIGHT_GREEN + text + ANSI_RESET;
    }

    public static String white(String text) {
        return ANSI_BRIGHT_WHITE + text + ANSI_RESET;
    }

    public static String bgBlack(String text) {
        return ANSI_BG_BLACK + text + ANSI_RESET;
    }

    public static String bgRed(String text) {
        return ANSI_BG_RED + text + ANSI_RESET;
    }

    public static String bgWhite(String text) {
        return ANSI_BG_246 + text + ANSI_RESET;
    }

    public static String italic(String text) {
        return ANSI_ITALIC + text + ANSI_RESET;
    }

    public static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase(Locale.ROOT)
                + text.substring(1);
    }

}
