package uk.fergcb.rogue.entities;

import uk.fergcb.rogue.map.rooms.Room;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Entity {

    public Room currentRoom = null;

    public static String stripArticle(String nounPhrase) {
        Pattern pattern = Pattern.compile("^(the|a|an)\\s+");
        Matcher matcher = pattern.matcher(nounPhrase);
        return matcher.replaceFirst("");
    }

    public String getDefiniteName() {
        return "the " + getName();
    }

    public String getIndefiniteName() {
        String vowels = "aeiou";
        if(vowels.indexOf(getName().charAt(0)) == -1) return "a " + getName();
        return "an " + getName();
    }

    public abstract String getName();
    public abstract void tick();
    public abstract String draw();
}
