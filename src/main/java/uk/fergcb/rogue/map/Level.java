package uk.fergcb.rogue.map;

import uk.fergcb.rogue.map.rooms.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

/**
 * A data class to store information about a game level
 * @param width The width of the level in number of rooms
 * @param height The height of the level in number of rooms
 * @param startRoom The room where the player should start in this level
 */
public record Level (int width, int height, Room startRoom) {
    public Stream<Room> roomStream() {
        List<Room> visited = new ArrayList<>();
        Stack<Room> toVisit = new Stack<>();
        toVisit.push(startRoom);

        while (toVisit.size() > 0) {
            Room currentRoom = toVisit.pop();
            visited.add(currentRoom);
            currentRoom.exits
                    .values()
                    .stream()
                    .filter(room -> !visited.contains(room))
                    .forEach(toVisit::push);
        }

        return visited.stream();
    }
    public Iterator<Room> roomIterator () {
        return this.roomStream().iterator();
    }
}
