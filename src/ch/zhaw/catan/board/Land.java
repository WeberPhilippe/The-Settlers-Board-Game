package ch.zhaw.catan.board;

import static ch.zhaw.catan.board.Resource.*;

/**
 * This {@link Enum} specifies the available lands in the game. Some land types
 * produce resources (e.g., {@link Land#FOREST}, others do not (e.g.,
 * {@link Land#WATER}.
 *
 * @author tebe
 */
public enum Land {
    FOREST(LUMBER),
    PASTURE(WOOL),
    FIELDS(GRAIN),
    MOUNTAIN(ORE),
    HILLS(BRICK),
    WATER("~~"),
    DESERT("--");

    private final String representation;
    private Resource resource = null;

    Land(Resource resource) {
        this(resource.toString());
        this.resource = resource;
    }

    Land(String representation) {
        this.representation = representation;
    }

    @Override
    public String toString() {
        return this.representation;
    }

    /**
     * Returns the {@link Resource} that this land provides or null,
     * if it does not provide any.
     *
     * @return the {@link Resource} or null
     */
    public Resource getResource() {
        return resource;
    }
}
