package uk.fergcb.rogue.map;

import uk.fergcb.rogue.map.rooms.Room;

/**
 * A data class to store information about a game level
 * @param width The width of the level in number of rooms
 * @param height The height of the level in number of rooms
 * @param startRoom The room where the player should start in this level
 */
public record Level (int width, int height, Room startRoom) {}
