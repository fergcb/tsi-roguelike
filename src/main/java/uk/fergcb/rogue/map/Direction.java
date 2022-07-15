package uk.fergcb.rogue.map;

import java.awt.*;

public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    /**
     * Get the opposite cardinal direction to the one specified
     * @param dir The direction to invert
     * @return The opposite cardinal direction
     */
    public static Direction inverse(Direction dir) {
        return switch(dir) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }

    /**
     * The relative position described by this direction
     */
    public final Point vector;

    Direction(int rx, int ry) {
        this.vector = new Point(rx, ry);
    }
}
