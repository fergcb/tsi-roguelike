package uk.fergcb.rogue.entities.items;

import java.util.Map;

public class Gem extends Item {

    public final GemType type;
    public GemQuality quality;

    public Gem (GemType type, GemQuality quality) {
        super();
        this.type = type;
        this.quality = quality;
    }

    @Override
    public String getName() {
        return type.colorize.apply(quality.name() + " " + type.name());
    }

    @Override
    public Map<String, Integer> getValidNames() {
        Map<String, Integer> validNames = super.getValidNames();
        validNames.put(type.name(), 5);
        validNames.put("GEM", 10);
        return validNames;
    }
    @Override
    public void tick() {}

    @Override
    public String draw() {
        return type.colorize.apply("◆");
    }
}
