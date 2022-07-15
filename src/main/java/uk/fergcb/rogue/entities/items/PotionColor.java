package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

import java.util.Random;
import java.util.function.Function;

public enum PotionColor {
    GREEN(Text::green),
    PURPLE(Text::magenta),
    YELLOW(Text::yellow);

    public final Function<String, String> colorize;

    PotionColor (Function<String, String> colorize) {
        this.colorize = colorize;
    }

    public static PotionColor random() {
        Random random = new Random();
        return PotionColor.values()[random.nextInt(PotionColor.values().length)];
    }
}
