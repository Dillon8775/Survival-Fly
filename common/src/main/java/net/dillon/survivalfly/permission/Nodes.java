package net.dillon.survivalfly.permission;

/**
 * All permission nodes for survival fly.
 */
public enum Nodes {
    FLIGHT("survivalfly.flight"),
    FLIGHT_SPEED("survivalfly.flight_speed");

    private final String node;

    Nodes(String node) {
        this.node = node;
    }

    /**
     * @return the permission node.
     */
    public String getNode() {
        return this.node;
    }
}