package uk.fergcb.rogue.map.rooms;

import uk.fergcb.rogue.Interaction;
import uk.fergcb.rogue.Text;
import uk.fergcb.rogue.entities.Actor;
import uk.fergcb.rogue.entities.Entity;
import uk.fergcb.rogue.entities.Interactable;
import uk.fergcb.rogue.entities.Player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Room {
    public static final int HEIGHT = 3;
    public static final int WIDTH = 7;

    public Room north, east, south, west;
    public List<Entity> entities = new ArrayList<>();

    public Room() {
        this.init();
    }

    public void addNorth(Room room) {
        this.north = room;
        room.south = this;
    }

    public void addEast(Room room) {
        this.east = room;
        room.west = this;
    }

    public void addSouth(Room room) {
        this.south = room;
        room.north = this;
    }

    public void addWest(Room room) {
        this.west = room;
        room.east = this;
    }

    public Entity findEntity(String name) {
        return this.entities
                .stream()
                .filter(entity -> entity
                        .getName()
                        .replaceAll("\u001B\\[[;\\d]*m", "")
                        .equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Interactable findInteractableEntity(String name) {
        return (Interactable) this.entities
                .stream()
                .filter(entity -> entity instanceof Interactable)
                .filter(entity -> entity
                        .getName()
                        .replaceAll("\u001B\\[[;\\d]*m", "")
                        .equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
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
            Point pos = positions[i];
            contents[pos.y][pos.x] = entity.draw();
        }

        return Stream.of(contents)
                .map(row -> String.join("", row))
                .collect(Collectors.joining("\n"));
    }

    public String draw(Actor actor) {
        StringBuilder sb = new StringBuilder();
        sb.append(north == null ? "      ╔═══════╗\n" : "      ╔══╡" + Text.blue("▲") + "╞══╗\n");
        String contents = drawContents(actor);
        String[] rows = contents.split("\n");
        for (int i = 0; i < HEIGHT; i++) {
            String row = rows[i];
            if (west != null && i == 0) sb.append("      ╨");
            else if (west != null && i == 1) sb.append(Text.blue("      ◀"));
            else if (west != null) sb.append("      ╥");
            else sb.append("      ║");
            sb.append(row);
            if (east != null && i == 0) sb.append("╨");
            else if (east != null && i == 1) sb.append(Text.blue("▶"));
            else if (east != null) sb.append("╥");
            else sb.append("║");
            sb.append("\n");
        }
        sb.append(south == null ? "      ╚═══════╝\n" : "      ╚══╡" + Text.blue("▼") + "╞══╝\n");
        return sb.toString();
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("You are in a ");
        sb.append(this.getName());
        sb.append(".\nThere ");
        if (entities.size() > 0) {
            sb.append(entities.size() > 1 ? "are " : "is ");
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = entities.get(i);
                sb.append(entity.getIndefiniteName());
                if (entities.size() > 1 && i < entities.size() - 1) {
                    sb.append(i == entities.size() - 2 ? " and " : ", ");
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
        return sb.toString();
    }

    private List<String> exitStrings() {
        List<String> exits = new ArrayList<>();
        if (north != null) exits.add("North");
        if (east != null) exits.add("East");
        if (south != null) exits.add("South");
        if (west != null) exits.add("West");
        return exits;
    }

    public abstract String getName();

    public abstract void init();

    public abstract void tick();
}
