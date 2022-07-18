package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.room.Room;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GeneratorState {
    public final int width, height;

    public final Map<Point, Room> generated;
    public final Stack<Point> toExpand;

    public GeneratorState(int width, int height) {
        this.width = width;
        this.height = height;

        generated = new HashMap<>();
        toExpand = new Stack<>();
    }

    public boolean isExpanding() {
        return this.toExpand.size() > 0;
    }

}
