package uk.fergcb.rogue.map.room;

import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.enitity.Entity;
import uk.fergcb.rogue.enitity.actor.Actor;
import uk.fergcb.rogue.enitity.actor.Player;
import uk.fergcb.rogue.map.Direction;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Room {
    public static final int HEIGHT = 3;
    public static final int WIDTH = 7;

    public final int previewColor;
    public final int x, y;
    public Map<Direction, Room> exits;
    public List<Entity> entities = new ArrayList<>();
    public Map<Entity, Direction> leavingEntities = new HashMap<>();
    public Map<Entity, Direction> arrivingEntities = new HashMap<>();

    public Room(int previewColor, int x, int y) {
        this.previewColor = previewColor;
        this.x = x;
        this.y = y;
        this.exits = new HashMap<>();
        this.init();
    }

    public void attach(Direction direction, Room room) {
        exits.put(direction, room);
        room.exits.put(Direction.inverse(direction), this);
    }

    public boolean hasExit(Direction direction) {
        return exits.containsKey(direction);
    }

    public Room getExit(Direction direction) {
        return exits.get(direction);
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
        entity.currentRoom = this;
    }

    public List<Entity> findEntity(String name) {
        List<Entity> matches = new ArrayList<>();
        for (Entity entity : this.entities) {
            int distance = entity.matchName(name);
            if (distance == -1) continue;
            if (distance == 0) return List.of(entity);
            matches.add(entity);
        }

        return matches;
    }

    public boolean hasPlayer() {
        return entities
                .stream()
                .filter(entity -> !leavingEntities.containsKey(entity))
                .anyMatch(entity -> entity instanceof Player);
    }

    public Player getPlayer() {
        return (Player)entities
                .stream()
                .filter(entity -> !leavingEntities.containsKey(entity))
                .filter(entity -> entity instanceof Player)
                .findFirst()
                .orElseThrow();
    }

    public String drawContents(Actor actor) {
        Point[] positions = new Point[] {
                new Point(0, 0), new Point(5, 2), new Point(1, 2), new Point(6, 0),
                new Point(6, 2), new Point(1, 0), new Point(5, 0), new Point(0, 2)
        };

        String[][] contents = new String[HEIGHT][WIDTH];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                contents[r][c] = r == 1 && c == 3 ? actor.draw() : " ";
            }
        }

        int entitiesToDraw = Math.min(positions.length, entities.size());
        for (int i = 0; i < entitiesToDraw; i++) {
            Entity entity = entities.get(i);
            if (entity == actor) continue;
            Point pos = positions[i];
            contents[pos.y][pos.x] = entity.draw();
        }

        return Stream.of(contents)
                .map(row -> String.join("", row))
                .collect(Collectors.joining("\n"));
    }

    public String draw(Actor actor) {
        StringBuilder sb = new StringBuilder();
        sb.append(hasExit(Direction.NORTH) ? "      ╔══╡" + Text.blue("▲") + "╞══╗\n" : "      ╔═══════╗\n");
        String contents = drawContents(actor);
        String[] rows = contents.split("\n");
        for (int i = 0; i < HEIGHT; i++) {
            String row = rows[i];
            if (hasExit(Direction.WEST) && i == 0) sb.append("      ╨");
            else if (hasExit(Direction.WEST) && i == 1) sb.append(Text.blue("      ◀"));
            else if (hasExit(Direction.WEST)) sb.append("      ╥");
            else sb.append("      ║");
            sb.append(row);
            if (hasExit(Direction.EAST) && i == 0) sb.append("╨");
            else if (hasExit(Direction.EAST) && i == 1) sb.append(Text.blue("▶"));
            else if (hasExit(Direction.EAST)) sb.append("╥");
            else sb.append("║");
            sb.append("\n");
        }
        sb.append(hasExit(Direction.SOUTH) ? "      ╚══╡" + Text.blue("▼") + "╞══╝\n" : "      ╚═══════╝\n");
        return sb.toString();
    }

    public String observe() {
        return "";
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("You are in a ");
        sb.append(this.getName());
        sb.append(".\nThere ");

        List<Entity> visibleEntities = entities
                .stream()
                .filter(entity -> !(entity instanceof Player))
                .filter(entity -> !arrivingEntities.containsKey(entity))
                .toList();

        int numEntities = visibleEntities.size();
        if (numEntities > 0) {
            sb.append(numEntities > 1 ? "are " : "is ");
            for (int i = 0; i < numEntities; i++) {
                Entity entity = visibleEntities.get(i);
                sb.append(entity.getIndefiniteName());
                if (numEntities > 1 && i < numEntities - 1) {
                    sb.append(i == numEntities - 2 ? " and " : ", ");
                }
            }
        } else {
            sb.append("is nothing");
        }
        sb.append(" here.\n");
        List<String> exits = exitStrings();
        if (exits.size() > 0) {
            sb.append(exits.size() == 1 ? "A doorway leads to the " : "Doorways lead to the ");
            for (int i = 0; i < exits.size(); i++) {
                sb.append(Text.blue(exits.get(i)));
                if (exits.size() > 1 && i < exits.size() - 1) {
                    sb.append(i == exits.size() - 2 ? " and " : ", ");
                }
            }
            sb.append(".\n");
        }

        if (this.observe().length() > 0) {
            sb.append("\n");
            sb.append(Text.italic(Text.grey(this.observe())));
        }

        return sb.toString();
    }

    public final void preTick() {
        entities.forEach(Entity::preTick);
    }

    public void tick() {
        entities.forEach(Entity::tick);
        entities.removeAll(leavingEntities.keySet());
    }

    public void postTick() {
        entities.forEach(Entity::postTick);

        entities.removeIf(entity -> entity instanceof Actor actor && actor.isDead());

        if (hasPlayer()) {
            Player player = getPlayer();

            arrivingEntities.keySet()
                    .removeIf(player::equals);

            if (!arrivingEntities.isEmpty()) {
                arrivingEntities.forEach((entity, dir) -> player.messageNowf(
                        "%s enters from the %s.\n",
                        Text.capitalize(entity.getIndefiniteName()),
                        Text.blue(dir.name()))
                );
                player.messageNow("");
            }

            if (!leavingEntities.isEmpty()) {
                leavingEntities.forEach((entity, dir) -> player.messageNowf(
                        "%s leaves the room, heading %s.\n",
                        Text.capitalize(entity.getIndefiniteName()),
                        Text.blue(dir.name()))
                );
                player.messageNow("");
            }
        }

        arrivingEntities.clear();
        leavingEntities.clear();
    }

    private List<String> exitStrings() {
        return exits.keySet()
                .stream()
                .map(Enum::name)
                .toList();
    }

    public abstract String getName();

    public abstract void init();
}
