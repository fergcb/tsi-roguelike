package uk.fergcb.rogue.map.generation;

import uk.fergcb.rogue.map.Direction;
import uk.fergcb.rogue.map.rooms.Room;


import java.util.*;

/**
 * A data structure to track the rules for Room adjacency in level generation
 *
 * Keeps a weighted list of outgoing room possibilities for each type of room and in each direction
 */
public class Index {

    private record Link (Class<? extends Room> roomClass, int weight) {}

    private final Map<Class<? extends Room>, Map<Direction, List<Link>>> rules;

    public Index() {
        this.rules = new HashMap<>();
    }

    /**
     * Add a rule that a target room is allowed when leaving an origin room in a given direction,
     * and specify the likelihood (weight) of that target room being chosen.
     * @param origin The current room
     * @param target The possible next room
     * @param weight The likelihood of this link being chosen
     * @param directions The directions in which this transition is allowed
     */
    public void addRule(Class<? extends Room> origin, Class<? extends Room> target, int weight, Direction... directions) {
        if (!rules.containsKey(origin)) {
            rules.put(origin, new HashMap<>());
        }

        Map<Direction, List<Link>> outboundRules = rules.get(origin);

        for (Direction direction : directions) {
            if(!outboundRules.containsKey(direction)) {
                outboundRules.put(direction, new ArrayList<>());
            }

            List<Link> allowedLinks = outboundRules.get(direction);

            allowedLinks.add(new Link(target, weight));
        }
    }

    /**
     * Check if a specified transition is allowed
     * @param origin The room type to transition from
     * @param target The room type to transition into
     * @param direction The direction of outbound travel
     * @return True if the transition is allowed, otherwise false
     */
    public boolean isAllowed(Class<? extends Room> origin, Class<? extends Room> target, Direction direction) {
        if (!rules.containsKey(origin)) return false;
        Map<Direction, List<Link>> outboundRules = rules.get(origin);
        if (!outboundRules.containsKey(direction)) return false;
        List<Link> allowedLinks = outboundRules.get(direction);
        return allowedLinks
                .stream()
                .filter(link -> link.roomClass() != null)
                .anyMatch(link -> link.roomClass().equals(target));
    }

    /**
     * Choose a target room type by weighted selection from the list of outbound links for a given origin room type
     * @param origin The room type being transitioned from
     * @param direction The direction of outbound travel
     * @return The class of the chosen Room type.
     */
    public Class<? extends Room> chooseNext(Class<? extends Room> origin, Direction direction) {
        Map<Direction, List<Link>> outboundRules = rules.get(origin);
        List<Link> allowedLinks = outboundRules.get(direction);
        if (allowedLinks == null) return null;

        int sumWeights = allowedLinks
                .stream()
                .mapToInt(Link::weight)
                .sum();

        Random random = new Random();
        // Put on a blindfold, spin around and stick a pin in the pile of links
        int pin = random.nextInt(sumWeights);

        // Climb up the pile of links until you find the pin
        Class<? extends Room> choice;
        int i = 0;
        do {
            final Link link = allowedLinks.get(i++);
            choice = link.roomClass();
            pin -= link.weight;
        } while (pin >= 0);

        return choice;
    }
}
