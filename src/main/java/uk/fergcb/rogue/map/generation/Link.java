package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.room.Room;

import java.util.List;
import java.util.ArrayList;

import java.awt.Point;

public class Link  {
    public final Class<? extends Room> roomClass;
    public final int weight;

    private final List<LinkValidator> validators;
    private final List<LinkCallback> callbacks;

    public Link (Class<? extends Room> roomClass, int weight) {
        this.roomClass = roomClass;
        this.weight = weight;
        this.validators = new ArrayList<>();
        this.callbacks = new ArrayList<>();
    }

    public void addValidator (LinkValidator validator) {
        this.validators.add(validator);
    }

    public void addCallback(LinkCallback callback) {
        this.callbacks.add(callback);
    }

    public boolean validate (Point point, GeneratorState state) {
        return validators
                .stream()
                .allMatch(validator -> validator.apply(point, state));
    }

    public void callback (Room room, GeneratorState state) {
        for (LinkCallback callback : callbacks) {
            callback.apply(room, state);
        }
    }

    public interface LinkValidator {
        boolean apply(Point point, GeneratorState state);
    }

    public interface LinkCallback {
        void apply(Room room, GeneratorState state);
    }
}
