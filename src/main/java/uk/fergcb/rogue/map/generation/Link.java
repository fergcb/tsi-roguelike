package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.rooms.Room;

import java.awt.*;

public class Link  {
    public final Class<? extends Room> roomClass;
    public final int weight;

    public LinkValidator validator;

    public Link (Class<? extends Room> roomClass, int weight) {
        this.roomClass = roomClass;
        this.weight = weight;
        this.validator = (point, state) -> true;
    }

    public void setValidator (LinkValidator validator) {
        this.validator = validator;
    }

    public interface LinkValidator {
        boolean apply(Point point, GeneratorState state);
    }
}
