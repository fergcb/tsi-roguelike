package uk.fergcb.rogue.entities.items;

import uk.fergcb.rogue.Text;

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
    public void tick() {}

    @Override
    public String draw() {
        return Text.lime("◆");
    }
}
