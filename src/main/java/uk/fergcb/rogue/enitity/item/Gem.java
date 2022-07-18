package uk.fergcb.rogue.enitity.item;

import uk.fergcb.rogue.Text;

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
    public String describe() {
        return Text.capitalize(getDefiniteName()) + ".";
    }

    @Override
    public String draw() {
        return type.colorize.apply("◆");
    }

    @Override
    public Map<String, Integer> getValidNames() {
        Map<String, Integer> validNames = super.getValidNames();
        validNames.put(type.name(), 5);
        validNames.put("GEM", 10);
        return validNames;
    }
}
