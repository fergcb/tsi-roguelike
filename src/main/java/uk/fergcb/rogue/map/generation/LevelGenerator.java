package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.Direction;
import uk.fergcb.rogue.map.Level;
import uk.fergcb.rogue.map.rooms.*;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * The brain of the Level generation operation
 */
public class LevelGenerator {

    private final int width, height;
    private final Index index;

    public LevelGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.index = new Index();

        buildIndex();
    }

    /**
     * Establish the rules for transitioning between rooms
     */
    private void buildIndex() {
        // A starter room always has a hallway to the east and nothing else
        index.addRule(StarterRoom.class, HorizontalHallway.class, 100, Direction.EAST);
        index.addRule(StarterRoom.class, null, 100, Direction.NORTH, Direction.SOUTH, Direction.WEST);

        // Empty rooms can neighbour other rooms, and hallways
        index.addRule(EmptyRoom.class, EmptyRoom.class, 20, Direction.values());
        index.addRule(EmptyRoom.class, StorageRoom.class, 5, Direction.values());
        index.addRule(EmptyRoom.class, HorizontalHallway.class, 40, Direction.EAST, Direction.WEST)
                .when((point, state) -> point.x > 0 && point.x < state.width - 1);
        index.addRule(EmptyRoom.class, VerticalHallway.class, 40, Direction.NORTH, Direction.SOUTH)
                .when((point, state) -> point.y > 0 && point.y < state.height - 1);
        index.addRule(EmptyRoom.class, null, 40, Direction.values());

        // Horizontal hallways may only join other rooms to the east and west
        index.addRule(HorizontalHallway.class, HorizontalHallway.class, 30, Direction.EAST, Direction.WEST)
                .when((point, state) -> point.x > 0 && point.x < state.width - 1);
        index.addRule(HorizontalHallway.class, EmptyRoom.class, 60, Direction.EAST, Direction.WEST);
        index.addRule(HorizontalHallway.class, StorageRoom.class, 10, Direction.EAST, Direction.WEST);

        // Vertical hallways may only join other rooms to the north and south
        index.addRule(VerticalHallway.class, VerticalHallway.class, 30, Direction.NORTH, Direction.SOUTH)
                .when((point, state) -> point.y > 0 && point.y < state.height - 1);
        index.addRule(VerticalHallway.class, EmptyRoom.class, 60, Direction.NORTH, Direction.SOUTH);
        index.addRule(VerticalHallway.class, StorageRoom.class, 10, Direction.NORTH, Direction.SOUTH);
    }

    /**
     * Check if a given point is outside the map
     * @param p The Point to check
     * @return true if the Point is outside the map, otherwise false
     */
    private boolean isOutOfBounds(Point p) {
        return p.x < 0 || p.y < 0 || p.x >= width || p.y >= height;
    }

    /**
     * Generate a new Level from scratch
     *
     * Levels are generated by creating the starting room and then using a depth-first traversal
     * This is done iteratively on a stacks to avoid a large recursion depth for larger maps
     *
     * @return The generated Level
     */
    public Level generateLevel() {

        final int minSize = width * height / 4;

        // Place the starter room in the middle of the map
        final int sx = width / 2;
        final int sy = height / 2;

        final Point starterPoint = new Point(sx, sy);

        GeneratorState state;
        Room starterRoom;

        do {
            state = new GeneratorState(width, height);
            starterRoom = new StarterRoom(sx, sy);

            state.generated.put(starterPoint, starterRoom);
            state.toExpand.push(starterPoint);

            while (state.isExpanding()) {
                Point currentPoint = state.toExpand.pop();
                expand(currentPoint, state);
            }
        } while (state.generated.size() < minSize);

        insertCageRoom(starterRoom);

        return new Level(width, height, starterRoom);
    }

    /**
     * Find the room at a given point and choose its neighbours. Mark the neighbours to be expanded.
     * @param currentPoint The point to expand
     * @param state The current state of the generator
     */
    private void expand(Point currentPoint, GeneratorState state) {
        Room currentRoom = state.generated.get(currentPoint);

        for (Direction direction : Direction.values()) {
            Point vector = direction.vector;
            Point nextPoint = new Point(currentPoint.x + vector.x, currentPoint.y + vector.y);
            if (isOutOfBounds(nextPoint)) continue;

            // If this direction already has valid target room, just attach to it
            if (state.generated.containsKey(nextPoint)) {
                Room existingNeighbour = state.generated.get(nextPoint);
                if (index.isAllowed(currentRoom.getClass(), existingNeighbour.getClass(), direction, nextPoint, state)) {
                    currentRoom.attach(direction, existingNeighbour);
                }
                continue;
            }

            // Generate the new room
            Room nextRoom = createNextRoom(currentRoom, direction, nextPoint, state);
            if (nextRoom == null) continue;
            else currentRoom.attach(direction, nextRoom);

            // Attach the new room to the current one
            // Mark this point as having a generated room
            state.generated.put(nextPoint, nextRoom);
            // Remember to come back and explore the new room's neighbours
            state.toExpand.push(nextPoint);
        }
    }

    /**
     * Select the new room type and try to instantiate it
     * @param currentRoom The room being transitioned from
     * @param direction The outbound direction of travel
     * @param nextPoint The coordinates of the new room
     * @param state The current state of the generator
     * @return the new Room, or null if no room should be placed here
     */
    private Room createNextRoom(Room currentRoom, Direction direction, Point nextPoint, GeneratorState state) {
        Link link = index.chooseNext(currentRoom.getClass(), direction, nextPoint, state);
        if (link == null || link.roomClass == null) return null;
        Class<? extends Room> nextRoomClass = link.roomClass;
        try {
            Room room = nextRoomClass.getConstructor(int.class, int.class).newInstance(nextPoint.x, nextPoint.y);
            link.callback(room, state);
            return room;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertCageRoom(Room starterRoom) {
        Random random = new Random();

        Room current = starterRoom;
        Stack<Room> path = new Stack<>();

        while (current.exits.size() > 0) {
            path.add(current);
            List<Room> exits = current.exits.keySet()
                    .stream()
                    .map(current::getExit)
                    .filter(room -> !path.contains(room))
                    .toList();
            if (exits.size() == 0) break;
            current = exits.get(random.nextInt(exits.size()));
        }

        Room cageRoom = new CageRoom(current.x, current.y);

        for (Direction dir : Direction.values()) {
            if (current.hasExit(dir)) {
                cageRoom.attach(dir, current.getExit(dir));
            }
        }
    }

}
