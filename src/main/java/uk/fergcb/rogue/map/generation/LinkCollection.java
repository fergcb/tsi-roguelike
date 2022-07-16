package uk.fergcb.rogue.map.generation;

import java.util.ArrayList;

public class LinkCollection extends ArrayList<Link> {
    public LinkCollection when (Link.LinkValidator validator) {
        this.forEach(link -> link.addValidator(validator));
        return this;
    }

    public LinkCollection then (Link.LinkCallback callback) {
        this.forEach(link -> link.addCallback(callback));
        return this;
    }
}
