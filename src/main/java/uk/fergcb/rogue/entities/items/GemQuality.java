package uk.fergcb.rogue.entities.items;

import java.util.Random;

public enum GemQuality {
    ROUGH,
    CUT,
    POLISHED,
    BRILLIANT,
    SPARKLING;

    public static GemQuality random() {
        Random random = new Random();
        return GemQuality.values()[random.nextInt(GemQuality.values().length)];
    }
}
