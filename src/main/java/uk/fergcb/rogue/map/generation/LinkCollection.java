package uk.fergcb.rogue.map.generation;

import java.util.ArrayList;

public class LinkCollection extends ArrayList<Link> {
    public LinkCollection when (Link.LinkValidator validator) {
        this.forEach(link -> link.setValidator(validator));
        return this;
    }
}
